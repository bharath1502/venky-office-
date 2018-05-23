/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalVoidRequest {
  
  private String txnRefNum;
	private String expDate;
	private String terminalId;
	private String merchantId;
	private String authId;
	private String invoiceNumber;
	private String cardNum;
	
	/**
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}
	
	/**
   * @param authId
   *            the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
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
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
   * @param merchantId
   *            the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }
  
  /**
   * @param terminalId
   *            the terminalId to set
   */
  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
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
   * @param invoiceNumber
   *            the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
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
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }

	/**
	 * @param cardNum
	 *            the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

}
