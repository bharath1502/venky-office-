package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_APPLICATION_CLIENT")
public class PGApplicationClient implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4478915016818495935L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_APPLICATION_CLIENT_ID", sequenceName = "SEQ_APPLICATION_CLIENT")
  @GeneratedValue(generator = "SEQ_PG_APPLICATION_CLIENT_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "APP_NAME")
  private String appName;

  @Column(name = "APP_DESCRIPTION")
  private String appDescription;

  @Column(name = "APP_ADMIN_EMAIL")
  private String appAdminEmail;

  @Column(name = "APP_CLIENT_NAME")
  private String AppClientName;

  @Column(name = "APP_CLIENT_EMAIL")
  private String AppClientEmail;

  @Column(name = "APP_CLIENT_PHONE")
  private String AppClientPhone;

  @Column(name = "APP_CLIENT_ADDRESS")
  private String AppClientAddress;

  @Column(name = "APP_CLIENT_CITY")
  private String AppClientCity;

  @Column(name = "APP_CLIENT_COUNTRY")
  private String AppClientCountry;

  @Column(name = "APP_CLIENT_ZIP")
  private String AppClientZip;

  @Column(name = "APP_CLIENT_ROLE")
  private String AppClientRole;

  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "ACTIVE_FROM")
  private Timestamp activeFrom;

  @Column(name = "ACTIVE_TILL")
  private Timestamp activeTill;

  @Column(name = "APP_CLIENT_ID")
  private String appClientId;

  @Column(name = "APP_CLIENT_ACCESS")
  private String appClientAccess;
  
  @Column(name = "APP_AUTH_USER")
  private String appAuthUser;

  @Column(name = "APP_AUTH_PASS")
  private String appAuthPass;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "UPDATED_date")
  private Timestamp updatedDate;
  
  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "REFRESH_TOKEN")
  private String refreshToken;

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
   * @return the appName
   */
  public String getAppName() {
    return appName;
  }

  /**
   * @param appName the appName to set
   */
  public void setAppName(String appName) {
    this.appName = appName;
  }

  /**
   * @return the appDescription
   */
  public String getAppDescription() {
    return appDescription;
  }

  /**
   * @param appDescription the appDescription to set
   */
  public void setAppDescription(String appDescription) {
    this.appDescription = appDescription;
  }

  /**
   * @return the appAdminEmail
   */
  public String getAppAdminEmail() {
    return appAdminEmail;
  }

  /**
   * @param appAdminEmail the appAdminEmail to set
   */
  public void setAppAdminEmail(String appAdminEmail) {
    this.appAdminEmail = appAdminEmail;
  }

  /**
   * @return the appClientName
   */
  public String getAppClientName() {
    return AppClientName;
  }

  /**
   * @param appClientName the appClientName to set
   */
  public void setAppClientName(String appClientName) {
    AppClientName = appClientName;
  }

  /**
   * @return the appClientEmail
   */
  public String getAppClientEmail() {
    return AppClientEmail;
  }

  /**
   * @param appClientEmail the appClientEmail to set
   */
  public void setAppClientEmail(String appClientEmail) {
    AppClientEmail = appClientEmail;
  }

  /**
   * @return the appClientPhone
   */
  public String getAppClientPhone() {
    return AppClientPhone;
  }

  /**
   * @param appClientPhone the appClientPhone to set
   */
  public void setAppClientPhone(String appClientPhone) {
    AppClientPhone = appClientPhone;
  }

  /**
   * @return the appClientAddress
   */
  public String getAppClientAddress() {
    return AppClientAddress;
  }

  /**
   * @param appClientAddress the appClientAddress to set
   */
  public void setAppClientAddress(String appClientAddress) {
    AppClientAddress = appClientAddress;
  }

  /**
   * @return the appClientCity
   */
  public String getAppClientCity() {
    return AppClientCity;
  }

  /**
   * @param appClientCity the appClientCity to set
   */
  public void setAppClientCity(String appClientCity) {
    AppClientCity = appClientCity;
  }

  /**
   * @return the appClientCountry
   */
  public String getAppClientCountry() {
    return AppClientCountry;
  }

  /**
   * @param appClientCountry the appClientCountry to set
   */
  public void setAppClientCountry(String appClientCountry) {
    AppClientCountry = appClientCountry;
  }

  /**
   * @return the appClientZip
   */
  public String getAppClientZip() {
    return AppClientZip;
  }

  /**
   * @param appClientZip the appClientZip to set
   */
  public void setAppClientZip(String appClientZip) {
    AppClientZip = appClientZip;
  }

  /**
   * @return the appClientRole
   */
  public String getAppClientRole() {
    return AppClientRole;
  }

  /**
   * @param appClientRole the appClientRole to set
   */
  public void setAppClientRole(String appClientRole) {
    AppClientRole = appClientRole;
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
   * @return the activeFrom
   */
  public Timestamp getActiveFrom() {
    return activeFrom;
  }

  /**
   * @param activeFrom the activeFrom to set
   */
  public void setActiveFrom(Timestamp activeFrom) {
    this.activeFrom = activeFrom;
  }

  /**
   * @return the activeTill
   */
  public Timestamp getActiveTill() {
    return activeTill;
  }

  /**
   * @param activeTill the activeTill to set
   */
  public void setActiveTill(Timestamp activeTill) {
    this.activeTill = activeTill;
  }

  /**
   * @return the appClientId
   */
  public String getAppClientId() {
    return appClientId;
  }

  /**
   * @param appClientId the appClientId to set
   */
  public void setAppClientId(String appClientId) {
    this.appClientId = appClientId;
  }

  /**
   * @return the appClientAccess
   */
  public String getAppClientAccess() {
    return appClientAccess;
  }

  /**
   * @param appClientAccess the appClientAccess to set
   */
  public void setAppClientAccess(String appClientAccess) {
    this.appClientAccess = appClientAccess;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }
  
  /**
   * @param appAuthPass the appAuthPass to set
   */
  public void setAppAuthPass(String appAuthPass) {
    this.appAuthPass = appAuthPass;
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
   * @return the appAuthPass
   */
  public String getAppAuthPass() {
    return appAuthPass;
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
   * @param appAuthUser the appAuthUser to set
   */
  public void setAppAuthUser(String appAuthUser) {
    this.appAuthUser = appAuthUser;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }
  
  /**
   * @return the appAuthUser
   */
  public String getAppAuthUser() {
    return appAuthUser;
  }

  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
