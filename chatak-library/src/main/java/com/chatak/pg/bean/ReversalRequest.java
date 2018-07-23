package com.chatak.pg.bean;

import java.io.Serializable;

public class ReversalRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String txnRefNum;//Transaction Id of Auth transaction
	private String authId;//Auht Id of Auth transaction
	private String issuerTxnRefNum;
	
	
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
 * @return the issuerTxnRefNum
 */
public String getIssuerTxnRefNum() {
	return issuerTxnRefNum;
}
/**
 * @param issuerTxnRefNum the issuerTxnRefNum to set
 */
public void setIssuerTxnRefNum(String issuerTxnRefNum) {
	this.issuerTxnRefNum = issuerTxnRefNum;
}
  
	
}
