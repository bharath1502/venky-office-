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

import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.repository.AccountHistoryRepository;
import com.chatak.pg.acq.dao.repository.AccountTransactionsRepository;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.util.Constants;

/**
 * @Author: Girmiti Software
 * @Date: Jan 17, 2018
 * @Time: 2:34:50 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountTransactionsDaoImplTest {
  
  @InjectMocks
  AccountTransactionsDaoImpl accountTransactionsDaoImpl;
  
  @Mock
  Query query;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityManagerFactory emEntityManagerFactory;
  
  @Mock
  AccountTransactionsRepository accountTransactionsRepository;
  
  @Mock
  MerchantDao merchantDao;
  
  @Mock
  TransactionDao transactionDao;
  
  @Mock
  AccountHistoryRepository accountHistoryRepository;
  
  @Mock
  RefundTransactionDao refundTransactionDao;
  
  @Test
  public void testIsDuplicateAccountTransactionId() {
    List<PGAccountTransactions> accountTransactionsList = new ArrayList<>();
    Mockito.when(accountTransactionsRepository.findByAccountTransactionId(Matchers.anyString())).thenReturn(accountTransactionsList);
    boolean value = accountTransactionsDaoImpl.isDuplicateAccountTransactionId("123456");
    Assert.assertNotNull(value);
  }
  
  @Test
  public void testCreateOrUpdate() {
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    Mockito.when(accountTransactionsRepository.save(Matchers.any(PGAccountTransactions.class))).thenReturn(pgAccountTransactions);
    pgAccountTransactions = accountTransactionsDaoImpl.createOrUpdate(pgAccountTransactions);
    Assert.assertNotNull(pgAccountTransactions);
  }
  
  @Test
  public void testGenerateAccountTransactionId() {
    List<PGAccountTransactions> accountTransactionsList = new ArrayList<>();
    Mockito.when(accountTransactionsRepository.findByAccountTransactionId(Matchers.anyString())).thenReturn(accountTransactionsList);
    String str = accountTransactionsDaoImpl.generateAccountTransactionId();
    Assert.assertNotNull(str);
  }
  
  @Test
  public void testGetAccountTransactionByTransferId() {
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    Mockito.when(accountTransactionsRepository.findByPgTransferId(Matchers.anyString())).thenReturn(pgAccountTransactions);
    pgAccountTransactions = accountTransactionsDaoImpl.getAccountTransactionByTransferId("123");
    Assert.assertNotNull(pgAccountTransactions);
  }
  
  @Test
  public void testGetAccountTransactionsOnTransactionId() {
    List<PGAccountTransactions> pgAccountTransactionsList = new ArrayList<>();
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    pgAccountTransactions.setAccountNumber("123456789");
    pgAccountTransactionsList.add(pgAccountTransactions);
    Mockito.when(accountTransactionsRepository.findByPgTransactionId(Matchers.anyString())).thenReturn(pgAccountTransactionsList);
    accountTransactionsDaoImpl.getAccountTransactionsOnTransactionId("123");
    Assert.assertNotNull(pgAccountTransactions);
  }
  
  @Test
  public void testGetAccountTransactionsOnTransactionIdAndTransactionType() {
    List<PGAccountTransactions> pgAccountTransactionsList = new ArrayList<>();
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    pgAccountTransactions.setAccountNumber("123456789");
    pgAccountTransactionsList.add(pgAccountTransactions);
    Mockito.when(accountTransactionsRepository.findByPgTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString())).thenReturn(pgAccountTransactionsList);
    accountTransactionsDaoImpl.getAccountTransactionsOnTransactionIdAndTransactionType("123", "Cheque");
    Assert.assertNotNull(pgAccountTransactions);
  }
  
  @Test
  public void testGetAccountTransactionsOnTransactionIdAndTransactionCode() {
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    Mockito.when(accountTransactionsRepository.findByPgTransactionIdAndTransactionCode(Matchers.anyString(), Matchers.anyString())).thenReturn(pgAccountTransactions);
    pgAccountTransactions = accountTransactionsDaoImpl.getAccountTransactionsOnTransactionIdAndTransactionCode("123", "T11");
    Assert.assertNotNull(pgAccountTransactions);
  }
  
  @Test
  public void testGetAccountTransactions() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(Integer.parseInt("1"));
    getTransactionsListRequest.setPageSize(Integer.parseInt("25"));
    getTransactionsListRequest.setTransaction_type("auto");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] =getObjectDetails(time, "void");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsToDateNull() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(Integer.parseInt("1"));
    getTransactionsListRequest.setPageSize(Integer.parseInt("25"));
    getTransactionsListRequest.setTransaction_type("auto");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "void");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsDatesAreNull() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(Integer.parseInt("1"));
    getTransactionsListRequest.setPageSize(Integer.parseInt("25"));
    getTransactionsListRequest.setTransaction_type("auto");
    getTransactionsListRequest.setAcqChannel("web");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "void");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsFromDateNotNull() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("Executed");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "refund");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsManualToDateNotNull() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "auth");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsManualFromDateAndToDateNull() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "sale");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsManualFromDateAndToDateNotNull() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "FT_BANK");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsStatusFTBank() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "FT_BANK");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsStatusFTCheck() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "FT_CHECK");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsStatusManualcredit() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "MANUAL_CREDIT");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsStatusAccCredit() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = getObjectDetails(time, "ACCOUNT_CREDIT");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }
  
  @Test
  public void testGetAccountTransactionsManualStatusDefault() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setSettlementStatus("settled");
    getTransactionsListRequest.setTransaction_type("MANUAL_CREDIT");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setFrom_date("14/02/2017");
    getTransactionsListRequest.setTo_date("14/02/2019");
    List<String> transactionCodeList = new ArrayList<>();
    String str1 = "manualDebit";
    transactionCodeList.add(str1);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object[] objects = getObjectDetails(time, "default");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountTransactions(getTransactionsListRequest));
  }

  private Object[] getObjectDetails(Timestamp time, String type) {
    Object objects[] = new Object[Integer.parseInt("14")];
    objects[0] = time;
    objects[1] = time;
    objects[Integer.parseInt("2")] = new String("pGAccountTransactions.accountTransactionId");
    objects[Integer.parseInt("3")] = new String(type);
    objects[Integer.parseInt("4")] = new String("Testing");
    objects[Integer.parseInt("5")] = new Long(Long.parseLong("58"));
    objects[Integer.parseInt("6")] = new String("M159");
    objects[Integer.parseInt("7")] = new String("T159");
    objects[Integer.parseInt("8")] = new Long(Long.parseLong("86"));
    objects[Integer.parseInt("9")] = new Long(Long.parseLong("584845"));
    objects[Integer.parseInt("10")] = new String(Constants.ACTIVE);
    objects[Integer.parseInt("11")] = new Long(Long.parseLong("584845"));
    objects[Integer.parseInt("12")] = new String("5458645656");
    objects[Integer.parseInt("13")] = new String("USD");
    return objects;
  }
  
  @Test
  public void testGetSaleAccountTransactionIdIsNull() {
    List<PGAccountTransactions> list = new ArrayList<>();
    Mockito.when(accountTransactionsRepository.findByAccountTransactionIdAndTransactionTypeAndMerchantCode(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(list);
    Assert.assertNull(accountTransactionsDaoImpl.getSaleAccountTransactionId("123654",  "215656"));
  }
  
  @Test
  public void testGetSaleAccountTransactionIdIsNotNull() {
    List<PGAccountTransactions> pgAccountTransactionsList = new ArrayList<>();
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    pgAccountTransactions.setAccountNumber("123456789");
    pgAccountTransactionsList.add(pgAccountTransactions);
    Mockito.when(accountTransactionsRepository.findByAccountTransactionIdAndTransactionTypeAndMerchantCode(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(pgAccountTransactionsList);
    Assert.assertNull(accountTransactionsDaoImpl.getSaleAccountTransactionId("123654",  "215656"));
  }
  
  @Test
  public void testGetAccountTransactionsId() {
    List<PGAccountTransactions> pgAccountTransactionsList = new ArrayList<>();
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    pgAccountTransactions.setAccountNumber("123456789");
    pgAccountTransactionsList.add(pgAccountTransactions);
    Mockito.when(accountTransactionsRepository.findByAccountTransactionId(Matchers.anyString())).thenReturn(pgAccountTransactionsList);
    pgAccountTransactionsList = accountTransactionsDaoImpl.getAccountTransactions("215656");
    Assert.assertNotNull(pgAccountTransactionsList);
  }
  
  @Test
  public void testGetAccountAllTransactions() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    List<String> transactionCodeList = new ArrayList<>();
    String str = "465845616";
    transactionCodeList.add(str);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    getTransactionsListRequest.setSettlementStatus("Executed");
    getTransactionsListRequest.setAccountNumber("1236987466955");
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = new Object[Integer.parseInt("17")];
    objects[0] = time;
    objects[1] = time;
    objects[Integer.parseInt("2")] = new String("23456");
    objects[Integer.parseInt("3")] = new String("default");
    objects[Integer.parseInt("4")] = new String("Testing");
    objects[Integer.parseInt("5")] = new Long(Long.parseLong("58"));
    objects[Integer.parseInt("6")] = new String("M159");
    objects[Integer.parseInt("7")] = new String("T159");
    objects[Integer.parseInt("8")] = new Long(Long.parseLong("86"));
    objects[Integer.parseInt("9")] = new Long(Long.parseLong("584845"));
    objects[Integer.parseInt("10")] = new String(Constants.ACTIVE);
    objects[Integer.parseInt("11")] = new Long(Long.parseLong("123"));
    objects[Integer.parseInt("12")] = time;
    objects[Integer.parseInt("13")] = time;
    objects[Integer.parseInt("14")] = new String("545864565566");
    objects[Integer.parseInt("15")] = new String("PGT5486468");
    tuplelist.add(objects);
    AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
    accountTransactionDTO.setType("S");
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountAllTransactions(getTransactionsListRequest));
    
  }
  
  @Test
  public void testGetAccountAllTransactionsAccountAndStatusNull() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    List<String> transactionCodeList = new ArrayList<>();
    String str = "465845616";
    transactionCodeList.add(str);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = new Object[Integer.parseInt("17")];
    objects[0] = time;
    objects[1] = time;
    objects[Integer.parseInt("2")] = new String("23456");
    objects[Integer.parseInt("3")] = new String("default");
    objects[Integer.parseInt("4")] = new String("Testing");
    objects[Integer.parseInt("5")] = new Long(Long.parseLong("58"));
    objects[Integer.parseInt("6")] = new String("M159");
    objects[Integer.parseInt("7")] = new String("T159");
    objects[Integer.parseInt("8")] = new Long(Long.parseLong("86"));
    objects[Integer.parseInt("9")] = new Long(Long.parseLong("584845"));
    objects[Integer.parseInt("10")] = new String(Constants.ACTIVE);
    objects[Integer.parseInt("11")] = new Long(Long.parseLong("123"));
    objects[Integer.parseInt("12")] = time;
    objects[Integer.parseInt("13")] = time;
    objects[Integer.parseInt("14")] = new String("545864565566");
    objects[Integer.parseInt("15")] = new String("PGT5486468");
    tuplelist.add(objects);
    AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
    accountTransactionDTO.setType("S");
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getAccountAllTransactions(getTransactionsListRequest));
    
  }
  
  @Test
  public void testSaveAccountHistory() {
    PGAccountHistory pgAccountHistory = new PGAccountHistory();
    pgAccountHistory.setAccountNum(Long.parseLong("15864651456465846"));
    Mockito.when(accountHistoryRepository.save(Matchers.any(PGAccountHistory.class))).thenReturn(pgAccountHistory);
    pgAccountHistory = accountTransactionsDaoImpl.saveAccountHistory(pgAccountHistory);
    Assert.assertNotNull(pgAccountHistory);
  }
  
  @Test
  public void testGetManulAccountTransactions() {
    GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
    getTransactionsListRequest.setPageIndex(1);
    getTransactionsListRequest.setTransaction_type("MANUAL");
    getTransactionsListRequest.setAcqChannel("web");
    getTransactionsListRequest.setMerchant_code("1546645");
    List<String> transactionCodeList = new ArrayList<>();
    String str = "465845616";
    transactionCodeList.add(str);
    getTransactionsListRequest.setTransactionCodeList(transactionCodeList);
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object[] objects = getObjectDetails(time, "default");
    tuplelist.add(objects);
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    Assert.assertNotNull(accountTransactionsDaoImpl.getManulAccountTransactions(getTransactionsListRequest));
  }
}
