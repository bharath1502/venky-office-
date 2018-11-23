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
 * @Date: Sep 30, 2016
 * @Time: 5:16:25 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_PAY_PAGE_CONFIG")
public class PGPayPageConfig {

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_PAY_PAGE_CONFIG_ID", sequenceName = "SEQ_PG_PAY_PAGE_CONFIG")
	@GeneratedValue(generator = "SEQ_PG_PAY_PAGE_CONFIG_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MERCHANT_ID")
	private Long merchantId;

	@Column(name = "HEADER_TEXT")
	private String header;

	@Column(name = "FOOTER_TEXT")
	private String footer;

	@Column(name = "LOGO")
	private byte[] payPageLogo;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}
	
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public byte[] getPayPageLogo() {
		return payPageLogo;
	}
	
	public String getHeader() {
		return header;
	}

	public void setPayPageLogo(byte[] payPageLogo) {
		this.payPageLogo = payPageLogo;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
