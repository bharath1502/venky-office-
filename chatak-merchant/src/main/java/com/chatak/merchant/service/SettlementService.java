/**
 * 
 */
package com.chatak.merchant.service;

import java.io.IOException;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.BulkSettlementResponse;
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
      String txnType, String status, String comments) throws ChatakMerchantException;

  public BulkSettlementResponse updateBulkSettlementStatus(
      SettlementActionDTOList settlementActionDTOList, String status, String comments)
          throws ChatakMerchantException;

  public PGAccountFeeLog postVirtualAccFee(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String partnerId, String mode, String programManagerId)
          throws ChatakMerchantException, IOException;


  public PGAccountFeeLog postVirtualAccFeeReversal(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String ciVirtualAccTxnId, String mode) throws IOException, HttpClientException;
}
