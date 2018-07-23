/**
 * 
 */
package com.chatak.merchant.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.ChatakMerchantErrorCode;
import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.pg.util.Constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 20-Feb-2015 7:00:40 PM
 * @version 1.0
 */
@Controller
public class PayController implements URLMappingConstants {

	private Logger logger = Logger.getLogger(PayController.class);

	/**
	 * Method to show Payment Progress page
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param mId
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.POST)
	public ModelAndView showPaymentProcessing(HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@PathVariable
			String mId) {
		logger.debug("Entering:: PayController:: showPayment method");
		ModelAndView modelAndView = new ModelAndView("merchant");
		try {
			logger.info("Entering:: PayController:: showPaymentProcessing method");
		}
		catch(Exception e) {
			logger.error("ERROR:: PayController:: showPaymentProcessing method", e);
			modelAndView.addObject(Constants.ERROR, ChatakMerchantErrorCode.TXN_0997.value());
		}
		logger.debug("Exiting:: PayController:: showPaymentProcessing method");
		return modelAndView;
	}
}
