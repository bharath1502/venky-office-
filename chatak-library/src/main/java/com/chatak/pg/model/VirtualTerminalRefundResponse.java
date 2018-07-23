/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalRefundResponse extends Response {
	/*
	 * authId:�"554738" txnRefNum:�"697152420616" txnAmount:�109 errorCode:�"00"
	 * errorMessage:�"Approved"
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String txnRefNum;
	private String authId;
	private String txnAmount;
	

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
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
	 * @param txnRefNum
	 *            the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
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

}
