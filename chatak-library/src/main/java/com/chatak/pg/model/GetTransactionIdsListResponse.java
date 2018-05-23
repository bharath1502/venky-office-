package com.chatak.pg.model;

import java.util.List;

public class GetTransactionIdsListResponse extends Response {
  
  /**
   * 
   */
  private static final long serialVersionUID = -5696822519358135329L;
  private List<EFTRefTxnData> transactionIdsList;

  public List<EFTRefTxnData> getTransactionIdsList() {
    return transactionIdsList;
  }

  public void setTransactionIdsList(List<EFTRefTxnData> transactionIdsList) {
    this.transactionIdsList = transactionIdsList;
  }
  

}
