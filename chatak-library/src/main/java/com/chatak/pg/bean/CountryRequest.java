package com.chatak.pg.bean;

/**
 * This class represents the Country request
 * 
 * @Author: Girmiti Software
 * @Date: Nov 27, 2014
 * @Time: 11:00:00
 * @Version: 1.0
 * @Comments:
 * 
 */
public class CountryRequest extends SearchRequest {

	private static final long serialVersionUID = 1611601139219257970L;

	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CountryRequest{");
		sb.append("[id:" + id + "],");
		sb.append("[name:" + name + "]");

		sb.append("}");
		return sb.toString();
	}
}
