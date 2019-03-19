package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY_MODULE_MAPPING")
public class CategoryModuleMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CATEGORY_MAPPING_ID")
	private Long categoryMappingId;

	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	@Column(name = "MODULE_ID")
	private Long moduleId;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getCatgeoryId() {
		return categoryId;
	}

	public void setCatgoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryMappingId() {
		return categoryMappingId;
	}

	public void setCategeoryMappingId(Long categoryMappingId) {
		this.categoryMappingId = categoryMappingId;
	}

}