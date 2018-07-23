package com.chatak.pg.model;

import java.util.List;

public class PartnerFeeCodeResponseDetails extends Response {


  /**
   * 
   */
  private static final long serialVersionUID = -1233410393648955521L;

  private List<PartnerFeeCodeDTO> partnerFeeCodeList;
  
  private Integer noOfRecords;

  public List<PartnerFeeCodeDTO> getPartnerFeeCodeList() {
    return partnerFeeCodeList;
  }

  public void setPartnerFeeCodeList(List<PartnerFeeCodeDTO> partnerFeeCodeList) {
    this.partnerFeeCodeList = partnerFeeCodeList;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

}
