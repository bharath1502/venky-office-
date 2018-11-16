package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGCaPublicKeys;

public interface CAPublicKeysRepository extends
		JpaRepository<PGCaPublicKeys, Long>,
		QuerydslPredicateExecutor<PGCaPublicKeys> {
	public PGCaPublicKeys findByPublicKeyId(Long publicKeyId);

	public PGCaPublicKeys findByPublicKeyName(String publicKeyName);
}
