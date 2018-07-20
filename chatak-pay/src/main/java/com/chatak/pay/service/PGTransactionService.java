/**
 * 
 */
package com.chatak.pay.service;

import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pg.acq.dao.model.PGMerchant;

/**
 * @Author: Girmiti Software
 * @Date: Apr 24, 2015
 * @Time: 12:18:19 PM
 * @Version: 1.0
 * @Comments:
 */
public interface PGTransactionService {

  public Response processTransaction(TransactionRequest transactionRequest, PGMerchant merchant);
  
  public Response processLoadFundTransaction(TransactionRequest transactionRequest, PGMerchant merchant);

}
