package com.chatak.merchant.model;

import java.util.List;

public class FeatureResponse 
{
    private List<String> feature;
	
    private List<String> subFeature;

	/**
	 * @return the feature
	 */
	public List<String> getFeature() {
		return feature;
	}

	/**
	 * @param feature the feature to set
	 */
	public void setFeature(List<String> feature) {
		this.feature = feature;
	}

	/**
	 * @return the subFeature
	 */
	public List<String> getSubFeature() {
		return subFeature;
	}

	/**
	 * @param subFeature the subFeature to set
	 */
	public void setSubFeature(List<String> subFeature) {
		this.subFeature = subFeature;
	}
	
	
	
	

}
