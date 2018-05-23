package com.chatak.pg.bean;

public class PaymentSchemeNameResponse extends Response {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String paymentSchemeName;

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
   * @return the paymentSchemeName
   */
  public String getPaymentSchemeName() {
    return paymentSchemeName;
  }

  /**
   * @param paymentSchemeName
   *          the paymentSchemeName to set
   */
  public void setPaymentSchemeName(String paymentSchemeName) {
    this.paymentSchemeName = paymentSchemeName;
  }

}
