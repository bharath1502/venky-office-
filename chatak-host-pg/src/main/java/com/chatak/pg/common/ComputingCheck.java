package com.chatak.pg.common;

import com.chatak.pg.util.ByteConversionUtils;

/**
 * This class is used to compute all the checks required before
 * sending the request or respose. As of now it has only one method
 * for LRC. Later on LUHN generation, etc could be added
 */
public class ComputingCheck {

  /**
   * Default Constructor no paramters as of now. Intentionally private, to
   * prevent instantiation, for now.
   */
  private ComputingCheck() {
  }

  /**
   * This method computes the LRC, given a array of bytes. We EX-OR all the
   * bytes in the array including the <ETX>, but excluding the <STX> The array
   * sent to us would typically be of the form <STX><LOD>data<ETX><LRC>
   * 
   * @params: 1. The entire Transaction message about to be sent including a
   *          dummy LRC characer 2. The index of LRC
   * @return: The computed LRC value ( 1 byte ) as String
   */
  public static String getLRCString(byte[] messageArray, int index) {
    return ByteConversionUtils.byteToHexString(getLRCByte(messageArray, index));
  }

  /**
   * This method computes the LRC, given a array of bytes. We EX-OR all the
   * bytes in the array including the <ETX>, but excluding the <STX> The array
   * sent to us would typically be of the form <STX><LOD>data<ETX><LRC>
   * 
   * @params: 1. The entire Transaction message about to be sent including a
   *          dummy LRC characer 2. The index of LRC
   * @return: The computed LRC value ( 1 byte )
   */
  public static byte getLRCByte(byte[] messageArray, int index) {
    byte lrc = 0x00;
    int lrc1 = 0x00;

    for(int x = 1; x < (index); x++) {
      lrc1 = lrc1 ^ messageArray[x];
    }

    lrc = (byte) lrc1;
    return lrc;
  }
}