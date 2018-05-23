package com.chatak.pg.model;

import java.io.Serializable;

public class ProcessingFee implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4216850136194565315L;

  private Double chatakProcessingFee;

  private String accountNumber;

  /**
   * @return the chatakProcessingFee
   */
  public Double getChatakProcessingFee() {
    return chatakProcessingFee;
  }

  /**
   * @param chatakProcessingFee
   *          the chatakProcessingFee to set
   */
  public void setChatakProcessingFee(Double chatakProcessingFee) {
    this.chatakProcessingFee = chatakProcessingFee;
  }

  /**
   * @return the accountNumber
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * @param accountNumber
   *          the accountNumber to set
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

}
