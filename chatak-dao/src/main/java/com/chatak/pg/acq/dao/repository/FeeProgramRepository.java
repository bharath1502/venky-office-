package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFeeProgram;

public interface FeeProgramRepository extends JpaRepository<PGFeeProgram, Long>, QuerydslPredicateExecutor<PGFeeProgram>
{

	public List<PGFeeProgram> findByFeeProgramNameIgnoreCase(String feeProgramName);
	
	public List<PGFeeProgram> findByFeeProgramId(Long feeProgramId);
	
	public List<PGFeeProgram> findByStatus(String status);

	public List<PGFeeProgram> findByStatusOrFeeProgramName(String string, String feeProgramName);
	
	public PGFeeProgram findByFeeProgramName(String feeProgramName);
	
	public List<PGFeeProgram> findByCardProgramId(Long cardProgramId);
}
