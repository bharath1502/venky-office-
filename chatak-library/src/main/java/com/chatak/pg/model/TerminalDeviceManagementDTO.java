package com.chatak.pg.model;

import java.util.Map;

import com.chatak.pg.bean.SearchRequest;

public class TerminalDeviceManagementDTO extends SearchRequest {
  /**
   * 
   */
  private static final long serialVersionUID = -2857041412634460693L;

  private Long deviceManagementId;

  private String merchantCode;

  private Long deviceId;

  private String deviceSerialNo;

  private String imeiNo;

  private String merchantId;

  private String terminalId;

  private Map<Long, String> availableApplicationId;

  private Map<Long, String> selectedApplicationId;

  private String applicationId;

  private Map<Long, String> availableMagneticStripeCardParameters;

  private Map<Long, String> selectedMagneticStripeCardParameters;

  private String magneticStripeCardParameters;

  private Map<Long, String> existingPublicKeys;

  private Map<Long, String> availablecaPublicKeys;

  private Map<Long, String> selectedcaPublicKeys;

  private String caPublicKeys;

  private Map<Long, String> availableActionCodeParameters;

  private Map<Long, String> selectedActionCodeParameters;

  private String actionCodeParameter;

  private String stanNumber;

  private String invoiceNumber;

  private String batchNumber;

  private String tipPercentage;

  private String allowed;

  private Boolean adjustAllowed;

  private Boolean refundAllowed;

  private Boolean preauthAllowed;

  private Boolean tipAllowed;

  private String status;

  private String keyStatus;

  private String keyName;

  private String rid;

  private String caKeyList;

  private String existingCaKeyList;

  private String remarks;

  private ParameterProfileDTO parameterProfileTo;

  private MerchantTerminalDTO merchantTerminalDTO;

  public Long getDeviceManagementId() {
    return deviceManagementId;
  }

