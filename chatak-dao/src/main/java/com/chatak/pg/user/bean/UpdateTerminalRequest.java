package com.chatak.pg.user.bean;

import java.io.Serializable;

public class UpdateTerminalRequest implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long id;

  private Long terminalId;

  private Long merchantId;

  private Long price;

  private String description;

  private String productId;

  private String createdBy;

  private String comments;

  private String updatedBy;

  private Integer status;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the terminalId
   */
  public Long getTerminalId() {
    return terminalId;
  }

  /**
   * @param terminalId
   *          the terminalId to set
   */
  public void setTerminalId(Long terminalId) {
    this.terminalId = terminalId;
  }

  /**
   * @return the merchantId
   */
  public Long getMerchantId() {
    return merchantId;
  }

  /**
   * @param price
   *          the price to set
   */
  public void setPrice(Long price) {
    this.price = price;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * @param merchantId
   *          the merchantId to set
   */
  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the price
   */
  public Long getPrice() {
    return price;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the productId
   */
  public String getProductId() {
    return productId;
  }

  /**
   * @param createdBy
   *          the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the comments
   */
  public String getComments() {
    return comments;
  }
  
  /**
   * @param productId
   *          the productId to set
   */
  public void setProductId(String productId) {
    this.productId = productId;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param comments
   *          the comments to set
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * @param updatedBy
   *          the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }
  
  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  public String validate() {
    String message = "";
    if(merchantId == null) {
      message += "Merchant_id is a required field";
    }
    else if(terminalId == null) {
      message += "Terminal_id is a required field";
    }
    return message;
  }
}
