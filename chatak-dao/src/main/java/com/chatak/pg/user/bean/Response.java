package com.chatak.pg.user.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_NULL)
public class Response implements Serializable {

  private static final long serialVersionUID = 4519640574583192188L;

  private String errorCode;

  private String errorMessage;

  private String entityId;

  private Integer totalNoOfRows;

  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * @param errorCode the errorCode to set
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @return the entityId
   */
  public String getEntityId() {
    return entityId;
  }

  /**
   * @param entityId the entityId to set
   */
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public Integer getTotalNoOfRows() {
    return totalNoOfRows;
  }

  public void setTotalNoOfRows(Integer totalNoOfRows) {
    this.totalNoOfRows = totalNoOfRows;
  }

}
