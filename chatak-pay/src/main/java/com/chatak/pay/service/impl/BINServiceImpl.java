package com.chatak.pay.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.service.BINService;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.repository.BINRepository;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;

@Service
public class BINServiceImpl implements BINService {

  private Logger logger = Logger.getLogger(BINServiceImpl.class);
  
	@Autowired
	private BINRepository binRepository;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Override
	public void validateCardProgram(String cardNumber, TransactionRequest transactionRequest) throws InvalidRequestException, InstantiationException, IllegalAccessException {
		
	    logger.debug("Incoming card program: " + cardNumber.substring(0, Constants.ELEVEN));
	    LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Incoming card program: " + cardNumber.substring(0, Constants.ELEVEN) );
	  
		if(StringUtil.isNullAndEmpty(cardNumber)) {
            throw new InvalidRequestException(ChatakPayErrorCode.TXN_0004.name(), ChatakPayErrorCode.TXN_0004.value());
        }
		com.chatak.pg.model.TransactionRequest transactionData = com.chatak.pg.util.CommonUtil
				.copyBeanProperties(transactionRequest, com.chatak.pg.model.TransactionRequest.class);
		List<Long> cardNumberList = transactionDao.fetchCardProgramDetailsByMerchantCode(transactionData);
		
		logger.debug("Supported Card programs: " + cardNumberList);
		LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Supported Card programs: " + cardNumberList);
		
		if(cardNumberList == null || !(cardNumberList.contains( Long.valueOf(cardNumber.substring(0, Constants.ELEVEN))) )) {
			throw new InvalidRequestException(ChatakPayErrorCode.TXN_0115.name(), ChatakPayErrorCode.TXN_0115.value());
		}
	}
}
