package com.chatak.switches.sb.exception;

import com.chatak.pg.constants.ActionCode;


public class ServiceException extends Exception {

  private static final long serialVersionUID = -7457831330030646865L;
  
  private String errorCode;
  

  /**
   * Default constructor
   */
  public ServiceException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public ServiceException(String message) {
    super(message);
    setErrorCode(ActionCode.ERROR_CODE_Z5);
  }
  
  /**
   * Partial constructor
   * 
   * @param message
   */
  public ServiceException(String code, String message) {
    super(message);
    setErrorCode(code);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public ServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
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