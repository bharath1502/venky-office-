/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.model.IsoPmMap;

/**
 * @Author: Girmiti Software
 * @Date: May 11, 2018
 * @Time: 1:10:02 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IsoPmMapRepository extends JpaRepository<IsoPmMap, Long>{

	@Modifying
	@Transactional
	@Query("delete from IsoPmMap isoPmMap where isoPmMap.isoId = :isoId")
	public void deleteByIsoId(@Param("isoId")Long isoId);
	
	public List<IsoPmMap> findByPmId(Long pmId);
	
	public List<IsoPmMap> findByIsoId(Long isoId);
}
