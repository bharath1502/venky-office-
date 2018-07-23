package com.chatak.pg.model;

public class DashBoardRecords {
	
	private Long pendingCount;
	private Long canceledCount;
	private Long processingCount;
	private Long executedCount;
	private Long rejectedCount;
	private Long total;
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
	/**
	 * @return the canceledCount
	 */
	public Long getCanceledCount() {
		return canceledCount;
	}
	/**
	 * @param canceledCount the canceledCount to set
	 */
	public void setCanceledCount(Long canceledCount) {
		this.canceledCount = canceledCount;
	}
	/**
	 * @return the processingCount
	 */
	public Long getProcessingCount() {
		return processingCount;
	}
	/**
	 * @param processingCount the processingCount to set
	 */
	public void setProcessingCount(Long processingCount) {
		this.processingCount = processingCount;
	}
	/**
	 * @return the executedCount
	 */
	public Long getExecutedCount() {
		return executedCount;
	}
	/**
	 * @param executedCount the executedCount to set
	 */
	public void setExecutedCount(Long executedCount) {
		this.executedCount = executedCount;
	}
	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
  /**
   * @return the rejectedCount
   */
  public Long getRejectedCount() {
    return rejectedCount;
  }
  /**
   * @param rejectedCount the rejectedCount to set
   */
  public void setRejectedCount(Long rejectedCount) {
    this.rejectedCount = rejectedCount;
  }
	

}
