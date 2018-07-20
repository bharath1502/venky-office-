package com.chatak.pg.bean;

import java.io.Serializable;

public class RefundRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private String issuerTxnRefNum;
	
	private String txnRefNum;
	
	private Integer refundStatus;
	

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
   * @return the refundStatus
   */
  public Integer getRefundStatus() {
    return refundStatus;
  }

  /**
   * @param refundStatus the refundStatus to set
   */
  public void setRefundStatus(Integer refundStatus) {
    this.refundStatus = refundStatus;
  }
	

}
