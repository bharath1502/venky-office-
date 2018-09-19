package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.controller.model.SettlementTxnRequest;
import com.chatak.pay.service.SettlementTxnService;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.SettlementTxnResponse;
import com.chatak.pg.util.Constants;

@RestController
@RequestMapping(value = "/settlementServices", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class SettlementRestController {

	private static Logger logger = LogManager.getLogger(SettlementRestController.class);

	@Autowired
	private SettlementTxnService settlementTxnService;

	@RequestMapping(value = "/setSettlementData", method = RequestMethod.POST)
	public SettlementTxnResponse setSettlementData(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestBody SettlementTxnRequest settlementTxnRequest) {
		logger.info("Entering :: SettlementRestController :: setSettlementData");
		SettlementTxnResponse txnResponse = new SettlementTxnResponse();
		try {
			if (null != settlementTxnRequest) {
				PGIssSettlementData settlementData = new PGIssSettlementData();
				settlementData.setProgramManagerId(settlementTxnRequest.getProgramManagerId());
				settlementData.setTotalAmount(settlementTxnRequest.getTotalAmount());
				settlementData.setBatchDate(settlementTxnRequest.getBatchDate());
				settlementData.setTotalTxnCount(settlementTxnRequest.getTotalTxnCount());
				settlementData.setProgramManagerName(settlementTxnRequest.getProgramManagerName());
				settlementData.setStatus(PGConstants.S_STATUS_PENDING);
				txnResponse = settlementTxnService.saveIssSettlementData(settlementData);
			} else {
			  logger.info("SettlementTxnRequest is NULL");
			}
		} catch (Exception e) {
			logger.error("Error :: SettlementRestController :: setSettlementData : " + e.getMessage(), e);
			txnResponse.setErrorCode(Constants.ERROR_CODE);
		}
		logger.info("Exiting :: SettlementRestController :: setSettlementData");
		return txnResponse;

	}

}
