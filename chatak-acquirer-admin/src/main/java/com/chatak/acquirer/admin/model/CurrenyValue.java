package com.chatak.acquirer.admin.model;

public class CurrenyValue {
	
	  private Long id;
	  
	  private String currencyCodeNumeric;
	  
	  private String currencyName;
	  
	  private String status;
	  
	  private String currencyCodeAlpha;
	  
	  private Integer currencyExponent;
	  
	  private Character currencySymbol;
	  
	  private Integer currencySeparatorPosition;
	  
	  private Character currencyThousandsUnit;
	  
	  private Character currencyMinorUnit;
	  
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the currencyCodeNumeric
	 */
	public String getCurrencyCodeNumeric() {
		return currencyCodeNumeric;
	}

	/**
	 * @param currencyCodeNumeric the currencyCodeNumeric to set
	 */
	public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
		this.currencyCodeNumeric = currencyCodeNumeric;
	}
	
	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * @param currencyName the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * @return the currencyCodeAlpha
	 */
	public String getCurrencyCodeAlpha() {
		return currencyCodeAlpha;
	}

	/**
	 * @param currencyCodeAlpha the currencyCodeAlpha to set
	 */
	public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
		this.currencyCodeAlpha = currencyCodeAlpha;
	}

	/**
	 * @return the currencyExponent
	 */
	public Integer getCurrencyExponent() {
		return currencyExponent;
	}

	/**
	 * @param currencyExponent the currencyExponent to set
	 */
	public void setCurrencyExponent(Integer currencyExponent) {
		this.currencyExponent = currencyExponent;
	}
	
	/**
	 * @return the currencySymbol
	 */
	public Character getCurrencySymbol() {
		return currencySymbol;
	}

	/**
	 * @param currencySymbol the currencySymbol to set
	 */
	public void setCurrencySymbol(Character currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	/**
	 * @return the currencySeparatorPosition
	 */
	public Integer getCurrencySeparatorPosition() {
		return currencySeparatorPosition;
	}

	/**
	 * @param currencySeparatorPosition the currencySeparatorPosition to set
	 */
	public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
		this.currencySeparatorPosition = currencySeparatorPosition;
	}

	/**
	 * @return the currencyThousandsUnit
	 */
	public Character getCurrencyThousandsUnit() {
		return currencyThousandsUnit;
	}

	/**
	 * @param currencyThousandsUnit the currencyThousandsUnit to set
	 */
	public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
		this.currencyThousandsUnit = currencyThousandsUnit;
	}
	
	/**
	 * @return the currencyMinorUnit
	 */
	public Character getCurrencyMinorUnit() {
		return currencyMinorUnit;
	}

	/**
	 * @param currencyMinorUnit the currencyMinorUnit to set
	 */
	public void setCurrencyMinorUnit(Character currencyMinorUnit) {
		this.currencyMinorUnit = currencyMinorUnit;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	  
}
