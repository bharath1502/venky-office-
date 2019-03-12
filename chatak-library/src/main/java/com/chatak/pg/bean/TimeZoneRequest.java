package com.chatak.pg.bean;

public class TimeZoneRequest extends SearchRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long countryId;

	private String standardTimeOffset;
	
	private String dayLightTimeOffset;
	
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

	public String getStandardTimeOffset() {
		return standardTimeOffset;
	}

	public void setStandardTimeOffset(String standardTimeOffset) {
		this.standardTimeOffset = standardTimeOffset;
	}

	public String getDayLightTimeOffset() {
		return dayLightTimeOffset;
	}

	public void setDayLightTimeOffset(String dayLightTimeOffset) {
		this.dayLightTimeOffset = dayLightTimeOffset;
	}
}
