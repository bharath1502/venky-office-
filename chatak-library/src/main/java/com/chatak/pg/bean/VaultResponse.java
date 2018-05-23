package com.chatak.pg.bean;

import java.io.Serializable;

public class VaultResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6694010051623795125L;
  
  private String errorCode;
  
  private String errorMessage;

  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }
  
  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @param errorCode the errorCode to set
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  
  

}
