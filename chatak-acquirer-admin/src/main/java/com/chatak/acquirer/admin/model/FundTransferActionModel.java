package com.chatak.acquirer.admin.model;

import java.io.Serializable;

public class FundTransferActionModel implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -5791242075809808295L;

  private String action;

  private Long pgTransfersId;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Long getPgTransfersId() {
    return pgTransfersId;
  }

  public void setPgTransfersId(Long pgTransfersId) {
    this.pgTransfersId = pgTransfersId;
  }
  
}
