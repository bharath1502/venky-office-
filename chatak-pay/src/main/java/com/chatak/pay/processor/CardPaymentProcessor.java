/**
 * 
 */
package com.chatak.pay.processor;

import org.jpos.iso.ISOMsg;
import org.springframework.dao.DataAccessException;

import com.chatak.pay.controller.model.CardDetails;
import com.chatak.pay.controller.model.PaymentDetails;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 21-Feb-2015 11:26:01 PM
 * @version 1.0
 */
public interface CardPaymentProcessor {

  public PGMerchant validMerchant(String mId);

  public PGMerchant validateMerchantIdAndTerminalId(String mId, String tId);

  public Response processCardPayment(ISOMsg isoMsg);

  public PGOnlineTxnLog initializeCardPayment(PaymentDetails paymentDetails,
      CardDetails cardDetails);

  /**
   * Method to check duplicate txn
   * 
   * @param merchantCode
   * @param orderId
   * @return
   * @throws DataAccessException
   */
  public boolean isDuplicateRequest(String merchantCode, String orderId) throws ChatakPayException;

  /**
   * Method to fetch account details
   * 
   * @param merchantId
   * @param paymentMethod
   * @param txnAmount
   * @return
   * @throws DataAccessException
   */

  public void updateAccount(String merchantId, String paymentMethod, Long txnAmount);

  /**
   * Method to check duplicate txn
   * 
   * @param merchantCode
   * @param orderId
   * @return
   * @throws DataAccessException
   */
  public boolean isDuplicateRequest(String merchantCode, String orderId, Long txnAmount,
      String invoiceNumber, String registerNumber, String txnType) throws ChatakPayException;

  /**
   * @param merchantCode
   * @param orderId
   * @param txnType
   * @return
   * @throws ChatakPayException
   */
  public boolean isDuplicateRequest(String merchantCode, String orderId, String txnType)
      throws ChatakPayException;

  /**
   * @param merchantCode
   * @param orderId
   * @param txnAmount
   * @param registerNumber
   * @param panData
   * @param txnType
   * @return
   * @throws ChatakPayException
   */
  public void duplicateRequest(String merchantCode, String orderId, Long txnAmount,
      String registerNumber, String panData, String txnType) throws ChatakPayException;

  public void duplicateInvoice(String merchantCode, String terminalId, String invoiceNumber)
      throws ChatakPayException, InvalidRequestException;

  public void duplicateOrderRequest(String merchantCode, String orderId, Long txnAmount,
      String invoiceNumber, String txnType) throws ChatakPayException;

  public String fetchCurrencyCodeNumeric(String mId) throws ChatakPayException;

  public PGMerchant validateMerchantId(String merchantId);
  
}
