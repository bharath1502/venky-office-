package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class FeatureDTO extends SearchRequest
{
	private static final long serialVersionUID = -8952398370449904540L;

	private Long featureId;

  private String name;
  
  private Long featureLevel;

  private String roleType;
  
  private Long refFeatureId;
  
  private Integer status;
  
  private String roleFeatureId;

  public Long getFeatureId() {
    return featureId;
  }

  public Long getFeatureLevel() {
    return featureLevel;
  }

  public String getName() {
    return name;
  }
  
  public String getRoleType() {
    return roleType;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRefFeatureId(Long refFeatureId) {
    this.refFeatureId = refFeatureId;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }
  
  public void setFeatureLevel(Long featureLevel) {
    this.featureLevel = featureLevel;
  }

  public Long getRefFeatureId() {
    return refFeatureId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getRoleFeatureId() {
    return roleFeatureId;
  }

  public void setRoleFeatureId(String roleFeatureId) {
    this.roleFeatureId = roleFeatureId;
  }
  
  public void setFeatureId(Long featureId) {
    this.featureId = featureId;
  }

}
