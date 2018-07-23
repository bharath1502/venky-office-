package com.chatak.pay.service;

import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.exception.InvalidRequestException;

public interface BINService {
	public void validateCardProgram(String cardNumber, TransactionRequest transactionRequest) throws InvalidRequestException, InstantiationException, IllegalAccessException;

}
