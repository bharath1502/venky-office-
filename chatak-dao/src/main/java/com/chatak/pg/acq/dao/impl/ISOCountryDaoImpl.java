/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ISOCountryDao;
import com.chatak.pg.acq.dao.model.PGIsoCountryCode;
import com.chatak.pg.acq.dao.repository.IsoCountryCodeRepository;
import com.chatak.pg.bean.ISOCountryCodeRequest;
import com.chatak.pg.util.CommonUtil;

/**
 * @Author: Girmiti Software
 * @Date: Aug 11, 2016
 * @Time: 7:40:58 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository
public class ISOCountryDaoImpl implements ISOCountryDao {
	private static Logger logger = Logger.getLogger(ISOCountryDaoImpl.class);
	@Autowired
	private IsoCountryCodeRepository isoCountryCodeRepository;
	/**
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<ISOCountryCodeRequest> findAllISOCountries()
			throws DataAccessException {
		List<ISOCountryCodeRequest> isoCountryCodeRequests=new ArrayList<>();
		
		List<PGIsoCountryCode> list=isoCountryCodeRepository.findAll();
		try {
			isoCountryCodeRequests = CommonUtil.copyListBeanProperty(list, ISOCountryCodeRequest.class);
		} catch (InstantiationException e) {
			logger.error("Error in retrieving the list of ISOcountries", e);
		} catch (IllegalAccessException e) {
			logger.error("Error in retrieving the list of ISOcountries", e);
		}
		return isoCountryCodeRequests;
	}

}
