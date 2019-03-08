package com.chatak.pg.server.tcpIp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.chatak.pg.acq.processor.TransactionHandler;
import com.chatak.pg.common.TransactionFormatter;
import com.chatak.pg.util.ByteConversionUtils;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.JPOSUtil;

/**
 * @Comments:This class is responsible for handling a single client session on
 *                the server side.
 */

public class ClientHandler implements Runnable {

  private Logger logger = Logger.getLogger(ClientHandler.class);;

  private final static String VERSION = "V-1.0";

  protected String mVersion = null;

  protected String mServerTypeVersion = null;

  // The client socket - stream socket that connects client to the specified
  // port number on the named host.
  protected Socket mSocket = null;

  protected TcpServer mServer = null;

  /**
   * Parameterized constructor
   * 
   * @param mSocket
   *          accepted client socket
   */
  public ClientHandler(Socket socket, TcpServer server) {
    // set socket
    mSocket = socket;
    mServer = server;
    init();
    mServerTypeVersion = mServer.getType() + " " + mVersion;
  }

  protected void init() {
    isSocketOpen = true;
    mVersion = VERSION;
    try {
      mSocket.setSoTimeout(Constants.CHATAK_DOWNSTREAM_SOCKET_TIMEOUT);
    }
    catch(SocketException e) {
      logger.error("Error in setting Chatak socket timeout", e);
    }
  }

  /**
   * Runnable interface function
   */
  private boolean isSocketOpen = false;

  public void run() {
    while(isSocketOpen) {
      // if the server type NIP handle it NIP way
      String serverType = mServer.getType();
      logger.info("Waiting for more request to server " + serverType + " from " + mSocket.getPort());
      synchronized(this) {
        transactionFlow();
      }
    }
  }

  /**
   * Close the connection
   */
  private void closeClient() {
    logger.info("Closing the client connection from " + mSocket.getPort());
    isSocketOpen = false;
    try {
      mSocket.close();
    }
    catch(IOException e) {
      logger.error("Error closing socket . " + e.getMessage());
    }
  }

  /**
   * Transaction flow between POS and server .
   */
  protected void transactionFlow() {
    boolean txnCompleted = false;
    try {
      // Get input/output streams from the socket
      InputStream in = mSocket.getInputStream();
      BufferedInputStream bis = new BufferedInputStream(in);

      // 1. send ENQ to client

      // 2. receive data record from client
      
      byte[] buffer = new byte[1024];
      int nBytes = -1;

      try {
        if((nBytes = readStream(bis, buffer)) > 0) {
          txnCompleted = processTransaction(bis, buffer, nBytes);
        }
      }
      catch(Throwable e) {
        // ignore until we know what to do
        e.printStackTrace();
        closeClient();
        String msg = e.getMessage();
        if(msg == null)
          msg = "";
        logger.error("Caught unexpected ERROR -" + msg + "- client: " + mSocket.getPort(), e);
      }

      // 7. send EOT - End of transaction

    }
    catch(Throwable e) {
      closeClient();
      String msg = e.getMessage();
      if(msg == null)
        msg = "";
      logger.error("Caught unexpected ERROR -" + msg + "- client: " + mSocket.getPort(), e);
    }

    if(txnCompleted) {
      logger.info(">>> Transaction completed - Status OK   <<<");
    }
    else {
      logger.error(">>> No Valid Data...and closing the client socket  <<<");
      closeClient();
    }
    logger.info(">>> Txn completed - Session closed...");
  }
  
