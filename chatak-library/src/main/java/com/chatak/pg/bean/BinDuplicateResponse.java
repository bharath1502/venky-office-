package com.chatak.pg.bean;


public class BinDuplicateResponse extends Response
{

  private static final long serialVersionUID = -9105967310196202016L;
  
  private Long id;
  
  private Long bin;
  
  public Long getBin() {
    return bin;
  }

  public void setBin(Long bin) {
    this.bin = bin;
  }

  private String switchName;
  
  private Integer dccSupported;
  
  private Integer emvSupported;
  
  private String status;
  
  private Integer switchId;
  
  private String dcc;
  
  public void setId(Long id) {
    this.id = id;
  }

  public String getSwitchName() {
    return switchName;
  }
  
  public Long getId() {
    return id;
  }

  public void setSwitchName(String switchName) {
    this.switchName = switchName;
  }
  
  public Integer getEmvSupported() {
    return emvSupported;
  }

  public Integer getDccSupported() {
    return dccSupported;
  }

  public void setDccSupported(Integer dccSupported) {
    this.dccSupported = dccSupported;
  }

  public String getStatus() {
    return status;
  }

  public void setEmvSupported(Integer emvSupported) {
    this.emvSupported = emvSupported;
  }

  public String getDcc() {
    return dcc;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setSwitchId(Integer switchId) {
    this.switchId = switchId;
  }

  public void setDcc(String dcc) {
    this.dcc = dcc;
  }
  
  public Integer getSwitchId() {
    return switchId;
  }

}
