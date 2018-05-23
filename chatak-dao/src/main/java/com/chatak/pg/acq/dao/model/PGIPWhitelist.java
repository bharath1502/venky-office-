package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_IP_WHITELIST")
public class PGIPWhitelist implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8990769528360374249L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_IP_WHITELIST_ID", sequenceName = "SEQ_PG_IP_WHITELIST")
	@GeneratedValue(generator = "SEQ_PG_IP_WHITELIST_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;  

	@Column(name = "MERCHANT_ID")
	private String merchantId;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "IP")
	private String ip;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

}