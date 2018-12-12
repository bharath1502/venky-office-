package com.chatak.pg.server.validators;

import org.apache.log4j.Logger;

import com.chatak.pg.acq.processor.TxnAuthorizer;
import com.chatak.pg.common.MessageTypeCode;

/**
 * @Version : 1.0 This class has the Action Code associated with the
 *          transactions. This would basically be the Fixed alphanumeric Value
 *          to be set in Field 39
 */
public class GeneralValidation {

	private Logger logger = Logger.getLogger(GeneralValidation.class);;

	private String mti;
	private String procCode;

	/**
	 * DEFAULT CONSTRUCTOR
	 */
	public GeneralValidation(TxnAuthorizer txnAuth, String mti, String procCode) {
		this.mti = mti;
		this.procCode = procCode;
	}

	/**
	 * This message validates the Request
	 * 
	 * @return boolean
	 */
	public boolean validateRequestCodes() {
		logger.debug("GeneralValidation | validateRequestCodes | Entering");
		
		boolean validated = false;
		
		if(mti.equalsIgnoreCase(MessageTypeCode.AUTHORIZATION_REQUEST)) {//0100
			
			if(procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_PURCHASE) || procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_BALANCE_ENQUIRY)) {//000000
				return true;
			} else {
				logger.info("MTI " + mti + " does not support PROC CODE " + procCode);
				return false;
			}
			
		} else if (mti.equalsIgnoreCase(MessageTypeCode.OFFLINE_REQUEST)) {// 0220

			if (procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_CAPTURE)
					|| procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_VOID)
					|| procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_TIP_ADJUSTMENT)
					|| procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_REFUND_ADJUSTMENT)
					|| procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_REFUND)) {
				return true;
			} else {
				logger.info("MTI " + mti + " does not support PROC CODE "
						+ procCode);
				return false;
			}

		} else if(mti.equalsIgnoreCase(MessageTypeCode.ONLINE_REQUEST)) {//0200

			if(procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_PURCHASE)) {//000000
				return true;
			} else if(procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_VOID)){
				return true;
			} else if(procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_REFUND)){
				return true;
			} else if(procCode.equalsIgnoreCase(MessageTypeCode.PROC_CODE_REFUND_ADJUSTMENT)){
				return true;
			} else {
				logger.info("MTI " + mti + " does not support PROC CODE " + procCode);
				return false;
			}

		} else if(mti.equalsIgnoreCase(MessageTypeCode.REVERSAL_REQUEST)) {//0400
			return true;
		} else if(mti.equalsIgnoreCase(MessageTypeCode.RECONCILATION_REQUEST)) {//0500
			return true;
		} else if(mti.equalsIgnoreCase(MessageTypeCode.NETWORK_REQUEST)) {//0800
			return true;
		} else {

			logger.info("MTI " + mti + " IS NOT SUPPORTED");
			validated = false;
		}
		
		logger.debug("GeneralValidation | validateRequestCodes | Entering");
		
		return validated;
	}
}