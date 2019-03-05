package com.chatak.pg.model;

import java.util.List;

import com.chatak.pg.bean.Response;

public class AjaxResponseCAPublicKeys extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5739768060019160249L;

	private String deviceManagementId;
    
	private String deviceSerialNo;
	
	private String merchantId;
	
	private String terminalId;
	
	private List<CAPublicKeysDTO> existingCAPublicKeys;
	
	private List<CAPublicKeysDTO> availableCAPublicKeys;

	/**
	 * @return the deviceManagementId
	 */
	public String getDeviceManagementId() {
		return deviceManagementId;
	}

	/**
	 * @param deviceManagementId the deviceManagementId to set
	 */
	public void setDeviceManagementId(String deviceManagementId) {
		this.deviceManagementId = deviceManagementId;
	}


	/**
	 * @return the deviceSerialNo
	 */
	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	/**
	 * @param deviceSerialNo the deviceSerialNo to set
	 */
	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
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

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the existingCAPublicKeys
	 */
	public List<CAPublicKeysDTO> getExistingCAPublicKeys() {
		return existingCAPublicKeys;
	}

	/**
	 * @param existingCAPublicKeys the existingCAPublicKeys to set
	 */
	public void setExistingCAPublicKeys(List<CAPublicKeysDTO> existingCAPublicKeys) {
		this.existingCAPublicKeys = existingCAPublicKeys;
	}

	/**
	 * @return the availableCAPublicKeys
	 */
	public List<CAPublicKeysDTO> getAvailableCAPublicKeys() {
		return availableCAPublicKeys;
	}

	/**
	 * @param availableCAPublicKeys the availableCAPublicKeys to set
	 */
	public void setAvailableCAPublicKeys(List<CAPublicKeysDTO> availableCAPublicKeys) {
		this.availableCAPublicKeys = availableCAPublicKeys;
	}
	
	
}
