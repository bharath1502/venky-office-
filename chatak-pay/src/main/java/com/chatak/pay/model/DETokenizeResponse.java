package com.chatak.pay.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 27-Mar-2015 5:00:08 PM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DETokenizeResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 4372973469447871589L;

  private String versionNum;

  private String status;

  private String pan;

  private String panExpDate;

  private Integer transDataLen;

  private String transDataElements;

  public String getVersionNum() {
    return versionNum;
  }

  public void setVersionNum(String versionNum) {
    this.versionNum = versionNum;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPan() {
    return pan;
  }

  public void setPan(String pan) {
    this.pan = pan;
  }

  public String getPanExpDate() {
    return panExpDate;
  }

  public void setPanExpDate(String panExpDate) {
    this.panExpDate = panExpDate;
  }

  public Integer getTransDataLen() {
    return transDataLen;
  }

  public void setTransDataLen(Integer transDataLen) {
    this.transDataLen = transDataLen;
  }

  public String getTransDataElements() {
    return transDataElements;
  }

  public void setTransDataElements(String transDataElements) {
    this.transDataElements = transDataElements;
  }

}
