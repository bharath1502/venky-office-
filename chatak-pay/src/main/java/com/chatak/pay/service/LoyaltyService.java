package com.chatak.pay.service;

import com.chatak.pay.controller.model.LoyaltyResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pg.bean.PurchaseRequest;

public interface LoyaltyService {
	
	public LoyaltyResponse invokeLoyalty(TransactionRequest transactionRequest, PurchaseRequest request);
	
	public LoyaltyResponse invokeRedeemLoyaltyTxn(TransactionRequest transactionRequest, PurchaseRequest request);

}
