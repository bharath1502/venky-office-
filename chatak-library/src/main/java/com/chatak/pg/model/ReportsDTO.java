package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.TransactionPopUpDataDto;

public class ReportsDTO implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -6917838503245452240L;

  private String dateTime;
  
  private String companyName;

  private String userName;

  private String currency;

  private Long accountNumber;
  
  private String transactionId;

  private String accountType;

  private String cardType;
  
  private String paymentMethod;

  private String amount;

  private String description;

  private String availableBalance;
  
  private String currentBalance;
  
  private String status;
  
  private String fee;
  
  private Long merchantAccountCount;
  
  private Long subMerchantAccountCount;
  
  private Long chatakAccountCount;
  
  private Long manualRevenueCount;
  
  private Long systemRevenueCount;
  
  private Long allGeneratedRevenueCount;
  
  private String merchantAccountBalance;
  
  private String subMerchantAccountBalance;
  
  private String chatakAccountBalance;
  
  private Long manualRevenueBalance;
  
  private Long systemRevenueBalance;
  
  private Long allGeneratedRevenueBalance;
  
  private String chatakFee;
  
  private String parentMerchantId;
  
  private String totalTxnAmount;
  
  private String merchantCode;
  
  private Long id;
  
  private String transactionType;
  
  private String issuanceFeeTxnId;

  private TransactionPopUpDataDto txnPopupDto;
  
  private String txnJsonString;
  
  private Integer totalNoOfRows;
  
  private Integer currencyExponent;
  
  private Integer currencySeparatorPosition;
  
  private Character currencyMinorUnit;
  
  private Character currencyThousandsUnit;
  
  private String deviceLocalTxnTime;
  
  private String timeZoneOffset;
  
  private String batchId;
  
  public Integer getCurrencyExponent() {
    return currencyExponent;
  }
  
  public Integer getCurrencySeparatorPosition() {
    return currencySeparatorPosition;
  }

  public void setCurrencyExponent(Integer currencyExponent) {
    this.currencyExponent = currencyExponent;
  }

  public Character getCurrencyMinorUnit() {
    return currencyMinorUnit;
  }
  
  public Character getCurrencyThousandsUnit() {
    return currencyThousandsUnit;
  }
  
  public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
    this.currencySeparatorPosition = currencySeparatorPosition;
  }

  public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
    this.currencyThousandsUnit = currencyThousandsUnit;
  }
  
  public void setCurrencyMinorUnit(Character currencyMinorUnit) {
    this.currencyMinorUnit = currencyMinorUnit;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAccountType() {
    return accountType;
  }
  
  public String getTransactionId() {
    return transactionId;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getCardType() {
    return cardType;
  }
  
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }
  
  public String getDescription() {
    return description;
  }
  
  public String getCurrency() {
    return currency;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getAvailableBalance() {
    return availableBalance;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getStatus() {
    return status;
  }

  public void setAvailableBalance(String availableBalance) {
    this.availableBalance = availableBalance;
  }

  public void setCurrentBalance(String currentBalance) {
    this.currentBalance = currentBalance;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFee() {
    return fee;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }

  public Long getMerchantAccountCount() {
    return merchantAccountCount;
  }

  public void setMerchantAccountCount(Long merchantAccountCount) {
    this.merchantAccountCount = merchantAccountCount;
  }

  public Long getSubMerchantAccountCount() {
    return subMerchantAccountCount;
  }

  public void setSubMerchantAccountCount(Long subMerchantAccountCount) {
    this.subMerchantAccountCount = subMerchantAccountCount;
  }

  public Long getChatakAccountCount() {
    return chatakAccountCount;
  }

  public void setChatakAccountCount(Long chatakAccountCount) {
    this.chatakAccountCount = chatakAccountCount;
  }

  public Long getManualRevenueCount() {
    return manualRevenueCount;
  }

  public void setManualRevenueCount(Long manualRevenueCount) {
    this.manualRevenueCount = manualRevenueCount;
  }

  public Long getSystemRevenueCount() {
    return systemRevenueCount;
  }

  public void setSystemRevenueCount(Long systemRevenueCount) {
    this.systemRevenueCount = systemRevenueCount;
  }

  public Long getAllGeneratedRevenueCount() {
    return allGeneratedRevenueCount;
  }

  public void setAllGeneratedRevenueCount(Long allGeneratedRevenueCount) {
    this.allGeneratedRevenueCount = allGeneratedRevenueCount;
  }

  public String getMerchantAccountBalance() {
    return merchantAccountBalance;
  }

  public void setMerchantAccountBalance(String merchantAccountBalance) {
    this.merchantAccountBalance = merchantAccountBalance;
  }

  public String getSubMerchantAccountBalance() {
    return subMerchantAccountBalance;
  }

  public void setSubMerchantAccountBalance(String subMerchantAccountBalance) {
    this.subMerchantAccountBalance = subMerchantAccountBalance;
  }

  public String getChatakAccountBalance() {
    return chatakAccountBalance;
  }

  public void setChatakAccountBalance(String chatakAccountBalance) {
    this.chatakAccountBalance = chatakAccountBalance;
  }

  public Long getManualRevenueBalance() {
    return manualRevenueBalance;
  }

  public void setManualRevenueBalance(Long manualRevenueBalance) {
    this.manualRevenueBalance = manualRevenueBalance;
  }

  public Long getSystemRevenueBalance() {
    return systemRevenueBalance;
  }

  public void setSystemRevenueBalance(Long systemRevenueBalance) {
    this.systemRevenueBalance = systemRevenueBalance;
  }

  public Long getAllGeneratedRevenueBalance() {
    return allGeneratedRevenueBalance;
  }

  public void setAllGeneratedRevenueBalance(Long allGeneratedRevenueBalance) {
    this.allGeneratedRevenueBalance = allGeneratedRevenueBalance;
  }

  public String getChatakFee() {
    return chatakFee;
  }

  public void setChatakFee(String chatakFee) {
    this.chatakFee = chatakFee;
  }

  public String getParentMerchantId() {
    return parentMerchantId;
  }

  public void setParentMerchantId(String parentMerchantId) {
    this.parentMerchantId = parentMerchantId;
  }

  public String getTotalTxnAmount() {
    return totalTxnAmount;
  }

  public void setTotalTxnAmount(String totalTxnAmount) {
    this.totalTxnAmount = totalTxnAmount;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public String getIssuanceFeeTxnId() {
    return issuanceFeeTxnId;
  }

  public void setIssuanceFeeTxnId(String issuanceFeeTxnId) {
    this.issuanceFeeTxnId = issuanceFeeTxnId;
  }

  /**
   * @return the txnPopupDto
   */
  public TransactionPopUpDataDto getTxnPopupDto() {
    return txnPopupDto;
  }

  /**
   * @param txnPopupDto the txnPopupDto to set
   */
  public void setTxnPopupDto(TransactionPopUpDataDto txnPopupDto) {
    this.txnPopupDto = txnPopupDto;
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

  public Integer getTotalNoOfRows() {
    return totalNoOfRows;
  }

  public void setTotalNoOfRows(Integer totalNoOfRows) {
    this.totalNoOfRows = totalNoOfRows;
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

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }
}
