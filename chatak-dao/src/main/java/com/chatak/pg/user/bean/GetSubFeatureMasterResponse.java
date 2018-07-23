package com.chatak.pg.user.bean;

import java.util.List;

public class GetSubFeatureMasterResponse extends Response{

	private List<SubFeatureMaster> sub_feature_master_list;

	/**
	 * @return the sub_feature_master_list
	 */
	public List<SubFeatureMaster> getSub_feature_master_list() {
		return sub_feature_master_list;
	}

	/**
	 * @param sub_feature_master_list the sub_feature_master_list to set
	 */
	public void setSub_feature_master_list(
			List<SubFeatureMaster> sub_feature_master_list) {
		this.sub_feature_master_list = sub_feature_master_list;
	}


}
