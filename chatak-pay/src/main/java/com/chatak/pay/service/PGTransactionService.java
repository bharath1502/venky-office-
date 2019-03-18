/**
 * 
 */
package com.chatak.pay.service;

import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.SessionKeyRequest;
import com.chatak.pay.controller.model.SessionKeyResponse;
import com.chatak.pay.controller.model.TmkDataRequest;
import com.chatak.pay.controller.model.TmkDataResponse;
import com.chatak.pay.controller.model.TransactionHistoryResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.model.TransactionHistoryRequest;
import com.chatak.switches.sb.exception.ChatakInvalidTransactionException;

/**
 * @Author: Girmiti Software
 * @Date: Apr 24, 2015
 * @Time: 12:18:19 PM
 * @Version: 1.0
 * @Comments:
 */
public interface PGTransactionService {

  public Response processTransaction(TransactionRequest transactionRequest, PGMerchant merchant) throws ChatakInvalidTransactionException;
  
  public Response processLoadFundTransaction(TransactionRequest transactionRequest, PGMerchant merchant);

  public TransactionHistoryResponse getMerchantTransactionList(TransactionHistoryRequest transactionHistoryRequest);
  
  public SessionKeyResponse getSessionKeyForTmk(SessionKeyRequest sessionKeyRequest);
  
  public TmkDataResponse getTMKByDeviceSerialNumber(TmkDataRequest tmkDataRequest);
  
}
