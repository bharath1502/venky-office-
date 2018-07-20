package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.model.FeeReportResponse;

public interface FeeReportService {
	
	public FeeReportResponse fetchFeeTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException;
	
	public FeeReportResponse fetchISOFeeTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException;
	
	public FeeReportResponse fetchIsoRevenueTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException;
	
	public TransactionResponse getAllMatchedTxnsByEntityId(Long issuanceSettlementEntityId) throws ChatakAdminException;
	
	public FeeReportResponse fetchMerchantRevenueTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException;
	
	public FeeReportResponse fetchPmRevenueTransactions(FeeReportRequest feeReportRequest) throws ChatakAdminException;

}
