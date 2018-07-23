package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class FeeCodeDTO extends SearchRequest
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2043247453533416887L;
	private Long feeCode;
	private String feeShortCode;
	private String feeDescription;
	private String txnResponseCode;
	private String txnShortCode;
	private String status;
	
	/**
	 * @return the feeCode
	 */
	public Long getFeeCode() {
		return feeCode;
	}
	
	/**
   * @return the feeDescription
   */
  public String getFeeDescription() {
    return feeDescription;
  }
	
	/**
	 * @return the feeShortCode
	 */
	public String getFeeShortCode() {
		return feeShortCode;
	}
	
	/**
   * @return the txnShortCode
   */
  public String getTxnShortCode() {
    return txnShortCode;
  }
	/**
	 * @param feeShortCode the feeShortCode to set
	 */
	public void setFeeShortCode(String feeShortCode) {
		this.feeShortCode = feeShortCode;
	}
	
	/**
	 * @param feeDescription the feeDescription to set
	 */
	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}
	
	/**
	 * @param txnShortCode the txnShortCode to set
	 */
	public void setTxnShortCode(String txnShortCode) {
		this.txnShortCode = txnShortCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the txnResponseCode
	 */
	public String getTxnResponseCode() {
		return txnResponseCode;
	}
	
	/**
   * @param feeCode the feeCode to set
   */
  public void setFeeCode(Long feeCode) {
    this.feeCode = feeCode;
  }
	/**
	 * @param txnResponseCode the txnResponseCode to set
	 */
	public void setTxnResponseCode(String txnResponseCode) {
		this.txnResponseCode = txnResponseCode;
	}
	
	
	
}
