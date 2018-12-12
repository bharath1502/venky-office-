package com.chatak.pg.exception;


public class ConfigException extends Exception {

  private static final long serialVersionUID = -7457831330030646865L;

  /**
   * Default constructor
   */
  public ConfigException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public ConfigException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public ConfigException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public ConfigException(String message, Throwable cause) {
    super(message, cause);
  }
}