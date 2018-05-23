package com.chatak.pg.user.bean;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 24-Apr-2015 12:55:01 PM
 * @version 1.0
 */
public class AddTerminalRequest extends Request {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long merchantId;

  private Long price;

  private String description;

  private String productId;

  private String createdBy;

  private String comments;

  private String updatedBy;

  private Integer status;

  public String validate() {
    String message = "";
    if(merchantId == null) {
      message += "Merchant_id is arequired field";
    }
    return message;
  }

  /**
   * @return the merchantId
   */
  public Long getMerchantId() {
    return merchantId;
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
   * @param comments
   *          the comments to set
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
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
   * @param status
   *          the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

}
