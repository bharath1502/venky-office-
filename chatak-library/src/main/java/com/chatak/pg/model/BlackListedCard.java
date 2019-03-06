package com.chatak.pg.model;

import java.math.BigInteger;

import com.chatak.pg.bean.SearchRequest;

public class BlackListedCard extends SearchRequest{
  
  /**
   * 
   */
  private static final long serialVersionUID = -347562230489408588L;

  private Long id;
  
  private BigInteger cardNumber;
  
  private Integer status;
  
  /**
   * @return the cardNumber
   */
  public BigInteger getCardNumber() {
    return cardNumber;
  }
  /**
   * @param cardNumber the cardNumber to set
   */
  public void setCardNumber(BigInteger cardNumber) {
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
  @Override
  public Long getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  @Override
  public void setId(Long id) {
    this.id = id;
  }
  
  
}
