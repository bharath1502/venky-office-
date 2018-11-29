/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.IsoAccount;

/**
 * @Author: Girmiti Software
 * @Date: May 9, 2018
 * @Time: 2:29:07 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface IsoAccountRepository extends JpaRepository<IsoAccount, Long>, QueryDslPredicateExecutor<IsoAccount> {

	public List<IsoAccount> findByIsoIdAndAccountType(Long isoId, String accountType);

}
