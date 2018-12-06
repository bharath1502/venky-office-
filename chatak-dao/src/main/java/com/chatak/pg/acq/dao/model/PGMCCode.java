/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2016
 * @Time: 2:56:43 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_MCC_CODE")
public class PGMCCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MCC_CODE")
	private String merchantCategoryCode;

	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the merchantCategoryCode
	 */
	public String getMerchantCategoryCode() {
		return merchantCategoryCode;
	}

	/**
	 * @param merchantCategoryCode
	 *            the merchantCategoryCode to set
	 */
	public void setMerchantCategoryCode(String merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
