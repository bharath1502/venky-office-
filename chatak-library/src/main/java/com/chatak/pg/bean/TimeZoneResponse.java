package com.chatak.pg.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_NULL)
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
