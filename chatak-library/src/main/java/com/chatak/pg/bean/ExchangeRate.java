package com.chatak.pg.bean;

import java.sql.Timestamp;

public class ExchangeRate extends SearchRequest{

  private static final long serialVersionUID = -1468223768123135970L;
  
  private Long id;
  
  private String sourceCurrency;
  
  private String destCurrency;
  
  private Double exchRate;
  
  private Long souCurDecPos;
  
  private Long destCurDecPos;
  
  private Timestamp createdDate;
  
  private Timestamp updatedDate;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getSourceCurrency() {
    return sourceCurrency;
  }

  public void setSourceCurrency(String sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
  }

  public String getDestCurrency() {
    return destCurrency;
  }

  public void setDestCurrency(String destCurrency) {
    this.destCurrency = destCurrency;
  }

  public Double getExchRate() {
    return exchRate;
  }

  public void setExchRate(Double exchRate) {
    this.exchRate = exchRate;
  }

  public Long getSouCurDecPos() {
    return souCurDecPos;
  }

  public void setSouCurDecPos(Long souCurDecPos) {
    this.souCurDecPos = souCurDecPos;
  }

  public Long getDestCurDecPos() {
    return destCurDecPos;
  }

  public void setDestCurDecPos(Long destCurDecPos) {
    this.destCurDecPos = destCurDecPos;
  }

  @Override
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  @Override
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  @Override
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

}
