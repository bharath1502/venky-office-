package com.chatak.pg.model;

import java.sql.Timestamp;

public class Switch {
  
  private Long id;

  private String switchName;
  
  private String switchType;
  
  private String primarySwitchURL;
  
  private Integer primarySwitchPort;
  
  private String secondarySwitchURL;
  
  private Integer secondarySwitchPort;
  
  private Integer priority;
  
  private Integer status;
  
  private String createdBy;
  
  private Timestamp createdDate;
  
  private Timestamp updatedDate;
  
  private String updatedBy;
  
  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;

  public Long getId() {
    return id;
  }
  
  public String getSwitchType() {
    return switchType;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public String getPrimarySwitchURL() {
    return primarySwitchURL;
  }
  
  public String getSwitchName() {
    return switchName;
  }
  
  public void setSwitchType(String switchType) {
    this.switchType = switchType;
  }

  public void setSwitchName(String switchName) {
    this.switchName = switchName;
  }

  public Integer getPrimarySwitchPort() {
    return primarySwitchPort;
  }
  
  public void setPrimarySwitchURL(String primarySwitchURL) {
    this.primarySwitchURL = primarySwitchURL;
  }

  public void setPrimarySwitchPort(Integer primarySwitchPort) {
    this.primarySwitchPort = primarySwitchPort;
  }

  public Integer getSecondarySwitchPort() {
    return secondarySwitchPort;
  }
  
  public String getSecondarySwitchURL() {
    return secondarySwitchURL;
  }

  public void setSecondarySwitchPort(Integer secondarySwitchPort) {
    this.secondarySwitchPort = secondarySwitchPort;
  }
  
  public Integer getStatus() {
    return status;
  }

  public Integer getPriority() {
    return priority;
  }
  
  public void setSecondarySwitchURL(String secondarySwitchURL) {
    this.secondarySwitchURL = secondarySwitchURL;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }
  
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }
  
  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
  
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  public Integer getPageSize() {
    return pageSize;
  }
  
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }
  
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }

}
