package com.chatak.pg.bean;

import java.util.List;

public class TimeZoneResponse extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<TimeZoneRequest> listOfTimeZoneRequests;

	public List<TimeZoneRequest> getListOfTimeZoneRequests() {
		return listOfTimeZoneRequests;
	}

	public void setListOfTimeZoneRequests(List<TimeZoneRequest> listOfTimeZoneRequests) {
		this.listOfTimeZoneRequests = listOfTimeZoneRequests;
	}
}
