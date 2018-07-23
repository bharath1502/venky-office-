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
public class ChatakAdminException extends Exception {

	
	  private static final long serialVersionUID = 7375272986220181341L;
	  
	  
	private String errorCode;
	private String errorMessage;

	public ChatakAdminException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
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
   * 
   */
  public ChatakAdminException() {
    super();
  }

  /**
   * @param arg0
   */
  public ChatakAdminException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public ChatakAdminException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public ChatakAdminException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
