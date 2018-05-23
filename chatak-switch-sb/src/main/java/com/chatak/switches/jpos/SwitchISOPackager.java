/**
 * 
 */
package com.chatak.switches.jpos;

import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

import com.chatak.switches.sb.util.FileUtil;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 24-Jan-2015 3:01:34 PM
 * @version 1.0
 */
public final class SwitchISOPackager {

  public static final String JPOS_PACKAGER_XML = "iso-packager/iso93binary.xml";

  public static final String JPOS_CHATAK_PACKAGER_XML = "iso-packager/isoChatak93ascii.xml";

  private static GenericPackager packager = null;
  
  private static GenericPackager chatakPackager = null;
  
  private SwitchISOPackager() {
    
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

}
