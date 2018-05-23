package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

public class VirtualAccGetPartnersList implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -7500830948205914944L;
  private List<CIPartnerDetails> partnerDetailList;

  public List<CIPartnerDetails> getPartnerDetailList() {
    return partnerDetailList;
  }

  public void setPartnerDetailList(List<CIPartnerDetails> partnerDetailList) {
    this.partnerDetailList = partnerDetailList;
  }
  
}
