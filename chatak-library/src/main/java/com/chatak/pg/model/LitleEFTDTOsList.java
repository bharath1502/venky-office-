package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class LitleEFTDTOsList implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4969992639074294959L;
  
  private List<LitleEFTDTO> litleEFTDTOs;

  public List<LitleEFTDTO> getLitleEFTDTOs() {
    return litleEFTDTOs;
  }

  public void setLitleEFTDTOs(List<LitleEFTDTO> litleEFTDTOs) {
    this.litleEFTDTOs = litleEFTDTOs;
  }
}
