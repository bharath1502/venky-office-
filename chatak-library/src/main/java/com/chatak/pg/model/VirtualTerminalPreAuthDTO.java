package com.chatak.pg.model;

public class VirtualTerminalPreAuthDTO {

	private String cardNum;

	private String expDate;
	
	private String month;
	
	private String year;
	
	private String merchantId;
	
	private String terminalId;
	
	private String invoiceNumber;
	
	private Integer cvv;
	
	private String cardHolderName;
	
	private Double subTotal;
	
	private Double taxAmt;
	
	private Double tipAmount;
	
	private Double shippingAmt;
	
	private Double txnAmount;
	
	private String street;
	
	private String city;
	
	private String zip;
	
	private String state;

	private String country;

	private String email;
	
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
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }

	/**
	 * @param cardNum the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}
	
	/**
   * @param expDate the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	/**
   * @return the invoiceNumber
   */
  public String getInvoiceNumber() {
    return invoiceNumber;
  }

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}
	
	/**
   * @param invoiceNumber the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the cvv2
	 */
	public Integer getCvv() {
		return cvv;
	}

	/**
	 * @param cvv2 the cvv2 to set
	 */
	public void setCvv(Integer cvv) {
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
	 * @return the subTotal
	 */
	public Double getSubTotal() {
		return subTotal;
	}

	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
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
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the taxAmt
	 */
	public Double getTaxAmt() {
		return taxAmt;
	}
	
	/**
   * @return the tipAmount
   */
  public Double getTipAmount() {
    return tipAmount;
  }

	/**
	 * @param taxAmt the taxAmt to set
	 */
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}
	
	/**
   * @param tipAmount the tipAmount to set
   */
  public void setTipAmount(Double tipAmount) {
    this.tipAmount = tipAmount;
  }

	/**
	 * @return the shippingAmt
	 */
	public Double getShippingAmt() {
		return shippingAmt;
	}

	/**
	 * @param shippingAmt the shippingAmt to set
	 */
	public void setShippingAmt(Double shippingAmt) {
		this.shippingAmt = shippingAmt;
	}
	
	/**
   * @return the txnAmount
   */
  public Double getTxnAmount() {
    return txnAmount;
  }

	/**
   * @return the street
   */
  public String getStreet() {
    return street;
  }
	
	/**
   * @param street the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }
  
  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	
	/**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
   * @return the feeAmount
   */
  public Double getFeeAmount() {
    return feeAmount;
  }

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

  /**
   * @return the successDiv
   */
  public Boolean getSuccessDiv() {
    return successDiv;
  }
  
  /**
   * @param feeAmount the feeAmount to set
   */
  public void setFeeAmount(Double feeAmount) {
    this.feeAmount = feeAmount;
  }

  /**
   * @param successDiv the successDiv to set
   */
  public void setSuccessDiv(Boolean successDiv) {
    this.successDiv = successDiv;
  }

}
