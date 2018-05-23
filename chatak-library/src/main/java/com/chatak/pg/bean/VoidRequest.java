package com.chatak.pg.bean;

import java.io.Serializable;

import com.chatak.pg.emv.util.EMVData;

public class VoidRequest extends Request implements Serializable {

	private String txnRefNum;//Transaction Id of sale/refund transaction
	private String authId;//Auht Id of Auth transaction
	private Long txnAmount;//Transaction amount 
	private EMVData emvData;
	private String issuerTxnRefNum;
	
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
   * @return the emvData
   */
  public EMVData getEmvData() {
    return emvData;
  }
  /**
   * @param emvData the emvData to set
   */
  public void setEmvData(EMVData emvData) {
    this.emvData = emvData;
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
