/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Jan 31, 2019
 * @Time: 2:48:46 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PAN_RANGE")
public class PanRanges implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7097943514375179218L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PROGRAM_MANAGER_ID")
	private Long programManagerId;
	
	@Column(name = "PAN_LOW")
	private Long panLow;
	
	@Column(name = "PAN_HIGH")
	private Long panHigh;

	/**
	 * @return the programManagerId
	 */
	public Long getProgramManagerId() {
		return programManagerId;
	}

	/**
	 * @param programManagerId the programManagerId to set
	 */
	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	/**
	 * @return the panLow
	 */
	public Long getPanLow() {
		return panLow;
	}

	/**
	 * @param panLow the panLow to set
	 */
	public void setPanLow(Long panLow) {
		this.panLow = panLow;
	}

	/**
	 * @return the panHigh
	 */
	public Long getPanHigh() {
		return panHigh;
	}

	/**
	 * @param panHigh the panHigh to set
	 */
	public void setPanHigh(Long panHigh) {
		this.panHigh = panHigh;
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
}
