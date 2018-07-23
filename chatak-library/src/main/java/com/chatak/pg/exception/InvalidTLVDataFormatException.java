/**
 * 
 */
package com.chatak.pg.exception;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 04-Dec-2014 7:42:03 PM
 * @version 1.0
 */
public class InvalidTLVDataFormatException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 8470751273998123937L;

  /**
   * 
   */
  public InvalidTLVDataFormatException() {
   super("Invalid EMV Data Format Exception");
  }

  /**
   * @param message
   */
  public InvalidTLVDataFormatException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public InvalidTLVDataFormatException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public InvalidTLVDataFormatException(String message, Throwable cause) {
    super(message, cause);
  }

}
