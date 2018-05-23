package com.chatak.pg.bean;

import java.io.Serializable;

public class TransactionPopUpDataDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4082116531346050949L;

  private String transaction_type;

  private String auth_id;

  private String transactionId;

  private String ref_transaction_id;

  private String terminal_id;

  private String invoice_number;

  private String maskCardNumber;

  private String merchant_code;

  private String acqTxnMode;

  private String acqChannel;

  private String transactionDate;

  private String transactionAmount;

  private String fee_amount;

  private String txn_total_amount;

  private String processor;

  private String statusMessage;

  private String merchantSettlementStatus;

  private String txnDescription;

  private String merchantBusinessName;

  private String cardHolderName;

  private String merchantType;

  private String dtoType;

  private String fromAccount;

  private String toAccount;

  private String status;

  private String accountTransactionId;

  private String batchId;
  
  private String deviceLocalTxnTime;
  
  private String timeZoneOffset;

  public String getTransaction_type() {
    return transaction_type;
  }

  public void setTransaction_type(String transaction_type) {
    this.transaction_type = transaction_type;
  }

  public String getAuth_id() {
    return auth_id;
  }

  public void setAuth_id(String auth_id) {
    this.auth_id = auth_id;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getRef_transaction_id() {
    return ref_transaction_id;
  }

  public void setRef_transaction_id(String ref_transaction_id) {
    this.ref_transaction_id = ref_transaction_id;
  }

  public String getTerminal_id() {
    return terminal_id;
  }

  public void setTerminal_id(String terminal_id) {
    this.terminal_id = terminal_id;
  }

  public String getInvoice_number() {
    return invoice_number;
  }

  public void setInvoice_number(String invoice_number) {
    this.invoice_number = invoice_number;
  }

  public String getMaskCardNumber() {
    return maskCardNumber;
  }

  public void setMaskCardNumber(String maskCardNumber) {
    this.maskCardNumber = maskCardNumber;
  }

  public String getMerchant_code() {
    return merchant_code;
  }

  public void setMerchant_code(String merchant_code) {
    this.merchant_code = merchant_code;
  }

  public String getAcqTxnMode() {
    return acqTxnMode;
  }

  public void setAcqTxnMode(String acqTxnMode) {
    this.acqTxnMode = acqTxnMode;
  }

  public String getAcqChannel() {
    return acqChannel;
  }

  public void setAcqChannel(String acqChannel) {
    this.acqChannel = acqChannel;
  }

  public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  public String getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(String transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public String getFee_amount() {
    return fee_amount;
  }

  public void setFee_amount(String fee_amount) {
    this.fee_amount = fee_amount;
  }

  public String getTxn_total_amount() {
    return txn_total_amount;
  }

  public void setTxn_total_amount(String txn_total_amount) {
    this.txn_total_amount = txn_total_amount;
  }

  public String getProcessor() {
    return processor;
  }

  public void setProcessor(String processor) {
    this.processor = processor;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getMerchantSettlementStatus() {
    return merchantSettlementStatus;
  }

  public void setMerchantSettlementStatus(String merchantSettlementStatus) {
    this.merchantSettlementStatus = merchantSettlementStatus;
  }

  public String getTxnDescription() {
    return txnDescription;
  }

  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }

  public String getMerchantBusinessName() {
    return merchantBusinessName;
  }

  public void setMerchantBusinessName(String merchantBusinessName) {
    this.merchantBusinessName = merchantBusinessName;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  public String getMerchantType() {
    return merchantType;
  }

  public void setMerchantType(String merchantType) {
    this.merchantType = merchantType;
  }

  public String getDtoType() {
    return dtoType;
  }

  public void setDtoType(String dtoType) {
    this.dtoType = dtoType;
  }

  public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAccountTransactionId() {
    return accountTransactionId;
  }

  public void setAccountTransactionId(String accountTransactionId) {
    this.accountTransactionId = accountTransactionId;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getDeviceLocalTxnTime() {
    return deviceLocalTxnTime;
  }

  public void setDeviceLocalTxnTime(String deviceLocalTxnTime) {
    this.deviceLocalTxnTime = deviceLocalTxnTime;
  }

  public String getTimeZoneOffset() {
    return timeZoneOffset;
  }

  public void setTimeZoneOffset(String timeZoneOffset) {
    this.timeZoneOffset = timeZoneOffset;
  }

}