  public void setDeviceManagementId(Long deviceManagementId) {
    this.deviceManagementId = deviceManagementId;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public Long getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(Long deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceSerialNo() {
    return deviceSerialNo;
  }

  public void setDeviceSerialNo(String deviceSerialNo) {
    this.deviceSerialNo = deviceSerialNo;
  }

  public String getImeiNo() {
    return imeiNo;
  }

  public void setImeiNo(String imeiNo) {
    this.imeiNo = imeiNo;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getTerminalId() {
    return terminalId;
  }

  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  public Map<Long, String> getAvailableApplicationId() {
    return availableApplicationId;
  }

  public void setAvailableApplicationId(Map<Long, String> availableApplicationId) {
    this.availableApplicationId = availableApplicationId;
  }

  public Map<Long, String> getSelectedApplicationId() {
    return selectedApplicationId;
  }

  public void setSelectedApplicationId(Map<Long, String> selectedApplicationId) {
    this.selectedApplicationId = selectedApplicationId;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public Map<Long, String> getAvailableMagneticStripeCardParameters() {
    return availableMagneticStripeCardParameters;
  }

  public void setAvailableMagneticStripeCardParameters(Map<Long, String> availableMagneticStripeCardParameters) {
    this.availableMagneticStripeCardParameters = availableMagneticStripeCardParameters;
  }

  public Map<Long, String> getSelectedMagneticStripeCardParameters() {
    return selectedMagneticStripeCardParameters;
  }

  public void setSelectedMagneticStripeCardParameters(Map<Long, String> selectedMagneticStripeCardParameters) {
    this.selectedMagneticStripeCardParameters = selectedMagneticStripeCardParameters;
  }

  public String getMagneticStripeCardParameters() {
    return magneticStripeCardParameters;
  }

  public void setMagneticStripeCardParameters(String magneticStripeCardParameters) {
    this.magneticStripeCardParameters = magneticStripeCardParameters;
  }

  public Map<Long, String> getExistingPublicKeys() {
    return existingPublicKeys;
  }

  public void setExistingPublicKeys(Map<Long, String> existingPublicKeys) {
    this.existingPublicKeys = existingPublicKeys;
  }

  public Map<Long, String> getAvailablecaPublicKeys() {
    return availablecaPublicKeys;
  }

  public void setAvailablecaPublicKeys(Map<Long, String> availablecaPublicKeys) {
    this.availablecaPublicKeys = availablecaPublicKeys;
  }

  public Map<Long, String> getSelectedcaPublicKeys() {
    return selectedcaPublicKeys;
  }

  public void setSelectedcaPublicKeys(Map<Long, String> selectedcaPublicKeys) {
    this.selectedcaPublicKeys = selectedcaPublicKeys;
  }

  public String getCaPublicKeys() {
    return caPublicKeys;
  }

  public void setCaPublicKeys(String caPublicKeys) {
    this.caPublicKeys = caPublicKeys;
  }

  public Map<Long, String> getAvailableActionCodeParameters() {
    return availableActionCodeParameters;
  }

  public void setAvailableActionCodeParameters(Map<Long, String> availableActionCodeParameters) {
    this.availableActionCodeParameters = availableActionCodeParameters;
  }

  public Map<Long, String> getSelectedActionCodeParameters() {
    return selectedActionCodeParameters;
  }

  public void setSelectedActionCodeParameters(Map<Long, String> selectedActionCodeParameters) {
    this.selectedActionCodeParameters = selectedActionCodeParameters;
  }

  public String getActionCodeParameter() {
    return actionCodeParameter;
  }
  
  public String getStanNumber() {
    return stanNumber;
  }

  public void setActionCodeParameter(String actionCodeParameter) {
    this.actionCodeParameter = actionCodeParameter;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public void setStanNumber(String stanNumber) {
    this.stanNumber = stanNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public String getBatchNumber() {
    return batchNumber;
  }
  
  public String getTipPercentage() {
    return tipPercentage;
  }

  public void setBatchNumber(String batchNumber) {
    this.batchNumber = batchNumber;
  }

  public void setTipPercentage(String tipPercentage) {
    this.tipPercentage = tipPercentage;
  }

  public String getAllowed() {
    return allowed;
  }
  
  public Boolean getAdjustAllowed() {
    return adjustAllowed;
  }

  public void setAllowed(String allowed) {
    this.allowed = allowed;
  }

  public Boolean getRefundAllowed() {
    return refundAllowed;
  }
  
  public void setAdjustAllowed(Boolean adjustAllowed) {
    this.adjustAllowed = adjustAllowed;
  }

  public void setRefundAllowed(Boolean refundAllowed) {
    this.refundAllowed = refundAllowed;
  }
  
  public Boolean getTipAllowed() {
    return tipAllowed;
  }

  public Boolean getPreauthAllowed() {
    return preauthAllowed;
  }
  
  public void setTipAllowed(Boolean tipAllowed) {
    this.tipAllowed = tipAllowed;
  }

  public void setPreauthAllowed(Boolean preauthAllowed) {
    this.preauthAllowed = preauthAllowed;
  }

  public String getStatus() {
    return status;
  }
  
  public String getKeyStatus() {
    return keyStatus;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getKeyName() {
    return keyName;
  }
  
  public void setKeyStatus(String keyStatus) {
    this.keyStatus = keyStatus;
  }

  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  public String getRid() {
    return rid;
  }

  public void setRid(String rid) {
    this.rid = rid;
  }

  public String getCaKeyList() {
    return caKeyList;
  }

  public void setCaKeyList(String caKeyList) {
    this.caKeyList = caKeyList;
  }

  public String getExistingCaKeyList() {
    return existingCaKeyList;
  }

  public void setExistingCaKeyList(String existingCaKeyList) {
    this.existingCaKeyList = existingCaKeyList;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public ParameterProfileDTO getParameterProfileTo() {
    return parameterProfileTo;
  }

  public void setParameterProfileTo(ParameterProfileDTO parameterProfileTo) {
    this.parameterProfileTo = parameterProfileTo;
  }

  public MerchantTerminalDTO getMerchantTerminalDTO() {
    return merchantTerminalDTO;
  }

  public void setMerchantTerminalDTO(MerchantTerminalDTO merchantTerminalDTO) {
    this.merchantTerminalDTO = merchantTerminalDTO;
  }

}
