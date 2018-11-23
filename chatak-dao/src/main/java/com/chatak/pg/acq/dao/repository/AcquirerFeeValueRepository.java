/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.util.Constants;

/**
 * @Author: Girmiti Software
 * @Date: Feb 6, 2016
 * @Time: 1:38:45 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AcquirerFeeValueRepository extends JpaRepository<PGAcquirerFeeValue,Long>,QueryDslPredicateExecutor<PGAcquirerFeeValue>{
  public List<PGAcquirerFeeValue> findByFeeProgramId(Long feeProgramId);
  
  @Query("select fpv.feePercentageOnly, fpv.flatFee from PGFeeProgram fp, PGAcquirerFeeValue fpv where fp.cardProgramId = :cardProgramId and fp.feeProgramId = fpv.feeProgramId and fp.status = '"+ Constants.ACTIVE +"' ")
  public List<Object> findByFeeProgramIdOnQuery(@Param("cardProgramId") Long cardProgramId);
}
