/**
 * 
 */
package com.chatak.pay.exception;

import com.chatak.pay.constants.ChatakPayErrorCode;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jun 6, 2015 2:11:36 PM
 * @version 1.0
 */
public class SplitTransactionException extends Exception{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String errorCode;
  /**
   * 
   */
  public SplitTransactionException() {
    super();
    setErrorCode(ChatakPayErrorCode.TXN_0108.name());
  }

  /**
   * @param arg0
   */
  public SplitTransactionException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public SplitTransactionException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public SplitTransactionException(String arg0, Throwable arg1) {
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
