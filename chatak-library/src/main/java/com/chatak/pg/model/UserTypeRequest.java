package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

/**
 * This class represents the UserType request
 * 
 * @Author: Girmiti Software
 * @Date: Nov 27, 2014
 * @Time: 11:00:00
 * @Version: 1.0
 * @Comments:
 * 
 */
public class UserTypeRequest extends SearchRequest {

	private static final long serialVersionUID = 1611601139219257970L;

	private String shortName;

	private String longName;

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("UserTypeRequest{");
		sb.append("[shortName:" + shortName + "],");
		sb.append("[longName:" + longName + "]");

		sb.append("}");
		return sb.toString();
	}
}
