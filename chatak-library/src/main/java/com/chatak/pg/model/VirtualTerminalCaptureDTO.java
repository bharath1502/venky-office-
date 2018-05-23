package com.chatak.pg.model;

/**
 * @author chetan.narayan
 *
 */
public class VirtualTerminalCaptureDTO {

	private String cardNum;
	
	private String cardHolderName;

	private Integer cvv;

	private String expDate;
	
	private String terminalId;

	private String merchantId;

	private Double subTotal;
	
	private Double tipAmount;

	private Double taxAmt;

	private Double shippingAmt;
	
	private String street;

	private Double txnAmount;

	private String city;

	private String invoiceNumber;
	
	private String zip;

	private String authId;
	
	private String txnRefNumber;

	private String txnRefNum;
	
	private String cgRefNumber;
	
	private Double feeAmount;
	
	private String description;
	
	private String cardNumMasked;
	
	private Boolean successDiv;
	
	/**
	 * @return the txnRefNum
	 */
	public String getTxnRefNum() {
		return txnRefNum;
	}
	
	/**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }

	/**
	 * @param txnRefNum the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}

	/**
	 * @param authId the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}
	
	/**
   * @param cardHolderName the cardHolderName to set
   */
  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

	/**
	 * @param cardNum the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	/**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }

	/**
	 * @return the cvv2
	 */
	public Integer getCvv() {
		return cvv;
	}
	
	/**
   * @return the cardHolderName
   */
  public String getCardHolderName() {
    return cardHolderName;
  }
  
  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

	/**
	 * @param cvv2 the cvv2 to set
	 */
	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	/**
   * @return the terminalId
   */
  public String getTerminalId() {
    return terminalId;
  }

  /**
   * @return the taxAmt
   */
  public Double getTaxAmt() {
    return taxAmt;
  }

	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	/**
   * @return the txnAmount
   */
  public Double getTxnAmount() {
    return txnAmount;
  }

	/**
	 * @param merchantId the merchantId to set
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
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
   * @return the shippingAmt
   */
  public Double getShippingAmt() {
    return shippingAmt;
  }

	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	/**
   * @param street the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }

	/**
	 * @param taxAmt the taxAmt to set
	 */
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	/**
	 * @return the tipAmount
	 */
	public Double getTipAmount() {
		return tipAmount;
	}

	/**
	 * @param tipAmount the tipAmount to set
	 */
	public void setTipAmount(Double tipAmount) {
		this.tipAmount = tipAmount;
	}

	/**
   * @return the street
   */
  public String getStreet() {
    return street;
  }

	/**
	 * @param shippingAmt the shippingAmt to set
	 */
	public void setShippingAmt(Double shippingAmt) {
		this.shippingAmt = shippingAmt;
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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
   * @return the txnRefNumber
   */
  public String getTxnRefNumber() {
    return txnRefNumber;
  }

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
   * @return the invoiceNumber
   */
  public String getInvoiceNumber() {
    return invoiceNumber;
  }

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
   * @param cgRefNumber the cgRefNumber to set
   */
  public void setCgRefNumber(String cgRefNumber) {
    this.cgRefNumber = cgRefNumber;
  }

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @param txnRefNumber the txnRefNumber to set
	 */
	public void setTxnRefNumber(String txnRefNumber) {
		this.txnRefNumber = txnRefNumber;
	}

	/**
   * @return the successDiv
   */
  public Boolean getSuccessDiv() {
    return successDiv;
  }

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
   * @return the cgRefNumber
   */
  public String getCgRefNumber() {
    return cgRefNumber;
  }

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

  /**
   * @return the feeAmount
   */
  public Double getFeeAmount() {
    return feeAmount;
  }
  
  public void setCardNumMasked(String cardNumMasked) {
    this.cardNumMasked = cardNumMasked;
  }

  /**
   * @param feeAmount the feeAmount to set
   */
  public void setFeeAmount(Double feeAmount) {
    this.feeAmount = feeAmount;
  }

  public String getCardNumMasked() {
    return cardNumMasked;
  }

  /**
   * @param successDiv the successDiv to set
   */
  public void setSuccessDiv(Boolean successDiv) {
    this.successDiv = successDiv;
  }
	
}
