package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.acquirer.admin.service.FeeReportService;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.FeeReportDao;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.model.FeeReportResponse;


@Service
public class FeeReportServiceImpl implements FeeReportService {
	
	private static Logger logger = Logger.getLogger(FeeReportServiceImpl.class);
	
	@Autowired
	FeeReportDao feeReportDao;

	@Override
	public FeeReportResponse fetchFeeTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException {
		logger.info("Entering :: FeeReportServiceImpl :: fetchFeeTransactions");
		feeTxnCodes(feeReportRequest);
		FeeReportResponse feeReportResponse = feeReportDao.fetchFeeTransactions(feeReportRequest);
		logger.info("Exiting :: FeeReportServiceImpl :: fetchFeeTransactions");
		return feeReportResponse;
	}

	private void feeTxnCodes(FeeReportRequest feeReportRequest) {
		List<String> txnCodeList = new ArrayList<>();
		txnCodeList.add(AccountTransactionCode.CC_ISO_FEE_CREDIT);
		feeReportRequest.setTransactionCodeList(txnCodeList);
	}

	@Override
	public FeeReportResponse fetchISOFeeTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException {
		feeTxnCodes(feeReportRequest);
		return feeReportDao.fetchISOFeeTransactions(feeReportRequest);
	}

	@Override
	public FeeReportResponse fetchIsoRevenueTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException {
		return feeReportDao.fetchISORevenueTransactions(feeReportRequest);
		
	}
	
	@Override
	public FeeReportResponse fetchMerchantRevenueTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException {
		return feeReportDao.fetchMerchantRevenueTransactions(feeReportRequest);
		
	}

	@Override
	public TransactionResponse getAllMatchedTxnsByEntityId(Long issuanceSettlementEntityId)
			throws ChatakAdminException {
		logger.info("Entering :: FeeReportServiceImpl :: getAllMatchedTxnsByEntityId");
		TransactionResponse response = new TransactionResponse();
		List<SettlementEntity> settlementTransactions = feeReportDao
				.getAllMatchedTxnsByEntityId(issuanceSettlementEntityId);
		logger.info("settlementTransactions : " + settlementTransactions.toString());
		if (StringUtil.isListNotNullNEmpty(settlementTransactions)) {
			response.setSettlementEntity(settlementTransactions);
		}
		logger.info("Exiting :: FeeReportServiceImpl :: getAllMatchedTxnsByEntityId");
		return response;
	}
	
	@Override
	public FeeReportResponse fetchPmRevenueTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException {
		return feeReportDao.fetchPmRevenueTransactions(feeReportRequest);
	}
	
}
