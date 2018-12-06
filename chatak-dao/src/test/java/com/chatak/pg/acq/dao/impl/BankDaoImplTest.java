/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.BankCurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.BankCurrencyRepository;
import com.chatak.pg.acq.dao.repository.BankProgramManagerRepository;
import com.chatak.pg.acq.dao.repository.BankRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.user.bean.BankRequest;

/**
 * @Author: Girmiti Software
 * @Date: 16-Feb-2018
 * @Time: 11:37:43 am
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BankDaoImplTest {

  private static final String BANK_NAME = "bankName";
  
	@InjectMocks
	BankDaoImpl bankDaoImpl;

	@Mock
	private EntityManager entityManager;
	
	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	private BankRepository bankRepository;

	@Mock
	private BankCurrencyRepository bankCurrencyRepository;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private BankCurrencyConfigRepository bankCurrencyConfigRepository;

	@Mock
	MerchantRepository merchantRepository;

	@Mock
	MessageSource messageSource;

	@Mock
	BankProgramManagerRepository bankProgramManagerRepository;
	
	@Test
	public void testCreateBank() {
		BankRequest bankRequest = new BankRequest();
		PGBank pgBank = new PGBank();
		PGAccount account = new PGAccount();
		PGBankCurrencyMapping bankCurrencyMapped = new PGBankCurrencyMapping();
		pgBank.setStatus("Deleted");
		pgBank.setId(Long.parseLong("54"));
		Mockito.when(bankCurrencyRepository.findByBankId(Matchers.anyLong())).thenReturn(bankCurrencyMapped);
		Mockito.when(bankRepository.save(Matchers.any(PGBank.class))).thenReturn(pgBank);
		Mockito.when(accountRepository.findByEntityTypeAndCurrencyAndStatus(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(account);
		Mockito.when(bankRepository.findByBankName(Matchers.anyString())).thenReturn(pgBank);
		bankDaoImpl.createBank(bankRequest);
	}

	@Test
	public void testCreateBankElse() {
		BankRequest bankRequest = new BankRequest();
		PGAccount response = new PGAccount();
		PGBank bank = new PGBank();
		PGBankCurrencyMapping bankCurrencyMapped = null;
		bank.setId(Long.parseLong("54"));
		Mockito.when(bankRepository.save(Matchers.any(PGBank.class))).thenReturn(bank);
		Mockito.when(bankCurrencyRepository.findByBankId(Matchers.anyLong())).thenReturn(bankCurrencyMapped);
		Mockito.when(accountRepository.save(Matchers.any(PGAccount.class))).thenReturn(response);
		bankDaoImpl.createBank(bankRequest);
	}

	@Test
	public void testCreateBankStatus() {
		BankRequest bankRequest = new BankRequest();
		PGBank bank = new PGBank();
		bank.setStatus("deleted");
		Mockito.when(bankRepository.findByBankName(Matchers.anyString())).thenReturn(bank);
		bankDaoImpl.createBank(bankRequest);
	}

	@Test
	public void testUpdateBank() {
		BankRequest bankRequest = new BankRequest();
		PGBank pgBank = new PGBank();
		Mockito.when(bankRepository.findByBankNameAndStatusNotLike(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgBank);
		bankDaoImpl.updateBank(bankRequest);
	}

	@Test
	public void testUpdateBankNull() {
		BankRequest bankRequest = new BankRequest();
		bankDaoImpl.updateBank(bankRequest);
	}

	@Test
	public void testGetBankByName() {
		bankDaoImpl.getBankByName("abcd");
	}

	@Test
	public void testGetBanklist() {
		BankRequest bankRequest = new BankRequest();
		bankRequest.setNoOfRecords(Integer.parseInt("123"));
		bankRequest.setPageIndex(1);
		bankRequest.setPageSize(Integer.parseInt("23"));
		List<PGBank> pgBankList = new ArrayList<>();
		PGBank bank = new PGBank();
		pgBankList.add(bank);
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("8")];
		objects[0] = "name";
		objects[1] = "name";
		objects[Integer.parseInt("2")] = "name";
		objects[Integer.parseInt("3")] = "phone";
		objects[Integer.parseInt("4")] = new Long("10");
		objects[Integer.parseInt("5")] = "status";
		objects[Integer.parseInt("6")] = "status";
		objects[Integer.parseInt("7")] = "phone";
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(pgBankList, tuplelist);
		bankDaoImpl.getBanklist(bankRequest);
	}

	@Test
	public void testGetBanklistElse() {
		BankRequest bankRequest = new BankRequest();
		bankRequest.setNoOfRecords(Integer.parseInt("123"));
		bankRequest.setPageIndex(1);
		bankRequest.setPageSize(Integer.parseInt("23"));
		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		bankDaoImpl.getBanklist(bankRequest);
	}

	@Test
	public void testGetBanklistException() {
		BankRequest bankRequest = new BankRequest();
		bankDaoImpl.getBanklist(bankRequest);
	}

	@Test
	public void testDeleteBank() {
		PGBank pgBank = new PGBank();
		List<PGMerchant> pgMerchant = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		pgMerchant.add(merchant);
		Mockito.when(bankRepository.findByBankName(BANK_NAME)).thenReturn(pgBank);
		Mockito.when(merchantRepository.findByBankId(Matchers.anyLong())).thenReturn(pgMerchant);
		bankDaoImpl.deleteBank(BANK_NAME);
	}

	@Test
	public void testDeleteBankElse() {
		PGBank pgBank = new PGBank();
		Mockito.when(bankRepository.findByBankName(Matchers.anyString())).thenReturn(pgBank);
		bankDaoImpl.deleteBank("Deleted");
	}

	@Test
	public void testGetBankData() {
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("2")];
		objects[0] = new Long("5");
		objects[1] = "name";
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(tuplelist);
		bankDaoImpl.getBankData();
	}

	@Test
	public void testGetCurrencyByBankId() {
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("3")];
		objects[0] = new Long("5");
		objects[1] = "name";

		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(tuplelist);
		bankDaoImpl.getCurrencyByBankId(Long.parseLong("123"));
	}

	@Test
	public void testGetCurrencyAlpha() {
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		Mockito.when(bankCurrencyConfigRepository.findById(Matchers.anyLong())).thenReturn(pgCurrencyConfig);
		bankDaoImpl.getCurrencyAlpha(Long.parseLong("123"));
	}

	@Test
	public void testGetCurrencyAlphaNull() {
		bankDaoImpl.getCurrencyAlpha(Long.parseLong("431"));
	}

	@Test
	public void testGetBankName() {
		bankDaoImpl.getBankName(Long.parseLong("431"));
	}

	@Test
	public void testCreateOrUpdateBank() {
		PGBank pgBank = new PGBank();
		bankDaoImpl.createOrUpdateBank(pgBank);
	}

}
