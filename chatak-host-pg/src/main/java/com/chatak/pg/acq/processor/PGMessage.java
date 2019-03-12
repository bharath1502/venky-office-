package com.chatak.pg.acq.processor;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.chatak.pg.common.MessageTypeCode;

/**
 * @Comments:This class shell be the super class for the RequestMessage and the
 *                ResponseMessage classes that will be used for holding the
 *                respective request and response associated with a particular
 *                transaction.
 */
public abstract class PGMessage {

	protected ISOMsg mISOMessage;

	protected Logger logger = Logger.getLogger(PGMessage.class);

	/**
	 * Default ViVOMessage constructor
	 */
	public PGMessage() {
		super();
	}

	/**
	 * This method is used to set the FieldValues We might not actually need to do
	 * this for the RequestMessage since the generated ISOMsg will set the field
	 * values ) ???
	 * 
	 * @param fldno
	 *          int The field number
	 * @param value
	 *          String The value associated with that filed number
	 * @return void
	 */
	public void setFieldValue(int fldno, String value) {
		try {
			if(fldno == 0) {

				if(value.equalsIgnoreCase(MessageTypeCode.AUTHORIZATION_REQUEST)){
					mISOMessage.setMTI(MessageTypeCode.AUTHORIZATION_RESPONSE);
				} else if(value.equalsIgnoreCase(MessageTypeCode.OFFLINE_REQUEST)){
					mISOMessage.setMTI(MessageTypeCode.OFFLINE_RESPONSE);
				} else if(value.equalsIgnoreCase(MessageTypeCode.ONLINE_REQUEST)){
					mISOMessage.setMTI(MessageTypeCode.ONLINE_RESPONSE);
				} else if(value.equalsIgnoreCase(MessageTypeCode.REVERSAL_REQUEST)){
					mISOMessage.setMTI(MessageTypeCode.REVERSAL_RESPONSE);
				} else if(value.equalsIgnoreCase(MessageTypeCode.NETWORK_REQUEST)){
					mISOMessage.setMTI(MessageTypeCode.NETWORK_RESPONSE);
				} else if(value.equalsIgnoreCase(MessageTypeCode.RECONCILATION_REQUEST)){
					mISOMessage.setMTI(MessageTypeCode.RECONCILATION_RESPONSE);
				} else if(value.equalsIgnoreCase(MessageTypeCode.AUTHORIZATION_REQUEST_2003)){
					mISOMessage.setMTI(MessageTypeCode.AUTHORIZATION_RESPONSE_2003);
				} else if(value.equalsIgnoreCase(MessageTypeCode.ADVICE_REQUEST_2003)){
					mISOMessage.setMTI(MessageTypeCode.ADVICE_RESPONSE_2003);
				} else if(value.equalsIgnoreCase(MessageTypeCode.AUTH_CAPTURE_REQUEST_2003)){
					mISOMessage.setMTI(MessageTypeCode.AUTH_CAPTURE_RESPONSE_2003);
				} else if(value.equalsIgnoreCase(MessageTypeCode.REVERSAL_REQUEST_2003)){
					mISOMessage.setMTI(MessageTypeCode.REVERSAL_RESPONSE_2003);
				} else if(value.equalsIgnoreCase(MessageTypeCode.RECONCILATION_REQUEST_2003)){
					mISOMessage.setMTI(MessageTypeCode.RECONCILATION_RESPONSE_2003);
				}

			} else {
				mISOMessage.set(fldno, value);
			}

		}
		catch(ISOException e) {
			logger.error("ISOException in RequestMessage " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to set the FieldValues We might not actually need to do
	 * this for the RequestMessage since the generated ISOMsg will set the field
	 * values ) ???
	 * 
	 * @param fldno
	 *          int The field number
	 * @param value
	 *          String The value associated with that filed number
	 * @return void
	 */
	public void setFieldValue(int fldno, byte[] value) {
		try {
				mISOMessage.set(fldno, value);
		}
		catch(Exception e) {
			logger.error("ISOException in RequestMessage " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public void setUnprocessedFieldValue(int fldno, String value) {
		try {
			if(fldno == 0) {
				mISOMessage.setMTI(value);
			}
			else
				mISOMessage.set(fldno, value);
		}
		catch(ISOException e) {
			logger.error("ISOException in RequestMessage " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to get the FieldValues given the resp Field number (We
	 * might not actually need to do this for the RequestMessage since the
	 * generated ISOMsg will give us the field values ) ???
	 * 
	 * @param int The field number
	 * @return String The value associated with that filed number
	 */
	public String getFieldValue(int fldno) {
		String fieldValue = null; 
		try {
			if(mISOMessage != null) {
				Object obj = mISOMessage.getValue(fldno);
				if(obj != null)
					fieldValue = (String) obj;
			}
		}
		catch(Exception e) {
			String msg = "ISOException - Unable to get value field: " + fldno + ", ";
			logger.error(msg + e.getMessage());
		}
				return fieldValue;
	}
	
	/**
	 * This method is used to get the FieldValues given the resp Field number (We
	 * might not actually need to do this for the RequestMessage since the
	 * generated ISOMsg will give us the field values ) ???
	 * 
	 * @param int The field number
	 * @return String The value associated with that filed number
	 */
	public byte[] getFieldByteValue(int fldno) {
		byte[] fieldValue = null; 
		try {
			if(mISOMessage != null) {
				Object obj = mISOMessage.getValue(fldno);
				if(obj != null)
					fieldValue = (byte[]) obj;
			}
		}
		
		catch(Exception e) {
			String msg = "Exception - Unable to get value field: " + fldno + ", ";
			logger.error(msg + e.getMessage());
		}
		return fieldValue;
	}

	/**
	 * This method gets the ISOMessage variable
	 */
	public ISOMsg getISOMessage() {
		if(mISOMessage == null)
			logger.error("ISO Message NOT CREATED");
		return mISOMessage;
	}
}