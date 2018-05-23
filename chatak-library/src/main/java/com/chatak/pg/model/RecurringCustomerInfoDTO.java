package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class RecurringCustomerInfoDTO extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3968902042885383417L;

	private Long recurringCustInfoId;

	private String customerId;

	private String firstName;

	private String lastName;

	private String emailId;

	private String mobileNumber;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String zipCode;

	private String country;

	private String status;

	private String fax;

	private String address3;

	private String area;

	private String termsFlag;

	private String termsVersion;
	
	private String daytimePhone;
	
  private String eveningPhone;
	 
  private String department;
	 
  private String title;
	 
  private String businessName;
  
  private String merchantId;


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

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}

	/**
	 * @param address3
	 *            the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the recurringCustInfoId
	 */
	public Long getRecurringCustInfoId() {
		return recurringCustInfoId;
	}
	
	/**
   * @return the customerId
   */
  public String getCustomerId() {
    return customerId;
  }

	/**
	 * @param recurringCustInfoId
	 *            the recurringCustInfoId to set
	 */
	public void setRecurringCustInfoId(Long recurringCustInfoId) {
		this.recurringCustInfoId = recurringCustInfoId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
   * @param customerId
   *            the customerId to set
   */
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
   * @param emailId
   *            the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	/**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

	/**
	 * @param mobileNumber
	 *            the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	
	/**
   * @param address1
   *            the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	/**
   * @return the state
   */
  public String getState() {
    return state;
  }

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
   * @param state
   *            the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	
	/**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
   * @param country
   *            the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the termsVersion
	 */
	public String getTermsVersion() {
		return termsVersion;
	}

	/**
	 * @param termsVersion
	 *            the termsVersion to set
	 */
	public void setTermsVersion(String termsVersion) {
		this.termsVersion = termsVersion;
	}

	/**
	 * @return the termsFlag
	 */
	public String getTermsFlag() {
		return termsFlag;
	}

	/**
	 * @param termsFlag
	 *            the termsFlag to set
	 */
	public void setTermsFlag(String termsFlag) {
		this.termsFlag = termsFlag;
	}

  /**
   * @return the daytimePhone
   */
  public String getDaytimePhone() {
    return daytimePhone;
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
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param department the department to set
   */
  public void setDepartment(String department) {
    this.department = department;
  }

  /**
   * @return the businessName
   */
  public String getBusinessName() {
    return businessName;
  }
  
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param businessName the businessName to set
   */
  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }
  
}
