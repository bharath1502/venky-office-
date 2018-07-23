package com.chatak.merchant.service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.GetTransactionResponse;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.model.VirtualTerminalAdjustmentResponse;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalCaptureDTO;
import com.chatak.pg.model.VirtualTerminalPreAuthDTO;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalSaleDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 16-Mar-2015 6:46:12 PM
 * @version 1.0
 */
public interface RestPaymentService {

  /**
   * @param terminalVoidDTO
   * @return
   * @throws ChatakPayException
   */
  public TransactionResponse doVoid(VirtualTerminalVoidDTO terminalVoidDTO) throws ChatakPayException;

  /**
   * @param terminalPreAuthDTO
   * @return
   * @throws ChatakPayException
   */
  public TransactionResponse doPreAuth(VirtualTerminalPreAuthDTO terminalPreAuthDTO) throws ChatakPayException;

  /**
   * @param terminalCaptureDTO
   * @return
   * @throws ChatakPayException
   */
  public TransactionResponse doPreAuthCapture(VirtualTerminalCaptureDTO terminalCaptureDTO) throws ChatakPayException;

  /**
   * @param terminalRefundDTO
   * @return
   * @throws ChatakPayException
   */
  public TransactionResponse doRefund(VirtualTerminalRefundDTO terminalRefundDTO) throws ChatakPayException;

  /**
   * @param terminalAdjustmentDTO
   * @return
   * @throws ChatakPayException
   */
  public VirtualTerminalAdjustmentResponse doAdjust(VirtualTerminalAdjustmentDTO terminalAdjustmentDTO) throws ChatakPayException;

  /**
   * @param terminalSaleDTO
   * @return
   * @throws ChatakPayException
   */
  public TransactionResponse doSale(VirtualTerminalSaleDTO terminalSaleDTO) throws ChatakPayException;

  /**
   * @param merchantId
   * @param terminalId
   * @param authId
   * @param cardNum
   * @param invoiceNum
   * @return
   * @throws ChatakPayException
   * @throws Exception
   */
  public GetTransactionResponse getTransaction(String merchantId,
                                               String terminalId,
                                               String authId,
                                               String cardNum,
                                               String invoiceNum);

  /**
   * @param merchantId
   * @return
   */
  public GetMerchantDetailsResponse getMerchantIdAndTerminalId(String merchantId) throws ChatakPayException;

  /**
   * @param merchantId
   * @param terminalId
   * @param refId
   * @param txnType
   * @return
   * @throws ChatakPayException
   * @throws Exception
   */
  public GetTransactionResponse getTransactionByRefId(String merchantId, String terminalId, String refId, String txnType) throws ChatakPayException;
  
  public GetTransactionResponse getTransactionByRefIdForRefund(String merchantId, String terminalId, 
		  String refId, String txnType) throws ChatakMerchantException;
  
  
  public TransactionResponse processPopupVoidOrRefund(TransactionRequest transactionRequest) throws ChatakMerchantException;

}
