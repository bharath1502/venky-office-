package com.chatak.pg.user.bean;

import java.util.List;

public class GetFeatureMasterResponse extends Response{

	private List<FeatureMaster> feature_master_list;

	/**
	 * @return the feature_master_list
	 */
	public List<FeatureMaster> getFeature_master_list() {
		return feature_master_list;
	}

	/**
	 * @param feature_master_list the feature_master_list to set
	 */
	public void setFeature_master_list(List<FeatureMaster> feature_master_list) {
		this.feature_master_list = feature_master_list;
	}


}
