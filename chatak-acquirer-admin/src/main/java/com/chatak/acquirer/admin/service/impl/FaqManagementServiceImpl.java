package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.FaqManagementService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.pg.acq.dao.FaqManagementDao;
import com.chatak.pg.acq.dao.model.Category;
import com.chatak.pg.acq.dao.model.CategoryModule;
import com.chatak.pg.acq.dao.model.CategoryModuleMapping;
import com.chatak.pg.acq.dao.model.FaqManagement;
import com.chatak.pg.bean.Response;

import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.user.bean.FaqManagementResponse;

import com.chatak.pg.util.Constants;

@Service
public class FaqManagementServiceImpl implements FaqManagementService {

	private static Logger logger = Logger.getLogger(MerchantServiceImpl.class);

	@Autowired
	private FaqManagementDao faqmanagementDao;

	@Override
	public List<FaqManagementRequest> getAllCategories() throws ChatakAdminException {
		logger.info("Entering:: BankServiceImpl:: createBank method");

		List<Category> categeoryMgmtList = faqmanagementDao.getAllCategories();
		List<FaqManagementRequest> faqResponse = new ArrayList<FaqManagementRequest>();
		if (categeoryMgmtList != null) {
			for (Category categeoryManagementList : categeoryMgmtList) {
				FaqManagementRequest reponse = new FaqManagementRequest();
				reponse.setCategoryId(categeoryManagementList.getCategoryId());
				reponse.setCategoryName(categeoryManagementList.getCategoryName());
				faqResponse.add(reponse);
			}
		}

		return faqResponse;
	}

	@Override
	public FaqManagementResponse searchModule(FaqManagementRequest faqManagementRequest) throws ChatakAdminException {

		logger.info("Entering::FaqHandlerImpl :::: method  :::: searchModule");
		FaqManagementResponse faqManagementResponse = null;
		try {
			List<CategoryModule> categeoryModuleList = faqmanagementDao
					.findByModuleName(faqManagementRequest.getCategoryId());
			List<FaqManagementRequest> faqManagementRequestList = new ArrayList<>();
			for (CategoryModule categeoryList : categeoryModuleList) {
				faqManagementRequest = CommonUtil.copyBeanProperties(categeoryList, FaqManagementRequest.class);
				faqManagementRequestList.add(faqManagementRequest);
			}
			faqManagementResponse = new FaqManagementResponse();
			faqManagementResponse.setFaqManagementList(faqManagementRequestList);
		} catch (Exception e) {
			logger.error("ERROR: FaqHandlerImpl:: searchModule method", e);
		}
		return faqManagementResponse;
	}

	@Override
	public Response createFaqManagement(FaqManagementRequest faqManagementRequest) throws ChatakAdminException {
		logger.info("Entering:: FaqHandlerImpl:: createFaqManagement method");
		Response response = new Response();
		try {
			CategoryModuleMapping categeoryModuleList = faqmanagementDao
					.findByCategoryMappingId(faqManagementRequest.getCategoryId(), faqManagementRequest.getModuleId());
			faqManagementRequest.setCategoryMappingId(categeoryModuleList.getCategoryMappingId());
			faqManagementRequest.setStatus(Constants.ACTIVE);
			FaqManagement faqManagementDto = CommonUtil.copyBeanProperties(faqManagementRequest, FaqManagement.class);
			faqmanagementDao.saveOrUpdateFaqManagement(faqManagementDto);
			response.setErrorCode(Constants.SUCCESS_CODE);
			response.setErrorMessage(Constants.SUCESS);
		} catch (Exception e) {
			logger.error("ERROR: FaqHandlerImpl:: createFaqManagement method", e);
			response.setErrorCode(Constants.ERROR);
			response.setErrorMessage(Constants.ERROR_DATA);
		}
		logger.info("Exiting:: FaqHandlerImpl:: createFaqManagement method");
		return response;
	}

