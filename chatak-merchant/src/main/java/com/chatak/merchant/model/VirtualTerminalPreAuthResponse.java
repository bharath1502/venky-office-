/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.merchant.model;

/**
 * 
 *
 */
public class VirtualTerminalPreAuthResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authId;
	private String txnAmount;
	private String feeAmount;
	private String txnRefNum;
	private String totalAmount;

	/**
	 * @return the txnRefNum
	 */
	public String getTxnRefNum() {
		return txnRefNum;
	}
	
	/**
	 * @param feeAmount
	 *            the feeAmount to set
	 */
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	/**
	 * @param txnRefNum
	 *            the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}

	/**
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return txnAmount;
	}
	
	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the feeAmount
	 */
	public String getFeeAmount() {
		return feeAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}
	
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

}
