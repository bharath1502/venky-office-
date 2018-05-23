/**
 * 
 */
package com.chatak.pay.controller.model;

import java.util.List;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 28-Apr-2015 10:39:25 AM
 * @version 1.0
 */
public class FeeDataResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -6735851087459291734L;
  
  private List<FeeData> fees;
  private Long txnAmount;
  private Long totalFeeAmt;
  private Long netAmount;
  /**
   * @return the fees
   */
  public List<FeeData> getFees() {
    return fees;
  }
  /**
   * @param fees the fees to set
   */
  public void setFees(List<FeeData> fees) {
    this.fees = fees;
  }
  /**
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
  }
  /**
   * @param txnAmount the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }
  /**
   * @return the totalFeeAmt
   */
  public Long getTotalFeeAmt() {
    return totalFeeAmt;
  }
  /**
   * @param totalFeeAmt the totalFeeAmt to set
   */
  public void setTotalFeeAmt(Long totalFeeAmt) {
    this.totalFeeAmt = totalFeeAmt;
  }
  /**
   * @return the netAmount
   */
  public Long getNetAmount() {
    return netAmount;
  }
  /**
   * @param netAmount the netAmount to set
   */
  public void setNetAmount(Long netAmount) {
    this.netAmount = netAmount;
  }

}
