/**
 * 
 */
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

/**
 * @Author: Girmiti Software
 * @Date: May 8, 2018
 * @Time: 4:27:15 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_ISO")
public class Iso implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 6149871788122081868L;

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ID")
	  private Long id;
	  
	  @Column(name = "ISO_NAME")
	  private String isoName;
	  
	  @Column(name = "BUSINESS_ENTITY_NAME")
	  private String businessEntityName;
	  
	  @Column(name = "CONTACT_PERSON")
	  private String contactPerson;
	  
	  @Column(name = "PHONE_NUMBER")
	  private String phoneNumber;
	  
	  @Column(name = "EXTENSION")
	  private String extension;
	  
	  @Column(name = "STATUS")
	  private String status;
	  
	  @Column(name = "ADDRESS")
	  private String address;
	  
	  @Column(name = "COUNTRY")
	  private String country;
	  
	  @Column(name = "STATE")
	  private String state;
	  
	  @Column(name = "CITY")
	  private String city;
	  
	  @Column(name = "ZIP_CODE")
	  private String zipCode;
	  
	  @Column(name = "ISO_LOGO")
	  private byte[] isoLogo;
	  
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	  @JoinColumn(name = "ISO_ID", referencedColumnName = "ID")
	  private Set<IsoPmMap> pgIsoPmMap;
	  
	  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	  @JoinColumn(name = "ISO_ID", referencedColumnName = "ID")
	  private Set<IsoCardProgramMap> pgIsoCardProgramMap;
	  
	  @Column(name = "CURRENCY", updatable = false)
	  private String currency;
	  
	  @Column(name = "EMAIL")
	  private String email;
	  
	  @Column(name = "BANK_NAME")
	  private String bankName;

	  @Column(name = "BANK_ACC_NUM")
	  private String bankAccNum;

	  @Column(name = "ROUTING_NUMBER")
	  private String routingNumber;
	  
	  @Column(name = "PROCESSOR")
	  private String processor;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	  @JoinColumn(name = "ISO_ID", referencedColumnName = "ID")
	  private Set<PanRanges> panRanges;


	/**
	 * @return the panRanges
	 */
	public Set<PanRanges> getPanRanges() {
		return panRanges;
	}

	/**
	 * @param panRanges the panRanges to set
	 */
	public void setPanRanges(Set<PanRanges> panRanges) {
		this.panRanges = panRanges;
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
	 * @return the isoName
	 */
	public String getIsoName() {
		return isoName;
	}

	/**
	 * @param isoName the isoName to set
	 */
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	/**
	 * @return the businessEntityName
	 */
	public String getBusinessEntityName() {
		return businessEntityName;
	}

	/**
	 * @param businessEntityName the businessEntityName to set
	 */
	public void setBusinessEntityName(String businessEntityName) {
		this.businessEntityName = businessEntityName;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
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
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the isoLogo
	 */
	public byte[] getIsoLogo() {
		return isoLogo;
	}

	/**
	 * @param isoLogo the isoLogo to set
	 */
	public void setIsoLogo(byte[] isoLogo) {
		this.isoLogo = isoLogo;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
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
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the pgIsoPmMap
	 */
	public Set<IsoPmMap> getPgIsoPmMap() {
		return pgIsoPmMap;
	}

	/**
	 * @param pgIsoPmMap the pgIsoPmMap to set
	 */
	public void setPgIsoPmMap(Set<IsoPmMap> pgIsoPmMap) {
		this.pgIsoPmMap = pgIsoPmMap;
	}

	/**
	 * @return the pgIsoCardProgramMap
	 */
	public Set<IsoCardProgramMap> getPgIsoCardProgramMap() {
		return pgIsoCardProgramMap;
	}

	/**
	 * @param pgIsoCardProgramMap the pgIsoCardProgramMap to set
	 */
	public void setPgIsoCardProgramMap(Set<IsoCardProgramMap> pgIsoCardProgramMap) {
		this.pgIsoCardProgramMap = pgIsoCardProgramMap;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
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
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankAccNum
	 */
	public String getBankAccNum() {
		return bankAccNum;
	}

	/**
	 * @param bankAccNum the bankAccNum to set
	 */
	public void setBankAccNum(String bankAccNum) {
		this.bankAccNum = bankAccNum;
	}

	/**
	 * @return the routingNumber
	 */
	public String getRoutingNumber() {
		return routingNumber;
	}

	/**
	 * @param routingNumber the routingNumber to set
	 */
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	
	public String getProcessor() {
		return processor;
	}
		
	public void setProcessor(String processor) {
		this.processor = processor;
	}
}
