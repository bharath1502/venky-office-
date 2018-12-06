package com.chatak.pg.user.bean;

import java.util.List;

public class ProgramManagerAccountResponse extends com.chatak.pg.bean.Response {

  /**
   * 
   */
  private static final long serialVersionUID = 5325017663005582851L;

  private List<ProgramManagerAccountRequest> programManagerAccountRequestList;

  /**
   * @return the programManagerAccountRequestList
   */
  public List<ProgramManagerAccountRequest> getProgramManagerAccountRequestList() {
    return programManagerAccountRequestList;
  }

  /**
   * @param programManagerAccountRequestList
   *          the programManagerAccountRequestList to set
   */
  public void setProgramManagerAccountRequestList(
      List<ProgramManagerAccountRequest> programManagerAccountRequestList) {
    this.programManagerAccountRequestList = programManagerAccountRequestList;
  }
}
