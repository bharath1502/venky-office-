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
@Table(name = "portal_role_feature_map")
public class PortalRoleFeatureMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3538275025948013803L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_feature_map_id")
	private Long roleFeatureMapId;

	@Column(name = "user_role_id")
	private Long userRoleId;

	@Column(name = "feature_id")
	private Long featureId;

	@Column(name = "status")
	private String status;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Timestamp updatedDate;

	/**
	 * @return the featureId
	 */
	public Long getFeatureId() {
		return featureId;
	}

	/**
	 * @param featureId the featureId to set
	 */
	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	/**
	 * @return the roleFeatureMapId
	 */
	public Long getRoleFeatureMapId() {
		return roleFeatureMapId;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @param roleFeatureMapId the roleFeatureMapId to set
	 */
	public void setRoleFeatureMapId(Long roleFeatureMapId) {
		this.roleFeatureMapId = roleFeatureMapId;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * @return the userRoleId
	 */
	public Long getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
}
