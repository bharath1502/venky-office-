package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_TXN_CARD_INFO")
public class PGTxnCardInfo implements Serializable{
	
	/**
   * 
   */
  private static final long serialVersionUID = -5401063240476685797L;

  @Id
  @Column(name = "ID")
  private Long id;
	
  @Column(name = "TRANSACTION_ID")
	private Long transactionId;
	
  @Column(name = "CARD_NO")
	//encrypted card number
	private String cardNumber;
	
  @Column(name = "LAST_FOUR")
	private String lastFour;
	
  @Column(name = "CARD_NO_LENGTH")
	private Integer cardNumLength;
	
  @Column(name = "EXP_DATE")
	private String expirationDate;
	
  @Column(name = "NAME_ON_CARD")
	private String nameOnCard;
	
  @Column(name = "ADDRESS")
	private String address;
	
  @Column(name = "AVS_ZIP")
	private Integer avsZip;
	
  @Column(name = "TRACK2")
	private String track2;
	
  @Column(name = "CARD_ACC_TYPE")
	private String cardAccType;

  @Column(name = "EMV")
  private String emv;
  
  @Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	 * @return the cardNumLength
	 */
	public Integer getCardNumLength() {
		return cardNumLength;
	}

	/**
	 * @param cardNumLength the cardNumLength to set
	 */
	public void setCardNumLength(Integer cardNumLength) {
		this.cardNumLength = cardNumLength;
	}

	/**
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * @param nameOnCard the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the avsZip
	 */
	public Integer getAvsZip() {
		return avsZip;
	}

	/**
	 * @param avsZip the avsZip to set
	 */
	public void setAvsZip(Integer avsZip) {
		this.avsZip = avsZip;
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
	 * @return the cardAccType
	 */
	public String getCardAccType() {
		return cardAccType;
	}

	/**
	 * @param cardAccType the cardAccType to set
	 */
	public void setCardAccType(String cardAccType) {
		this.cardAccType = cardAccType;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the lastFour
	 */
	public String getLastFour() {
		return lastFour;
	}

	/**
	 * @param lastFour the lastFour to set
	 */
	public void setLastFour(String lastFour) {
		this.lastFour = lastFour;
	}

	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

  /**
   * @return the emv
   */
  public String getEmv() {
    return emv;
  }

  /**
   * @param emv the emv to set
   */
  public void setEmv(String emv) {
    this.emv = emv;
  }
	
	
	

}
