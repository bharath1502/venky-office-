/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.merchant.model;

/**
 * 
 *
 */
public class VirtualTerminalAdjustmentResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authId;
	private String txnRefNum;
	private String txnAmount;
	private String adjAmount;
	private String feeAmount;
	private String totalAmount;

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}
	
	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @return the adjAmount
	 */
	public String getAdjAmount() {
		return adjAmount;
	}
	
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
	 * @param adjAmount
	 *            the adjAmount to set
	 */
	public void setAdjAmount(String adjAmount) {
		this.adjAmount = adjAmount;
	}

	/**
	 * @return the feeAmount
	 */
	public String getFeeAmount() {
		return feeAmount;
	}
	
	/**
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return txnAmount;
	}

	/**
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @param feeAmount
	 *            the feeAmount to set
	 */
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

}
