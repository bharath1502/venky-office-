package com.chatak.pay.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.service.BINService;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.repository.BINRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.Constants;

@Service
public class BINServiceImpl implements BINService {

  private static Logger logger = LogManager.getLogger(BINServiceImpl.class);
  
	@Autowired
	private BINRepository binRepository;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	@Override
	public void validateCardProgram(String cardNumber, TransactionRequest transactionRequest, PGMerchant pgMerchant) throws InvalidRequestException, InstantiationException, IllegalAccessException {
		
	    logger.info("Incoming card program: " + cardNumber.substring(0, Constants.ELEVEN) );
	    List<Long> cardNumberList = null;
		if(StringUtil.isNullAndEmpty(cardNumber)) {
            throw new InvalidRequestException(ChatakPayErrorCode.TXN_0004.name(), ChatakPayErrorCode.TXN_0004.value());
        }
		com.chatak.pg.model.TransactionRequest transactionData = com.chatak.pg.util.CommonUtil
				.copyBeanProperties(transactionRequest, com.chatak.pg.model.TransactionRequest.class);
		if (pgMerchant.getMerchantType().equals(PGConstants.SUB_MERCHANT)) {
			PGMerchant merchant = merchantRepository.findById(pgMerchant.getParentMerchantId()).orElse(null);
			if (merchant == null || !merchant.getStatus().equals(PGConstants.STATUS_ACTIVE)) {
				throw new InvalidRequestException(ChatakPayErrorCode.TXN_0007.name(),
						ChatakPayErrorCode.TXN_0007.value());
			}
			transactionData.setMerchantCode(merchant.getMerchantCode());
		}
		cardNumberList = transactionDao.fetchCardProgramDetailsByMerchantCode(transactionData);
		logger.info("Supported Card programs: " + cardNumberList);
		if(cardNumberList == null || !(cardNumberList.contains( Long.valueOf(cardNumber.substring(0, Constants.ELEVEN))) )) {
			throw new InvalidRequestException(ChatakPayErrorCode.TXN_0115.name(), ChatakPayErrorCode.TXN_0115.value());
		}
	}
}
