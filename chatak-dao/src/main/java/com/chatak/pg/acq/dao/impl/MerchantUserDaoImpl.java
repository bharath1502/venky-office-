package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUserFeatureMapping;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantUserFeatureMapping;
import com.chatak.pg.acq.dao.model.QPGMerchantUsers;
import com.chatak.pg.acq.dao.model.QPGUserRoles;
import com.chatak.pg.acq.dao.model.QPgMposFeatures;
import com.chatak.pg.acq.dao.repository.ApplicationClientRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.MerchantUserRepository;
import com.chatak.pg.acq.dao.repository.PGMerchantUserFeatureMappingRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.model.MposFeatures;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
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
	
	@Autowired
	PGMerchantUserFeatureMappingRepository pGMerchantUserFeatureMappingRepository;

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
   * @param userName
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGMerchantUsers getMerchantUserByStatus(String userName) throws DataAccessException {
    return merchantUserRepository.findByUserNameAndStatusLike(userName,PGConstants.STATUS_ACTIVE);
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

  @Override
  public List<GenericUserDTO> searchMerchantUsersForPM(GenericUserDTO userTo, Long entityId) {
    logger.info("MerchantDaoImpl | searchMerchantUsersForPM | Entering");
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    List<GenericUserDTO> merchantList = new ArrayList<>();
    int startIndex = 0;
    int endIndex = 0;
    Integer totalRecords = userTo.getNoOfRecords();

    if (userTo.getPageIndex() == null || userTo.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfMerchantRecordsForPM(userTo, entityId);
      userTo.setNoOfRecords(totalRecords);
    }
    getMerchantListResponse.setNoOfRecords(totalRecords);
    if (userTo.getPageIndex() == null && userTo.getPageSize() == null) {
      startIndex = 0;
    } else {
      startIndex = (userTo.getPageIndex() - 1) * userTo.getPageSize();
      endIndex = userTo.getPageSize() + startIndex;
    }
    int resultIndex = endIndex - startIndex;
    StringBuilder query = new StringBuilder(
        "SELECT * FROM ( SELECT PGMU.USER_ROLE_TYPE, PGUR.ROLE_NAME, PGMU.USER_NAME, PGMU.FIRST_NAME, PGMU.LAST_NAME, PGMU.EMAIL, PGMU.STATUS, PGMU.PHONE, PM.MERCHANT_CODE, PGMU.CREATED_DATE, PGMU.UPDATED_DATE, PGMU.ID, PM.BUSINESS_NAME ")
            .append(" FROM PG_MERCHANT_USERS AS PGMU left JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGMU.PG_MERCHANT_ID = PMEM.MERCHANT_ID "
                + "left JOIN PG_USER_ROLES PGUR ON PGUR.ROLE_ID = PGMU.USER_ROLE_ID left JOIN PG_MERCHANT PM ON PM.ID = PMEM.MERCHANT_ID where PMEM.ENTITY_ID =:entityId")
            .append(" union ")
            .append(" SELECT PGMU.USER_ROLE_TYPE, PGUR.ROLE_NAME, PGMU.USER_NAME, PGMU.FIRST_NAME, PGMU.LAST_NAME, PGMU.EMAIL, PGMU.STATUS, PGMU.PHONE, PM.MERCHANT_CODE, PGMU.CREATED_DATE, PGMU.UPDATED_DATE, PGMU.ID, PM.BUSINESS_NAME ")
            .append(" FROM PG_MERCHANT AS PM left JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PM.ID = PMEM.MERCHANT_ID "
                + "left JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID left JOIN PG_MERCHANT_USERS PGMU ON PGMU.PG_MERCHANT_ID = PMEM.MERCHANT_ID left JOIN PG_USER_ROLES PGUR ON PGUR.ROLE_ID = PGMU.USER_ROLE_ID WHERE PMIM.PM_ID =:entityId ");
    query.append(" )a where 1=1  ");
    merchantFilterParameters(userTo, query);
    query.append("  ORDER BY a.CREATED_DATE DESC");
    query.append("  limit :startIndex,:resultSize");
    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("startIndex", startIndex);
    qry.setParameter("resultSize", resultIndex);
    qry.setParameter("entityId", entityId);
    List<Object> cardProgramResponse = qry.getResultList();
    GenericUserDTO request = null;
    if (StringUtil.isListNotNullNEmpty(cardProgramResponse)) {
      Iterator<Object> itr = cardProgramResponse.iterator();
      while (itr.hasNext()) {
        Object[] object = (Object[]) itr.next();
        request = new GenericUserDTO();
        request.setUserType(object[0].toString());
        request.setUserRoleName(object[1].toString());
        request.setUserName((object[2].toString()));
        request.setFirstName(object[3].toString());
        request.setLastName(object[4].toString());
        request.setEmail(String.valueOf(object[5]));
        request.setStatus(Integer.valueOf(object[6].toString()));
        request.setPhone(object[7].toString());
        request.setMerchantCode(object[8].toString());
        request.setCreatedDate((Timestamp)(object[9]));
        request.setUpdatedDate((Timestamp)(object[10]));
        request.setAdminUserId(Long.valueOf(object[11].toString()));
        request.setMerchantName(object[12].toString());
        merchantList.add(request);
      }
    }
    logger.info("MerchantDaoImpl | getMerchantlist | Exiting");
    return merchantList;
  }

  private void merchantFilterParameters(GenericUserDTO userTo, StringBuilder query) {
    if (!StringUtils.isNullAndEmpty(userTo.getFirstName())) {
      query.append(" and (a.FIRST_NAME= '" + userTo.getFirstName() + "' )");
    }
    if (!StringUtils.isNullAndEmpty(userTo.getLastName())) {
      query.append(" and (a.LAST_NAME= '" + userTo.getLastName() + "' )");
    }
    if (!StringUtils.isNullAndEmpty(userTo.getEmail())) {
      query.append(" and (a.EMAIL= '" + userTo.getEmail() + "' )");
    }
    if (!StringUtils.isNullAndEmpty(userTo.getPhone())) {
      query.append(" and (a.PHONE= '" + userTo.getPhone() + "' )");
    }
    if (!StringUtils.isNullAndEmpty(userTo.getStatus())) {
      query.append(" and (a.STATUS= '" + userTo.getStatus() + "' )");
    }
    if (!StringUtils.isNullAndEmpty(userTo.getMerchantCode())) {
      query.append(" and ( a.MERCHANT_CODE= '" + userTo.getMerchantCode() + "' ) ");
    }
  }

private int getTotalNumberOfMerchantRecordsForPM(GenericUserDTO userTo, Long entityId) {
  StringBuilder query = new StringBuilder("SELECT * FROM ( SELECT PGUR.ROLE_NAME, PGMU.USER_NAME, PGMU.FIRST_NAME, ")
      .append( "PGMU.LAST_NAME, PGMU.EMAIL, PGMU.STATUS, PGMU.CREATED_DATE, PGMU.PHONE, PGMU.ID, PGMU.USER_ROLE_TYPE, PM.MERCHANT_CODE ")
      .append(" FROM PG_MERCHANT_USERS AS PGMU left JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGMU.PG_MERCHANT_ID = PMEM.MERCHANT_ID ")
      .append(" left JOIN PG_USER_ROLES PGUR ON PGUR.ROLE_ID = PGMU.USER_ROLE_ID left JOIN PG_MERCHANT PM ON PM.ID = PMEM.MERCHANT_ID where PMEM.ENTITY_ID =:entityId")
      .append(" union ")
      .append(" SELECT PGUR.ROLE_NAME, PGMU.USER_NAME, PGMU.FIRST_NAME, PGMU.LAST_NAME, PGMU.EMAIL, PGMU.STATUS, PGMU.CREATED_DATE, PGMU.PHONE, PGMU.ID, PGMU.USER_ROLE_TYPE, PM.MERCHANT_CODE ")
      .append(" FROM PG_MERCHANT AS PM left JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PM.ID = PMEM.MERCHANT_ID ")
      .append(" left JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID left JOIN PG_MERCHANT_USERS PGMU ON PGMU.PG_MERCHANT_ID = PMEM.MERCHANT_ID ")
      .append(" left JOIN PG_USER_ROLES PGUR ON PGUR.ROLE_ID = PGMU.USER_ROLE_ID WHERE PMIM.PM_ID=:entityId");
      query.append(" )a where 1=1  ");
  merchantFilterParameters(userTo, query);
  query.append("  ORDER BY a.CREATED_DATE DESC");

  Query qry = entityManager.createNativeQuery(query.toString());
  qry.setParameter("entityId", entityId);
  List<Object> cardProgramResponse = qry.getResultList();
  return (StringUtils.isListNotNullNEmpty(cardProgramResponse) ? cardProgramResponse.size() : 0);
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
	List<PGMerchantUsers> userAdminList = merchantUserRepository.findByPassRetryCountAndStatusNotLike(Integer.parseInt("3"), PGConstants.STATUS_DELETED);
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
    logger.info("Entering :: MerchantUserDaoImpl :: saveOrUpdateApplicationClient");

    if(!StringUtil.isNull(applicationClient)){
        applicationClient.setActiveFrom(DateUtil.getCurrentTimestamp());
        applicationClient.setActiveTill(DateUtil.getCurrentTimestamp());
        applicationClientRepository.save(applicationClient);
        logger.info("Updated Application Client");
    }

    logger.info("Exiting :: MerchantUserDaoImpl :: saveOrUpdateApplicationClient");
  }
  @Override
  public PGMerchantUserFeatureMapping saveOrUpdateUserRoleFeatureMap(
		  PGMerchantUserFeatureMapping pGMerchantUserFeatureMapping) {
    return pGMerchantUserFeatureMappingRepository.save(pGMerchantUserFeatureMapping);
  }
  
  @Override
  public List<MposFeatures> findByRoleId(Long userId) throws DataAccessException {

    List<MposFeatures> mposFeaturesList = null;
    if (userId == null) {
    	return Collections.emptyList();
    }
    try {
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query
          .from(QPgMposFeatures.pgMposFeatures,
              QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping)
          .where(isMposConfigIdEq(userId).and(QPgMposFeatures.pgMposFeatures.id
              .eq(QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.featureId)))
          .list(QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.id,
              QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.featureId,
              QPgMposFeatures.pgMposFeatures.featureName,
              QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.status);

      if (!CollectionUtils.isEmpty(tupleList)) {
        mposFeaturesList = new ArrayList<MposFeatures>();
        MposFeatures mposFeatures = null;
        for (Tuple tuple : tupleList) {
          mposFeatures = new MposFeatures();
          mposFeatures
              .setId(tuple.get(QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.id));
          mposFeatures.setFeatureId(
              tuple.get(QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.featureId));
          mposFeatures.setFeatureName(tuple.get(QPgMposFeatures.pgMposFeatures.featureName));
          mposFeatures.setEnabled(
              (tuple.get(QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.status)));
          mposFeaturesList.add(mposFeatures);
        }
      }
      if (mposFeaturesList != null && !mposFeaturesList.isEmpty()) {
        return mposFeaturesList;
      }
    } catch (Exception e) {
      logger.error("Error ::MerchantUserDaoImpl :: findByRoleId", e);
    }
    logger.info("Exiting ::MerchantUserDaoImpl :: findByRoleId");
    return Collections.emptyList();
  }

  private BooleanExpression isMposConfigIdEq(Long userId) {
    return userId != null
        ? QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.merchantUserID
            .eq(userId.intValue())
        : null;
  }
  
  @Override
  public List<String> findByFeatureStatus(Long userId) throws DataAccessException {

    try {
      JPAQuery query = new JPAQuery(entityManager);
      List<String> tupleList = query
          .from(QPgMposFeatures.pgMposFeatures,
              QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping)
          .where(isMposConfigIdEq(userId),
              isFeatureStatusEq()
              .and(QPgMposFeatures.pgMposFeatures.id
              .eq(QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.featureId)))
          .list(QPgMposFeatures.pgMposFeatures.featureName);
      if (tupleList != null && !tupleList.isEmpty()) {
        return tupleList;
      }
    } catch (Exception e) {
      logger.error("Error ::MerchantUserDaoImpl :: findByRoleId", e);
    }
    logger.info("Exiting ::MerchantUserDaoImpl :: findByRoleId");
    return Collections.emptyList();
  }
  
  private BooleanExpression isFeatureStatusEq() {

    return QPGMerchantUserFeatureMapping.pGMerchantUserFeatureMapping.status.eq(true);
}
}
