/**
 * 
 */
package com.chatak.pg.util;

import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.ISOValidator;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.GenericValidatingPackager;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 15-Jan-2015 10:58:15 AM
 * @version 1.0
 */
public final class JPOSUtil {

  private static GenericPackager packager = null;

  private static ISOValidator isoValidator = null;

  private static GenericPackager chatakPackager = null;

  private static ISOValidator chatakIsoValidator = null;

  private JPOSUtil() {

  }

  /**
   * Method to get GenericPackager object
   * 
   * @return
   * @throws ISOException
   */
  public static GenericPackager getGenericPackager() throws ISOException {
    if(null == packager) {
      packager = new GenericPackager(FileUtil.getJPOSPackagerFileInputStream());
    }
    return packager;
  }

  /**
   * Method to get chatak GenericPackager object
   * 
   * @return
   * @throws ISOException
   */
  public static GenericPackager getChatakGenericPackager() throws ISOException {
    if(null == chatakPackager) {
      chatakPackager = new GenericPackager(FileUtil.getJPOSChatakPackagerFileInputStream());
    }
    return chatakPackager;
  }

  /**
   * Method to get ISOValidator object
   * 
   * @return
   * @throws ISOException
   */
  public static ISOValidator getISOValidator() throws ISOException {
    if(null == isoValidator) {
      isoValidator = new GenericValidatingPackager(FileUtil.getJPOSPackagerFileInputStream());
    }
    return isoValidator;
  }

  /**
   * Method to get chatak ISOValidator object
   * 
   * @return
   * @throws ISOException
   */
  public static ISOValidator getChatakISOValidator() throws ISOException {
    if(null == chatakIsoValidator) {
      chatakIsoValidator = new GenericValidatingPackager(FileUtil.getJPOSChatakPackagerFileInputStream());
    }
    return chatakIsoValidator;
  }

  /**
   * Method to log ISO Data
   * 
   * @param isoMsg
   * @param logger
   */
  public static void logISOData(ISOMsg isoMsg, Logger logger) {
    isoMsg.dump(createLoggingProxy(System.err, logger), " ");
  }

  /**
   * Method to get create Logging Proxy
   * 
   * @param realPrintStream
   * @param logger
   * @return
   */
  public static PrintStream createLoggingProxy(final PrintStream realPrintStream, final Logger logger) {
    return new PrintStream(realPrintStream) {
    	@Override
    	public void print(final String string) {
        //realPrintStream.print(string);
        logger.info(string);
      }
    };
  }
  
  /**
   * Print message data as HEX
   * 
   * @param pfxMsg
   * @param data
   * @param numBytes
   * @param logger
   */
  public static void printData(String pfxMsg, byte[] data, int numBytes, Logger logger) {
    logger.info("\n\n-------------->>> "+pfxMsg + " HEX <<<-------------- \n" + ISOUtil.hexdump(data, 0, numBytes)+"\n");
  }

}
