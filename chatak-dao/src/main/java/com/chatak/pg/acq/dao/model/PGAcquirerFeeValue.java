/**
 * 
 */
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
 * @Author: Girmiti Software
 * @Date: Feb 6, 2016
 * @Time: 11:56:30 AM
 * @Version: 1.0
 * @Comments: Model to store the acquirer fee values corresponding to fee
 *            program
 */
@Entity
@Table(name = "PG_ACQUIRER_FEE_VALUE")
public class PGAcquirerFeeValue implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8006951488759388758L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_ACQUIRER_FEE_VALUE_ID", sequenceName = "SEQ_PG_ACQUIRER_FEE_VALUE")
  @GeneratedValue(generator = "SEQ_PG_ACQUIRER_FEE_VALUE_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PG_ACQUIRER_FEE_VALUE_ID")
  private Long id;

  @Column(name = "FEE_PROGRAM_ID")
  private Long feeProgramId;

  @Column(name = "CARD_TYPE")
  private String cardType;

  @Column(name = "FEE_FLAT_VALUE")
  private Long flatFee;

  @Column(name = "FEE_PERCENTAGE_ONLY")
  private Double feePercentageOnly;

  @Column(name = "ACCOUNT_NUMBER")
  private String accountNumber;

  @Column(name = "ACCOUNT_TYPE")
  private String accountType;

  @Column(name = "CREATED_DATE", updatable = false)
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "CREATED_BY", updatable = false)
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "STATUS")
  private String status;

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
   * @return the feeProgramId
   */
  public Long getFeeProgramId() {
    return feeProgramId;
  }

  /**
   * @param feeProgramId
   *          the feeProgramId to set
   */
  public void setFeeProgramId(Long feeProgramId) {
    this.feeProgramId = feeProgramId;
  }

  /**
   * @return the flatFee
   */
  public Long getFlatFee() {
    return flatFee;
  }

  /**
   * @param flatFee
   *          the flatFee to set
   */
  public void setFlatFee(Long flatFee) {
    this.flatFee = flatFee;
  }

  /**
   * @return the feePercentageOnly
   */
  public Double getFeePercentageOnly() {
    return feePercentageOnly;
  }

  /**
   * @param feePercentageOnly
   *          the feePercentageOnly to set
   */
  public void setFeePercentageOnly(Double feePercentageOnly) {
    this.feePercentageOnly = feePercentageOnly;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }
  
  /**
   * @return the accountNumber
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * @param createdDate
   *          the createdDate to set
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
   * @param accountNumber the accountNumber to set
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * @param updatedDate
   *          the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }
  
  /**
   * @return the accountType
   */
  public String getAccountType() {
    return accountType;
  }

  /**
   * @param createdBy
   *          the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }
  
  /**
   * @param cardType
   *          the cardType to set
   */
  public void setCardType(String cardType) {
    this.cardType = cardType;
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
  public String getStatus() {
    return status;
  }
  
  /**
   * @return the cardType
   */
  public String getCardType() {
    return cardType;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @param accountType the accountType to set
   */
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

}
