/**
 * 
 */
package com.chatak.pg.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
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


  /**
   * 
   */
  private static final long serialVersionUID = -8793812026991479051L;

  private String errorCode;

  private String errorMessage;
  
  private String errorMessageExt;
  
  private Long txnDateTime;
  
  private Integer totalRecords;

  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }
  
  public void setTotalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
  }

  /**
   * @param errorCode the errorCode to set
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  
  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }
  
  public Integer getTotalRecords() {
    return totalRecords;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * @return the errorMessageExt
   */
  public String getErrorMessageExt() {
    return errorMessageExt;
  }
  
  /**
   * @return the txnDateTime
   */
  public Long getTxnDateTime() {
    return txnDateTime;
  }

  /**
   * @param errorMessageExt the errorMessageExt to set
   */
  public void setErrorMessageExt(String errorMessageExt) {
    this.errorMessageExt = errorMessageExt;
  }

  /**
   * @param txnDateTime the txnDateTime to set
   */
  public void setTxnDateTime(Long txnDateTime) {
    this.txnDateTime = txnDateTime;
  }

}
