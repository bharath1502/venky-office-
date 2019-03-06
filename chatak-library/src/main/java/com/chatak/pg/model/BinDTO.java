package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class BinDTO.
 */
public class BinDTO extends SearchRequest{

  /**
   * 
   */
  private static final long serialVersionUID = -4928663744020549534L;

  private Long id;
  
  private Integer binNumber;
  
  private String switchName;
  
  private Integer dccSupported;
  
  private Integer emvSupported;
  
  private String status;
  
  private Integer switchId;
  
  private String dcc;
  
  private String emv;

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  @Override
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the switch name.
   *
   * @return the switch name
   */
  public String getSwitchName() {
    return switchName;
  }

  /**
   * Sets the switch name.
   *
   * @param switchName the new switch name
   */
  public void setSwitchName(String switchName) {
    this.switchName = switchName;
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

  public Integer getBinNumber() {
    return binNumber;
  }

  public void setBinNumber(Integer binNumber) {
    this.binNumber = binNumber;
  }
  
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getSwitchId() {
    return switchId;
  }

  public void setSwitchId(Integer switchId) {
    this.switchId = switchId;
  }
  public String getDcc() {
    return dcc;
  }

  public void setDcc(String dcc) {
    this.dcc = dcc;
  }

  @Override
  public String getEmv() {
    return emv;
  }

  @Override
  public void setEmv(String emv) {
    this.emv = emv;
  }

}
