/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.bean.Response;


/**
 * @Author: Girmiti Software
 * @Date: Aug 1, 2016
 * @Time: 11:20:11 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class SwitchResponse extends Response{
	
	private static final long serialVersionUID = 4124080161329849537L;

	private List<SwitchRequest> switchRequest;
	
	private Integer noOfRecords;
	
	public List<SwitchRequest> getSwitchRequest() {
		return switchRequest;
	}

	public void setSwitchRequest(List<SwitchRequest> switchRequest) {
		this.switchRequest = switchRequest;
	}

	/**
	 * @return the noOfRecords
	 */
	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	/**
	 * @param noOfRecords the noOfRecords to set
	 */
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

}