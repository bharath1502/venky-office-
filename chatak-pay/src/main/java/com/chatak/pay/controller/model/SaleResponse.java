/**
 * 
 */
package com.chatak.pay.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 26-Feb-2015 9:44:25 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -1520263770190575792L;

  private String txnId;

  private String txnReferenceId;

  private String txnAuthId;

  private PaymentDetails paymentDetails;

  /**
   * @return the txnId
   */
  public String getTxnId() {
    return txnId;
  }
  
  /**
   * @param txnReferenceId
   *          the txnReferenceId to set
   */
  public void setTxnReferenceId(String txnReferenceId) {
    this.txnReferenceId = txnReferenceId;
  }

  /**
   * @return the txnAuthId
   */
  public String getTxnAuthId() {
    return txnAuthId;
  }

  /**
   * @param txnId
   *          the txnId to set
   */
  public void setTxnId(String txnId) {
    this.txnId = txnId;
  }

  /**
   * @return the txnReferenceId
   */
  public String getTxnReferenceId() {
    return txnReferenceId;
  }

  /**
   * @param txnAuthId
   *          the txnAuthId to set
   */
  public void setTxnAuthId(String txnAuthId) {
    this.txnAuthId = txnAuthId;
  }

  /**
   * @return the paymentDetails
   */
  public PaymentDetails getPaymentDetails() {
    return paymentDetails;
  }

  /**
   * @param paymentDetails
   *          the paymentDetails to set
   */
  public void setPaymentDetails(PaymentDetails paymentDetails) {
    this.paymentDetails = paymentDetails;
  }
}
