/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.UserActivityLogDao;
import com.chatak.pg.acq.dao.model.PGUserActivityLog;
import com.chatak.pg.acq.dao.model.QPGAdminUser;
import com.chatak.pg.acq.dao.model.QPGUserActivityLog;
import com.chatak.pg.acq.dao.repository.UserActivityLogRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.AccessLogsDTO;
import com.chatak.pg.util.DateUtil;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: Sep 15, 2015
 * @Time: 6:05:28 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository
public class UserActivityLogDaoImpl implements UserActivityLogDao {
	@Autowired
	private UserActivityLogRepository userActivityLogRepository;
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @param pgUserActivityLog
	 * @throws DataAccessException
	 */
	@Override
	public void logUserActivity(PGUserActivityLog pgUserActivityLog)
			throws DataAccessException {
		userActivityLogRepository.save(pgUserActivityLog);
	}

	/**
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<AccessLogsDTO> getAllPgUserActivityLogs()
			throws DataAccessException {
		List<AccessLogsDTO> logsList = null;
		JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
		List<Tuple> infoList = query.distinct().from(QPGUserActivityLog.pGUserActivityLog,QPGAdminUser.pGAdminUser)
				.select(QPGAdminUser.pGAdminUser.email,QPGUserActivityLog.pGUserActivityLog.activityDate,QPGUserActivityLog.pGUserActivityLog.ipAddress)
				.where(QPGUserActivityLog.pGUserActivityLog.userId.eq(QPGAdminUser.pGAdminUser.adminUserId.stringValue()))
				.orderBy(QPGUserActivityLog.pGUserActivityLog.activityDate.desc()).fetch();
		if (StringUtil.isListNotNullNEmpty(infoList)) {
			logsList = new ArrayList<AccessLogsDTO>();
			for (Tuple tuple : infoList) {
				AccessLogsDTO logDTO = new AccessLogsDTO();
				logDTO.setDateTime(DateUtil.toDateStringFormat(tuple.get(QPGUserActivityLog.pGUserActivityLog.activityDate), DateUtil.VIEW_DATE_TIME_FORMAT));
				logDTO.setUserName(tuple.get(QPGAdminUser.pGAdminUser.email));
				logDTO.setIpAddress(tuple.get(QPGUserActivityLog.pGUserActivityLog.ipAddress));
				logsList.add(logDTO);
			}
		}
		return logsList;
	}

}
