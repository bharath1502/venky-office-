package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantUsers;
import com.chatak.pg.acq.dao.model.QPGUserRoles;
import com.chatak.pg.acq.dao.repository.ApplicationClientRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.MerchantUserRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("merchantUserDao")
public class MerchantUserDaoImpl implements MerchantUserDao {
	
	@Autowired
	MerchantUserRepository merchantUserRepository;
	
	@Autowired
	MerchantRepository merchantRepository;

	@Autowired
	ApplicationClientRepository applicationClientRepository;
	@PersistenceContext
	private EntityManager entityManager;

	private static Logger logger = Logger.getLogger(MerchantUpdateDaoImpl.class);

	/**
	 *DAO method to authenticate PG service Merchant users
	 * 
	 * @param email
	 * @param pgPass
	 * @return
	 * @throws DataAccessException
	 */
  @Override
  public PGMerchantUsers authenticateMerchant(String email, String pgPass)
      throws DataAccessException {

    try {
      List<PGMerchantUsers> merchantUsers =
          merchantUserRepository.findByUserNameAndMerPassword(email, pgPass);
      if (null != merchantUsers && !merchantUsers.isEmpty()
          && (merchantUsers.get(0).getStatus().equals(PGConstants.STATUS_SUCCESS)
              || merchantUsers.get(0).getEmailVerified().equals(1))) {
        return merchantUsers.get(0);
      }
    } catch (Exception e) {
      logger.error("Error ::MerchantUserDaoImpl :: authenticateMerchant", e);
    }
    return null;
  }

	@Override
	public List<PGMerchant> getMerchant(Long pgMerchantId) throws DataAccessException {
		
		List<PGMerchant> merchantList = null;
		try {
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant).where(isMerchantUserEq( pgMerchantId)).list(QPGMerchant.pGMerchant.id,QPGMerchant.pGMerchant.parentMerchantId, QPGMerchant.pGMerchant.merchantCode);
			
			if (!CollectionUtils.isEmpty(tupleList)) {
				merchantList = new ArrayList<PGMerchant>();
				PGMerchant merchant = null;
				for (Tuple tuple : tupleList) {
					merchant = new PGMerchant();
					merchant.setId(tuple.get(QPGMerchant.pGMerchant.id));
					merchant.setParentMerchantId(tuple.get(QPGMerchant.pGMerchant.parentMerchantId));
					merchant.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));
					merchantList.add(merchant);
				}
			}
			if (merchantList != null && !merchantList.isEmpty()) {
				return merchantList;
			} 
		} catch (Exception e) {
		  logger.error("Error ::MerchantUserDaoImpl :: getMerchant", e);
		}
		logger.info("Exiting ::MerchantUserDaoImpl :: getMerchant");
		return Collections.emptyList();
	}
	
	private BooleanExpression isMerchantUserEq(Long pgMerchantId) { 
		PGMerchantUsers merchantUsers = merchantUserRepository.findById(pgMerchantId);
		return pgMerchantId != null ? QPGMerchant.pGMerchant.pgMerchantUsers.contains(merchantUsers) : null;
	}

  /**
   * @param userName
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGMerchantUsers findByUserName(String userName) throws DataAccessException {
    return merchantUserRepository.findByUserNameAndStatusNotLike(userName,PGConstants.STATUS_DELETED);
  }

  /**
   * @param userId
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGMerchantUsers findByMerchantUserId(Long userId) throws DataAccessException {
    return merchantUserRepository.findById(userId);
  }

  /**
   * @param adminUser
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGMerchantUsers createOrUpdateUser(PGMerchantUsers merchantUsers) throws DataAccessException {
    return merchantUserRepository.save(merchantUsers);
  }

  /**
   * @param adminUserList
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGMerchantUsers> createOrUpdateUsers(List<PGMerchantUsers> merchantUsersList) throws DataAccessException {
    return Collections.emptyList();
  }

  /**
   * @param userId
   * @param token
   * @return
   */
  @Override
  public PGMerchantUsers findByAdminUserIdAndEmailToken(Long userId, String token) {
    return merchantUserRepository.findByIdAndEmailToken(userId, token);
  }

/**
 * @param genericUserDTO
 * @return
 * @throws Exception 
 */
