/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: 31-Aug-2015
 * @Time: 10:47:54 AM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_PARAMS")
public class PGParams implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6180476674685478905L;

  @Id
  @Column(name = "ID")
  private Long id; 
  
  @Column(name = "PARAM_NAME")
  private String paramName;

  @Column(name = "PARAM_VALUE")
  private String paramValue;

  @Column(name = "STATUS")
  private Integer status;

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
   * @return the paramName
   */
  public String getParamName() {
    return paramName;
  }

  /**
   * @param paramName the paramName to set
   */
  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  /**
   * @return the paramValue
   */
  public String getParamValue() {
    return paramValue;
  }

  /**
   * @param paramValue the paramValue to set
   */
  public void setParamValue(String paramValue) {
    this.paramValue = paramValue;
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
  
}
