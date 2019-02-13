/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Feb 11, 2019
 * @Time: 4:03:23 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = " PG_MERCHANT_USER_FEATURE_MAPPING")
public class PGMerchantUserFeatureMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "Merchant_User_ID")
	private Integer merchantUserID;

	@Column(name = "FEATURE_ID")
	private Long featureId;

	@Column(name = "STATUS")
	private Boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMerchantUserID() {
		return merchantUserID;
	}

	public void setMerchantUserID(Integer merchantUserID) {
		this.merchantUserID = merchantUserID;
	}

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
