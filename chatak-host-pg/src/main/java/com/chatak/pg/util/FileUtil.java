/**
 * 
 */
package com.chatak.pg.util;

import java.io.InputStream;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 15-Jan-2015 12:12:59 AM
 * @version 1.0
 */
public final class FileUtil {
  
  static FileUtil fileUtil = new FileUtil();
  
  // configuration file path
  private static final String CONFIG_PATH = "config/config.xml";
  private static final String JPOS_PACKAGER_XML = "config/iso93binary.xml";
  private static final String JPOS_CHATAK_PACKAGER_XML = "config/isoChatak93ascii.xml";

  private InputStream getFileInputStream(String fileName) {
    try {
      return this.getClass().getClassLoader().getResourceAsStream(fileName);
    } catch (Exception e) {
      // TODO: handle exception
    }
    return null;
  }
  
  /**
   * Method to get Socket Config XML stream
   * 
   * @return
   */
  public static InputStream getConfigFileInputStream() {
    return fileUtil.getFileInputStream(CONFIG_PATH);
  }
  
  /**
   * Method to get JPOS Packager XML stream
   * 
   * @return
   */
  public static InputStream getJPOSPackagerFileInputStream() {
    return fileUtil.getFileInputStream(JPOS_PACKAGER_XML);
  }
  
  /**
   * Method to get JPOS Packager XML stream
   * 
   * @return
   */
  public static InputStream getJPOSChatakPackagerFileInputStream() {
    return fileUtil.getFileInputStream(JPOS_CHATAK_PACKAGER_XML);
  }
  
}
