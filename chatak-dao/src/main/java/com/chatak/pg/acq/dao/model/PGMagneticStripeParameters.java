package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_MAGNETIC_STRIPE_PARAMETERS")
public class PGMagneticStripeParameters implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2497375034955536460L;

	@Id
	@Column(name = "MAGNETIC_STRIPE_ID")
	private Long magneticStripeId;
	
	@Column(name = "MAGNETIC_STRIPE_NAME")
	private String magneticStripeName;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;


	/**
	 * @return the magneticStripeId
	 */
	public Long getMagneticStripeId() {
		return magneticStripeId;
	}

	/**
	 * @param magneticStripeId the magneticStripeId to set
	 */
	public void setMagneticStripeId(Long magneticStripeId) {
		this.magneticStripeId = magneticStripeId;
	}

	/**
	 * @return the magneticStripeName
	 */
	public String getMagneticStripeName() {
		return magneticStripeName;
	}

	/**
	 * @param magneticStripeName the magneticStripeName to set
	 */
	public void setMagneticStripeName(String magneticStripeName) {
		this.magneticStripeName = magneticStripeName;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
