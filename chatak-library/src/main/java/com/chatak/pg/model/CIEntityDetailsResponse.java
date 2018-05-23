package com.chatak.pg.model;

import java.util.List;

public class CIEntityDetailsResponse  extends Response {
  
  /**
   * 
   */
  private static final long serialVersionUID = -9025414007135299949L;
  
  private List<CIProgramManagerDetails> ciProgramManagerDetails;

  /**
   * @return the ciProgramManagerDetails
   */
  public List<CIProgramManagerDetails> getCiProgramManagerDetails() {
    return ciProgramManagerDetails;
  }
  
  /**
   * @param ciProgramManagerDetails the ciProgramManagerDetails to set
   */
  public void setCiProgramManagerDetails(
      List<CIProgramManagerDetails> ciProgramManagerDetails) {
    this.ciProgramManagerDetails = ciProgramManagerDetails;
  }
  
}
