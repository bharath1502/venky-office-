/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.merchant.model;

/**
 * 
 *
 */
public class VirtualTerminalRefundResponse extends Response {

	private String authId;
	private String txnRefNum;
	private String txnAmount;

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}
	
	/**
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @param txnRefNum
	 *            the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}
	
	/**
	 * @return the txnRefNum
	 */
	public String getTxnRefNum() {
		return txnRefNum;
	}

	/**
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return txnAmount;
	}

}
