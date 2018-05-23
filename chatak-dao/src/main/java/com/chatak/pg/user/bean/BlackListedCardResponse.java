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
public class BlackListedCardResponse extends Response{
	
	private static final long serialVersionUID = 4124080161329849537L;

	private List<BlackListedCardRequest> blacklistedcardRequest;
	
	public List<BlackListedCardRequest> getBlackListedCardRequest() {
		return blacklistedcardRequest;
	}

	public void setBlackListedCardRequest(List<BlackListedCardRequest> blacklistedcardRequest) {
		this.blacklistedcardRequest = blacklistedcardRequest;
	}

}