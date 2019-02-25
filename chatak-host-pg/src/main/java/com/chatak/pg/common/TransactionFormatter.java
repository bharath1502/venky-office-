package com.chatak.pg.common;

import org.apache.log4j.Logger;

import com.chatak.pg.util.ByteConversionUtils;

/**
 * This class helps in formatting the Transaction to the required format.
 */
public class TransactionFormatter {

  // Logger for TransactionHandler
  private Logger logger = Logger.getLogger(TransactionFormatter.class);

  /**
   * Default constructor
   */
  public TransactionFormatter() {
    super();
  }

  /**
   * Package the ISO data bytes in a the agreed format.
   * 
   * @param dataBytes
   * @return byte[]
   */
  public byte[] packPGFormat(byte[] dataBytes, String respTpdu) {

    int index = 0;
    int txnLength = dataBytes.length;
    // Getting the length of the complete formatted transaction
    int totalLength = txnLength;

    byte[] completeTxnByte = null;

    // deducting the length 2 bytes
    byte[] packetLengthBytes = ByteConversionUtils.intToByteArray(totalLength - 2);

    // Checking dialup TPDU present
    if("".equals(respTpdu.trim())) { // First 2 bytes 00 + 2 bytes length + data
      totalLength = totalLength + 2;
      completeTxnByte = new byte[totalLength];

      byte[] blankBytes = ByteConversionUtils.HexStringToByteArray("00");
      int blankBytesLength = blankBytes.length;

      for(int i = 0; i < blankBytesLength; i++) {
        completeTxnByte[index++] = blankBytes[i];
      }

      // length
      for(int i = 0; i < packetLengthBytes.length; i++) {
        completeTxnByte[index++] = packetLengthBytes[i];
      }
    }
    else {// 2 bytes length + TPDU + data
      // TPDU length to consider - message length (2byte) {TPDU+data} + TPDU
      // (5byte) + data
      totalLength = 2 + 5 + totalLength;
      completeTxnByte = new byte[totalLength];

      // Setting first 2 bytes with the full length of packet
      completeTxnByte[0] = packetLengthBytes[0];
      completeTxnByte[1] = packetLengthBytes[1];

      index = 2;

      String resultTpdu = getResponseTPDU(respTpdu);
      byte[] tpduBytes = ByteConversionUtils.HexStringToByteArray(resultTpdu);
      int tpduBytesLength = tpduBytes.length;

      for(int i = 0; i < tpduBytesLength; i++) {
        completeTxnByte[index++] = tpduBytes[i];
      }
    }

    // data
    for(int i = 0; i < txnLength; i++) {
      completeTxnByte[index++] = dataBytes[i];
    }

    // LRC
    // completeTxnByte[index] = ComputingCheck.getLRCByte(completeTxnByte,
    // index);
    return completeTxnByte;
  }

  /**
   * Method to get TPDU data for response from the ISO request
   * 
   * @param tpduData
   * @return
   */
  private static String getResponseTPDU(String tpduData) {
    String tpduId = "";
    String destination = "";
    String source = "";

    tpduId = tpduData.substring(0, 2);
    destination = tpduData.substring(2, 6);
    source = tpduData.substring(6, tpduData.length());
    return tpduId + source + destination;

  }

  /**
   * This method is used to validate the PG Format for the lower level data
   * transmission protocol. The protocol would be as follows: We have two cases
   * where length is in ASCII or in Binary In Binary mode the data format is:
   * <STX(1)> <IIN>(6)<LENGTH(2)> <DATA> <ETX(1)> <LRC(1)> In ASCII mode
   * <STX(1)> <IIN>(6)<LENGTH(4)> <DATA> <ETX(1)> <LRC(1)> where LOD =
   * LengthOfData
   * 
   * @param : 1) byte[] The complete data that we receive 2) int The length of
   *        the data received.
   * @param bytesRead
   * @param isIINPresent
   * @return: String The <Data> after stripping of everythin else
   */
  public byte[] unpackPGFormat(byte[] completeTxnByte, int bytesRead, boolean tpduFlag) {

    // now check if the data packet has the binary length. Try parsing that way
    // and if it fails try the ascii mode.
    byte[] validData = unpackBinaryLengthPacket_ISO2003(completeTxnByte, tpduFlag);

    if(validData == null) {
      validData = unpackASCIILengthPacket_ISO2003(completeTxnByte, tpduFlag);
      if(validData == null) {
        logger.debug("Ascii length packet too got rejected");
        return null;
      }
    }
    return validData;
  }

  /**
   * This method is used to validate the PG Format for the lower level data
   * transmission protocol. The protocol would be as follows: We have two cases
   * where length is in ASCII or in Binary In Binary mode the data format is:
   * <STX(1)> <IIN>(6)<LENGTH(2)> <DATA> <ETX(1)> <LRC(1)> In ASCII mode
   * <STX(1)> <IIN>(6)<LENGTH(4)> <DATA> <ETX(1)> <LRC(1)> where LOD =
   * LengthOfData
   * 
   * @param : 1) byte[] The complete data that we receive 2) int The length of
   *        the data received.
   * @param bytesRead
   * @param isIINPresent
   * @return: String The <Data> after stripping of everythin else
   */
  public byte[] unpackPGFormat_ISO2003(byte[] completeTxnByte, int bytesRead, boolean isIINPresent) {
    boolean status = false;
    // total trx length
    int totalLength = completeTxnByte.length;
    int index = 0;
    // Do few validations Check STX
    for(int i = 0; i < totalLength; i++) {
      if(completeTxnByte[i] == Protocol.STX) {
        index = ++i;
        status = true;
        break;
      }
    }
    if(!status) {
      logger.fatal("Data format error.Could not find the STX at the start");
      return null;
    }

    if(isIINPresent) {
      // skip the 6 bytes.Dav confirmed that all packets have IIN.
      index = index + 6;
    }

    // now check if the data packet has the binary length. Try parsing that way
    // and if it fails try the ascii mode.
    byte[] validData = unpackBinaryLengthPacket_ISO2003(completeTxnByte, isIINPresent);

    if(validData == null) {
      validData = unpackASCIILengthPacket_ISO2003(completeTxnByte, isIINPresent);
      if(validData == null) {
        logger.debug("Ascii length packet too got rejected");
        return null;
      }
    }
    return validData;
  }

