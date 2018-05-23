/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Apr 29, 2015 9:04:59 PM
 * @version 1.0
 */
@Entity
@Table(name = "PG_CARD_TOKEN_DETAILS")
public class PGCardTokenDetails implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8476850545851411218L;

  /*@SequenceGenerator(name = "PG_CARD_TOKEN_SEQ_ID", sequenceName = "SEQ_PG_CARD_TOKEN_DETAILS")
  @GeneratedValue(generator = "PG_CARD_TOKEN_SEQ_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "CARD_USER_EMAIL")
  private String cardUserEmail;

  @Column(name = "CARD_TOKEN")
  private String token;

  @Column(name = "CARD_TOKEN_EXP_DATE")
  private String tokenExpDate;

  @Column(name = "CARD_LAST_FOUR")
  private String cardLastFourDigits;

  @Column(name = "CARD_TYPE")
  private String cardType;
  
  @Column(name = "PG_TOKEN_CUSTOMER_ID")
  private Long pgTokenCustomerId;
  
  @Column(name = "PAN")
  private String pan;

  @Column(name = "EXPIRY_DATE")
  private String expiryDate;

  @Column(name = "CARD_HOLDER_NAME")
  private String cardHolderName;
  
  @Column(name = "STATUS")
  private String status;

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate
   *          the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the cardUserEmail
   */
  public String getCardUserEmail() {
    return cardUserEmail;
  }

  /**
   * @param cardUserEmail
   *          the cardUserEmail to set
   */
  public void setCardUserEmail(String cardUserEmail) {
    this.cardUserEmail = cardUserEmail;
  }

  /**
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * @param token
   *          the token to set
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * @return the tokenExpDate
   */
  public String getTokenExpDate() {
    return tokenExpDate;
  }

  /**
   * @param tokenExpDate
   *          the tokenExpDate to set
   */
  public void setTokenExpDate(String tokenExpDate) {
    this.tokenExpDate = tokenExpDate;
  }

  /**
   * @return the cardLastFourDigits
   */
  public String getCardLastFourDigits() {
    return cardLastFourDigits;
  }

  /**
   * @param cardLastFourDigits
   *          the cardLastFourDigits to set
   */
  public void setCardLastFourDigits(String cardLastFourDigits) {
    this.cardLastFourDigits = cardLastFourDigits;
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
 * @return the pgTokenCustomerId
 */
public Long getPgTokenCustomerId() {
	return pgTokenCustomerId;
}

/**
 * @param pgTokenCustomerId the pgTokenCustomerId to set
 */
public void setPgTokenCustomerId(Long pgTokenCustomerId) {
	this.pgTokenCustomerId = pgTokenCustomerId;
}

/**
 * @return the pan
 */
public String getPan() {
	return pan;
}

/**
 * @param pan the pan to set
 */
public void setPan(String pan) {
	this.pan = pan;
}

/**
 * @return the expiryDate
 */
public String getExpiryDate() {
	return expiryDate;
}

/**
 * @param expiryDate the expiryDate to set
 */
public void setExpiryDate(String expiryDate) {
	this.expiryDate = expiryDate;
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

public String getStatus() {
  return status;
}

public void setStatus(String status) {
  this.status = status;
}

}
