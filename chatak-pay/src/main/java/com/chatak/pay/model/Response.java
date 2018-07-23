/**
 * 
 */
package com.chatak.pay.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Model class basically holds all response
 * 
 * @Author: Girmiti Software
 * @Date: 16-Aug-2014
 * @Time: 12:38:06 pm
 * @Version: 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

  private static final long serialVersionUID = -6537889815349084535L;

  private String errorCode;

  private Integer totalNoOfRows;
  
  private String errorMessage;

  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * @param errorMessage
   *          the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * @return the totalNoOfRows
   */
  public Integer getTotalNoOfRows() {
    return totalNoOfRows;
  }
  
  /**
   * @param errorCode
   *          the errorCode to set
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

  /**
   * @param totalNoOfRows the totalNoOfRows to set
   */
  public void setTotalNoOfRows(Integer totalNoOfRows) {
    this.totalNoOfRows = totalNoOfRows;
  }
  
  

}
