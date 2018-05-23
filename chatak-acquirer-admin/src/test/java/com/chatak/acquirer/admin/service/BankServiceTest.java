package com.chatak.acquirer.admin.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.BankSearchResponse;
import com.chatak.acquirer.admin.service.impl.BankServiceImpl;
import com.chatak.pg.acq.dao.BankDao;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.repository.StateRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.Bank;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.GetBankListResopnse;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class BankServiceTest {

  @InjectMocks
  private BankServiceImpl bankService = new BankServiceImpl();

  @Mock
  private CountryDao countryDao;

  @Mock
  private BankDao bankDao;

  @Mock
  private BankRequest bankRequest;

  @Mock
  private Bank bank1;

  @Mock
  private StateRepository stateRepository;

  @Mock
  private Response response;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testcreateBank() throws ChatakAdminException {

    BankResponse bankResponse = Mockito.mock(BankResponse.class);
    Bank bank = Mockito.mock(Bank.class);

    Mockito.when(bankDao.createBank(Matchers.any(BankRequest.class))).thenReturn(bankResponse);
    Mockito.when(bankResponse.getErrorCode()).thenReturn(ActionErrorCode.ERROR_CODE_B0);
    Mockito.when(bankResponse.getErrorMessage())
        .thenReturn(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_B0));
    bankResponse = bankService.createBank(bank);
    Assert.assertNotNull(bankResponse);
  }

  @Test(expected=NullPointerException.class)
  public void testsearchBank() throws ChatakAdminException {
    GetBankListResopnse getBankListResopnse = Mockito.mock(GetBankListResopnse.class);
    PGBank pgBank = Mockito.mock(PGBank.class);
    Bank bank = Mockito.mock(Bank.class);

    Mockito.when(bankDao.getBanklist(Matchers.any(BankRequest.class)))
        .thenReturn(getBankListResopnse);
    Mockito.when(getBankListResopnse.getBanks()).thenReturn(Arrays.asList(pgBank));
    BankSearchResponse bankSearchResponse = bankService.searchBank(bank);
    Assert.assertNotNull(bankSearchResponse);
  }

  @Test(expected=NullPointerException.class)
  public void testfindByBankName() throws ChatakAdminException {
    PGBank pgBank = Mockito.mock(PGBank.class);
    Bank bank = Mockito.mock(Bank.class);
    Mockito.when(bank.getBankName()).thenReturn("axis bank");
    Mockito.when(bankDao.getBankByName("axis bank")).thenReturn(pgBank);
    bank = bankService.findByBankName(bank);
    Assert.assertNotNull(bank);
  }

  @Test
  public void testgetCountries() {
    CountryRequest countryRequests = Mockito.mock(CountryRequest.class);

    Mockito.when(countryDao.findAllCountries()).thenReturn(Arrays.asList(countryRequests));
    List<Option> options = bankService.getCountries();
    Assert.assertNotNull(options);
  }

  @Test
  public void testdeleteBankByName() {
    BankResponse bankResponse = Mockito.mock(BankResponse.class);
    Mockito.when(bankDao.deleteBank("axis bank")).thenReturn(bankResponse);
    bankResponse = bankService.deleteBankByName("axis bank");
    Assert.assertNotNull(bankResponse);
  }

  @Test
  public void testupdateBank() throws ChatakAdminException {
    Bank bank = Mockito.mock(Bank.class);
    BankResponse bankResponse = Mockito.mock(BankResponse.class);
    Mockito.when(bankDao.updateBank(Matchers.any(BankRequest.class))).thenReturn(bankResponse);
    bankResponse = bankService.updateBank(bank);
    Assert.assertNotNull(bankResponse);
  }

  @Test
  public void testgetStatesByCountry() throws ChatakAdminException {
    CountryRequest countryRequest = Mockito.mock(CountryRequest.class);
    PGState pgStates = Mockito.mock(PGState.class);
    Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
    Mockito.when(countryRequest.getId()).thenReturn(Constants.TWO_LONG);
    Mockito.when(countryRequest.getName()).thenReturn("india");
    Mockito.when(stateRepository.findByCountryId(Constants.TWO_LONG)).thenReturn(Arrays.asList(pgStates));
    response = bankService.getStatesByCountry("0");
    Assert.assertNotNull(response);
  }

  @Test
  public void testgetBankData() throws ChatakAdminException {
    PGBank bankList = Mockito.mock(PGBank.class);
    Mockito.when(bankDao.getBankData()).thenReturn(Arrays.asList(bankList));
    List<Option> option = bankService.getBankData();
    Assert.assertNotNull(option);
  }
}
