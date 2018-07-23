package com.chatak.pg.bean.settlement;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class SettlementMerchantDetails {
  
  private String businessName;
  
  private String localCurrency;
  
  private String bankRoutingNumber;

  private String bankAccountNumber;
  
  private String bankNmae;
  
  private String merchantCode;
  
  private BigInteger isoTotalRevenueAmount;
  
  private BigInteger pmTotalRevenueAmount;
  
  private String entityTotalRevenueAmount;
  
  private List<String> pGTransactionIds;

  public String getBusinessName() {
    return businessName;
  }

  public String getLocalCurrency() {
    return localCurrency;
  }

  public String getBankRoutingNumber() {
    return bankRoutingNumber;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  public String getBankNmae() {
    return bankNmae;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public void setLocalCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
  }

  public void setBankRoutingNumber(String bankRoutingNumber) {
    this.bankRoutingNumber = bankRoutingNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }

  public void setBankNmae(String bankNmae) {
    this.bankNmae = bankNmae;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public BigInteger getIsoTotalRevenueAmount() {
    return isoTotalRevenueAmount;
  }

  public BigInteger getPmTotalRevenueAmount() {
    return pmTotalRevenueAmount;
  }

  public void setIsoTotalRevenueAmount(BigInteger isoTotalRevenueAmount) {
    this.isoTotalRevenueAmount = isoTotalRevenueAmount;
  }

  public void setPmTotalRevenueAmount(BigInteger pmTotalRevenueAmount) {
    this.pmTotalRevenueAmount = pmTotalRevenueAmount;
  }

  public String getEntityTotalRevenueAmount() {
    return entityTotalRevenueAmount;
  }

  public void setEntityTotalRevenueAmount(String entityTotalRevenueAmount) {
    this.entityTotalRevenueAmount = entityTotalRevenueAmount;
  }

  /**
   * @return the pGTransactionIds
   */
  public List<String> getpGTransactionIds() {
    return pGTransactionIds;
  }

  /**
   * @param pGTransactionIds the pGTransactionIds to set
   */
  public void setpGTransactionIds(List<String> pGTransactionIds) {
    this.pGTransactionIds = pGTransactionIds;
  }
  
}
