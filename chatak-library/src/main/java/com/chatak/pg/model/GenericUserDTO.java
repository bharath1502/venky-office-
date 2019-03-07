package com.chatak.pg.model;

import java.sql.Timestamp;

import com.chatak.pg.bean.SearchRequest;

public class GenericUserDTO extends SearchRequest{

	private static final long serialVersionUID = -2580372724706772704L;

	private Long userRoleId;
	
	private Long adminUserId;
	
	private String userRoleName;
	
	private String userName;

	private String email;

	private String lastName;
	
	private String firstName;

	private String password;
	
	private Integer emailVerified;

	private String emailToken;

	private String language;
	
	private String address2;

	private String address1;

	private String city;
	
	private String country;

	private String state;

	private Long zip;

	private String phone;
	
	private String lastLoginIP;

	private Timestamp lastLogin;

	private String previousPasswords;
	
	private Integer passRetryCount;

	private Timestamp lastPassWordChange;

	private Integer status;
	
	private String securityAnswer;
	
	private String securityQuestion;
	
	private String userType;
	
	private String usersGroup;
	
	private String requestType;
	
	private String merchantName;
	
	private String merchantCode;
	
	private Long entityId;
	
  private String loginuserType;

  public String getLoginuserType() {
    return loginuserType;
  }

  public void setLoginuserType(String loginuserType) {
    this.loginuserType = loginuserType;
  }

  /**
	 * @return the adminUserId
	 */
	public Long getAdminUserId() {
		return adminUserId;
	}
	
	/**
   * @return the userRoleId
   */
  public Long getUserRoleId() {
    return userRoleId;
  }

	/**
	 * @param adminUserId the adminUserId to set
	 */
	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	/**
   * @param userName the userName to set
   */
	@Override
	public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }
  
	/**
	 * @return the userRoleName
	 */
	public String getUserRoleName() {
		return userRoleName;
	}
	
	/**
   * @param userRoleId the userRoleId to set
   */
  public void setUserRoleId(Long userRoleId) {
    this.userRoleId = userRoleId;
  }

	/**
	 * @param userRoleName the userRoleName to set
	 */
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	
	/**
   * @return the userName
   */
	@Override
	public String getUserName() {
    return userName;
  }

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }
  /**
   * @return the emailToken
   */
  public String getEmailToken() {
    return emailToken;
  }

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
   * @param emailToken the emailToken to set
   */
  public void setEmailToken(String emailToken) {
    this.emailToken = emailToken;
  }
	
	/**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the emailVerified
	 */
	public Integer getEmailVerified() {
		return emailVerified;
	}
	
	/**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
	
	/**
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

	/**
	 * @param emailVerified the emailVerified to set
	 */
	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	
	/**
   * @param language the language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }
  
  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
   * @return the state
   */
  public String getState() {
    return state;
  }
  /**
   * @return the zip
   */
  public Long getZip() {
    return zip;
  }

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(Long zip) {
		this.zip = zip;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
   * @param lastLoginIP the lastLoginIP to set
   */
  public void setLastLoginIP(String lastLoginIP) {
    this.lastLoginIP = lastLoginIP;
  }

	/**
	 * @return the lastLogin
	 */
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	
	/**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the previousPasswords
	 */
	public String getPreviousPasswords() {
		return previousPasswords;
	}
	
	/**
   * @return the lastPassWordChange
   */
  public Timestamp getLastPassWordChange() {
    return lastPassWordChange;
  }
	
	/**
   * @return the lastLoginIP
   */
  public String getLastLoginIP() {
    return lastLoginIP;
  }

	/**
	 * @param previousPasswords the previousPasswords to set
	 */
	public void setPreviousPasswords(String previousPasswords) {
		this.previousPasswords = previousPasswords;
	}

	/**
	 * @return the passRetryCount
	 */
	public Integer getPassRetryCount() {
		return passRetryCount;
	}
	
	/**
   * @param lastPassWordChange the lastPassWordChange to set
   */
  public void setLastPassWordChange(Timestamp lastPassWordChange) {
    this.lastPassWordChange = lastPassWordChange;
  }

	/**
	 * @param passRetryCount the passRetryCount to set
	 */
	public void setPassRetryCount(Integer passRetryCount) {
		this.passRetryCount = passRetryCount;
	}
	
	/**
   * @return the securityQuestion
   */
  public String getSecurityQuestion() {
    return securityQuestion;
  }

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
   * @param securityQuestion the securityQuestion to set
   */
  public void setSecurityQuestion(String securityQuestion) {
    this.securityQuestion = securityQuestion;
  }

	/**
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	
	/**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

	/**
	 * @param securityAnswer the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getUsersGroup() {
    return usersGroup;
  }
	
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	
	public void setUsersGroup(String usersGroup) {
    this.usersGroup = usersGroup;
  }
	
	/**
   * @return the requestType
   */
  public String getRequestType() {
    return requestType;
  }
  
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMerchantCode() {
    return merchantCode;
  }

  public String getMerchantName() {
    return merchantName;
  }
  
  /**
   * @param requestType the requestType to set
   */
  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  /**
   * @return the entityId
   */
  public Long getEntityId() {
    return entityId;
  }

  /**
   * @param entityId the entityId to set
   */
  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

}
