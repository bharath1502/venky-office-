package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EditRoleResponse implements Serializable {

  private static final long serialVersionUID = 1458090226175852256L;

  private UserRoleDTO roleRequest;

  private Map<Long, List<FeatureDTO>> featureMap;

  private List<Long> existingFeature;

  private Long serviceProvider;

  private List<String> serviceProviderList;

  private List<String> subServiceProviderList;

  public UserRoleDTO getRoleRequest() {
    return roleRequest;
  }

  public void setRoleRequest(UserRoleDTO roleRequest) {
    this.roleRequest = roleRequest;
  }

  public Map<Long, List<FeatureDTO>> getFeatureMap() {
    return featureMap;
  }

  public void setFeatureMap(Map<Long, List<FeatureDTO>> featureMap) {
    this.featureMap = featureMap;
  }

  public List<Long> getExistingFeature() {
    return existingFeature;
  }

  public void setExistingFeature(List<Long> existingFeature) {
    this.existingFeature = existingFeature;
  }

  public Long getServiceProvider() {
    return serviceProvider;
  }

  public void setServiceProvider(Long serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public List<String> getServiceProviderList() {
    return serviceProviderList;
  }

  public void setServiceProviderList(List<String> serviceProviderList) {
    this.serviceProviderList = serviceProviderList;
  }

  public List<String> getSubServiceProviderList() {
    return subServiceProviderList;
  }

  public void setSubServiceProviderList(List<String> subServiceProviderList) {
    this.subServiceProviderList = subServiceProviderList;
  }

}
