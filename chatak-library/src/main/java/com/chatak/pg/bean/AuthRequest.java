package com.chatak.pg.bean;

import java.io.Serializable;

public class AuthRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long totalTxnAmount;
	
	private Long txnFee;

	/**
	 * @return the totalTxnAmount
	 */
	@Override
	public Long getTotalTxnAmount() {
		return totalTxnAmount;
	}

	/**
	 * @param totalTxnAmount the totalTxnAmount to set
	 */
	@Override
	public void setTotalTxnAmount(Long totalTxnAmount) {
		this.totalTxnAmount = totalTxnAmount;
	}

	/**
	 * @return the txnFee
	 */
	@Override
	public Long getTxnFee() {
		return txnFee;
	}

	/**
	 * @param txnFee the txnFee to set
	 */
	@Override
	public void setTxnFee(Long txnFee) {
		this.txnFee = txnFee;
	}
	

}
