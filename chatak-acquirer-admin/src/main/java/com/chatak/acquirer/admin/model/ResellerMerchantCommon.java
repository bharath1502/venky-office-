package com.chatak.acquirer.admin.model;

public class ResellerMerchantCommon  {

  private String address1;

  private String address2;

  private String city;

  private String state;

  private String country;

  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }
  
  /**
   * @param address2
   *            the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param address1
   *            the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }
  
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * @param state
   *            the state to set
   */
  public void setState(String state) {
    this.state = state;
  }
  
  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city
   *            the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country
   *            the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }


}
