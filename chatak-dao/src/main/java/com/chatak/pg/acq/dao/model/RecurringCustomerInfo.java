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
@Table(name = "PG_RECURRING_CUSTOMER_INFO")
public class RecurringCustomerInfo implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 2436658260377459662L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_RECURRING_ID", sequenceName = "SEQ_PG_RECURR_CUST_INFO_ID")
  @GeneratedValue(generator = "SEQ_PG_RECURRING_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "RECURRING_CUSTOMER_INFO_ID")
  private Long recurringCustInfoId;

  @Column(name = "CUSTOMER_ID")
  private String customerId;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "EMAIL_ID")
  private String emailId;

  @Column(name="DAYTIME_PHONE")
  private String daytimePhone;
  
  @Column(name="EVENING_PHONE")
  private String eveningPhone;
  
  @Column(name = "MOBILE_NUMBER")
  private String mobileNumber;

  @Column(name = "ADDRESS1")
  private String address1;

  @Column(name = "ADDRESS2")
  private String address2;

  @Column(name = "ADDRESS3")
  private String address3;

  @Column(name = "CITY")
  private String city;

  @Column(name = "STATE")
  private String state;

  @Column(name = "ZIPCODE")
  private String zipCode;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "AREA")
  private String area;

  @Column(name = "FAX")
  private String fax;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "TERMS_FLAG")
  private String termsFlag;

  @Column(name = "TERMS_VERSION")
  private String termsVersion;
  
  @Column(name="DEPARTMENT")
  private String department;
  
  @Column(name="TITLE")
  private String title;
  
  @Column(name="COMPANY")
  private String businessName;
  
  @Column(name="PG_MERCHANT_ID")
  private String merchantId;
  
   /**
   * @return the recurringCustInfoId
   */
  public Long getRecurringCustInfoId() {
    return recurringCustInfoId;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }
  
  /**
   * @param recurringCustInfoId
   *          the recurringCustInfoId to set
   */
  public void setRecurringCustInfoId(Long recurringCustInfoId) {
    this.recurringCustInfoId = recurringCustInfoId;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }
  
  /**
   * @return the customerId
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }
  
  /**
   * @param customerId
   *          the customerId to set
   */
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  /**
   * @param emailId
   *          the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  /**
   * @return the mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * @param mobileNumber
   *          the mobileNumber to set
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * @param address2
   *          the address2 to set
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
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }
  /**
   * @param city
   *          the city to set
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
  
  /**
   * @param address1
   *          the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @param state
   *          the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the zipCode
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * @param zipCode
   *          the zipCode to set
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }
  
  /**
   * @param area
   *          the area to set
   */
  public void setArea(String area) {
    this.area = area;
  }
  
  /**
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @param createdDate
   *          the createdDate to set
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
   * @return the area
   */
  public String getArea() {
    return area;
  }

  /**
   * @param updatedDate
   *          the updatedDate to set
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
   * @param address3
   *          the address3 to set
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
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
   * @return the address3
   */
  public String getAddress3() {
    return address3;
  }

  /**
   * @param updatedBy
   *          the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the fax
   */
  public String getFax() {
    return fax;
  }

  /**
   * @param fax
   *          the fax to set
   */
  public void setFax(String fax) {
    this.fax = fax;
  }

  /**
   * @return the termsFlag
   */
  public String getTermsFlag() {
    return termsFlag;
  }

  /**
   * @param termsFlag the termsFlag to set
   */
  public void setTermsFlag(String termsFlag) {
    this.termsFlag = termsFlag;
  }

  /**
   * @return the termsVersion
   */
  public String getTermsVersion() {
    return termsVersion;
  }

  /**
   * @param termsVersion the termsVersion to set
   */
  public void setTermsVersion(String termsVersion) {
    this.termsVersion = termsVersion;
  }

/**
 * @return the daytimePhone
 */
public String getDaytimePhone() {
	return daytimePhone;
}

/**
 * @param daytimePhone the daytimePhone to set
 */
public void setDaytimePhone(String daytimePhone) {
	this.daytimePhone = daytimePhone;
}

/**
 * @return the eveningPhone
 */
public String getEveningPhone() {
	return eveningPhone;
}

/**
 * @param eveningPhone the eveningPhone to set
 */
public void setEveningPhone(String eveningPhone) {
	this.eveningPhone = eveningPhone;
}

/**
 * @return the department
 */
public String getDepartment() {
	return department;
}

/**
 * @param department the department to set
 */
public void setDepartment(String department) {
	this.department = department;
}

/**
 * @return the title
 */
public String getTitle() {
	return title;
}

/**
 * @param title the title to set
 */
public void setTitle(String title) {
	this.title = title;
}

/**
 * @return the businessName
 */
public String getBusinessName() {
	return businessName;
}

/**
 * @param businessName the businessName to set
 */
public void setBusinessName(String businessName) {
	this.businessName = businessName;
}

/**
 * @return the merchantId
 */
public String getMerchantId() {
	return merchantId;
}

/**
 * @param merchantId the merchantId to set
 */
public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
}

}
