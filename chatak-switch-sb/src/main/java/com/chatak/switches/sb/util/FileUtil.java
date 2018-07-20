/**
 * 
 */
package com.chatak.switches.sb.util;

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.chatak.switches.jpos.SwitchISOPackager;
import com.chatak.switches.jpos.util.JPOSUtil;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 24-Jan-2015 3:04:12 PM
 * @version 1.0
 */
public class FileUtil {
  
  private static Logger logger = Logger.getLogger(FileUtil.class);
  
  private static FileUtil fileUtil = new FileUtil();
  
  private InputStream getFileInputStream(String fileName) {
    try {
      return this.getClass().getClassLoader().getResourceAsStream(fileName);
    } catch (Exception e) {
      logger.error("FileUtil :: getFileInputStream method :", e);
    }
    return null;
  }
  
  /**
   * Method to get Socket Config XML stream
   * 
   * @return
   */
  public static InputStream getConfigFileInputStream() {
    return fileUtil.getFileInputStream(JPOSUtil.CONFIG_PATH);
  }
  
  /**
   * Method to get JPOS Packager XML stream
   * 
   * @return
   */
  public static InputStream getJPOSPackagerFileInputStream() {
    return fileUtil.getFileInputStream(SwitchISOPackager.JPOS_PACKAGER_XML);
  }
  
  /**
   * Method to get JPOS Packager XML stream
   * 
   * @return
   */
  public static InputStream getJPOSChatakPackagerFileInputStream() {
    return fileUtil.getFileInputStream(SwitchISOPackager.JPOS_CHATAK_PACKAGER_XML);
  }
  
  /**
   * Method to get Litle Properties file
   * 
   * @return
   */
  public static InputStream getLitleProperties(String fileName) {
    return FileUtil.class.getClassLoader().getResourceAsStream(fileName);
  }
  
}
