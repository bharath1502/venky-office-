package com.chatak.pg.exception;

import com.chatak.pg.util.ISOResponseCodes;

/**
 *
 * The exception class handle and catch the validation common
 * errors
 *
 * @author Girmiti Software
 * @date 19-Dec-2014 5:31:18 pm
 * @version 1.0
 */
public class ValidationException extends Exception {
	private static final long serialVersionUID = -907558219319972542L;
	
	private ISOResponseCodes responseCode;

	/**
	 * Default constructor for TransactionException
	 */
	public ValidationException() {
		super();
	}

	/**
	 * Partial constructor
	 * 
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}


	/**
	 * Constructor to throw ISOResponse codes
	 * @param responseCode
	 * @param message
	 */
	public ValidationException(ISOResponseCodes responseCode) {
		super(responseCode.toString());
		this.responseCode = responseCode;
	}

	/**
	 * Partial constructor
	 * 
	 * @param cause
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Full constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
	

	/**
	 * @return the responseCode
	 */
	public ISOResponseCodes getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(ISOResponseCodes responseCode) {
		this.responseCode = responseCode;
	}
}