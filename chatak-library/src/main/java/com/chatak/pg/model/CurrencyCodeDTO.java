package com.chatak.pg.model;

import java.io.Serializable;



public class CurrencyCodeDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1010198443917442485L;

	
	
	private Long countryId;
	
	private Long id;
	
	private String code;
	
	private String createdBy;
	
	private String createdDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
   * @return the countryId
   */
  public Long getCountryId() {
    return countryId;
  }

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
   * @return the createdDate
   */
  public String getCreatedDate() {
    return createdDate;
  }

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
	
}
