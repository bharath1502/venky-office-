package com.chatak.pg.user.bean;

import java.util.List;

public class Feature {
	
	private Integer feature_id;
	private String feature_name;
	private String feature_url;
	private Integer display_order;
	
	private List<SubFeature> sub_features;
	/**
	 * @return the feature_name
	 */
	public String getFeature_name() {
		return feature_name;
	}
	/**
	 * @return the feature_url
	 */
	public String getFeature_url() {
		return feature_url;
	}
	/**
	 * @return the sub_features
	 */
	public List<SubFeature> getSub_features() {
		return sub_features;
	}
	/**
	 * @param feature_name the feature_name to set
	 */
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	/**
	 * @param feature_url the feature_url to set
	 */
	public void setFeature_url(String feature_url) {
		this.feature_url = feature_url;
	}
	/**
	 * @param sub_features the sub_features to set
	 */
	public void setSub_features(List<SubFeature> sub_features) {
		this.sub_features = sub_features;
	}
	/**
	 * @return the display_order
	 */
	public Integer getDisplay_order() {
		return display_order;
	}
	/**
	 * @param display_order the display_order to set
	 */
	public void setDisplay_order(Integer display_order) {
		this.display_order = display_order;
	}
	/**
	 * @return the feature_id
	 */
	public Integer getFeature_id() {
		return feature_id;
	}
	/**
	 * @param feature_id the feature_id to set
	 */
	public void setFeature_id(Integer feature_id) {
		this.feature_id = feature_id;
	}

}
