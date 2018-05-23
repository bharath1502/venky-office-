package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.Request;

public class ValidateAgentDataRequest extends Request implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long agentAccountNumber;

  private String agentClientId;

  private String agentAni;

  private String currencyCodeNumeric;

  private Long agentId;

  private String agentName;

  public Long getAgentAccountNumber() {
    return agentAccountNumber;
  }

  public String getAgentAni() {
    return agentAni;
  }

  public void setAgentAni(String agentAni) {
    this.agentAni = agentAni;
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

  public String getCurrencyCodeNumeric() {
    return currencyCodeNumeric;
  }

  public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
    this.currencyCodeNumeric = currencyCodeNumeric;
  }

  public Long getAgentId() {
    return agentId;
  }

  public void setAgentId(Long agentId) {
    this.agentId = agentId;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

}
