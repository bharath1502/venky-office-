/**
 * 
 */
package com.chatak.pay.service;

import com.chatak.pay.controller.model.AccountBalanceInquiryRequest;
import com.chatak.pay.controller.model.AccountBalanceInquiryResponse;
import com.chatak.pay.controller.model.AccountTransactionHistory;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.exception.ChatakPayException;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 19-May-2016 3:02:20 pm
 * @version 1.0
 */
public interface AccountService {

  public Response validate(Long accountNumber) throws ChatakPayException;
  
  public AccountBalanceInquiryResponse balanceInquiry(AccountBalanceInquiryRequest accountBalanceInquiryRequest) throws ChatakPayException;
  
  public AccountTransactionHistory getAccountHistory(Long accountNumber) throws ChatakPayException;	
}
