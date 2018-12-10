package com.chatak.pg.exception;

public class ChatakSwitchException extends Exception {

  private static final long serialVersionUID = -7457831330030646865L;

  /**
   * Default constructor
   */
  public ChatakSwitchException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public ChatakSwitchException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public ChatakSwitchException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public ChatakSwitchException(String message, Throwable cause) {
    super(message, cause);
  }
}
