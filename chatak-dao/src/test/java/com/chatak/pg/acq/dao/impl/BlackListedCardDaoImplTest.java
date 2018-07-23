/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
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

import com.chatak.pg.acq.dao.model.PGBlackListedCard;
import com.chatak.pg.acq.dao.repository.BlackListedCardRepository;
import com.chatak.pg.user.bean.BlackListedCardRequest;

/**
 * @Author: Girmiti Software
 * @Date: 17-Feb-2018
 * @Time: 2:09:40 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BlackListedCardDaoImplTest {

  public static final Long ID = Long.parseLong("12");
  public static final String SWITCH_NAME = "switchName";
  public static final Integer DCC_SUPPORTED = Integer.parseInt("10");
  public static final Integer STATUS = Integer.parseInt("0");
  public static final Long BIN = Long.parseLong("12");
  public static final Integer EMV_SUPPORTED = Integer.parseInt("10");
  public static final Long SWITCH_ID = Long.parseLong("12");
  
	@InjectMocks
	BlackListedCardDaoImpl blackListedCardDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	MessageSource messageSource;

	@Mock
	BlackListedCardRepository blackListedCardRepository;

	@Test
	public void testAddBlackListedCardInfo() {
		BlackListedCardRequest addBlackListedCardRequest = new BlackListedCardRequest();
		PGBlackListedCard pgBlackListedCard = new PGBlackListedCard();
		Mockito.when(blackListedCardRepository.findByCardNumber(Matchers.any(BigInteger.class))).thenReturn(pgBlackListedCard);
		blackListedCardDaoImpl.addBlackListedCardInfo(addBlackListedCardRequest);
	}

	@Test
	public void testAddBlackListedCardInfoNull() {
		BlackListedCardRequest addBlackListedCardRequest = new BlackListedCardRequest();
		blackListedCardDaoImpl.addBlackListedCardInfo(addBlackListedCardRequest);
	}

	@Test
	public void testAddBlackListedCardInfoException() {
		BlackListedCardRequest addBlackListedCardRequest = new BlackListedCardRequest();
		Mockito.when(blackListedCardRepository.findByCardNumber(Matchers.any(BigInteger.class)))
				.thenThrow(new NullPointerException());
		blackListedCardDaoImpl.addBlackListedCardInfo(addBlackListedCardRequest);
	}

	@Test
	public void testUpdateBlackListedCardInformation() {
		BlackListedCardRequest addBlackListedCardRequest = new BlackListedCardRequest();
		PGBlackListedCard pgBlackListedCard = new PGBlackListedCard();
		Mockito.when(blackListedCardRepository.findById(Matchers.anyLong())).thenReturn(pgBlackListedCard);
		blackListedCardDaoImpl.updateBlackListedCardInformation(addBlackListedCardRequest, "123");
	}

	@Test
	public void testUpdateBlackListedCardInformationElse() {
		BlackListedCardRequest addBlackListedCardRequest = new BlackListedCardRequest();
		blackListedCardDaoImpl.updateBlackListedCardInformation(addBlackListedCardRequest, "123");
	}

	@Test
	public void testUpdateBlackListedCardInformationException() {
		BlackListedCardRequest addBlackListedCardRequest = new BlackListedCardRequest();
		PGBlackListedCard pgBlackListedCard = new PGBlackListedCard();
		Mockito.when(blackListedCardRepository.findById(Matchers.anyLong())).thenReturn(pgBlackListedCard);
		Mockito.when(blackListedCardRepository.save(Matchers.any(PGBlackListedCard.class)))
				.thenThrow(new NullPointerException());
		blackListedCardDaoImpl.updateBlackListedCardInformation(addBlackListedCardRequest, "123");
	}

	@Test
	public void testGetBlackListedCardInfoById() {
		blackListedCardDaoImpl.getBlackListedCardInfoById(Long.parseLong("123"));
	}

	@Test
	public void testSearchBlackListedCardInformation() {
		List<BlackListedCardRequest> searchBlackListedCardList = new ArrayList<>();
		BlackListedCardRequest searchBlackListedCardRequest = new BlackListedCardRequest();
		searchBlackListedCardRequest.setPageIndex(null);
		searchBlackListedCardRequest.setNoOfRecords(1);
		searchBlackListedCardList.add(searchBlackListedCardRequest);
		List<Object> tuplelist = new ArrayList<>();
		Object[] objects = new Object[Integer.parseInt("7")];
        objects[Integer.parseInt("0")] = ID;
        objects[Integer.parseInt("1")] = BIN;
        objects[Integer.parseInt("2")] = STATUS;
        objects[Integer.parseInt("3")] = SWITCH_ID;
        objects[Integer.parseInt("4")] = DCC_SUPPORTED;
        objects[Integer.parseInt("5")] = EMV_SUPPORTED;
        objects[Integer.parseInt("6")] = SWITCH_NAME;
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(searchBlackListedCardList, tuplelist);

		blackListedCardDaoImpl.searchBlackListedCardInformation(searchBlackListedCardRequest);
	}

	@Test
	public void testGetCardDataByCardNumber() {
	  BigInteger bigInteger = BigInteger.valueOf(12);
		PGBlackListedCard blackListedCard = new PGBlackListedCard();
		Mockito.when(blackListedCardRepository.findByCardNumberAndStatusNotLike(Matchers.any(BigInteger.class), Matchers.anyInt()))
				.thenReturn(blackListedCard);
		blackListedCardDaoImpl.getCardDataByCardNumber(bigInteger);
	}

	@Test
	public void testCreateOrUpdateBlackListedCard() {
		PGBlackListedCard pgBlackListedCard = new PGBlackListedCard();
		blackListedCardDaoImpl.createOrUpdateBlackListedCard(pgBlackListedCard);

	}

	@Test
	public void testGetCardNumber() {
	  BigInteger bigInteger = BigInteger.valueOf(12);
		blackListedCardDaoImpl.getCardNumber(bigInteger);

	}

}
