package com.chatak.pg.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ApplicationClientDTO implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = -9002041194071073056L;

  private Long id;

  private String appName;

  private String appDescription;

  private String appAdminEmail;

  private String AppClientName;

  private String AppClientEmail;

  private String AppClientPhone;

  private String AppClientAddress;

  private String AppClientCity;

  private String AppClientCountry;

  private String AppClientZip;

  private String AppClientRole;

  private String status;

  private Timestamp activeTill;

  private String appClientId;

  private String appClientAccess;

  private String appAuthUser;

  private String appAuthPass;

  private String createdBy;

  private String updatedBy;

  private Timestamp createdDate;

  private Timestamp updatedDate;

  private String refreshToken;

  /**
   * @return the appName
   */
  public String getAppName() {
    return appName;
  }

  /**
   * @param appName
   *          the appName to set
   */
  public void setAppName(String appName) {
    this.appName = appName;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the appAdminEmail
   */
  public String getAppAdminEmail() {
    return appAdminEmail;
  }

  /**
   * @param appAdminEmail
   *          the appAdminEmail to set
   */
  public void setAppAdminEmail(String appAdminEmail) {
    this.appAdminEmail = appAdminEmail;
  }

  /**
   * @return the appDescription
   */
  public String getAppDescription() {
    return appDescription;
  }

  /**
   * @param appDescription
   *          the appDescription to set
   */
  public void setAppDescription(String appDescription) {
    this.appDescription = appDescription;
  }

  /**
   * @return the appClientEmail
   */
  public String getAppClientEmail() {
    return AppClientEmail;
  }

  /**
   * @param appClientEmail
   *          the appClientEmail to set
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
   * @param appClientPhone
   *          the appClientPhone to set
   */
  public void setAppClientPhone(String appClientPhone) {
    AppClientPhone = appClientPhone;
  }

  /**
   * @return the appClientName
   */
  public String getAppClientName() {
    return AppClientName;
  }

  /**
   * @param appClientName
   *          the appClientName to set
   */
  public void setAppClientName(String appClientName) {
    AppClientName = appClientName;
  }

  /**
   * @return the appClientCity
   */
  public String getAppClientCity() {
    return AppClientCity;
  }

  /**
   * @param appClientCity
   *          the appClientCity to set
   */
  public void setAppClientCity(String appClientCity) {
    AppClientCity = appClientCity;
  }

  /**
   * @return the appClientAddress
   */
  public String getAppClientAddress() {
    return AppClientAddress;
  }

  /**
   * @param appClientAddress
   *          the appClientAddress to set
   */
  public void setAppClientAddress(String appClientAddress) {
    AppClientAddress = appClientAddress;
  }

  /**
   * @return the appClientZip
   */
  public String getAppClientZip() {
    return AppClientZip;
  }

  /**
   * @param appClientZip
   *          the appClientZip to set
   */
  public void setAppClientZip(String appClientZip) {
    AppClientZip = appClientZip;
  }

  /**
   * @return the appClientCountry
   */
  public String getAppClientCountry() {
    return AppClientCountry;
  }

  /**
   * @param appClientCountry
   *          the appClientCountry to set
   */
  public void setAppClientCountry(String appClientCountry) {
    AppClientCountry = appClientCountry;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the appClientRole
   */
  public String getAppClientRole() {
    return AppClientRole;
  }

  /**
   * @param appClientRole
   *          the appClientRole to set
   */
  public void setAppClientRole(String appClientRole) {
    AppClientRole = appClientRole;
  }

  /**
   * @return the appClientId
   */
  public String getAppClientId() {
    return appClientId;
  }

  /**
   * @param appClientId
   *          the appClientId to set
   */
  public void setAppClientId(String appClientId) {
    this.appClientId = appClientId;
  }

  /**
   * @return the activeTill
   */
  public Timestamp getActiveTill() {
    return activeTill;
  }

  /**
   * @param activeTill
   *          the activeTill to set
   */
  public void setActiveTill(Timestamp activeTill) {
    this.activeTill = activeTill;
  }

  /**
   * @return the appAuthUser
   */
  public String getAppAuthUser() {
    return appAuthUser;
  }

  /**
   * @param appAuthUser
   *          the appAuthUser to set
   */
  public void setAppAuthUser(String appAuthUser) {
    this.appAuthUser = appAuthUser;
  }

  /**
   * @return the appClientAccess
   */
  public String getAppClientAccess() {
    return appClientAccess;
  }

  /**
   * @param appClientAccess
   *          the appClientAccess to set
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
   * @param createdBy
   *          the createdBy to set
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
   * @param updatedBy
   *          the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the appAuthPass
   */
  public String getAppAuthPass() {
    return appAuthPass;
  }

  /**
   * @param appAuthPass
   *          the appAuthPass to set
   */
  public void setAppAuthPass(String appAuthPass) {
    this.appAuthPass = appAuthPass;
  }

  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param updatedDate
   *          the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate
   *          the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
