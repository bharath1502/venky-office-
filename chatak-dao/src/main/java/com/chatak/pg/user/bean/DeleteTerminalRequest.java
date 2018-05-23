package com.chatak.pg.user.bean;

import java.io.Serializable;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 24-Apr-2015 2:27:15 PM
 * @version 1.0
 */
public class DeleteTerminalRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long merchant_code;

  private Long terminal_id;

  public String validate() {
    String message = "";
    if(terminal_id == null) {
      message = "terminal_id is the Required field";
    }
    return message;
  }

  /**
   * @return the merchant_code
   */
  public Long getMerchant_code() {
    return merchant_code;
  }

  /**
   * @param merchant_code
   *          the merchant_code to set
   */
  public void setMerchant_code(Long merchant_code) {
    this.merchant_code = merchant_code;
  }

  /**
   * @return the terminal_id
   */
  public Long getTerminal_id() {
    return terminal_id;
  }

  /**
   * @param terminal_id
   *          the terminal_id to set
   */
  public void setTerminal_id(Long terminal_id) {
    this.terminal_id = terminal_id;
  }

}
