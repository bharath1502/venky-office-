package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PG_FEATURES")
public class PGFeatures implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3847999642936659960L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_FEATURES_ID", sequenceName = "SEQ_PG_FEATURES")
    @GeneratedValue(generator = "SEQ_PG_FEATURES_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FEATURE_ID")
	private Long featureId;
	
	@Column(name = "FEATURE_NAME")
	private String featureName;
	
	@Column(name = "FEATURE_URL")
	private String featureUrl;

	/**
	 * @return the featureId
	 */
	public Long getFeatureId() {
		return featureId;
	}

	/**
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}
	
	/**
	 * @param featureUrl the featureUrl to set
	 */
	public void setFeatureUrl(String featureUrl) {
		this.featureUrl = featureUrl;
	}

	/**
	 * @param featureName the featureName to set
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	/**
	 * @param featureId the featureId to set
	 */
	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	/**
	 * @return the featureUrl
	 */
	public String getFeatureUrl() {
		return featureUrl;
	}

}
