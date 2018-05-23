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
import com.chatak.acquirer.admin.service.impl.FeeProgramServiceImpl;
import com.chatak.pg.acq.dao.AcquirerFeeValueDao;
import com.chatak.pg.acq.dao.FeeCodeDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOtherFeeValue;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.model.FeeProgramDTO;
import com.chatak.pg.model.FeeValue;
import com.chatak.pg.model.OtherFeesDTO;

@RunWith(MockitoJUnitRunner.class)
public class FeeProgramServiceImplTest {

	@InjectMocks
	FeeProgramServiceImpl feeProgramServiceImpl = new FeeProgramServiceImpl();

	@Mock
	FeeProgramDao feeProgramDao;

	@Mock
	FeeCodeDao feeCodeDao;

	@Mock
	AcquirerFeeValueDao acquirerFeeValueDao;

	@Mock
	AccountRepository accountRepository;

	@Mock
	MerchantRepository merchantRepository;

	@Test
	public void testCreateFeeProgram() throws ChatakAdminException {
		List<PGAcquirerFeeValue> feeProgramValueDaoDetails = new ArrayList<>();
		List<FeeValue> feeValues = new ArrayList<>();
		FeeValue feeValue = new FeeValue();
		feeValues.add(feeValue);
		PGAcquirerFeeValue acquirerFeeValue = new PGAcquirerFeeValue();
		feeProgramValueDaoDetails.add(acquirerFeeValue);
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		OtherFeesDTO otherFeesDTO = new OtherFeesDTO();
		feeProgramDTO.setFeeValueList(feeValues);
		feeValue.setFeePercentage("456");
		feeValue.setAccountNumber("OTH");
		feeProgramDTO.setOtherFee(otherFeesDTO);
		feeProgramServiceImpl.createFeeProgram(feeProgramDTO);
	}

