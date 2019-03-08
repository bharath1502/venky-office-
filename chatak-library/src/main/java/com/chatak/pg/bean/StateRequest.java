package com.chatak.pg.bean;

/**
 * This class represents the State request
 * 
 * @Author: Girmiti Software
 * @Date: Nov 27, 2014
 * @Time: 11:00:00
 * @Version: 1.0
 * @Comments:
 * 
 */
public class StateRequest extends SearchRequest {

	private static final long serialVersionUID = 8189553325914063329L;

	private Long id;
	
	private Long countryId;

	private String name;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("StateRequest{");
		sb.append("[id:" + id + "],");
		sb.append("[countryId:" + countryId + "],");
		sb.append("[name:" + name + "]");
		sb.append("}");
		return sb.toString();
	}
}
