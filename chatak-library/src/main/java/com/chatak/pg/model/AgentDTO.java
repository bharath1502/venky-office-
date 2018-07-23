package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class AgentDTO extends SearchRequest {

  private static final long serialVersionUID = 6208126797332453017L;

  private Long agentId;

  private String agentName;

  /**
   * @return the agentId
   */
  public Long getAgentId() {
    return agentId;
  }

  /**
   * @param agentId
   *          the agentId to set
   */
  public void setAgentId(Long agentId) {
    this.agentId = agentId;
  }

  /**
   * @return the agentName
   */
  public String getAgentName() {
    return agentName;
  }

  /**
   * @param agentName
   *          the agentName to set
   */
  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

}
