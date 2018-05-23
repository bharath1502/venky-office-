package com.chatak.pg.model;

import java.io.Serializable;

public class VirtualAccFeeReversalRequest implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -1781778301509587770L;
  private String ciVirtualAccTxnId;

  public String getCiVirtualAccTxnId() {
    return ciVirtualAccTxnId;
  }

  public void setCiVirtualAccTxnId(String ciVirtualAccTxnId) {
    this.ciVirtualAccTxnId = ciVirtualAccTxnId;
  }

}
