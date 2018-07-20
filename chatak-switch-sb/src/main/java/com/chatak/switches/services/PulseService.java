/**
 * 
 */
package com.chatak.switches.services;

import com.chatak.pg.bean.AdjustmentRequest;
import com.chatak.pg.bean.AdjustmentResponse;
import com.chatak.pg.bean.AuthRequest;
import com.chatak.pg.bean.AuthResponse;
import com.chatak.pg.bean.BalanceEnquiryResponse;
import com.chatak.pg.bean.CaptureRequest;
import com.chatak.pg.bean.CaptureResponse;
import com.chatak.pg.bean.CashBackRequest;
import com.chatak.pg.bean.CashBackResponse;
import com.chatak.pg.bean.CashWithdrawalRequest;
import com.chatak.pg.bean.CashWithdrawalResponse;
import com.chatak.pg.bean.NetworkRequest;
import com.chatak.pg.bean.NetworkResponse;
import com.chatak.pg.bean.PurchaseRequest;
import com.chatak.pg.bean.PurchaseResponse;
import com.chatak.pg.bean.RefundRequest;
import com.chatak.pg.bean.RefundResponse;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.ReversalResponse;
import com.chatak.pg.bean.VoidRequest;
import com.chatak.pg.bean.VoidResponse;
import com.chatak.switches.sb.exception.ServiceException;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jul 8, 2015 8:01:27 PM
 * @version 1.0
 */
public interface PulseService {
  
  /**
   * @param networkRequest
   * @return
   * @throws ServiceException
   */
  public NetworkResponse networkTransaction(NetworkRequest networkRequest) throws ServiceException;
  
  /**<<Method to get processor >>
   * @return
   */
  public String getProcessor();

  /**
   * Method to Authorise a payment transaction
   * 
   * @param authRequest
   * @return AuthResponse
   * @throws ServiceException
   */
  public AuthResponse authTransaction(AuthRequest authRequest) throws ServiceException;

  /**
   * Method to Capture the Authorised Payment Transaction
   * 
   * @param captureRequest
   * @return CaptureResponse
   * @throws ServiceException
   */
  public CaptureResponse captureTransaction(CaptureRequest captureRequest) throws ServiceException;

  /**
   * Method to Auth-Capture Payment Transaction
   * 
   * @param purchaseRequest
   * @return PurchaseResponse
   * @throws ServiceException
   */
  public PurchaseResponse purchaseTransaction(PurchaseRequest purchaseRequest) throws ServiceException;

  /**
   * Method to Adjust to successful Payment Transaction (Tip Adjustment, sale
   * amount adjustment)
   * 
   * @param adjustmentRequest
   * @return AdjustmentResponse
   * @throws ServiceException
   */
  public AdjustmentResponse adjustmentTransaction(AdjustmentRequest adjustmentRequest) throws ServiceException;

  /**
   * Method to Void/Cancel an successful Auth transaction
   * 
   * @param voidRequest
   * @return VoidResponse
   * @throws ServiceException
   */
  public VoidResponse voidTransaction(VoidRequest voidRequest) throws ServiceException;

  /**
   * Method to Reverse a transaction
   * 
   * @param reversalRequest
   * @return ReversalResponse
   * @throws ServiceException
   */
  public ReversalResponse reversalTransaction(ReversalRequest reversalRequest) throws ServiceException;

  /**
   * Method to Refund a transaction
   * 
   * @param reversalRequest
   * @return ReversalResponse
   * @throws ServiceException
   */
  public RefundResponse refundTransaction(RefundRequest refundRequest) throws ServiceException;
  

  /**
   * Mehtod to balance enquiry transaction
   * @param balanceEnquiryRequest
   * @return
   * @throws ServiceException
   */
  public BalanceEnquiryResponse balanceEnquiryTransaction(Request balanceEnquiryRequest)
      throws ServiceException;
  
  /**
   * Method to Cash withdrawal transaction
   * @param cashWithdrawalRequest
   * @return CashWithdrawalResponse
   * @throws ServiceException
   */
  public CashWithdrawalResponse cashWithdrawalTransaction(CashWithdrawalRequest cashWithdrawalRequest)
      throws ServiceException ;
  
  /**
   * Method to cashback transaction 
   * @param cashBackRequest
   * @return CashBackResponse
   * @throws ServiceException
   */
  public CashBackResponse cashBackTransaction(CashBackRequest cashBackRequest) throws ServiceException;
  
  /**
   * Method to update account 
   * 
   * @param merchantId,paymentMethod,txnAmount
   * @return 
   * @throws ServiceException
   */

  
  public CaptureResponse partialCaptureTransaction(CaptureRequest captureRequest) throws ServiceException;

}
