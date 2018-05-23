package com.chatak.merchant.controller.model;

/**
 * Model class used to process role response when new role is added
 * 
 * @Author: Girmiti Software
 * @Date: 16-Aug-2014
 * @Time: 11:43:36 am
 * @Version: 1.0
 */
public class AddRoleResponse {
  private Boolean status;

  private String message;

  /**
   * @return the status
   */
  public Boolean getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(Boolean status) {
    this.status = status;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
