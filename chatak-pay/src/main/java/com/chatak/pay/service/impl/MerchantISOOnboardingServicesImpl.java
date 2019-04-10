package com.chatak.pay.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.pay.controller.model.MerchantIsoOnboardingResponse;
import com.chatak.pay.service.MerchantISOOnboardingServices;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.user.bean.IsoResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.Constants;

@Service
public class MerchantISOOnboardingServicesImpl implements MerchantISOOnboardingServices {
	
	private static Logger logger = LogManager.getLogger(PGTransactionServiceImpl.class);
	
	@Autowired
	private ProgramManagerDao programManagerDao;
	
	@Autowired
	private IsoServiceDao isoServiceDao;

	@Override
	public MerchantIsoOnboardingResponse fetchPmsByCurrency(String currency) {
		logger.info("Entering :: MerchantISOOnboardingServicesImpl :: getMerchantTransactionList");
		MerchantIsoOnboardingResponse response = new MerchantIsoOnboardingResponse();
		List<ProgramManagerRequest> programManagers = programManagerDao.fetchProgramManagerNameByAccountCurrency(currency);
		if (StringUtil.isListNotNullNEmpty(programManagers)) {
			response.setProgramManager(programManagers);
			response.setErrorCode(Constants.SUCCESS_CODE);
			response.setErrorMessage(Constants.SUCCESS);
		} else {
			response.setProgramManager(programManagers);
			response.setErrorCode(Constants.ERROR_CODE);
			response.setErrorMessage(Constants.ERROR);
		}
		logger.info("Exiting :: MerchantISOOnboardingServicesImpl :: getMerchantTransactionList");
		return response;
	}

	@Override
	public MerchantIsoOnboardingResponse fetchIsoByPmId(Long pmId) {
		logger.info("Entering :: MerchantISOOnboardingServicesImpl :: fetchIsoByPmId");
		MerchantIsoOnboardingResponse response = new MerchantIsoOnboardingResponse();
		IsoResponse  isoResponse = isoServiceDao.getIsoNameByProgramManagerId(pmId);
		if (StringUtil.isListNotNullNEmpty(isoResponse.getIsoRequest())) {
			response.setIsoRequest(isoResponse.getIsoRequest());
			response.setErrorCode(Constants.SUCCESS_CODE);
			response.setErrorMessage(Constants.SUCCESS);
		} else {
			response.setIsoRequest(isoResponse.getIsoRequest());
			response.setErrorCode(Constants.ERROR_CODE);
			response.setErrorMessage(Constants.ERROR);
		}
		return response;
	}
}
