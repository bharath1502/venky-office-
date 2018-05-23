package com.chatak.pg.bean;

import java.io.Serializable;

import com.chatak.pg.emv.util.EMVData;

public class PurchaseRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long txnAmount;
	
	private Long totalTxnAmount;
	
	private Long txnFee;
	
	private EMVData emvData;
	
	
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
 * @return the totalTxnAmount
 */
public Long getTotalTxnAmount() {
	return totalTxnAmount;
}
/**
 * @param totalTxnAmount the totalTxnAmount to set
 */
public void setTotalTxnAmount(Long totalTxnAmount) {
	this.totalTxnAmount = totalTxnAmount;
}
/**
 * @return the txnFee
 */
public Long getTxnFee() {
	return txnFee;
}
/**
 * @param txnFee the txnFee to set
 */
public void setTxnFee(Long txnFee) {
	this.txnFee = txnFee;
}
	
}
