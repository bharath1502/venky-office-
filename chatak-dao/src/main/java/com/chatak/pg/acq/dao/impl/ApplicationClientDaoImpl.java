package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ApplicationClientDao;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.repository.ApplicationClientRepository;
import com.chatak.pg.dao.util.StringUtil;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 06-Mar-2015 10:06:47 AM
 * @version 1.0
 */
@Repository("applicationClientDao")
public class ApplicationClientDaoImpl implements ApplicationClientDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ApplicationClientRepository applicationClientRepository;

  @Override
  public PGApplicationClient getApplicationClient(String clientId, String clientAccess) {
    List<PGApplicationClient> applicationClients = applicationClientRepository.findByAppClientIdAndAppClientAccess(clientId, clientAccess);
    if(StringUtil.isListNotNullNEmpty(applicationClients)) {
      return applicationClients.get(0);
    }
    return null;
  }

  @Override
  public PGApplicationClient getApplicationClientAuth(String authUser, String authPassword) {
    List<PGApplicationClient> applicationClients = applicationClientRepository.findByAppAuthUserAndAppAuthPass(authUser, authPassword);
    if(StringUtil.isListNotNullNEmpty(applicationClients)) {
      return applicationClients.get(0);
    }
    return null;
  }
  
  @Override
  public PGApplicationClient getApplicationClient(String clientId) {
    List<PGApplicationClient> applicationClients = applicationClientRepository.findByAppClientId(clientId);
    if(StringUtil.isListNotNullNEmpty(applicationClients)) {
      return applicationClients.get(0);
    }
    return null;
  }

  @Override
  public PGApplicationClient getApplicationClientAuth(String authUser) {
    List<PGApplicationClient> applicationClients = applicationClientRepository.findByAppAuthUser(authUser);
    if(StringUtil.isListNotNullNEmpty(applicationClients)) {
      return applicationClients.get(0);
    }
    return null;
  }
	
}
