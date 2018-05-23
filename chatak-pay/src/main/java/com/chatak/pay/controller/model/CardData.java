/**
 * 
 */
package com.chatak.pay.controller.model;

import java.io.Serializable;

import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 9, 2015 11:19:00 AM
 * @version 1.0
 */
public class CardData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String cardNumber;

  private String expDate;

  private String cvv;

  private String cardHolderName;

  private MethodOfPaymentTypeEnum cardType;

  private String track1;

  private String track2;

  private String track3;

  private String track;

  private String keySerial;

  private String cardHolderEmail;

  private String emv;

  private String uid;

  public String getCardNumber() {
    return cardNumber;
  }

  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }
  
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getExpDate() {
    return expDate;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public String getTrack3() {
    return track3;
  }

  public String getTrack1() {
    return track1;
  }

  public void setTrack1(String track1) {
    this.track1 = track1;
  }
  
  public String getCardHolderName() {
    return cardHolderName;
  }

  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  public String getTrack2() {
    return track2;
  }

  public void setTrack2(String track2) {
    this.track2 = track2;
  }

  public void setTrack3(String track3) {
    this.track3 = track3;
  }

  public MethodOfPaymentTypeEnum getCardType() {
    return cardType;
  }

  public void setCardType(MethodOfPaymentTypeEnum cardType) {
    this.cardType = cardType;
  }

  public String getTrack() {
    return track;
  }

  public void setTrack(String track) {
    this.track = track;
  }

  public String getKeySerial() {
    return keySerial;
  }

  public void setKeySerial(String keySerial) {
    this.keySerial = keySerial;
  }

  public String getCardHolderEmail() {
    return cardHolderEmail;
  }

  public void setCardHolderEmail(String cardHolderEmail) {
    this.cardHolderEmail = cardHolderEmail;
  }

  public String getEmv() {
    return emv;
  }

  public void setEmv(String emv) {
    this.emv = emv;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

}
