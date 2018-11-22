package com.chatak.acquirer.admin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.BinServiceImpl;
import com.chatak.pg.acq.dao.BINDao;
import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.model.BinDTO;

@RunWith(MockitoJUnitRunner.class)
public class BinServiceImplTest {

	@InjectMocks
	BinServiceImpl binServiceImpl = new BinServiceImpl();

	@Mock
	private BINDao binDao;

	@Test
	public void testSearchBin() throws ChatakAdminException {
		BinDTO binDTO = new BinDTO();
		binServiceImpl.searchBin(binDTO);
	}

	@Test
	public void testSaveOrUpdateBin() throws ChatakAdminException {
		PGBINRange pgbinRange = new PGBINRange();
		BinDTO binDTO = new BinDTO();
		binDTO.setStatus("Active");
		binDTO.setBinNumber(1);
		binDTO.setSwitchId(1);
		Mockito.when(binDao.saveBin(Matchers.any(PGBINRange.class))).thenReturn(pgbinRange);
		binServiceImpl.saveOrUpdateBin(binDTO);
	}

	@Test
	public void testSaveOrUpdateBinSuspended() throws ChatakAdminException {
		PGBINRange pgbinRange = new PGBINRange();
		BinDTO binDTO = new BinDTO();
		binDTO.setStatus("Suspended");
		binDTO.setBinNumber(1);
		binDTO.setSwitchId(1);
		Mockito.when(binDao.saveBin(Matchers.any(PGBINRange.class))).thenReturn(pgbinRange);
		binServiceImpl.saveOrUpdateBin(binDTO);
	}

	@Test
	public void testSaveOrUpdateBinDeleted() throws ChatakAdminException {
		PGBINRange pgbinRange = new PGBINRange();
		BinDTO binDTO = new BinDTO();
		binDTO.setStatus("Deleted");
		binDTO.setBinNumber(1);
		binDTO.setSwitchId(1);
		Mockito.when(binDao.saveBin(Matchers.any(PGBINRange.class))).thenReturn(pgbinRange);
		binServiceImpl.saveOrUpdateBin(binDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSaveOrUpdateBinException() throws ChatakAdminException {
		BinDTO binDTO = new BinDTO();
		binServiceImpl.saveOrUpdateBin(binDTO);
	}

	@Test
	public void testChangeBinStatus() throws ChatakAdminException {
		PGBINRange pgbinRange = new PGBINRange();
		BinDTO binDTO = new BinDTO();
		binDTO.setStatus("Active");
		Mockito.when(binDao.findByBinId(Matchers.anyLong())).thenReturn(pgbinRange);
		binServiceImpl.changeBinStatus(binDTO);
	}

	@Test
	public void testChangeBinStatusElse() throws ChatakAdminException {
		PGBINRange pgbinRange = new PGBINRange();
		BinDTO binDTO = new BinDTO();
		Mockito.when(binDao.findByBinId(Matchers.anyLong())).thenReturn(pgbinRange);
		Mockito.when(binDao.createOrUpdateBin(Matchers.any(PGBINRange.class))).thenReturn(pgbinRange);
		binServiceImpl.changeBinStatus(binDTO);
	}

	@Test
	public void testSearchBinById() throws ChatakAdminException {
		binServiceImpl.searchBinById(Long.parseLong("432"));
	}

	@Test
	public void testSearchBins() throws ChatakAdminException {
		BinDTO binDTO = new BinDTO();
		binServiceImpl.searchBins(binDTO);
	}

	@Test
	public void testValidateBin() throws ChatakAdminException {
		binServiceImpl.validateBin(Long.parseLong("54"));
	}

}
