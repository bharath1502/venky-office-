package com.chatak.acquirer.admin.model;

import java.math.BigInteger;
import java.util.List;

public class IsoSettlementDetails {

	private String isoName;

	private String bankName;

	private String bankAccNum;

	private String routingNumber;

	private String amount;
	
	private String currency;
	
	private List<String> pGTransactionIds;

	/**
	 * @return the isoName
	 */
	public String getIsoName() {
		return isoName;
	}

	/**
	 * @param isoName
	 *            the isoName to set
	 */
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankAccNum
	 */
	public String getBankAccNum() {
		return bankAccNum;
	}

	/**
	 * @param bankAccNum
	 *            the bankAccNum to set
	 */
	public void setBankAccNum(String bankAccNum) {
		this.bankAccNum = bankAccNum;
	}

	/**
	 * @return the routingNumber
	 */
	public String getRoutingNumber() {
		return routingNumber;
	}

	/**
	 * @param routingNumber
	 *            the routingNumber to set
	 */
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the pGTransactionIds
	 */
	public List<String> getpGTransactionIds() {
		return pGTransactionIds;
	}

	/**
	 * @param pGTransactionIds the pGTransactionIds to set
	 */
	public void setpGTransactionIds(List<String> pGTransactionIds) {
		this.pGTransactionIds = pGTransactionIds;
	}

}
