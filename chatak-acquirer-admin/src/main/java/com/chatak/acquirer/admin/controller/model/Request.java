/**
 * 
 */
package com.chatak.acquirer.admin.controller.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Model class to hold basic details for every request
 * 
 * @author Girmiti Software
 * @date 02-Jul-2014 10:49:42 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3463794052777695279L;

  private String createdBy;

  private String orginChannel;

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy
   *          the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the orginChannel
   */
  public String getOrginChannel() {
    return orginChannel;
  }

  /**
   * @param orginChannel
   *          the orginChannel to set
   */
  public void setOrginChannel(String orginChannel) {
    this.orginChannel = orginChannel;
  }

}
