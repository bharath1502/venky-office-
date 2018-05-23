package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PG_DEVICE_MANAGEMENT")
public class PGTerminalDeviceMangement  implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2517592090531292245L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_DEVICE_MANAGEMENT_ID", sequenceName = "SEQ_PG_MOBILE_DEVICE_MGMT")
	@GeneratedValue(generator = "SEQ_PG_DEVICE_MANAGEMENT_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEVICE_MANAGEMENT_ID")
	private Long deviceManagementId;
	
	@Column(name = "POS_DEVICE_ID")
	private Long deviceId;
	
	@Column(name = "IMEI_NO")
	private String imeiNo;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "TERMINAL_ID")
	PGMerchantTerminal pgMerchantTerminal;		
	
	@Column(name = "APPLICATION_ID")
	private String applicationId;
	
	@Column(name = "MAGNETIC_STRIPE_PARAMETERS")
	private String magneticStripeCardParameters;
	
	@Column(name = "CA_PUBLIC_KEYS")
	private String caPublicKeys;
	
	@Column(name = "ACTION_CODE_PARAMETERS")
	private String actionCodeParameter;
	
	@Column(name = "STAN_NUMBER")
	private String stanNumber;
	
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;
	
	@Column(name = "BATCH_NUMBER")
	private String batchNumber;
	
	@Column(name = "TIP_PERCENTAGE")
	private String tipPercentage;
	
	
	@Column(name = "JUST_ALLOWED")
	private Boolean adjustAllowed;
	
	@Column(name = "REFUND_ALLOWED")
	private Boolean refundAllowed;
	
	@Column(name = "PREAUTH_ALLOWED")
	private Boolean preauthAllowed;
	
	@Column(name = "TIP_ALLOWED")
	private Boolean tipAllowed;
	
	@Column(name = "TERMINAL_CONFIG_PROFILE")
	private String terminalConfigurationProfile;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "REMARKS")
	private String remarks;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PROFILE_ID")
	PGParameterProfile parameterProfile;

	/**
	 * @return the deviceManagementId
	 */
	public Long getDeviceManagementId() {
		return deviceManagementId;
	}

	/**
	 * @param deviceManagementId the deviceManagementId to set
	 */
	public void setDeviceManagementId(Long deviceManagementId) {
		this.deviceManagementId = deviceManagementId;
	}

	/**
	 * @return the deviceId
	 */
	public Long getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the imeiNo
	 */
	public String getImeiNo() {
		return imeiNo;
	}

	/**
	 * @param imeiNo the imeiNo to set
	 */
	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	/**
	 * @return the pgMerchantTerminal
	 */
	public PGMerchantTerminal getPgMerchantTerminal() {
		return pgMerchantTerminal;
	}

	/**
	 * @param pgMerchantTerminal the pgMerchantTerminal to set
	 */
	public void setPgMerchantTerminal(PGMerchantTerminal pgMerchantTerminal) {
		this.pgMerchantTerminal = pgMerchantTerminal;
	}

	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the magneticStripeCardParameters
	 */
	public String getMagneticStripeCardParameters() {
		return magneticStripeCardParameters;
	}

	/**
	 * @param magneticStripeCardParameters the magneticStripeCardParameters to set
	 */
	public void setMagneticStripeCardParameters(String magneticStripeCardParameters) {
		this.magneticStripeCardParameters = magneticStripeCardParameters;
	}

	/**
	 * @return the caPublicKeys
	 */
	public String getCaPublicKeys() {
		return caPublicKeys;
	}

	/**
	 * @param caPublicKeys the caPublicKeys to set
	 */
	public void setCaPublicKeys(String caPublicKeys) {
		this.caPublicKeys = caPublicKeys;
	}

	/**
	 * @return the actionCodeParameter
	 */
	public String getActionCodeParameter() {
		return actionCodeParameter;
	}

	/**
	 * @param actionCodeParameter the actionCodeParameter to set
	 */
	public void setActionCodeParameter(String actionCodeParameter) {
		this.actionCodeParameter = actionCodeParameter;
	}

	/**
	 * @return the stanNumber
	 */
	public String getStanNumber() {
		return stanNumber;
	}

	/**
	 * @param stanNumber the stanNumber to set
	 */
	public void setStanNumber(String stanNumber) {
		this.stanNumber = stanNumber;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the tipPercentage
	 */
	public String getTipPercentage() {
		return tipPercentage;
	}

	/**
	 * @param tipPercentage the tipPercentage to set
	 */
	public void setTipPercentage(String tipPercentage) {
		this.tipPercentage = tipPercentage;
	}

	/**
	 * @return the adjustAllowed
	 */
	public Boolean getAdjustAllowed() {
		return adjustAllowed;
	}

	/**
	 * @param adjustAllowed the adjustAllowed to set
	 */
	public void setAdjustAllowed(Boolean adjustAllowed) {
		this.adjustAllowed = adjustAllowed;
	}

	/**
	 * @return the refundAllowed
	 */
	public Boolean getRefundAllowed() {
		return refundAllowed;
	}

	/**
	 * @param refundAllowed the refundAllowed to set
	 */
	public void setRefundAllowed(Boolean refundAllowed) {
		this.refundAllowed = refundAllowed;
	}

	/**
	 * @return the preauthAllowed
	 */
	public Boolean getPreauthAllowed() {
		return preauthAllowed;
	}

	/**
	 * @param preauthAllowed the preauthAllowed to set
	 */
	public void setPreauthAllowed(Boolean preauthAllowed) {
		this.preauthAllowed = preauthAllowed;
	}

	/**
	 * @return the tipAllowed
	 */
	public Boolean getTipAllowed() {
		return tipAllowed;
	}

	/**
	 * @param tipAllowed the tipAllowed to set
	 */
	public void setTipAllowed(Boolean tipAllowed) {
		this.tipAllowed = tipAllowed;
	}

	/**
	 * @return the terminalConfigurationProfile
	 */
	public String getTerminalConfigurationProfile() {
		return terminalConfigurationProfile;
	}

	/**
	 * @param terminalConfigurationProfile the terminalConfigurationProfile to set
	 */
	public void setTerminalConfigurationProfile(String terminalConfigurationProfile) {
		this.terminalConfigurationProfile = terminalConfigurationProfile;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
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
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param updatedDate the updatedDate to set
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
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the parameterProfile
	 */
	public PGParameterProfile getParameterProfile() {
		return parameterProfile;
	}

	/**
	 * @param parameterProfile the parameterProfile to set
	 */
	public void setParameterProfile(PGParameterProfile parameterProfile) {
		this.parameterProfile = parameterProfile;
	}


}

