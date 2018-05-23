/**
 * 
 */
package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.constants.URLMappingConstants;
import com.chatak.pay.controller.model.AccountBalanceInquiryRequest;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.service.AccountService;

/**
 *
 * Class provides the REST services for Merchant Account.
 *
 * @author Girmiti Software
 * @date 19-May-2016 1:21:13 pm
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/account", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class AccountRestController extends BaseController implements URLMappingConstants, Constant {

	private Logger logger = Logger.getLogger(AccountRestController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/validate/{accNumber}", method = RequestMethod.GET)
	public Response validate(HttpServletRequest request, @PathVariable Long accNumber) {
		logger.debug("Entering:: AccountRestController:: validate method");
		try {
			String ipPort = request.getRemoteAddr() + ":" + request.getRemotePort();
			logger.info("Requested IP Address: "+ipPort);
			logger.info("Account Number"+accNumber);
			return accountService.validate(accNumber);
		}
		catch(Exception e) {
			logger.error("ERROR:: AccountRestController:: validate method", e);
		}

		logger.debug("Exiting:: AccountRestController:: validate method");

		return null;
	}

	@RequestMapping(value = "/balanceInquiry", method = RequestMethod.POST)
	public Response balanceInquiry(HttpServletRequest request, @RequestBody AccountBalanceInquiryRequest accountBalanceInquiryRequest) {
		logger.debug("Entering:: AccountRestController:: balanceInquiry method");

		try {
			return accountService.balanceInquiry(accountBalanceInquiryRequest);

		}
		catch(Exception e) {
			logger.error("ERROR:: AccountRestController:: balanceInquiry method", e);
		}

		return null;
	}

	@RequestMapping(value = "/history/{accNumber}", method = RequestMethod.GET)
	public Response getAccountHistory(HttpServletRequest request, @PathVariable Long accNumber) {
		logger.debug("Entering:: AccountRestController:: validate method");
		try {
			logger.info("Account Number"+accNumber);
			return accountService.getAccountHistory(accNumber);
		}catch(Exception e) {
			logger.error("ERROR:: AccountRestController:: getAccountHistory method", e);
		}
		logger.debug("Exiting:: AccountRestController:: getAccountHistory method");
		return null;
	}

}
