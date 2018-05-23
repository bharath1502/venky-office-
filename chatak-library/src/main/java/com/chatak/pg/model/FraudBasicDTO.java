package com.chatak.pg.model;

import java.util.List;

import com.chatak.pg.bean.SearchRequest;

public class FraudBasicDTO extends SearchRequest {
	
  private static final long serialVersionUID = -6101014265162951274L;

  Long MerchantID;

	private String deniedIp;

	private List<String> iPMultiple;

	private String deniedBin;

	private List<String> binMultiple;

	private String deniedEMail;

	private List<String> eMailMultiple;

	private String deniedCountry;

	private List<String> countryMultiple;
	
	private List<String> isoCountryList;

	/**
	 * @return the deniedIp
	 */
	public String getDeniedIp() {
		return deniedIp;
	}

	/**
	 * @param deniedIp the deniedIp to set
	 */
	public void setDeniedIp(String deniedIp) {
		this.deniedIp = deniedIp;
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
	 * @return the deniedBin
	 */
	public String getDeniedBin() {
		return deniedBin;
	}

	/**
	 * @param deniedBin the deniedBin to set
	 */
	public void setDeniedBin(String deniedBin) {
		this.deniedBin = deniedBin;
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
	 * @return the deniedEMail
	 */
	public String getDeniedEMail() {
		return deniedEMail;
	}

	/**
	 * @param deniedEMail the deniedEMail to set
	 */
	public void setDeniedEMail(String deniedEMail) {
		this.deniedEMail = deniedEMail;
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
	 * @return the deniedCountry
	 */
	public String getDeniedCountry() {
		return deniedCountry;
	}

	/**
	 * @param deniedCountry the deniedCountry to set
	 */
	public void setDeniedCountry(String deniedCountry) {
		this.deniedCountry = deniedCountry;
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

	/**
	 * @return the merchantID
	 */
	public Long getMerchantID() {
		return MerchantID;
	}

	/**
	 * @param merchantID the merchantID to set
	 */
	public void setMerchantID(Long merchantID) {
		MerchantID = merchantID;
	}

  public List<String> getIsoCountryList() {
    return isoCountryList;
  }

  public void setIsoCountryList(List<String> isoCountryList) {
    this.isoCountryList = isoCountryList;
  }
	
	

	
}
