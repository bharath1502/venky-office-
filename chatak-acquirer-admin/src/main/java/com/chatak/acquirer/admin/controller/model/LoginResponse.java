package com.chatak.acquirer.admin.controller.model;

import java.util.List;
import com.chatak.acquirer.admin.model.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;




/**
 * REST API model class to hold response login result
 * 
 * @Author: Girmiti Software
 * @Date: 16-Aug-2014
 * @Time: 12:26:45 pm
 * @Version: 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_NULL)
public class LoginResponse extends Response {
  /**
   * 
   */
  private static final long serialVersionUID = -3128219292788373292L;

  private Boolean status;
  
  private Long userId;

  private String message;

  private String email;
  
  private Long serviceProviderId;

  private String accessToken;

  private Long subServiceProviderId;
  
  private Long userRoleId;

  private List<String> existingFeature;

  private String userType;

  private Boolean makerCheckerRequired;

  private Integer emailVerified;
  
  private String lastName;
	
  private String firstName;
  
  private String userName;
  
  private Long entityId;

  private String lastLonginTime;
  
  public String getLastLonginTime() {
    return lastLonginTime;
  }

  public void setLastLonginTime(String lastLonginTime) {
    this.lastLonginTime = lastLonginTime;
  }

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
   * @return the userName
   */
   public String getUserName() {
 	return userName;
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
   * @param userName the userName to set
   */
   public void setUserName(String userName) {
 	this.userName = userName;
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
   * @return the status
   */
  public Boolean getStatus() {
    return status;
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

  public void setFirstName(String firstName) {
	this.firstName = firstName;
  }

  /**
   * @return the accessToken
   */
  public String getAccessToken() {
    return accessToken;
  }
  
  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * @param accessToken
   *          the accessToken to set
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  
  public String getFirstName() {
	return firstName;
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
  
  public void setLastName(String lastName) {
	this.lastName = lastName;
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
  
  public String getLastName() {
	return lastName;
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
  
  public void setEmailVerified(Integer emailVerified) {
	this.emailVerified = emailVerified;
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
  
  public Integer getEmailVerified() {
	return emailVerified;
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
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

}
