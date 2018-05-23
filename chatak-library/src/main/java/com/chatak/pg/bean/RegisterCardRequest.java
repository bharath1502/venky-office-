package com.chatak.pg.bean;

import java.io.Serializable;

import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.model.CardData;

public class RegisterCardRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1413373030672464522L;

  private String userId;

  private String password;

  private CardData cardData;

  private String email;

  private EntryModeEnum entryMode;

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the cardData
   */
  public CardData getCardData() {
    return cardData;
  }

  /**
   * @param cardData
   *          the cardData to set
   */
  public void setCardData(CardData cardData) {
    this.cardData = cardData;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public EntryModeEnum getEntryMode() {
    return entryMode;
  }

  public void setEntryMode(EntryModeEnum entryMode) {
    this.entryMode = entryMode;
  }

}
