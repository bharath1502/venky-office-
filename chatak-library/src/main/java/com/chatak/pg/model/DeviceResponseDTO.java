package com.chatak.pg.model;

import java.util.List;

public class DeviceResponseDTO extends Response{

  /**
   * 
   */
  private static final long serialVersionUID = 195189983508821893L;
  
  private List<PosDeviceDTO> deviceDTO;

  public List<PosDeviceDTO> getDeviceDTO() {
    return deviceDTO;
  }

  public void setDeviceDTO(List<PosDeviceDTO> deviceDTO) {
    this.deviceDTO = deviceDTO;
  }

}
