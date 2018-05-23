package com.chatak.pg.model;

import java.util.List;

public class BinResponse extends com.chatak.pg.bean.Response {
  
  /**
   * 
   */
  private static final long serialVersionUID = -6377458749631615317L;
  
  private List<BinDTO> bins;
  
  private Integer noOfRecords;
  
  public List<BinDTO> getBins() {
    return bins;
  }

  public void setBins(List<BinDTO> bins) {
    this.bins = bins;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }


}
