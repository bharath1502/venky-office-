package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMagneticStripeParameters;

public interface MagneticStripeCradParametersRepository extends JpaRepository<PGMagneticStripeParameters,Long>,QuerydslPredicateExecutor<PGMagneticStripeParameters>
{

	public List<PGMagneticStripeParameters> findByMagneticStripeId(Long magneticStripeId);
}
