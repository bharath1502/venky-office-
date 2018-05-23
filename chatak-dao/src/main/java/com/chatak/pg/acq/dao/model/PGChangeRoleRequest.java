package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

public class PGChangeRoleRequest implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6857592869417915983L;
	
	private Long roleId;
	
	private Integer status;
	

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
	
	
	

}
