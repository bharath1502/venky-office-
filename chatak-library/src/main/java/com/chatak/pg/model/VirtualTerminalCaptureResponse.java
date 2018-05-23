/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalCaptureResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * authId:�"581736" txnRefNum:�"426053726372" txnAmount:�99 feeAmount:�0
	 * totalAmount:�99 errorCode:�"00" errorMessage:�"Approved"
	 */
	private String txnRefNum;
	private String authId;
	private String totalAmount;
	private String feeAmount;
	private String txnAmount;
	

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}
	
	/**
   * @param feeAmount
   *            the feeAmount to set
   */
  public void setFeeAmount(String feeAmount) {
    this.feeAmount = feeAmount;
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
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return txnAmount;
	}
	
	/**
   * @return the txnRefNum
   */
  public String getTxnRefNum() {
    return txnRefNum;
  }
	
	/**
   * @param totalAmount
   *            the totalAmount to set
   */
  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
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

}
