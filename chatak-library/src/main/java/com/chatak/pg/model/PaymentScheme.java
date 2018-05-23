package com.chatak.pg.model;

import java.io.Serializable;

public class PaymentScheme implements Serializable {
 
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long id;
 
  private String paymentSchemeName;
  
  private String contactName;
  
  private String contactEmail;
  
  private String contactPhone;
  
  private String rid;
  
  private Integer status;
  
  private String typeOfCard;
  
  private Integer recordsPerPage;

  public String getPaymentSchemeName() {
    return paymentSchemeName;
  }
  
  public Integer getRecordsPerPage() {
    return recordsPerPage;
  }

  public void setPaymentSchemeName(String paymentSchemeName) {
    this.paymentSchemeName = paymentSchemeName;
  }
  
  public void setRecordsPerPage(Integer recordsPerPage) {
    this.recordsPerPage = recordsPerPage;
  }

  public String getContactName() {
    return contactName;
  }
  
  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }
  
  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }
  
  public Integer getStatus() {
    return status;
  }

  public String getRid() {
    return rid;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setRid(String rid) {
    this.rid = rid;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public String getTypeOfCard() {
    return typeOfCard;
  }
  
  public Long getId() {
    return id;
  }

  public void setTypeOfCard(String typeOfCard) {
    this.typeOfCard = typeOfCard;
  }

}
