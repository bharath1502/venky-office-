package com.chatak.pg.bean;

import java.io.Serializable;

public class FeatureDataResponse extends Response implements Serializable
{
  /**
	 * 
	 */
  private static final long serialVersionUID = 2412963518414658393L;

  private Long featureId;
  
  private String featureName;
  
  private Long subFeatureId;
  
  private String featureUrl;
  
  private Integer featureLevel;

/**
 * @return the featureId
 */
public Long getFeatureId() {
	return featureId;
}

/**
 * @param featureId the featureId to set
 */
public void setFeatureId(Long featureId) {
	this.featureId = featureId;
}

/**
 * @return the featureName
 */
public String getFeatureName() {
	return featureName;
}

/**
 * @param featureName the featureName to set
 */
public void setFeatureName(String featureName) {
	this.featureName = featureName;
}

/**
 * @return the subFeatureId
 */
public Long getSubFeatureId() {
	return subFeatureId;
}

/**
 * @param subFeatureId the subFeatureId to set
 */
public void setSubFeatureId(Long subFeatureId) {
	this.subFeatureId = subFeatureId;
}

/**
 * @return the featureUrl
 */
public String getFeatureUrl() {
	return featureUrl;
}

/**
 * @param featureUrl the featureUrl to set
 */
public void setFeatureUrl(String featureUrl) {
	this.featureUrl = featureUrl;
}

/**
 * @return the featureLevel
 */
public Integer getFeatureLevel() {
	return featureLevel;
}

/**
 * @param featureLevel the featureLevel to set
 */
public void setFeatureLevel(Integer featureLevel) {
	this.featureLevel = featureLevel;
}

  
  
  
}
