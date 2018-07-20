package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
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
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;

@RestController
@RequestMapping(value = "/settlementServices", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class SettlementRestController {

	private Logger logger = Logger.getLogger(SettlementRestController.class);

	@Autowired
	private SettlementTxnService settlementTxnService;

	@RequestMapping(value = "/setSettlementData", method = RequestMethod.POST)
	public SettlementTxnResponse setSettlementData(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestBody SettlementTxnRequest settlementTxnRequest) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
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
				LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "SettlementTxnRequest is NULL");
			}
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, e.getMessage());
			txnResponse.setErrorCode(Constants.ERROR_CODE);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return txnResponse;

	}

}
