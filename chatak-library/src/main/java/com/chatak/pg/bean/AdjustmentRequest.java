package com.chatak.pg.bean;

import java.io.Serializable;

public class AdjustmentRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long adjAmount;//adjustment amount
	private String authId;
	private String txnRefNum;
	
	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}
	/**
   * @return the adjAmount
   */
  public Long getAdjAmount() {
    return adjAmount;
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
   * @param adjAmount the adjAmount to set
   */
  public void setAdjAmount(Long adjAmount) {
    this.adjAmount = adjAmount;
  }
	/**
	 * @param txnRefNum the txnRefNum to set
	 */
	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}
	
}
