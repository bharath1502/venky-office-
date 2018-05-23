package com.chatak.pg.user.bean;

public class SubFeatureMaster {
	
	private Integer sub_feature_id;
	private Integer feature_id;
	private String feature_url;
	private String feature_name;
	/**
	 * @return the feature_name
	 */
	public String getFeature_name() {
		return feature_name;
	}
	/**
	 * @param feature_name the feature_name to set
	 */
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	/**
	 * @param sub_feature_id the sub_feature_id to set
	 */
	public void setSub_feature_id(Integer sub_feature_id) {
		this.sub_feature_id = sub_feature_id;
	}
	/**
	 * @param feature_url the feature_url to set
	 */
	public void setFeature_url(String feature_url) {
		this.feature_url = feature_url;
	}
	/**
	 * @return the feature_url
	 */
	public String getFeature_url() {
		return feature_url;
	}
	/**
	 * @return the sub_feature_id
	 */
	public Integer getSub_feature_id() {
		return sub_feature_id;
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
