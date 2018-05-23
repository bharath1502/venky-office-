package com.chatak.pay.controller.model;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 26-Feb-2015 10:02:45 AM
 * @version 1.0
 */
public class VoidResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 5710568674955077931L;
  
  private String txnId;

  private String txnReferenceId;

  private String txnAuthId;

  /**
   * @return the txnId
   */
  public String getTxnId() {
    return txnId;
  }

  /**
   * @param txnId the txnId to set
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
   * @param txnReferenceId the txnReferenceId to set
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
   * @param txnAuthId the txnAuthId to set
   */
  public void setTxnAuthId(String txnAuthId) {
    this.txnAuthId = txnAuthId;
  }

}
