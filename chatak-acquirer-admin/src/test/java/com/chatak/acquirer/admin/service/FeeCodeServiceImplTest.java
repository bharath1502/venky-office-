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
import com.chatak.acquirer.admin.service.impl.FeeCodeServiceImpl;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AcquirerFeeCodeDao;
import com.chatak.pg.acq.dao.FeeCodeDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerFeeCodeDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;
import com.chatak.pg.acq.dao.model.PGFeeCode;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;
import com.chatak.pg.model.AcquirerFeeCodeDTO;
import com.chatak.pg.model.PartnerFeeCodeDTO;

@RunWith(MockitoJUnitRunner.class)
public class FeeCodeServiceImplTest {

	@InjectMocks
	FeeCodeServiceImpl feeCodeServiceImpl = new FeeCodeServiceImpl();

	@Mock
	FeeCodeDao feeCodeDao;

	@Mock
	PartnerFeeCodeDao partnerFeeCodeDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	AccountDao accountDao;

	@Mock
	AcquirerFeeCodeDao acquirerFeeCodeDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Test
	public void testGetAllFeeCodeList() throws ChatakAdminException {
		List<PGFeeCode> feeCode =new ArrayList<>();
		PGFeeCode code=new PGFeeCode();
		feeCode.add(code);
		Mockito.when(feeCodeDao.getAllFeeCodeList()).thenReturn(feeCode);
		feeCodeServiceImpl.getAllFeeCodeList();
	}
	
	@Test
	public void testGetAllFeeCodeListException() throws ChatakAdminException {
		Mockito.when(feeCodeDao.getAllFeeCodeList()).thenThrow(new NullPointerException());
		feeCodeServiceImpl.getAllFeeCodeList();
	}
	
	
	@Test
	public void testGetAllPartnerFeeCodeList() throws ChatakAdminException {
		List<PGPartnerFeeCode> pgPartnerFeeCodeList=new ArrayList<>();
		PGPartnerFeeCode code=new PGPartnerFeeCode();
		pgPartnerFeeCodeList.add(code);
		Mockito.when(partnerFeeCodeDao.getAllFeeCodes()).thenReturn(pgPartnerFeeCodeList);
		feeCodeServiceImpl.getAllPartnerFeeCodeList();
	}
	
	@Test
	public void testGetPartnerFeeCodeByEntityId() throws ChatakAdminException {
		 PGPartnerFeeCode pgPartnerFeeCode=new PGPartnerFeeCode();
		 Mockito.when(partnerFeeCodeDao.getPartnerFeeCodeByEntityId(Matchers.anyString())).thenReturn(pgPartnerFeeCode);
		feeCodeServiceImpl.getPartnerFeeCodeByEntityId("543");
	}
	
	@Test
	public void testAddPartnerFeeCode() throws ChatakAdminException {
		PartnerFeeCodeDTO partnerFeeCodeDTO=new PartnerFeeCodeDTO();
		feeCodeServiceImpl.addPartnerFeeCode(partnerFeeCodeDTO);
	}

	@Test
	public void testValidatePartnerFeeCode() throws ChatakAdminException {
		PGMerchant pgMerchant = new PGMerchant();
		PGPartnerFeeCode value=new PGPartnerFeeCode();
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(partnerFeeCodeDao.getPartnerFeeCodeByEntityId(Matchers.anyString())).thenReturn(value);
		feeCodeServiceImpl.validatePartnerFeeCode("543");
	}
	@Test
	public void testValidatePartnerFeeCodeNull() throws ChatakAdminException {
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount=new PGAccount();
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		feeCodeServiceImpl.validatePartnerFeeCode("543");
	}
	
	@Test
	public void testGetAllAcquirerFeeCodes() throws ChatakAdminException {
		feeCodeServiceImpl.getAllAcquirerFeeCodes();
	}
	
