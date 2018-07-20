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
@Table(name = "PG_PARTNER_FEE_CODE")
public class PGPartnerFeeCode implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2127653880224134066L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_PARTNER_FEE_CODE_ID", sequenceName = "SEQ_PG_PARTNER_FEE_CODE")
  @GeneratedValue(generator = "SEQ_PG_PARTNER_FEE_CODE_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PARTNER_FEE_CODE_ID")
  private Long partnerFeeCodeId;

  @Column(name = "PARTNER_NAME")
  private String partnerName;

  @Column(name = "PARTNER_ENTITY_ID")
  private String partnerEntityId;

  @Column(name = "PARTNER_ENTITY_TYPE")
  private String partnerEntityType;

  @Column(name = "FLAT_FEE")
  private Long flatFee;

  @Column(name = "FEE_PERCENTAGE_ONLY")
  private Long feePercentageOnly;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "ACCOUNT_NUM")
  private Long accountNumber;

  public String getPartnerEntityId() {
    return partnerEntityId;
  }

  public void setPartnerEntityId(String partnerEntityId) {
    this.partnerEntityId = partnerEntityId;
  }

  public String getPartnerEntityType() {
    return partnerEntityType;
  }

  public void setPartnerEntityType(String partnerEntityType) {
    this.partnerEntityType = partnerEntityType;
  }

  public String getCreatedBy() {
    return createdBy;
  }
  
  public Long getFlatFee() {
	 return flatFee;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }
  
  public void setFlatFee(Long flatFee) {
	    this.flatFee = flatFee;
	  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }
  
  public Long getAccountNumber() {
    return accountNumber;
  }
  
  public Long getFeePercentageOnly() {
	 return feePercentageOnly;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }
  
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }
  
  public void setFeePercentageOnly(Long feePercentageOnly) {
    this.feePercentageOnly = feePercentageOnly;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public Long getPartnerFeeCodeId() {
    return partnerFeeCodeId;
  }

  public void setPartnerFeeCodeId(Long partnerFeeCodeId) {
    this.partnerFeeCodeId = partnerFeeCodeId;
  }

}
