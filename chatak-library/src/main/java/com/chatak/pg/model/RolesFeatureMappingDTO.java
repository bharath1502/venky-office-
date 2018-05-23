package com.chatak.pg.model;

import java.sql.Timestamp;

public class RolesFeatureMappingDTO {
  
  private Long roleFeatureMappingId;

  private Long roleId;
  
  private Long featureId;
  
  private Timestamp createdDate;

  private String createdBy;

  private Timestamp updatedDate;

  private String updatedBy;

  public Long getRoleId() {
    return roleId;
  }
  
  public Long getRoleFeatureMappingId() {
    return roleFeatureMappingId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
  
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public Long getFeatureId() {
    return featureId;
  }
  
  public void setRoleFeatureMappingId(Long roleFeatureMappingId) {
    this.roleFeatureMappingId = roleFeatureMappingId;
  }

  public void setFeatureId(Long featureId) {
    this.featureId = featureId;
  }

  public String getCreatedBy() {
    return createdBy;
  }
  
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }
  
  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

}
