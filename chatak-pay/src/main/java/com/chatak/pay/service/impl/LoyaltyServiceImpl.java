package com.chatak.pay.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chatak.pay.controller.model.LoyaltyProgramRequest;
import com.chatak.pay.controller.model.LoyaltyResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.service.LoyaltyService;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.bean.PurchaseRequest;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoyaltyServiceImpl implements LoyaltyService {
	
	private Logger log = LogManager.getLogger(PGTransactionServiceImpl.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public LoyaltyResponse invokeLoyalty(TransactionRequest transactionRequest, PurchaseRequest request) {

		LoyaltyResponse loyaltyResponse = new LoyaltyResponse();

		LoyaltyProgramRequest loyaltyProgramAwardRequest = new LoyaltyProgramRequest();

		loyaltyProgramAwardRequest.setTxnAmount(transactionRequest.getTxnAmount());
		loyaltyProgramAwardRequest.setMobileNumber(transactionRequest.getMobileNumber());
		loyaltyProgramAwardRequest.setAccountNumber(transactionRequest.getAccountNumber());
		loyaltyProgramAwardRequest.setMerchantId(request.getMerchantId());
		loyaltyProgramAwardRequest.setLoyaltyProgramType(PGConstants.MERCHANT);
		loyaltyProgramAwardRequest.setIsoId(request.getIsoId());
		loyaltyProgramAwardRequest.setLoyaltyUrl(Properties.getProperty("loyalty.service.url"));
		loyaltyProgramAwardRequest.setEmail(Properties.getProperty("loyalty.service.url.user.name"));
		loyaltyProgramAwardRequest.setPassword(Properties.getProperty("loyalty.service.url.user.password"));

		log.info("Strating Thread for Awarding Transaction");
		log.info("Entering :: LoyaltyServiceImpl :: invokLoyalty :: Runnable");
		try {
			log.info("Calling JsonUtil To Get OAuth Token:: Calling callRESTApi Method");
			String accessToken = JsonUtil.postLoyaltyRequest(loyaltyProgramAwardRequest, "/userService/user/login",
					String.class);

			if (StringUtil.isNullEmpty(accessToken)) {
				loyaltyResponse.setErrorCode(Constants.ERROR_CODE);
				loyaltyResponse.setErrorMessage(Constants.TOKEN_ERROR);
				return loyaltyResponse;
			}
			else {

				loyaltyResponse = mapper.readValue(accessToken, LoyaltyResponse.class);
				log.info("Calling JsonUtil After  getting OAuth Token:: Oauth Token :" + accessToken);
				JsonUtil.awardLoyatyTransaction(loyaltyProgramAwardRequest, loyaltyProgramAwardRequest.getLoyaltyUrl(),
						loyaltyResponse.getValue());
			}
		} catch (Exception e) {
			log.error(e + "Exit:: JsonUtil:: callRESTApi : call for loyalty account method2");
		}
		log.info("Exiting :: LoyaltyServiceImpl :: invokLoyalty :: Runnable");
		return loyaltyResponse;
	}
}
