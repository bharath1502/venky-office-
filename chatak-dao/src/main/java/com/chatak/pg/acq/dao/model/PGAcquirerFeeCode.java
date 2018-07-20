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
 * @Date: Aug 8, 2015
 * @Time: 3:15:50 PM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_ACQUIRER_FEE_CODE")
public class PGAcquirerFeeCode implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_ACQUIRER_FEE_CODE_ID", sequenceName = "SEQ_PG_ACQUIRER_FEE_CODE")
  @GeneratedValue(generator = "SEQ_PG_ACQUIRER_FEE_CODE_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ACQUIRER_FEE_CODE_ID")
  private Long acquirerFeeCodeId;

  @Column(name = "ACQUIRER_NAME")
  private String acquirerName;

  @Column(name = "PARTNER_ID")
  private Long partnerId;

  @Column(name = "FLAT_FEE")
  private Double flatFee;

  @Column(name = "FEE_PERCENTAGE_ONLY")
  private Double feePercentageOnly;


  @Column(name = "UPDATED_BY")
  private String updatedBy;


  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;
  
  @Column(name = "MERCHANT_CODE")
  private String merchantCode;



  public String getAcquirerName() {
    return acquirerName;
  }

  public void setAcquirerName(String acquirerName) {
    this.acquirerName = acquirerName;
  }


  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public Long getAcquirerFeeCodeId() {
    return acquirerFeeCodeId;
  }

  public void setAcquirerFeeCodeId(Long acquirerFeeCodeId) {
    this.acquirerFeeCodeId = acquirerFeeCodeId;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public Double getFlatFee() {
    return flatFee;
  }

  public void setFlatFee(Double flatFee) {
    this.flatFee = flatFee;
  }

  public Double getFeePercentageOnly() {
    return feePercentageOnly;
  }

  public void setFeePercentageOnly(Double feePercentageOnly) {
    this.feePercentageOnly = feePercentageOnly;
  }
  
  
}

