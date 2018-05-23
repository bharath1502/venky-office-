/**
 * 
 */
package com.chatak.merchant.model;

import java.io.Serializable;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 9, 2015 11:19:00 AM
 * @version 1.0
 */
public class CardData implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String cardNumber;

  private String expDate;

  private String cvv;

  private String cardHolderName;

  private String track1;

  private String track2;

  private String track3;
  
  private String cardType;

  /**
   * @return the cardNumber
   */
  public String getCardNumber() {
    return cardNumber;
  }

  /**
   * @param cardNumber the cardNumber to set
   */
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }

  /**
   * @param expDate the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
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
   * @return the cardHolderName
   */
  public String getCardHolderName() {
    return cardHolderName;
  }

  /**
   * @param cardHolderName the cardHolderName to set
   */
  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  /**
   * @return the track1
   */
  public String getTrack1() {
    return track1;
  }

  /**
   * @param track1 the track1 to set
   */
  public void setTrack1(String track1) {
    this.track1 = track1;
  }

  /**
   * @return the track2
   */
  public String getTrack2() {
    return track2;
  }

  /**
   * @param track2 the track2 to set
   */
  public void setTrack2(String track2) {
    this.track2 = track2;
  }

  /**
   * @return the track3
   */
  public String getTrack3() {
    return track3;
  }

  /**
   * @param track3 the track3 to set
   */
  public void setTrack3(String track3) {
    this.track3 = track3;
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
  
  
  
  
}
