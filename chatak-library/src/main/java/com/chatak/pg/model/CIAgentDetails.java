package com.chatak.pg.model;

import java.io.Serializable;

public class CIAgentDetails implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 2153789265417903958L;
  private String agentName;
  private String agentId;
  private String partnerId;
  private String agentAccNumber;
  private String partnerAccNumber;
  public String getAgentName() {
    return agentName;
  }
  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }
  public String getAgentId() {
    return agentId;
  }
  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }
  public String getPartnerId() {
    return partnerId;
  }
  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }
  public String getAgentAccNumber() {
    return agentAccNumber;
  }
  public void setAgentAccNumber(String agentAccNumber) {
    this.agentAccNumber = agentAccNumber;
  }
  public String getPartnerAccNumber() {
    return partnerAccNumber;
  }
  public void setPartnerAccNumber(String partnerAccNumber) {
    this.partnerAccNumber = partnerAccNumber;
  }
 
}
