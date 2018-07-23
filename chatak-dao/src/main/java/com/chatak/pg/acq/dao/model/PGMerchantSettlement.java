package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_MERCHANT_SETTLEMENT")
public class PGMerchantSettlement implements Serializable{
  
  private static final long serialVersionUID = 1L;
  
  @Id
  /*@SequenceGenerator(name = "SEQ_PG_MERCHANT_SETTLEMENT_ID", sequenceName = "SEQ_PG_MERCHANT_SETTLEMENT")
  @GeneratedValue(generator = "SEQ_PG_MERCHANT_SETTLEMENT_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id; 
  
  @Column(name = "ENTITY_ID")
  private String entityId;
  
  @Column(name = "MERCAHNT_CODE")
  private String merchantCode;
  
  @Column(name = "ACCOUNT_NUMBER")
  private String accountNumber;
  
  @Column(name = "AMOUNT")
  private Long amount;
  
  @Column(name = "STATUS")
  private String status;
  
  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;
  
  @Column(name = "REQUESTED_DATE")
  private Timestamp requestedDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the entityId
   */
  public String getEntityId() {
    return entityId;
  }

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }

  /**
   * @return the accountNumber
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * @return the amount
   */
  public Long getAmount() {
    return amount;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @return the requestedDate
   */
  public Timestamp getRequestedDate() {
    return requestedDate;
  }

  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @param entityId the entityId to set
   */
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  /**
   * @param merchantCode the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  /**
   * @param accountNumber the accountNumber to set
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(Long amount) {
    this.amount = amount;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @param requestedDate the requestedDate to set
   */
  public void setRequestedDate(Timestamp requestedDate) {
    this.requestedDate = requestedDate;
  }

  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

}
