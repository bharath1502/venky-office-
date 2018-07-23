/**
 * 
 */
package com.chatak.pay.exception;

import com.chatak.pay.constants.ChatakPayErrorCode;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date May 19, 2015 3:56:35 PM
 * @version 1.0
 */
public class InvalidRequestException extends Exception {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String errorCode;

  public InvalidRequestException() {
    super();
  }

  /**
   * @param arg0
   */
  public InvalidRequestException(String arg0) {
    super(arg0);
    setErrorCode(ChatakPayErrorCode.TXN_0993.name());
  }
  
  /**
   * @param code
   * @param msg
   */
  public InvalidRequestException(String code, String msg) {
    super(msg);
    setErrorCode(code);
  }

  /**
   * @param arg0
   */
  public InvalidRequestException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public InvalidRequestException(String arg0, Throwable arg1) {
    super(arg0, arg1);
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

}
