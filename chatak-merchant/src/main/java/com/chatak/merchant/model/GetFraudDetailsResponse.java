package com.chatak.merchant.model;

import java.util.List;

import com.chatak.merchant.controller.model.Option;

public class GetFraudDetailsResponse extends Response{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7863718204621387161L;

	private List<String> iPMultiple;

	private List<String> binMultiple;

	private List<String> eMailMultiple;

	private List<String> countryMultiple;
	
	private List<Option> isoCountryList;

	public List<Option> getIsoCountryList() {
		return isoCountryList;
	}

	public void setIsoCountryList(List<Option> isoCountryList) {
		this.isoCountryList = isoCountryList;
	}

	/**
	 * @return the iPMultiple
	 */
	public List<String> getiPMultiple() {
		return iPMultiple;
	}

	/**
	 * @param iPMultiple the iPMultiple to set
	 */
	public void setiPMultiple(List<String> iPMultiple) {
		this.iPMultiple = iPMultiple;
	}

	/**
	 * @return the binMultiple
	 */
	public List<String> getBinMultiple() {
		return binMultiple;
	}

	/**
	 * @param binMultiple the binMultiple to set
	 */
	public void setBinMultiple(List<String> binMultiple) {
		this.binMultiple = binMultiple;
	}

	/**
	 * @return the eMailMultiple
	 */
	public List<String> geteMailMultiple() {
		return eMailMultiple;
	}

	/**
	 * @param eMailMultiple the eMailMultiple to set
	 */
	public void seteMailMultiple(List<String> eMailMultiple) {
		this.eMailMultiple = eMailMultiple;
	}

	/**
	 * @return the countryMultiple
	 */
	public List<String> getCountryMultiple() {
		return countryMultiple;
	}

	/**
	 * @param countryMultiple the countryMultiple to set
	 */
	public void setCountryMultiple(List<String> countryMultiple) {
		this.countryMultiple = countryMultiple;
	}

}
