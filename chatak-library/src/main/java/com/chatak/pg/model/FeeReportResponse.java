package com.chatak.pg.model;

import java.util.List;

import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.settlement.SettlementEntity;

public class FeeReportResponse extends Response{

  /**
   * 
   */
  private static final long serialVersionUID = -4036588591427000546L;
  
  private List<FeeReportDto> feeReportDto;
  
  private List<SettlementEntity> settlementEntity;

  /**
   * @return the feeReportDto
   */
  public List<FeeReportDto> getFeeReportDto() {
    return feeReportDto;
  }

  /**
   * @param feeReportDto the feeReportDto to set
   */
  public void setFeeReportDto(List<FeeReportDto> feeReportDto) {
    this.feeReportDto = feeReportDto;
  }

  /**
   * @return the settlementEntity
   */
  public List<SettlementEntity> getSettlementEntity() {
    return settlementEntity;
  }

  /**
   * @param settlementEntity the settlementEntity to set
   */
  public void setSettlementEntity(List<SettlementEntity> settlementEntity) {
    this.settlementEntity = settlementEntity;
  }
  
}
