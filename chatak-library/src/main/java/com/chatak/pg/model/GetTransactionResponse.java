package com.chatak.pg.model;

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

	private Double tipAmount;
	
	private String cvv2;
	
	private Double txnAmount;
	
	private String street;

	private Double shippingAmt;

	private String zip;
	
	private String cardNum;

  private String cardHolderName;
  
  private String expDate;

  private String merchantId;
  
  private String terminalId;
  
  private Double subTotal;
  
  private Double taxAmt;

	private String city;

	private String invoiceNumber;
	
	private String txnRefNum;

	private String authId;
	
	private String cgRefNumber;
	
	private Double feeAmount;
	
	private String settlementStatus;
	
	private Double totalRefundableBalance;
	
	private String cardNumMasked;

	/**
	 * @return the cvv2
	 */
	public String getCvv2() {
		return cvv2;
	}
	
	/**
   * @param cardNum
   *            the cardNum to set
   */
  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

	/**
	 * @param cvv2
	 *            the cvv2 to set
	 */
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	
	/**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }
	
	/**
   * @return the cardNum
   */
  public String getCardNum() {
    return cardNum;
  }

	/**
	 * @return the cardHolderName
	 */
	public String getCardHolderName() {
		return cardHolderName;
	}
	
	/**
   * @param expDate
   *            the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
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
   * @return the subTotal
   */
  public Double getSubTotal() {
    return subTotal;
  }

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	/**
   * @return the taxAmt
   */
  public Double getTaxAmt() {
    return taxAmt;
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
	 * @return the shippingAmt
	 */
	public Double getShippingAmt() {
		return shippingAmt;
	}
	
	/**
   * @param txnAmount
   *            the txnAmount to set
   */
  public void setTxnAmount(Double txnAmount) {
    this.txnAmount = txnAmount;
  }

	/**
	 * @param shippingAmt
	 *            the shippingAmt to set
	 */
	public void setShippingAmt(Double shippingAmt) {
		this.shippingAmt = shippingAmt;
	}

	/**
   * @return the city
   */
  public String getCity() {
    return city;
  }

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	
	/**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
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
   * @param zip
   *            the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}
	
	/**
   * @return the cgRefNumber
   */
  public String getCgRefNumber() {
    return cgRefNumber;
  }

	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @param totalRefundableBalance the totalRefundableBalance to set
	 */
	public void setTotalRefundableBalance(Double totalRefundableBalance) {
	  this.totalRefundableBalance = totalRefundableBalance;
	}

  /**
   * @return the settlementStatus
   */
  public String getSettlementStatus() {
    return settlementStatus;
  }
  
  /**
   * @param cgRefNumber the cgRefNumber to set
   */
  public void setCgRefNumber(String cgRefNumber) {
    this.cgRefNumber = cgRefNumber;
  }

  /**
   * @param settlementStatus the settlementStatus to set
   */
  public void setSettlementStatus(String settlementStatus) {
    this.settlementStatus = settlementStatus;
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
   * @return the totalRefundableBalance
   */
  public Double getTotalRefundableBalance() {
    return totalRefundableBalance;
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

}
