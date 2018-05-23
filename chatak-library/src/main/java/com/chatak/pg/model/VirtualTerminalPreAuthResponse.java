/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalPreAuthResponse extends Response {
	/*
	 * authId:�"581736" txnRefNum:�"441217506616" txnAmount:�99 feeAmount:�0
	 * totalAmount:�99 errorCode:�"00" errorMessage:�"Approved"
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String txnRefNum;
	private String feeAmount;
	private String txnAmount;
	private String authId;
	private String totalAmount;

	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
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
	
	/**
   * @return the feeAmount
   */
  public String getFeeAmount() {
    return feeAmount;
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
   * @param txnRefNum
   *            the txnRefNum to set
   */
  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
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
