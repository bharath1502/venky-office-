/**
 * 
 */
package com.chatak.pg.bean;

import java.io.Serializable;

/**
 * << Model to request account to account transfer >>
 *
 * @author Girmiti Software
 * @date Mar 9, 2016 5:14:30 PM
 * @version 1.0
 */
public class AccountTransferRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 9048164500758980927L;

  /**
   * 
   */

  private String sourceMerchantCode;

  private String sourceMerchantName;

  private String sourceAccountNumber;

  private Long sourceAvailableBalance;

  private String destinationMerchantCode;

  private String destinationMerchantName;

  private String destinationAccountNumber;
  
  private String destinationAvailableBalance;

  private String description;

  private Double transferAmount;

  private String accountCloseFlag;

  public String getSourceMerchantCode() {
    return sourceMerchantCode;
  }

  public void setSourceMerchantCode(String sourceMerchantCode) {
    this.sourceMerchantCode = sourceMerchantCode;
  }

  public String getSourceMerchantName() {
    return sourceMerchantName;
  }

  public void setSourceMerchantName(String sourceMerchantName) {
    this.sourceMerchantName = sourceMerchantName;
  }

  public String getSourceAccountNumber() {
    return sourceAccountNumber;
  }

  public void setSourceAccountNumber(String sourceAccountNumber) {
    this.sourceAccountNumber = sourceAccountNumber;
  }

  public Long getSourceAvailableBalance() {
    return sourceAvailableBalance;
  }

  public void setSourceAvailableBalance(Long sourceAvailableBalance) {
    this.sourceAvailableBalance = sourceAvailableBalance;
  }

  public String getDestinationMerchantCode() {
    return destinationMerchantCode;
  }

  public void setDestinationMerchantCode(String destinationMerchantCode) {
    this.destinationMerchantCode = destinationMerchantCode;
  }

  public String getDestinationMerchantName() {
    return destinationMerchantName;
  }

  public void setDestinationMerchantName(String destinationMerchantName) {
    this.destinationMerchantName = destinationMerchantName;
  }

  public String getDestinationAccountNumber() {
    return destinationAccountNumber;
  }

  public void setDestinationAccountNumber(String destinationAccountNumber) {
    this.destinationAccountNumber = destinationAccountNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getTransferAmount() {
    return transferAmount;
  }

  public void setTransferAmount(Double transferAmount) {
    this.transferAmount = transferAmount;
  }

  public String getDestinationAvailableBalance() {
    return destinationAvailableBalance;
  }

  public void setDestinationAvailableBalance(String destinationAvailableBalance) {
    this.destinationAvailableBalance = destinationAvailableBalance;
  }

  /**
   * @return the accountCloseFlag
   */
  public String getAccountCloseFlag() {
    return accountCloseFlag;
  }

  /**
   * @param accountCloseFlag the accountCloseFlag to set
   */
  public void setAccountCloseFlag(String accountCloseFlag) {
    this.accountCloseFlag = accountCloseFlag;
  }
}
