package com.chatak.pg.user.bean;


public class GetTerminalListRequest extends SearchRequest{

	/**
   * 
   */
  private static final long serialVersionUID = -4478700682359829328L;


	
	private Long terminalId;
	
	private Integer status;

  /**
   * @return the terminalId
   */
  public Long getTerminalId() {
    return terminalId;
  }

  /**
   * @param terminalId the terminalId to set
   */
  public void setTerminalId(Long terminalId) {
    this.terminalId = terminalId;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }


}
