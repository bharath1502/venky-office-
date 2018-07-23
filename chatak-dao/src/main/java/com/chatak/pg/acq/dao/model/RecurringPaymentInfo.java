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
 * @author Girmiti Software
 *
 */
@Entity
@Table(name = "PG_RECURRING_PAYMENT_INFO")
public class RecurringPaymentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3005967141369792089L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_RECURRING_PAYMT_ID", sequenceName = "SEQ_PG_RECURR_PAYMT_INFO_ID")
	@GeneratedValue(generator = "SEQ_PG_RECURRING_PAYMT_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECURRING_PAYMENT_INFO_ID")
	private Long recurringPaymentInfoId;

	@Column(name = "RECURRING_CUSTOMER_INFO_ID")
	private Long recurringCustomerInfoId;

	@Column(name = "CREDIT_CARD_TYPE")
	private String creditCardType;

	@Column(name = "CARD_NUMBER")
	private String cardNumber;

	@Column(name = "EXP_DT")
	private String expDt;

	@Column(name = "NAME_ON_CARD")
	private String nameOnCard;

	@Column(name = "STREET_ADDRESS")
	private String streetAddress;

	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "IMMIDIATE_CHARGE")
	private String immidiateCharge;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "AMOUNT")
	private String immidateChargeAmount;
	
	
	
	

	/**
	 * @return the immidateChargeAmount
	 */
	public String getImmidateChargeAmount() {
		return immidateChargeAmount;
	}

	/**
	 * @param immidateChargeAmount the immidateChargeAmount to set
	 */
	public void setImmidateChargeAmount(String immidateChargeAmount) {
		this.immidateChargeAmount = immidateChargeAmount;
	}

	/**
	 * @return the recurringPaymentInfoId
	 */
	public Long getRecurringPaymentInfoId() {
		return recurringPaymentInfoId;
	}

	/**
	 * @param recurringPaymentInfoId the recurringPaymentInfoId to set
	 */
	public void setRecurringPaymentInfoId(Long recurringPaymentInfoId) {
		this.recurringPaymentInfoId = recurringPaymentInfoId;
	}

	/**
	 * @return the recurringCustomerInfoId
	 */
	public Long getRecurringCustomerInfoId() {
		return recurringCustomerInfoId;
	}

	/**
	 * @param recurringCustomerInfoId the recurringCustomerInfoId to set
	 */
	public void setRecurringCustomerInfoId(Long recurringCustomerInfoId) {
		this.recurringCustomerInfoId = recurringCustomerInfoId;
	}

	/**
	 * @return the creditCardType
	 */
	public String getCreditCardType() {
		return creditCardType;
	}

	/**
	 * @param creditCardType the creditCardType to set
	 */
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
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
	 * @return the expDt
	 */
	public String getExpDt() {
		return expDt;
	}

	/**
	 * @param expDt the expDt to set
	 */
	public void setExpDt(String expDt) {
		this.expDt = expDt;
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
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @param immidiateCharge the immidiateCharge to set
	 */
	public void setImmidiateCharge(String immidiateCharge) {
		this.immidiateCharge = immidiateCharge;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * @return the immidiateCharge
	 */
	public String getImmidiateCharge() {
		return immidiateCharge;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
