package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class OtherCommissionDTO extends SearchRequest
{

  private static final long serialVersionUID = 3625361789753626291L;

  private Long id;
  
  private Long commissionProgramId;
  
  private String commissionType;
  
  private String flatFee;
  
  private String fromValue;
  
  private String toValue;
  
  private String amount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCommissionType() {
    return commissionType;
  }

  public void setCommissionType(String commissionType) {
    this.commissionType = commissionType;
  }

 

  public Long getCommissionProgramId() {
    return commissionProgramId;
  }

  public void setCommissionProgramId(Long commissionProgramId) {
    this.commissionProgramId = commissionProgramId;
  }

  /**
   * @return the flatFee
   */
  public String getFlatFee() {
    return flatFee;
  }

  /**
   * @param flatFee the flatFee to set
   */
  public void setFlatFee(String flatFee) {
    this.flatFee = flatFee;
  }

  /**
   * @return the fromValue
   */
  public String getFromValue() {
    return fromValue;
  }

  /**
   * @param fromValue the fromValue to set
   */
  public void setFromValue(String fromValue) {
    this.fromValue = fromValue;
  }

  /**
   * @return the toValue
   */
  public String getToValue() {
    return toValue;
  }

  /**
   * @param toValue the toValue to set
   */
  public void setToValue(String toValue) {
    this.toValue = toValue;
  }

  /**
   * @return the amount
   */
  public String getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(String amount) {
    this.amount = amount;
  }

  
}
