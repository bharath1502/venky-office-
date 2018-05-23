/**
 * 
 */
package com.chatak.acquirer.admin.exception;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 03-Jan-2015 10:58:42 AM
 * @version 1.0
 */
public class ChatakPayException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 7375272986220181341L;
  
  /**
   * @param arg0
   */
  public ChatakPayException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public ChatakPayException(Throwable arg0) {
    super(arg0);
  }
  
  /**
   * 
   */
  public ChatakPayException() {
    super();
  }

  /**
   * @param arg0
   * @param arg1
   */
  public ChatakPayException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
