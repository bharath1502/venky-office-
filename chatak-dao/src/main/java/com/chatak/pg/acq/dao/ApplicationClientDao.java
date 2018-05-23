package com.chatak.pg.acq.dao;

import com.chatak.pg.acq.dao.model.PGApplicationClient;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 06-Mar-2015 10:06:52 AM
 * @version 1.0
 */
public interface ApplicationClientDao {
  
  /**
   * DAO method to get application client on client id and access details
   * 
   * @param clientId
   * @param clientAccess
   * @return
   */
  public PGApplicationClient getApplicationClient(String clientId, String clientAccess);
  
  /**
   * DAO method to get application client on auth user and auth password details
   * 
   * @param authUser
   * @param authPassword
   * @return
   */
  public PGApplicationClient getApplicationClientAuth(String authUser, String authPassword);
  
  /**
   * DAO method to get application client on client id
   * 
   * @param clientId
   * @return
   */
  public PGApplicationClient getApplicationClient(String clientId);
  
  /**
   * DAO method to get application client on auth user
   * 
   * @param authUser
   * @return
   */
  public PGApplicationClient getApplicationClientAuth(String authUser);

}
