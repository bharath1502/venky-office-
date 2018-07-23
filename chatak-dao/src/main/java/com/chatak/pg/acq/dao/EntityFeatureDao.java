/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: May 11, 2018
 * @Time: 1:31:36 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface EntityFeatureDao {
	
	public List<Long> getFeaturesByEntity(String entity);

}
