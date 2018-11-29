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
 * @Date: 20-Dec-2016
 * @Time: 11:38:56 pm
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_BANK_CURRENCY_MAPPING")
public class PGBankCurrencyMapping implements Serializable {

	private static final long serialVersionUID = 4150774875565233305L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_BANK_CURRENCY_MAPPING_ID", sequenceName = "SEQ_PG_BANK_CURRENCY_MAPPING")
	@GeneratedValue(generator = "SEQ_PG_BANK_CURRENCY_MAPPING_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PG_BANK_CUR_MAP_ID")
	private Long id;
	
	@Column(name = "BANK_ID")
	private Long bankId;
	
	@Column(name = "CURRENCY_CODE_ALPHA")
	private String currencyCodeAlpha;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public Long getBankId() {
		return bankId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
		this.currencyCodeAlpha = currencyCodeAlpha;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getCurrencyCodeAlpha() {
		return currencyCodeAlpha;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
