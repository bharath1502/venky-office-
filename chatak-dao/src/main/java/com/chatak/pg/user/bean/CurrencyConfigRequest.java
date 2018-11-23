/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @Author: Girmiti Software
 * @Date: May 2, 2018
 * @Time: 8:08:52 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_NULL)
public class CurrencyConfigRequest implements Serializable{
	private static final long serialVersionUID = -362537255370697839L;

	private Long id;

	private String currencyName;

	private String currencyCodeNumeric;

	private String currencyCodeAlpha;

	private String currencySymbol;

	private Long currencySeparatorPosition;

	private Character currencyMinorUnit;

	private Character currencyThousandsUnit;

	private Long currencyExponent;

	private String status;

	public Long getCurrencySeparatorPosition() {
		return currencySeparatorPosition;
	}

	public void setCurrencySeparatorPosition(Long currencySeparatorPosition) {
		this.currencySeparatorPosition = currencySeparatorPosition;
	}

	public Character getCurrencyMinorUnit() {
		return currencyMinorUnit;
	}

	public void setCurrencyMinorUnit(Character currencyMinorUnit) {
		this.currencyMinorUnit = currencyMinorUnit;
	}

	public Character getCurrencyThousandsUnit() {
		return currencyThousandsUnit;
	}

	public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
		this.currencyThousandsUnit = currencyThousandsUnit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyCodeAlpha() {
		return currencyCodeAlpha;
	}

	public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
		this.currencyCodeAlpha = currencyCodeAlpha;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public Long getCurrencyExponent() {
		return currencyExponent;
	}

	public void setCurrencyExponent(Long currencyExponent) {
		this.currencyExponent = currencyExponent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrencyCodeNumeric() {
		return currencyCodeNumeric;
	}

	public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
		this.currencyCodeNumeric = currencyCodeNumeric;
	}
}