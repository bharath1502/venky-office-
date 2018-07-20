package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

public class PGUpdateRoleRequest implements Serializable
{
  /**
	 * 
	 */

  private static final long serialVersionUID = -3869057513672987903L;

  private Long  roleId;
  
  private String roleName;
  
  private String roleLevel;
  
  private Integer status;
  
  private String feature[];

/**
 * @return the roleName
 */
public String getRoleName() {
	return roleName;
}

/**
 * @return the roleId
 */
public Long getRoleId() {
	return roleId;
}

/**
 * @param roleName the roleName to set
 */
public void setRoleName(String roleName) {
	this.roleName = roleName;
}

/**
 * @return the roleLevel
 */
public String getRoleLevel() {
	return roleLevel;
}

/**
 * @param roleId the roleId to set
 */
public void setRoleId(Long roleId) {
	this.roleId = roleId;
}

/**
 * @param roleLevel the roleLevel to set
 */
public void setRoleLevel(String roleLevel) {
	this.roleLevel = roleLevel;
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
 * @return the feature
 */
public String[] getFeature() {
	return feature;
}

/**
 * @param feature the feature to set
 */
public void setFeature(String[] feature) {
	this.feature = feature;
}
  
  
	
}