@Override
public List<GenericUserDTO> searchMerchantUsers(GenericUserDTO userTo) {

	Integer pageIndex = userTo.getPageIndex();
	Integer pageSize = userTo.getPageSize();
	Integer limit = 0;
	Integer offset = 0;
	Integer totalRecords;
	List<GenericUserDTO> userRespList = new ArrayList<GenericUserDTO>();

	if (pageIndex == null || pageIndex == 1) {
		totalRecords = getTotalNumberOfRecords(userTo);
		userTo.setNoOfRecords(totalRecords);
	}

	if (pageSize == null && pageIndex == null) {
		offset = 0;
		limit = Constants.DEFAULT_PAGE_SIZE;
	} else {
		offset = (pageIndex - 1) * pageSize;		
		limit = pageSize;
	}

	JPAQuery query = new JPAQuery(entityManager);
	List<Tuple> dataList = query
			.from(QPGMerchantUsers.pGMerchantUsers, QPGUserRoles.pGUserRoles, QPGMerchant.pGMerchant)
			.where(isUserIdEq(userTo.getAdminUserId()),
					isLastNameEq(userTo.getLastName()),
					isFirstNameEq(userTo.getFirstName()),
					isRoleIdEq(userTo.getUserRoleId()),
					isUserNameEq(userTo.getUserName()),
					isPhone(userTo.getPhone()),
					isUserStatusEq(userTo.getStatus()),
					isMerchantUserStatusNotEq(),
					isEmailIdEq(userTo.getEmail()),
					isUserRoleTypeEq(userTo.getUserType()),
					isMerchantCodeEq(userTo.getMerchantCode()),
                    QPGMerchant.pGMerchant.id.eq(QPGMerchantUsers.pGMerchantUsers.pgMerchantId),
					QPGMerchantUsers.pGMerchantUsers.userRoleId
							.eq(QPGUserRoles.pGUserRoles.roleId))
			.offset(offset)
			.limit(limit)
			.orderBy(orderByCreatedDateDesc())
			.list(QPGMerchantUsers.pGMerchantUsers.id,
					QPGMerchantUsers.pGMerchantUsers.status,
					QPGMerchantUsers.pGMerchantUsers.userName,
					QPGMerchantUsers.pGMerchantUsers.email,
					QPGUserRoles.pGUserRoles.roleName,
					QPGMerchantUsers.pGMerchantUsers.firstName,
					QPGMerchantUsers.pGMerchantUsers.lastName,
					QPGMerchantUsers.pGMerchantUsers.phone,
					QPGMerchantUsers.pGMerchantUsers.userRoleType,
					QPGMerchantUsers.pGMerchantUsers.createdDate,
					QPGMerchant.pGMerchant.merchantCode,
					QPGMerchant.pGMerchant.businessName,
					QPGMerchantUsers.pGMerchantUsers.updatedDate);
	GenericUserDTO merchantUserDto = null;
	for (Tuple data : dataList) {
		merchantUserDto = new GenericUserDTO();
		merchantUserDto.setAdminUserId(data.get(QPGMerchantUsers.pGMerchantUsers.id));
		merchantUserDto.setUserName(data.get(QPGMerchantUsers.pGMerchantUsers.userName));
		merchantUserDto.setEmail(data.get(QPGMerchantUsers.pGMerchantUsers.email));
		merchantUserDto.setUserRoleName(data.get(QPGUserRoles.pGUserRoles.roleName));
		merchantUserDto.setStatus(data.get(QPGMerchantUsers.pGMerchantUsers.status));
		merchantUserDto.setFirstName(data.get(QPGMerchantUsers.pGMerchantUsers.firstName));
		merchantUserDto.setLastName(data.get(QPGMerchantUsers.pGMerchantUsers.lastName));
		merchantUserDto.setPhone(data.get(QPGMerchantUsers.pGMerchantUsers.phone));
		merchantUserDto.setUserType(data.get(QPGMerchantUsers.pGMerchantUsers.userRoleType));
		merchantUserDto.setCreatedDate(data.get(QPGMerchantUsers.pGMerchantUsers.createdDate));
		merchantUserDto.setUsersGroup(Constants.USERS_GROUP_MERCHANT);
		merchantUserDto.setMerchantCode(data.get(QPGMerchant.pGMerchant.merchantCode));
		merchantUserDto.setMerchantName(data.get(QPGMerchant.pGMerchant.businessName));
		merchantUserDto.setUpdatedDate(data.get(QPGMerchantUsers.pGMerchantUsers.updatedDate));
		userRespList.add(merchantUserDto);
	}
	return userRespList;

}
private int getTotalNumberOfRecords(GenericUserDTO userTo) {
	JPAQuery query = new JPAQuery(entityManager);
	List<PGMerchantUsers> adminuserList = query
			.from(QPGMerchantUsers.pGMerchantUsers, QPGUserRoles.pGUserRoles, QPGMerchant.pGMerchant)
			.where(isUserIdEq(userTo.getAdminUserId()),
					isLastNameEq(userTo.getLastName()),
					isFirstNameEq(userTo.getFirstName()),
					isRoleIdEq(userTo.getUserRoleId()),
					isUserNameEq(userTo.getUserName()),
					isPhone(userTo.getPhone()),
					isUserStatusEq(userTo.getStatus()),
					isMerchantUserStatusNotEq(),
					isEmailIdEq(userTo.getEmail()),
					isUserRoleTypeEq(userTo.getUserType()),
					isMerchantCodeEq(userTo.getMerchantCode()),
					QPGMerchant.pGMerchant.id.eq(QPGMerchantUsers.pGMerchantUsers.pgMerchantId),
					QPGMerchantUsers.pGMerchantUsers.userRoleId
							.eq(QPGUserRoles.pGUserRoles.roleId))
			.orderBy(orderByUserIdDesc()).list(QPGMerchantUsers.pGMerchantUsers);

	return (adminuserList != null && !adminuserList.isEmpty() ? adminuserList
			.size() : 0);
}

