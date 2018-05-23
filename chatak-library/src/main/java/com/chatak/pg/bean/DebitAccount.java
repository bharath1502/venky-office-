package com.chatak.pg.bean;

import java.io.Serializable;

public class DebitAccount implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = -7949918383619937941L;
  private Long accountNumber;
  private String accountType;
  private String avaliableBalance;
  private String accountTypeValue;
  public String getAccountType() {
    return accountType;
  }
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  public Long getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String getAvaliableBalance() {
    return avaliableBalance;
  }
  public void setAvaliableBalance(String avaliableBalance) {
    this.avaliableBalance = avaliableBalance;
  }
  public String getAccountTypeValue() {
    return accountTypeValue;
  }
  public void setAccountTypeValue(String accountTypeValue) {
    this.accountTypeValue = accountTypeValue;
  }

}
