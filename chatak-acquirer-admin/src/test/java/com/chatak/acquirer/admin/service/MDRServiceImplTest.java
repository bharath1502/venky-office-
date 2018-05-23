package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.MDRServiceImpl;
import com.chatak.pg.acq.dao.MDRDao;
import com.chatak.pg.acq.dao.model.PGDynamicMDR;
import com.chatak.pg.model.DynamicMDRDTO;

@RunWith(MockitoJUnitRunner.class)
public class MDRServiceImplTest {

	@InjectMocks
	MDRServiceImpl mdRServiceImpl = new MDRServiceImpl();

	@Mock
	MDRDao mdrDao;

	@Test
	public void testSaveOrUpdateDynamicMDR() throws ChatakAdminException {
		DynamicMDRDTO dynamicMDRDTO = new DynamicMDRDTO();
		PGDynamicMDR pgDynamicMDRResponse = new PGDynamicMDR();
		dynamicMDRDTO.setSlab("543");
		Mockito.when(mdrDao.saveDynamicMDR(Matchers.any(PGDynamicMDR.class))).thenReturn(pgDynamicMDRResponse);
		mdRServiceImpl.saveOrUpdateDynamicMDR(dynamicMDRDTO);
	}

	@Test
	public void testSaveOrUpdateDynamicMDRElse() throws ChatakAdminException {
		DynamicMDRDTO dynamicMDRDTO = new DynamicMDRDTO();
		dynamicMDRDTO.setSlab("543");
		mdRServiceImpl.saveOrUpdateDynamicMDR(dynamicMDRDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSaveOrUpdateDynamicMDRException() throws ChatakAdminException {
		DynamicMDRDTO dynamicMDRDTO = new DynamicMDRDTO();
		Mockito.when(mdrDao.saveDynamicMDR(Matchers.any(PGDynamicMDR.class))).thenThrow(new NullPointerException());
		mdRServiceImpl.saveOrUpdateDynamicMDR(dynamicMDRDTO);
	}

	@Test
	public void testSearchMDRById() throws ChatakAdminException {
		mdRServiceImpl.searchMDRById(Long.parseLong("5345"));
	}

	@Test(expected = NullPointerException.class)
	public void testSearchDynamicMDR() throws ChatakAdminException {
		List<PGDynamicMDR> pgMDRDynamic = new ArrayList<>();
		PGDynamicMDR pgDynamicMDR = new PGDynamicMDR();
		List<DynamicMDRDTO> dynamicMDRDTOs = new ArrayList<>();
		DynamicMDRDTO dynamicMDRDTO = new DynamicMDRDTO();
		dynamicMDRDTO.setSlab("abcd");
		pgMDRDynamic.add(pgDynamicMDR);
		dynamicMDRDTOs.add(dynamicMDRDTO);
		Mockito.when(mdrDao.searchDynamicMDR(Matchers.any(DynamicMDRDTO.class))).thenReturn(pgMDRDynamic);
		mdRServiceImpl.searchDynamicMDR(dynamicMDRDTO);
	}

	@Test
	public void testSearchDynamicMDRResponse() throws ChatakAdminException {
		List<PGDynamicMDR> pgMDRDynamic = new ArrayList<>();
		PGDynamicMDR pgDynamicMDR = new PGDynamicMDR();
		DynamicMDRDTO dynamicMDRDTO = new DynamicMDRDTO();
		pgMDRDynamic.add(pgDynamicMDR);
		mdRServiceImpl.searchDynamicMDR(dynamicMDRDTO);
	}

}
