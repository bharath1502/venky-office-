package com.chatak.pg.bean;

public class CardNumberResponse extends Response {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long cardNumber;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the cardNumber
   */
  public Long getCardNumber() {
    return cardNumber;
  }

  /**
   * @param cardNumber
   *          the cardNumber to set
   */
  public void setCardNumber(Long cardNumber) {
    this.cardNumber = cardNumber;
  }

}
