/**
 * 
 */
package com.chatak.pay.controller.model;

import java.io.Serializable;

import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 19-Feb-2015 11:38:42 AM
 * @version 1.0
 */
public class CardDetails implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4421259613253026078L;
  
  private String number;
  
  private String expMonthYear;
  
  private String cvv;
  
  private MethodOfPaymentTypeEnum type;
  
  private String requestId;
  
  private String name;
  
  private String saveCard;
  
  private String email;
  

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the number
   */
  public String getNumber() {
    return number;
  }

  /**
   * @param number the number to set
   */
  public void setNumber(String number) {
    this.number = number;
  }

  /**
   * @return the expMonthYear
   */
  public String getExpMonthYear() {
    return expMonthYear;
  }

  /**
   * @param expMonthYear the expMonthYear to set
   */
  public void setExpMonthYear(String expMonthYear) {
    this.expMonthYear = expMonthYear;
  }

  /**
   * @return the cvv
   */
  public String getCvv() {
    return cvv;
  }

  /**
   * @param cvv the cvv to set
   */
  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  /**
   * @return the type
   */
  public MethodOfPaymentTypeEnum getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(MethodOfPaymentTypeEnum type) {
    this.type = type;
  }

  /**
   * @return the requestId
   */
  public String getRequestId() {
    return requestId;
  }

  /**
   * @param requestId the requestId to set
   */
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the saveCard
   */
  public String getSaveCard() {
    return saveCard;
  }

  /**
   * @param saveCard the saveCard to set
   */
  public void setSaveCard(String saveCard) {
    this.saveCard = saveCard;
  }

  

}
