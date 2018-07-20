package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Transaction implements Serializable {

  private static final long serialVersionUID = -4097441693665168699L;

  private Long id;

  private Long txn_ref_num;

  private Long ref_transaction_id;

  private String auth_id;

  private Long sys_trace_num;

  private String transaction_type;

  private String payment_method;

  private Double txn_amount;

  private Double adj_amount;

  private Double fee_amount;

  private Double txn_total_amount;

  private String merchant_code;

  private Long terminal_id;

  private Timestamp created_date;

  private String updated_date;

  private String status;

  private String invoice_number;

  private String transactionId;

  private String transactionAmount;

  private String transactionDate;

  private String maskCardNumber;

  private String statusMessage;

  private String procTxnCode;

  private Long merchantFeeAmount;

  private String txnDescription;

  private String merchantSettlementStatus;

  private Long accountNumber;

  private String merchantType;

  private String merchantName;

  private String entryMode;

  private String acqChannel;

  private String availableBalance;

  private String acqTxnMode;

  private String processor;

  private String txnMode;

  private String eftStatus;

  private String txnJsonString;

  private String feeString;

  private String merchantBusinessName;

  private String cardHolderName;

  private Integer refundStatus;

  private String localCurrency;

  private String batchId;
  
  private String totalAmt;

  private String userName;
  
  private String batchDate;
  
  private String deviceLocalTxnTime;
  
  private String timeZoneOffset;

  public String getTotalAmt() {
    return totalAmt;
  }

  public void setTotalAmt(String totalAmt) {
    this.totalAmt = totalAmt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTxn_ref_num() {
    return txn_ref_num;
  }

  public void setTxn_ref_num(Long txn_ref_num) {
    this.txn_ref_num = txn_ref_num;
  }

  public Long getRef_transaction_id() {
    return ref_transaction_id;
  }

  public void setRef_transaction_id(Long ref_transaction_id) {
    this.ref_transaction_id = ref_transaction_id;
  }

  public String getAuth_id() {
    return auth_id;
  }

  public void setAuth_id(String auth_id) {
    this.auth_id = auth_id;
  }

  public Long getSys_trace_num() {
    return sys_trace_num;
  }

  public void setSys_trace_num(Long sys_trace_num) {
    this.sys_trace_num = sys_trace_num;
  }

  public String getTransaction_type() {
    return transaction_type;
  }

  public void setTransaction_type(String transaction_type) {
    this.transaction_type = transaction_type;
  }

  public String getPayment_method() {
    return payment_method;
  }

  public void setPayment_method(String payment_method) {
    this.payment_method = payment_method;
  }

  public Double getTxn_amount() {
    return txn_amount;
  }

  public void setTxn_amount(Double txn_amount) {
    this.txn_amount = txn_amount;
  }

  public Double getAdj_amount() {
    return adj_amount;
  }

  public void setAdj_amount(Double adj_amount) {
    this.adj_amount = adj_amount;
  }

  public Double getFee_amount() {
    return fee_amount;
  }

  public void setFee_amount(Double fee_amount) {
    this.fee_amount = fee_amount;
  }

  public Double getTxn_total_amount() {
    return txn_total_amount;
  }

  public void setTxn_total_amount(Double txn_total_amount) {
    this.txn_total_amount = txn_total_amount;
  }

  public String getMerchant_code() {
    return merchant_code;
  }

  public void setMerchant_code(String merchant_code) {
    this.merchant_code = merchant_code;
  }

  public Long getTerminal_id() {
    return terminal_id;
  }

  public void setTerminal_id(Long terminal_id) {
    this.terminal_id = terminal_id;
  }

  public Timestamp getCreated_date() {
    return created_date;
  }

  public void setCreated_date(Timestamp created_date) {
    this.created_date = created_date;
  }

  public String getUpdated_date() {
    return updated_date;
  }

  public void setUpdated_date(String updated_date) {
    this.updated_date = updated_date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getInvoice_number() {
    return invoice_number;
  }

  public void setInvoice_number(String invoice_number) {
    this.invoice_number = invoice_number;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(String transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  public String getMaskCardNumber() {
    return maskCardNumber;
  }

  public void setMaskCardNumber(String maskCardNumber) {
    this.maskCardNumber = maskCardNumber;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getProcTxnCode() {
    return procTxnCode;
  }

  public void setProcTxnCode(String procTxnCode) {
    this.procTxnCode = procTxnCode;
  }

  public Long getMerchantFeeAmount() {
    return merchantFeeAmount;
  }

  public void setMerchantFeeAmount(Long merchantFeeAmount) {
    this.merchantFeeAmount = merchantFeeAmount;
  }

  public String getTxnDescription() {
    return txnDescription;
  }

  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }

  public String getMerchantSettlementStatus() {
    return merchantSettlementStatus;
  }

  public void setMerchantSettlementStatus(String merchantSettlementStatus) {
    this.merchantSettlementStatus = merchantSettlementStatus;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getMerchantType() {
    return merchantType;
  }

  public void setMerchantType(String merchantType) {
    this.merchantType = merchantType;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getEntryMode() {
    return entryMode;
  }

  public void setEntryMode(String entryMode) {
    this.entryMode = entryMode;
  }

  public String getAcqChannel() {
    return acqChannel;
  }

  public void setAcqChannel(String acqChannel) {
    this.acqChannel = acqChannel;
  }

  public String getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(String availableBalance) {
    this.availableBalance = availableBalance;
  }

  public String getAcqTxnMode() {
    return acqTxnMode;
  }

  public void setAcqTxnMode(String acqTxnMode) {
    this.acqTxnMode = acqTxnMode;
  }

  public String getProcessor() {
    return processor;
  }

  public void setProcessor(String processor) {
    this.processor = processor;
  }

  public String getTxnMode() {
    return txnMode;
  }

  public void setTxnMode(String txnMode) {
    this.txnMode = txnMode;
  }

  public String getEftStatus() {
    return eftStatus;
  }

  public void setEftStatus(String eftStatus) {
    this.eftStatus = eftStatus;
  }

  public String getTxnJsonString() {
    return txnJsonString;
  }

  public void setTxnJsonString(String txnJsonString) {
    this.txnJsonString = txnJsonString;
  }

  public String getFeeString() {
    return feeString;
  }

  public void setFeeString(String feeString) {
    this.feeString = feeString;
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

  public Integer getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(Integer refundStatus) {
    this.refundStatus = refundStatus;
  }

  public String getLocalCurrency() {
    return localCurrency;
  }

  public void setLocalCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getBatchDate() {
    return batchDate;
  }

  public void setBatchDate(String batchDate) {
    this.batchDate = batchDate;
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
