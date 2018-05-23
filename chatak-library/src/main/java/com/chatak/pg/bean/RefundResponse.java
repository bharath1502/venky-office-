package com.chatak.pg.bean;

import java.io.Serializable;

public class RefundResponse extends Response implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String authId;
	private String txnRefNum;
	private Long txnAmount;
	private Long feeAmount;
	private Long totalTxnAmount;
	
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
	 * @param authId the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	/**
	 * @param txnRefNum the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}
	/**
	 * @return the txnAmount
	 */
	public Long getTxnAmount() {
		return txnAmount;
	}
	/**
	 * @return the feeAmount
	 */
	public Long getFeeAmount() {
		return feeAmount;
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
  public Long getTotalTxnAmount() {
    return totalTxnAmount;
  }
  public void setTotalTxnAmount(Long totalTxnAmount) {
    this.totalTxnAmount = totalTxnAmount;
  }
	
	
	
}
