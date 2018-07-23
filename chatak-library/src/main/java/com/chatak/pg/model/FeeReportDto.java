package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class FeeReportDto extends SearchRequest {

  /**
   * 
   */
  private static final long serialVersionUID = -1362469209326277494L;
  
  private String isoName;
  private Long isoEarnedAmount;
  private Long pmEarnedAmount;
  private Long isoId;
  /**
   * @return the isoName
   */
  public String getIsoName() {
    return isoName;
  }
  /**
   * @param isoName the isoName to set
   */
  public void setIsoName(String isoName) {
    this.isoName = isoName;
  }
  /**
   * @return the isoEarnedAmount
   */
  public Long getIsoEarnedAmount() {
    return isoEarnedAmount;
  }
  /**
   * @param isoEarnedAmount the isoEarnedAmount to set
   */
  public void setIsoEarnedAmount(Long isoEarnedAmount) {
    this.isoEarnedAmount = isoEarnedAmount;
  }
  /**
   * @return the pmEarnedAmount
   */
  public Long getPmEarnedAmount() {
    return pmEarnedAmount;
  }
  /**
   * @param pmEarnedAmount the pmEarnedAmount to set
   */
  public void setPmEarnedAmount(Long pmEarnedAmount) {
    this.pmEarnedAmount = pmEarnedAmount;
  }
  /**
   * @return the isoId
   */
  public Long getIsoId() {
    return isoId;
  }
  /**
   * @param isoId the isoId to set
   */
  public void setIsoId(Long isoId) {
    this.isoId = isoId;
  }

}
