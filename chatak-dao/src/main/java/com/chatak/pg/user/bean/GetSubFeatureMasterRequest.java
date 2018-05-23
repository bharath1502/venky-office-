package com.chatak.pg.user.bean;

public class GetSubFeatureMasterRequest extends Request{
	//feature_id from pg_feature table- master value
	private Integer feature_id;

	public String validate(){
		String message = "";
		if(feature_id == null){
			message= "feature_id is the Required field";
		} 
		return message;
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
