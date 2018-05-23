package com.chatak.merchant.controller.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * REST API model class to hold response login result
 * 
 * @Author: Girmiti Software
 * @Date: 16-Aug-2014
 * @Time: 12:26:45 pm
 * @Version: 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse extends Response {
  /**
   * 
   */
  private static final long serialVersionUID = -3128219292788373292L;

  private Boolean status;

  private String message;

  private Long userId;
  
  private String email;

  private String accessToken;

  private Long serviceProviderId;

  private Long subServiceProviderId;

  private List<String> existingFeature;

  private Long userRoleId;

  private Boolean makerCheckerRequired;

  private String userType;
  
  private Long parentMerchantId;
  
  private Long userMerchantId;
  
  private String userName;

  private String merchantCode;

  /**
   * @return the serviceProviderId
   */
  public Long getServiceProviderId() {
    return serviceProviderId;
  }

  /**
   * @param serviceProviderId
   *          the serviceProviderId to set
   */
  public void setServiceProviderId(Long serviceProviderId) {
    this.serviceProviderId = serviceProviderId;
  }

  /**
   * @return the subServiceProviderId
   */
  public Long getSubServiceProviderId() {
    return subServiceProviderId;
  }

  /**
   * @param subServiceProviderId
   *          the subServiceProviderId to set
   */
  public void setSubServiceProviderId(Long subServiceProviderId) {
    this.subServiceProviderId = subServiceProviderId;
  }

  /**
   * @return the status
   */
  public Boolean getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(Boolean status) {
    this.status = status;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the userId
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * @return the accessToken
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * @param accessToken
   *          the accessToken to set
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   * @return the existingFeature
   */
  public List<String> getExistingFeature() {
    return existingFeature;
  }

  /**
   * @param existingFeature
   *          the existingFeature to set
   */
  public void setExistingFeature(List<String> existingFeature) {
    this.existingFeature = existingFeature;
  }

  /**
   * @return the userRoleId
   */
  public Long getUserRoleId() {
    return userRoleId;
  }

  /**
   * @param userRoleId
   *          the userRoleId to set
   */
  public void setUserRoleId(Long userRoleId) {
    this.userRoleId = userRoleId;
  }

  /**
   * @return the makerCheckerRequired
   */
  public Boolean getMakerCheckerRequired() {
    return makerCheckerRequired;
  }

  /**
   * @param makerCheckerRequired
   *          the makerCheckerRequired to set
   */
  public void setMakerCheckerRequired(Boolean makerCheckerRequired) {
    this.makerCheckerRequired = makerCheckerRequired;
  }

  /**
   * @return the userType
   */
  public String getUserType() {
    return userType;
  }

  /**
   * @param userType
   *          the userType to set
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the parentMerchantId
   */
  public Long getParentMerchantId() {
    return parentMerchantId;
  }

  /**
   * @param parentMerchantId the parentMerchantId to set
   */
  public void setParentMerchantId(Long parentMerchantId) {
    this.parentMerchantId = parentMerchantId;
  }

/**
 * @return the userMerchantId
 */
public Long getUserMerchantId() {
	return userMerchantId;
}

/**
 * @param userMerchantId the userMerchantId to set
 */
public void setUserMerchantId(Long userMerchantId) {
	this.userMerchantId = userMerchantId;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getMerchantCode() {
  return merchantCode;
}

public void setMerchantCode(String merchantCode) {
  this.merchantCode = merchantCode;
}

}
