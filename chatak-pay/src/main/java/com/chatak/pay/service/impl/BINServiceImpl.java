package com.chatak.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.service.BINService;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.acq.dao.repository.BINRepository;
import com.chatak.pg.constants.PGConstants;

@Service
public class BINServiceImpl implements BINService {

	@Autowired
	private BINRepository binRepository;
	
	@Override
	public void validateBin(String cardNumber) throws InvalidRequestException {
		
		if(StringUtil.isNullAndEmpty(cardNumber)) {
            throw new InvalidRequestException(ChatakPayErrorCode.TXN_0004.name(), ChatakPayErrorCode.TXN_0004.value());
        }
		
		PGBINRange binRange = binRepository.findByBin(Long.valueOf(cardNumber.substring(0, Integer.parseInt("6"))));
		if(binRange == null || binRange.getStatus().intValue() != PGConstants.STATUS_SUCCESS) {
			throw new InvalidRequestException(ChatakPayErrorCode.TXN_0004.name(), ChatakPayErrorCode.TXN_0004.value());
		}
	}
}
