package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.service.FaqManagementService;
import com.chatak.pg.acq.dao.FaqManagementDao;
import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.user.bean.FaqManagementResponse;
import com.chatak.pg.util.CommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaqManagementServiceImpl implements FaqManagementService {

	@Autowired
	private FaqManagementDao faqmanagementDao;

	private static Logger logger = LogManager.getLogger(FaqManagementServiceImpl.class);

	@Override
	public FaqManagementResponse searchFaqManagement(FaqManagementRequest faqManagementRequest)
			throws ChatakMerchantException {
		logger.info("Entering::FaqHandlerImpl ::::searchFaqModule method  :::: getUsers");
		FaqManagementResponse faqManagementResponse = null;
		try {
			List<FaqManagementRequest> faqRequestList = faqmanagementDao.findByCategoryMappingId(faqManagementRequest);
			List<FaqManagementRequest> faqManagementRequestList = new ArrayList<>();
			for (FaqManagementRequest faqList : faqRequestList) {
				faqManagementRequest = CommonUtil.copyBeanProperties(faqList, FaqManagementRequest.class);
				faqManagementRequestList.add(faqManagementRequest);
			}
			faqManagementResponse = new FaqManagementResponse();
			faqManagementResponse.setFaqManagementList(faqManagementRequestList);
		} catch (Exception e) {
			logger.error("ERROR: FaqHandlerImpl:: searchFaqModule method", e);
		}
		return faqManagementResponse;
	}
}
