package com.chatak.pg.bean;

import java.io.Serializable;

public class CardToken implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 8833602466122869560L;
  
  private String token;
  
  private String cardLastFourDigit;
  
  private String cardType;
  
  private String cvv;
  
  private String email;

  /**
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * @param token the token to set
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * @return the cardLastFourDigit
   */
  public String getCardLastFourDigit() {
    return cardLastFourDigit;
  }

  /**
   * @param cardLastFourDigit the cardLastFourDigit to set
   */
  public void setCardLastFourDigit(String cardLastFourDigit) {
    this.cardLastFourDigit = cardLastFourDigit;
  }

  /**
   * @return the cardType
   */
  public String getCardType() {
    return cardType;
  }

  /**
   * @param cardType the cardType to set
   */
  public void setCardType(String cardType) {
    this.cardType = cardType;
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
  

}
