package com.chatak.pg.user.bean;

import java.io.Serializable;

public class GetRoleRequest extends SearchRequest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7372472825962200241L;
	
	private Long roleId;

	private String roleName;
	
	private String roleLevel;
	
	private Integer status;

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
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
