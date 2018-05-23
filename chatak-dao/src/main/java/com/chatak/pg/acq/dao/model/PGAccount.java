package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PG_ACCOUNT")
public class PGAccount implements Serializable{
  
  private static final long serialVersionUID = 1L;
  
  @Id
  /*@SequenceGenerator(name = "SEQ_PG_ACCOUNT_ID", sequenceName = "SEQ_PG_ACCOUNT")
  @GeneratedValue(generator = "SEQ_PG_ACCOUNT_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id; 
  
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "MERCHANT_BANK_ID")
  private PGMerchantBank pgMerchantBank;
  
  @Column(name = "ENTITY_ID")
  private String entityId;
  
  @Column(name = "ENTITY_TYPE")
  private String entityType;
  
  @Column(name = "ACCOUNT_DESC")
  private String accountDesc;
  
  @Column(name = "ACCOUNT_NUM")
  private Long accountNum;
  
  @Column(name = "CATEGORY")
  private String category;
  
  @Column(name = "CURRENT_BALANCE")
  private Long currentBalance;
  
  @Column(name = "AVAILABLE_BALANCE")
  private Long availableBalance;
  
  @Column(name = "CURRENCY")
  private String currency;
  
  @Column(name = "AUTO_PAYMENT_LIMIT")
  private BigDecimal autoPaymentLimit;
  
  @Column(name = "AUTO_PAYMENT_METHOD")
  private String autoPaymentMethod;
  
  @Column(name = "AUTO_TRANSFER_DAY")
  private String autoTransferDay;
  
  @Column(name = "STATUS")
  private String status;
  
  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

@Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;
  
  @Column(name = "FEE_BALANCE")
  private Long feeBalance;
  
  @Column(name = "REASON")
  private String reason;
  
  @Column(name = "AUTO_SETTLEMENT")
  private Integer autoSettlement;

  public Integer getAutoSettlement() {
		return autoSettlement;
	}

	public void setAutoSettlement(Integer autoSettlement) {
		this.autoSettlement = autoSettlement;
	}
  
  public Long getId() {
    return id;
  }

  /**
   * @return the entityId
   */
  public String getEntityId() {
    return entityId;
  }

  /**
   * @return the entityType
   */
  public String getEntityType() {
    return entityType;
  }

  /**
   * @return the accountDesc
   */
  public String getAccountDesc() {
    return accountDesc;
  }

  /**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @return the currentBalance
   */
  public Long getCurrentBalance() {
    return currentBalance;
  }

  /**
   * @return the availableBalance
   */
  public Long getAvailableBalance() {
    return availableBalance;
  }

  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @return the autoPaymentLimit
   */
  public BigDecimal getAutoPaymentLimit() {
    return autoPaymentLimit;
  }

  /**
   * @return the autoPaymentMethod
   */
  public String getAutoPaymentMethod() {
    return autoPaymentMethod;
  }


  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @param entityId the entityId to set
   */
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  /**
   * @param entityType the entityType to set
   */
  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  /**
   * @param accountDesc the accountDesc to set
   */
  public void setAccountDesc(String accountDesc) {
    this.accountDesc = accountDesc;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @param currentBalance the currentBalance to set
   */
  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }

  /**
   * @param availableBalance the availableBalance to set
   */
  public void setAvailableBalance(Long availableBalance) {
    this.availableBalance = availableBalance;
  }

  /**
   * @param currency the currency to set
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * @param autoPaymentLimit the autoPaymentLimit to set
   */
  public void setAutoPaymentLimit(BigDecimal autoPaymentLimit) {
    this.autoPaymentLimit = autoPaymentLimit;
  }

  /**
   * @param autoPaymentMethod the autoPaymentMethod to set
   */
  public void setAutoPaymentMethod(String autoPaymentMethod) {
    this.autoPaymentMethod = autoPaymentMethod;
  }


  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the accountNum
   */
  public Long getAccountNum() {
    return accountNum;
  }

  /**
   * @param accountNum the accountNum to set
   */
  public void setAccountNum(Long accountNum) {
    this.accountNum = accountNum;
  }

  /**
   * @return the feeBalance
   */
  public Long getFeeBalance() {
    return feeBalance;
  }

  /**
   * @param feeBalance the feeBalance to set
   */
  public void setFeeBalance(Long feeBalance) {
    this.feeBalance = feeBalance;
  }

  public String getAutoTransferDay() {
    return autoTransferDay;
  }

  public void setAutoTransferDay(String autoTransferDay) {
    this.autoTransferDay = autoTransferDay;
  }

/**
 * @return the reason
 */
public String getReason() {
	return reason;
}

/**
 * @param reason the reason to set
 */
public void setReason(String reason) {
	this.reason = reason;
}

/**
 * @return the pgMerchantBank
 */
public PGMerchantBank getPgMerchantBank() {
	return pgMerchantBank;
}

/**
 * @param pgMerchantBank the pgMerchantBank to set
 */
public void setPgMerchantBank(PGMerchantBank pgMerchantBank) {
	this.pgMerchantBank = pgMerchantBank;
}
  

}
