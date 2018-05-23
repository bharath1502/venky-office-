package com.chatak.pg.model;

import java.util.List;

public class ValidateAgentDataResponse {

  private List<AgentDTO> agentDTOlist;

  private Long agentAccountNumber;
  
  private String errorMessage;

  private String agentClientId;

  private String agentAni;

  private String errorCode;

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Long getAgentAccountNumber() {
    return agentAccountNumber;
  }
  
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public void setAgentAccountNumber(Long agentAccountNumber) {
    this.agentAccountNumber = agentAccountNumber;
  }

  public String getAgentClientId() {
    return agentClientId;
  }

  public void setAgentClientId(String agentClientId) {
    this.agentClientId = agentClientId;
  }

  public String getAgentAni() {
    return agentAni;
  }

  public void setAgentAni(String agentAni) {
    this.agentAni = agentAni;
  }
  
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public List<AgentDTO> getAgentDTOlist() {
    return agentDTOlist;
  }

  public void setAgentDTOlist(List<AgentDTO> agentDTOlist) {
    this.agentDTOlist = agentDTOlist;
  }

}
