/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Aug 2, 2016
 * @Time: 2:19:12 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class BankRequest extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4884403720981093283L;

	private Long id;

	private Long bankId;

	private String bankName;

	private String bankShortName;

	private String status;

	private String acquirerId;

	private String address1;

	private String city;

	private String address2;

	private String state;

	private String zip;

	private String country;

	private String updatedBy;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private Long currencyId;

	private String currencyCodeAlpha;

	private String extension;

	private String contactPersonPhone;

	private String contactPersonCell;

	private String contactPersonName;

	private String contactPersonFax;

	private String contactPersonEmail;

	private List<String> statusList;
	
	private List<Long> bankIds;
	
	private Long issuanceBankId;
	
	private String bankCode;
	
	private String settlRoutingNumber;
	
	private String settlAccountNumber;

	public String getCurrencyCodeAlpha() {
		return currencyCodeAlpha;
	}

	public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
		this.currencyCodeAlpha = currencyCodeAlpha;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankShortName() {
		return bankShortName;
	}

	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcquirerId() {
		return acquirerId;
	}

	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	/**
	 * @return the bankId
	 */
	public Long getBankId() {
		return bankId;
	}

	/**
	 * @param bankId
	 *            the bankId to set
	 */
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the settlRoutingNumber
	 */
	public String getSettlRoutingNumber() {
		return settlRoutingNumber;
	}

	/**
	 * @param settlRoutingNumber
	 *            the settlRoutingNumber to set
	 */
	public List<Long> getBankIds() {
		return bankIds;
	}

	/**
	 * @param bankIds the bankIds to set
	 */
	public void setBankIds(List<Long> bankIds) {
		this.bankIds = bankIds;
	}

	/**
	 * @return the issuanceBankId
	 */
	public Long getIssuanceBankId() {
		return issuanceBankId;
	}

	/**
	 * @param issuanceBankId the issuanceBankId to set
	 */
	public void setIssuanceBankId(Long issuanceBankId) {
		this.issuanceBankId = issuanceBankId;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 *            the bankCode to set
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * @param settlRoutingNumber the settlRoutingNumber to set
	 */
	public void setSettlRoutingNumber(String settlRoutingNumber) {
		this.settlRoutingNumber = settlRoutingNumber;
	}

	/**
	 * @return the settlAccountNumber
	 */
	public String getSettlAccountNumber() {
		return settlAccountNumber;
	}

	/**
	 * @param settlAccountNumber
	 *            the settlAccountNumber to set
	 * @param settlAccountNumber the settlAccountNumber to set
	 */
	public void setSettlAccountNumber(String settlAccountNumber) {
		this.settlAccountNumber = settlAccountNumber;
	}

	/**
	 * @return the contactPersonPhone
	 */
	public String getContactPersonPhone() {
		return contactPersonPhone;
	}

	/**
	 * @param contactPersonPhone
	 *            the contactPersonPhone to set
	 */
	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}

	/**
	 * @return the contactPersonCell
	 */
	public String getContactPersonCell() {
		return contactPersonCell;
	}

	/**
	 * @param contactPersonCell
	 *            the contactPersonCell to set
	 */
	public void setContactPersonCell(String contactPersonCell) {
		this.contactPersonCell = contactPersonCell;
	}

	/**
	 * @return the contactPersonName
	 */
	public String getContactPersonName() {
		return contactPersonName;
	}

	/**
	 * @param contactPersonName
	 *            the contactPersonName to set
	 */
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	/**
	 * @return the contactPersonFax
	 */
	public String getContactPersonFax() {
		return contactPersonFax;
	}

	/**
	 * @param contactPersonFax
	 *            the contactPersonFax to set
	 */
	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
	}

	/**
	 * @return the contactPersonEmail
	 */
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	/**
	 * @param contactPersonEmail
	 *            the contactPersonEmail to set
	 */
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}

}
