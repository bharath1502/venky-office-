package com.chatak.pg.model;

import java.util.List;

import com.chatak.pg.bean.SearchRequest;

public class AdvancedFraudRequest extends SearchRequest{

  private static final long serialVersionUID = 8115410433696522209L;
  
  private List<AdvancedFraudDTO> advancedFraudDTOs;

  /**
   * @return the advancedFraudDTOs
   */
  public List<AdvancedFraudDTO> getAdvancedFraudDTOs() {
    return advancedFraudDTOs;
  }

  /**
   * @param advancedFraudDTOs the advancedFraudDTOs to set
   */
  public void setAdvancedFraudDTOs(List<AdvancedFraudDTO> advancedFraudDTOs) {
    this.advancedFraudDTOs = advancedFraudDTOs;
  }

}
