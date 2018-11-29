/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.model.Iso;

/**
 * @Author: Girmiti Software
 * @Date: May 8, 2018
 * @Time: 5:09:34 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IsoRepository extends JpaRepository<Iso, Long>, QueryDslPredicateExecutor<Iso>{

	public List<Iso> findByIsoName(String isoName);
	public List<Iso> findById(Long isoId);
	
	@Query("select status from Iso iso where iso.id = :isoId")
	public String findISOStatusById(@Param("isoId")Long isoId);
	
	@Modifying
	@Transactional
	@Query("update Iso iso set iso.status= :status, iso.reason = :reason, iso.updatedBy = :updatedBy, iso.updatedDate = :updatedDate where iso.id = :isoId")
    public int updateISOStatusById(@Param("isoId")Long isoId, @Param("reason")String reason, 
        @Param("updatedBy")String updatedBy, @Param("updatedDate")Timestamp updatedDate, @Param("status")String status);
}
