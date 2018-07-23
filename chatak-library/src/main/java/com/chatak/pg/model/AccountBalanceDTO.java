package com.chatak.pg.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class AccountBalanceDTO extends Response implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -6914054968945262952L;
  private String merchantCode;
  private String merchantName;
  private Long availableBalance;
  private Long currentBalance;
  private Long accountNumber;
  private Long inputAmount;
  private String description;
  private boolean systemAccountFlag;
  private boolean batchIdCheckBox;
  private String batchId;
  private String nameOnAccount;
  private boolean revenueAccountFlag;
  private boolean chargeBackFlag;   
  private String status;
  private String entityType;
  private String merchantCurrencyAlpha;
  private Integer currencyExponent;
  private Integer currencySeparatorPosition;
  private Character currencyMinorUnit;
  private Character currencyThousandsUnit;
  private String timeZoneOffset;
  private String timeZoneRegion;
  private String deviceLocalTxnTime;
  /**
   * @return the currencyMinorUnit
   */
  public Character getCurrencyMinorUnit() {
    return currencyMinorUnit;
  }
  /**
   * @param currencyMinorUnit the currencyMinorUnit to set
   */
  public void setCurrencyMinorUnit(Character currencyMinorUnit) {
    this.currencyMinorUnit = currencyMinorUnit;
  }
  /**
   * @return the currencyThousandsUnit
   */
  public Character getCurrencyThousandsUnit() {
    return currencyThousandsUnit;
  }
  /**
   * @param currencyThousandsUnit the currencyThousandsUnit to set
   */
  public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
    this.currencyThousandsUnit = currencyThousandsUnit;
  }
  
  
  /**
   * @return the currencyExponent
   */
  public Integer getCurrencyExponent() {
    return currencyExponent;
  }
  /**
   * @param currencyExponent the currencyExponent to set
   */
  public void setCurrencyExponent(Integer currencyExponent) {
    this.currencyExponent = currencyExponent;
  }
  /**
   * @return the currencySeparatorPosition
   */
  public Integer getCurrencySeparatorPosition() {
    return currencySeparatorPosition;
  }
  /**
   * @param currencySeparatorPosition the currencySeparatorPosition to set
   */
  public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
    this.currencySeparatorPosition = currencySeparatorPosition;
  }
  
  public String getMerchantCode() {
    return merchantCode;
  }
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }
  public String getMerchantName() {
    return merchantName;
  }
  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }
  public Long getAvailableBalance() {
    return availableBalance;
  }
  public void setAvailableBalance(Long availableBalance) {
    this.availableBalance = availableBalance;
  }
  public Long getCurrentBalance() {
    return currentBalance;
  }
  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }
  public Long getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }
  public Long getInputAmount() {
    return inputAmount;
  }
  public void setInputAmount(Long inputAmount) {
    this.inputAmount = inputAmount;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public boolean isSystemAccountFlag() {
    return systemAccountFlag;
  }
  public void setSystemAccountFlag(boolean systemAccountFlag) {
    this.systemAccountFlag = systemAccountFlag;
  }
  public boolean isBatchIdCheckBox() {
    return batchIdCheckBox;
  }
  public void setBatchIdCheckBox(boolean batchIdCheckBox) {
    this.batchIdCheckBox = batchIdCheckBox;
  }
  public String getBatchId() {
    return batchId;
  }
  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }
  public String getNameOnAccount() {
    return nameOnAccount;
  }
  public void setNameOnAccount(String nameOnAccount) {
    this.nameOnAccount = nameOnAccount;
  }
  public boolean isRevenueAccountFlag() {
    return revenueAccountFlag;
  }
  public void setRevenueAccountFlag(boolean revenueAccountFlag) {
    this.revenueAccountFlag = revenueAccountFlag;
  }
  public boolean isChargeBackFlag() {
    return chargeBackFlag;
  }
  public void setChargeBackFlag(boolean chargeBackFlag) {
    this.chargeBackFlag = chargeBackFlag;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getEntityType() {
    return entityType;
  }
  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }
  public String getMerchantCurrencyAlpha() {
    return merchantCurrencyAlpha;
  }
  public void setMerchantCurrencyAlpha(String merchantCurrencyAlpha) {
    this.merchantCurrencyAlpha = merchantCurrencyAlpha;
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
}
