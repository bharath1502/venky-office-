/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Jun 16, 2015
 * @Time: 10:47:19 AM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_ACCOUNT_HISTORY")
public class PGAccountHistory implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_ACCOUNT_HISTORY_ID", sequenceName = "SEQ_PG_ACCOUNT_HISTORY")
  @GeneratedValue(generator = "SEQ_PG_ACCOUNT_HISTORY_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "ENTITY_ID")
  private String entityId;
  
  @Column(name = "ACCOUNT_DESC")
  private String accountDesc;

  @Column(name = "ENTITY_TYPE")
  private String entityType;

  @Column(name = "ACCOUNT_NUM")
  private Long accountNum;
  
  @Column(name = "CURRENT_BALANCE")
  private Long currentBalance;

  @Column(name = "CATEGORY")
  private String category;

  @Column(name = "AVAILABLE_BALANCE")
  private Long availableBalance;
  
  @Column(name = "AUTO_PAYMENT_LIMIT")
  private BigDecimal autoPaymentLimit;

  @Column(name = "CURRENCY")
  private String currency;

  @Column(name = "AUTO_PAYMENT_METHOD")
  private String autoPaymentMethod;
  
  @Column(name = "STATUS")
  private String status;

  @Column(name = "AUTO_TRANSFER_DAY")
  private String autoTransferDay;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "FEE_BALANCE")
  private Long feeBalance;

  @Column(name = "PAYMENT_METHOD")
  private String paymentMethod;
  
  @Column(name="TRANSACTION_ID")
  private String transactionId;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }
  
  /**
   * @return the entityType
   */
  public String getEntityType() {
    return entityType;
  }

  /**
   * @return the entityId
   */
  public String getEntityId() {
    return entityId;
  }

  /**
   * @return the accountDesc
   */
  public String getAccountDesc() {
    return accountDesc;
  }
  
  /**
   * @return the currentBalance
   */
  public Long getCurrentBalance() {
    return currentBalance;
  }

  /**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @return the availableBalance
   */
  public Long getAvailableBalance() {
    return availableBalance;
  }
  
  /**
   * @return the autoPaymentLimit
   */
  public BigDecimal getAutoPaymentLimit() {
    return autoPaymentLimit;
  }

  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @return the autoPaymentMethod
   */
  public String getAutoPaymentMethod() {
    return autoPaymentMethod;
  }
  
  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * @param entityType
   *          the entityType to set
   */
  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  /**
   * @param entityId
   *          the entityId to set
   */
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  /**
   * @param accountDesc
   *          the accountDesc to set
   */
  public void setAccountDesc(String accountDesc) {
    this.accountDesc = accountDesc;
  }
  
  /**
   * @param currentBalance
   *          the currentBalance to set
   */
  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }

  /**
   * @param category
   *          the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @param availableBalance
   *          the availableBalance to set
   */
  public void setAvailableBalance(Long availableBalance) {
    this.availableBalance = availableBalance;
  }
  
  /**
   * @param autoPaymentLimit
   *          the autoPaymentLimit to set
   */
  public void setAutoPaymentLimit(BigDecimal autoPaymentLimit) {
    this.autoPaymentLimit = autoPaymentLimit;
  }

  /**
   * @param currency
   *          the currency to set
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * @param autoPaymentMethod
   *          the autoPaymentMethod to set
   */
  public void setAutoPaymentMethod(String autoPaymentMethod) {
    this.autoPaymentMethod = autoPaymentMethod;
  }
  
  /**
   * @param updatedDate
   *          the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the accountNum
   */
  public Long getAccountNum() {
    return accountNum;
  }
  
  /**
   * @return the feeBalance
   */
  public Long getFeeBalance() {
    return feeBalance;
  }

  /**
   * @param accountNum
   *          the accountNum to set
   */
  public void setAccountNum(Long accountNum) {
    this.accountNum = accountNum;
  }

  /**
   * @param feeBalance
   *          the feeBalance to set
   */
  public void setFeeBalance(Long feeBalance) {
    this.feeBalance = feeBalance;
  }
  
  /**
   * @param paymentMethod the paymentMethod to set
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /**
   * @return the paymentMethod
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @return the transactionId
   */
  public String getTransactionId() {
    return transactionId;
  }
  
  public String getAutoTransferDay() {
    return autoTransferDay;
  }

  /**
   * @param transactionId the transactionId to set
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public void setAutoTransferDay(String autoTransferDay) {
    this.autoTransferDay = autoTransferDay;
  }
  

}