	@Test
	public void testCreateFeeProgramException() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		feeProgramServiceImpl.createFeeProgram(feeProgramDTO);
	}

	@Test
	public void testSearchFeeProgramForJpa() throws ChatakAdminException {
		List<FeeProgramDTO> feeProgramRequestResponseList = new ArrayList<>();
		FeeProgramDTO dto = new FeeProgramDTO();
		feeProgramRequestResponseList.add(dto);
		Mockito.when(feeProgramDao.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
				.thenReturn(feeProgramRequestResponseList);
		feeProgramServiceImpl.searchFeeProgramForJpa(dto);
	}

	@Test
	public void testSearchFeeProgramForJpaException() throws ChatakAdminException {
		List<FeeProgramDTO> feeProgramRequestResponseList = new ArrayList<>();
		FeeProgramDTO dto = new FeeProgramDTO();
		feeProgramRequestResponseList.add(dto);
		Mockito.when(feeProgramDao.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
				.thenThrow(new NullPointerException());
		feeProgramServiceImpl.searchFeeProgramForJpa(dto);
	}

	@Test
	public void testGetFeeProgramDetails() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		List<PGFeeProgram> feeProgramModel = new ArrayList<>();
		PGFeeProgram pgFeeProgram = new PGFeeProgram();
		PGOtherFeeValue pgOtherFeeValue = new PGOtherFeeValue();
		feeProgramModel.add(pgFeeProgram);
		List<PGAcquirerFeeValue> acquirerFeeValueModels = new ArrayList<>();
		PGAcquirerFeeValue pgAcquirerFeeValue = new PGAcquirerFeeValue();
		acquirerFeeValueModels.add(pgAcquirerFeeValue);
		pgFeeProgram.setPgOtherFeeValue(pgOtherFeeValue);
		Mockito.when(feeProgramDao.getByFeeProgramId(Matchers.anyLong())).thenReturn(feeProgramModel);
		Mockito.when(acquirerFeeValueDao.getAcquirerFeeValuesByFeeProgramId(Matchers.anyLong()))
				.thenReturn(acquirerFeeValueModels);
		feeProgramServiceImpl.getFeeProgramDetails(feeProgramDTO);
	}

	@Test
	public void testGetFeeProgramDetailsElse() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		feeProgramServiceImpl.getFeeProgramDetails(feeProgramDTO);
	}

	@Test
	public void testGetFeeProgramDetailsException() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		Mockito.when(feeProgramDao.getByFeeProgramId(Matchers.anyLong())).thenThrow(new NullPointerException());
		feeProgramServiceImpl.getFeeProgramDetails(feeProgramDTO);
	}

	@Test
	public void testGetByFeeProgramId() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		List<PGFeeProgram> feeProgramlist = new ArrayList<>();
		PGFeeProgram feeProgram = new PGFeeProgram();
		feeProgramlist.add(feeProgram);
		Mockito.when(feeProgramDao.getByFeeProgramId(Matchers.anyLong())).thenReturn(feeProgramlist);
		feeProgramServiceImpl.getByFeeProgramId(feeProgramDTO);
	}

	@Test
	public void testGetByFeeProgramIdElse() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		feeProgramServiceImpl.getByFeeProgramId(feeProgramDTO);
	}

	@Test
	public void testGetByFeeProgramIdException() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		Mockito.when(feeProgramDao.getByFeeProgramId(Matchers.anyLong())).thenThrow(new NullPointerException());
		feeProgramServiceImpl.getByFeeProgramId(feeProgramDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testUpdateFeeProgram() throws ChatakAdminException {
		List<FeeValue> feeValueList = new ArrayList<>();
		FeeValue feeValue = new FeeValue();
		feeValue.setFeePercentage("55");
		feeValue.setFlatFee("45");
		feeValueList.add(feeValue);
		List<PGFeeProgram> feeProgramDetails = new ArrayList<>();
		List<PGAcquirerFeeValue> acquirerFeeValueModels = new ArrayList<>();
		PGAcquirerFeeValue pgAcquirerFeeValue = new PGAcquirerFeeValue();
		acquirerFeeValueModels.add(pgAcquirerFeeValue);
		PGFeeProgram feeProgram = new PGFeeProgram();
		feeProgramDetails.add(feeProgram);
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		feeProgramDTO.setFeeValueList(feeValueList);
		OtherFeesDTO otherFeesDTO = new OtherFeesDTO();
		otherFeesDTO.setChargeBacKFee("101");
		otherFeesDTO.setChargeFrequency("123");
		otherFeesDTO.setSetupFee("35453");
		otherFeesDTO.setSettlementFee("200");
		feeProgramDTO.setOtherFee(otherFeesDTO);
		feeProgramDTO.setUpdatedBy("Admin");
		feeProgramDTO.setFeeProgramName("a");
		feeProgramDTO.setFeeProgramId(Long.parseLong("1"));
		feeProgram.setFeeProgramId(Long.parseLong("1"));
		feeProgramDetails.add(feeProgram);
		Mockito.when(feeProgramDao.findByFeeProgramName(Matchers.anyString())).thenReturn(feeProgramDetails);
		Mockito.when(acquirerFeeValueDao.getAcquirerFeeValuesByFeeProgramId(Matchers.anyLong()))
				.thenReturn(acquirerFeeValueModels);
		feeProgramServiceImpl.updateFeeProgram(feeProgramDTO);
	}

	@Test
	public void testValidateFeePgmAccNo() throws ChatakAdminException {
		PGAccount pgAccount = new PGAccount();
		PGMerchant pgMerchant = new PGMerchant();
		pgAccount.setEntityType("iChatak");
		pgAccount.setStatus("Active");
		pgMerchant.setStatus(0);
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(merchantRepository.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchant);
		feeProgramServiceImpl.validateFeePgmAccNo("534985");
	}

	@Test
	public void testValidateFeePgmAccNoFalse() throws ChatakAdminException {
		PGAccount pgAccount = new PGAccount();
		pgAccount.setEntityType("iChatak");
		pgAccount.setStatus("Active");
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		feeProgramServiceImpl.validateFeePgmAccNo("534985");
	}

	@Test
	public void testDeleteFeeProgram() throws ChatakAdminException {
		feeProgramServiceImpl.deleteFeeProgram(Long.parseLong("6664"));
	}

	@Test
	public void testValidateFeeprogramName() throws ChatakAdminException {
		PGFeeProgram feeProgram = new PGFeeProgram();
		feeProgram.setStatus("Deleted");
		Mockito.when(feeProgramDao.getFeeprogramName(Matchers.anyString())).thenReturn(feeProgram);
		feeProgramServiceImpl.validateFeeprogramName("6664");
	}

	@Test
	public void testValidateFeeprogramNameElse() throws ChatakAdminException {
		PGFeeProgram feeProgram = new PGFeeProgram();
		feeProgram.setStatus("Success");
		Mockito.when(feeProgramDao.getFeeprogramName(Matchers.anyString())).thenReturn(feeProgram);
		feeProgramServiceImpl.validateFeeprogramName("6664");
	}

	@Test
	public void testValidateFeeprogramNameNull() throws ChatakAdminException {
		feeProgramServiceImpl.validateFeeprogramName("6664");
	}

	@Test
	public void testChangeFeeProgramStatus() throws ChatakAdminException {
		FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
		List<PGFeeProgram> feeProgramModel = new ArrayList<>();
		PGFeeProgram feeProgram = new PGFeeProgram();
		feeProgramModel.add(feeProgram);
		Mockito.when(feeProgramDao.getByFeeProgramId(Matchers.anyLong())).thenReturn(feeProgramModel);
		feeProgramServiceImpl.changeFeeProgramStatus(feeProgramDTO);
	}

}
