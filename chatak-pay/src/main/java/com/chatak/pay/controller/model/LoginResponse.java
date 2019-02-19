package com.chatak.pay.controller.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.chatak.pay.model.TSMResponse;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.model.MposFeatures;

/**
 * REST API model class to hold response login result
 * 
 * @Author: Girmiti Software
 * @Date: 16-Aug-2014
 * @Time: 12:26:45 pm
 * @Version: 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3128219292788373292L;

	private Boolean status;
	
	private Long userId;

	private String message;

	private String email;

	private String accessToken;
	
	private Long subServiceProviderId;

	private Long serviceProviderId;

	private List<String> existingFeature;
	
	private String userType;

	private Long userRoleId;

	private Boolean makerCheckerRequired;

	private String merchantCode;

	private String terminalID;

	private String bussinessName;
	
	private CurrencyDTO currencyDTO;
	
	private TSMResponse terminalData;
	
	private String address;

	private String city;

	private String state;

	private String country;

	private String pin;

	private String refreshToken;
	
	private List<String> mpsoFeatures;
	
	public List<String> getMpsoFeatures() {
	  return mpsoFeatures;
	}

	public void setMpsoFeatures(List<String> mpsoFeatures) {
	  this.mpsoFeatures = mpsoFeatures;
	}

  /**
	 * @return the subServiceProviderId
	 */
	public Long getSubServiceProviderId() {
		return subServiceProviderId;
	}

	/**
	 * @param subServiceProviderId
	 *            the subServiceProviderId to set
	 */
	public void setSubServiceProviderId(Long subServiceProviderId) {
		this.subServiceProviderId = subServiceProviderId;
	}
	
	/**
	 * @return the serviceProviderId
	 */
	public Long getServiceProviderId() {
		return serviceProviderId;
	}

	/**
	 * @param serviceProviderId
	 *            the serviceProviderId to set
	 */
	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken
	 *            the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
	 * @return the existingFeature
	 */
	public List<String> getExistingFeature() {
		return existingFeature;
	}

	/**
	 * @param existingFeature
	 *            the existingFeature to set
	 */
	public void setExistingFeature(List<String> existingFeature) {
		this.existingFeature = existingFeature;
	}

	/**
	 * @return the makerCheckerRequired
	 */
	public Boolean getMakerCheckerRequired() {
		return makerCheckerRequired;
	}

	/**
	 * @param makerCheckerRequired
	 *            the makerCheckerRequired to set
	 */
	public void setMakerCheckerRequired(Boolean makerCheckerRequired) {
		this.makerCheckerRequired = makerCheckerRequired;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * @param merchantCode
	 *            the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * @return the terminalID
	 */
	public String getTerminalID() {
		return terminalID;
	}

	/**
	 * @param terminalID
	 *            the terminalID to set
	 */
	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	/**
	 * @return the bussinessName
	 */
	public String getBussinessName() {
		return bussinessName;
	}

	/**
	 * @param bussinessName
	 *            the bussinessName to set
	 */
	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	/**
	 * @return the currencyDTO
	 */
	public CurrencyDTO getCurrencyDTO() {
		return currencyDTO;
	}

	/**
	 * @param currencyDTO the currencyDTO to set
	 */
	public void setCurrencyDTO(CurrencyDTO currencyDTO) {
		this.currencyDTO = currencyDTO;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public TSMResponse getTerminalData() {
		return terminalData;
	}

	public void setTerminalData(TSMResponse terminalData) {
		this.terminalData = terminalData;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

    public String getRefreshToken() {
      return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
    }

}
