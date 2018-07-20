package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.SearchRequest;

public class PosDeviceDTO extends SearchRequest implements Serializable{

	private static final long serialVersionUID = -1447470063098395382L;
	
	private Long id;
	
	private String deviceSerialNo;
	
	private String deviceType;
	
	private String deviceMake;
	
	private String deviceModel;
	
	private String status;
	
	private Integer deviceStatus;

  /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	public Integer getDeviceStatus() {
    return deviceStatus;
  }

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
   * @return the deviceType
   */
  public String getDeviceType() {
    return deviceType;
  }

	/**
	 * @return the deviceSerialNo
	 */
	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}
	
	public void setDeviceStatus(Integer deviceStatus) {
    this.deviceStatus = deviceStatus;
  }

	/**
	 * @param deviceSerialNo the deviceSerialNo to set
	 */
	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}

	/**
	 * @return the deviceMake
	 */
	public String getDeviceMake() {
		return deviceMake;
	}
	
	/**
   * @param deviceType the deviceType to set
   */
  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

	/**
	 * @param deviceMake the deviceMake to set
	 */
	public void setDeviceMake(String deviceMake) {
		this.deviceMake = deviceMake;
	}
	
	/**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}
	
	/**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

	/**
	 * @param deviceModel the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

}
