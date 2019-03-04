package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.bean.SearchRequest;
import com.chatak.pg.model.MposFeatures;

public class UserData extends SearchRequest {
  /**
   * 
   */
  private static final long serialVersionUID = 5789724850995872688L;

  private Long userId;
  
  private String firstName;

  private String roleName;

  private String lastName;
  
  private String phone;

  private String emailId;

  private Long roleId;
  
  private String address;

  private String name;

  private String merchantId;
  
  private String requestType;

  private String usersGrouopType;

  private String merchantLink;
  
  private String merchantName;

  private Integer status;

  private String roleType;

  private String userType;
  
  private Long entityId;
  
  private transient List<MposFeatures> mpsoFeatures;
  
 	/**
 	 * @return the mpsoFeatures
 	 */
 	public List<MposFeatures> getMpsoFeatures() {
 		return mpsoFeatures;
 	}

 	/**
 	 * @param mpsoFeatures
 	 *            the mpsoFeatures to set
 	 */
 	public void setMpsoFeatures(List<MposFeatures> mpsoFeatures) {
 		this.mpsoFeatures = mpsoFeatures;
 	}
  
  /**
   * @return the userId
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  /**
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * @param roleName the roleName to set
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  /**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }

  /**
   * @param emailId the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  /**
   * @return the roleId
   */
  public Long getRoleId() {
    return roleId;
  }

  /**
   * @param roleId the roleId to set
   */
  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }
  
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getUsersGrouopType() {
    return usersGrouopType;
  }

  public void setUsersGrouopType(String usersGrouopType) {
    this.usersGrouopType = usersGrouopType;
  }
  
  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the requestType
   */
  public String getRequestType() {
    return requestType;
  }

  /**
   * @param requestType the requestType to set
   */
  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
  
  /**
   * @return the merchantLink
   */
  public String getMerchantLink() {
    return merchantLink;
  }

  /**
   * @param merchantLink the merchantLink to set
   */
  public void setMerchantLink(String merchantLink) {
    this.merchantLink = merchantLink;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }
  
  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
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
