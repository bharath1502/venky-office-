package com.chatak.pg.bean;

public class PublickeyNameResponse extends Response {

  private static final long serialVersionUID = 1L;

  private Long publicKeyId;

  private String publicKeyName;

  /**
   * @return the publicKeyId
   */
  public Long getPublicKeyId() {
    return publicKeyId;
  }

  /**
   * @param publicKeyId
   *          the publicKeyId to set
   */
  public void setPublicKeyId(Long publicKeyId) {
    this.publicKeyId = publicKeyId;
  }

  /**
   * @return the publicKeyName
   */
  public String getPublicKeyName() {
    return publicKeyName;
  }

  /**
   * @param publicKeyName
   *          the publicKeyName to set
   */
  public void setPublicKeyName(String publicKeyName) {
    this.publicKeyName = publicKeyName;
  }

}
