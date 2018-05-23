package com.chatak.acquirer.admin.model;

import java.io.Serializable;
import java.util.List;

public class FundTransferActionListModel implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -5791242075809808295L;

  private String action;
  private List<FundTransferActionModel> pgTransfersIds;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  /**
   * @return the pgTransfersIds
   */
  public List<FundTransferActionModel> getPgTransfersIds() {
    return pgTransfersIds;
  }

  /**
   * @param pgTransfersIds the pgTransfersIds to set
   */
  public void setPgTransfersIds(List<FundTransferActionModel> pgTransfersIds) {
    this.pgTransfersIds = pgTransfersIds;
  }



}
