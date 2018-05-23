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
import com.chatak.pg.acq.dao.repository.CountryRepository;
import com.chatak.pg.acq.dao.repository.StateRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.StateRequest;
import com.chatak.pg.util.CommonUtil;

@Repository("countryDao")
public class CountryDaoImpl implements CountryDao {

	private static Logger logger = Logger.getLogger(CountryDaoImpl.class);

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;


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
			List<PGCountry> countryList = countryRepository.findById(countryId);
			if (countryList!=null && !countryList.isEmpty()) {
				countryRequest = CommonUtil.copyBeanProperties(countryList.get(0), CountryRequest.class);
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
			List<PGState> stateList = stateRepository.findById(stateId);
			if (stateList!=null && !stateList.isEmpty()) {
				stateRequest = CommonUtil.copyBeanProperties(stateList.get(0), StateRequest.class);
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

}
