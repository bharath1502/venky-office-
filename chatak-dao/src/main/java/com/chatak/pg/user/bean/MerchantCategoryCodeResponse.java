/**
 * 
 */
package com.chatak.pg.user.bean;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2016
 * @Time: 2:44:14 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class MerchantCategoryCodeResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8096811773938397246L;

	private Long id;

	private String merchantCategoryCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCategoryCode() {
		return merchantCategoryCode;
	}

	public void setMerchantCategoryCode(String merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

}
