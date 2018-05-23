/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 31, 2016
 * @Time: 7:25:09 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_DYNAMIC_MDR")
public class PGDynamicMDR {
	
	  @Id
	  /*@SequenceGenerator(name = "SEQ_PG_DYNAMIC_MDR_ID", sequenceName = "SEQ_PG_DYNAMIC_MDR")
	  @GeneratedValue(generator = "SEQ_PG_DYNAMIC_MDR_ID")*/
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "MDR_ID")
	  private Long id;
	  
	  @Column(name = "BIN_NUMBER")
	  private Long binNumber;

	  @Column(name = "ACCOUNT_TYPE")
	  private String accountType;
	  
	  @Column(name = "PRODUCT_TYPE")
	  private String productType;
	  
	  @Column(name = "TRANSACTION_TYPE")
	  private String transactionType;
	  
	  @Column(name = "PAYMENT_SCHEME")
	  private String paymentSchemeName;
	  
	  @Column(name = "BANK")
	  private String bankName;
	  
	  @Column(name = "SLAB")
	  private Double slab;
	  
	  @Column(name = "CREATED_DATE")
	  private Timestamp createdDate;
	  
	  @Column(name = "CREATED_BY")
	  private String createdBy;
	  
	  @Column(name = "UPDATED_BY")
	  private String updatedBy;
	  
	  @Column(name = "UPDATED_DATE")
	  private Timestamp updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBinNumber() {
		return binNumber;
	}

	public void setBinNumber(Long binNumber) {
		this.binNumber = binNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentSchemeName() {
		return paymentSchemeName;
	}

	public void setPaymentSchemeName(String paymentSchemeName) {
		this.paymentSchemeName = paymentSchemeName;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public Double getSlab() {
		return slab;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setSlab(Double slab) {
		this.slab = slab;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
		
}
