package com.chatak.pay.service;

import com.chatak.pay.exception.InvalidRequestException;

public interface BINService {
	public void validateBin(String cardNumber) throws InvalidRequestException;
}
