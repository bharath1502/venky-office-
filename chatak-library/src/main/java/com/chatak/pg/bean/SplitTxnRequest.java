package com.chatak.pg.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.chatak.pg.enums.ShareModeEnum;

public class SplitTxnRequest extends Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8364087223286529911L;

	  private String splitTransactionId;

	  private String pgTransactionId;

	  private Long splitAmount;

	  private ShareModeEnum splitMode;

	  private Timestamp createdDate;

	  private Timestamp updateddDate;

	  private Long status;

	  private String pan;

	  private String panMasked;

	  private String txnDescription;

	  private String reason;

	/**
	 * @return the splitTransactionId
	 */
	public String getSplitTransactionId() {
		return splitTransactionId;
	}

	

	/**
	 * @return the pgTransactionId
	 */
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	
	/**
   * @return the splitAmount
   */
  public Long getSplitAmount() {
    return splitAmount;
  }
	
	/**
   * @param splitTransactionId the splitTransactionId to set
   */
  public void setSplitTransactionId(String splitTransactionId) {
    this.splitTransactionId = splitTransactionId;
  }

	/**
	 * @param pgTransactionId the pgTransactionId to set
	 */
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}

	/**
	 * @param splitAmount the splitAmount to set
	 */
	public void setSplitAmount(Long splitAmount) {
		this.splitAmount = splitAmount;
	}

	/**
	 * @return the splitMode
	 */
	public ShareModeEnum getSplitMode() {
		return splitMode;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
   * @param splitMode the splitMode to set
   */
  public void setSplitMode(ShareModeEnum splitMode) {
    this.splitMode = splitMode;
  }

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updateddDate
	 */
	public Timestamp getUpdateddDate() {
		return updateddDate;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}
	
	/**
   * @param updateddDate the updateddDate to set
   */
  public void setUpdateddDate(Timestamp updateddDate) {
    this.updateddDate = updateddDate;
  }

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the pan
	 */
	public String getPan() {
		return pan;
	}

	/**
	 * @return the panMasked
	 */
	public String getPanMasked() {
		return panMasked;
	}
	
	/**
   * @param pan the pan to set
   */
  public void setPan(String pan) {
    this.pan = pan;
  }

	/**
	 * @param panMasked the panMasked to set
	 */
	public void setPanMasked(String panMasked) {
		this.panMasked = panMasked;
	}

	/**
	 * @return the txnDescription
	 */
	public String getTxnDescription() {
		return txnDescription;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	  
	/**
   * @param txnDescription the txnDescription to set
   */
  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }

	
}