  /**
   * @param bis
   * @param buffer
   * @param nBytes
   * @return
   * @throws Throwable
   */
  private boolean processTransaction(BufferedInputStream bis, byte[] buffer, int nBytes) throws Throwable {
    
    boolean tpduFlag = true;
    boolean txnCompleted = false;
    byte[] txnReq = null;
    long startTime = System.currentTimeMillis();
    
    String respTpdu = "";
    TransactionFormatter formatter = new TransactionFormatter();
    OutputStream out = mSocket.getOutputStream();
    
    String inRequestString = ByteConversionUtils.byteArrayToHexString(buffer, nBytes, false);
    JPOSUtil.printData("REQUEST", buffer, nBytes, logger);

    // if first 2 bytes are not 00 then message format is length(2bytes) +
    // 5bytes tpdu + data
    // if first 2 bytes is 00 then message format is length(4bytes) + data
    if(inRequestString.substring(0, 4).equals(Constants.CHATAK_ACQ_ISO_PACKET_FIRST_4_BYTES_VALUE)) {
      tpduFlag = false;
    } else {
      respTpdu = inRequestString.substring(4, 14);
    }
    logger.info("tpduFlag:" + tpduFlag);

    byte[] txnResponse = null;

    if((txnReq = formatter.unpackPGFormat(buffer, nBytes, tpduFlag)) != null) {

      // Unpack PG Format and Process Transaction
      txnResponse = new TransactionHandler(mSocket).processTransaction(txnReq);

      // now do pack response
      txnResponse = formatter.packPGFormat(txnResponse, respTpdu);

      logger.info("<== (4) End processing transaction.");

      // Show Response
      JPOSUtil.printData("RESPONSE", txnResponse, txnResponse.length, logger);

      // Validate response before sending
      if(!formatter.validateResponseMsg(txnResponse)) {
        logger.info(">>> Invalid Response, roll back");
      }
      else {

        // 5. send response data back
        logger.info("<== Sending response to the client, port: " + mSocket.getPort());
        sendResponseLine(out, txnResponse);

        long endTime = System.currentTimeMillis();
        logger.info("Time taken for transaction" + (endTime - startTime));
        // 6. wait for ACK and check it
        txnCompleted = true;
        /**
         * Committed below since there is no Acknowledge from the POS
         */

      }
    }
    return txnCompleted;
  }

  /**
   * This method used read the stream
   * 
   * @param bis
   * @param buffer
   * @return int
   */
  private int readStream(BufferedInputStream bis, byte[] buffer) {
    int retVal = -1;
    try {
      retVal = bis.read(buffer);
    }
    catch(SocketTimeoutException e) {
      logger.info("TimeoutException Reading from socket  " + mSocket.getPort() + ". I am no longer listening..");
      closeClient();
    }
    catch(IOException e2) {
      logger.info("IOException  Reading from socket  " + mSocket.getPort() + ". I am no longer listening..");
      closeClient();
    }
    return retVal;
  }

  /**
   * writes control character to output stream
   * 
   * @param out
   *          output stream to write to
   * @param control
   *          control character
   */
  @SuppressWarnings("unused")
  private void sendControlByteLine(OutputStream out, int control) {
    try {
      out.write(control);
      out.flush();
    }
    catch(IOException e) {
      logger.info("IOExcepton " + e.getMessage() + " for client " + mSocket.getPort()
                  + ".Hence close polling the client");
      closeClient();
    }
  }

  /**
   * write vital response to given output stream
   * <p/>
   * first the response is converted to 7-even parity and a LRC is appended to
   * it
   * 
   * @param out
   *          output stream to write to
   * @param response
   *          response byte data
   * @param length
   *          length of response in bytes
   */
  protected void sendResponseLine(OutputStream out, byte[] response) {
    if(response != null)
      sendResponseLine(out, response, response.length);
  }

  /**
   * This method send the response line
   * 
   * @param out
   * @param response
   * @param length
   */
  protected void sendResponseLine(OutputStream out, byte[] response, int length) {
    try {
      out.write(response, 0, length);
      out.flush();
    }
    catch(IOException e) {
      logger.error("Caught IOExcepton " + e.getMessage() + " for client, port: " + mSocket.getPort());
      closeClient();
    }
  }

  String HexToBinary(String Hex) {
    int i = Integer.parseInt(Hex, 16);
    String Bin = Integer.toBinaryString(i);
    return Bin;
  }

  protected void sendResponseLine1(OutputStream out, String response) {
    if(response != null)
      try {
        out.write(response.getBytes(Charset.forName("UTF-8")), 0, response.length());
        out.flush();
      }
      catch(IOException e) {
        logger.error("Caught IOExcepton " + e.getMessage() + " for client, port: " + mSocket.getPort());
        closeClient();
      }
  }

  protected void sendResponseLine2(OutputStream out, byte[] response) {
    if(response != null)
      try {
        out.write(response);
        out.flush();
      }
      catch(IOException e) {
        logger.error("Caught IOExcepton " + e.getMessage() + " for client, port: " + mSocket.getPort());
        closeClient();
      }
  }
  
}