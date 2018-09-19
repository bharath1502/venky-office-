/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.model.IsoCardProgramMap;

/**
 * @Author: Girmiti Software
 * @Date: May 11, 2018
 * @Time: 1:17:23 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IsoCardProgramMapRepository extends JpaRepository<IsoCardProgramMap, Long>{

	@Modifying
	@Transactional
	@Query("delete from IsoCardProgramMap isoCpMap where isoCpMap.isoId = :isoId")
	public void deleteByIsoId(@Param("isoId")Long isoId);
	
	@Query("select ambiguityPmId from IsoCardProgramMap isoCpMap where isoCpMap.isoId = :isoId and isoCpMap.cardProgramId = :cardProgramId")
	public Long findByIsoIdAndCardProgramId(@Param("isoId")Long isoId,@Param("cardProgramId")Long cardProgramId);
}
