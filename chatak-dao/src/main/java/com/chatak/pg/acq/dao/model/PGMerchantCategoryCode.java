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
 * @Date: Aug 22, 2016
 * @Time: 2:56:43 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_MERCHANT_CATEGORY_CODES")
public class PGMerchantCategoryCode {

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_MERCHANT_CATEGORY_CODES_ID", sequenceName = "SEQ_PG_MERCHANT_CATEGORY_CODES")
	@GeneratedValue(generator = "SEQ_PG_MERCHANT_CATEGORY_CODES_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MERCHANT_CATEGORY_CODE")
	private String merchantCategoryCode;

	@Column(name = "TRANSACTION_CATEGORY_CODE")
	private String selectedTcc;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "REASON")
	private String reason;

	public Long getId() {
		return id;
	}
	
	public String getSelectedTcc() {
		return selectedTcc;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCategoryCode() {
		return merchantCategoryCode;
	}
	
	public void setSelectedTcc(String selectedTcc) {
		this.selectedTcc = selectedTcc;
	}

	public void setMerchantCategoryCode(String merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public String getReason() {
		return reason;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
