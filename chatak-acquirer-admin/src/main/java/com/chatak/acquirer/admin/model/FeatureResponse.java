package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.model.FeatureDTO;

public class FeatureResponse extends Response {

  private static final long serialVersionUID = -4974360784912896581L;

  private List<String> feature;

  private List<String> subFeature;

  private List<FeatureDTO> featureDTO;

  private String roleType;

  public List<String> getFeature() {
    return feature;
  }

  public void setFeature(List<String> feature) {
    this.feature = feature;
  }

  public List<String> getSubFeature() {
    return subFeature;
  }

  public void setSubFeature(List<String> subFeature) {
    this.subFeature = subFeature;
  }

  public List<FeatureDTO> getFeatureDTO() {
    return featureDTO;
  }

  public void setFeatureDTO(List<FeatureDTO> featureDTO) {
    this.featureDTO = featureDTO;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }
}
