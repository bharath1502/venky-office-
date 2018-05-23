package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.chatak.pg.model.FeeValue;
import com.chatak.pg.util.CommonUtil;

@Entity
@Table(name="PG_FEE_PROGRAM_VALUE")
public class PGFeeProgramValue implements Serializable
{
    private static Logger logger = Logger.getLogger(PGFeeProgramValue.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -4792017751518282965L;

	@Id
	/*@SequenceGenerator(name="seq_pg_fee_program_value_id", sequenceName="SEQ_PG_FEE_PROGRAM_VALUE")
	@GeneratedValue(generator="seq_pg_fee_program_value_id")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FEE_PROGRAM_VALUE_ID")
	private Long feePgmValueId;

	@Column(name = "FEE_CODE")
	private Long feeCode;
	
	@Column(name = "FEE_PROGRAM_ID")
	private Long feeProgramId;
	
	@Column(name = "FEE_MAX_VALUE")
	private Long    feeMaxValue;
	
	@Column(name = "FEE_MIN_VALUE")
	private Long    feeMinValue;
	
	@Column(name = "FEE_PERCENTAGE_ONLY")
	private Long  feePercentageOnly;
	
	@Column(name = "CREATED_DATE",updatable=false)
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
	@Column(name = "CREATED_BY",updatable=false)
	private String createdBy;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	/**
	 * @return the feePgmValueId
	 */
	public Long getFeePgmValueId() {
		return feePgmValueId;
	}

	/**
	 * @param feePgmValueId the feePgmValueId to set
	 */
	public void setFeePgmValueId(Long feePgmValueId) {
		this.feePgmValueId = feePgmValueId;
	}

	/**
	 * @return the feeCode
	 */
	public Long getFeeCode() {
		return feeCode;
	}

	/**
	 * @param feeCode the feeCode to set
	 */
	public void setFeeCode(Long feeCode) {
		this.feeCode = feeCode;
	}

	/**
	 * @return the feeProgramId
	 */
	public Long getFeeProgramId() {
		return feeProgramId;
	}

	/**
	 * @param feeProgramId the feeProgramId to set
	 */
	public void setFeeProgramId(Long feeProgramId) {
		this.feeProgramId = feeProgramId;
	}

	/**
	 * @return the feeMaxValue
	 */
	public Long getFeeMaxValue() {
		return feeMaxValue;
	}

	/**
	 * @param feeMaxValue the feeMaxValue to set
	 */
	public void setFeeMaxValue(Long feeMaxValue) {
		this.feeMaxValue = feeMaxValue;
	}

	/**
	 * @return the feeMinValue
	 */
	public Long getFeeMinValue() {
		return feeMinValue;
	}

	/**
	 * @param feeMinValue the feeMinValue to set
	 */
	public void setFeeMinValue(Long feeMinValue) {
		this.feeMinValue = feeMinValue;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @return the feePercentageOnly
	 */
	public Long getFeePercentageOnly() {
		return feePercentageOnly;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * @param feePercentageOnly the feePercentageOnly to set
	 */
	public void setFeePercentageOnly(Long feePercentageOnly) {
		this.feePercentageOnly = feePercentageOnly;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public static PGFeeProgramValue convertToDAO(FeeValue feeValue){
		PGFeeProgramValue feeProgramValue = new PGFeeProgramValue();
		
		try {
			feeProgramValue = (PGFeeProgramValue) CommonUtil.copyBeanProperties(feeValue, PGFeeProgramValue.class);
			feeProgramValue.setFeeMinValue((CommonUtil.getLongAmount(feeValue.getFeeMinValue())));
			feeProgramValue.setFeeMaxValue((CommonUtil.getLongAmount(feeValue.getFeeMaxValue())));
			feeProgramValue.setFeePercentageOnly((CommonUtil.getLongAmountFromDoubleString(feeValue.getFeePercentage())));
			
			
		} catch (Exception e) {
		    logger.error("ERROR:: PGFeeProgramValue:: convertToDAO method", e);
		}
		
		return feeProgramValue;
	}
	
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}

