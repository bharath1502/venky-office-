package com.chatak.pg.bean;

import java.io.Serializable;

public class VoidResponse extends Response implements Serializable {

	private String authId;
	private String txnRefNum;
	private Double txnAmount;
	private Double feeAmount;
	
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
	public Double getTxnAmount() {
		return txnAmount;
	}
	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}
	/**
	 * @return the feeAmount
	 */
	public Double getFeeAmount() {
		return feeAmount;
	}
	/**
	 * @param feeAmount the feeAmount to set
	 */
	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	
	
}
