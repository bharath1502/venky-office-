package com.chatak.pg.model;

import java.io.Serializable;

public class OtherFeesDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4606615505322263319L;

  private String settlementFee;

  private String chargeBacKFee;

  private String setupFee;

  private String chargeFrequency;
  
  private Long id;
  
  private Long feeProgramId;


  /**
   * @return the chargeFrequency
   */
  public String getChargeFrequency() {
    return chargeFrequency;
  }

  /**
   * @param chargeFrequency
   *          the chargeFrequency to set
   */
  public void setChargeFrequency(String chargeFrequency) {
    this.chargeFrequency = chargeFrequency;
  }

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

  public String getSettlementFee() {
    return settlementFee;
  }

  public void setSettlementFee(String settlementFee) {
    this.settlementFee = settlementFee;
  }

  public String getChargeBacKFee() {
    return chargeBacKFee;
  }

  public void setChargeBacKFee(String chargeBacKFee) {
    this.chargeBacKFee = chargeBacKFee;
  }

  public String getSetupFee() {
    return setupFee;
  }

  public void setSetupFee(String setupFee) {
    this.setupFee = setupFee;
  }
  
  

}
