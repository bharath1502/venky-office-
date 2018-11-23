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
 * @Date: Sep 21, 2015
 * @Time: 2:33:50 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_ACCOUNT_FEE_LOG")
public class PGAccountFeeLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -645387378691034517L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_ACCOUNT_FEE_LOG_ID", sequenceName = "SEQ_PG_ACCOUNT_FEE_LOG")
	@GeneratedValue(generator = "SEQ_PG_ACCOUNT_FEE_LOG_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ENTITY_TYPE")
	private String entityType;
	
	@Column(name = "ENTITY_ID")
	private String entityId;

	@Column(name = "ACCOUNT_DESC")
	private String accountDesc;

	@Column(name = "ACCOUNT_NUM")
	private Long accountNum;

	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;

	@Column(name = "TRANSACTION_ID")
	private String transactionId;

	@Column(name = "PARENT_ENTITY_ID")
	private String parentEntityId;

	@Column(name = "MERCHANT_FEE")
	private Long merchantFee;
	
	@Column(name = "CHATAK_FEE")
	private Long chatakFee;
	
	@Column(name = "TXN_AMOUNT")
	private Long txnAmount;
	
	@Column(name = "FEE_POST_STATUS")
	private Integer feePostStatus;
	
	@Column(name = "PARTNER_ACCOUNT_NUM")
	private String partnerAccNum;

	@Column(name = "AGENT_ACCOUNT_NUM")
	private String agentAccNum;
	
	@Column(name = "ISSUANCE_MESSAGE")
	private String issuanceMessage;
	
	@Column(name = "ISSUANCE_FEE_TXN_ID")
	private String issuanceFeeTxnId;
	
	@Column(name = "FEE_TXN_DATE")
	private Timestamp feeTxnDate;
	
	@Column(name = "SPECIFIC_ACCOUNT_NUM")
	private String specificAccNumber;
	
	public String getAccountDesc() {
		return accountDesc;
	}
	
	public Long getId() {
		return id;
	}

	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}

	public Long getAccountNum() {
		return accountNum;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}

	public String getCategory() {
		return category;
	}
	
	public String getEntityId() {
		return entityId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}
	
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public String getEntityType() {
		return entityType;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

	public Long getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(Long merchantFee) {
		this.merchantFee = merchantFee;
	}

	public Long getChatakFee() {
		return chatakFee;
	}

	public void setChatakFee(Long chatakFee) {
		this.chatakFee = chatakFee;
	}

	public Long getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Integer getFeePostStatus() {
		return feePostStatus;
	}

	public void setFeePostStatus(Integer feePostStatus) {
		this.feePostStatus = feePostStatus;
	}

	public String getPartnerAccNum() {
		return partnerAccNum;
	}

	public void setPartnerAccNum(String partnerAccNum) {
		this.partnerAccNum = partnerAccNum;
	}

	public String getAgentAccNum() {
		return agentAccNum;
	}

	public void setAgentAccNum(String agentAccNum) {
		this.agentAccNum = agentAccNum;
	}

	public String getIssuanceMessage() {
		return issuanceMessage;
	}

	public void setIssuanceMessage(String issuanceMessage) {
		this.issuanceMessage = issuanceMessage;
	}

	public String getIssuanceFeeTxnId() {
		return issuanceFeeTxnId;
	}

	public void setIssuanceFeeTxnId(String issuanceFeeTxnId) {
		this.issuanceFeeTxnId = issuanceFeeTxnId;
	}

	public Timestamp getFeeTxnDate() {
		return feeTxnDate;
	}

	public void setFeeTxnDate(Timestamp feeTxnDate) {
		this.feeTxnDate = feeTxnDate;
	}

  /**
   * @return the specificAccNumber
   */
  public String getSpecificAccNumber() {
    return specificAccNumber;
  }

  /**
   * @param specificAccNumber the specificAccNumber to set
   */
  public void setSpecificAccNumber(String specificAccNumber) {
    this.specificAccNumber = specificAccNumber;
  }


}
