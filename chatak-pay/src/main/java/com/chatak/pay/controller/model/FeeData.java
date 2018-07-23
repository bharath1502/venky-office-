/**
 * 
 */
package com.chatak.pay.controller.model;

import com.chatak.pg.enums.CardAssociationEnum;
import com.chatak.pg.enums.FeeTypeEnum;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 28-Apr-2015 10:39:53 AM
 * @version 1.0
 */
public class FeeData {

  private String feeCode;
  private String feeDescription;
  private FeeTypeEnum feeType;
  private Long feeValue;
  private String amountRange;
  private CardAssociationEnum cardType;
  /**
   * @return the feeCode
   */
  public String getFeeCode() {
    return feeCode;
  }
  /**
   * @param feeCode the feeCode to set
   */
  public void setFeeCode(String feeCode) {
    this.feeCode = feeCode;
  }
  /**
   * @return the feeDescription
   */
  public String getFeeDescription() {
    return feeDescription;
  }
  /**
   * @param feeDescription the feeDescription to set
   */
  public void setFeeDescription(String feeDescription) {
    this.feeDescription = feeDescription;
  }
  /**
   * @return the feeType
   */
  public FeeTypeEnum getFeeType() {
    return feeType;
  }
  /**
   * @param feeType the feeType to set
   */
  public void setFeeType(FeeTypeEnum feeType) {
    this.feeType = feeType;
  }
  /**
   * @return the feeValue
   */
  public Long getFeeValue() {
    return feeValue;
  }
  /**
   * @param feeValue the feeValue to set
   */
  public void setFeeValue(Long feeValue) {
    this.feeValue = feeValue;
  }
  /**
   * @return the cardType
   */
  public CardAssociationEnum getCardType() {
    return cardType;
  }
  /**
   * @param cardType the cardType to set
   */
  public void setCardType(CardAssociationEnum cardType) {
    this.cardType = cardType;
  }
  /**
   * @return the amountRange
   */
  public String getAmountRange() {
    return amountRange;
  }
  /**
   * @param amountRange the amountRange to set
   */
  public void setAmountRange(String amountRange) {
    this.amountRange = amountRange;
  }
}
