package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PG_PARTNER")
public class Partner implements Serializable {

  private static final long serialVersionUID = -7959795778180388896L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PARTNER_ID")
  private Long partnerId;

  @Column(name = "PROGRAM_MANAGER_ID")
  private Long programManagerId;

  @Column(name = "PARTNER_NAME")
  private String partnerName;

  @Column(name = "COMPANY_NAME")
  private String companyName;

  @Column(name = "BUSINESS_NAME")
  private String businessEntityName;

  @Column(name = "PARTNER_CLIENT_ID")
  private String partnerClientId;

  @Column(name = "ADDRESS1")
  private String address1;

  @Column(name = "ADDRESS2")
  private String address2;

  @Column(name = "CITY")
  private String city;

  @Column(name = "ZIP")
  private String zip;

  @Column(name = "STATE")
  private String state;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "LOGO")
  private byte[] partnerLogo;

  @Column(name = "CONTACT_NAME")
  private String contactPerson;

  @Column(name = "CONTACT_PHONE")
  private String contactPhone;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CREATED_DATE", updatable = false)
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "CREATED_BY", updatable = false)
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "REASON")
  private String reason;

  @Column(name = "WHITELIST_IP_ADDRESS")
  private String whiteListIPAddress;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARTNER_ID", referencedColumnName = "PARTNER_ID")
  private Set<BankPartnerMap> bankPartnerMaps;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARTNER_ID", referencedColumnName = "PARTNER_ID")
  private Set<PartnerAccount> partnerAccounts;

  @Column(name = "EXTENSION")
  private String extension;

  @Column(name = "PARTNER_TYPE")
  private String partnerType;

  public String getAccountCurrency() {
    return accountCurrency;
  }

  public void setAccountCurrency(String accountCurrency) {
    this.accountCurrency = accountCurrency;
  }

  @Column(name = "ACCOUNT_CURRENCY")
  private String accountCurrency;

  /**
   * @return the extension
   */
  public String getExtension() {
    return extension;
  }

  /**
   * @param extension the extension to set
   */
  public void setExtension(String extension) {
    this.extension = extension;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public Long getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(Long programManagerId) {
    this.programManagerId = programManagerId;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getBusinessEntityName() {
    return businessEntityName;
  }

  public void setBusinessEntityName(String businessEntityName) {
    this.businessEntityName = businessEntityName;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public byte[] getPartnerLogo() {
    return partnerLogo;
  }

  public void setPartnerLogo(byte[] partnerLogo) {
    this.partnerLogo = partnerLogo;
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Set<BankPartnerMap> getBankPartnerMaps() {
    return bankPartnerMaps;
  }

  public void setBankPartnerMaps(Set<BankPartnerMap> bankPartnerMaps) {
    this.bankPartnerMaps = bankPartnerMaps;
  }

  public Set<PartnerAccount> getPartnerAccounts() {
    return partnerAccounts;
  }

  public void setPartnerAccounts(Set<PartnerAccount> partnerAccounts) {
    this.partnerAccounts = partnerAccounts;
  }

  public String getPartnerClientId() {
    return partnerClientId;
  }

  public void setPartnerClientId(String partnerClientId) {
    this.partnerClientId = partnerClientId;
  }

  public String getWhiteListIPAddress() {
    return whiteListIPAddress;
  }

  public void setWhiteListIPAddress(String whiteListIPAddress) {
    this.whiteListIPAddress = whiteListIPAddress;
  }

  /**
   * @return the partnerType
   */
  public String getPartnerType() {
    return partnerType;
  }

  /**
   * @param partnerType the partnerType to set
   */
  public void setPartnerType(String partnerType) {
    this.partnerType = partnerType;
  }

}