  /**
   * Unpacks the data packet assuming binary length.
   * 
   * @param data
   * @param isINNPresent
   * @return valid data packet or null if can not be parsed as binary
   */
  private byte[] unpackBinaryLengthPacket_ISO2003(byte[] data, boolean tpduFlag) {
    // Now detect the length of the transaction record in binary
    int indexBinary = 0;

    byte[] lengthBytesBinary = new byte[2];

    if(tpduFlag) {
      // if first 2 bytes are not 0000 then message format is length(2bytes) +
      // 5bytes tpdu + data
      // Skipping 2 bytes length and 5 bytes tpdu

      // indexBinary = 7;
      // memcpy(lengthBytesBinary, data, 2 );
      lengthBytesBinary[0] = data[indexBinary++];
      lengthBytesBinary[1] = data[indexBinary++];
      // indexBinary = indexBinary + 5; //5 bytes tpdu
    }
    else {
      // if first 2 bytes is 0000 then message format is length(4bytes) + data
      // Skipping 4 bytes length
      // indexBinary = 4;
      // memcpy(lengthBytesBinary, data+2, 2 ); // first 2 bytes are
      indexBinary = 2;
      lengthBytesBinary[0] = data[indexBinary++];
      lengthBytesBinary[1] = data[indexBinary++];
    }

    int bufLength = data.length;

    int txnLODBinary = ByteConversionUtils.getIntFromByteArray(lengthBytesBinary);

    logger.debug("Length in Binary - Complete Packet: " + bufLength + ", Payload: " + txnLODBinary);

    // Check LOD, if false return null
    if((txnLODBinary + indexBinary) > data.length) {
      logger.warn("Check LOD failed asumming binary length.This may be having ascii length , return null");
      logger.warn("Check LOD failed asumming binary length.This may be having ascii length , return null");
      return null;
    }

    if(tpduFlag) {
      indexBinary = indexBinary + 5; // 5 bytes tpdu
      txnLODBinary = txnLODBinary - 5;
    }
    
    //Quick fix to simulator issue on auth completion
    txnLODBinary=txnLODBinary+1;

    byte[] dataRetrieved = new byte[txnLODBinary];
    // Now retrieve the real transaction data
    for(int i = 0; i < txnLODBinary; i++) {
      byte[] buf = new byte[1];
      buf[0] = data[indexBinary++];
      dataRetrieved[i] = buf[0];
    }
    return dataRetrieved;
  }

  /**
   * Unpacks the data packet assuming binary length.
   * 
   * @param data
   * @param isINNPresent
   * @return valid data packet or null if can not be parsed as binary
   */
  private byte[] unpackASCIILengthPacket_ISO2003(byte[] data, boolean isINNPresent) {
    // Now detect the length of the transaction record in binary
    int indexAscii = isINNPresent ? 7 : 1;
    // now the length in 4 bytes
    byte[] lengthBytesAscii = new byte[4];
    lengthBytesAscii[0] = data[indexAscii++];
    lengthBytesAscii[1] = data[indexAscii++];
    lengthBytesAscii[2] = data[indexAscii++];
    lengthBytesAscii[3] = data[indexAscii++];

    int bufLength = data.length;
    String asciiLength = new String(lengthBytesAscii);
    int txnLODAscii = 0;
    try {
      txnLODAscii = Integer.parseInt(asciiLength);
    }
    catch(NumberFormatException e) {
      logger.fatal("Ascii length not an integer" + asciiLength);
      return null;
    }
    logger.debug("Length in Ascii - Row: " + bufLength + ", Payload: " + txnLODAscii);

    // Check LOD, if false return null
    if((txnLODAscii + indexAscii + 2) > data.length) {
      logger.error("Check LOD failed when assumed Ascii length , return null");
      return null;
    }

    byte[] dataRetrieved = new byte[txnLODAscii];
    // Now retrieve the real transaction data
    for(int i = 0; i < txnLODAscii; i++)
      dataRetrieved[i] = data[indexAscii++];

    // Now do check for ETX
    if(data[indexAscii++] != Protocol.ETX) {
      logger.error("Check ETX, failed when assumed Ascii length , return null");
      return null;
    }

    // Now do check for LRC - Compare received and calculated LRC
    byte rcvdLRC = data[indexAscii];
    byte calcLRC = ComputingCheck.getLRCByte(data, indexAscii);
    if(rcvdLRC == calcLRC) {
      logger.debug("Computed LRC: " + calcLRC + " matches LRC recieved " + rcvdLRC);
      return dataRetrieved;
    }
    else {
      logger.error("Computed LRC: " + calcLRC + " does not match LRC recieved " + rcvdLRC);
    }
    logger.error("Check LRC failed when assumed Ascii length , return null");
    return null;
  }

  /**
   * This method is used to validate the Response Data Format
   * 
   * @param byte[] responseBuffer The complete data that we receive
   * @return boolean status of Response validation
   */
  public boolean validateResponseMsg(byte[] responseBuffer) {
    boolean status = true;
    if(responseBuffer == null || responseBuffer.length == 0)
      status = false;
    return status;
  }
}