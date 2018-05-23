/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VitrualTerminalSaleResponse extends Response {
	/*
	 * authId:�"556514" txnRefNum:�"553515545257" txnAmount:�99 feeAmount:�0
	 * totalAmount:�99 errorCode:�"00" errorMessage:�"Approved"
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String txnRefNum;
	private String authId;
	private String txnAmount;
	private String totalAmount;
	private String feeAmount;

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
	 * @param feeAmount
	 *            the feeAmount to set
	 */
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	/**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
  
  /**
   * @return the totalAmount
   */
  public String getTotalAmount() {
    return totalAmount;
  }

  /**
   * @param authId
   *            the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }

  /**
   * @return the feeAmount
   */
  public String getFeeAmount() {
    return feeAmount;
  }

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


}
