package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 18-Mar-2015 11:15:38 AM
 * @version 1.0
 */
@Entity
@Table(name = "PG_FRAUD_BASIC")
public class PGFraudBasic {

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_FRAUD_BASIC_ID", sequenceName = "SEQ_PG_FRAUD_BASIC")
	@GeneratedValue(generator = "SEQ_PG_FRAUD_BASIC_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DENIED_IP")
	private String deniedIP;

	@Column(name = "DENIED_BIN")
	private String deniedBin;

	@Column(name = "DENIED_EMAIL")
	private String deniedEMail;

	@Column(name = "DENIED_COUNTRY")
	private String deniedCountry;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "MERCHANT_ID")
	private Long merchantId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the deniedIP
	 */
	public String getDeniedIP() {
		return deniedIP;
	}

	/**
	 * @param deniedIP
	 *            the deniedIP to set
	 */
	public void setDeniedIP(String deniedIP) {
		this.deniedIP = deniedIP;
	}

	/**
	 * @return the deniedBin
	 */
	public String getDeniedBin() {
		return deniedBin;
	}

	/**
	 * @param deniedBin
	 *            the deniedBin to set
	 */
	public void setDeniedBin(String deniedBin) {
		this.deniedBin = deniedBin;
	}

	/**
	 * @return the deniedEMail
	 */
	public String getDeniedEMail() {
		return deniedEMail;
	}

	/**
	 * @param deniedEMail
	 *            the deniedEMail to set
	 */
	public void setDeniedEMail(String deniedEMail) {
		this.deniedEMail = deniedEMail;
	}

	/**
	 * @return the deniedCountry
	 */
	public String getDeniedCountry() {
		return deniedCountry;
	}

	/**
	 * @param deniedCountry
	 *            the deniedCountry to set
	 */
	public void setDeniedCountry(String deniedCountry) {
		this.deniedCountry = deniedCountry;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
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
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the merchantId
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

}
