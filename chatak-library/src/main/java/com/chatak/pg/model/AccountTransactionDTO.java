package com.chatak.pg.model;

import java.io.Serializable;

public class AccountTransactionDTO extends Response implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 3682725795266785022L;

  private Long id;

  private String transactionTime;

  private String processedTime;

  private String transactionId;

  private String type;
  
  private String merchantCompanyName;

  private String description;

  private String debit;

  private String credit;

  private String currentBalance;

  private String status;
  
  private String merchantCode;
  
  private String transactionCode;
  
  private String pgTransferId;
  
  private String pgTransactionId;
  
  private Integer refundStatus;
  
  private String emailId;
  
  private String accountNumber;
  
  private String batchId;
  
  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;
  
  private String toDate;

  private String fromDate;
  
  private Long debitLong;
  
  private Long creditLong;
  
  private Long currentBallong;
  
  private boolean searchResults;
  
  private String currency;
  
  private Integer currencyExponent;
  
  private Character currencyMinorUnit;
  
  private Integer currencySeparatorPosition;
  
  private Character currencyThousandsUnit;
  
  private String deviceLocalTxnTime;
  
  private String timeZoneOffset;
  
  /**
   * @return the currencyExponent
   */
  public Integer getCurrencyExponent() {
    return currencyExponent;
  }
  
  /**
   * @param currencySeparatorPosition the currencySeparatorPosition to set
   */
  public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
    this.currencySeparatorPosition = currencySeparatorPosition;
  }

  /**
   * @param currencyExponent the currencyExponent to set
   */
  public void setCurrencyExponent(Integer currencyExponent) {
    this.currencyExponent = currencyExponent;
  }

  /**
   * @return the currencyThousandsUnit
   */
  public Character getCurrencyThousandsUnit() {
    return currencyThousandsUnit;
  }
  
  /**
   * @return the currencyMinorUnit
   */
  public Character getCurrencyMinorUnit() {
    return currencyMinorUnit;
  }
  
  /**
   * @return the currencySeparatorPosition
   */
  public Integer getCurrencySeparatorPosition() {
    return currencySeparatorPosition;
  }

  /**
   * @param currencyMinorUnit the currencyMinorUnit to set
   */
  public void setCurrencyMinorUnit(Character currencyMinorUnit) {
    this.currencyMinorUnit = currencyMinorUnit;
  }

  /**
   * @param currencyThousandsUnit the currencyThousandsUnit to set
   */
  public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
    this.currencyThousandsUnit = currencyThousandsUnit;
  }

  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @param currency the currency to set
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getTransactionTime() {
    return transactionTime;
  }

  public void setTransactionTime(String transactionTime) {
    this.transactionTime = transactionTime;
  }

  public String getProcessedTime() {
    return processedTime;
  }

  public void setProcessedTime(String processedTime) {
    this.processedTime = processedTime;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDebit() {
    return debit;
  }

  public void setDebit(String debit) {
    this.debit = debit;
  }

  public String getCredit() {
    return credit;
  }

  public void setCredit(String credit) {
    this.credit = credit;
  }

  public String getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(String currentBalance) {
    this.currentBalance = currentBalance;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }

  /**
   * @param merchantCode the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  /**
   * @return the transactionCode
   */
  public String getTransactionCode() {
    return transactionCode;
  }

  /**
   * @param transactionCode the transactionCode to set
   */
  public void setTransactionCode(String transactionCode) {
    this.transactionCode = transactionCode;
  }

  public String getPgTransferId() {
    return pgTransferId;
  }

  public void setPgTransferId(String pgTransferId) {
    this.pgTransferId = pgTransferId;
  }

  public String getPgTransactionId() {
    return pgTransactionId;
  }

  public void setPgTransactionId(String pgTransactionId) {
    this.pgTransactionId = pgTransactionId;
  }

  public String getMerchantCompanyName() {
    return merchantCompanyName;
  }

  public void setMerchantCompanyName(String merchantCompanyName) {
    this.merchantCompanyName = merchantCompanyName;
  }
  
  /**
   * @return the refundStatus
   */
  public Integer getRefundStatus() {
    return refundStatus;
  }

  /**
   * @param refundStatus the refundStatus to set
   */
  public void setRefundStatus(Integer refundStatus) {
    this.refundStatus = refundStatus;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }
  
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @return the toDate
   */
  public String getToDate() {
    return toDate;
  }

  /**
   * @param toDate the toDate to set
   */
  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  /**
   * @return the fromDate
   */
  public String getFromDate() {
    return fromDate;
  }

  /**
   * @param fromDate the fromDate to set
   */
  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public Long getDebitLong() {
    return debitLong;
  }

  public void setDebitLong(Long debitLong) {
    this.debitLong = debitLong;
  }

  public Long getCreditLong() {
    return creditLong;
  }

  public void setCreditLong(Long creditLong) {
    this.creditLong = creditLong;
  }

  public Long getCurrentBallong() {
    return currentBallong;
  }

  public void setCurrentBallong(Long currentBallong) {
    this.currentBallong = currentBallong;
  }

  public boolean isSearchResults() {
    return searchResults;
  }

  public void setSearchResults(boolean searchResults) {
    this.searchResults = searchResults;
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
