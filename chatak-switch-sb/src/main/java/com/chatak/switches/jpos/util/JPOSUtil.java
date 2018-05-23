/**
 * 
 */
package com.chatak.switches.jpos.util;

import java.io.PrintStream;

import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.ISOValidator;
import org.jpos.iso.packager.GenericValidatingPackager;

import com.chatak.switches.sb.util.FileUtil;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 24-Jan-2015 3:03:05 PM
 * @version 1.0
 */
public class JPOSUtil {

  // configuration file path
  public static final String CONFIG_PATH = "iso-packager/config.xml";

  
  private static ISOValidator isoValidator = null;

  private static ISOValidator chatakIsoValidator = null;

  private JPOSUtil() {

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
   * Method to log ISO Data
   * 
   * @param isoMsg
   * @param logger
   */
  public static void logISOData(ISOMsg isoMsg) {
    isoMsg.dump(System.err, " ");
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
    logger.info("\n\n-------------->>> " + pfxMsg + " HEX <<<-------------- \n" + ISOUtil.hexdump(data, 0, numBytes)
                + "\n");
  }

}
