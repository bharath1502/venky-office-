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
@Table(name = "PG_PROGRAM_MANAGER")
public class ProgramManager implements Serializable {

  private static final long serialVersionUID = -7177406037133493575L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "ISSUANCE_PM_ID")
  private Long issuancepmid;

  @Column(name = "COMPANY_NAME")
  private String companyName;

  @Column(name = "DEFAULT_PROGRAM_MANAGER")
  private Boolean defaultProgramManager;

  @Column(name = "BUSINESS_NAME")
  private String businessName;

  @Column(name = "PROGRAM_MANAGER_NAME")
  private String programManagerName;

  @Column(name = "CONTACT_NAME")
  private String contactName;

  @Column(name = "CONTACT_PHONE")
  private String contactPhone;

  @Column(name = "CONTACT_EMAIL")
  private String contactEmail;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "PROGRAMLOGO")
  private byte[] programManagerLogo;

  @Column(name = "REASON")
  private String reason;

  @Column(name = "CREATED_DATE", updatable = false)
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "CREATED_BY", updatable = false)
  private String createdBy;

  @Column(name = "EXTENSION")
  private String extension;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "PROGRAM_MANAGER_ID", referencedColumnName = "ID")
  private Set<BankProgramManagerMap> bankProgramManagerMaps;
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "PM_ID", referencedColumnName = "ID")
  private Set<PmCardProgamMapping> cardProgamMapping;

  @Column(name = "ACCOUNT_CURRENCY")
  private String accountCurrency;

	@Column(name = "PM_STATE")
	private String state;
	
	@Column(name = "PM_COUNTRY")
	private String country;
	
	@Column(name = "PM_TIMEZONE")
	private String pmTimeZone;
	
	@Column(name = "PM_SYSTEM_CONVERTED_TIME")
	private String pmSystemConvertedTime;
	
	@Column(name = "BATCH_PREFIX")
	private String batchPrefix;
	
	@Column(name = "SHEDULER_RUN_TIME")
	private String schedulerRunTime;
  

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getProgramManagerLogo() {
    return programManagerLogo;
  }

  public void setProgramManagerLogo(byte[] programManagerLogo) {
    this.programManagerLogo = programManagerLogo;
  }

  public Set<BankProgramManagerMap> getBankProgramManagerMaps() {
    return bankProgramManagerMaps;
  }

  public void setBankProgramManagerMaps(Set<BankProgramManagerMap> bankProgramManagerMaps) {
    this.bankProgramManagerMaps = bankProgramManagerMaps;
  }

  public Boolean getDefaultProgramManager() {
    return defaultProgramManager;
  }

  public void setDefaultProgramManager(Boolean defaultProgramManager) {
    this.defaultProgramManager = defaultProgramManager;
  }

  public String getAccountCurrency() {
    return accountCurrency;
  }

  public void setAccountCurrency(String accountCurrency) {
    this.accountCurrency = accountCurrency;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getBusinessName() {
    return businessName;
  }

  public String getProgramManagerName() {
    return programManagerName;
  }

  public String getContactName() {
    return contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public String getStatus() {
    return status;
  }

  public String getReason() {
    return reason;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public void setProgramManagerName(String programManagerName) {
    this.programManagerName = programManagerName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

	/**
	 * @return the cardProgamMapping
	 */
	public Set<PmCardProgamMapping> getCardProgamMapping() {
		return cardProgamMapping;
  }

	/**
	 * @param cardProgamMapping
	 *            the cardProgamMapping to set
	 */
	public void setCardProgamMapping(Set<PmCardProgamMapping> cardProgamMapping) {
		this.cardProgamMapping = cardProgamMapping;
   }
	
	/**
	 * @return the issuancepmid
	 */
	public Long getIssuancepmid() {
		return issuancepmid;
	}

	/**
	 * @param issuancepmid
	 *            the issuancepmid to set
	 */
	public void setIssuancepmid(Long issuancepmid) {
		this.issuancepmid = issuancepmid;
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
	 * @return the pmTimeZone
	 */
	public String getPmTimeZone() {
		return pmTimeZone;
	}

	/**
	 * @param pmTimeZone the pmTimeZone to set
	 */
	public void setPmTimeZone(String pmTimeZone) {
		this.pmTimeZone = pmTimeZone;
	}

	/**
	 * @return the pmSystemConvertedTime
	 */
	public String getPmSystemConvertedTime() {
		return pmSystemConvertedTime;
	}

	/**
	 * @param pmSystemConvertedTime the pmSystemConvertedTime to set
	 */
	public void setPmSystemConvertedTime(String pmSystemConvertedTime) {
		this.pmSystemConvertedTime = pmSystemConvertedTime;
	}

	/**
	 * @return the batchPrefix
	 */
	public String getBatchPrefix() {
		return batchPrefix;
	}

	/**
	 * @param batchPrefix the batchPrefix to set
	 */
	public void setBatchPrefix(String batchPrefix) {
		this.batchPrefix = batchPrefix;
	}

	/**
	 * @return the schedulerRunTime
	 */
	public String getSchedulerRunTime() {
		return schedulerRunTime;
	}

	/**
	 * @param schedulerRunTime the schedulerRunTime to set
	 */
	public void setSchedulerRunTime(String schedulerRunTime) {
		this.schedulerRunTime = schedulerRunTime;
	}
	
}
