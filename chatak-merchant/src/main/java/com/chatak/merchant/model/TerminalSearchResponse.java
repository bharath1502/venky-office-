package com.chatak.merchant.model;

import java.util.List;

public class TerminalSearchResponse extends Response{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private List<Terminals> terminalList;

  /**
   * @return the terminalList
   */
  public List<Terminals> getTerminalList() {
    return terminalList;
  }

  /**
   * @param terminalList the terminalList to set
   */
  public void setTerminalList(List<Terminals> terminalList) {
    this.terminalList = terminalList;
  }
  
  

}
