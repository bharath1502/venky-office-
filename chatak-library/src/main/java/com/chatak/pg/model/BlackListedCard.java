package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class BlackListedCard extends SearchRequest{
  
  /**
   * 
   */
  private static final long serialVersionUID = -347562230489408588L;

  private Long id;
  
  private Long cardNumber;
  
  private Integer status;
  
  /**
   * @return the cardNumber
   */
  public Long getCardNumber() {
    return cardNumber;
  }
  /**
   * @param cardNumber the cardNumber to set
   */
  public void setCardNumber(Long cardNumber) {
    this.cardNumber = cardNumber;
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
  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  
}
