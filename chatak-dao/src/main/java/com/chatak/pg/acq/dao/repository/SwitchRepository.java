package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGSwitch;

/**
 *
 * DAO Repository class to process Switch
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface SwitchRepository extends JpaRepository<PGSwitch, Long>,
QuerydslPredicateExecutor<PGSwitch> {

	public List<PGSwitch> findBySwitchName(String switchName);

	public List<PGSwitch> findByStatus(Integer status);

	public Optional<PGSwitch> findById(Long id);

}
