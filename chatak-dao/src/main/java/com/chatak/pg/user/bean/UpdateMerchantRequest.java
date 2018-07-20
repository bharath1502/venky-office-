package com.chatak.pg.user.bean;

import java.util.List;
import java.util.Map;

import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.CommonUtil;

public class UpdateMerchantRequest extends MerchantRequest {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String userPassword;

  private String businessType;

  private String lookingFor;

  private String payOutAt;

  private String merchantCategory;

  private Boolean allowAdvancedFraudFilter;

  private Integer nmasRequired;

  private String declineReason;

  private String businessName;

  private String merchantCode;
  
  private Long phone;
  
  private String address1;
  
  private String city;

  private String state;

  private String country;

  private String pin;
  
  private Integer status;
  
  private String businessStartDate;
  
  private String userName;

  private String businessURL;
  
  private String firstName;

  private String lastName;
  
  private String emailId;
  
  private List<String> cardProgramIds;

  private List<Long> entitiesId;
  
  private String associatedTo;
  
  private String merchantType;
  
  private String process;
  
  private Map<Long, Long> cardProgramAndEntityId;

  public String validate() {
    String message = "";
    if (!CommonUtil.isNullAndEmpty(businessName)
        && merchantCode.length() != PGConstants.LENGTH_MERCHANT_ID) {
      message = "merchant_code value should be of 15 digits";
    } else if (CommonUtil.isNullAndEmpty(businessName)) {
      message = "businessName is the Required field";
    } else if (phone == null) {
      message = "phone is the Required field";
    } else if (CommonUtil.isNullAndEmpty(city)) {
        message = "city is the Required field";
    } else if (CommonUtil.isNullAndEmpty(address1)) {
      message = "address1 is the Required field";
    } else if (CommonUtil.isNullAndEmpty(pin)) {
        message = "pin is the Required field";
    } else if (CommonUtil.isNullAndEmpty(state)) {
      message = "state is the Required field";
    } else if (CommonUtil.isNullAndEmpty(country)) {
      message = "country is the Required field";
    } else if (status == null) {
      message = "status is the Required field";
    }

    return message;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public String getLookingFor() {
    return lookingFor;
  }

  public void setLookingFor(String lookingFor) {
    this.lookingFor = lookingFor;
  }

  public String getPayOutAt() {
    return payOutAt;
  }

  public void setPayOutAt(String payOutAt) {
    this.payOutAt = payOutAt;
  }

  public String getMerchantCategory() {
    return merchantCategory;
  }

  public void setMerchantCategory(String merchantCategory) {
    this.merchantCategory = merchantCategory;
  }

  public Boolean getAllowAdvancedFraudFilter() {
    return allowAdvancedFraudFilter;
  }

  public void setAllowAdvancedFraudFilter(Boolean allowAdvancedFraudFilter) {
    this.allowAdvancedFraudFilter = allowAdvancedFraudFilter;
  }

  public Integer getNmasRequired() {
    return nmasRequired;
  }

  public void setNmasRequired(Integer nmasRequired) {
    this.nmasRequired = nmasRequired;
  }

  public String getDeclineReason() {
    return declineReason;
  }

  public void setDeclineReason(String declineReason) {
    this.declineReason = declineReason;
  }

  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getCity() {
    return city;
  }
  
  public String getMerchantCode() {
    return merchantCode;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }
  
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }
  
  public String getBusinessName() {
    return businessName;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPin() {
    return pin;
  }
  
  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getBusinessStartDate() {
    return businessStartDate;
  }

  public void setBusinessStartDate(String businessStartDate) {
    this.businessStartDate = businessStartDate;
  }

  public String getFirstName() {
    return firstName;
  }
  
  public String getUserName() {
    return userName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getBusinessURL() {
    return businessURL;
  }

  public String getEmailId() {
    return emailId;
  }
  
  public void setBusinessURL(String businessURL) {
    this.businessURL = businessURL;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public List<String> getCardProgramIds() {
		return cardProgramIds;
  }

  public void setCardProgramIds(List<String> cardProgramIds) {
		this.cardProgramIds = cardProgramIds;
  }

  public List<Long> getEntitiesId() {
		return entitiesId;
  }

  public void setEntitiesId(List<Long> entitiesId) {
		this.entitiesId = entitiesId;
  }

  public String getAssociatedTo() {
		return associatedTo;
  }

  public void setAssociatedTo(String associatedTo) {
		this.associatedTo = associatedTo;
  }

  public String getMerchantType() {
		return merchantType;
  }

  public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
  }

  public String getProcess() {
		return process;
  }

  public void setProcess(String process) {
		this.process = process;
  }

  /**
   * @return the cardProgramAndEntityId
   */
  public Map<Long, Long> getCardProgramAndEntityId() {
    return cardProgramAndEntityId;
  }

  /**
   * @param cardProgramAndEntityId the cardProgramAndEntityId to set
   */
  public void setCardProgramAndEntityId(Map<Long, Long> cardProgramAndEntityId) {
    this.cardProgramAndEntityId = cardProgramAndEntityId;
  }
  
}
