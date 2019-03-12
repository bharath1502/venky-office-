package com.chatak.pg.acq.processor;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOValidator;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.validator.ISOVException;

import com.chatak.pg.acq.dao.model.PGActivityLog;
import com.chatak.pg.exception.TransactionException;
import com.chatak.pg.util.ByteConversionUtils;
import com.chatak.pg.util.JPOSUtil;

/**
 * @Comments:This class would implement the interface FixedMessage and will be
 *                used for holding the unique response associated with a
 *                particular transaction
 */
public class ResponseMessage extends PGMessage {

  private PGActivityLog gatewayResponse;

  public PGActivityLog getGatewayResponse() {
    return gatewayResponse;
  }

  /**
   * Default constructor
   */

  public ResponseMessage() {
    mISOMessage = new ISOMsg();
  }

  /**
   * This method gets and reformats the Transaction Response from Card
   * Authorization Service. The Transaction Response has been mostly populated
   * during the authorization. First pack the ISOMessage formatted data into
   * byte[], process opposite of generateISOMessage(byte[] rawBytes). Then
   * format the enitre data into the acceptable format. OUR PROTOCOL: <STX(1)>
   * <LOD(2)> <??'L'(1)??> <DATA(LOD)> <ETX(1)> <LRC(1)> where LOD =
   * LengthOfData
   * 
   * @return byte[] formattedResponseBuffer
   */
  public byte[] generateFormattedResponse() throws TransactionException {
    try {
      // set the time field .This must go with all reponses.
      GenericPackager p2 = JPOSUtil.getGenericPackager();
      mISOMessage.setPackager(p2);
      byte[] isoResponseByte = mISOMessage.pack();
      logger.info("Response bytes length:"+isoResponseByte.length);
      JPOSUtil.logISOData(mISOMessage, logger);

      logger.debug(ByteConversionUtils.byteArrayToHexString(isoResponseByte, isoResponseByte.length));
      return isoResponseByte;
    }
    catch(ISOException e) {
      String msg = "Unable to generate formatted response.";
      logger.error(msg, e);
      JPOSUtil.logISOData(mISOMessage, logger);
      throw new TransactionException(msg, e);
    }
    catch(Exception e) {
      String msg = "Unable to generate formatted response.";
      logger.error(msg, e);
      JPOSUtil.logISOData(mISOMessage, logger);
      throw new TransactionException(msg, e);
    }
  }

  /**
   * This method unpacks the raw data and packages it into a ISOMessage format
   * as specified by the format of the packager. It
   * also validates the generated ISOMessage
   * 
   * @param bytes
   *          [] rawBytes
   * @return boolean true if valid ISOMessage is generated
   */
  public boolean generateISOMessage(byte[] rawBytes) {
    ISOMsg isoMessage = new ISOMsg();
    boolean status = false;
    try {
      GenericPackager p2 = JPOSUtil.getGenericPackager();
      isoMessage.setPackager(p2);
      isoMessage.unpack(rawBytes);
      ISOValidator isoValidator = JPOSUtil.getISOValidator();
      isoMessage = (ISOMsg) isoValidator.validate(isoMessage);
      status = true;
    }
    catch(ISOVException e) {
      logger.error("ISOVException " + e.getMessage());
    }
    catch(ISOException e) {
      logger.error("ISOException " + e.getLocalizedMessage());
      JPOSUtil.logISOData(mISOMessage, logger);
    }
    if(status)
      mISOMessage = isoMessage;
    return status;
  }


}