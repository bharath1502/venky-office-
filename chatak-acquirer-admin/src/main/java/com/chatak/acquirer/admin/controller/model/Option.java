/**
 * 
 */
package com.chatak.acquirer.admin.controller.model;

import java.io.Serializable;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 08-Jan-2015 12:00:44 AM
 * @version 1.0
 */
public class Option implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = -865034353778525940L;

  private String value;

  private String label;
  
  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @param label
   *          the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

}
