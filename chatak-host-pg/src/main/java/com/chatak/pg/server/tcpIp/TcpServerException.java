package com.chatak.pg.server.tcpIp;

public class TcpServerException extends Exception {

  private static final long serialVersionUID = 6395357763938637650L;

  /**
   * Default constructor
   */
  public TcpServerException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public TcpServerException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public TcpServerException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public TcpServerException(String message, Throwable cause) {
    super(message, cause);
  }
}