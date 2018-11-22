package com.chatak.acquirer.admin.model;

import java.math.BigInteger;

public class SettlementTransactionEntity {
	
	private String pGTransactionId;

	private BigInteger isoAmount;

	private BigInteger pmAmount;

	private BigInteger merchantAmount;

	private String batchId;

	public String getPGTransactionId() {
		return pGTransactionId;
	}

	public BigInteger getIsoAmount() {
		return isoAmount;
	}

	public BigInteger getPmAmount() {
		return pmAmount;
	}

	public BigInteger getMerchantAmount() {
		return merchantAmount;
	}

	public void setPGTransactionId(String pGTransactionId) {
		this.pGTransactionId = pGTransactionId;
	}

	public void setIsoAmount(BigInteger isoAmount) {
		this.isoAmount = isoAmount;
	}

	public void setPmAmount(BigInteger pmAmount) {
		this.pmAmount = pmAmount;
	}

	public void setMerchantAmount(BigInteger merchantAmount) {
		this.merchantAmount = merchantAmount;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

}
