package com.chatak.merchant.constants;

import com.chatak.pg.util.Properties;

public interface StatusConstants
{
	public static final String STATUS_CODE_SUCCESS = "0";
	
	public static final String STATUS_CODE_FAILED = "1";
	
	public static final String STATUS_MESSAGE_SUCCESS = "SUCCESS";
	
	public static final String STATUS_MESSAGE_FAILED = "FAILED";
	
	public static final String STATUS_CODE_ACCESS_TOKEN_INVALID = "3";
	
	public static final String STATUS_MESSAGE_ACCESS_TOKEN_INVALID = "Access Token is invalid";
	
	public static final String STATUS_MESSAGE_FAILURE = "Unable to process the request";
	
	public static final String USER_INACTIVE_ERROR_CODE = "1100";
	
	public static final String REASON = "30";
	
	public static final String ALLOW_RIGHT_CLICK =
		      Properties.getProperty("chatak.merchant.allow.right.click.flag");
	
}
