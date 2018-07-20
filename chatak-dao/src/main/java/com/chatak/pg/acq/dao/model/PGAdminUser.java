package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Girmiti Software
 *
 */
@Entity
@Table(name = "PG_ADMIN_USER")
public class PGAdminUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 85715961188781746L;

	 @Id
	  /*@SequenceGenerator(name = "SEQ_PG_ADMIN_USER", sequenceName = "SEQ_PG_ADMIN_USER")
	  @GeneratedValue(generator = "SEQ_PG_ADMIN_USER")*/
	 @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ADMIN_USER_ID")

	private Long adminUserId;

	@Column(name = "USER_ROLE_ID")
	private Long userRoleId;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL_TOKEN")
	private String emailToken;

	@Column(name = "EMAIL_VERIFIED")
	private Integer emailVerified;

	@Column(name = "ADDRESS1")
	private String address1;
	
	@Column(name = "CITY")
	private String city;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "STATE")
	private String state;
	
	@Column(name = "LANGUAGE")
	private String language;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "ZIP")
	private String zip;

	@Column(name = "LAST_LOGIN")
	private Timestamp lastLogin;

	@Column(name = "LAST_LOGIN_IP")
	private String lastLoginIP;

	@Column(name = "PREVIOUS_PASSWORDS")
	private String previousPasswords;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "PASS_RETRY_COUNT")
	private Integer passRetryCount;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_PASSWORD_CHANGE")
	private Timestamp lastPassWordChange;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "SECURITY_QUESTION")
	private String securityQuestion;
	
	@Column(name = "SECURITY_ANSWER")
	private String securityAnswer;
	
	@Column(name = "REASON")
	private String reason;
	
	@Column(name = "SERVICE_TYPE")
	private Integer serviceType;
	
	@Column(name = "USER_ROLE_TYPE")
	private String userRoleType;
	
	@Column(name = "ENTITY_ID")
	private Long entityId;
	
    @Column(name = "LAST_LOGIN_TIME")
    private String lastLonginTime;


    public String getLastLonginTime() {
      return lastLonginTime;
    }

    public void setLastLonginTime(String lastLonginTime) {
      this.lastLonginTime = lastLonginTime;
    }
	
	/**
	 * @return the adminUserId
	 */
	public Long getAdminUserId() {
		return adminUserId;
	}

	/**
	 * @param adminUserId
	 *            the adminUserId to set
	 */
	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	/**
	 * @return the userRoleId
	 */
	public Long getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId
	 *            the userRoleId to set
	 */
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @param firstName
	 *            the firstName to set
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
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the emailToken
	 */
	public String getEmailToken() {
		return emailToken;
	}

	/**
	 * @param emailToken
	 *            the emailToken to set
	 */
	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

	/**
	 * @return the emailVerified
	 */
	public Integer getEmailVerified() {
		return emailVerified;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	
	/**
	 * @param emailVerified
	 *            the emailVerified to set
	 */
	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param address2
	 *            the address2 to set
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
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	public final String getReason() {
		return reason;
	}

	public final void setReason(String reason) {
		this.reason = reason;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the lastLogin
	 */
	public Timestamp getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin
	 *            the lastLogin to set
	 */
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the lastLoginIP
	 */
	public String getLastLoginIP() {
		return lastLoginIP;
	}

	/**
	 * @param lastLoginIP
	 *            the lastLoginIP to set
	 */
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	/**
	 * @return the previousPasswords
	 */
	public String getPreviousPasswords() {
		return previousPasswords;
	}

	/**
	 * @param previousPasswords
	 *            the previousPasswords to set
	 */
	public void setPreviousPasswords(String previousPasswords) {
		this.previousPasswords = previousPasswords;
	}

	/**
	 * @return the lastPassWordChange
	 */
	public Timestamp getLastPassWordChange() {
		return lastPassWordChange;
	}

	/**
	 * @param lastPassWordChange
	 *            the lastPassWordChange to set
	 */
	public void setLastPassWordChange(Timestamp lastPassWordChange) {
		this.lastPassWordChange = lastPassWordChange;
	}

	/**
	 * @return the passRetryCount
	 */
	public Integer getPassRetryCount() {
		return passRetryCount;
	}

	/**
	 * @param passRetryCount
	 *            the passRetryCount to set
	 */
	public void setPassRetryCount(Integer passRetryCount) {
		this.passRetryCount = passRetryCount;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	
	public String getUserRoleType() {
		return userRoleType;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setUserRoleType(String userRoleType) {
		this.userRoleType = userRoleType;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
	   * @param serviceType the serviceType to set
	   */
	  public void setServiceType(Integer serviceType) {
	    this.serviceType = serviceType;
	  }

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	/**
	   * @return the serviceType
	   */
	  public Integer getServiceType() {
	    return serviceType;
	  }

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
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
