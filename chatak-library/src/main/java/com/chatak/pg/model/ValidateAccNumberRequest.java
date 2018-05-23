package com.chatak.pg.model;

import java.io.Serializable;

public class ValidateAccNumberRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8974275079882622484L;
  
  private String specificAccountNumber;

  /**
   * @return the specificAccountNumber
   */
  public String getSpecificAccountNumber() {
    return specificAccountNumber;
  }

  /**
   * @param specificAccountNumber the specificAccountNumber to set
   */
  public void setSpecificAccountNumber(String specificAccountNumber) {
    this.specificAccountNumber = specificAccountNumber;
  }
  

}
