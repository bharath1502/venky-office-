package com.chatak.pg.model;


import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.chatak.pg.bean.SearchRequest;

public class CommissionDTO extends SearchRequest

{

  /**
   * @return the merchantOnBoardingFee
   */
  public String getMerchantOnBoardingFee() {
    return merchantOnBoardingFee;
  }

  /**
   * @param merchantOnBoardingFee the merchantOnBoardingFee to set
   */
  public void setMerchantOnBoardingFee(String merchantOnBoardingFee) {
    this.merchantOnBoardingFee = merchantOnBoardingFee;
  }

  /**
   * 
   */
  private static final long serialVersionUID = 4831977998224364577L;

  private Long commissionProgramId;

  private String commissionName;
  
  private String status;
  
  private String merchantOnBoardingFee; 
  
  private List<OtherCommissionDTO> otherCommissionDTO = new AutoPopulatingList<OtherCommissionDTO>(OtherCommissionDTO.class);

  /**
   * @return the commissionProgramId
   */
  public Long getCommissionProgramId() {
    return commissionProgramId;
  }
  
  /**
   * @param commissionName the commissionName to set
   */
  public void setCommissionName(String commissionName) {
    this.commissionName = commissionName;
  }

  /**
   * @param commissionProgramId the commissionProgramId to set
   */
  public void setCommissionProgramId(Long commissionProgramId) {
    this.commissionProgramId = commissionProgramId;
  }
  
  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the commissionName
   */
  public String getCommissionName() {
    return commissionName;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the merchantOnBoardingFee
   */

  /**
   * @return the otherCommissionDTO
   */
  public List<OtherCommissionDTO> getOtherCommissionDTO() {
    return otherCommissionDTO;
  }

  /**
   * @param otherCommissionDTO the otherCommissionDTO to set
   */
  public void setOtherCommissionDTO(List<OtherCommissionDTO> otherCommissionDTO) {
    this.otherCommissionDTO = otherCommissionDTO;
  }

  /**
   * @return the serialversionuid
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  };



 


 
}
