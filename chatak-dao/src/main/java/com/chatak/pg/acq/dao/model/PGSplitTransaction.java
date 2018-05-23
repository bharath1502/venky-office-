/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Jun 5, 2015
 * @Time: 7:50:45 PM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_SPLIT_TRANSACTION")
public class PGSplitTransaction {

  @Id
  /*@SequenceGenerator(name = "PG_SPLIT_TRANSACTION_ID", sequenceName = "SEQ_PG_SPLIT_TRANSACTION")
  @GeneratedValue(generator = "PG_SPLIT_TRANSACTION_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "SPLIT_TRANSACTION_ID")
  private String splitTransactionId;

  @Column(name = "PG_TRANSACTION_ID")
  private String pgTransactionId;//
  
  @Column(name = "PG_REF_TRANSACTION_ID")
  private String pgRefTransactionId;

  @Column(name = "SPLIT_AMOUNT")
  private Long splitAmount;

  @Column(name = "TOTAL_AMOUNT")
  private Long txnAmount;

  @Column(name = "MERCHANT_ID")
  private String merchantId;

  @Column(name = "SPLIT_MODE")
  private String splitMode;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updateddDate;

  @Column(name = "STATUS")
  private Long status;

  @Column(name = "CARD_HOLDER_NAME")
  private String cardHolderName;

  @Column(name = "PAN")
  private String pan;

  @Column(name = "PAN_MASKED")
  private String panMasked;

  @Column(name = "EXP_DATE")
  private String expDate;

  @Column(name = "TXN_DESCRIPTION")
  private String txnDescription;

  @Column(name = "REASON")
  private String reason;
  
  @Column(name = "MOBILE_NUM")
  private Long mobileNumber;//Added for making split txn reference.

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the splitTransactionId
   */
  public String getSplitTransactionId() {
    return splitTransactionId;
  }

  /**
   * @param splitTransactionId the splitTransactionId to set
   */
  public void setSplitTransactionId(String splitTransactionId) {
    this.splitTransactionId = splitTransactionId;
  }

  /**
   * @return the pgTransactionId
   */
  public String getPgTransactionId() {
    return pgTransactionId;
  }

  /**
   * @param pgTransactionId the pgTransactionId to set
   */
  public void setPgTransactionId(String pgTransactionId) {
    this.pgTransactionId = pgTransactionId;
  }

  /**
   * @return the splitAmount
   */
  public Long getSplitAmount() {
    return splitAmount;
  }

  /**
   * @param splitAmount the splitAmount to set
   */
  public void setSplitAmount(Long splitAmount) {
    this.splitAmount = splitAmount;
  }

  /**
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
  }

  /**
   * @param txnAmount the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }

  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the splitMode
   */
  public String getSplitMode() {
    return splitMode;
  }

  /**
   * @param splitMode the splitMode to set
   */
  public void setSplitMode(String splitMode) {
    this.splitMode = splitMode;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updateddDate
   */
  public Timestamp getUpdateddDate() {
    return updateddDate;
  }

  /**
   * @param updateddDate the updateddDate to set
   */
  public void setUpdateddDate(Timestamp updateddDate) {
    this.updateddDate = updateddDate;
  }

  /**
   * @return the status
   */
  public Long getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Long status) {
    this.status = status;
  }

  /**
   * @return the cardHolderName
   */
  public String getCardHolderName() {
    return cardHolderName;
  }

  /**
   * @param cardHolderName the cardHolderName to set
   */
  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  /**
   * @return the pan
   */
  public String getPan() {
    return pan;
  }

  /**
   * @param pan the pan to set
   */
  public void setPan(String pan) {
    this.pan = pan;
  }

  /**
   * @return the panMasked
   */
  public String getPanMasked() {
    return panMasked;
  }

  /**
   * @param panMasked the panMasked to set
   */
  public void setPanMasked(String panMasked) {
    this.panMasked = panMasked;
  }

  /**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }

  /**
   * @param expDate the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

  /**
   * @return the txnDescription
   */
  public String getTxnDescription() {
    return txnDescription;
  }

  /**
   * @param txnDescription the txnDescription to set
   */
  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }

  /**
   * @return the reason
   */
  public String getReason() {
    return reason;
  }

  /**
   * @param reason the reason to set
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * @return the pgRefTransactionId
   */
  public String getPgRefTransactionId() {
    return pgRefTransactionId;
  }

  /**
   * @param pgRefTransactionId the pgRefTransactionId to set
   */
  public void setPgRefTransactionId(String pgRefTransactionId) {
    this.pgRefTransactionId = pgRefTransactionId;
  }

  /**
   * @return the mobileNumber
   */
  public Long getMobileNumber() {
    return mobileNumber;
  }

  /**
   * @param mobileNumber the mobileNumber to set
   */
  public void setMobileNumber(Long mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
  
  
  

  /*
   * "ID" NUMBER(10,0) PRIMARY KEY, "SPLIT_TRANSACTION_ID" NUMBER(12,0),
   * "PG_TRANSACTION_ID" NUMBER(12,0), "SPLIT_AMOUNT" NUMBER(20,0),
   * "TOTAL_AMOUNT" NUMBER(20,0), "MERCHANT_ID" NUMBER(15,0), "SPLIT_MODE"
   * VARCHAR2(20 BYTE), "CREATED_DATE" TIMESTAMP (6), "UPDATED_DATE" TIMESTAMP
   * (6), "STATUS" NUMBER(1,0), "CARD_HOLDER_NAME" VARCHAR2(150 BYTE), "PAN"
   * VARCHAR2(150 BYTE), "PAN_MASKED" VARCHAR2(20 BYTE), "EXP_DATE" VARCHAR2(50
   * BYTE), "TXN_DESCRIPTION" VARCHAR2(500 BYTE), "REASON" VARCHAR2(500 BYTE)
   */
  
  
  

}
