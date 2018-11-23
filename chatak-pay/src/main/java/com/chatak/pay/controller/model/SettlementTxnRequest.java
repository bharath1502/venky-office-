package com.chatak.pay.controller.model;

import java.math.BigInteger;
import java.sql.Timestamp;

public class SettlementTxnRequest {

	private Long programManagerId;
	
	private Long acquirerProgramManagerId;

	private BigInteger totalAmount;

	private Timestamp batchDate;

	private String programManagerName;

	private Integer totalTxnCount;

	public Long getProgramManagerId() {
		return programManagerId;
	}

	public BigInteger getTotalAmount() {
		return totalAmount;
	}

	public Timestamp getBatchDate() {
		return batchDate;
	}

	public String getProgramManagerName() {
		return programManagerName;
	}

	public Integer getTotalTxnCount() {
		return totalTxnCount;
	}

	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	public void setTotalAmount(BigInteger totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setBatchDate(Timestamp batchDate) {
		this.batchDate = batchDate;
	}

	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}

	public void setTotalTxnCount(Integer totalTxnCount) {
		this.totalTxnCount = totalTxnCount;
	}

	public Long getAcquirerProgramManagerId() {
		return acquirerProgramManagerId;
	}

	public void setAcquirerProgramManagerId(Long acquirerProgramManagerId) {
		this.acquirerProgramManagerId = acquirerProgramManagerId;
	}

}
