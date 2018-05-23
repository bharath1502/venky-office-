package com.chatak.pg.exception;

public class MagneticStripeParseException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3302146797867540411L;

	/**
	   * Default constructor
	   */
	  public MagneticStripeParseException() {
	    super();
	  }

	  /**
	   * Partial constructor
	   * 
	   * @param message
	   */
	  public MagneticStripeParseException(String message) {
	    super(message);
	  }

	  /**
	   * Partial constructor
	   * 
	   * @param message
	   * @param cause
	   */
	  public MagneticStripeParseException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  /**
	   * Full constructor
	   * 
	   * @param cause
	   */
	  public MagneticStripeParseException(Throwable cause) {
	    super(cause);
	  }


}