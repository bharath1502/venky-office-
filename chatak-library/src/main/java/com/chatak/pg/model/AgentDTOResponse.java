package com.chatak.pg.model;

import java.util.List;

public class AgentDTOResponse extends Response {

  /**
	 * 
	 */
  private static final long serialVersionUID = -4732756091253944044L;

  private List<AgentDTO> agentDTOlist;

  /**
   * @return the agentDTOlist
   */
  public List<AgentDTO> getAgentDTOlist() {
    return agentDTOlist;
  }

  /**
   * @param agentDTOlist
   *          the agentDTOlist to set
   */
  public void setAgentDTOlist(List<AgentDTO> agentDTOlist) {
    this.agentDTOlist = agentDTOlist;
  }

}
