package com.chatak.pg.user.bean;

import java.util.List;

public class GetFeaturesListResponse extends Response{

	private List<Feature> feature_list;

	/**
	 * @return the feature_list
	 */
	public List<Feature> getFeature_list() {
		return feature_list;
	}

	/**
	 * @param feature_list the feature_list to set
	 */
	public void setFeature_list(List<Feature> feature_list) {
		this.feature_list = feature_list;
	}

}
