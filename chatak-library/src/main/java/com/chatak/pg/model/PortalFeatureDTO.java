package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;



public class PortalFeatureDTO extends SearchRequest{

	private static final long serialVersionUID = 8031747942717786837L;
	
	private Long featureId;

	private String name;

	private String featureGroup;

	private String status;
	
	private String roleType;
	
	private Boolean isAttached=Boolean.FALSE;
	
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
	 * @return the featureGroup
	 */
	public String getFeatureGroup() {
		return featureGroup;
	}

	/**
	 * @param featureGroup the featureGroup to set
	 */
	public void setFeatureGroup(String featureGroup) {
		this.featureGroup = featureGroup;
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
	 * @return the isAttached
	 */
	public Boolean getIsAttached() {
		return isAttached;
	}

	/**
	 * @param isAttached the isAttached to set
	 */
	public void setIsAttached(Boolean isAttached) {
		this.isAttached = isAttached;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
}
