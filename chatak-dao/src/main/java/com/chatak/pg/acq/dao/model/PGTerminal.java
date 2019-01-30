package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 24-Apr-2015 11:20:47 AM
 * @version 1.0
 */
@Entity
@Table(name = "PG_TERMINAL")
public class PGTerminal implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -357229254946462491L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_TERMINAL_ID", sequenceName = "SEQ_PG_TERMINAL")
  @GeneratedValue(generator = "SEQ_PG_TERMINAL_ID")*/
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TERMINAL_ID")
  private Long terminalId;

  @Column(name = "MERCHANT_ID")
  private Long merchantId;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "PRICE")
  private Long price;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PRODUCT_ID")
  private String productId;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;
  
  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }
  
  /**
   * @param terminalId the terminalId to set
   */
  public void setTerminalId(Long terminalId) {
    this.terminalId = terminalId;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the comments
   */
  public String getComments() {
    return comments;
  }

  /**
   * @return the merchantId
   */
  public Long getMerchantId() {
    return merchantId;
  }
  
  /**
   * @return the terminalId
   */
  public Long getTerminalId() {
    return terminalId;
  }

  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @param comments the comments to set
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * @return the price
   */
  public Long getPrice() {
    return price;
  }
  
  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(Long price) {
    this.price = price;
  }

  /**
   * @return the productId
   */
  public String getProductId() {
    return productId;
  }
  
  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param productId the productId to set
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
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

}
