package com.chatak.merchant.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.service.FaqManagementService;
import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.user.bean.FaqManagementResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

@Controller
public class FaqManagementController implements URLMappingConstants {

	private static Logger logger = LogManager.getLogger(FaqManagementController.class);

	@Autowired
	FaqManagementService faqManagementService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = SHOW_FAQ_MANAGEMENT_PAGE, method = RequestMethod.GET)
	public ModelAndView showFaqManagement(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map model) {
		logger.info("Entering :::: FaqManagementController :::: showFaqManagement method");
		ModelAndView modelAndView = new ModelAndView(SHOW_FAQ_MANAGEMENT_PAGE);

		try {
			FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
			model.put("faqManagementRequest", faqManagementRequest);
		} catch (Exception e) {
			logger.info("ERROR:::: FaqManagementController:::: showFaqManagement method", e);
		}
		logger.info("Exiting:::: FaqManagementController:::: showFaqManagement method");
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = SEARCH_FAQ_MANAGEMENT_REPORT, method = RequestMethod.POST)
	public ModelAndView processFaqManagementSearch(HttpServletRequest request,
			FaqManagementRequest faqManagementRequest, HttpServletResponse response, Map<String, Object> model,
			HttpSession session) {
		logger.info("Entering:: FaqManagementController:: processFaqManagementSearch method");
		ModelAndView modelAndView = new ModelAndView(SHOW_FAQ_MANAGEMENT_PAGE);
		try {
			faqManagementRequest.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			FaqManagementResponse faqManagementResponse = faqManagementService
					.searchFaqManagement(faqManagementRequest);
			List<FaqManagementRequest> faqManagementRequestList = faqManagementResponse.getFaqManagementList();
			if (CommonUtil.isListNotNullAndEmpty(faqManagementRequestList)) {
				modelAndView.addObject("faqManagementRequestList", faqManagementRequestList);
			}
		} catch (Exception e) {
			logger.error("ERROR:: FaqManagementController:: processFaqManagementSearch method", e);
		}
		modelAndView.addObject("faqManagementRequest", faqManagementRequest);
		logger.info("Exiting:: FaqManagementController:: processFaqManagementSearch method");
		return modelAndView;
	}
}