package com.chatak.pay.controller.model.topup;

import java.io.Serializable;

public class TopupCategoryDTO implements Serializable {
	
	private Long categoryID;

	private Long operatorID;

	private String categoryName;

	public Long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	public Long getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(Long operatorID) {
		this.operatorID = operatorID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
