/**
 * 
 */
package com.chatak.pg.bean;

import java.io.Serializable;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 9, 2015 11:29:19 AM
 * @version 1.0
 */
public class BillingData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String address1;
  
  private String city;

  private String address2;

  private String country;

  private String state;

  private String email;

  private String zipCode;
  
  /**
   * @return the billerEmail
   */
  public String getEmail() {
    return email;
  }
  
  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * @param billerEmail the billerEmail to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * @param address1 the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }
  
  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @param zipCode the zipCode to set
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @return the zipCode
   */
  public String getZipCode() {
    return zipCode;
  }

  

}
