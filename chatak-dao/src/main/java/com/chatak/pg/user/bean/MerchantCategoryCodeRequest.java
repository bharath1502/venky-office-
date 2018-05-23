/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2016
 * @Time: 2:15:33 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class MerchantCategoryCodeRequest extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 674693090192007789L;

	private Long id;

	private String merchantCategoryCode;

	private String selectedTcc;

	private List<String> tccMultiple;

	private String description;

	private String status;

	private String updatedBy;

	private String createdDate;

	private String updatedDate;

	private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCategoryCode() {
		return merchantCategoryCode;
	}

	public void setMerchantCategoryCode(String merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

	public String getSelectedTcc() {
		return selectedTcc;
	}

	public void setSelectedTcc(String selectedTcc) {
		this.selectedTcc = selectedTcc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public String getStatus() {
		return status;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	
	public String getDescription() {
		return description;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public void setTccMultiple(List<String> tccMultiple) {
		this.tccMultiple = tccMultiple;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}
	
	public List<String> getTccMultiple() {
		return tccMultiple;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}
