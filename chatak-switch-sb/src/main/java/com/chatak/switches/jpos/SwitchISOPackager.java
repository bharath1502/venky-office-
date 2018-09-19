/**
 * 
 */
package com.chatak.switches.jpos;

import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import com.chatak.pg.util.StringUtils;
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
	
	private static Logger logger = LogManager.getLogger(SwitchISOPackager.class);

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
  
  // ADDED for REST
  public static ISOMsg unpackRequest(byte[] input){
		ISOMsg isoMsg = new ISOMsg();
		try {
			isoMsg.setPackager(chatakPackager);
			isoMsg.unpack(input);
			logISOData(isoMsg, logger);
		} catch (ISOException e) {
			logger.error("ISOException -  during Packing ISO Message", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception -  during Packing ISO Message", e.getMessage());
		}
		return isoMsg;
	}
  
  public static void logISOData(ISOMsg originalISOMsg, Logger logger) {
		ISOMsg logISOMsg = (ISOMsg) originalISOMsg.clone();
		String last4Digits = "";
		try{
			String track2 = (String) logISOMsg.getValue(35);
			if(StringUtils.isValidString(track2) ) {
//				TrackUtil magneticStripeCardUtil = new TrackUtil();
//				magneticStripeCardUtil._parseTrack2(track2);
//				last4Digits = magneticStripeCardUtil.getPan().substring(12);
//				logISOMsg.set(35, last4Digits);
			}else{
				if(StringUtils.isValidString((String) logISOMsg.getValue(2))){
					last4Digits = ((String) logISOMsg.getValue(2)).substring(12);
					logISOMsg.set(2, last4Digits);
				}
			}
			String pinData_DE52 = (String) logISOMsg.getValue(52);
			if(StringUtils.isValidString(pinData_DE52)){
				logISOMsg.set(52,"xxxx");
			}
		}catch (Exception e) {
			logger.error("Exception | get last 4 digits |", e);
		}
		
		logISOMsg.dump(createLoggingProxy((PrintStream) logger, logger), " ");
	}
  
  public static PrintStream createLoggingProxy(final PrintStream realPrintStream, final Logger logger) {
		return new PrintStream(realPrintStream) {
			StringBuilder printerMsg = new StringBuilder();
			@Override
			public void print(final String string) {
				logger.info(string);
			}
		};
	}
  // END

}
