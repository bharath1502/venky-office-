/**
 * 
 */
package com.chatak.acquirer.admin.service;

import java.io.IOException;
import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactionEntity;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.model.BulkSettlementResponse;
import com.chatak.pg.model.Response;
import com.chatak.pg.model.SettlementActionDTOList;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 15, 2015 4:17:38 PM
 * @version 1.0
 */
public interface SettlementService {

  public boolean updateSettlementStatus(String merchantId, String terminalId, String txnId,
      String txnType, String status, String comments, String userName,String timeZoneOffset,String timeZoneRegion) throws ChatakAdminException;

  public BulkSettlementResponse updateBulkSettlementStatus(
      SettlementActionDTOList settlementActionDTOList, String status, String comments, String userName)
          throws ChatakAdminException;

  public PGAccountFeeLog postVirtualAccFee(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String partnerId, String mode, String programManagerId)
          throws ChatakAdminException, IOException;


  public PGAccountFeeLog postVirtualAccFeeReversal(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String ciVirtualAccTxnId, String mode) throws ChatakAdminException, IOException;
  
  public List<SettlementEntity> getPgTransactions(String merchantId, String terminalId,
	      String issuerTxnRefNum, String transactionId);
  
  public Response saveIssuanceSettlementTransaction(IssuanceSettlementTransactionEntity pgSettlementTransaction, Integer batchCount, Integer batchSize) 
      throws InstantiationException, IllegalAccessException, ChatakAdminException;
  
  public void deleteAllIssuanceSettlementData(String programManagerId) throws PrepaidAdminException;
  
  public List<PGTransaction> getPGTransactionListNotInAcquiring(String batchId, List<String> pgTxnIds);
  
  public List<PGAccountTransactions> getPGAccTransactionsByTxnId(String pgTxnId);
  
  public void logRevenueAccountTransaction(String batchId, Long accountNumber, Long currentBalance, Long entityId, 
			Long amountToTransfer, String transactionCode, String entityType,
			String timeZoneOffset, String timeZoneRegion, String accountTransactionId);
  
  public List<PGAccountTransactions> getAccountTransactionsOnTransactionId(String pgTransactionId);

  public List<ProgramManager> findByProgramMangerName(String pmName);
}

