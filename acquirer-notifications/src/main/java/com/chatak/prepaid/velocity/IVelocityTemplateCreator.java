/**
 * 
 */
package com.chatak.prepaid.velocity;

import java.util.Map;


public interface IVelocityTemplateCreator {

  /**
   * This method is used to create XMLRequest
   * @param requestMap
   * @param requestType
   * @return
   * @throws Exception
   */
  public String createEmailTemplate(Map<String, String> requestMap, String requestType);

}
