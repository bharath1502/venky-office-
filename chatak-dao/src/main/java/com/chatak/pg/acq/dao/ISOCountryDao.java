/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.bean.ISOCountryCodeRequest;

/**
 * @Author: Girmiti Software
 * @Date: Aug 11, 2016
 * @Time: 7:36:25 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface ISOCountryDao {
	public List<ISOCountryCodeRequest> findAllISOCountries() throws DataAccessException;

}
