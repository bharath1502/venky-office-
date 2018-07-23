package com.chatak.pg.bean;

import java.io.Serializable;

public class CreditAccount implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 5512656732133240346L;
  private String accountNumber;
  private String bankName;
  private String address;
  private String city;
  private String state;
  private String accountType;
  private String bankRoutingNumber;
  private String nameOnAcccount;
  private String accountTypeValue;
  public String getAccountNumber() {
    return accountNumber;
  }
  public String getAddress() {
    return address;
  }
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getBankName() {
    return bankName;
  }
  public String getState() {
    return state;
  }
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getCity() {
    return city;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getAccountType() {
    return accountType;
  }
  public String getBankRoutingNumber() {
    return bankRoutingNumber;
  }
  public String getNameOnAcccount() {
    return nameOnAcccount;
  }
  public void setNameOnAcccount(String nameOnAcccount) {
    this.nameOnAcccount = nameOnAcccount;
  }
  public String getAccountTypeValue() {
    return accountTypeValue;
  }
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  public void setAccountTypeValue(String accountTypeValue) {
    this.accountTypeValue = accountTypeValue;
  }
  public void setBankRoutingNumber(String bankRoutingNumber) {
    this.bankRoutingNumber = bankRoutingNumber;
  }

}
