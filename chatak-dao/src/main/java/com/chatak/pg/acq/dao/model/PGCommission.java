package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PG_COMMISSION_PROGRAM")
public class PGCommission  implements Serializable {

	private static final long serialVersionUID = 7794012959027604232L;

	@Id
	/*@SequenceGenerator(name = "seq_pg_commission_program_id", sequenceName = "SEQ_PG_COMMISSION_PROGRAM")
	@GeneratedValue(generator = "seq_pg_commission_program_id")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMMISSION_PROGRAM_ID")
	private Long commissionProgramId;
	
	 @Column(name = "COMMISSION_NAME")
	 private String commissionName;
	
	  @Column(name = "STATUS")
	  private String status;
	
	  @Column(name = "MERCHANT_ON_BORDING_FEE")
	  private Double merchantOnBoardingFee;
	
	  @Column(name = "CREATED_DATE", updatable = false)
	  private Timestamp createdDate;

	  @Column(name = "UPDATED_DATE")
	  private Timestamp updatedDate;

	  @Column(name = "CREATED_BY", updatable = false)
	  private String createdBy;

	  @Column(name = "UPDATED_BY")
	  private String updatedBy;
	  
	  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	  @JoinColumn(name = "COMMISSION_PROGRAM_ID")
	  private List<PGOtherCommission> OtherCommission;
	  
	  public Timestamp getCreatedDate() {
		return createdDate;
	  }
	  
	  public List<PGOtherCommission> getOtherCommission() {
			return OtherCommission;
		  }

	  public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	  }

	  public Timestamp getUpdatedDate() {
		return updatedDate;
	  }
	  
	  public void setOtherCommission(List<PGOtherCommission> otherCommission) {
			OtherCommission = otherCommission;
		  }

	  public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	  }

	  public String getCreatedBy() {
		return createdBy;
	  }
	  
	  public void setCommissionProgramId(Long commissionProgramId) {
			this.commissionProgramId = commissionProgramId;
		  }

	  public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	  }

	  public String getUpdatedBy() {
		return updatedBy;
	  }
	  
	  public Long getCommissionProgramId() {
			return commissionProgramId;
		  }

	  public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	  }

	  public String getCommissionName() {
		return commissionName;
	  }

	  public void setCommissionName(String commissionName) {
		this.commissionName = commissionName;
	  }

	  public String getStatus() {
		return status;
	  }

	  public void setStatus(String status) {
		this.status = status;
	  }

	/**
	 * @return the merchantOnBoardingFee
	 */
	public Double getMerchantOnBoardingFee() {
		return merchantOnBoardingFee;
	}

	/**
	 * @param merchantOnBoardingFee the merchantOnBoardingFee to set
	 */
	public void setMerchantOnBoardingFee(Double merchantOnBoardingFee) {
		this.merchantOnBoardingFee = merchantOnBoardingFee;
	}

	
}