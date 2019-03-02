package com.chatak.acquirer.admin.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EntityMappedTransactions implements Serializable{

	private List<String> pGTransactionIds = new ArrayList<String>();
	private BigInteger totalEntityAmount = new BigInteger("0");

	public List<String> getpGTransactionIds() {
		return pGTransactionIds;
	}

	public void setpGTransactionIds(List<String> pGTransactionIds) {
		this.pGTransactionIds = pGTransactionIds;
	}

	public BigInteger getTotalEntityAmount() {
		return totalEntityAmount;
	}

	public void setTotalEntityAmount(BigInteger totalEntityAmount) {
		this.totalEntityAmount = totalEntityAmount;
	}
}
