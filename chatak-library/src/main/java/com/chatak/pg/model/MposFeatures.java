package com.chatak.pg.model;

public class MposFeatures {

  private Long id;

  private String featureName;

  private String transactionType;

  private Boolean enabled;

  /**
   * @return the enabled
   */
  public Boolean getEnabled() {
    return enabled;
  }

  /**
   * @param enabled
   *          the enabled to set
   */
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return
   */
  @Override
  public String toString() {
    return "MposFeatures [id=" + id + ", featureName=" + featureName + ", transactionType=" + transactionType + "]";
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the featureName
   */
  public String getFeatureName() {
    return featureName;
  }

  /**
   * @param featureName
   *          the featureName to set
   */
  public void setFeatureName(String featureName) {
    this.featureName = featureName;
  }

  /**
   * @return the transactionType
   */
  public String getTransactionType() {
    return transactionType;
  }

  /**
   * @param transactionType
   *          the transactionType to set
   */
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

}