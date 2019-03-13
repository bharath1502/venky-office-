package com.chatak.pg.acq.processor;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.chatak.pg.bean.ISOInRequest;
import com.chatak.pg.common.CurrencyFormatter;
import com.chatak.pg.constants.ActionCode;

/**
 * @Comments : This class super class for all processor
 */
public abstract class Processor {

  protected TxnAuthorizer _txnAuthorizer;
  
  protected ISOInRequest _ISOInputRequest;

  protected int _auditLogonID;

  protected Logger logger = Logger.getLogger(Processor.class);

  /**
   * CONSTRUCTOR with NO parameter
   */
  protected Processor() {
  }

  /**
   * Processor constructor with Request as an param. Construct Processor object
   * for given Request object.
   * 
   * @param txnReq
   */
  protected Processor(TxnAuthorizer txnAuth) {
    _txnAuthorizer = txnAuth;
    _ISOInputRequest = txnAuth.get_ISOInputRequest();
  }

  
  /**
   * This method used for set response fields
   * 
   * @param actionCode
   */
  public void setResponseFields(String actionCode) {

    // Update the field 44 for Message to be printed on POS
    String msg = ActionCode.getInstance().getMessage(actionCode);
    
    _txnAuthorizer.set_actionCode(actionCode);
    _txnAuthorizer.set_autoMessage(msg);

  }

  /**
   * This method used for set formatted response fields
   * 
   * @param fieldId
   * @param fieldData
   */
  protected void setFormattedResponseField(int fieldId, String fieldData) {
    if(fieldData != null && fieldData.length() > 0) {
      // not need to reset field value, if field already has been set
      if(_txnAuthorizer.get_txnHandler().getResponseMessage().getFieldValue(fieldId) == null) {
        logger.debug("Set Response Field(" + fieldId + "): " + fieldData);
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(fieldId, fieldData);
      }
    }
  }

  /**
   * This method used for set formatted response fields
   * 
   * @param fieldId
   * @param fieldData
   * @param flag
   */
  protected void setFormattedResponseField(int fieldId, String fieldData, boolean flag) {
    logger.debug("(0) Set Response Field(" + fieldId + "): " + fieldData);
    _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(fieldId, fieldData);
  }

  /**
   * This method set used for formatted currency response fields
   * 
   * @param fieldId
   * @param amount
   */
  protected void setFormattedCurrencyResponseField(int fieldId, Double amount) {
    CurrencyFormatter cf = new CurrencyFormatter();
    String balance = cf.convertTo8583FormattedAmount(amount);
    logger.debug("(0) Set Response Field(" + fieldId + "): " + balance);
    _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(fieldId, balance);
  }

  /**
   * @return
   */
  protected RequestMessage getRequestMessage() {
    return _txnAuthorizer.get_requestMessage();
  }

  /**
   * This method format the amount
   * 
   * @param amount
   * @return String
   */
  protected String formatAmount(Float amount) {
    DecimalFormat formatter = new DecimalFormat("0.00");
    return formatter.format(amount);

  }

  /**
   * This method format the phone
   * 
   * @param phone
   * @return String
   */
  protected String formatPhone(String phone) {
    if(phone != null && phone.length() == 10) {
      return phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6, 10);
    }
    else {
      return phone;
    }
  }
}