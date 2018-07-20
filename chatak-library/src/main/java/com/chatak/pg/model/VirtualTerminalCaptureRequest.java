/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalCaptureRequest {
  
	private String expDate;
	private String terminalId;
	private String merchantId;
	private String cardNum;
	private String invoiceNumber;
	private String authId;
	private Long txnAmount;
	private String authTxnRefNum;

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
	 * @param expDate
	 *            the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	
	/**
   * @param terminalId
   *            the terminalId to set
   */
  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
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
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	
	/**
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
  }

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}
	
	/**
   * @param authId
   *            the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }

	/**
	 * @param cardNum
	 *            the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	/**
   * @param authTxnRefNum
   *            the authTxnRefNum to set
   */
  public void setAuthTxnRefNum(String authTxnRefNum) {
    this.authTxnRefNum = authTxnRefNum;
  }

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}

	/**
	 * @return the authTxnRefNum
	 */
	public String getAuthTxnRefNum() {
		return authTxnRefNum;
	}
	
	/**
   * @param txnAmount
   *            the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }

}
