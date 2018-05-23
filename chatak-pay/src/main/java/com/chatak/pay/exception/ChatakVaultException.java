package com.chatak.pay.exception;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Feb 5, 2016 1:48:37 PM
 * @version 1.0
 */
public class ChatakVaultException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7210254744240869643L;
	/**
   * 
   */
  public ChatakVaultException() {
    super();
  }

  /**
   * @param arg0
   */
  public ChatakVaultException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public ChatakVaultException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public ChatakVaultException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
