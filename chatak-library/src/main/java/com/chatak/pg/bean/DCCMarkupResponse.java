package com.chatak.pg.bean;

import java.util.List;

public class DCCMarkupResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -5030848796978963706L;

  private List<DCCMarkup> dccmarkUps;

  public List<DCCMarkup> getDccmarkUps() {
    return dccmarkUps;
  }

  public void setDccmarkUps(List<DCCMarkup> dccmarkUps) {
    this.dccmarkUps = dccmarkUps;
  }

}
