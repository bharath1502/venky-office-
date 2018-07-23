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
@Table(name = "PG_SWITCH")
public class PGSwitch implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3009936752059340755L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_SWITCH_ID", sequenceName = "SEQ_PG_SWITCH")
  @GeneratedValue(generator = "SEQ_PG_SWITCH_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
	
  @Column(name = "SWITCH_NAME")
  private String switchName;
  
  @Column(name = "SWITCH_TYPE")
  private String switchType;
  
  @Column(name="PRIMARY_SWITCH_URL")
  private String primarySwitchURL;
  
  @Column(name="PRIMARY_SWITCH_PORT")
  private Integer primarySwitchPort;
	
  @Column(name="SECONDARY_SWITCH_URL")
  private String secondarySwitchURL;
	  
  @Column(name="SECONDARY_SWITCH_PORT")
  private Integer secondarySwitchPort;
  
  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "PRIORITY")
  private Integer priority;
  
  @Column(name = "CREATED_BY")
  private Long createdBy;
  
  @Column(name = "UPDATED_BY")
  private Long updatedBy;
  
  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;
  
  @Column(name = "REASON")
  private String reason;

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
   * @return the switchName
   */
  public String getSwitchName() {
    return switchName;
  }

  /**
   * @param switchName the switchName to set
   */
  public void setSwitchName(String switchName) {
    this.switchName = switchName;
  }

  /**
   * @return the switchType
   */
  public String getSwitchType() {
    return switchType;
  }

  /**
   * @param switchType the switchType to set
   */
  public void setSwitchType(String switchType) {
    this.switchType = switchType;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  public String getPrimarySwitchURL() {
	return primarySwitchURL;
  }
  
  public void setSecondarySwitchPort(Integer secondarySwitchPort) {
		this.secondarySwitchPort = secondarySwitchPort;
	}

  public void setPrimarySwitchURL(String primarySwitchURL) {
	this.primarySwitchURL = primarySwitchURL;
  }

  public void setSecondarySwitchURL(String secondarySwitchURL) {
	this.secondarySwitchURL = secondarySwitchURL;
  }

  public void setPrimarySwitchPort(Integer primarySwitchPort) {
	this.primarySwitchPort = primarySwitchPort;
  }

  public String getSecondarySwitchURL() {
	return secondarySwitchURL;
  }

  public Integer getSecondarySwitchPort() {
	return secondarySwitchPort;
  }

  public Integer getPrimarySwitchPort() {
	return primarySwitchPort;
  }

/**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the priority
   */
  public Integer getPriority() {
    return priority;
  }

  /**
   * @param priority the priority to set
   */
  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  /**
   * @return the createdBy
   */
  public Long getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the updatedBy
   */
  public Long getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(Long updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getReason() {
	return reason;
  }

  public void setReason(String reason) {
	this.reason = reason;
  }  
  
}
