package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

/**
 * @author Girmiti Software
 *
 */
public class RecurringPaymentInfoDTO extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7438219460330146110L;

	private Long recurringPaymentInfoId;

	private Long recurringCustomerInfoId;

	private String creditCardType;

	private String cardNumber;

	private String expDt;

	private String nameOnCard;

	private String streetAddress;

	private String zipCode;

	private String immidiateCharge;
	
	private String status;

	private String month;
	
	private String year;
	
	private String immidateChargeAmount;
	
	private String merchantId;
	
	

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
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the recurringPaymentInfoId
	 */
	public Long getRecurringPaymentInfoId() {
		return recurringPaymentInfoId;
	}
	
	/**
   * @param year the year to set
   */
  public void setYear(String year) {
    this.year = year;
  }

	/**
	 * @param recurringPaymentInfoId the recurringPaymentInfoId to set
	 */
	public void setRecurringPaymentInfoId(Long recurringPaymentInfoId) {
		this.recurringPaymentInfoId = recurringPaymentInfoId;
	}
	
	/**
   * @return the year
   */
  public String getYear() {
    return year;
  }

	/**
	 * @return the recurringCustomerInfoId
	 */
	public Long getRecurringCustomerInfoId() {
		return recurringCustomerInfoId;
	}
	
  /**
   * @param creditCardType the creditCardType to set
   */
  public void setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
  }

	/**
	 * @return the creditCardType
	 */
	public String getCreditCardType() {
		return creditCardType;
	}

	/**
   * @return the expDt
   */
  public String getExpDt() {
    return expDt;
  }

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	
	/**
   * @param recurringCustomerInfoId the recurringCustomerInfoId to set
   */
  public void setRecurringCustomerInfoId(Long recurringCustomerInfoId) {
    this.recurringCustomerInfoId = recurringCustomerInfoId;
  }

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
   * @return the streetAddress
   */
  public String getStreetAddress() {
    return streetAddress;
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
   * @param streetAddress the streetAddress to set
   */
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }
  
  /**
   * @param zipCode the zipCode to set
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

	/**
	 * @param nameOnCard the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	/**
   * @param immidiateCharge the immidiateCharge to set
   */
  public void setImmidiateCharge(String immidiateCharge) {
    this.immidiateCharge = immidiateCharge;
  }

  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

	/**
	 * @return the immidiateCharge
	 */
	public String getImmidiateCharge() {
		return immidiateCharge;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the mechantId
   */
 		
}
