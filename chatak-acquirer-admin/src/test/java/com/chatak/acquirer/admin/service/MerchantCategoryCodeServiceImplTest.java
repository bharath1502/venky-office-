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
import org.springframework.context.MessageSource;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.MerchantCategoryCodeServiceImpl;
import com.chatak.pg.acq.dao.MerchantCategoryCodeDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.TransactionCategoryCodeDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantCategoryCode;
import com.chatak.pg.bean.TransactionCategoryCodeRequest;
import com.chatak.pg.model.MerchantCategoryCode;
import com.chatak.pg.user.bean.GetMerchantCategoryCodeListResponse;
import com.chatak.pg.user.bean.MerchantCategoryCodeRequest;

@RunWith(MockitoJUnitRunner.class)
public class MerchantCategoryCodeServiceImplTest {

	@InjectMocks
	MerchantCategoryCodeServiceImpl merchantCategoryCodeServiceImpl = new MerchantCategoryCodeServiceImpl();

	@Mock
	TransactionCategoryCodeDao tccDao;

	@Mock
	MerchantCategoryCodeDao mccDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	MessageSource messageSource;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Test
	public void testCreateMerchantCategoryCode() throws ChatakAdminException {
		MerchantCategoryCode mcc = new MerchantCategoryCode();
		merchantCategoryCodeServiceImpl.createMerchantCategoryCode(mcc);

	}

	@Test
	public void testSearchMerchantCategoryCode() throws ChatakAdminException {
		MerchantCategoryCode mcc = new MerchantCategoryCode();
		GetMerchantCategoryCodeListResponse getMCCSearchResponse = new GetMerchantCategoryCodeListResponse();
		List<PGMerchantCategoryCode> pgMCCs = new ArrayList<>();
		PGMerchantCategoryCode pgMCC = new PGMerchantCategoryCode();
		getMCCSearchResponse.setPgMCC(pgMCCs);
		pgMCCs.add(pgMCC);
		Mockito.when(mccDao.getMCCList(Matchers.any(MerchantCategoryCodeRequest.class)))
				.thenReturn(getMCCSearchResponse);
		merchantCategoryCodeServiceImpl.searchMerchantCategoryCode(mcc);

	}

	@Test
	public void testGetMCCDetails() throws ChatakAdminException {
		PGMerchantCategoryCode pgMCC = new PGMerchantCategoryCode();
		pgMCC.setSelectedTcc("abcd");
		Mockito.when(mccDao.findById(Matchers.anyLong())).thenReturn(pgMCC);
		merchantCategoryCodeServiceImpl.getMCCDetails(Long.parseLong("4234"));

	}

	@Test
	public void testUpdateMerchantCategoryCode() throws ChatakAdminException {
		MerchantCategoryCode mcc = new MerchantCategoryCode();
		merchantCategoryCodeServiceImpl.updateMerchantCategoryCode(mcc);
	}

	@Test
	public void testGetAllTCCs() throws ChatakAdminException {
		List<TransactionCategoryCodeRequest> tcc = new ArrayList<>();
		TransactionCategoryCodeRequest transactionCategoryCodeRequest = new TransactionCategoryCodeRequest();
		tcc.add(transactionCategoryCodeRequest);
		Mockito.when(tccDao.findAllTCC()).thenReturn(tcc);
		merchantCategoryCodeServiceImpl.getAllTCCs();
	}

	@Test
	public void testChangeMerchantCategoryCodeStatus() throws ChatakAdminException {
		MerchantCategoryCode mcc = new MerchantCategoryCode();
		PGMerchantCategoryCode pgMerchantCategoryCode = new PGMerchantCategoryCode();
		Mockito.when(mccDao.findById(Matchers.anyLong())).thenReturn(pgMerchantCategoryCode);
		merchantCategoryCodeServiceImpl.changeMerchantCategoryCodeStatus(mcc);
	}

	@Test
	public void testGetAllMCC() {
		merchantCategoryCodeServiceImpl.getAllMCC();
	}

	@Test
	public void testDeleteMcc() throws NumberFormatException, ChatakAdminException {
		PGMerchantCategoryCode merchantCategoryCode = new PGMerchantCategoryCode();
		List<PGMerchant> merchant = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.add(pgMerchant);
		Mockito.when(mccDao.findById(Matchers.anyLong())).thenReturn(merchantCategoryCode);
		Mockito.when(merchantUpdateDao.findByMcc(Matchers.anyString())).thenReturn(merchant);
		merchantCategoryCodeServiceImpl.deleteMcc(Long.parseLong("543"));
	}

	@Test
	public void testDeleteMccElse() throws NumberFormatException, ChatakAdminException {
		PGMerchantCategoryCode merchantCategoryCode = new PGMerchantCategoryCode();
		List<PGMerchant> merchant = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.add(pgMerchant);
		Mockito.when(mccDao.findById(Matchers.anyLong())).thenReturn(merchantCategoryCode);
		merchantCategoryCodeServiceImpl.deleteMcc(Long.parseLong("543"));
	}

	@Test(expected = ChatakAdminException.class)
	public void testDeleteMccException() throws NumberFormatException, ChatakAdminException {
		List<PGMerchant> merchant = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.add(pgMerchant);
		Mockito.when(mccDao.findById(Matchers.anyLong())).thenThrow(new NullPointerException());
		merchantCategoryCodeServiceImpl.deleteMcc(Long.parseLong("543"));

	}

}
