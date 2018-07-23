package com.chatak.pg.bean;

import java.io.Serializable;

public class PurchaseResponse extends CashBackResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long totalTxnAmount;
	
  public Long getTotalTxnAmount() {
    return totalTxnAmount;
  }
  public void setTotalTxnAmount(Long totalTxnAmount) {
    this.totalTxnAmount = totalTxnAmount;
  }
	
	
}
