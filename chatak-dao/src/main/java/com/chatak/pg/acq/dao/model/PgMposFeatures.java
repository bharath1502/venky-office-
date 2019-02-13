/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: 11-Feb-2019
 * @Time: 11:14:19 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "MPOS_FEATURE")
public class PgMposFeatures implements Serializable {
 
	private static final long serialVersionUID = 1L;

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "PgMposFeatures [id=" + id + ", featureName=" + featureName + ", transactionType=" + transactionType
				+ "]";
	}

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "mPosF_Name")
	private String featureName;
	
	@Column(name = "Transcation_Type")
	private String transactionType;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the featurename
	 */
	public String getFeaturename() {
		return featureName;
	}

	/**
	 * @param featurename the featurename to set
	 */
	public void setFeaturename(String featurename) {
		this.featureName = featurename;
	}

	/**
	 * @return the transactiontype
	 */
	public String getTransactiontype() {
		return transactionType;
	}

	/**
	 * @param transactiontype the transactiontype to set
	 */
	public void setTransactiontype(String transactiontype) {
		this.transactionType = transactiontype;
	}
	
}