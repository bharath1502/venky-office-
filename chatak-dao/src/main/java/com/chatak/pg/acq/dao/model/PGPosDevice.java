package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chatak.pg.bean.SearchRequest;

@Entity
@Table(name = "PG_POS_DEVICE")
public class PGPosDevice extends SearchRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_POS_DEVICE_ID", sequenceName = "SEQ_PG_POS_DEVICE")
	@GeneratedValue(generator = "SEQ_PG_POS_DEVICE_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DEVICE_SERIAL_NO")
	private String deviceSerialNo;
	
	@Column(name = "DEVICE_TYPE")
	private String deviceType;
	
	@Column(name = "DEVICE_MAKE")
	private String deviceMake;
	
	@Column(name = "DEVICE_MODEL")
	private String deviceModel;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "STATUS")
	private String status;

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
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceMake
	 */
	public String getDeviceMake() {
		return deviceMake;
	}

	/**
	 * @param deviceMake the deviceMake to set
	 */
	public void setDeviceMake(String deviceMake) {
		this.deviceMake = deviceMake;
	}

	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}

	/**
	 * @param deviceModel the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
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


}
