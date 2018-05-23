/**
 * 
 */
package com.chatak.pay.controller.model;


/**
 *
 * << Data to be used for split transaction process >>
 *
 * @author Girmiti Software
 * @date Jun 6, 2015 1:19:57 PM
 * @version 1.0
 */
public class SplitTxnData {
  
  private Long splitAmount;
  private String refMaskedPAN;//Not using as refMobile number used for split transaction completion 
  private Long refMobileNumber;
  /**
   * @return the splitAmount
   */
  public Long getSplitAmount() {
    return splitAmount;
  }
  /**
   * @param splitAmount the splitAmount to set
   */
  public void setSplitAmount(Long splitAmount) {
    this.splitAmount = splitAmount;
  }
  
  /**
   * @return the refMaskedPAN
   */
  public String getRefMaskedPAN() {
    return refMaskedPAN;
  }
  /**
   * @param refMaskedPAN the refMaskedPAN to set
   */
  public void setRefMaskedPAN(String refMaskedPAN) {
    this.refMaskedPAN = refMaskedPAN;
  }
  public Long getRefMobileNumber() {
    return refMobileNumber;
  }
  /**
   * @param refMobileNumber the refMobileNumber to set
   */
  public void setRefMobileNumber(Long refMobileNumber) {
    this.refMobileNumber = refMobileNumber;
  }
 

}
