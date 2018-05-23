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

import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.repository.AccountHistoryRepository;
import com.chatak.pg.user.bean.MerchantAccountHistory;

/**
 * @Author: Girmiti Software
 * @Date: Jan 17, 2018
 * @Time: 2:17:12 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountHistoryDaoImplTest {
  
  @InjectMocks
  AccountHistoryDaoImpl accountHistoryDaoImpl;
  
  @Mock
  AccountHistoryRepository accountHistoryRepository;
  
  @Mock
  Query query;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityManagerFactory emEntityManagerFactory;
  
  @Test
  public void testCreateOrSave() {
    PGAccountHistory accountHistory = new PGAccountHistory();
    accountHistoryDaoImpl.createOrSave(accountHistory);
  }
  
  @Test
  public void testGetHistoryByAccountNum() {
    List<PGAccountHistory> pgAccountHistoryList = new ArrayList<>();
    PGAccountHistory accountHistory = new PGAccountHistory();
    accountHistory.setAccountNum(Long.parseLong("123456"));
    pgAccountHistoryList.add(accountHistory);
    Mockito.when(accountHistoryRepository.findByAccountNumOrderByUpdatedDateDesc(Matchers.anyLong())).thenReturn(pgAccountHistoryList);
    pgAccountHistoryList = accountHistoryDaoImpl.getHistoryByAccountNum(Long.parseLong("12345"));
    Assert.assertNotNull(pgAccountHistoryList);
  }
  
  @Test
  public void testSearchAccountHistory() {
    MerchantAccountHistory merchantAccountHistory = new MerchantAccountHistory();
    merchantAccountHistory.setPageIndex(Integer.parseInt("1"));
    merchantAccountHistory.setPageSize(Integer.parseInt("25"));
    Timestamp time = new Timestamp(Integer.parseInt("2018"));
    List<Object[]> tuplelist = new ArrayList<>();
    Object objects[] = new Object[Integer.parseInt("8")];
    objects[0] = new Long(Long.parseLong("123456789012"));
    objects[1] = new String("USD");
    objects[Integer.parseInt("2")] = new Long(Long.parseLong("12345"));
    objects[Integer.parseInt("3")] = new String("Active");
    objects[Integer.parseInt("4")] = new Long(Long.parseLong("12345"));
    objects[Integer.parseInt("5")] = new Long(Long.parseLong("12345"));
    objects[Integer.parseInt("6")] = new String("Cheque");
    objects[Integer.parseInt("7")] = time;
    tuplelist.add(objects);
    
    Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
    Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
    Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emEntityManagerFactory);
    Mockito.when(query.getResultList()).thenReturn(tuplelist);
    List<PGAccountHistory> accoHistList = accountHistoryDaoImpl.SearchAccountHistory(merchantAccountHistory);
    Assert.assertNotNull(accoHistList);
  }
  
  

}
