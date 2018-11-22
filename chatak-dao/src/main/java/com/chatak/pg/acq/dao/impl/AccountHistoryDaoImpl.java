/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.QPGAccountHistory;
import com.chatak.pg.acq.dao.repository.AccountHistoryRepository;
import com.chatak.pg.user.bean.MerchantAccountHistory;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Jun 16, 2015
 * @Time: 10:53:38 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class AccountHistoryDaoImpl implements AccountHistoryDao {
  @Autowired
  AccountHistoryRepository accountHistoryRepository;

  @PersistenceContext
	private EntityManager entityManager;
  /**
   * @param accountHistory
   * @throws DataAccessException
   */
  @Override
  public void createOrSave(PGAccountHistory accountHistory) throws DataAccessException {
    accountHistoryRepository.save(accountHistory);
  }

  /**
   * @param accountNumber
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGAccountHistory> getHistoryByAccountNum(Long accountNumber) throws DataAccessException {
    return accountHistoryRepository.findByAccountNumOrderByUpdatedDateDesc(accountNumber);
  }

/**
 * @param merchantAccountHistory
 * @return
 * @throws Exception
 */
@Override
public List<PGAccountHistory> SearchAccountHistory(MerchantAccountHistory merchantAccountHistory) {
	Integer pageIndex = merchantAccountHistory.getPageIndex();
	Integer pageSize = merchantAccountHistory.getPageSize();
	Integer offset = 0;
	Integer limit = 0;
	Integer totalRecords;
	List<PGAccountHistory> accoHistList = new ArrayList<PGAccountHistory>();

	if (pageIndex == null || pageIndex == 1) {
		totalRecords = getTotalNumberOfRecords(merchantAccountHistory);
		merchantAccountHistory.setNoOfRecords(totalRecords);
	}

	if (pageIndex == null && pageSize == null) {
		offset = 0;
		limit = Constants.DEFAULT_PAGE_SIZE;
	} else {
		offset = (pageIndex - 1) * pageSize;
		limit = pageSize;
	}

	JPAQuery query = new JPAQuery(entityManager);
	List<Tuple> dataList = query
			.from(QPGAccountHistory.pGAccountHistory)
			.where(isaccountNum(merchantAccountHistory.getAccountNum()))
			.offset(offset)
			.limit(limit)
			.orderBy(orderByUpdatedDateDesc())
			.list(QPGAccountHistory.pGAccountHistory.accountNum,
					QPGAccountHistory.pGAccountHistory.currency,
					QPGAccountHistory.pGAccountHistory.availableBalance,
					QPGAccountHistory.pGAccountHistory.status,
					QPGAccountHistory.pGAccountHistory.currentBalance,
					QPGAccountHistory.pGAccountHistory.feeBalance,
					QPGAccountHistory.pGAccountHistory.paymentMethod,
					QPGAccountHistory.pGAccountHistory.updatedDate);
	PGAccountHistory accounthistory = null;
	for (Tuple data : dataList) {
		accounthistory = new PGAccountHistory();
		accounthistory.setAccountNum(data
				.get(QPGAccountHistory.pGAccountHistory.accountNum));
		accounthistory.setCurrency(data.get(QPGAccountHistory.pGAccountHistory.currency));
		accounthistory.setAvailableBalance(data.get(QPGAccountHistory.pGAccountHistory.availableBalance));
		accounthistory.setCurrentBalance(data.get(QPGAccountHistory.pGAccountHistory.currentBalance));
		accounthistory.setFeeBalance(data.get(QPGAccountHistory.pGAccountHistory.feeBalance));
		accounthistory.setStatus(data.get(QPGAccountHistory.pGAccountHistory.status));
		accounthistory.setPaymentMethod(data.get(QPGAccountHistory.pGAccountHistory.paymentMethod));
		accounthistory.setUpdatedDate(data.get(QPGAccountHistory.pGAccountHistory.updatedDate));
		accoHistList.add(accounthistory);
	}
	return accoHistList;
}

private int getTotalNumberOfRecords(MerchantAccountHistory merchantAccountHistory) {
	JPAQuery query = new JPAQuery(entityManager);
	List<PGAccountHistory> accountList = query
			.from(QPGAccountHistory.pGAccountHistory)
			.where(isaccountNum(merchantAccountHistory.getAccountNum()))
			.orderBy(orderByUpdatedDateDesc()).list(QPGAccountHistory.pGAccountHistory);

	return (accountList != null && !accountList.isEmpty() ? accountList
			.size() : 0);
}

private BooleanExpression isaccountNum(Long accountNum) {

	return accountNum != null ? QPGAccountHistory.pGAccountHistory.accountNum
			.eq(accountNum) : null;
}
private OrderSpecifier<Timestamp> orderByUpdatedDateDesc() {
	return QPGAccountHistory.pGAccountHistory.updatedDate.desc();
}

}
