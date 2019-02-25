package com.chatak.pg.bean;

import java.io.Serializable;

public class CaptureResponse extends Response implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String authId;
	private String txnRefNum;
	private Long txnAmount;
	private Long feeAmount;
	private Long totalAmount;
	
	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
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
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}
	/**
	 * @return the feeAmount
	 */
	public Long getFeeAmount() {
		return feeAmount;
	}
	/**
	 * @param feeAmount the feeAmount to set
	 */
	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
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
