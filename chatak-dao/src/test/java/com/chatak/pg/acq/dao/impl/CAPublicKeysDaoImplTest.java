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

import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.acq.dao.repository.CAPublicKeysRepository;
import com.chatak.pg.model.CAPublicKeysDTO;

/**
 * @Author: Girmiti Software
 * @Date: 17-Feb-2018
 * @Time: 2:42:12 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CAPublicKeysDaoImplTest {

  public static final Long ID = Long.parseLong("23");
  public static final String PUBLIC_KEY_NAME = "publicKeyName";
  public static final String PUBLIC_KEY_INDEX = "publicKeyIndex";
  public static final String PUBLIC_KEY_MODULUS = "publicKeyModulus";
  public static final String R_ID = "rId";
  public static final String EXPIRY_DATE = "expiryDate";
  public static final String STATUS = "status";
  
	@InjectMocks
	CAPublicKeysDaoImpl cAPublicKeysDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	CAPublicKeysRepository caPublickeysRepository;

	@Test
	public void testSearchCAPublicKeys() {
		List<CAPublicKeysDTO> caPublicKeysRequestList = new ArrayList<>();
		CAPublicKeysDTO caPublicKeysDTO = new CAPublicKeysDTO();
		caPublicKeysDTO.setPageIndex(null);
		caPublicKeysDTO.setNoOfRecords(1);
		caPublicKeysRequestList.add(caPublicKeysDTO);
		List<Object> tuplelist = new ArrayList<>();
		Object[] objects = new Object[Integer.parseInt("8")];
		objects[Integer.parseInt("0")] = PUBLIC_KEY_NAME;
		objects[Integer.parseInt("1")] = PUBLIC_KEY_INDEX;
		objects[Integer.parseInt("2")] = PUBLIC_KEY_MODULUS;
		objects[Integer.parseInt("3")] = ID;
		objects[Integer.parseInt("4")] = R_ID;
		objects[Integer.parseInt("5")] = EXPIRY_DATE;
		objects[Integer.parseInt("6")] = STATUS;
		objects[Integer.parseInt("7")] = ID;

		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(caPublicKeysRequestList, tuplelist);

		cAPublicKeysDaoImpl.searchCAPublicKeys(caPublicKeysDTO);
	}

	@Test
	public void testCreateCAPublicKeys() {
		PGCaPublicKeys caPublicKeysDaoDetails = new PGCaPublicKeys();
		cAPublicKeysDaoImpl.createCAPublicKeys(caPublicKeysDaoDetails);
	}

	@Test
	public void testUpdateCAPublicKeys() {
		PGCaPublicKeys caPublicKeysDaoDetails = new PGCaPublicKeys();
		Mockito.when(caPublickeysRepository.findByPublicKeyId(Matchers.anyLong())).thenReturn(caPublicKeysDaoDetails);
		cAPublicKeysDaoImpl.updateCAPublicKeys(caPublicKeysDaoDetails);
	}

	@Test
	public void testUpdateCAPublicKeysNull() {
		PGCaPublicKeys caPublicKeysDaoDetails = new PGCaPublicKeys();
		cAPublicKeysDaoImpl.updateCAPublicKeys(caPublicKeysDaoDetails);
	}

	@Test
	public void testCaPublicKeysById() {
		cAPublicKeysDaoImpl.caPublicKeysById(Long.parseLong("23"));
	}

	@Test
	public void testGetpublicKeyName() {
		cAPublicKeysDaoImpl.getpublicKeyName("abcd");
	}

	@Test
	public void testSaveCAPublicKey() {
		PGCaPublicKeys pgCaPublicKey = new PGCaPublicKeys();
		cAPublicKeysDaoImpl.saveCAPublicKey(pgCaPublicKey);
	}

	@Test
	public void testFindByPublicKeyId() {
		PGCaPublicKeys pgCaPublicKeys = new PGCaPublicKeys();
		pgCaPublicKeys.setStatus("0");
		Mockito.when(caPublickeysRepository.findByPublicKeyId(Matchers.anyLong())).thenReturn(pgCaPublicKeys);
		cAPublicKeysDaoImpl.findByPublicKeyId(Long.parseLong("23"));
	}

}
