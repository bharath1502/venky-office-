package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.controller.model.TransactionHistoryResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.service.MerchantISOOnboardingServices;
import com.chatak.pay.controller.model.MerchantIsoOnboardingRequest;
import com.chatak.pay.controller.model.MerchantIsoOnboardingResponse;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.TransactionHistoryRequest;
import com.chatak.pg.util.Constants;

@RestController
@RequestMapping(value = "/merchantIsoOnboardingServices", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class MerchantIsoOnbordingRestController {

	private static Logger logger = LogManager.getLogger(MerchantIsoOnbordingRestController.class);
	
	@Autowired
	private MerchantISOOnboardingServices merchantISOOnboardingServices;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/fetchPMsByCurrency", method = RequestMethod.POST)
	public MerchantIsoOnboardingResponse fetchPMsByCurrency(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestBody MerchantIsoOnboardingRequest merchantIsoOnboardingRequest) {
		logger.info("Entering:: MerchantIsoOnbordingRestController:: fetchPMsByCurrency method");
		MerchantIsoOnboardingResponse merchantOnboardingResponse = new MerchantIsoOnboardingResponse();
		try {
			merchantOnboardingResponse = merchantISOOnboardingServices.fetchPmsByCurrency(merchantIsoOnboardingRequest.getCurrency());
			if (null != merchantOnboardingResponse
					&& merchantOnboardingResponse.getErrorCode().equals(Constants.ERROR_CODE)) {
				merchantOnboardingResponse = new MerchantIsoOnboardingResponse();
				merchantOnboardingResponse.setErrorCode(Constants.ERROR_CODE);
				merchantOnboardingResponse.setErrorMessage(
						messageSource.getMessage("chatak.transaction.error", null, LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
			logger.error("Error :: MerchantIsoOnbordingRestController :: fetchPMsByCurrency", e);
		}
		logger.info("Exiting:: MerchantIsoOnbordingRestController:: fetchPMsByCurrency method");
		return merchantOnboardingResponse;
	}
	
	@RequestMapping(value = "/fetchIsoByPmId", method = RequestMethod.POST)
	public MerchantIsoOnboardingResponse fetchIsoByPmId(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestBody MerchantIsoOnboardingRequest merchantIsoOnboardingRequest) {
		logger.info("Entering:: MerchantIsoOnbordingRestController:: fetchIsoByPmId method");
		MerchantIsoOnboardingResponse merchantOnboardingResponse = new MerchantIsoOnboardingResponse();
		try {
			merchantOnboardingResponse = merchantISOOnboardingServices.fetchIsoByPmId(merchantIsoOnboardingRequest.getPmId());
			if (null != merchantOnboardingResponse
					&& merchantOnboardingResponse.getErrorCode().equals(Constants.ERROR_CODE)) {
				merchantOnboardingResponse = new MerchantIsoOnboardingResponse();
				merchantOnboardingResponse.setErrorCode(Constants.ERROR_CODE);
				merchantOnboardingResponse.setErrorMessage(
						messageSource.getMessage("chatak.transaction.error", null, LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
			logger.error("Error :: MerchantIsoOnbordingRestController :: fetchIsoByPmId", e);
		}
		logger.info("Exiting:: MerchantIsoOnbordingRestController:: fetchIsoByPmId method");
		return merchantOnboardingResponse;
	}
}