private BooleanExpression isUserIdEq(Long userid) {

	return userid != null ? QPGMerchantUsers.pGMerchantUsers.id
			.eq(userid) : null;
}

private BooleanExpression isRoleIdEq(Long userRoleId) {

	return (userRoleId != null) ? QPGUserRoles.pGUserRoles.roleId
			.eq(userRoleId) : null;
}

private BooleanExpression isUserNameEq(String userName) {

	return (userName != null && !"".equals(userName)) ? QPGMerchantUsers.pGMerchantUsers.userName
			.toUpperCase().like(
					"%" + userName.toUpperCase().replace("*", "") + "%")
			: null;
}

private BooleanExpression isUserRoleTypeEq(String userType) {

	return (userType != null && !"".equals(userType)) ? QPGMerchantUsers.pGMerchantUsers.userRoleType
			.toUpperCase().like(
					"%" + userType.toUpperCase().replace("*", "") + "%")
			: null;
}

private BooleanExpression isMerchantCodeEq(String merchantCode) {

  return (merchantCode != null && !"".equals(merchantCode)) ? QPGMerchant.pGMerchant.merchantCode.eq(merchantCode) : null;
}

private BooleanExpression isEmailIdEq(String emailId) {

	return (emailId != null && !"".equals(emailId)) ? QPGMerchantUsers.pGMerchantUsers.email
			.equalsIgnoreCase(emailId) : null;
}

private BooleanExpression isUserStatusEq(Integer status) {

	return (status != null) ? QPGMerchantUsers.pGMerchantUsers.status.eq(status)
			: null;
}
private BooleanExpression isMerchantUserStatusNotEq(){
	  return(QPGMerchantUsers.pGMerchantUsers.status.ne(Constants.THREE));
}

private BooleanExpression isLastNameEq(String lastName) {

	return (lastName != null && !"".equals(lastName)) ? QPGMerchantUsers.pGMerchantUsers.lastName
			.toUpperCase().like(
					"%" + lastName.toUpperCase().replace("*", "") + "%")
			: null;
}

private BooleanExpression isFirstNameEq(String firstName) {

	return (firstName != null && !"".equals(firstName)) ? QPGMerchantUsers.pGMerchantUsers.firstName
			.toUpperCase().like(
					"%" + firstName.toUpperCase().replace("*", "") + "%")
			: null;
}

private BooleanExpression isPhone(String phone) {
	return (phone != null && !"".equals(phone)) ?  QPGMerchantUsers.pGMerchantUsers.phone
			.toUpperCase().like(
					"%" + phone.toUpperCase().replace("*", "") + "%")
			: null;
}

@Override
public PGMerchantUsers findByEmail(String email) throws DataAccessException {
  return merchantUserRepository.findByEmailIdAndStatusNotLike(email,PGConstants.STATUS_DELETED);
}

@Override
public List<PGMerchantUsers> findByUserNameAndType(String acqU, String userType) throws DataAccessException{
	return merchantUserRepository.findByUserNameAndUserType(acqU, userType);
}
private OrderSpecifier<Long> orderByUserIdDesc() {
	return QPGMerchantUsers.pGMerchantUsers.id.desc();
}

@Override
public List<Long> getRoleListMerchant() {
	return merchantUserRepository.getRoleList();
}

/**
 * @param pgMerchantId
 * @return
 */
@Override
public PGMerchant findById(Long pgMerchantId) {
	return merchantRepository.findById(pgMerchantId);
}

/**
 * @return
 */
@Override
public List<AdminUserDTO> searchMerchantUserList() {
	List<AdminUserDTO> userAdminListData = new ArrayList<AdminUserDTO>();
	List<PGMerchantUsers> userAdminList = merchantUserRepository.findByPassRetryCount(Integer.parseInt("3"));
	AdminUserDTO userData = null;
	if ( null != userAdminList) {
		for (PGMerchantUsers pgMerchantUsers : userAdminList) {
			userData = new AdminUserDTO();

			userData.setUserName(pgMerchantUsers.getUserName());
			userData.setFirstName(pgMerchantUsers.getFirstName());
			userData.setLastName(pgMerchantUsers.getLastName());
			userData.setEmail(pgMerchantUsers.getEmail());

			userAdminListData.add(userData);
		}
	}
	return userAdminListData;
}

private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
  return QPGMerchantUsers.pGMerchantUsers.createdDate.desc();
}

  public PGApplicationClient getApplicationClientAuth(String appAuthUser) {
    return applicationClientRepository.findByAppAuthUser(appAuthUser).get(0);
  }

  @Override
  public void saveOrUpdateApplicationClient(PGApplicationClient applicationClient) {
    LogHelper.logEntry(logger, LoggerMessage.getCallerName());

    if(!StringUtil.isNull(applicationClient)){
        applicationClient.setActiveFrom(DateUtil.getCurrentTimestamp());
        applicationClient.setActiveTill(DateUtil.getCurrentTimestamp());
        applicationClientRepository.save(applicationClient);
        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Updated Application Client");
    }

    LogHelper.logExit(logger, LoggerMessage.getCallerName());
  }
  
}
