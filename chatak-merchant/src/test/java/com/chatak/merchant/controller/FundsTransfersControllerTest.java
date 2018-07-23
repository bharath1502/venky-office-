package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.service.FundTransferService;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.DebitAccount;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

public class FundsTransfersControllerTest {

	private static Logger logger = Logger.getLogger(FundsTransfersController.class);
	@InjectMocks
	FundsTransfersController fundsTransfersController = new FundsTransfersController();

	@Mock
	FundTransferService fundTransferService;

	@Mock
	MerchantDao merchantDao;

	@Mock
	private MessageSource messageSource;

	@Mock
	MerchantProfileDao merchantProfileDao;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(fundsTransfersController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.services.fraud.basic.feature.id", "abcd");
		properties.setProperty("merchant.services.fraud.advanced.feature.id", "existing");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowEFTTransferPage() throws ChatakMerchantException {
		PGMerchant pgMerchant = new PGMerchant();
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		debitAccount.setAccountNumber(null);
		pgMerchant.setMerchantCode("12342");
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(fundTransferService.populateFundTransferDTO(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(fundTransferDTO);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowEFTTransferPage method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowEFTTransferPage method", e);

		}
	}

	@Test
	public void testShowEFTTransferPageChatakMerchantException() throws ChatakMerchantException {
		PGMerchant pgMerchant = new PGMerchant();
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		debitAccount.setAccountNumber(null);
		pgMerchant.setMerchantCode("12342");
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(fundTransferService.populateFundTransferDTO(Matchers.anyString(), Matchers.anyString()))
				.thenThrow(ChatakMerchantException.class);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowEFTTransferPageChatakMerchantException method",
					e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowEFTTransferPageChatakMerchantException method",
					e);

		}
	}

	@Test
	public void testShowEFTTransferPageElse() throws ChatakMerchantException {
		PGMerchant pgMerchant = new PGMerchant();
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		debitAccount.setAccountNumber(Long.parseLong("543"));
		pgMerchant.setMerchantCode("12342");
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(fundTransferService.populateFundTransferDTO(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(fundTransferDTO);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowEFTTransferPageElse method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowEFTTransferPageElse method", e);

		}
	}

	@Test
	public void testShowCheckTransferPage() throws ChatakMerchantException {
		PGMerchant pgMerchant = new PGMerchant();
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		debitAccount.setAccountNumber(null);
		pgMerchant.setMerchantCode("12342");
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(fundTransferService.populateFundTransferDTO(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(fundTransferDTO);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowCheckTransferPage method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowCheckTransferPage method", e);

		}
	}

	@Test
	public void testShowCheckTransferPageElse() throws ChatakMerchantException {
		PGMerchant pgMerchant = new PGMerchant();
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		debitAccount.setAccountNumber(Long.parseLong("534"));
		pgMerchant.setMerchantCode("12342");
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(fundTransferService.populateFundTransferDTO(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(fundTransferDTO);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowCheckTransferPageElse method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowCheckTransferPageElse method", e);

		}
	}

	@Test
	public void testProcessEFTTransferHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_EFT_FUND_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessEFTTransferHeader method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessEFTTransferHeader method", e);

		}
	}

	@Test
	public void testShowCheckTransferPageChatakMerchantException() throws ChatakMerchantException {
		PGMerchant pgMerchant = new PGMerchant();
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		debitAccount.setAccountNumber(null);
		pgMerchant.setMerchantCode("12342");
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(fundTransferService.populateFundTransferDTO(Matchers.anyString(), Matchers.anyString()))
				.thenThrow(ChatakMerchantException.class);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")));
		} catch (NumberFormatException e) {
			logger.info(
					"Error:: FundsTransfersControllerTest:: testShowCheckTransferPageChatakMerchantException method",
					e);

		} catch (Exception e) {
			logger.info(
					"Error:: FundsTransfersControllerTest:: testShowCheckTransferPageChatakMerchantException method",
					e);

		}
	}

	@Test
	public void testProcessEFTTransfer() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_EFT_FUND_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")).header(Constants.REFERER, "null"))
					.andExpect(view().name(URLMappingConstants.CHATAK_FUND_TRANSFER_RESPONSE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessEFTTransfer method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessEFTTransfer method", e);

		}
	}

	@Test
	public void testProcessCheckTransferHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_CHECK_FUND_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessCheckTransferHeader method", e);
		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessCheckTransferHeader method", e);

		}
	}

	@Test
	public void testProcessCheckTransfer() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_CHECK_FUND_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")).header(Constants.REFERER, "null"))
					.andExpect(view().name(URLMappingConstants.CHATAK_FUND_TRANSFER_RESPONSE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessCheckTransfer method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessCheckTransfer method", e);

		}
	}

	@Test
	public void testFetchAccountDetails() throws ChatakMerchantException {
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("234");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(fundTransferService.fetchAccountDetails(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountBalanceDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_FETCH_ACCOUNT_DETAILS)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testFetchAccountDetails method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testFetchAccountDetails method", e);

		}
	}

	@Test
	public void testFetchAccountDetailsChatakMerchantException() throws ChatakMerchantException {
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("234");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenThrow(ChatakMerchantException.class);
		Mockito.when(fundTransferService.fetchAccountDetails(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountBalanceDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_FETCH_ACCOUNT_DETAILS)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testFetchAccountDetailsChatakMerchantException method",
					e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testFetchAccountDetailsChatakMerchantException method",
					e);

		}
	}

	@Test
	public void testShowAccountTransfer() throws ChatakMerchantException {
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("234");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(fundTransferService.fetchAccountDetails(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountBalanceDTO);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowAccountTransfer method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowAccountTransfer method", e);

		}
	}

	@Test
	public void testShowAccountTransferChatakMerchantException() throws ChatakMerchantException {
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("234");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenThrow(ChatakMerchantException.class);
		Mockito.when(fundTransferService.fetchAccountDetails(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountBalanceDTO);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER));
		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testShowAccountTransferChatakMerchantException method",
					e);

		}
	}

	@Test
	public void testProcessAccountTransfer() throws ChatakMerchantException {
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("234");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(fundTransferService.fetchAccountDetails(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountBalanceDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")).header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER));
		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessAccountTransfer method", e);

		}
	}

	@Test
	public void testProcessAccountTransferHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessAccountTransferHeader method", e);

		} catch (Exception e) {
			logger.info("Error:: FundsTransfersControllerTest:: testProcessAccountTransferHeader method", e);

		}
	}

	@Test
	public void testProcessAccountTransferChatakMerchantException() {
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("234");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenThrow(ChatakMerchantException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER)
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")).header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_PROCESS_ACCOUNT_TRANSFER));
		} catch (Exception e) {
			logger.info(
					"Error:: FundsTransfersControllerTest:: testProcessAccountTransferChatakMerchantException method",
					e);

		}
	}

}
