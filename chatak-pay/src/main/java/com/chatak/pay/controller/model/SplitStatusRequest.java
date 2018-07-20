/**
 * 
 */
package com.chatak.pay.controller.model;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jun 5, 2015 7:01:08 PM
 * @version 1.0
 */
public class SplitStatusRequest extends Request {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long splitTxnAmount;
  
  private String splitRefNumber;

 
  /**
   * @return the splitTxnAmount
   */
  public Long getSplitTxnAmount() {
    return splitTxnAmount;
  }

  /**
   * @param splitTxnAmount the splitTxnAmount to set
   */
  public void setSplitTxnAmount(Long splitTxnAmount) {
    this.splitTxnAmount = splitTxnAmount;
  }

  /**
   * @return the splitRefNumber
   */
  public String getSplitRefNumber() {
    return splitRefNumber;
  }

  /**
   * @param splitRefNumber the splitRefNumber to set
   */
  public void setSplitRefNumber(String splitRefNumber) {
    this.splitRefNumber = splitRefNumber;
  }
  
  

}
