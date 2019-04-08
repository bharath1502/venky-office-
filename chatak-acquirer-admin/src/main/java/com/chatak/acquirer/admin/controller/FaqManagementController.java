package com.chatak.acquirer.admin.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;


import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.FaqManagementService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.exception.PrepaidException;
import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.user.bean.FaqManagementResponse;
import com.chatak.pg.util.Constants;

@Controller
public class FaqManagementController implements URLMappingConstants {

	private Logger logger = LogManager.getLogger(FaqManagementController.class);

	@Autowired
	FaqManagementService faqManagementService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = SHOW_FAQ_MANAGEMENT_SEARCH, method = RequestMethod.GET)
	public ModelAndView showFaqManagementSearch(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Map<String, Object> model) throws ChatakAdminException {
		logger.info("Entering:: FaqManagementController:: showFaqManagementSearch method");

		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_SEARCH_PAGE);
		FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
		if (request.getHeader(Constants.REFERER) == null) {
			session.invalidate();
			modelAndView.setViewName(INVALID_REQUEST_PAGE);
			return modelAndView;
		}
		modelAndView.addObject(Constants.ERROR, null);
		session.setAttribute(Constants.ERROR, null);
		modelAndView.addObject(PGConstants.SEARCH_LIST, Constants.YES);
		modelAndView.addObject(Constants.FAQ_MANAGEMENT_MODEL, faqManagementRequest);
		getCategory(model);
		logger.info("Exiting:: FaqManagementController:: showFaqManagementSearch method");
		return modelAndView;
	}

	private void getCategory(Map<String, Object> model) throws ChatakAdminException {
		List<FaqManagementRequest> faqManagementRequestList = faqManagementService.getAllCategories();
		model.put("faqManagementCategeoryList", faqManagementRequestList);

	}

	@RequestMapping(value = SHOW_FAQ_MANAGEMENT_CREATE, method = RequestMethod.GET)
	public ModelAndView showFaqManagementCreate(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, HttpSession session) throws ChatakAdminException {
		logger.info("Entering:: FaqManagementController:: showFaqManagementCreate method");

		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_CREATE_PAGE);
		if (request.getHeader(Constants.REFERER) == null) {
			session.invalidate();
			modelAndView.setViewName(INVALID_REQUEST_PAGE);
			return modelAndView;
		}
		modelAndView.addObject(Constants.ERROR, null);
		session.setAttribute(Constants.ERROR, null);
		getCategory(model);
		FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
		modelAndView.addObject(PGConstants.FAQ_MANAGEMENT_REQUEST, faqManagementRequest);
		logger.info("Exiting:: FaqManagementController:: showFaqManagementCreate method");
		return modelAndView;
	}

	@RequestMapping(value = FETCH_MODULE_FOR_CATEGORY_ID, method = RequestMethod.GET)
	public @ResponseBody String fetchModuleNameForCategory(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, HttpSession session, @FormParam("categoryId") final Long categoryId)
			throws PrepaidException {
		logger.info("Entering  :: FaqManagementController:: fetchModuleNameForCategory method");
		try {
			FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
			faqManagementRequest.setCategoryId(categoryId);
			session.setAttribute("categoryId", categoryId);
			FaqManagementResponse faqManagementResponse = faqManagementService.searchModule(faqManagementRequest);
			faqManagementResponse.setErrorMessage("SUCCESS");
			model.put("faqManagementResponse", faqManagementResponse);
			return JsonUtil.convertObjectToJSON(faqManagementResponse);
		} catch (Exception e) {
			logger.error("ERROR:: FaqManagementController:: fetchModuleNameForCategory method", e);
		}
		logger.info("Exiting  :: FaqManagementController:: fetchModuleNameForCategory method");

		return null;
	}

	@RequestMapping(value = FAQ_MANAGEMENT_CREATE, method = RequestMethod.POST)
	public ModelAndView createFaqManagement(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, HttpSession session, @ModelAttribute FaqManagementRequest faqManagementRequest,
			BindingResult bindingResult) {
		logger.info("Entering:: FaqManagementController:: createFaqManagement method");
		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_CREATE_PAGE);
		try {

			Response faqManagementResponse = faqManagementService.createFaqManagement(faqManagementRequest);
			if (faqManagementResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
				modelAndView = showFaqManagementSearch(request, response, session, model);
				String successData = messageSource.getMessage("prepaid.new.faqmanagement.success.message", null,
						LocaleContextHolder.getLocale());
				model.put(Constants.SUCESS, successData);
			} else {
				modelAndView = showFaqManagementCreate(request, response, model, session);
				model.put(Constants.ERROR, faqManagementResponse.getErrorMessage());
			}
		} catch (Exception e) {
			logger.error("Error:: FaqManagementController:: createFaqManagement method", e);
		}
		return modelAndView;
	}

	@RequestMapping(value = FAQ_MANAGEMENT_SEARCH_ACTION, method = RequestMethod.POST)
	public ModelAndView processFaqManagementSearch(HttpServletRequest request,
			FaqManagementRequest faqManagementRequest, HttpServletResponse response, Map<String, Object> model,
			HttpSession session) throws ChatakAdminException {
		logger.info("Entering:: FaqManagementController:: processFaqManagementSearch method");
		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_SEARCH_PAGE);
		try {
			faqManagementRequest.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
			faqManagementRequest.setPageIndex(Constants.ONE);
			getCategory(model);
			FaqManagementResponse faqManagementResponse = faqManagementService
					.searchFaqManagement(faqManagementRequest);
			modelAndView = PaginationUtil.getPagenationModel(modelAndView, faqManagementResponse.getTotalNoOfRows(),
					faqManagementRequest.getPageSize());
			List<FaqManagementRequest> faqManagementRequestList = faqManagementResponse.getFaqManagementList();
			if (CommonUtil.isListNotNullAndEmpty(faqManagementRequestList)) {
				model.put("faqManagementRequestLists", faqManagementRequestList);
				model.put(PGConstants.TOTAL_RECORDS, faqManagementResponse.getTotalNoOfRows());
			}
		} catch (Exception e) {
			logger.error("ERROR:: FaqManagementController:: processFaqManagementSearch method", e);
		}

		model.put(PGConstants.FAQ_MANAGEMENT_REQUEST, faqManagementRequest);
		logger.info("Exiting:: FaqManagementController:: processFaqManagementSearch method");
		return modelAndView;
	}

	@RequestMapping(value = SHOW_FAQ_MANAGEMENT_EDIT, method = RequestMethod.POST)
	public ModelAndView showFaqManagementEdit(HttpServletRequest request, @FormParam("faqIdData") final Long faqIdData,
			@FormParam("status") final String status, HttpServletResponse response, Map<String, Object> model,
			HttpSession session) {
		logger.info("Entering:: FaqManagementController:: showFaqManagementEdit method");

		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_EDIT_PAGE);
		if (request.getHeader(Constants.REFERER) == null) {
			session.invalidate();
			modelAndView.setViewName(INVALID_REQUEST_PAGE);
			return modelAndView;
		}
		try {
			FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
			faqManagementRequest.setFaqId(faqIdData);
			FaqManagementResponse faqManagementResponse = faqManagementService.searcFaqMgmtById(faqManagementRequest);
			if (faqManagementResponse != null) {
				List<FaqManagementRequest> faqManagementRequestList = faqManagementResponse.getFaqManagementList();
				faqManagementRequest = faqManagementRequestList.get(0);
				if (CommonUtil.isListNotNullAndEmpty(faqManagementRequestList)) {
					modelAndView.addObject(PGConstants.PAGE_SIZE, faqManagementRequest.getPageSize());
					model.put(PGConstants.FAQ_MANAGEMENT_REQUEST, faqManagementRequest);
				}
			}
		} catch (Exception e) {
			logger.error("Error:: FaqManagementController:: showFaqManagementEdit method", e);
		}
		logger.info("Exiting:: FaqManagementController:: showFaqManagementEdit method");
		return modelAndView;
	}

	@RequestMapping(value = FAQ_MANAGEMENT_EDIT, method = RequestMethod.POST)
	public ModelAndView editFaqManagement(HttpServletRequest request, FaqManagementRequest faqManagementRequest,
			HttpServletResponse response, Map<String, Object> model, HttpSession session)
			throws ChatakAdminException {
		logger.info("Entering:: FaqManagementController:: editFaqManagement method");

		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_SEARCH_PAGE);
		try {
			Response faqManagementResponse = faqManagementService.createFaqManagement(faqManagementRequest);
			if (faqManagementResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
				String successData = messageSource.getMessage("prepaid.faq.success.update.message", null,
						LocaleContextHolder.getLocale());
				model.put(Constants.SUCESS, successData);
			} else {
				model.put(Constants.ERROR, faqManagementResponse.getErrorMessage());
			}
		} catch (Exception e) {
			logger.error("Errror :: FaqManagementController :: editFaqManagement", e);
		}

		logger.info("Exiting :: FaqManagementController :: editFaqManagement");
		modelAndView = showFaqManagementSearch(request, response, session, model);
		return modelAndView;
	}

	@RequestMapping(value = FAQ_PAGINATION_ACTION, method = RequestMethod.POST)
	public ModelAndView getPaginationList(final HttpSession session, @FormParam("pageNumber") final Integer pageNumber,
			@FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords, Map<String, Object> model) {
		logger.info("Entering:: FaqManagementController:: getPaginationList method");
		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_SEARCH_PAGE);
		try {
			FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
			faqManagementRequest.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
			faqManagementRequest.setPageIndex(pageNumber);
			faqManagementRequest.setNoOfRecords(totalRecords);
			getCategory(model);
			FaqManagementResponse faqManagementResponse = faqManagementService
					.searchFaqManagement(faqManagementRequest);
			modelAndView = PaginationUtil.getPagenationModel(modelAndView, faqManagementResponse.getTotalNoOfRows(),
					faqManagementRequest.getPageSize());
			List<FaqManagementRequest> faqManagementRequestList = faqManagementResponse.getFaqManagementList();
			if (CommonUtil.isListNotNullAndEmpty(faqManagementRequestList)) {
				model.put("faqManagementRequestList", faqManagementRequestList);
				model.put(PGConstants.TOTAL_RECORDS, faqManagementResponse.getTotalNoOfRows());
			}
			model.put(PGConstants.FAQ_MANAGEMENT_REQUEST, faqManagementRequest);
		} catch (Exception e) {
			logger.error("ERROR:: FaqManagementController:: getPaginationList method", e);
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
		}
		logger.info("Exiting:: FaqManagementController:: getPaginationList method");
		return modelAndView;
	}

	
	@RequestMapping(value = FAQ_MANAGEMENT_STATUS, method = RequestMethod.POST)
	public ModelAndView changeFaqManagementStatus(HttpServletRequest request, HttpServletResponse response,
			final HttpSession session, Map<String, Object> model, @FormParam("faqId") final Long faqId,
			@FormParam("faqstatus") final String faqstatus, @FormParam("reason") final String reason) {

		logger.info("Entring ::FaqManagementController :: changeFaqManagementStatus method");

		ModelAndView modelAndView = new ModelAndView(FAQ_MANAGEMENT_SEARCH_PAGE);

		try {
			FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
			faqManagementRequest.setFaqId(faqId);
			faqManagementRequest.setStatus(faqstatus);
			FaqManagementResponse faqManagementResponse = faqManagementService
					.updateFaqManagement(faqManagementRequest);
			if (faqManagementResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
				String successData = messageSource.getMessage("prepaid.faq.success.update.message", null,
						LocaleContextHolder.getLocale());
				model.put(Constants.SUCESS, successData);
			} else {
				model.put(Constants.ERROR, faqManagementResponse.getErrorMessage());
			}
			modelAndView = showFaqManagementSearch(request, response, session, model);
		} catch (Exception e) {
			logger.error("ERROR:: FaqManagementController:: changeFaqManagementStatus method", e);
			model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
					null, LocaleContextHolder.getLocale()));
		}
		logger.info("Exiting :: FaqManagementController :: changeFaqManagementStatus method");
		return modelAndView;
	}

}
