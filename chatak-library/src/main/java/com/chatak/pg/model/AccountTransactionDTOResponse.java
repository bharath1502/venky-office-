package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

public class AccountTransactionDTOResponse extends Response implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private List<AccountTransactionDTO> accountTransactionDTO;
  
  private Integer totalResultCount;

  public List<AccountTransactionDTO> getAccountTransactionDTO() {
    return accountTransactionDTO;
  }

  public void setAccountTransactionDTO(List<AccountTransactionDTO> accountTransactionDTO) {
    this.accountTransactionDTO = accountTransactionDTO;
  }

  public Integer getTotalResultCount() {
    return totalResultCount;
  }

  public void setTotalResultCount(Integer totalResultCount) {
    this.totalResultCount = totalResultCount;
  }

}
