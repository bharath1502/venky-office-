package com.chatak.pg.bean;

import java.io.Serializable;

public class AdjustmentResponse extends Response implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String authId;
	private String txnRefNum;
	private Long txnAmount;
	private Long adjAmount;
	private Long feeAmount;
	private Long totalAmount;
	
	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}
	/**
   * @param txnRefNum the txnRefNum to set
   */
  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
  }
	/**
	 * @param authId the authId to set
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
   * @return the adjAmount
   */
  public Long getAdjAmount() {
    return adjAmount;
  }
	/**
	 * @return the txnAmount
	 */
	public Long getTxnAmount() {
		return txnAmount;
	}
	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}
	/**
   * @param feeAmount the feeAmount to set
   */
  public void setFeeAmount(Long feeAmount) {
    this.feeAmount = feeAmount;
  }
	/**
	 * @param adjAmount the adjAmount to set
	 */
	public void setAdjAmount(Long adjAmount) {
		this.adjAmount = adjAmount;
	}
	/**
	 * @return the feeAmount
	 */
	public Long getFeeAmount() {
		return feeAmount;
	}
	/**
	 * @return the totalAmount
	 */
	public Long getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
