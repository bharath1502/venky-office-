/**
 * 
 */
package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 1, 2015 10:17:12 AM
 * @version 1.0
 */
public class CIPartnersAgentsDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8540853846957682961L;

  private CIEntityDetailsResponse ciResponse;
  
  private List<String> linkedPartnersList;
  
  private List<String> linkedAgentsList;
  
  private String linkedPartnerId;

  /**
   * @return the ciResponse
   */
  public CIEntityDetailsResponse getCiResponse() {
    return ciResponse;
  }

  /**
   * @param ciResponse the ciResponse to set
   */
  public void setCiResponse(CIEntityDetailsResponse ciResponse) {
    this.ciResponse = ciResponse;
  }

  /**
   * @return the linkedPartnersList
   */
  public List<String> getLinkedPartnersList() {
    return linkedPartnersList;
  }

  /**
   * @param linkedPartnersList the linkedPartnersList to set
   */
  public void setLinkedPartnersList(List<String> linkedPartnersList) {
    this.linkedPartnersList = linkedPartnersList;
  }

  /**
   * @return the linkedAgentsList
   */
  public List<String> getLinkedAgentsList() {
    return linkedAgentsList;
  }

  /**
   * @param linkedAgentsList the linkedAgentsList to set
   */
  public void setLinkedAgentsList(List<String> linkedAgentsList) {
    this.linkedAgentsList = linkedAgentsList;
  }

  /**
   * @return the linkedPartnerId
   */
  public String getLinkedPartnerId() {
    return linkedPartnerId;
  }

  /**
   * @param linkedPartnerId the linkedPartnerId to set
   */
  public void setLinkedPartnerId(String linkedPartnerId) {
    this.linkedPartnerId = linkedPartnerId;
  }
  
}
