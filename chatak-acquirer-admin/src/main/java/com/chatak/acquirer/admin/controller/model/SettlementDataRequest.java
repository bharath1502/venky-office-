package com.chatak.acquirer.admin.controller.model;

import java.math.BigInteger;
import java.sql.Timestamp;

public class SettlementDataRequest {
	
	private Long programManagerId;
	private BigInteger totalAmount;
	private Timestamp batchDate;
	private Integer totalTxnCount;
	private String programManagerName;
	private String description;
	private String debit;
	private String credit;
	private String txnType;
	private String txnCode;
	private String txnStatus;
	private String txnDesc;
	private Long txnAmount;
	private String txnRefNum;
	private Timestamp txnDate;
	private String merchantDesc;
	private String deviceTimeZoneOffset;
	private String deviceTxnTime;
	private String terminalId;
	private String merchantId;
	private Long pgTxnId;
	private Long partnerId;
	private Long pmId;
	private Timestamp batchtime;
	private String partnerName;
	private String gatewayTxnId;
	private Integer rowName;
	private String errorMessage;
	
	public Long getProgramManagerId() {
		return programManagerId;
	}

	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	public BigInteger getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigInteger totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Timestamp batchDate) {
		this.batchDate = batchDate;
	}

	public Integer getTotalTxnCount() {
		return totalTxnCount;
	}

	public void setTotalTxnCount(Integer totalTxnCount) {
		this.totalTxnCount = totalTxnCount;
	}

	public String getProgramManagerName() {
		return programManagerName;
	}

	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getTxnDesc() {
		return txnDesc;
	}

	public void setTxnDesc(String txnDesc) {
		this.txnDesc = txnDesc;
	}

	public Long getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getTxnRefNum() {
		return txnRefNum;
	}

	public void setTxnRefNum(String txnRefNum) {
		this.txnRefNum = txnRefNum;
	}

	public Timestamp getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public String getMerchantDesc() {
		return merchantDesc;
	}

	public void setMerchantDesc(String merchantDesc) {
		this.merchantDesc = merchantDesc;
	}

	public String getDeviceTimeZoneOffset() {
		return deviceTimeZoneOffset;
	}

	public void setDeviceTimeZoneOffset(String deviceTimeZoneOffset) {
		this.deviceTimeZoneOffset = deviceTimeZoneOffset;
	}

	public String getDeviceTxnTime() {
		return deviceTxnTime;
	}

	public void setDeviceTxnTime(String deviceTxnTime) {
		this.deviceTxnTime = deviceTxnTime;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Long getPgTxnId() {
		return pgTxnId;
	}

	public void setPgTxnId(Long pgTxnId) {
		this.pgTxnId = pgTxnId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getPmId() {
		return pmId;
	}

	public void setPmId(Long pmId) {
		this.pmId = pmId;
	}

	public Timestamp getBatchtime() {
		return batchtime;
	}

	public void setBatchtime(Timestamp batchtime) {
		this.batchtime = batchtime;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getGatewayTxnId() {
		return gatewayTxnId;
	}

	public void setGatewayTxnId(String gatewayTxnId) {
		this.gatewayTxnId = gatewayTxnId;
	}

	public Integer getRowName() {
		return rowName;
	}

	public void setRowName(Integer rowName) {
		this.rowName = rowName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
