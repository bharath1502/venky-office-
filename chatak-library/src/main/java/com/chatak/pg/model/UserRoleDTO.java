package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class UserRoleDTO extends SearchRequest
{
	private Long roleId;
	private Long userRoleId;
	private String roleName;
	private String description;
	private String roleType;
	private String makerCheckerReq;
	private String status;
	private String feature[];
	/**
	 * @return the userRoleId
	 */
	public Long getUserRoleId() {
		return userRoleId;
	}
	
	/**
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
  }
	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
   * @param roleName the roleName to set
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}
	
	public String[] getFeature() {
    return feature;
  }
	/**
   * @param roleType the roleType to set
   */
  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }
	/**
	 * @return the makerCheckerReq
	 */
	public String getMakerCheckerReq() {
		return makerCheckerReq;
	}
	
	public void setFeature(String feature[]) {
    this.feature = feature;
  }
	/**
	 * @param makerCheckerReq the makerCheckerReq to set
	 */
	public void setMakerCheckerReq(String makerCheckerReq) {
		this.makerCheckerReq = makerCheckerReq;
	}
	
	public Long getRoleId() {
    return roleId;
  }
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
  

  
  
  
	
	
}
	
