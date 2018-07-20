package com.chatak.merchant.model;

public class VitrualTerminalSaleRequest {
	
private String cardNum;
	

	private String expDate;
	
	private String merchantId;
	
	private String terminalId;
	
	
	private Long txnAmount;
	
	private String invoiceNumber;
	
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
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}



	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}



	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}



	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}



	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}



	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}



	/**
	 * @return the txnAmount
	 */
	public Long getTxnAmount() {
		return txnAmount;
	}



	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}



	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}



	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}




}
