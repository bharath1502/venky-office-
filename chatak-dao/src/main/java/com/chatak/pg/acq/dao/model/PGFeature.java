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
@Table(name = "PG_FEATURE")
public class PGFeature implements Serializable
{
	private static final long serialVersionUID = -3847999642936659960L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_FEATURES_ID", sequenceName = "SEQ_PG_FEATURE")
    @GeneratedValue(generator = "SEQ_PG_FEATURES_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FEATURE_ID")
	private Long featureId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "FEATURE_LEVEL")
	private Long featureLevel;
	
	@Column(name = "ROLE_TYPE")
	private String roleType;
	
	@Column(name = "REF_FEATURE_ID")
	private Long refFeatureId;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	public Long getFeatureId() {
		return featureId;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public String getName() {
		return name;
	}
	
	public Long getRefFeatureId() {
		return refFeatureId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFeatureLevel() {
		return featureLevel;
	}
	
	public String getRoleType() {
		return roleType;
	}

	public void setFeatureLevel(Long featureLevel) {
		this.featureLevel = featureLevel;
	}

	public void setRefFeatureId(Long refFeatureId) {
		this.refFeatureId = refFeatureId;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
}