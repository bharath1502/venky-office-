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
import javax.persistence.Transient;

/**
 * @Author: Girmiti Software
 * @Date: Feb 25, 2016
 * @Time: 6:54:47 PM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_ACCOUNT_TRANSACTIONS")
public class PGAccountTransactions implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5323691461141311402L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_ACCOUNT_TRANSACTIONS_ID", sequenceName = "SEQ_PG_ACCOUNT_TRANSACTIONS")
  @GeneratedValue(generator = "SEQ_PG_ACCOUNT_TRANSACTIONS_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "ACCOUNT_TRANSACTION_ID")
  private String accountTransactionId;
  
  @Column(name="PG_TRANSACTION_ID")
  private String pgTransactionId;
  
  @Column(name="PG_TRANSFER_ID")
  private String pgTransferId;

  @Column(name = "ACCOUNT_NUMBER")
  private String accountNumber;

  @Column(name = "TRANSACTION_TYPE")
  private String transactionType;

  @Column(name = "TRANSACTION_TIME")
  private Timestamp transactionTime;

  @Column(name = "PROCESSED_TIME")
  private Timestamp processedTime;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedTime;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "DEBIT")
  private Long debit;

  @Column(name = "CREDIT")
  private Long credit;

  @Column(name = "CURRENT_BALANCE")
  private Long currentBalance;

  @Column(name = "STATUS")
  private String status;
  
  @Column(name = "TRANSACTION_CODE")
  private String transactionCode;
  
  @Column(name = "MERCHANT_CODE")
  private String merchantCode;
  
  @Column(name ="TO_ACCOUNT_NUMBER")
  private String toAccountNumber;

  @Column(name = "REFUNDABLE_AMOUNT")
  private Long refundableAmount;
  
  @Transient
  private String txnCurrencyCode;

  @Column(name = "TIME_ZONE_OFFSET")
  private String timeZoneOffset;

  @Column(name = "TIME_ZONE_REGION")
  private String timeZoneRegion;

  @Column(name = "DEVICE_LOCAL_TXN_TIME")
  private String deviceLocalTxnTime;
  
  @Column(name = "ENTITY_TYPE")
  private String entityType;
  
  @Column(name = "ENTITY_ID")
  private Long entityId;

  @Column(name = "BATCH_ID")
  private String batchId;

	public String getTxnCurrencyCode() {
		return txnCurrencyCode;
	}

	public void setTxnCurrencyCode(String txnCurrencyCode) {
		this.txnCurrencyCode = txnCurrencyCode;
	}

	public Long getId() {
		return id;
	}

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccountTransactionId() {
    return accountTransactionId;
  }

  public void setAccountTransactionId(String accountTransactionId) {
    this.accountTransactionId = accountTransactionId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public Timestamp getTransactionTime() {
    return transactionTime;
  }

  public void setTransactionTime(Timestamp transactionTime) {
    this.transactionTime = transactionTime;
  }

  public Timestamp getProcessedTime() {
    return processedTime;
  }

  public void setProcessedTime(Timestamp processedTime) {
    this.processedTime = processedTime;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public Timestamp getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Timestamp updatedTime) {
    this.updatedTime = updatedTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getDebit() {
    return debit;
  }

  public void setDebit(Long debit) {
    this.debit = debit;
  }

  public Long getCredit() {
    return credit;
  }

  public void setCredit(Long credit) {
    this.credit = credit;
  }

  public Long getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPgTransactionId() {
    return pgTransactionId;
  }

  public void setPgTransactionId(String pgTransactionId) {
    this.pgTransactionId = pgTransactionId;
  }

  public String getPgTransferId() {
    return pgTransferId;
  }

  public void setPgTransferId(String pgTransferId) {
    this.pgTransferId = pgTransferId;
  }

  public String getTransactionCode() {
    return transactionCode;
  }

  public void setTransactionCode(String transactionCode) {
    this.transactionCode = transactionCode;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getToAccountNumber() {
    return toAccountNumber;
  }

  public void setToAccountNumber(String toAccountNumber) {
    this.toAccountNumber = toAccountNumber;
  }

	public Long getRefundableAmount() {
		return refundableAmount;
	}

	public void setRefundableAmount(Long refundableAmount) {
		this.refundableAmount = refundableAmount;
	}

	public String getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(String timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

	public String getTimeZoneRegion() {
		return timeZoneRegion;
	}

	public void setTimeZoneRegion(String timeZoneRegion) {
		this.timeZoneRegion = timeZoneRegion;
	}

	public String getDeviceLocalTxnTime() {
		return deviceLocalTxnTime;
	}

	public void setDeviceLocalTxnTime(String deviceLocalTxnTime) {
		this.deviceLocalTxnTime = deviceLocalTxnTime;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

}
