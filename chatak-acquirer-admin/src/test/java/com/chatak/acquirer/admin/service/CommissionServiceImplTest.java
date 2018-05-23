package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.CommissionServiceImpl;
import com.chatak.pg.acq.dao.CommissionDao;
import com.chatak.pg.acq.dao.model.PGCommission;
import com.chatak.pg.acq.dao.model.PGOtherCommission;
import com.chatak.pg.model.CommissionDTO;
import com.chatak.pg.model.OtherCommissionDTO;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class CommissionServiceImplTest {

	@InjectMocks
	CommissionServiceImpl commissionServiceImpl = new CommissionServiceImpl();

	@Mock
	CommissionDao commissionDao;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("fee.program.not.exist.errorcode", "abcd");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testCreateCommission() throws ChatakAdminException {
		List<OtherCommissionDTO> otherCommissionDTO = new ArrayList<>();
		CommissionDTO commissionDTO = new CommissionDTO();
		OtherCommissionDTO otherCommissionValue = new OtherCommissionDTO();
		otherCommissionDTO.add(otherCommissionValue);
		commissionDTO.setMerchantOnBoardingFee("543");
		commissionDTO.setOtherCommissionDTO(otherCommissionDTO);
		commissionServiceImpl.createCommission(commissionDTO);
	}

	@Test
	public void testCreateCommissionException() throws ChatakAdminException {
		List<OtherCommissionDTO> otherCommissionDTO = new ArrayList<>();
		CommissionDTO commissionDTO = new CommissionDTO();
		OtherCommissionDTO otherCommissionValue = new OtherCommissionDTO();
		PGCommission commissionDaoDetails = new PGCommission();
		otherCommissionDTO.add(otherCommissionValue);
		commissionDTO.setMerchantOnBoardingFee("543");
		otherCommissionValue.setCommissionType("flat");
		otherCommissionValue.setFlatFee("565");
		Mockito.when(commissionDao.createCommissionProgram(Matchers.any(PGCommission.class)))
				.thenReturn(commissionDaoDetails);
		commissionDTO.setOtherCommissionDTO(otherCommissionDTO);
		commissionServiceImpl.createCommission(commissionDTO);
	}

	@Test
	public void testCreateCommissionExceptionElse() throws ChatakAdminException {
		List<OtherCommissionDTO> otherCommissionDTO = new ArrayList<>();
		CommissionDTO commissionDTO = new CommissionDTO();
		OtherCommissionDTO otherCommissionValue = new OtherCommissionDTO();
		PGCommission commissionDaoDetails = new PGCommission();
		otherCommissionDTO.add(otherCommissionValue);
		commissionDTO.setMerchantOnBoardingFee("543");
		otherCommissionValue.setCommissionType("xyz");
		otherCommissionValue.setFromValue("5435");
		otherCommissionValue.setToValue("8795");
		otherCommissionValue.setAmount("4356");
		Mockito.when(commissionDao.createCommissionProgram(Matchers.any(PGCommission.class)))
				.thenReturn(commissionDaoDetails);
		commissionDTO.setOtherCommissionDTO(otherCommissionDTO);
		commissionServiceImpl.createCommission(commissionDTO);
	}

	@Test
	public void testCreateCommissionExceptionCommissionTypeNull() throws ChatakAdminException {
		List<OtherCommissionDTO> otherCommissionDTO = new ArrayList<>();
		CommissionDTO commissionDTO = new CommissionDTO();
		OtherCommissionDTO otherCommissionValue = new OtherCommissionDTO();
		PGCommission commissionDaoDetails = new PGCommission();
		otherCommissionDTO.add(otherCommissionValue);
		commissionDTO.setMerchantOnBoardingFee("543");
		otherCommissionValue.setFromValue("5435");
		otherCommissionValue.setToValue("8795");
		otherCommissionValue.setAmount("4356");
		Mockito.when(commissionDao.createCommissionProgram(Matchers.any(PGCommission.class)))
				.thenReturn(commissionDaoDetails);
		commissionDTO.setOtherCommissionDTO(otherCommissionDTO);
		commissionServiceImpl.createCommission(commissionDTO);
	}

	@Test
	public void testCreateCommissionExceptionException() throws ChatakAdminException {
		CommissionDTO commissionDTO = new CommissionDTO();
		commissionServiceImpl.createCommission(commissionDTO);
	}

	@Test
	public void testSearchCommissionProgram() throws ChatakAdminException {
		List<PGCommission> pgcommission = new ArrayList<>();
		PGCommission commission = new PGCommission();
		CommissionDTO commissionDTO = new CommissionDTO();
		pgcommission.add(commission);
		Mockito.when(commissionDao.searchCommissionProgram(Matchers.any(CommissionDTO.class))).thenReturn(pgcommission);
		commissionServiceImpl.searchCommissionProgram(commissionDTO);
	}

	@Test
	public void testGetCommissionProgramDetails() throws ChatakAdminException {
		List<PGCommission> pgcommission = new ArrayList<>();
		PGCommission commission = new PGCommission();
		CommissionDTO commissionDTO = new CommissionDTO();
		List<PGOtherCommission> othCommDTOValues = new ArrayList<>();
		PGOtherCommission othCommModel = new PGOtherCommission();
		commission.setMerchantOnBoardingFee(Double.parseDouble("5435"));
		othCommModel.setCommissionType("flat");
		othCommModel.setFlatFee(Double.parseDouble("423"));
		othCommDTOValues.add(othCommModel);
		pgcommission.add(commission);
		Mockito.when(commissionDao.getByCommProgramId(Matchers.anyLong())).thenReturn(pgcommission);
		Mockito.when(commissionDao.getOtherCommissionByCommissionProgramId(Matchers.anyLong()))
				.thenReturn(othCommDTOValues);
		commissionServiceImpl.getCommissionProgramDetails(commissionDTO);
	}

	@Test
	public void testGetCommissionProgramDetailsElse() throws ChatakAdminException {
		List<PGCommission> pgcommission = new ArrayList<>();
		PGCommission commission = new PGCommission();
		CommissionDTO commissionDTO = new CommissionDTO();
		List<PGOtherCommission> othCommDTOValues = new ArrayList<>();
		PGOtherCommission othCommModel = new PGOtherCommission();
		commission.setMerchantOnBoardingFee(Double.parseDouble("5435"));
		othCommModel.setCommissionType("xyz");
		othCommModel.setFromValue(Double.parseDouble("423"));
		othCommModel.setToValue(Double.parseDouble("423"));
		othCommModel.setAmount(Double.parseDouble("423"));
		othCommDTOValues.add(othCommModel);
		pgcommission.add(commission);
		Mockito.when(commissionDao.getByCommProgramId(Matchers.anyLong())).thenReturn(pgcommission);
		Mockito.when(commissionDao.getOtherCommissionByCommissionProgramId(Matchers.anyLong()))
				.thenReturn(othCommDTOValues);
		commissionServiceImpl.getCommissionProgramDetails(commissionDTO);
	}

	@Test
	public void testGetCommissionProgramDetailsCommissionTypeNull() throws ChatakAdminException {
		List<PGCommission> pgcommission = new ArrayList<>();
		PGCommission commission = new PGCommission();
		CommissionDTO commissionDTO = new CommissionDTO();
		List<PGOtherCommission> othCommDTOValues = new ArrayList<>();
		PGOtherCommission othCommModel = new PGOtherCommission();
		commission.setMerchantOnBoardingFee(Double.parseDouble("5435"));
		othCommModel.setFromValue(Double.parseDouble("423"));
		othCommModel.setToValue(Double.parseDouble("423"));
		othCommModel.setAmount(Double.parseDouble("423"));
		othCommDTOValues.add(othCommModel);
		pgcommission.add(commission);
		Mockito.when(commissionDao.getByCommProgramId(Matchers.anyLong())).thenReturn(pgcommission);
		Mockito.when(commissionDao.getOtherCommissionByCommissionProgramId(Matchers.anyLong()))
				.thenReturn(othCommDTOValues);
		commissionServiceImpl.getCommissionProgramDetails(commissionDTO);
	}

	@Test
	public void testGetCommissionProgramDetailsException() throws ChatakAdminException {
		CommissionDTO commissionDTO = new CommissionDTO();
		Mockito.when(commissionDao.getByCommProgramId(Matchers.anyLong())).thenThrow(new NullPointerException());
		commissionServiceImpl.getCommissionProgramDetails(commissionDTO);
	}

	@Test
	public void testGetCommissionProgramDetailsResponseNull() throws ChatakAdminException {
		CommissionDTO commissionDTO = new CommissionDTO();
		commissionServiceImpl.getCommissionProgramDetails(commissionDTO);
	}

	@Test
	public void testGetByCommProgramId() throws ChatakAdminException {
		List<PGCommission> commProgramlist = new ArrayList<>();
		PGCommission commission = new PGCommission();
		CommissionDTO commissionDTO = new CommissionDTO();
		commProgramlist.add(commission);
		Mockito.when(commissionDao.getByCommProgramId(Matchers.anyLong())).thenReturn(commProgramlist);
		commissionServiceImpl.getByCommProgramId(commissionDTO);
	}

	@Test
	public void testGetByCommProgramIdElse() throws ChatakAdminException {
		List<PGCommission> commProgramlist = new ArrayList<>();
		PGCommission commission = new PGCommission();
		CommissionDTO commissionDTO = new CommissionDTO();
		commProgramlist.add(commission);
		commissionServiceImpl.getByCommProgramId(commissionDTO);
	}

	@Test
	public void testGetByCommProgramIdException() throws ChatakAdminException {
		CommissionDTO commissionDTO = new CommissionDTO();
		Mockito.when(commissionDao.getByCommProgramId(Matchers.anyLong())).thenThrow(new NullPointerException());
		commissionServiceImpl.getByCommProgramId(commissionDTO);
	}

	@Test
	public void testUpdateCommissionProgram() throws ChatakAdminException {
		List<PGCommission> pgCommDetails = new ArrayList<>();
		List<PGOtherCommission> othCommission = new ArrayList<>();
		List<OtherCommissionDTO> commissionDTOs = new ArrayList<>();
		OtherCommissionDTO dto = new OtherCommissionDTO();
		commissionDTOs.add(dto);
		CommissionDTO commissionDTO = new CommissionDTO();
		commissionDTO.setCommissionProgramId(Long.parseLong("432"));
		PGCommission pgCommission = new PGCommission();
		PGOtherCommission pgOtherCommission = new PGOtherCommission();
		othCommission.add(pgOtherCommission);
		commissionDTO.setCommissionName("Name");
		pgCommission.setCommissionProgramId(Long.parseLong("432"));
		commissionDTO.setMerchantOnBoardingFee("5466");
		pgOtherCommission.setCommissionType("flat");
		pgCommDetails.add(pgCommission);
		commissionDTO.setOtherCommissionDTO(commissionDTOs);
		Mockito.when(commissionDao.findByCommissionName(Matchers.anyString())).thenReturn(pgCommDetails);
		Mockito.when(commissionDao.getOtherCommissionByCommissionProgramId(Matchers.anyLong()))
				.thenReturn(othCommission);
		Mockito.when(commissionDao.createCommissionProgram(Matchers.any(PGCommission.class))).thenReturn(pgCommission);
		commissionServiceImpl.updateCommissionProgram(commissionDTO);
	}

	@Test
	public void testUpdateCommissionProgramException() throws ChatakAdminException {
		CommissionDTO commissionDTO = new CommissionDTO();
		Mockito.when(commissionDao.findByCommissionName(Matchers.anyString())).thenThrow(new NullPointerException());
		commissionServiceImpl.updateCommissionProgram(commissionDTO);
	}

}
