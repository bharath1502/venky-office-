/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 5:19:02 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name="DEVICE")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -383227556134676041L;

	@Id
	/*@SequenceGenerator(name = "SEQ_DEVICE", sequenceName = "SEQ_DEVICE")
	@GeneratedValue(generator = "SEQ_DEVICE")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEVICE_ID")
	private Long id;

	@Column(name = "DEVICE_TYPE")
	private String deviceType;

	@Column(name = "DEVICE_MAKE")
	private String deviceMake;
	
	@Column(name = "DEVICE_MODEL")
	private String deviceModel;

	@Column(name = "STATUS")
	private Integer deviceStatus;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceMake() {
		return deviceMake;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setDeviceMake(String deviceMake) {
		this.deviceMake = deviceMake;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
