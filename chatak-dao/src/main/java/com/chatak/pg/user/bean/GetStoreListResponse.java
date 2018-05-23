package com.chatak.pg.user.bean;

import java.util.List;

public class GetStoreListResponse extends Response{

	private List<Store> store_list;

	/**
	 * @return the store_list
	 */
	public List<Store> getStore_list() {
		return store_list;
	}

	/**
	 * @param store_list the store_list to set
	 */
	public void setStore_list(List<Store> store_list) {
		this.store_list = store_list;
	}

}
