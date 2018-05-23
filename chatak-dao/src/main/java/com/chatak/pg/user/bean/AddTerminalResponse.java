package com.chatak.pg.user.bean;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 24-Apr-2015 2:07:15 PM
 * @version 1.0
 */
public class AddTerminalResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long terminal_id;

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
