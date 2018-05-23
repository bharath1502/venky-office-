package com.chatak.pg.bean;

public class TransactionCategoryCodeRequest extends SearchRequest {
  /**
   * 
   */
  private static final long serialVersionUID = -8359132510188344092L;

  private Long id;

  private String transactionCategoryCode;

  private String description;

  public Long getId() {
    return id;
  }
  
  public String getTransactionCategoryCode() {
    return transactionCategoryCode;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }
  
  public void setTransactionCategoryCode(String transactionCategoryCode) {
    this.transactionCategoryCode = transactionCategoryCode;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
