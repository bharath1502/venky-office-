package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.model.PGCountry;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.model.TimeZone;
import com.chatak.pg.acq.dao.repository.CountryRepository;
import com.chatak.pg.acq.dao.repository.StateRepository;
import com.chatak.pg.acq.dao.repository.TimeZoneRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.StateRequest;
import com.chatak.pg.bean.TimeZoneRequest;
import com.chatak.pg.util.CommonUtil;

@Repository("countryDao")
public class CountryDaoImpl implements CountryDao {

	private static Logger logger = Logger.getLogger(CountryDaoImpl.class);

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private TimeZoneRepository timeZoneRepository;


	@Override
	public List<CountryRequest> findAllCountries() throws DataAccessException {
		List<CountryRequest> list = new ArrayList<CountryRequest>();
		try {
			List<PGCountry> countryList = countryRepository.findAll();
			list = CommonUtil.copyListBeanProperty(countryList, CountryRequest.class);
		} catch (Exception e) {
			logger.error("Error in retrieving the list of countries", e);
		}

		return list;
	}

	@Override
	public List<StateRequest> findAllStates(String status) throws DataAccessException {
		List<StateRequest> list = new ArrayList<StateRequest>();
		try {
			List<PGState> stateList = stateRepository.findByStatus(status);
			list = CommonUtil.copyListBeanProperty(stateList, StateRequest.class);
		} catch (Exception e) {
			logger.error("Error in retrieving the list of states for country id ", e);
		}

		return list;
	}

	@Override
	public CountryRequest findCountryByID(Long countryId) throws DataAccessException {
		CountryRequest countryRequest = null;
		try {
			PGCountry country = countryRepository.findById(countryId).orElse(null);
			if (country!=null) {
				countryRequest = CommonUtil.copyBeanProperties(country, CountryRequest.class);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving the country for country id " + countryId, e);
		}

		return countryRequest;
	}

	@Override
	public StateRequest findStateByID(Long stateId) throws DataAccessException {
		StateRequest stateRequest = null;
		try {
			PGState state = stateRepository.findById(stateId).orElse(null);
			if (state!=null) {
				stateRequest = CommonUtil.copyBeanProperties(state, StateRequest.class);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving the state for state id " + stateId, e);
		}

		return stateRequest;
	}

  @Override
  public CountryRequest getCountryByName(String country) throws DataAccessException {
   PGCountry pgCountry=countryRepository.findByName(country);
   CountryRequest countryRequest=new CountryRequest();
   if(pgCountry!=null) {
    countryRequest.setId(pgCountry.getId());
    countryRequest.setName(pgCountry.getName());
  }
  return countryRequest;
  }
  
  @Override
  public CountryRequest getPmCountryById(Long countryId) throws DataAccessException {
   PGCountry pgCountry =countryRepository.findById(countryId).orElse(null);
   CountryRequest countryRequest=new CountryRequest();
   if(pgCountry!=null) {
    countryRequest.setId(pgCountry.getId());
    countryRequest.setName(pgCountry.getName());
  }
  return countryRequest;
  }

  @Override
	//@Cacheable("getAllStates")
	public List<TimeZoneRequest> findAllTimeZone(Long countryId) throws DataAccessException {
		List<TimeZoneRequest> list = new ArrayList<TimeZoneRequest>();
		try {
			List<TimeZone> timeZoneList = timeZoneRepository.findByCountryId(countryId);
			list = CommonUtil.copyListBeanProperty(timeZoneList, TimeZoneRequest.class);
		} catch (Exception e) {
			logger.error("Error in retrieving the list of time Zone for country id " + countryId, e);
		}

		return list;
	}
	
	@Override
	public TimeZoneRequest findTimeZoneByID(Long timeZoneId) throws DataAccessException{
		TimeZoneRequest timeZoneRequest = null;
		try {
			TimeZone timeZone =  timeZoneRepository.findById(timeZoneId).orElse(null);
			if (timeZone!=null) {
				timeZoneRequest = CommonUtil.copyBeanProperties(timeZone, TimeZoneRequest.class);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving the timeZone for timeZone id " + timeZoneId, e);
		}

		return timeZoneRequest;
	}
}