	@Test
	public void testUpdatePartnerFeeCode() throws ChatakAdminException {
		PartnerFeeCodeDTO partnerFeeCodeDTO=new PartnerFeeCodeDTO();
		PGPartnerFeeCode pgPartnerFeeCode =new PGPartnerFeeCode();
		partnerFeeCodeDTO.setMerchantCode("52547");
		partnerFeeCodeDTO.setFlatFee(Long.parseLong("877"));
		Mockito.when(partnerFeeCodeDao.getPartnerFeeCodeByEntityId(Matchers.anyString())).thenReturn(pgPartnerFeeCode);
		feeCodeServiceImpl.updatePartnerFeeCode(partnerFeeCodeDTO);
	}
	@Test(expected=ChatakAdminException.class)
	public void testUpdatePartnerFeeCodeElse() throws ChatakAdminException {
		PartnerFeeCodeDTO partnerFeeCodeDTO=new PartnerFeeCodeDTO();
		feeCodeServiceImpl.updatePartnerFeeCode(partnerFeeCodeDTO);
	}
	
	@Test
	public void testGetPartnerFeeCodeByAccountNumber() throws ChatakAdminException {
		PGPartnerFeeCode pgPartnerFeeCode =new PGPartnerFeeCode();
		pgPartnerFeeCode.setFlatFee(Long.parseLong("877"));
		Mockito.when(partnerFeeCodeDao.getPartnerFeeCodeByAccountNumber(Matchers.anyLong())).thenReturn(pgPartnerFeeCode);
		feeCodeServiceImpl.getPartnerFeeCodeByAccountNumber(Long.parseLong("53"));
	}
	
	@Test
	public void testGetAcquirerFeeCodeByID() throws ChatakAdminException {
		PGAcquirerFeeCode pgAcquirerFeeCode =new PGAcquirerFeeCode();
		pgAcquirerFeeCode.setFlatFee(Double.parseDouble("877"));
		Mockito.when(acquirerFeeCodeDao.getAcquirerFeeCodeByFeeCodeId(Matchers.anyLong())).thenReturn(pgAcquirerFeeCode);
		feeCodeServiceImpl.getAcquirerFeeCodeByID(Long.parseLong("53"));
	}
	
	@Test
	public void testUpdateAcquirerFeeCode() throws ChatakAdminException {
		AcquirerFeeCodeDTO pgAcquirerFeeCodeDTO=new AcquirerFeeCodeDTO();
		pgAcquirerFeeCodeDTO.setFlatFee(Double.parseDouble("877"));
		feeCodeServiceImpl.updateAcquirerFeeCode(pgAcquirerFeeCodeDTO);
	}
	
	@Test
	public void testGetAllAcquirerFeeCodesByPartnerId() throws ChatakAdminException {
		feeCodeServiceImpl.getAllAcquirerFeeCodesByPartnerId(Long.parseLong("53"));
	}
	
	@Test
	public void testFindByPartnerIdAndMerchantCode() throws ChatakAdminException {
		List<PGAcquirerFeeCode> pgAcquirerFeeCodeList =new ArrayList<>();
		PGAcquirerFeeCode acquirerFeeCode=new PGAcquirerFeeCode();
		pgAcquirerFeeCodeList.add(acquirerFeeCode);
		acquirerFeeCode.setFlatFee(Double.parseDouble("877"));
		Mockito.when(acquirerFeeCodeDao.getFeeCodesByPartnerIdAndMerchantCode(Matchers.anyLong(),Matchers.anyString())).thenReturn(pgAcquirerFeeCodeList);
		feeCodeServiceImpl.findByPartnerIdAndMerchantCode(Long.parseLong("53"),"6878");
	}
	
	@Test
	public void testPopulateAcquirerFee() throws ChatakAdminException {
		List<PGAcquirerFeeCode> acquirerFeeCodes=new ArrayList<>();
		PGAcquirerFeeCode pgAcquirerFeeCode=new PGAcquirerFeeCode();
		acquirerFeeCodes.add(pgAcquirerFeeCode);
		pgAcquirerFeeCode.setFlatFee(Double.parseDouble("877"));
		feeCodeServiceImpl.populateAcquirerFee(acquirerFeeCodes);
	}
	
	@Test
	public void testAddAcquirerFeeCode() throws ChatakAdminException{
		AcquirerFeeCodeDTO pgAcquirerFeeCode=new AcquirerFeeCodeDTO();
		pgAcquirerFeeCode.setFlatFee(Double.parseDouble("877"));
		feeCodeServiceImpl.addAcquirerFeeCode(pgAcquirerFeeCode);
	}
	
	@Test
	public void testGetAcquirerFeeByMerchantCode() throws ChatakAdminException {
		feeCodeServiceImpl.getAcquirerFeeByMerchantCode("53");
	}
	
	
}
