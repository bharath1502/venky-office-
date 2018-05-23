package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PG_ROLE_FEATURE_MAPPING_NEW")
public class PGRolesFeatureMapping implements Serializable
{
	
	private static final long serialVersionUID = 6488645474993654593L;
	
	
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_ROLE_MAPPING_NEW_ID", sequenceName = "SEQ_PG_ROLE_MAPPING_NEW")
    @GeneratedValue(generator= "SEQ_PG_ROLE_MAPPING_NEW_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_ROLE_ID")
	private Long roleId;
	
	@Column(name = "FEATURE_ID")
	private Long featureId;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
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

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
