package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.StateRequest;
import com.chatak.pg.bean.TimeZoneRequest;

public interface CountryDao {
	public List<CountryRequest> findAllCountries() throws DataAccessException;

	public List<StateRequest> findAllStates(String status) throws DataAccessException;

	public CountryRequest findCountryByID(Long countryId) throws DataAccessException;

	public StateRequest findStateByID(Long StateId) throws DataAccessException;
	
	public CountryRequest getCountryByName(String country)throws DataAccessException;
	
	public List<TimeZoneRequest> findAllTimeZone(Long countryId) throws DataAccessException;
	
	public TimeZoneRequest findTimeZoneByID(Long timeZoneId) throws DataAccessException;
	
	public CountryRequest getPmCountryById(Long countryId)throws DataAccessException;

}
