/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.util.Constants;

/**
 * @Author: Girmiti Software
 * @Date: Jan 15, 2018
 * @Time: 5:25:49 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountDaoImplTest {

  @InjectMocks
  AccountDaoImpl accountDaoImpl;

  @Mock
  AccountRepository accountRepository;

  @Mock
  Query query;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityManagerFactory emEntityManagerFactory;

  @Mock
  CurrencyConfigRepository currencyConfigRepository;

  @Test
  public void testSavePgAcc() {
    AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
    AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
    addMerchantResponse = accountDaoImpl.savePgAcc(addMerchantRequest, addMerchantResponse);
    Assert.assertNotNull(addMerchantResponse);

  }

  @Test
  public void testGetPgAccount() {
    PGAccount pGAccount = new PGAccount();
    Mockito
        .when(
            accountRepository.findByEntityIdAndCategory(Matchers.anyString(), Matchers.anyString()))
        .thenReturn(pGAccount);
    Assert.assertNotNull(accountDaoImpl.getPgAccount("1234"));

  }

  @Test
  public void testSavePGAccount() {
    accountDaoImpl.savePGAccount(new PGAccount());

  }

  @Test
  public void testGetAccountRepository() {
    Assert.assertNotNull(accountDaoImpl.getAccountRepository());

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyAndAutoPaymentMethodDaily() {
    Assert.assertNotNull(accountDaoImpl.getPGAccountsOnPayoutFrequencyAndAutoPaymentMethod("D",
        "autoPaymentMethod"));

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyAndAutoPaymentMethodWeekly() {
    List<PGAccount> pgAccountList = new ArrayList<>();
    PGAccount pgAccount = new PGAccount();
    pgAccount.setAccountNum(Long.parseLong("1236987456321"));
    pgAccount.setAutoTransferDay("autoTransferDay:4");
    pgAccountList.add(pgAccount);
    Mockito.when(accountRepository.findByPayoutFrequencyAndautoPaymentMethod(Matchers.anyString(),
        Matchers.anyString())).thenReturn(pgAccountList);
    pgAccountList =
        accountDaoImpl.getPGAccountsOnPayoutFrequencyAndAutoPaymentMethod("W", "autoPaymentMethod");
    Assert.assertNotNull(pgAccountList);

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyAndAutoPaymentMethodMonthly() {
    List<PGAccount> pgAccountList = new ArrayList<>();
    PGAccount pgAccount = new PGAccount();
    pgAccount.setAccountNum(Long.parseLong("1236987456321"));
    pgAccount.setAutoTransferDay("autoTransferDay:17");
    pgAccountList.add(pgAccount);
    Mockito.when(accountRepository.findByPayoutFrequencyAndautoPaymentMethod(Matchers.anyString(),
        Matchers.anyString())).thenReturn(pgAccountList);
    pgAccountList =
        accountDaoImpl.getPGAccountsOnPayoutFrequencyAndAutoPaymentMethod("M", "autoPaymentMethod");
    Assert.assertNotNull(pgAccountList);

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyAndAutoPaymentMethodOthers() {
    Assert.assertNotNull(accountDaoImpl.getPGAccountsOnPayoutFrequencyAndAutoPaymentMethod("A",
        "autoPaymentMethod"));

  }

  @Test
  public void testGetOverViewData() {
    List<String> pgAccCurrency = new ArrayList<>();
    String str = "abc";
    String str1 = "def";
    String str2 = "mno";
    pgAccCurrency.add(str);
    pgAccCurrency.add(str1);
    pgAccCurrency.add(str2);
    PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
    Mockito.when(accountRepository.getAllCurrency()).thenReturn(pgAccCurrency);
    Mockito.when(currencyConfigRepository.findByCurrencyCodeAlpha(Matchers.anyString()))
        .thenReturn(pgCurrencyConfig);
    Assert.assertNotNull(accountDaoImpl.getOverViewData());

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyAndDaily() {
    Assert.assertNotNull(accountDaoImpl.getPGAccountsOnPayoutFrequency("D"));

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyWeekly() {
    List<PGAccount> pgAccountList = new ArrayList<>();
    PGAccount pgAccount = new PGAccount();
    pgAccount.setAccountNum(Long.parseLong("1236987456321"));
    pgAccount.setAutoTransferDay("autoTransferDay:4");
    pgAccountList.add(pgAccount);
    Mockito.when(accountRepository.findByPayoutFrequency(Matchers.anyString()))
        .thenReturn(pgAccountList);
    pgAccountList = accountDaoImpl.getPGAccountsOnPayoutFrequency("W");
    Assert.assertNotNull(pgAccountList);

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyMonthly() {
    List<PGAccount> pgAccountList = new ArrayList<>();
    PGAccount pgAccount = new PGAccount();
    pgAccount.setAccountNum(Long.parseLong("1236987456321"));
    pgAccount.setAutoTransferDay("autoTransferDay:17");
    pgAccountList.add(pgAccount);
    Mockito.when(accountRepository.findByPayoutFrequency(Matchers.anyString()))
        .thenReturn(pgAccountList);
    pgAccountList = accountDaoImpl.getPGAccountsOnPayoutFrequency("M");
    Assert.assertNotNull(pgAccountList);

  }

  @Test
  public void testGetPGAccountsOnPayoutFrequencyOthers() {
    Assert.assertNotNull(accountDaoImpl.getPGAccountsOnPayoutFrequency("A"));

  }


  @Test
  public void testAddMerchantAccount() {
    PGAccount pgAccount = new PGAccount();
    Mockito.when(accountRepository.save(Matchers.any(PGAccount.class))).thenReturn(pgAccount);
    pgAccount = accountDaoImpl.addMerchantAccount(pgAccount);
    Assert.assertNotNull(pgAccount);

  }

  @Test
  public void testSearchMerchantAccount() {
    List<MerchantAccountSearchDto> merchantAccountSearchDtoList = new ArrayList<>();
    MerchantAccountSearchDto merchantAccountSearchDto = new MerchantAccountSearchDto();
    merchantAccountSearchDto.setPageIndex(Integer.parseInt("1"));
    merchantAccountSearchDto.setPageSize(Integer.parseInt("25"));
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    List<PGAccount> pgAccountList = new ArrayList<>();
    PGAccount pgAccount = new PGAccount();
    pgAccount.setId(Long.parseLong("123"));
    pgAccountList.add(pgAccount);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = new Object[Integer.parseInt("11")];
    objects[0] = new String("businessName");
    objects[1] = new String("123456");
    objects[Integer.parseInt("2")] = new Long(Long.parseLong("123"));
    objects[Integer.parseInt("3")] = new String("merchantType");
    objects[Integer.parseInt("4")] = new Long(Long.parseLong("123"));
    objects[Integer.parseInt("5")] = new Long(Long.parseLong("123456789012"));
    objects[Integer.parseInt("6")] = new Long(Long.parseLong("123"));
    objects[Integer.parseInt("7")] = new String(Constants.ACTIVE);
    objects[Integer.parseInt("8")] = time;
    objects[Integer.parseInt("9")] = new String("Acquirer");  //ReBrand
    objects[Integer.parseInt("10")] = new String("Alaska");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(pgAccountList, tuplelist);
    merchantAccountSearchDtoList = accountDaoImpl.searchMerchantAccount(merchantAccountSearchDto);
    Assert.assertNotNull(merchantAccountSearchDtoList);

  }

  @Test
  public void testSearchMerchantAccountExp() {
    MerchantAccountSearchDto merchantAccountSearchDto = new MerchantAccountSearchDto();
    merchantAccountSearchDto.setPageIndex(Integer.parseInt("1"));
    Assert.assertNull(accountDaoImpl.searchMerchantAccount(merchantAccountSearchDto));

  }

  @Test
  public void testChangeAccountStatus() {
    PGAccount pgAccount = new PGAccount();
    Mockito.when(accountRepository.findOne(Matchers.anyLong())).thenReturn(pgAccount);
    accountDaoImpl.changeAccountStatus(Long.parseLong("123"), Constants.ACTIVE, "test");

  }

  @Test
  public void testGetAccountOnId() {
    PGAccount pgAccount = new PGAccount();
    Mockito.when(accountRepository.findById(Matchers.anyLong())).thenReturn(pgAccount);
    Assert.assertNotNull(accountDaoImpl.getAccountOnId(Long.parseLong("123")));

  }

  @Test
  public void testGetAccountonAccountNumber() {
    PGAccount pgAccount = new PGAccount();
    Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
    Assert.assertNotNull(accountDaoImpl.getAccountonAccountNumber(Long.parseLong("123")));

  }

  @Test
  public void testGetSecondaryAccount() {
    List<PGAccount> pgAccountList = new ArrayList<>();
    PGAccount pgAccount = new PGAccount();
    pgAccount.setAccountNum(Long.parseLong("1236998845"));
    pgAccountList.add(pgAccount);
    Mockito.when(accountRepository.findByEntityIdAndCategoryAndStatus(Matchers.anyString(),
        Matchers.anyString(), Matchers.anyString())).thenReturn(pgAccountList);
    pgAccount = accountDaoImpl.getSecondaryAccount("1234");
    Assert.assertNotNull(pgAccount);

  }

  @Test
  public void testGetSecondaryAccountIsNull() {
    Assert.assertNull(accountDaoImpl.getSecondaryAccount("1234"));

  }

  @Test
  public void testGetActivePGAccounts() {
    List<PGAccount> pgAccountList = new ArrayList<>();
    Mockito
        .when(accountRepository.findByEntityIdAndStatus(Matchers.anyString(), Matchers.anyString()))
        .thenReturn(pgAccountList);
    pgAccountList = accountDaoImpl.getActivePGAccounts("1234");
    Assert.assertNotNull(pgAccountList);

  }

  @Test
  public void testGetAccDetailsOnAccNums() {
    List<AccountBalanceDTO> accountBalanceDTOList = new ArrayList<>();
    AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
    accountBalanceDTO.setAccountNumber(Long.parseLong("123698745"));
    accountBalanceDTOList.add(accountBalanceDTO);
    List<Long> longList = new ArrayList<>();
    Long longValue1 = new Long("12345");
    longList.add(longValue1);
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = new Object[Integer.parseInt("5")];
    objects[0] = new Long(Long.parseLong("123456789012"));
    objects[1] = new Long(Long.parseLong("584845"));
    objects[Integer.parseInt("2")] = new Long(Long.parseLong("584845"));
    objects[Integer.parseInt("3")] = new String("AccountTesting");
    objects[Integer.parseInt("4")] = new String(Constants.ACTIVE);
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    accountBalanceDTOList = accountDaoImpl.getAccDetailsOnAccNums(longList);
    Assert.assertNotNull(accountBalanceDTOList);

  }

  @Test
  public void testSearchMerchantAccountOnMerchantCode() {
    List<MerchantAccountSearchDto> MerchantAccountSearchDTOList = new ArrayList<>();
    MerchantAccountSearchDto merchantAccountSearchDto = new MerchantAccountSearchDto();
    MerchantAccountSearchDTOList.add(merchantAccountSearchDto);
    MerchantAccountSearchDTOList =
        accountDaoImpl.searchMerchantAccountOnMerchantCode(merchantAccountSearchDto);
    Assert.assertNotNull(MerchantAccountSearchDTOList);

  }

}
