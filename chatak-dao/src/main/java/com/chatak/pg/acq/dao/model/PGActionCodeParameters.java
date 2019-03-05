package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chatak.pg.bean.SearchRequest;

@Entity
@Table(name = "PG_ACTION_CODE_PARAMETERS")
public class PGActionCodeParameters extends SearchRequest implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5432589349994845L;

	@Id
	@Column(name = "ACTION_CODE_ID")
	private Long actioncodeId;

	@Column(name = "ACTION_CODE_NAME")
	private String actioncodeName;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	/**
	 * @return the actioncodeId
	 */
	public Long getActioncodeId() {
		return actioncodeId;
	}

	/**
	 * @param actioncodeId the actioncodeId to set
	 */
	public void setActioncodeId(Long actioncodeId) {
		this.actioncodeId = actioncodeId;
	}

	/**
	 * @return the actioncodeName
	 */
	public String getActioncodeName() {
		return actioncodeName;
	}

	/**
	 * @param actioncodeName the actioncodeName to set
	 */
	public void setActioncodeName(String actioncodeName) {
		this.actioncodeName = actioncodeName;
	}

	/**
	 * @return the createdBy
	 */
	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	@Override
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	@Override
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
}
