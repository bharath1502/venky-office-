/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Jun 10, 2015
 * @Time: 10:14:17 AM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_BIN_RANGE")
public class PGBINRange {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "BIN")
  private Long bin;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "DCC_SUPPORTED")
  private Integer dccSupported;

  @Column(name = "EMV_SUPPORTED")
  private Integer emvSupported;

  @Column(name = "SWITCH_ID")
  private Long switchId;

  @Column(name = "REASON")
  private String reason;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the bin
   */
  public Long getBin() {
    return bin;
  }

  /**
   * @param bin the bin to set
   */
  public void setBin(Long bin) {
    this.bin = bin;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * Gets the dcc supported.
   *
   * @return the dcc supported
   */
  public Integer getDccSupported() {
    return dccSupported;
  }

  /**
   * Sets the dcc supported.
   *
   * @param dccSupported the new dcc supported
   */
  public void setDccSupported(Integer dccSupported) {
    this.dccSupported = dccSupported;
  }

  /**
   * Gets the emv supported.
   *
   * @return the emv supported
   */
  public Integer getEmvSupported() {
    return emvSupported;
  }

  /**
   * Sets the emv supported.
   *
   * @param emvSupported the new emv supported
   */
  public void setEmvSupported(Integer emvSupported) {
    this.emvSupported = emvSupported;
  }

  /**
   * Gets the switch id.
   *
   * @return the switch id
   */
  public Long getSwitchId() {
    return switchId;
  }

  /**
   * Sets the switch id.
   *
   * @param switchId the new switch id
   */
  public void setSwitchId(Long switchId) {
    this.switchId = switchId;
  }

  /**
   * Gets the reason.
   *
   * @return the reason
   */
  public String getReason() {
    return reason;
  }

  /**
   * Sets the reason.
   *
   * @param reason the new reason
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

}
