package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.SearchRequest;

public class UserRolesDTO extends SearchRequest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9136345277821933550L;
	private Long roleId;
	private String roleName;
	private String roleType;
	private Integer status;
	private String isRoleCreatedUser;
	
	/**
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
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
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
  public String getRoleType() {
    return roleType;
  }
  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }
  public String getIsRoleCreatedUser() {
    return isRoleCreatedUser;
  }
  
  /**
   * @return the roleId
   */
  public Long getRoleId() {
    return roleId;
  }
  public void setIsRoleCreatedUser(String isRoleCreatedUser) {
    this.isRoleCreatedUser = isRoleCreatedUser;
  }
	
	
	

}
