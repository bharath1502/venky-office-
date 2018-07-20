package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;


public class FeatureData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5391094097798472951L;
	private Long featureId;
	private String featureName;
	private String featureUrl;
	private String roleLevel;
	private String featureLevel;
	private Timestamp createdDate;
	private Integer status;
	private Long roleId;
	
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
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}
	/**
	 * @param featureName the featureName to set
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	/**
	 * @return the featureUrl
	 */
	public String getFeatureUrl() {
		return featureUrl;
	}
	/**
	 * @param featureUrl the featureUrl to set
	 */
	public void setFeatureUrl(String featureUrl) {
		this.featureUrl = featureUrl;
	}
	/**
	 * @return the roleLevel
	 */
	public String getRoleLevel() {
		return roleLevel;
	}
	/**
	 * @param roleLevel the roleLevel to set
	 */
	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}
	/**
	 * @return the featureLevel
	 */
	public String getFeatureLevel() {
		return featureLevel;
	}
	/**
	 * @param featureLevel the featureLevel to set
	 */
	public void setFeatureLevel(String featureLevel) {
		this.featureLevel = featureLevel;
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
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
	


}
