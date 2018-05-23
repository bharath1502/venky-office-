package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

public class CIPartnerDetails implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 7871166913786410521L;
  private String partnerName;
  private String partnerId;
  private String partnerAccNumber;
  
  private List<CIAgentDetails> ciAgentDetails;
  public String getPartnerName() {
    return partnerName;
  }
  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }
  public String getPartnerId() {
    return partnerId;
  }
  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }
  public String getPartnerAccNumber() {
    return partnerAccNumber;
  }
  public void setPartnerAccNumber(String partnerAccNumber) {
    this.partnerAccNumber = partnerAccNumber;
  }
  public List<CIAgentDetails> getCiAgentDetails() {
    return ciAgentDetails;
  }
  public void setCiAgentDetails(List<CIAgentDetails> ciAgentDetails) {
    this.ciAgentDetails = ciAgentDetails;
  }
 
}
