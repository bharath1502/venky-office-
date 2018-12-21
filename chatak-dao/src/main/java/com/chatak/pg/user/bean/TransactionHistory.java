/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;

/**
 * @Author: Girmiti Software
 * @Date: 18-Dec-2018
 * @Time: 4:36:43 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class TransactionHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorCode;

	private String errorMessage;

	private String deviceLocalTxnTime;

	private String txnRefNumber;

	private String authId;

	private String address;

	private String city;

	private String state;

	private String country;

	private String pin;

	private String txnDateTime;

	private String cgRefNumber;

	private String merchantCode;

	private String maskedCardNumber;

	private String expDate;

	private String merchantName;

	private String transactionType;

	private String tipAmount;

	private String totalAmount;

	private String cardHolderName;

	private String transactionfee;

	private double transactionamount;

	private String status;

	private String transactionId;

	private String terminalId;

	private String entrymode;

	private String invoiceno;

	private String processorTxnId;

	public String getTerminalId() {
		return terminalId;
	}

	public String getEntrymode() {
		return entrymode;
	}

	public String getProcessorTxnId() {
		return processorTxnId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public void setEntrymode(String entrymode) {
		this.entrymode = entrymode;
	}

	public void setProcessorTxnId(String processorTxnId) {
		this.processorTxnId = processorTxnId;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getDeviceLocalTxnTime() {
		return deviceLocalTxnTime;
	}

	public String getTxnRefNumber() {
		return txnRefNumber;
	}

	public String getAuthId() {
		return authId;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getPin() {
		return pin;
	}

	public String getTxnDateTime() {
		return txnDateTime;
	}

	public String getCgRefNumber() {
		return cgRefNumber;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public String getMaskedCardNumber() {
		return maskedCardNumber;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getTipAmount() {
		return tipAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public String getTransactionfee() {
		return transactionfee;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setDeviceLocalTxnTime(String deviceLocalTxnTime) {
		this.deviceLocalTxnTime = deviceLocalTxnTime;
	}

	public void setTxnRefNumber(String txnRefNumber) {
		this.txnRefNumber = txnRefNumber;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setTxnDateTime(String txnDateTime) {
		this.txnDateTime = txnDateTime;
	}

	public void setCgRefNumber(String cgRefNumber) {
		this.cgRefNumber = cgRefNumber;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public void setMaskedCardNumber(String maskedCardNumber) {
		this.maskedCardNumber = maskedCardNumber;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public void setTransactionfee(String transactionfee) {
		this.transactionfee = transactionfee;
	}

	public double getTransactionamount() {
		return transactionamount;
	}

	public void setTransactionamount(double transactionamount) {
		this.transactionamount = transactionamount;
	}
}
