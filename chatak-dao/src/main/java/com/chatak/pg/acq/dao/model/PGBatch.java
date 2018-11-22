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
 * @Date: May 28, 2018
 * @Time: 9:39:43 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_BATCH")
public class PGBatch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ID")
	  private Long id;
	
	  @Column(name = "PM_ID")
	  private Long programManagerId;
	  
	  @Column(name = "BATCH_ID")
	  private String batchId;
	  
	  @Column(name = "STATUS")
	  private String status;
	  
	  @Column(name = "CREATED_DATE")
	  private Timestamp createdDate;

	  @Column(name = "UPDATED_DATE")
	  private Timestamp updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProgramManagerId() {
		return programManagerId;
	}

	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}
