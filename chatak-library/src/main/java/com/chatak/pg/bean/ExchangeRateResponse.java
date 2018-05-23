package com.chatak.pg.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Sep 27, 2016
 * @Time: 2:45:53 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class ExchangeRateResponse extends Response {

	private static final long serialVersionUID = 1L;
	
	private List<ExchangeRate> exchangeRateInfo;
	
	private Integer noOfRecords;

	public List<ExchangeRate> getExchangeRateInfo() {
		return exchangeRateInfo;
	}

	public void setExchangeRateInfo(List<ExchangeRate> exchangeRateInfo) {
		this.exchangeRateInfo = exchangeRateInfo;
	}

	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

}
