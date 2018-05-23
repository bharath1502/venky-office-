/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chatak.pg.bean.SearchRequest;

/**
 * @Author: Girmiti Software
 * @Date: Aug 20, 2016
 * @Time: 3:10:03 PM
 * @Version: 1.0
 * @Comments:
 *
 */

@Entity
@Table(name = "PG_TRANSACTION_CATEGORY_CODES")
public class PGTransactionCategoryCode extends SearchRequest implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3436901422409168596L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "TRANSACTION_CATEGORY_CODE")
	private String transactionCategoryCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionCategoryCode() {
		return transactionCategoryCode;
	}

	public void setTransactionCategoryCode(String transactionCategoryCode) {
		this.transactionCategoryCode = transactionCategoryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
