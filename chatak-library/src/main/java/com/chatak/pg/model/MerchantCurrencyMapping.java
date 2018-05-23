package com.chatak.pg.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class MerchantCurrencyMapping implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 9032617740374332261L;

  private Long pgMerchantCurMapId;
  
  private String merchantCode;
  
  private String currencyCode;
  
  private Integer status;
  
  private Timestamp createdDate;

  private String createdBy;
  
  private Timestamp updatedDate;

  private String updatedBy;

  public Long getPgMerchantCurMapId() {
    return pgMerchantCurMapId;
  }
  
  public String getMerchantCode() {
    return merchantCode;
  }

  public void setPgMerchantCurMapId(Long pgMerchantCurMapId) {
    this.pgMerchantCurMapId = pgMerchantCurMapId;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }
  
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }
  
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public Integer getStatus() {
    return status;
  }
  
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getCreatedBy() {
    return createdBy;
  }
  
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }
  
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

}
