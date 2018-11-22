package com.chatak.pay.service;

import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pg.acq.dao.model.PGMerchant;

public interface BINService {
	public void validateCardProgram(String cardNumber, TransactionRequest transactionRequest, PGMerchant pgMerchant) throws InvalidRequestException, InstantiationException, IllegalAccessException;

}
