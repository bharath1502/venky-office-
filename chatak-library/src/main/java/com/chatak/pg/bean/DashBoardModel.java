package com.chatak.pg.bean;

import java.io.Serializable;

public class DashBoardModel implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1919327580697895995L;

  private Long processingCount;

  private Long executedCount;

  private Long cancelledCount;

  private String requestType;
  
  private Long pendingCount;

  public Long getProcessingCount() {
    return processingCount;
  }

  public void setProcessingCount(Long processingCount) {
    this.processingCount = processingCount;
  }

  public Long getExecutedCount() {
    return executedCount;
  }

  public void setExecutedCount(Long executedCount) {
    this.executedCount = executedCount;
  }

  public Long getCancelledCount() {
    return cancelledCount;
  }

  public void setCancelledCount(Long cancelledCount) {
    this.cancelledCount = cancelledCount;
  }

  public String getRequestType() {
    return requestType;
  }

  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }

  /**
   * @return the pendingCount
   */
  public Long getPendingCount() {
    return pendingCount;
  }

  /**
   * @param pendingCount the pendingCount to set
   */
  public void setPendingCount(Long pendingCount) {
    this.pendingCount = pendingCount;
  }

}
