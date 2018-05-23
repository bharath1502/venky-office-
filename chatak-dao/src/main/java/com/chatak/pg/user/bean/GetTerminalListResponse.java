package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGTerminal;

public class GetTerminalListResponse extends Response{

	/**
   * 
   */
  private static final long serialVersionUID = -4788897743598690601L;
  private List<PGTerminal> terminalList;
  private Integer noOfRecords;

  /**
   * @return the terminalList
   */
  public List<PGTerminal> getTerminalList() {
    return terminalList;
  }

  /**
   * @param terminalList the terminalList to set
   */
  public void setTerminalList(List<PGTerminal> terminalList) {
    this.terminalList = terminalList;
  }

  /**
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @param noOfRecords the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }
	
}
