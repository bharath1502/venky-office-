package com.chatak.acquirer.admin.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.chatak.pg.model.FeatureDTO;

public class AdminFeatureRequest extends Response implements Serializable {

	private static final long serialVersionUID = -954414819788829091L;

	private Long featureId;
	private String name;
	private Long featureLevel;
	private Long refFeatureId;
	private String status;
	private Timestamp createdDate;
	private String roleFeatureId;
	private String roleType;
	private Long roleId;
	private Long displayOrder;
	private Long subFeatureId;
	private List<FeatureDTO> featureDTOs;
	
	/**
	 * @return the roleFeatureId
	 */
	public String getRoleFeatureId() {
		return roleFeatureId;
	}
	/**
	 * @param roleFeatureId the roleFeatureId to set
	 */
	public void setRoleFeatureId(String roleFeatureId) {
		this.roleFeatureId = roleFeatureId;
	}
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the featureLevel
	 */
	public Long getFeatureLevel() {
		return featureLevel;
	}
	/**
	 * @param featureLevel the featureLevel to set
	 */
	public void setFeatureLevel(Long featureLevel) {
		this.featureLevel = featureLevel;
	}
	/**
	 * @return the refFeatureId
	 */
	public Long getRefFeatureId() {
		return refFeatureId;
	}
	/**
	 * @param refFeatureId the refFeatureId to set
	 */
	public void setRefFeatureId(Long refFeatureId) {
		this.refFeatureId = refFeatureId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
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
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Long getSubFeatureId() {
		return subFeatureId;
	}
	public void setSubFeatureId(Long subFeatureId) {
		this.subFeatureId = subFeatureId;
	}
	public List<FeatureDTO> getFeatureDTOs() {
		return featureDTOs;
	}
	public void setFeatureDTOs(List<FeatureDTO> featureDTOs) {
		this.featureDTOs = featureDTOs;
	}
	
}
