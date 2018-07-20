package com.chatak.pg.bean;

import java.io.Serializable;

public class CheckBeneficiary implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -3342375029334396807L;
  String beneficiaryName;
  String address1;
  String address2;
  String city;
  String state;
  String zip;
  String country;
  public String getAddress1() {
    return address1;
  }
  public String getBeneficiaryName() {
    return beneficiaryName;
  }
  public String getAddress2() {
    return address2;
  }
  public void setBeneficiaryName(String beneficiaryName) {
    this.beneficiaryName = beneficiaryName;
  }
  public String getCity() {
    return city;
  }
  public void setAddress1(String address1) {
    this.address1 = address1;
  }
  public String getState() {
    return state;
  }
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public void setState(String state) {
    this.state = state;
  }
  public void setZip(String zip) {
    this.zip = zip;
  }
  public String getCountry() {
    return country;
  }
  public String getZip() {
    return zip;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  

}
