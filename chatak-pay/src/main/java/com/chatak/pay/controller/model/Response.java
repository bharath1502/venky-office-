/**
 * 
 */
package com.chatak.pay.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Model class basically holds all response
 * 
 * @Author: Girmiti Software
 * @Date: 16-Aug-2014
 * @Time: 12:38:06 pm
 * @Version: 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class Response implements Serializable {

  private static final long serialVersionUID = -6537889815349084535L;

  private String errorCode;

  private String errorMessage;
  
  private String errorMessageExt;
  
  private Long txnDateTime;
  
  private String txnRefNumber;

  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  
  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * @param errorCode the errorCode to set
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @return the errorMessageExt
   */
  public String getErrorMessageExt() {
    return errorMessageExt;
  }

  /**
   * @param errorMessageExt the errorMessageExt to set
   */
  public void setErrorMessageExt(String errorMessageExt) {
    this.errorMessageExt = errorMessageExt;
  }
  
  /**
  * @return txnRefNumber
  */
  public String getTxnRefNumber() {
	return txnRefNumber;
  }

  /**
  * @param txnRefNumber
  */
  public void setTxnRefNumber(String txnRefNumber) {
	this.txnRefNumber = txnRefNumber;
  }

  /**
   * @return the txnDateTime
   */
  public Long getTxnDateTime() {
    return txnDateTime;
  }

  /**
   * @param txnDateTime the txnDateTime to set
   */
  public void setTxnDateTime(Long txnDateTime) {
    this.txnDateTime = txnDateTime;
  }
  
}
