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
 * @Date: Jun 30, 2016
 * @Time: 2:49:20 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_MERCHANT_CURRENCY_MAPPING")
public class PGMerchantCurrencyMapping implements Serializable {
	
	private static final long serialVersionUID = 3377782740320110549L;
	
	
	

	
	@Id
	/*@SequenceGenerator(name="seq_pgmerchant_cur_map_id", sequenceName="SEQ_PG_MERCHANT_CUR_MAP_ID")
	@GeneratedValue(generator="seq_pgmerchant_cur_map_id")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PG_MERCHANT_CUR_MAP_ID")
	private Long pgMerchantCurMapId;
	
	@Column(name = "MERCHANT_CODE")
	private String merchantCode;
	
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getPgMerchantCurMapId() {
		return pgMerchantCurMapId;
	}

	public void setPgMerchantCurMapId(Long pgMerchantCurMapId) {
		this.pgMerchantCurMapId = pgMerchantCurMapId;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
