package com.chatak.pg.acq.processor;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOValidator;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.validator.ISOVException;

import com.chatak.pg.acq.dao.model.PGActivityLog;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.exception.TransactionException;
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.ISORequestValidator;
import com.chatak.pg.util.JPOSUtil;

/**
 * @Comments:This class would implement the interface FixedMessage and will be
 *                used for holding the unique request associated with a
 *                particular transaction
 */
public class RequestMessage extends PGMessage {

  Logger logger = Logger.getLogger(this.getClass());

  private PGActivityLog gatewayRequest;

  public PGActivityLog getGatewayRequest() {
    return gatewayRequest;
  }

  public RequestMessage() {
  }

  /**
   * This method unpacks the raw data and packages it into a ISOMessage format
   * as specified by the format of the packager that Payment system determines.
   * It also validates the generated ISOMessage
   * 
   * @param String
   *          raw data
   * @return boolean true if valid ISOMessage is generated
   */
  public boolean generateISOMessage(String message) throws ISOException, Exception {
    boolean status = false;
    ISOMsg isoMsg = new ISOMsg();
    GenericPackager packager = JPOSUtil.getGenericPackager();
    try {
      isoMsg.setPackager(packager);
      isoMsg.unpack(message.getBytes());
      JPOSUtil.logISOData(isoMsg, logger);
      logger.debug("RequestMessage | unpackRequest | request message unpack success");

      ISOValidator isoValidator = JPOSUtil.getISOValidator();

      isoMsg = (ISOMsg) isoValidator.validate(isoMsg);
      logger.debug("RequestMessage | unpackRequest | Generic validation success");

      ISORequestValidator.validateBasicISOMsg(isoMsg);
      logger.debug("RequestMessage | unpackRequest | Basic iso fields check success");

      ISORequestValidator.validateMandatoryFields(isoMsg, isoMsg.getString(22));
      logger.debug("RequestMessage | unpackRequest | Mandatory iso fields check success");

      status = true;

      gatewayRequest = new PGActivityLog();
    }
    catch(ISOVException e) {
      String msg = "Unable to generate ISO request.";
      logger.error(msg, e);
      JPOSUtil.logISOData(isoMsg, logger);
      throw new TransactionException(msg, e);
    }
    catch(ISOException e) {
      String msg = "Unable to generate ISO request.";
      logger.error(msg, e);
      JPOSUtil.logISOData(isoMsg, logger);
      throw new TransactionException(msg, e);
    }
    if(status)
      mISOMessage = isoMsg;
    return status;
    // return isoMsg ;
  }

  /**
   * This method unpacks the raw data and packages it into a ISOMessage format
   * as specified by the format of the packager that Payment system determines.
   * It also validates the generated ISOMessage
   * 
   * @param bytes
   *          [] rawBytes
   * @return boolean true if valid ISOMessage is generated
   */
  public boolean generateISOMessage(byte[] rawBytes) throws ISOException, Exception {

    ISOMsg isoMessage = new ISOMsg();
    boolean status = false;
    try {
      GenericPackager packager = JPOSUtil.getGenericPackager();

      isoMessage.setPackager(packager);

      logger.info("Raw Data Length: " + rawBytes.length);
      isoMessage.unpack(rawBytes);

      ISOValidator isoValidator = JPOSUtil.getISOValidator();

      isoMessage = (ISOMsg) isoValidator.validate(isoMessage);
      
      // Now if no error display the message
      JPOSUtil.logISOData(isoMessage, logger);
      
      ISORequestValidator.validateBasicISOMsg(isoMessage);
	  logger.info("RequestMessage | generateISOMessage | Basic iso fields check success");
	  
      //Skip validation for network transaction and void
      /*if(!isoMessage.getMTI().substring(1,4).equals("800") && !isoMessage.getMTI().substring(1,4).equals("400")){
    	  ISORequestValidator.validateMandatoryFields(isoMessage, isoMessage.getString(22));
    	  logger.info("RequestMessage | generateISOMessage | Mandatory iso fields check success");
      }*/
      
      status = true;

      gatewayRequest = new PGActivityLog();

    }
    catch(ISOVException e) {
      String msg = "Unable to generate ISO request.";
      logger.error(msg, e);
      JPOSUtil.logISOData(isoMessage, logger);
    }
    catch(ISOException e) {
      String msg = "Unable to generate ISO request.";
      logger.error(msg, e);
      JPOSUtil.logISOData(isoMessage, logger);
    }

    if(status) {

      mISOMessage = isoMessage;

    }
    else {
      // Format error while unpacking
      try {
        //TODO: Need to check the MTI
        isoMessage.setMTI("0210");
        isoMessage.set(39, ActionCode.ERROR_CODE_96);
        isoMessage.set(44, ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_96));

        mISOMessage = isoMessage;
      }
      catch(ISOException e) {
        JPOSUtil.logISOData(isoMessage, logger);
      }

    }
    return status;
  }

}
