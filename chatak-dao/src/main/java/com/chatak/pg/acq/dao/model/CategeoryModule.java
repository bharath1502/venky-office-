package com.chatak.pg.acq.dao.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGEORY_MODULE")
public class CategeoryModule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MODULE_ID")
	private Long moduleId;

	@Column(name = "MODULE_NAME")
	private String moduleName;

	@Column(name = "CATEGEORY_ID")
	private Long categeoryId;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getCategeoryId() {
		return categeoryId;
	}

	public void setCategeoryId(Long categeoryId) {
		this.categeoryId = categeoryId;
	}

}