package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chatak.pg.bean.SearchRequest;

@Entity
@Table(name = "PG_PAN_LENGTH")
public class PGPanLength extends SearchRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PAN_LENGTH_ID")
	private Long panLengthId;

	@Column(name = "PAN_LENGTH")
	private Long panLength;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	/**
	 * @return the panLengthId
	 */
	public Long getPanLengthId() {
		return panLengthId;
	}

	/**
	 * @param panLengthId the panLengthId to set
	 */
	public void setPanLengthId(Long panLengthId) {
		this.panLengthId = panLengthId;
	}

	/**
	 * @return the panLength
	 */
	public Long getPanLength() {
		return panLength;
	}

	/**
	 * @param panLength the panLength to set
	 */
	public void setPanLength(Long panLength) {
		this.panLength = panLength;
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
