package com.chatak.pg.model;

import java.io.Serializable;

public class TransferRequestsCount implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 5810802608150163923L;

  private Long pendingCount;

  private Long canceledCount;

  private Long processingCount;

  private Long executedCount;

  private Long total;

  public Long getPendingCount() {
    return pendingCount;
  }
  
  public Long getCanceledCount() {
    return canceledCount;
  }

  public void setPendingCount(Long pendingCount) {
    this.pendingCount = pendingCount;
  }

  public void setCanceledCount(Long canceledCount) {
    this.canceledCount = canceledCount;
  }

  public Long getProcessingCount() {
    return processingCount;
  }
  
  public void setExecutedCount(Long executedCount) {
    this.executedCount = executedCount;
  }

  public void setProcessingCount(Long processingCount) {
    this.processingCount = processingCount;
  }

  public Long getTotal() {
    return total;
  }
  
  public Long getExecutedCount() {
    return executedCount;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

}
