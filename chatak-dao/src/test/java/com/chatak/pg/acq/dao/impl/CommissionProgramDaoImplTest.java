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

import com.chatak.pg.acq.dao.model.PGCommission;
import com.chatak.pg.acq.dao.model.PGOtherCommission;
import com.chatak.pg.acq.dao.repository.CommissionProgramRepository;
import com.chatak.pg.acq.dao.repository.OtherCommissionRepository;
import com.chatak.pg.model.CommissionDTO;

/**
 * @Author: Girmiti Software
 * @Date: 16-Feb-2018
 * @Time: 6:48:51 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommissionProgramDaoImplTest {

	@InjectMocks
	CommissionProgramDaoImpl commissionProgramDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	private CommissionProgramRepository commissionProgramRepository;

	@Mock
	private OtherCommissionRepository otherCommissionRepository;

	@Test
	public void testCreateCommissionProgram() {
		PGCommission commissionDaoDetails = new PGCommission();
		commissionProgramDaoImpl.createCommissionProgram(commissionDaoDetails);
	}

	@Test(expected=ClassCastException.class)
	public void testSearchCommissionProgram() {
		CommissionDTO commissionDTO = new CommissionDTO();
		List<PGCommission> commissionDTORequestList = new ArrayList<>();
		PGCommission commission = new PGCommission();
		commissionDTORequestList.add(commission);
		commissionDTO.setPageIndex(null);
		commissionDTO.setPageSize(null);
		commissionDTO.setStatus("abc");
		commissionDTO.setCommissionName("123");
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("13")];
		objects[0] = "name";
		objects[1] = "name";
		objects[Integer.parseInt("2")] = "name";
		objects[Integer.parseInt("3")] = "name";
		objects[Integer.parseInt("4")] = "phone";
		objects[Integer.parseInt("5")] = "name";
		objects[Integer.parseInt("6")] = "status";
		objects[Integer.parseInt("7")] = "status";
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(commissionDTORequestList, tuplelist);
		commissionProgramDaoImpl.searchCommissionProgram(commissionDTO);
	}

	@Test
	public void testGetByCommProgramId() {
		commissionProgramDaoImpl.getByCommProgramId(Long.parseLong("123"));
	}

	@Test
	public void testFindByCommissionName() {
		commissionProgramDaoImpl.findByCommissionName("abc");
	}

	@Test
	public void testGetOtherCommissionByCommissionProgramId() {
		commissionProgramDaoImpl.getOtherCommissionByCommissionProgramId(Long.parseLong("123"));
	}

	@Test
	public void testRemoveOthCommission() {
		List<PGOtherCommission> othCommission = new ArrayList<>();
		commissionProgramDaoImpl.removeOthCommission(othCommission);
	}

}
