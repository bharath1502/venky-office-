/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Feb 6, 2016
 * @Time: 12:08:48 PM
 * @Version: 1.0
 * @Comments: Model to store other fee values for fee program configuration.
 */
@Entity
@Table(name = "PG_OTHER_FEE_VALUE")
public class PGOtherFeeValue implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8473102453879350448L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_OTHER_FEE_VALUE_ID", sequenceName = "SEQ_PG_OTHER_FEE_VALUE")
  @GeneratedValue(generator = "SEQ_PG_OTHER_FEE_VALUE_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PG_OTHER_FEE_VALUE_ID")
  private Long id;

  @Column(name = "SETUP_FEE")
  private Long setupFee;

  @Column(name = "SETTLEMENT_FEE")
  private Long settlementFee;

  @Column(name = "CHARGE_BACK_FEE")
  private Long chargeBackFee;

  @Column(name = "CHARGE_FREQUENCY")
  private String chargeFrequency;

  @Column(name = "CREATED_DATE", updatable = false)
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "CREATED_BY", updatable = false)
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the setupFee
   */
  public Long getSetupFee() {
    return setupFee;
  }

  /**
   * @param setupFee
   *          the setupFee to set
   */
  public void setSetupFee(Long setupFee) {
    this.setupFee = setupFee;
  }

  /**
   * @return the settlementFee
   */
  public Long getSettlementFee() {
    return settlementFee;
  }

  /**
   * @param settlementFee
   *          the settlementFee to set
   */
  public void setSettlementFee(Long settlementFee) {
    this.settlementFee = settlementFee;
  }

  /**
   * @return the chargeBackFee
   */
  public Long getChargeBackFee() {
    return chargeBackFee;
  }

  /**
   * @param chargeBackFee
   *          the chargeBackFee to set
   */
  public void setChargeBackFee(Long chargeBackFee) {
    this.chargeBackFee = chargeBackFee;
  }

  /**
   * @return the chargeFrequency
   */
  public String getChargeFrequency() {
    return chargeFrequency;
  }
  
  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy
   *          the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @param chargeFrequency
   *          the chargeFrequency to set
   */
  public void setChargeFrequency(String chargeFrequency) {
    this.chargeFrequency = chargeFrequency;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param updatedDate
   *          the updatedDate to set
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
   * @param createdDate
   *          the createdDate to set
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
   * @param createdBy
   *          the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

}
