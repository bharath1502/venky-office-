package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.SearchRequest;

public class AdvancedFraudDTO extends SearchRequest implements Serializable{

  private static final long serialVersionUID = 4932834722205098382L;

  private Long id;

  private String filterType;

  private String filterOn;

  private String duration;

  private String transactionLimit;

  private String maxLimit;

  private String merchantCode;

  private String action;
  
  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the filterType
   */
  public String getFilterType() {
    return filterType;
  }
  
  /**
   * @return the duration
   */
  public String getDuration() {
    return duration;
  }

  /**
   * @param filterType
   *          the filterType to set
   */
  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * @return the filterOn
   */
  public String getFilterOn() {
    return filterOn;
  }
  
  /**
   * @param action the action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * @param filterOn
   *          the filterOn to set
   */
  public void setFilterOn(String filterOn) {
    this.filterOn = filterOn;
  }

  /**
   * @param duration
   *          the duration to set
   */
  public void setDuration(String duration) {
    this.duration = duration;
  }
  
  /**
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * @return the transactionLimit
   */
  public String getTransactionLimit() {
    return transactionLimit;
  }
  
  /**
   * @return the maxLimit
   */
  public String getMaxLimit() {
    return maxLimit;
  }

  /**
   * @param transactionLimit
   *          the transactionLimit to set
   */
  public void setTransactionLimit(String transactionLimit) {
    this.transactionLimit = transactionLimit;
  }

  /**
   * @param merchantCode
   *          the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }
  
  /**
   * @param maxLimit
   *          the maxLimit to set
   */
  public void setMaxLimit(String maxLimit) {
    this.maxLimit = maxLimit;
  }

}