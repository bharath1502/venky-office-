package com.chatak.pg.model;

import java.util.List;

public class MerchantCategoryCode {
  private Long id;

  private String merchantCategoryCode;

  private String selectedTcc;

  private List<String> tccMultiple;

  private String description;

  private String status;

  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;

  private String createdBy;

  private String updatedBy;

  private String createdDate;

  private String updatedDate;

  private List<String> selectedTCCMultiple;
  
  private String reason;

  public String getMerchantCategoryCode() {
    return merchantCategoryCode;
  }
  
  public Long getId() {
    return id;
  }

  public void setMerchantCategoryCode(String merchantCategoryCode) {
    this.merchantCategoryCode = merchantCategoryCode;
  }
  
  public List<String> getTccMultiple() {
    return tccMultiple;
  }

  public String getSelectedTcc() {
    return selectedTcc;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public void setSelectedTcc(String selectedTcc) {
    this.selectedTcc = selectedTcc;
  }

  public String getDescription() {
    return description;
  }
  
  public void setTccMultiple(List<String> tccMultiple) {
    this.tccMultiple = tccMultiple;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }
  
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  public Integer getPageSize() {
    return pageSize;
  }
  
  public String getStatus() {
    return status;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getCreatedBy() {
    return createdBy;
  }
  
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public String getUpdatedDate() {
    return updatedDate;
  }
  
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  public List<String> getSelectedTCCMultiple() {
    return selectedTCCMultiple;
  }

  public void setSelectedTCCMultiple(List<String> selectedTCCMultiple) {
    this.selectedTCCMultiple = selectedTCCMultiple;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

}
