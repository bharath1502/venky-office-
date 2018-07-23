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

import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.acq.dao.repository.BINRepository;
import com.chatak.pg.model.BinDTO;

/**
 * @Author: Girmiti Software
 * @Date: 17-Feb-2018
 * @Time: 12:08:15 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BINDaoImplTest {

    public static final Long ID = Long.parseLong("12");
    public static final Long BIN = Long.parseLong("12");
    public static final Long SWITCH_ID = Long.parseLong("12");
    public static final Integer STATUS = Integer.parseInt("0");
    public static final Integer DCC_SUPPORTED = Integer.parseInt("10");
    public static final Integer EMV_SUPPORTED = Integer.parseInt("10");
    public static final String SWITCH_NAME = "switchName";
  
	@InjectMocks
	BINDaoImpl bINDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	BINRepository binRepository;

	@Test
	public void testContainsBin() {
		bINDaoImpl.containsBin(Long.parseLong("123"));
	}

	@Test
	public void testGetAllBins() {
		List<PGBINRange> list = new ArrayList<>();
		PGBINRange pgbinRange = new PGBINRange();
		pgbinRange.setBin(Long.parseLong("12"));
		pgbinRange.setSwitchId(Long.parseLong("13"));
		pgbinRange.setStatus(0);
		list.add(pgbinRange);
		Mockito.when(binRepository.findAll()).thenReturn(list);
		bINDaoImpl.getAllBins();
	}

	@Test
	public void testGetAllBinsElse() {
		List<PGBINRange> list = new ArrayList<>();
		PGBINRange pgbinRange = new PGBINRange();
		pgbinRange.setSwitchId(Long.parseLong("13"));
		pgbinRange.setBin(Long.parseLong("12"));
		pgbinRange.setStatus(1);
		list.add(pgbinRange);
		Mockito.when(binRepository.findAll()).thenReturn(list);
		bINDaoImpl.getAllBins();
	}

	@Test
	public void testGetAllActiveBins() {
		List<PGBINRange> list = new ArrayList<>();
		PGBINRange pgbinRange = new PGBINRange();
		list.add(pgbinRange);
		Mockito.when(binRepository.getAllActiveBins()).thenReturn(list);
		bINDaoImpl.getAllActiveBins();
	}

	@Test
	public void testSaveBin() {
		PGBINRange pgbinRange = new PGBINRange();
		Mockito.when(binRepository.findByBin(Matchers.anyLong())).thenReturn(pgbinRange);
		bINDaoImpl.saveBin(pgbinRange);
	}

	@Test
	public void testSaveBinElse() {
		PGBINRange pgbinRange = new PGBINRange();
		bINDaoImpl.saveBin(pgbinRange);
	}

	@Test
	public void testSaveBinException() {
		PGBINRange pgbinRange = new PGBINRange();
		Mockito.when(binRepository.findByBin(Matchers.anyLong())).thenThrow(new NullPointerException());
		bINDaoImpl.saveBin(pgbinRange);
	}

	@Test
	public void testFindById() {
		PGBINRange pgbinRange = new PGBINRange();
		pgbinRange.setBin(Long.parseLong("12"));
		pgbinRange.setStatus(0);
		pgbinRange.setSwitchId(Long.parseLong("13"));
		Mockito.when(binRepository.findById(Matchers.anyLong())).thenReturn(pgbinRange);
		bINDaoImpl.findById(Long.parseLong("12"));
	}

	@Test
	public void testFindByIdElse() {
		PGBINRange pgbinRange = new PGBINRange();
		pgbinRange.setStatus(1);
		pgbinRange.setBin(Long.parseLong("12"));
		pgbinRange.setSwitchId(Long.parseLong("13"));
		Mockito.when(binRepository.findById(Matchers.anyLong())).thenReturn(pgbinRange);
		bINDaoImpl.findById(Long.parseLong("12"));
	}

	@Test
	public void testSearchBin() {
		List<BinDTO> binList = new ArrayList<>();
		BinDTO binDTO = new BinDTO();
		binDTO.setPageIndex(null);
		binDTO.setNoOfRecords(1);
		binList.add(binDTO);
		List<Object> tuplelist = new ArrayList<>();
		Object[] objects = new Object[Integer.parseInt("7")];
		objects[Integer.parseInt("0")] = ID;
		objects[Integer.parseInt("1")] = BIN;
		objects[Integer.parseInt("2")] = STATUS;
		objects[Integer.parseInt("3")] = SWITCH_ID;
		objects[Integer.parseInt("4")] = DCC_SUPPORTED;
		objects[Integer.parseInt("5")] = EMV_SUPPORTED;
		objects[Integer.parseInt("6")] = SWITCH_NAME;
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(binList, tuplelist);

		bINDaoImpl.searchBin(binDTO);
	}

	@Test
	public void testSearchBinElse() {
		BinDTO binDTO = new BinDTO();
		bINDaoImpl.searchBin(binDTO);
	}

	@Test
	public void testGetUserByBin() {
		PGBINRange pgbinRange = new PGBINRange();
		pgbinRange.setStatus(Integer.parseInt("3"));
		Mockito.when(binRepository.findByBin(Matchers.anyLong())).thenReturn(pgbinRange);
		bINDaoImpl.getUserByBin(Long.parseLong("12"));
	}

	@Test
	public void testGetUserByBinElse() {
		PGBINRange pgbinRange = new PGBINRange();
		Mockito.when(binRepository.findByBin(Matchers.anyLong())).thenReturn(pgbinRange);
		bINDaoImpl.getUserByBin(Long.parseLong("12"));
	}

	@Test
	public void testGetUserByBinNull() {
		bINDaoImpl.getUserByBin(Long.parseLong("12"));
	}

	@Test
	public void testFindByBinId() {
		bINDaoImpl.findByBinId(Long.parseLong("12"));
	}

	@Test
	public void testCreateOrUpdateBin() {
		PGBINRange pGBINRange = new PGBINRange();
		bINDaoImpl.createOrUpdateBin(pGBINRange);
	}

}
