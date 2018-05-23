package com.chatak.pg.bean;

import java.io.Serializable;

import com.chatak.pg.emv.util.EMVData;

public class CaptureRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long txnAmount;
	private String authId;
	private String authTxnRefNum;
	private EMVData emvData;
	private String issuerTxnRefNum;
	private Long feeAmount;
	private Long totalAmount;
	private boolean isPartialCapture;
	
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
	 * @return the authTxnRefNum
	 */
	public String getAuthTxnRefNum() {
		return authTxnRefNum;
	}
	/**
	 * @param authTxnRefNum the authTxnRefNum to set
	 */
	public void setAuthTxnRefNum(String authTxnRefNum) {
		this.authTxnRefNum = authTxnRefNum;
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
/**
 * @return the isPartialCapture
 */
public boolean isPartialCapture() {
	return isPartialCapture;
}
/**
 * @param isPartialCapture the isPartialCapture to set
 */
public void setPartialCapture(boolean isPartialCapture) {
	this.isPartialCapture = isPartialCapture;
}

	
}
