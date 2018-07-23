package com.chatak.pg.model;

import java.sql.Timestamp;
import java.util.List;

import com.chatak.pg.bean.SearchRequest;

@SuppressWarnings("rawtypes")
public class CAPublicKeysDTO extends SearchRequest {

  private static final long serialVersionUID = -6821604525930975678L;

  private List pgCapublicKeysList;

  private Long publicKeyId;

  private String publicKeyName;

  private String rid;

  private String publicKeyModulus;

  private Long publicKeyExponent;

  private String expiryDate;

  private String publicKeyIndex;

  private Timestamp createDate;

  private String status;
  
  private String createdBy;
  

  public String getRid() {
    return rid;
  }

  public void setRid(String rid) {
    this.rid = rid;
  }

  public Long getPublicKeyId() {
    return publicKeyId;
  }
  
  public String getPublicKeyName() {
    return publicKeyName;
  }

  public void setPublicKeyId(Long publicKeyId) {
    this.publicKeyId = publicKeyId;
  }

  public void setPublicKeyName(String publicKeyName) {
    this.publicKeyName = publicKeyName;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  
  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

 
  public String getPublicKeyModulus() {
    return publicKeyModulus;
  }

  public void setPublicKeyModulus(String publicKeyModulus) {
    this.publicKeyModulus = publicKeyModulus;
  }

  public Long getPublicKeyExponent() {
    return publicKeyExponent;
  }

  public void setPublicKeyExponent(Long publicKeyExponent) {
    this.publicKeyExponent = publicKeyExponent;
  }

  public String getPublicKeyIndex() {
    return publicKeyIndex;
  }

  public void setPublicKeyIndex(String publicKeyIndex) {
    this.publicKeyIndex = publicKeyIndex;
  }

  public List getPgCapublicKeysList() {
    return pgCapublicKeysList;
  }

  public void setPgCapublicKeysList(List pgCapublicKeysList) {
    this.pgCapublicKeysList = pgCapublicKeysList;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  
  

}
