package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_MPOS_SESSION_KEY")
public class MPosSessionKey  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id; 
	
	@Column(name = "Device_Serial")
	private String deviceSerial;
	
	@Column(name = "Device_SK")
	private String deviceSk;
	
	public String getDeviceSerail() {
		return deviceSerial;
	}

	public void setDeviceSerail(String deviceSerail) {
		this.deviceSerial = deviceSerail;
	}

	public String getDeviceSk() {
		return deviceSk;
	}

	public void setDeviceSk(String deviceSk) {
		this.deviceSk = deviceSk;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
