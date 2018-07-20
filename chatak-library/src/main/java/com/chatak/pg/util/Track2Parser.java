package com.chatak.pg.util;


public class Track2Parser {
  /**
   * <<Method to parse track2date to give cardNumber and expDate>>
   * 
   * @param track2data
   * @throws Exception
   */
  public Track2Parser(String track2data) {
    this.cardNumber = track2data.substring(1, track2data.indexOf('='));
    this.expDate = track2data.substring(track2data.indexOf('=') + 1, track2data.indexOf('=') + Integer.parseInt("5"));

  }

  private String cardNumber;

  private String expDate;

  /**
   * @return the cardNumber
   */
  public String getCardNumber() {
    return cardNumber;
  }

  /**
   * @param cardNumber
   *          the cardNumber to set
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
   * @param expDate
   *          the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

}
