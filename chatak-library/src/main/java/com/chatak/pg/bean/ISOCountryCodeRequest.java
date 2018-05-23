package com.chatak.pg.bean;

public class ISOCountryCodeRequest extends SearchRequest {

  /**
   * 
   */
  private static final long serialVersionUID = -1682272913321910951L;
  
  private Long id;
  
  private String code;
  
  private String name;

  public Long getId() {
    return id;
  }
  
  public String getCode() {
    return code;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }
  
  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }
  
}
