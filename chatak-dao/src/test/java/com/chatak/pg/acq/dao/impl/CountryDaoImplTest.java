/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGCountry;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.repository.CountryRepository;
import com.chatak.pg.acq.dao.repository.StateRepository;

/**
 * @Author: Girmiti Software
 * @Date: 17-Feb-2018
 * @Time: 3:14:17 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CountryDaoImplTest {

	@InjectMocks
	CountryDaoImpl countryDaoImpl;

	@Mock
	private CountryRepository countryRepository;

	@Mock
	private StateRepository stateRepository;

	@Test
	public void testFindAllCountries() {
		countryDaoImpl.findAllCountries();
	}

	@Test
	public void testFindAllCountriesException() {
		Mockito.when(countryRepository.findAll()).thenThrow(new NullPointerException());
		countryDaoImpl.findAllCountries();
	}

	@Test
	public void testFindAllStates() {
		countryDaoImpl.findAllStates("abcd");
	}

	@Test
	public void testFindAllStatesException() {
		Mockito.when(stateRepository.findByStatus(Matchers.anyString())).thenThrow(new NullPointerException());
		countryDaoImpl.findAllStates("abcd");
	}

	@Test
	public void testFindCountryByID() {
		List<PGCountry> countryList = new ArrayList<>();
		PGCountry country = new PGCountry();
		countryList.add(country);
		Mockito.when(countryRepository.findById(Matchers.anyLong())).thenReturn(countryList);
		countryDaoImpl.findCountryByID(Long.parseLong("123"));
	}

	@Test
	public void testFindCountryByIDException() {
		Mockito.when(countryRepository.findById(Matchers.anyLong())).thenThrow(new NullPointerException());
		countryDaoImpl.findCountryByID(Long.parseLong("123"));
	}

	@Test
	public void testFindStateByID() {
		List<PGState> stateList = new ArrayList<>();
		PGState pgState = new PGState();
		stateList.add(pgState);
		Mockito.when(stateRepository.findById(Matchers.anyLong())).thenReturn(stateList);
		countryDaoImpl.findStateByID(Long.parseLong("123"));
	}

	@Test
	public void testFindStateByIDException() {
		Mockito.when(stateRepository.findById(Matchers.anyLong())).thenThrow(new NullPointerException());
		countryDaoImpl.findStateByID(Long.parseLong("123"));
	}

	@Test
	public void testGetCountryByName() {
		PGCountry pgCountry = new PGCountry();
		Mockito.when(countryRepository.findByName(Matchers.anyString())).thenReturn(pgCountry);
		countryDaoImpl.getCountryByName("abcd");
	}

}
