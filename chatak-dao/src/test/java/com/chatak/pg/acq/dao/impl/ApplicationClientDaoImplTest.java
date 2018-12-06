/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.repository.ApplicationClientRepository;

/**
 * @Author: Girmiti Software
 * @Date: 15-Feb-2018
 * @Time: 5:41:04 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationClientDaoImplTest {

	@InjectMocks
	ApplicationClientDaoImpl applicationClientDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	ApplicationClientRepository applicationClientRepository;

	@Test
	public void testGetApplicationClient() {
		List<PGApplicationClient> applicationClients = new ArrayList<>();
		PGApplicationClient applicationClient = new PGApplicationClient();
		applicationClients.add(applicationClient);
		Mockito.when(applicationClientRepository.findByAppClientIdAndAppClientAccess(Matchers.anyString(),
				Matchers.anyString())).thenReturn(applicationClients);
		applicationClientDaoImpl.getApplicationClient("123", "234");
	}

	@Test
	public void testGetApplicationClientNull() {
		applicationClientDaoImpl.getApplicationClient("123", "234");
	}

	@Test
	public void testGetApplicationClientAuth() {
		List<PGApplicationClient> applicationClients = new ArrayList<>();
		PGApplicationClient applicationClient = new PGApplicationClient();
		applicationClients.add(applicationClient);
		Mockito.when(
				applicationClientRepository.findByAppAuthUserAndAppAuthPass(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(applicationClients);
		applicationClientDaoImpl.getApplicationClientAuth("123", "234");
	}

	@Test
	public void testGetApplicationClientAuthNull() {
		applicationClientDaoImpl.getApplicationClientAuth("123", "234");
	}

	@Test
	public void testGetApplicationClients() {
		List<PGApplicationClient> applicationClients = new ArrayList<>();
		PGApplicationClient applicationClient = new PGApplicationClient();
		applicationClients.add(applicationClient);
		Mockito.when(applicationClientRepository.findByAppClientId(Matchers.anyString()))
				.thenReturn(applicationClients);
		applicationClientDaoImpl.getApplicationClient("123");
	}

	@Test
	public void testGetApplicationClientsNull() {
		applicationClientDaoImpl.getApplicationClient("123");
	}

	@Test
	public void testGetApplicationClientAuths() {
		List<PGApplicationClient> applicationClients = new ArrayList<>();
		PGApplicationClient applicationClient = new PGApplicationClient();
		applicationClients.add(applicationClient);
		Mockito.when(applicationClientRepository.findByAppAuthUser(Matchers.anyString()))
				.thenReturn(applicationClients);
		applicationClientDaoImpl.getApplicationClientAuth("123");
	}

	@Test
	public void testGetApplicationClientAuthsNull() {
		applicationClientDaoImpl.getApplicationClientAuth("123");
	}

}
