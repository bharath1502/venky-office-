package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_COUNTRY_TIME_ZONE")
public class TimeZone implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "COUNTRY_ID")
	private Long countryId;

	@Column(name = "STANDARD_TIME_OFFSET")
	private String standardTimeOffset;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getStandardTimeOffset() {
		return standardTimeOffset;
	}

	public void setStandardTimeOffset(String standardTimeOffset) {
		this.standardTimeOffset = standardTimeOffset;
	}

}
