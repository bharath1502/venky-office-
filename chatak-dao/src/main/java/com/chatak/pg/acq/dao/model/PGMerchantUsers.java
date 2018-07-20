package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_MERCHANT_USERS")
public class PGMerchantUsers {

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_MERCHANT_USERS_ID", sequenceName = "SEQ_PG_MERCHANT_USERS")
  @GeneratedValue(generator = "SEQ_PG_MERCHANT_USERS_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "P_PASSWORD")
  private String merPassword;

  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "USER_TYPE")
  private String userType;
	
  @Column(name = "EMAIL_TOKEN")
  private String emailToken;

  @Column(name = "EMAIL_VERIFIED")
  private Integer emailVerified;
  
  @Column(name = "LAST_LOGIN_IP")
  private Timestamp lastLoginIP;

  @Column(name = "LAST_LOGIN")
  private Timestamp lastLogin;

  @Column(name = "SERVICE_TYPE")
  private Integer serviceType;
  
  @Column(name = "PREVIOUS_PASSWORDS")
  private String previousPasswords;

  @Column(name = "LAST_PASSWORD_CHANGE")
  private Timestamp lastPassWordChange;

  @Column(name = "PASS_RETRY_COUNT")
  private Integer passRetryCount;

  @Column(name = "SECURITY_QUESTION")
  private String securityQuestion;
  
  @Column(name = "SECURITY_ANSWER")
  private String securityAnswer;
  
  @Column(name = "REASON")
  private String reason;
  
  @Column(name = "PG_MERCHANT_ID")
	private Long pgMerchantId;
  
  @Column(name = "user_role_id")
  private Long userRoleId;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "PHONE")
  private String phone;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "USER_ROLE_TYPE")
  private String userRoleType;

  @Column(name = "LAST_LOGIN_TIME")
  private String lastLonginTime;

  public String getLastLonginTime() {
    return lastLonginTime;
  }

  public void setLastLonginTime(String lastLonginTime) {
    this.lastLonginTime = lastLonginTime;
  }


  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @return the merPassword
   */
  public String getMerPassword() {
    return merPassword;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param merPassword the merPassword to set
   */
  public void setMerPassword(String merPassword) {
    this.merPassword = merPassword;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
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

  /**
   * @return the emailToken
   */
  public String getEmailToken() {
    return emailToken;
  }

  /**
   * @param emailToken the emailToken to set
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
   * @param emailVerified the emailVerified to set
   */
  public void setEmailVerified(Integer emailVerified) {
    this.emailVerified = emailVerified;
  }

  /**
   * @return the lastLoginIP
   */
  public Timestamp getLastLoginIP() {
    return lastLoginIP;
  }

  /**
   * @param lastLoginIP the lastLoginIP to set
   */
  public void setLastLoginIP(Timestamp lastLoginIP) {
    this.lastLoginIP = lastLoginIP;
  }

  /**
   * @return the lastLogin
   */
  public Timestamp getLastLogin() {
    return lastLogin;
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
   * @param serviceType the serviceType to set
   */
  public void setServiceType(Integer serviceType) {
    this.serviceType = serviceType;
  }

  /**
   * @param lastPassWordChange the lastPassWordChange to set
   */
  public void setLastPassWordChange(Timestamp lastPassWordChange) {
    this.lastPassWordChange = lastPassWordChange;
  }

  /**
   * @return the serviceType
   */
  public Integer getServiceType() {
    return serviceType;
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
   * @param securityAnswer the securityAnswer to set
   */
  public void setSecurityAnswer(String securityAnswer) {
    this.securityAnswer = securityAnswer;
  }

  /**
   * @return the reason
   */
  public String getReason() {
    return reason;
  }

  /**
   * @param reason the reason to set
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  public Long getUserRoleId() {
    return userRoleId;
  }

  public void setUserRoleId(Long userRoleId) {
    this.userRoleId = userRoleId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the pgMerchantId
   */
  public Long getPgMerchantId() {
    return pgMerchantId;
  }

  /**
   * @param pgMerchantId the pgMerchantId to set
   */
  public void setPgMerchantId(Long pgMerchantId) {
    this.pgMerchantId = pgMerchantId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getUserRoleType() {
    return userRoleType;
  }

  public void setUserRoleType(String userRoleType) {
    this.userRoleType = userRoleType;
  }

}
