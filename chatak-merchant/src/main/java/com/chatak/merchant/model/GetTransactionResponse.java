package com.chatak.merchant.model;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 18-Mar-2015 3:12:07 PM
 * @version 1.0
 */
public class GetTransactionResponse extends Response {

	/**
   * 
   */

  private static final Long serialVersionUID = -4329374109275794619L;

	private String cardNum;

	private String cvv2;

	private String cardHolderName;

	private String expDate;

	private String merchantId;

	private String terminalId;

	private Double subTotal;

	private Double taxAmt;

	private Double tipAmount;

	private Double shippingAmt;

	private Double txnAmount;

	private String street;

	private String city;

	private String zip;

	private String invoiceNumber;

	private String authId;
	
	private String txnRefNum;
	
	private String cgRefNumber;
	
	private String settlementStatus;
	
	private Double feeAmount;
	
	private String cardNumMasked;

	private Double totalRefundableBalance;

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
	public String getCvv2() {
		return cvv2;
	}

	/**
	 * @param cvv2
	 *            the cvv2 to set
	 */
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	/**
	 * @return the cardHolderName
	 */
	public String getCardHolderName() {
		return cardHolderName;
	}

	/**
	 * @param cardHolderName
	 *            the cardHolderName to set
	 */
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
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
	 * @param terminalId
	 *            the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the subTotal
	 */
	public Double getSubTotal() {
		return subTotal;
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
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	/**
	 * @param expDate
	 *            the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	/**
	 * @param taxAmt
	 *            the taxAmt to set
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
	 * @param tipAmount
	 *            the tipAmount to set
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
	 * @param shippingAmt
	 *            the shippingAmt to set
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
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street
	 *            the street to set
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
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

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
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}

	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
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
   * @return the settlementStatus
   */
  public String getSettlementStatus() {
    return settlementStatus;
  }

  /**
   * @param settlementStatus the settlementStatus to set
   */
  public void setSettlementStatus(String settlementStatus) {
    this.settlementStatus = settlementStatus;
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

public String getCardNumMasked() {
	return cardNumMasked;
}

public void setCardNumMasked(String cardNumMasked) {
	this.cardNumMasked = cardNumMasked;
}

/**
 * @return the totalRefundableBalance
 */
public Double getTotalRefundableBalance() {
	return totalRefundableBalance;
}

/**
 * @param totalRefundableBalance the totalRefundableBalance to set
 */
public void setTotalRefundableBalance(Double totalRefundableBalance) {
	this.totalRefundableBalance = totalRefundableBalance;
}


}
