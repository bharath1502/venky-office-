package com.chatak.pay.controller.model;

public class TransactionDetails {
	
	private String merchantCode;
	
	private String terminalId;
	
	private String timeZoneOffset;
	
	private String timeZoneRegion;
	
	private String transactiontype;
	
	private String invoiceNumber;
	
	private String orderId;
	
	private String transactionData;
	
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getTimeZoneOffset() {
		return timeZoneOffset;
	}
	public void setTimeZoneOffset(String timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}
	public String getTimeZoneRegion() {
		return timeZoneRegion;
	}
	public void setTimeZoneRegion(String timeZoneRegion) {
		this.timeZoneRegion = timeZoneRegion;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTransactionData() {
		return transactionData;
	}
	public void setTransactionData(String transactionData) {
		this.transactionData = transactionData;
	}
	

}
