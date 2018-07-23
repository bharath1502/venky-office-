package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_ACTIVITY_LOG")
public class PGActivityLog implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5117840945179857076L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_ACTIVITY_LOG_ID", sequenceName = "SEQ_PG_ACTIVITY_LOG")
	@GeneratedValue(generator = "SEQ_PG_ACTIVITY_LOG_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SYS_TRACE_NUM")
	private String sysTraceNum;

	@Column(name = "REQUEST_IP")
	private String requestIP;

	@Column(name = "REQUEST_PORT")
	private Integer requestPort;

	@Column(name = "RESPONSE_PORT")
	private Integer responsePort;

	@Column(name = "POS_ENTRY_MODE")
	private String posEntryMode;

	@Column(name = "CHIP_TRANSACTION")
	private Integer chipTransaction;

	@Column(name = "PROCESSING_CODE")
	private String processingCode;

	@Column(name = "RESPONSE_CODE")
	private String responseCode;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "F39")
	private String f39;

	@Column(name = "TXN_AMOUNT")
	private Long txnAmount;

	@Column(name = "ADJ_AMOUNT")
	private Long adjAmount;

	@Column(name = "MTI")
	private String mti;

	@Column(name = "PAN")
	private String pan;

	@Column(name = "PAN_MASKED")
	private String panMasked;

	@Column(name = "EXP_DATE")
	private Long expDate;

	@Column(name = "POS_TXN_TIME")
	private String posTxnTime;

	@Column(name = "POS_TXN_DATE")
	private String posTxnDate;

	@Column(name = "MCC")
	private String mcc;

	@Column(name = "TXN_COUNTRY_CODE")
	private String txnCountryCode;

	@Column(name = "TXN_CURRENCY_CODE")
	private String txnCurrencyCode;

	@Column(name = "AI_COUNTRY_CODE")
	private String aiCountryCode;

	@Column(name = "PAN_COUNTRY_CODE")
	private String panCountryCode;

	@Column(name = "FWD_COUNTRY_CODE")
	private String fwdCountryCode;

	@Column(name = "F55")
	private String f55;

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
	 * @return the requestPort
	 */
	public Integer getRequestPort() {
		return requestPort;
	}

	/**
	 * @param requestPort the requestPort to set
	 */
	public void setRequestPort(Integer requestPort) {
		this.requestPort = requestPort;
	}

	/**
	 * @return the responsePort
	 */
	public Integer getResponsePort() {
		return responsePort;
	}

	/**
	 * @param responsePort the responsePort to set
	 */
	public void setResponsePort(Integer responsePort) {
		this.responsePort = responsePort;
	}

	/**
	 * @return the sysTraceNum
	 */
	public String getSysTraceNum() {
		return sysTraceNum;
	}

	/**
	 * @param sysTraceNum the sysTraceNum to set
	 */
	public void setSysTraceNum(String sysTraceNum) {
		this.sysTraceNum = sysTraceNum;
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
	 * @return the requestIP
	 */
	public String getRequestIP() {
		return requestIP;
	}

	/**
	 * @param requestIP the requestIP to set
	 */
	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
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
	 * @return the chipTransaction
	 */
	public Integer getChipTransaction() {
		return chipTransaction;
	}

	/**
	 * @param chipTransaction the chipTransaction to set
	 */
	public void setChipTransaction(Integer chipTransaction) {
		this.chipTransaction = chipTransaction;
	}

	/**
	 * @return the processingCode
	 */
	public String getProcessingCode() {
		return processingCode;
	}

	/**
	 * @param processingCode the processingCode to set
	 */
	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the f39
	 */
	public String getF39() {
		return f39;
	}

	/**
	 * @param f39 the f39 to set
	 */
	public void setF39(String f39) {
		this.f39 = f39;
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
	 * @return the adjAmount
	 */
	public Long getAdjAmount() {
		return adjAmount;
	}

	/**
	 * @param adjAmount the adjAmount to set
	 */
	public void setAdjAmount(Long adjAmount) {
		this.adjAmount = adjAmount;
	}

	/**
	 * @return the mti
	 */
	public String getMti() {
		return mti;
	}

	/**
	 * @param mti the mti to set
	 */
	public void setMti(String mti) {
		this.mti = mti;
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
	 * @return the expDate
	 */
	public Long getExpDate() {
		return expDate;
	}

	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(Long expDate) {
		this.expDate = expDate;
	}

	/**
	 * @return the posTxnTime
	 */
	public String getPosTxnTime() {
		return posTxnTime;
	}

	/**
	 * @param posTxnTime the posTxnTime to set
	 */
	public void setPosTxnTime(String posTxnTime) {
		this.posTxnTime = posTxnTime;
	}

	/**
	 * @return the posTxnDate
	 */
	public String getPosTxnDate() {
		return posTxnDate;
	}

	/**
	 * @param posTxnDate the posTxnDate to set
	 */
	public void setPosTxnDate(String posTxnDate) {
		this.posTxnDate = posTxnDate;
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
	 * @return the txnCountryCode
	 */
	public String getTxnCountryCode() {
		return txnCountryCode;
	}

	/**
	 * @param txnCountryCode the txnCountryCode to set
	 */
	public void setTxnCountryCode(String txnCountryCode) {
		this.txnCountryCode = txnCountryCode;
	}

	/**
	 * @return the txnCurrencyCode
	 */
	public String getTxnCurrencyCode() {
		return txnCurrencyCode;
	}

	/**
	 * @param txnCurrencyCode the txnCurrencyCode to set
	 */
	public void setTxnCurrencyCode(String txnCurrencyCode) {
		this.txnCurrencyCode = txnCurrencyCode;
	}

	/**
	 * @return the aiCountryCode
	 */
	public String getAiCountryCode() {
		return aiCountryCode;
	}

	/**
	 * @param aiCountryCode the aiCountryCode to set
	 */
	public void setAiCountryCode(String aiCountryCode) {
		this.aiCountryCode = aiCountryCode;
	}

	/**
	 * @return the panCountryCode
	 */
	public String getPanCountryCode() {
		return panCountryCode;
	}

	/**
	 * @param panCountryCode the panCountryCode to set
	 */
	public void setPanCountryCode(String panCountryCode) {
		this.panCountryCode = panCountryCode;
	}

	/**
	 * @return the fwdCountryCode
	 */
	public String getFwdCountryCode() {
		return fwdCountryCode;
	}

	/**
	 * @param fwdCountryCode the fwdCountryCode to set
	 */
	public void setFwdCountryCode(String fwdCountryCode) {
		this.fwdCountryCode = fwdCountryCode;
	}

	/**
	 * @return the f55
	 */
	public String getF55() {
		return f55;
	}

	/**
	 * @param f55 the f55 to set
	 */
	public void setF55(String f55) {
		this.f55 = f55;
	}


}