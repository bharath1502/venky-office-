/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Oct 7, 2017
 * @Time: 10:50:11 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_FUNDING_REPORT")
public class PGFundingReport implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "BATCH_ID")
  private String batchId;

  @Column(name = "MERCHANT_CODE")
  private String merchantCode;

  @Column(name = "MERCHANT_NAME")
  private String merchantName;

  @Column(name = "SUB_MERCHANT_CODE")
  private String subMerchantCode;

  @Column(name = "SUB_MERCHANT_NAME")
  private String subMerchantName;

  @Column(name = "CURRENCY")
  private String currency;

  @Column(name = "BANK_ACCOUNT_NUMBER")
  private String bankAccountNumber;

  @Column(name = "BANK_ROUTING_NUMBER")
  private String bankRoutingNumber;

  @Column(name = "FUNDING_AMOUNT")
  private Double fundingAmount;

  @Column(name = "FEE_BILLED_AMOUNT")
  private Double feeBilledAmount;

  @Column(name = "NET_FUNDING_AMOUNT")
  private Double netFundingAmount;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getSubMerchantCode() {
    return subMerchantCode;
  }

  public void setSubMerchantCode(String subMerchantCode) {
    this.subMerchantCode = subMerchantCode;
  }

  public String getSubMerchantName() {
    return subMerchantName;
  }

  public void setSubMerchantName(String subMerchantName) {
    this.subMerchantName = subMerchantName;
  }

  public String getCurrency() {
    return currency;
  }
  
  public String getBankRoutingNumber() {
	    return bankRoutingNumber;
	  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }
  
  public void setBankRoutingNumber(String bankRoutingNumber) {
	    this.bankRoutingNumber = bankRoutingNumber;
	  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }

  public Double getFundingAmount() {
    return fundingAmount;
  }

  public void setFundingAmount(Double fundingAmount) {
    this.fundingAmount = fundingAmount;
  }

  public Double getFeeBilledAmount() {
    return feeBilledAmount;
  }

  public void setFeeBilledAmount(Double feeBilledAmount) {
    this.feeBilledAmount = feeBilledAmount;
  }

  public Double getNetFundingAmount() {
    return netFundingAmount;
  }

  public void setNetFundingAmount(Double netFundingAmount) {
    this.netFundingAmount = netFundingAmount;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

}
