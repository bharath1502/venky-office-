package com.chatak.pg.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.chatak.pg.bean.TransactionPopUpDataDto;

public class LitleEFTDTO implements Serializable {
	
	private String merchantCode;
	
	private String transactionId;
	
	private Long amount;
	
	private Long feeAmount;
	
	private Timestamp dateTime;
	
	private TransactionPopUpDataDto txnDto;
	
	private String txnJsonString;
	
	private String modeOfExecution;
	
	private String batchId;
	
	private String paymentMethod;
	
	private Long debitAmount;
	
	private Long creditAmount;
	
	private String pgTransactionId;
	
	private String accountNumber;
	
	public String getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
  /**
   * @return the txnDto
   */
  public TransactionPopUpDataDto getTxnDto() {
    return txnDto;
  }
  /**
   * @param txnDto the txnDto to set
   */
  public void setTxnDto(TransactionPopUpDataDto txnDto) {
    this.txnDto = txnDto;
  }
  /**
   * @return the txnJsonString
   */
  public String getTxnJsonString() {
    return txnJsonString;
  }
  /**
   * @param txnJsonString the txnJsonString to set
   */
  public void setTxnJsonString(String txnJsonString) {
    this.txnJsonString = txnJsonString;
  }
  /**
   * @return the modeOfExecution
   */
  public String getModeOfExecution() {
    return modeOfExecution;
  }
  /**
   * @param modeOfExecution the modeOfExecution to set
   */
  public void setModeOfExecution(String modeOfExecution) {
    this.modeOfExecution = modeOfExecution;
  }
  /**
   * @return the batchId
   */
  public String getBatchId() {
    return batchId;
  }
  /**
   * @param batchId the batchId to set
   */
  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }
  public String getPaymentMethod() {
    return paymentMethod;
  }
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
  public Long getFeeAmount() {
    return feeAmount;
  }
  public void setFeeAmount(Long feeAmount) {
    this.feeAmount = feeAmount;
  }
  public Long getDebitAmount() {
    return debitAmount;
  }
  public void setDebitAmount(Long debitAmount) {
    this.debitAmount = debitAmount;
  }
  public Long getCreditAmount() {
    return creditAmount;
  }
  public void setCreditAmount(Long creditAmount) {
    this.creditAmount = creditAmount;
  }
  public String getPgTransactionId() {
    return pgTransactionId;
  }
  public void setPgTransactionId(String pgTransactionId) {
    this.pgTransactionId = pgTransactionId;
  }
}
