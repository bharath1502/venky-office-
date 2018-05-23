package com.chatak.pg.exception;

public class InvalidMerchantException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3302146797867540411L;

	/**
	   * Default constructor
	   */
	  public InvalidMerchantException() {
	    super();
	  }

	  /**
	   * Partial constructor
	   * 
	   * @param message
	   */
	  public InvalidMerchantException(String message) {
	    super(message);
	  }

	  /**
	   * Partial constructor
	   * 
	   * @param message
	   * @param cause
	   */
	  public InvalidMerchantException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  /**
	   * Full constructor
	   * 
	   * @param cause
	   */
	  public InvalidMerchantException(Throwable cause) {
	    super(cause);
	  }


}