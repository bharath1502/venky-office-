package com.chatak.pg.common;

import org.jpos.iso.ISOMsg;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This helper class used for convert the ISO Message into string
 *           format
 */
public class ISOUtils {
	
	private ISOUtils() {
		
	}

  /**
   * This method convert the ISO Message into string format.
   * 
   * @param isoMsg
   * @return String
   */
  public static String getISOMsgXML(ISOMsg isoMsg) {
    if(isoMsg == null) {
      return null;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    isoMsg.dump(new PrintStream(baos), "");
    return baos.toString();
  }
}
