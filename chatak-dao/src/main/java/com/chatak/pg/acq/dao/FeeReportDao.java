/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.model.FeeReportResponse;

/**
 * @Author: Girmiti Software
 * @Date: Jun 26, 2018
 * @Time: 12:42:24 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface FeeReportDao {
	
	public FeeReportResponse fetchFeeTransactions(FeeReportRequest feeReportRequest);
	
	public FeeReportResponse fetchISOFeeTransactions(FeeReportRequest feeReportRequest);
	
	public FeeReportResponse fetchISORevenueTransactions(FeeReportRequest feeReportRequest);
	
	public List<SettlementEntity> getAllMatchedTxnsByEntityId(Long issuanceSettlementEntityId);
	
	public FeeReportResponse fetchMerchantRevenueTransactions(FeeReportRequest feeReportRequest);
	
	public FeeReportResponse fetchPmRevenueTransactions(FeeReportRequest feeReportRequest);

}
