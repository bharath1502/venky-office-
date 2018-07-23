/**
 * 
 */
package com.chatak.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.ChatakPayService;
import com.chatak.pg.acq.dao.ApplicationClientDao;
import com.chatak.pg.acq.dao.model.PGApplicationClient;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 06-Mar-2015 10:13:19 AM
 * @version 1.0
 */
@Service
public class ChatakPayServiceImpl implements ChatakPayService {
  
  @Autowired
  private ApplicationClientDao applicationClientDao;

  @Override
  public PGApplicationClient getApplicationClient(String clientId, String clientAccess) throws ChatakPayException {
    PGApplicationClient pgApplicationClient = applicationClientDao.getApplicationClient(clientId, clientAccess);
    if(null == pgApplicationClient) {
      throw new ChatakPayException();
    }
    return pgApplicationClient;
  }

  @Override
  public PGApplicationClient getApplicationClientAuth(String authUser, String authPassword) throws ChatakPayException {
    PGApplicationClient pgApplicationClient = applicationClientDao.getApplicationClientAuth(authUser, authPassword);
    if(null == pgApplicationClient) {
      throw new ChatakPayException();
    }
    return pgApplicationClient;
  }

  @Override
  public PGApplicationClient getApplicationClient(String clientId) throws ChatakPayException {
    PGApplicationClient pgApplicationClient = applicationClientDao.getApplicationClient(clientId);
    if(null == pgApplicationClient) {
      throw new ChatakPayException();
    }
    return pgApplicationClient;
  }

  @Override
  public PGApplicationClient getApplicationClientAuth(String authUser) throws ChatakPayException {
    PGApplicationClient pgApplicationClient = applicationClientDao.getApplicationClientAuth(authUser);
    if(null == pgApplicationClient) {
      throw new ChatakPayException();
    }
    return pgApplicationClient;
  }

}
