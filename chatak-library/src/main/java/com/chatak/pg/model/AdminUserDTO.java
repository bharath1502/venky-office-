package com.chatak.pg.model;

import java.sql.Timestamp;

import com.chatak.pg.bean.SearchRequest;

public class AdminUserDTO extends SearchRequest{

	private static final long serialVersionUID = -2580372724706772704L;

	private Long adminUserId;

	private Long userRoleId;
	
	private String userRoleName;

	private String email;

	private String userName;
	
	private String firstName;

	private String lastName;

	private String password;

	private String emailToken;

	private Integer emailVerified;

	private String language;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String country;

	private String zip;

	private String phone;

	private Timestamp lastLogin;

	private String lastLoginIP;

	private String previousPasswords;

	private Timestamp lastPassWordChange;

	private Integer passRetryCount;

	private Integer status;
	
	private String securityQuestion;
	
	private String securityAnswer;
	
	private String userType;
	
	private String usersGroup;

	/**
	 * @return the adminUserId
	 */
	public Long getAdminUserId() {
		return adminUserId;
	}

	/**
	 * @param adminUserId the adminUserId to set
	 */
	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
   * @param userRoleName the userRoleName to set
   */
  public void setUserRoleName(String userRoleName) {
    this.userRoleName = userRoleName;
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
   * @return the userRoleId
   */
  public Long getUserRoleId() {
    return userRoleId;
  }

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }
  
  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @return the emailToken
   */
  public String getEmailToken() {
    return emailToken;
  }

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
   * @return the emailVerified
   */
  public Integer getEmailVerified() {
    return emailVerified;
  }

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

	/**
	 * @param emailToken the emailToken to set
	 */
	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

	/**
	 * @param emailVerified the emailVerified to set
	 */
	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
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
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
   * @param zip the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }

	/**
	 * @return the lastLogin
	 */
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	
	/**
   * @param previousPasswords the previousPasswords to set
   */
  public void setPreviousPasswords(String previousPasswords) {
    this.previousPasswords = previousPasswords;
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
	 * @return the lastLoginIP
	 */
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	
	/**
   * @return the lastPassWordChange
   */
  public Timestamp getLastPassWordChange() {
    return lastPassWordChange;
  }

	/**
	 * @param lastLoginIP the lastLoginIP to set
	 */
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	/**
   * @return the passRetryCount
   */
  public Integer getPassRetryCount() {
    return passRetryCount;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

	/**
	 * @param lastPassWordChange the lastPassWordChange to set
	 */
	public void setLastPassWordChange(Timestamp lastPassWordChange) {
		this.lastPassWordChange = lastPassWordChange;
	}

	/**
   * @return the securityQuestion
   */
  public String getSecurityQuestion() {
    return securityQuestion;
  }

	/**
	 * @param passRetryCount the passRetryCount to set
	 */
	public void setPassRetryCount(Integer passRetryCount) {
		this.passRetryCount = passRetryCount;
	}

	/**
   * @param securityQuestion the securityQuestion to set
   */
  public void setSecurityQuestion(String securityQuestion) {
    this.securityQuestion = securityQuestion;
  }

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @param securityAnswer the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

  public String getUsersGroup() {
    return usersGroup;
  }

  public void setUsersGroup(String usersGroup) {
    this.usersGroup = usersGroup;
  }
}
