package com.chatak.pg.bean;

public class FeeprogramNameResponse extends Response {

  private static final long serialVersionUID = 1L;

  private Long feeProgramId;

  private String feeProgramName;

  /**
   * @return the feeProgramId
   */
  public Long getFeeProgramId() {
    return feeProgramId;
  }

  /**
   * @param feeProgramId
   *          the feeProgramId to set
   */
  public void setFeeProgramId(Long feeProgramId) {
    this.feeProgramId = feeProgramId;
  }

  /**
   * @return the feeProgramName
   */
  public String getFeeProgramName() {
    return feeProgramName;
  }

  /**
   * @param feeProgramName
   *          the feeProgramName to set
   */
  public void setFeeProgramName(String feeProgramName) {
    this.feeProgramName = feeProgramName;
  }

}
