package com.chatak.pg.upstream.tcpIp;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOValidator;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.GenericValidatingPackager;

import com.chatak.pg.util.ByteConversionUtils;
import com.chatak.pg.util.JPOSUtil;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


@SuppressWarnings("unused")
public class TcpClient {

  protected Logger logger = Logger.getLogger(this.getClass());

  private int mPort = 22505;

  private String mHost = "192.168.3.21";

  private byte[] _txnOut;

  private String configFilePath = "config";

  private Socket socket = null;

  public ISOMsg _responseIsoMsg;

  /**
   * Default constructor for TcpClient.
   */
  public TcpClient() {
    super();
  }

  
  /**
   * Init Method
   * 
   * @param port
   * @param host
   * @param txnOut
   * @return ISOMsg
   */
  public ISOMsg init(int port, String host, byte[] txnOut) {
    mPort = port;
    mHost = host;
    _txnOut = txnOut;
    ISOMsg isomsg = runSync();
    
    return isomsg;
  }

 
  /**
   * Implementation of run method from Runnable
   * 
   * @return ISOMsg
   */
  public ISOMsg runSync() {
    Socket socket = null;
    try {
      socket = createSocket();
      // send Request and receive Response data
      return transactionFlow(socket);
    }
    catch(Exception e) {
      logger.error("RunError: " + e.getMessage());
    }
    return null;
  }

  /**
   * This method creates a default Socket
   * 
   * @return
   * @throws Exception
   */
  protected Socket createSocket() throws Exception {
    return createSocket(mHost, mPort);
  }

  /**
   * This method create a socket
   * 
   * @param host
   * @param port
   * @return Socket
   * @throws Exception
   */
  protected Socket createSocket(String host, int port) throws Exception {
    try {
      // 1) Creating the socket connection on the agreed port
      if(socket == null || socket.isClosed()) {
        socket = new Socket(host, port);
        logger.info("Created standard socket, host: " + host + "::" + port + " from port: " + socket.getLocalPort());
      }
      else {
        logger.info("Using already created standard socket, host: " + host + "::" + port + " from port: "
                    + socket.getLocalPort());
      }
    }
    catch(UnknownHostException e) {
      String msg = "Unable to create socket. Host: " + host + ", Port: " + port;
      throw new Exception(msg, e);
    }
    catch(IOException e) {
      String msg = "Unable to create socket. Host: " + host + ", Port: " + port;
      throw new Exception(msg, e);
    }
    catch(SecurityException e) {
      String msg = "Unable to create server socket. Host: " + host + ", Port: " + port;
      throw new Exception(msg, e);
    }
    catch(Exception e) {
      String msg = "Unable to create server socket. Host: " + host + ", Port: " + port;
      throw new Exception(msg, e);
    }
    return socket;
  }


  /**
   * This method is responsible for the transmission of data to the server
   * 
   * @param Socket
   *          : Ths socket where the server listens It is usually 443 for SSL
   * @return ISOMsg
   * @throws Exception
   */
  protected ISOMsg transactionFlow(Socket socket) throws Exception {
		ISOMsg reply = null;
		Date date = new Date();
		logger.info("Inside the RIGHT SSL transactionFlow");

		byte[] txnIn = new byte[1024];
		byte[] txnOut = this.get_txnOut();

		BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

		logger.info(">>> 0 - Txn Request created - size: " + txnOut.length);

		// Print REQUEST data
		printData("REQ. ", txnOut, txnOut.length);

		boolean debugFlow = false;
		int readSize = -1;
		//while(socket.isConnected() && (readSize = bis.read(txnIn)) >= 0) {
		while(socket.isConnected()) {
			logger.info(">>> 1 - Received size: " + readSize);
			//Write to socket
			bos.write(txnOut);
			bos.flush();

			//Read from socket
			readSize = bis.read(txnIn);
			logger.info(">>> 3 - Received size: " + readSize);

			// 3. if received ResponseRecord, send ACK
			try{
				if(readSize > 1) {
					logger.info(">>> Received response...start processing ");
					byte[] txnRes = null;
					printData("RESP: ", txnIn, readSize);
					logger.info("txnIn >> "+txnIn);

					String respString = ByteConversionUtils.byteArrayToString(txnIn, readSize);
					logger.info("Resp ascii"+respString);

					ISOMsg isoMessage = new ISOMsg();

					GenericPackager p2 = JPOSUtil.getChatakGenericPackager();
					isoMessage.setPackager(p2);
					isoMessage.unpack(respString.getBytes());
					ISOValidator isoValidator = JPOSUtil.getChatakISOValidator();
					isoMessage = (ISOMsg) isoValidator.validate(isoMessage);
					logger.info("Switch Response ISO VALIDATED");
					JPOSUtil.logISOData(isoMessage, logger);
					reply = isoMessage;
					break;
				} else {
					logger.info(">>> 3 - Received unexpected msg: " + new String(txnIn, 0, readSize));
					break;
				}
			}catch(Exception e){
				logger.error("Exception in client response",e);
			}finally{
				if(bis != null) // Close input stream
					bis.close();
				if(bos != null) // Close outpur stream
					bos.close();
				if(socket != null) // Close socket
					socket.close();
			}
		}
		return reply;
	}

  /**
   * The sleep(long sleepTime) causes the currently executing thread to sleep
   * (temporarily cease execution) for the specified number of milliseconds. The
   * thread does not lose ownership of any monitors.
   * 
   * @param millis
   *          - the length of time to sleep in milliseconds. Throws: The
   *          currently executing thread's sleep(...) method throws
   *          InterruptedException if another thread has interrupted the current
   *          thread. That exception is handled within this method and system
   *          error output will show message.
   */
  protected void sleep(long millis) {
    try {
      Thread.sleep(millis);
    }
    catch(InterruptedException e) {
      logger.error("Awakened prematurely !");
    }
  }

  /**
   * Returns the configFilePath value
   * 
   * @return the configFilePath
   */
  public String getConfigFilePath() {
    return configFilePath;
  }

  /**
   * Set the configFilePath value
   * 
   * @param configFilePath
   *          the configFilePath to set
   */
  public void setConfigFilePath(String configFilePath) {
    this.configFilePath = configFilePath;
  }

  /**
   * Returns the _txnOut value
   * 
   * @return the _txnOut
   */
  public byte[] get_txnOut() {
    return _txnOut;
  }

  /**
   * Set the _txnOut value
   * 
   * @param out
   *          the _txnOut to set
   */
  public void set_txnOut(byte[] out) {
    _txnOut = out;
  }

  /**
   * Returns the _responseIsoMsg value
   * 
   * @return the _responseIsoMsg
   */
  public ISOMsg get_responseIsoMsg() {
    return _responseIsoMsg;
  }

  /**
   * Set the _responseIsoMsg value
   * 
   * @param isoMsg
   *          the _responseIsoMsg to set
   */
  public void set_responseIsoMsg(ISOMsg isoMsg) {
    _responseIsoMsg = isoMsg;
  }

  /**
   * Print message data as ASCII and HEX
   * 
   * @param data
   *          message data byte array
   * @param numBytes
   */
  protected void printData(String pfxMsg, byte[] data, int numBytes) {
    logger.info(pfxMsg + ">>>TcpClinet ASCII: " + ByteConversionUtils.byteArrayToString(data, numBytes));
    logger.info(pfxMsg + ">>>TcpClinet   HEX: " + ByteConversionUtils.byteArrayToHexString(data, numBytes));
  }
  
  
}