	@Override
	public FaqManagementResponse searchFaqManagement(FaqManagementRequest faqManagementRequest)
			throws ChatakAdminException {
		logger.info("Entering::FaqHandlerImpl :::: method  :::: searchFaqManagement");
		FaqManagementResponse faqManagementResponse = new FaqManagementResponse();
		faqManagementRequest.setIsAuditable(Boolean.TRUE);
		faqManagementRequest.setDataChange(Constants.NO);
		try {
			List<FaqManagementRequest> faqManagementRequestList = faqmanagementDao
					.searchFaqManagement(faqManagementRequest);
			if (CommonUtil.isListNotNullAndEmpty(faqManagementRequestList)) {
				faqManagementResponse.setFaqManagementList(faqManagementRequestList);
				faqManagementResponse.setTotalNoOfRows(faqManagementRequest.getNoOfRecords());
				faqManagementResponse.setErrorCode(Constants.SUCCESS);
			} else {
				faqManagementResponse.setErrorCode(Constants.ERROR);
				faqManagementResponse.setErrorMessage(Constants.ERROR_DATA);
			}
		} catch (Exception e) {
			logger.error("ERROR: FaqHandlerImpl::searchFaqManagement method", e);
		}
		return faqManagementResponse;

	}

	@Override
	public FaqManagementResponse searcFaqMgmtById(FaqManagementRequest faqManagementRequest)
			throws ChatakAdminException {
		logger.info("Entering::FaqHandlerImpl :::: method  :::: getFaqDetails");
		FaqManagementResponse response = new FaqManagementResponse();
		List<FaqManagementRequest> faqManagementRequestList = new ArrayList<FaqManagementRequest>();
		try {
			FaqManagementRequest faqFeilds = faqmanagementDao.findByFaqId(faqManagementRequest.getFaqId());
			FaqManagementRequest faqManagementRequests = CommonUtil.copyBeanProperties(faqFeilds,
					FaqManagementRequest.class);
			if (faqManagementRequests != null) {
				faqManagementRequests.setCategoryName(faqFeilds.getCategoryName());
				faqManagementRequests.setModuleName(faqFeilds.getModuleName());
				faqManagementRequests.setFaqId(faqFeilds.getFaqId());
				faqManagementRequests.setCategoryMappingId(faqFeilds.getCategoryMappingId());
				faqManagementRequests.setQuestionName(faqFeilds.getQuestionName());
				faqManagementRequests.setQuestionAnswer(faqFeilds.getQuestionAnswer());
				faqManagementRequests.setCategoryId(faqFeilds.getCategoryId());
				faqManagementRequests.setModuleId(faqFeilds.getModuleId());
				faqManagementRequests.setStatus(faqFeilds.getStatus());
				faqManagementRequestList.add(0, faqManagementRequests);
				response.setTotalNoOfRows(faqManagementRequestList.size());
				response.setFaqManagementList(faqManagementRequestList);
				response.setErrorCode(Constants.SUCCESS_CODE);
				response.setErrorMessage(Constants.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("ERROR: FaqHandlerImpl::getFaqDetails method", e);
			response.setErrorCode(Constants.ERROR);
			response.setErrorMessage(Constants.ERROR_DATA);

		}
		return response;
	}

	@Override
	public FaqManagementResponse updateFaqManagement(FaqManagementRequest faqManagementRequest)
			throws ChatakAdminException {
		FaqManagementResponse response = new FaqManagementResponse();
		try {
			FaqManagement faqManagementDto = CommonUtil.copyBeanProperties(faqManagementRequest, FaqManagement.class);
			faqManagementDto = faqmanagementDao.updateFaqManagement(faqManagementDto);
			faqManagementDto.setStatus(faqManagementRequest.getStatus());
			faqmanagementDao.saveOrUpdateFaqManagement(faqManagementDto);
			response.setErrorCode(Constants.SUCCESS_CODE);
			response.setErrorMessage(Constants.SUCCESS);
			logger.info("Exiting:: FaqHandlerImpl:: updateFaqManagement method");
		} catch (Exception e) {
			logger.error("ERROR: FaqHandlerImpl:: updateFaqManagement method", e);
			response.setErrorCode(Constants.ERROR);
			response.setErrorMessage(Constants.ERROR_DATA);
		}
		return response;
	}

}
