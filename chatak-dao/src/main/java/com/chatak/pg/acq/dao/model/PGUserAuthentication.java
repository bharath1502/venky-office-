package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_USER_AUTHENTICATION")
public class PGUserAuthentication implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_USER_AUTHENTICATION_ID", sequenceName = "SEQ_PG_USER_AUTHENTICATION")
	@GeneratedValue(generator = "SEQ_PG_USER_AUTHENTICATION_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PROFILE_ID")
	private Long profileId;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "TOKEN_EXP_TIME")
	private Timestamp tokenExpTime;
	
	@Column(name = "CLIENT_IP")
	private String clientIP;

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
	 * @return the profileId
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tokenExpTime
	 */
	public Timestamp getTokenExpTime() {
		return tokenExpTime;
	}

	/**
	 * @param tokenExpTime the tokenExpTime to set
	 */
	public void setTokenExpTime(Timestamp tokenExpTime) {
		this.tokenExpTime = tokenExpTime;
	}

	/**
	 * @return the clientIP
	 */
	public String getClientIP() {
		return clientIP;
	}

	/**
	 * @param clientIP the clientIP to set
	 */
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	
	
	
	
	

}
