/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGBINRange;

/**
 * @Author: Girmiti Software
 * @Date: Jun 10, 2015
 * @Time: 10:23:53 AM
 * @Version: 1.0
 * @Comments:
 */
public interface BINRepository extends JpaRepository<PGBINRange, Long>, QueryDslPredicateExecutor<PGBINRange> {
  @Query("select t from PGBINRange t where t.status=0")
  public List<PGBINRange> getAllActiveBins();

  public List<PGBINRange> findAll();
  
  @Query("select t from PGBINRange t where t.bin=:bin and t.status=0")
  public List<PGBINRange> findByActiveBin( @Param("bin") Long bin);
  
  public PGBINRange findById(Long binId);
  
  public PGBINRange findByBin(Long bin);  
  
 
  

}
