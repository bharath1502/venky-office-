package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class VirtualTerminalSaleDTO extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5421637642797146413L;

	private String cardNum;

	private String cvv;

	private String cardHolderName;

	private String month;

	private String year;

	private String expDate;

	private String merchantId;

	private String terminalId;

	private Double subTotal;

	private Double taxAmt;

	private Double tipAmount;

	private Double shippingAmt;

	private Double txnAmount;

	private String street;

	private String invoiceNumber;

	/* Billing data */

	private String address1;

	private String address2;

	private String state;

	private String country;

	private String email;

	private String city;

	private String zip;
	
	private String description;
	
	private Double feeAmount;
	
	private Boolean successDiv;

	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}

	/**
	 * @param cardNum
	 *            the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	/**
	 * @return the cvv2
	 */
	public String getCvv() {
		return cvv;
	}

	/**
	 * @param cvv2
	 *            the cvv2 to set
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
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }

	/**
	 * @param cardHolderName
	 *            the cardHolderName to set
	 */
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}
	
	/**
   * @param expDate
   *            the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	/**
   * @return the subTotal
   */
  public Double getSubTotal() {
    return subTotal;
  }

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}
	
	/**
   * @param subTotal
   *            the subTotal to set
   */
  public void setSubTotal(Double subTotal) {
    this.subTotal = subTotal;
  }

	/**
	 * @param terminalId
	 *            the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the taxAmt
	 */
	public Double getTaxAmt() {
		return taxAmt;
	}
	
	/**
   * @return the shippingAmt
   */
  public Double getShippingAmt() {
    return shippingAmt;
  }
	
	/**
   * @return the tipAmount
   */
  public Double getTipAmount() {
    return tipAmount;
  }

	/**
	 * @param taxAmt
	 *            the taxAmt to set
	 */
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	/**
   * @param shippingAmt
   *            the shippingAmt to set
   */
  public void setShippingAmt(Double shippingAmt) {
    this.shippingAmt = shippingAmt;
  }
	
	/**
   * @param tipAmount
   *            the tipAmount to set
   */
  public void setTipAmount(Double tipAmount) {
    this.tipAmount = tipAmount;
  }

  /**
   * @return the txnAmount
   */
  public Double getTxnAmount() {
    return txnAmount;
  }
  
  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }
	
	/**
   * @return the street
   */
  public String getStreet() {
    return street;
  }
  
  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }
	
	/**
   * @param street
   *            the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }

	/**
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}
  
  /**
   * @return the invoiceNumber
   */
  public String getInvoiceNumber() {
    return invoiceNumber;
  }

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
   * @param invoiceNumber
   *            the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }
	
	/**
   * @param zip
   *            the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
   * @param feeAmount the feeAmount to set
   */
  public void setFeeAmount(Double feeAmount) {
    this.feeAmount = feeAmount;
  }

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

  public Boolean getSuccessDiv() {
    return successDiv;
  }
  
  /**
   * @return the feeAmount
   */
  public Double getFeeAmount() {
    return feeAmount;
  }

  public void setSuccessDiv(Boolean successDiv) {
    this.successDiv = successDiv;
  }

}
