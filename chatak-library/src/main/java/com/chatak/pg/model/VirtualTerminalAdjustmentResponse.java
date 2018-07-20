/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalAdjustmentResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * authId:�"556514" txnRefNum:�"313362711039" txnAmount:�109 adjAmount:�10
	 * feeAmount:�0 totalAmount:�109 errorCode:�"00" errorMessage:�"Approved"
	 */
	private String txnRefNum;
	private String authId;
	private String txnAmount;
	private String feeAmount;
	private String adjAmount;
	private String totalAmount;

	/**
	 * @return the txnRefNum
	 */
	public String getTxnRefNum() {
		return txnRefNum;
	}

	/**
	 * @return the adjAmount
	 */
	public String getAdjAmount() {
		return adjAmount;
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
   * @param authId
   *            the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
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
