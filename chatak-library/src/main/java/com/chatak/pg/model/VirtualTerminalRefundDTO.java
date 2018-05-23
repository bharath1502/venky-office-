package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class VirtualTerminalRefundDTO extends SearchRequest {

	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String cardNum;

	private Integer cvv;

	private String zip;
	
	private Double shippingAmt;
	
	private String street;
	
	private Double txnAmount;

	private String city;
	
  private String cardHolderName;
  
  private String invoiceNumber;
  
  private String expDate;
  
  private String terminalId;
  
  private String merchantId;
  
  private Double subTotal;
  
  private Double tipAmount;
  
  private Double taxAmt;
	
	private String txnRefNum;

	private String authId;

	private String txnRefNumber;
	
	private String cgRefNumber;
	
	private String description;
	
	private Double feeAmount;
	
	private Boolean successDiv;
	
	private String cardNumMasked;
	
	private String userName;

	/**
	 * @return the txnRefNum
	 */
	public String getTxnRefNum() {
		return txnRefNum;
	}

	/**
	 * @param txnRefNum
	 *            the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}

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
	public Integer getCvv() {
		return cvv;
	}

	/**
	 * @param cvv2
	 *            the cvv2 to set
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
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

	/**
	 * @param cardHolderName
	 *            the cardHolderName to set
	 */
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	
	/**
   * @param merchantId
   *            the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

	/**
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}
	
	/**
   * @return the subTotal
   */
  public Double getSubTotal() {
    return subTotal;
  }

	/**
	 * @param terminalId
	 *            the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	/**
   * @param expDate
   *            the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

	/**
	 * @param subTotal
	 *            the subTotal to set
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	/**
	 * @return the taxAmt
	 */
	public Double getTaxAmt() {
		return taxAmt;
	}
	/**
   * @param tipAmount
   *            the tipAmount to set
   */
  public void setTipAmount(Double tipAmount) {
    this.tipAmount = tipAmount;
  }
	
	/**
   * @return the tipAmount
   */
  public Double getTipAmount() {
    return tipAmount;
  }
  /**
   * @return the txnAmount
   */
  public Double getTxnAmount() {
    return txnAmount;
  }

	/**
	 * @param taxAmt
	 *            the taxAmt to set
	 */
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	/**
   * @return the street
   */
  public String getStreet() {
    return street;
  }

	/**
	 * @return the shippingAmt
	 */
	public Double getShippingAmt() {
		return shippingAmt;
	}

	/**
	 * @param shippingAmt
	 *            the shippingAmt to set
	 */
	public void setShippingAmt(Double shippingAmt) {
		this.shippingAmt = shippingAmt;
	}

	/**
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
   * @param txnRefNumber the txnRefNumber to set
   */
  public void setTxnRefNumber(String txnRefNumber) {
    this.txnRefNumber = txnRefNumber;
  }

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	
	/**
   * @return the txnRefNumber
   */
  public String getTxnRefNumber() {
    return txnRefNumber;
  }

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/**
   * @param street
   *            the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }
  
  /**
   * @param authId
   *            the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	
	/**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the cgRefNumber
	 */
	public String getCgRefNumber() {
		return cgRefNumber;
	}

	/**
	 * @param cgRefNumber the cgRefNumber to set
	 */
	public void setCgRefNumber(String cgRefNumber) {
		this.cgRefNumber = cgRefNumber;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
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

  /**
   * @param feeAmount the feeAmount to set
   */
  public void setFeeAmount(Double feeAmount) {
    this.feeAmount = feeAmount;
  }

  public Boolean getSuccessDiv() {
    return successDiv;
  }

  public void setSuccessDiv(Boolean successDiv) {
    this.successDiv = successDiv;
  }

  public String getCardNumMasked() {
    return cardNumMasked;
  }

  public void setCardNumMasked(String cardNumMasked) {
    this.cardNumMasked = cardNumMasked;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}