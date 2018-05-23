/**
 * 
 */
package com.chatak.pay.service;

import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.SplitStatusRequest;
import com.chatak.pay.controller.model.SplitStatusResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.controller.model.TransactionResponse;
import com.chatak.pay.exception.SplitTransactionException;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jun 5, 2015 9:56:52 PM
 * @version 1.0
 */
public interface PGSplitTransactionService {

  public Response processLogSplitTransaction(TransactionRequest transactionRequest,
                                             TransactionResponse transactionResponse) throws SplitTransactionException;

  public SplitStatusResponse getSplitTxnStatus(SplitStatusRequest splitStatusRequest);

  public String validateSplitTransaction(TransactionRequest transactionRequest) throws SplitTransactionException;

  public void updateSplitTransactionLog(TransactionRequest transactionRequest, TransactionResponse transactionResponse);

}
