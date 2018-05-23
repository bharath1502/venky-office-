package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_SWITCH_TRANSACTION")
public class PGSwitchTransaction implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8990769528360374249L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_SWITCH_TRANSACTION_ID", sequenceName = "SEQ_PG_SWITCH_TRANSACTION")
	@GeneratedValue(generator = "SEQ_PG_SWITCH_TRANSACTION_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;  

	@Column(name = "TRANSACTION_ID")
	private String transactionId;

	@Column(name = "PG_TRANSACTION_ID")
	private String pgTransactionId;

	@Column(name = "SWITCH_ID")
	private Long switchId;

	@Column(name = "TXN_AMOUNT")
	private Long txnAmount;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "SETTLEMENT_BATCH_ID")
	private Long settlementBatchId;

	@Column(name = "SETTLEMENT_BATCH_STATUS")
	private Integer settlementBatchStatus;

	@Column(name = "TRANSACTION_CODE")
	private String transaction_code;

	@Column(name = "PAN")
	private String pan;

	@Column(name = "PAN_MASKED")
	private String panMasked;

	@Column(name = "MCC")
	private String mcc;

	@Column(name = "POS_ENTRY_MODE")
	private String posEntryMode;

	@Column(name = "PROCESSOR_RESPONSE")
	private String processorResponse;

	@Column(name = "PROCESSOR_RESPONSE_MSG")
	private String processorResponseMsg;

	@Column(name = "PROCESSOR_RESPONSE_TIME")
	private Timestamp processorResponseTime;

	@Column(name = "PROCESSOR_RESPONSE_POST_DATE")
	private Timestamp processorResponsePostDate;

	@Column(name = "PROCESSOR_MESSAGE")
	private String processorMessage;

	@Column(name = "PROCESSOR_AUTH_CODE")
	private String processorAuthCode;

	@Column(name = "PROCESSOR_TXN_DUPLICATE")
	private String processorTxnDuplicate;

	@Column(name = "PROCESSOR_APPROVED_AMT")
	private Long processorApprovedAmt; 
	
	@Column(name = "TXN_MODE")
  private String txnMode;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the pgTransactionId
	 */
	public String getPgTransactionId() {
		return pgTransactionId;
	}

	/**
	 * @param pgTransactionId the pgTransactionId to set
	 */
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}

	/**
	 * @return the switchId
	 */
	public Long getSwitchId() {
		return switchId;
	}

	/**
	 * @param switchId the switchId to set
	 */
	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
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
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the settlementBatchId
	 */
	public Long getSettlementBatchId() {
		return settlementBatchId;
	}

	/**
	 * @param settlementBatchId the settlementBatchId to set
	 */
	public void setSettlementBatchId(Long settlementBatchId) {
		this.settlementBatchId = settlementBatchId;
	}

	/**
	 * @return the settlementBatchStatus
	 */
	public Integer getSettlementBatchStatus() {
		return settlementBatchStatus;
	}

	/**
	 * @param settlementBatchStatus the settlementBatchStatus to set
	 */
	public void setSettlementBatchStatus(Integer settlementBatchStatus) {
		this.settlementBatchStatus = settlementBatchStatus;
	}

	/**
	 * @return the pan
	 */
	public String getPan() {
		return pan;
	}

	/**
	 * @param pan the pan to set
	 */
	public void setPan(String pan) {
		this.pan = pan;
	}

	/**
	 * @return the panMasked
	 */
	public String getPanMasked() {
		return panMasked;
	}

	/**
	 * @param panMasked the panMasked to set
	 */
	public void setPanMasked(String panMasked) {
		this.panMasked = panMasked;
	}

	/**
	 * @return the mcc
	 */
	public String getMcc() {
		return mcc;
	}

	/**
	 * @param mcc the mcc to set
	 */
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	/**
	 * @return the posEntryMode
	 */
	public String getPosEntryMode() {
		return posEntryMode;
	}

	/**
	 * @param posEntryMode the posEntryMode to set
	 */
	public void setPosEntryMode(String posEntryMode) {
		this.posEntryMode = posEntryMode;
	}

	/**
	 * @return the transaction_code
	 */
	public String getTransaction_code() {
		return transaction_code;
	}

	/**
	 * @param transaction_code the transaction_code to set
	 */
	public void setTransaction_code(String transaction_code) {
		this.transaction_code = transaction_code;
	}

	/**
	 * @return the processorResponse
	 */
	public String getProcessorResponse() {
		return processorResponse;
	}

	/**
	 * @param processorResponse the processorResponse to set
	 */
	public void setProcessorResponse(String processorResponse) {
		this.processorResponse = processorResponse;
	}

	/**
	 * @return the processorResponseMsg
	 */
	public String getProcessorResponseMsg() {
		return processorResponseMsg;
	}

	/**
	 * @param processorResponseMsg the processorResponseMsg to set
	 */
	public void setProcessorResponseMsg(String processorResponseMsg) {
		this.processorResponseMsg = processorResponseMsg;
	}

	/**
	 * @return the processorResponseTime
	 */
	public Timestamp getProcessorResponseTime() {
		return processorResponseTime;
	}

	/**
	 * @param processorResponseTime the processorResponseTime to set
	 */
	public void setProcessorResponseTime(Timestamp processorResponseTime) {
		this.processorResponseTime = processorResponseTime;
	}

	/**
	 * @return the processorResponsePostDate
	 */
	public Timestamp getProcessorResponsePostDate() {
		return processorResponsePostDate;
	}

	/**
	 * @param processorResponsePostDate the processorResponsePostDate to set
	 */
	public void setProcessorResponsePostDate(Timestamp processorResponsePostDate) {
		this.processorResponsePostDate = processorResponsePostDate;
	}

	/**
	 * @return the processorMessage
	 */
	public String getProcessorMessage() {
		return processorMessage;
	}

	/**
	 * @param processorMessage the processorMessage to set
	 */
	public void setProcessorMessage(String processorMessage) {
		this.processorMessage = processorMessage;
	}

	/**
	 * @return the processorAuthCode
	 */
	public String getProcessorAuthCode() {
		return processorAuthCode;
	}

	/**
	 * @param processorAuthCode the processorAuthCode to set
	 */
	public void setProcessorAuthCode(String processorAuthCode) {
		this.processorAuthCode = processorAuthCode;
	}

	/**
	 * @return the processorTxnDuplicate
	 */
	public String getProcessorTxnDuplicate() {
		return processorTxnDuplicate;
	}

	/**
	 * @param processorTxnDuplicate the processorTxnDuplicate to set
	 */
	public void setProcessorTxnDuplicate(String processorTxnDuplicate) {
		this.processorTxnDuplicate = processorTxnDuplicate;
	}

	/**
	 * @return the processorApprovedAmt
	 */
	public Long getProcessorApprovedAmt() {
		return processorApprovedAmt;
	}

	/**
	 * @param processorApprovedAmt the processorApprovedAmt to set
	 */
	public void setProcessorApprovedAmt(Long processorApprovedAmt) {
		this.processorApprovedAmt = processorApprovedAmt;
	}

  /**
   * @return the txnMode
   */
  public String getTxnMode() {
    return txnMode;
  }

  /**
   * @param txnMode the txnMode to set
   */
  public void setTxnMode(String txnMode) {
    this.txnMode = txnMode;
  }

}