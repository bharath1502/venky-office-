package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class VirtualTerminalAdjustmentDTO extends SearchRequest {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1365127764348474935L;

	private Integer cvv;
	
	private String cardNum;
	
	private String cardHolderName;
	
	private String merchantId;
	
	private String expDate;
	
	private String terminalId;
	
	private Double taxAmt;
	
	private Double subTotal;
	
	private Double tipAmount;
	
	private Double txnAmount;
	
	private Double shippingAmt;
	
	private String street;
	
	private String zip;
	
	private String invoiceNumber;
	
	private String city;
	
	private String txnRefNum;
	
	private String authId;

	/**
	 * @return the txnRefNum
	 */
	public String getTxnRefNum() {
		return txnRefNum;
	}

	/**
	 * @param txnRefNum the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}

	/**
	 * @param cardNum the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	

	/**
	 * @return the cardHolderName
	 */
	public String getCardHolderName() {
		return cardHolderName;
	}
	
	/**
   * @return the cvv2
   */
  public Integer getCvv() {
    return cvv;
}

	/**
	 * @param cardHolderName the cardHolderName to set
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
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}
	
	/**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
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
	 * @param subTotal the subTotal to set
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
   * @return the tipAmount
   */
  public Double getTipAmount() {
    return tipAmount;
  }
	
	/**
   * @param terminalId the terminalId to set
   */
  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

	/**
	 * @param taxAmt the taxAmt to set
	 */
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	/**
	 * @return the shippingAmt
	 */
	public Double getShippingAmt() {
		return shippingAmt;
	}
	
	/**
   * @param tipAmount the tipAmount to set
   */
  public void setTipAmount(Double tipAmount) {
    this.tipAmount = tipAmount;
  }

	/**
	 * @param shippingAmt the shippingAmt to set
	 */
	public void setShippingAmt(Double shippingAmt) {
		this.shippingAmt = shippingAmt;
	}
	
	/**
   * @return the street
   */
  public String getStreet() {
    return street;
  }

	/**
	 * @return the txnAmount
	 */
	public Double getTxnAmount() {
		return txnAmount;
	}
	
	/**
   * @param street the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }

	/**
	 * @param txnAmount the txnAmount to set
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
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @param authId the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @param cvv2 the cvv2 to set
	 */
	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}
	
	/**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
}