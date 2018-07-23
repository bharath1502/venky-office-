/**
 * 
 */
package com.chatak.pay.service;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.acq.dao.model.PGApplicationClient;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 06-Mar-2015 10:13:02 AM
 * @version 1.0
 */
public interface ChatakPayService {
  
  /**
   * Service method to get application client on client id and access details
   * 
   * @param clientId
   * @return
   * @throws ChatakPayException
   */
  public PGApplicationClient getApplicationClient(String clientId) throws ChatakPayException;

  /**
   * Service method to get application client on client id and access details
   * 
   * @param clientId
   * @param clientAccess
   * @return
   * @throws ChatakPayException
   */
  public PGApplicationClient getApplicationClient(String clientId, String clientAccess) throws ChatakPayException;
  
  /**
   * Service method to get application client on auth user and auth password details
   * 
   * @param authUser
   * @return
   * @throws ChatakPayException
   */
  public PGApplicationClient getApplicationClientAuth(String authUser) throws ChatakPayException;
  
  /**
   * Service method to get application client on auth user and auth password details
   * 
   * @param authUser
   * @param authPassword
   * @return
   * @throws ChatakPayException
   */
  public PGApplicationClient getApplicationClientAuth(String authUser, String authPassword) throws ChatakPayException;

  
}
