package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

public class VirtualAccGetAgentsResponse extends Response implements Serializable{
/**
   * 
   */
  private static final long serialVersionUID = -221802752822929907L;
private List<CIAgentDetails> agentDetailsList;

public List<CIAgentDetails> getAgentList() {
  return agentDetailsList;
}
public void setAgentList(List<CIAgentDetails> agentDetailsList) {
  this.agentDetailsList = agentDetailsList;
}
}
