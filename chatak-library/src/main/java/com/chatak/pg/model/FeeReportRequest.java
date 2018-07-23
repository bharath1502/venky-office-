package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

import com.chatak.pg.bean.SearchRequest;

public class FeeReportRequest extends SearchRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3231059310196066233L;
  
  private String programManagerId;
  
  private String fromDate;
  
  private String toDate;
  
  private List<String> transactionCodeList;
  
  private Long isoId;
  
  private String pgTxnIds;
  
  private Long issuanceSettlementEntityId;
  
  private String isoName;

  public String getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(String programManagerId) {
    this.programManagerId = programManagerId;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public List<String> getTransactionCodeList() {
    return transactionCodeList;
  }

  public void setTransactionCodeList(List<String> transactionCodeList) {
    this.transactionCodeList = transactionCodeList;
  }

  public Long getIsoId() {
    return isoId;
  }

  public void setIsoId(Long isoId) {
    this.isoId = isoId;
  }

  public String getPgTxnIds() {
    return pgTxnIds;
  }

  public void setPgTxnIds(String pgTxnIds) {
    this.pgTxnIds = pgTxnIds;
  }

  public Long getIssuanceSettlementEntityId() {
    return issuanceSettlementEntityId;
  }

  public void setIssuanceSettlementEntityId(Long issuanceSettlementEntityId) {
    this.issuanceSettlementEntityId = issuanceSettlementEntityId;
  }

  public String getIsoName() {
    return isoName;
  }

  public void setIsoName(String isoName) {
    this.isoName = isoName;
  }

}
