package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class SettlementActionDTOList implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 3316113884563639021L;
  private List< SettlemetActionDTO> actionDTOs;

  public List<SettlemetActionDTO> getActionDTOs() {
    return actionDTOs;
  }

  public void setActionDTOs(List<SettlemetActionDTO> actionDTOs) {
    this.actionDTOs = actionDTOs;
  }
  
  

}
