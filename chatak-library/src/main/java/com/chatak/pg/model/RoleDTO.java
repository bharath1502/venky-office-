package com.chatak.pg.model;

import java.util.List;

import com.chatak.pg.bean.SearchRequest;


public class RoleDTO extends SearchRequest{

	private static final long serialVersionUID = 4401357593463174678L;
	
	private Long userRoleId;

	private String roleName;

	private String description;

	private String roleType;
	
	private String featureList;
	
	private Long[] feature;

	private String makerCheckerReq;

	private String status;
	
	private String reason;
	
	private List<PortalFeatureDTO>  featureTO ;

	

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	/**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }
  
  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}
	
	/**
   * @return the makerCheckerReq
   */
  public String getMakerCheckerReq() {
    return makerCheckerReq;
  }

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @param makerCheckerReq the makerCheckerReq to set
	 */
	public void setMakerCheckerReq(String makerCheckerReq) {
		this.makerCheckerReq = makerCheckerReq;
	}

	/**
   * @return the userRoleId
   */
  public Long getUserRoleId() {
    return userRoleId;
  }

	/**
	 * @return the featureTO
	 */
	public List<PortalFeatureDTO> getFeatureTO() {
		return featureTO;
	}

	/**
	 * @param allFeatureListTO the featureTO to set
	 */
	public void setFeatureTO(List<PortalFeatureDTO> allFeatureListTO) {
		this.featureTO =  allFeatureListTO;
	}
	
	public String getFeatureList() {
		return featureList;
	}

	public void setFeatureList(String featureList) {
		this.featureList = featureList;
	}

	public Long[] getFeature() {
		return feature;
	}

	public void setFeature(Long[] feature) {
		this.feature = feature;
	}
	
	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
