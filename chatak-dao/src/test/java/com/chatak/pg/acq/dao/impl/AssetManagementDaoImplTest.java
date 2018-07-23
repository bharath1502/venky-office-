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

import com.chatak.pg.acq.dao.model.PGPosDevice;
import com.chatak.pg.acq.dao.repository.AssetManagementRepository;
import com.chatak.pg.model.PosDeviceDTO;

/**
 * @Author: Girmiti Software
 * @Date: 16-Feb-2018
 * @Time: 10:28:39 am
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AssetManagementDaoImplTest {

	@InjectMocks
	AssetManagementDaoImpl assetManagementDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	private AssetManagementRepository assetManagementRepository;

	@Test
	public void testCreateOrUpdateDevice() {
		PGPosDevice device = new PGPosDevice();
		assetManagementDaoImpl.createOrUpdateDevice(device);
	}

	@Test
	public void testSearchAssetDevice() {
		List<PosDeviceDTO> deviceDTOs;
		PosDeviceDTO deviceTO = new PosDeviceDTO();
		deviceTO.setPageIndex(1);
		deviceTO.setPageSize(Integer.parseInt("25"));

		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("7")];
		objects[0] = "name";
		objects[1] = "name";
		objects[Integer.parseInt("2")] = "name";
		objects[Integer.parseInt("3")] = "name";
		objects[Integer.parseInt("4")] = "phone";
		objects[Integer.parseInt("5")] = "userName";
		objects[Integer.parseInt("6")] = Integer.valueOf("10");

		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		assetManagementDaoImpl.searchAssetDevice(deviceTO);
	}

	@Test
	public void testFindByDeviceId() {
		assetManagementDaoImpl.findByDeviceId(Long.parseLong("543"));
	}

	@Test
	public void testFindByDeviceSerialNoIgnoreCase() {
		assetManagementDaoImpl.findByDeviceSerialNoIgnoreCase("abcd");
	}

	@Test
	public void testDeleteAssetDevice() {
		PGPosDevice posDeviceDb = new PGPosDevice();
		Mockito.when(assetManagementRepository.findById(Matchers.anyLong())).thenReturn(posDeviceDb);
		assetManagementDaoImpl.deleteAssetDevice(Long.parseLong("543"));
	}

	@Test
	public void testDeleteAssetDeviceElse() {
		assetManagementDaoImpl.deleteAssetDevice(Long.parseLong("543"));
	}

}
