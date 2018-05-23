package com.chatak.pg.user.bean;

/**
 * @Author: Girmiti Software
 * @Date: Aug 5, 2016
 * @Time: 1:10:26 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class DeleteAssetResponse extends Response{

	private boolean isdeleated;

	/**
	 * @return the isdeleated
	 */
	public boolean isIsdeleated() {
		return isdeleated;
	}

	/**
	 * @param isdeleated the isdeleated to set
	 */
	public void setIsdeleated(boolean isdeleated) {
		this.isdeleated = isdeleated;
	}

}
