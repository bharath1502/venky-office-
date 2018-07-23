package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.QPGAdminUser;
import com.chatak.pg.acq.dao.model.QPGUserRoles;
import com.chatak.pg.acq.dao.repository.AdminUserDaoRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("adminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {
  
    private static Logger logger = Logger.getLogger(AdminUserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    AdminUserDaoRepository adminUserDaoRepository;

    @Override
    public PGAdminUser findByUserNameAndUserType(String userName,
            String userType) throws DataAccessException {

        return adminUserDaoRepository.findByUserNameAndUserType(userName,
                userType);
    }

    @Override
    public List<PGAdminUser> findByUserName(String userName)
            throws DataAccessException {

        return adminUserDaoRepository.findByUserNameIgnoreCase(userName);
    }

    @Override
    public List<PGAdminUser> findByEmail(String email, String userType)
            throws DataAccessException {

        return adminUserDaoRepository.findByEmailIgnoreCaseAndUserType(email,
                userType);
    }
    
    @Override
    public PGAdminUser findByEmailAndUserType(String email, String userType)
            throws DataAccessException {
        return adminUserDaoRepository.findByEmailAndUserType(email, userType);
    }

    @Override
    public PGAdminUser findByAdminUserId(Long userId)
            throws DataAccessException {
        return adminUserDaoRepository.findByAdminUserId(userId);
    }

    @Override
    public PGAdminUser createOrUpdateUser(PGAdminUser adminUser)
            throws DataAccessException {
        return adminUserDaoRepository.save(adminUser);
    }

    @Override
    public List<PGAdminUser> findByUserRoleId(Long userRoleId)
            throws DataAccessException {
        return adminUserDaoRepository.findByUserRoleId(userRoleId);
    }

    @Override
    public List<PGAdminUser> createOrUpdateUsers(List<PGAdminUser> adminUserList)
            throws DataAccessException {
        return adminUserDaoRepository.save(adminUserList);

    }

    @Override
    public List<AdminUserDTO> searchUser(AdminUserDTO userTo) {
        Integer pageIndex = userTo.getPageIndex();
        Integer pageSize = userTo.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords;
        List<AdminUserDTO> userRespList = new ArrayList<AdminUserDTO>();

        if (pageIndex == 1 || pageIndex == null) {
            totalRecords = getTotalNumberOfAdminRecords(userTo);
            userTo.setNoOfRecords(totalRecords);
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
                .from(QPGAdminUser.pGAdminUser, QPGUserRoles.pGUserRoles)
                .where(isAdminUserIdEq(userTo.getAdminUserId()),
                        isLastNameEq(userTo.getLastName()),
                        isFirstNameEq(userTo.getFirstName()),
                        isUserTypeEq(userTo.getUserType()),
                        isRoleIdEq(userTo.getUserRoleId()),
                        isUserNameEq(userTo.getUserName()),
                        isPhone(userTo.getPhone()),
                        isUserStatusEq(userTo.getStatus()),
                        isEmailIdEq(userTo.getEmail()),
                        QPGAdminUser.pGAdminUser.userRoleId
                                .eq(QPGUserRoles.pGUserRoles.roleId))
                .offset(offset)
                .limit(limit)
                .orderBy(orderByCreatedDateDesc())
                .list(QPGAdminUser.pGAdminUser.adminUserId,
                        QPGAdminUser.pGAdminUser.firstName,
                        QPGAdminUser.pGAdminUser.lastName,
                        QPGAdminUser.pGAdminUser.status,
                        QPGAdminUser.pGAdminUser.phone,
                        QPGAdminUser.pGAdminUser.userName,
                        QPGAdminUser.pGAdminUser.email,
                        QPGUserRoles.pGUserRoles.roleName);
        AdminUserDTO adminUserDto = null;
        for (Tuple data : dataList) {
            adminUserDto = new AdminUserDTO();
            adminUserDto.setAdminUserId(data
                    .get(QPGAdminUser.pGAdminUser.adminUserId));
            adminUserDto.setPhone(data.get(QPGAdminUser.pGAdminUser.phone));
            adminUserDto.setUserName(data.get(QPGAdminUser.pGAdminUser.userName));
            adminUserDto.setEmail(data.get(QPGAdminUser.pGAdminUser.email));
            adminUserDto.setUserRoleName(data.get(QPGUserRoles.pGUserRoles.roleName));
            adminUserDto.setFirstName(data.get(QPGAdminUser.pGAdminUser.firstName));
            adminUserDto.setLastName(data.get(QPGAdminUser.pGAdminUser.lastName));
            adminUserDto.setStatus(data.get(QPGAdminUser.pGAdminUser.status));
            
            userRespList.add(adminUserDto);
        }
        return userRespList;
    }

    private BooleanExpression isAdminUserIdEq(Long userid) {

        return userid != null ? QPGAdminUser.pGAdminUser.adminUserId
                .eq(userid) : null;
    }
    
    private BooleanExpression isAdminUserIdNotEq(Long userid) {

        return userid != null ? QPGAdminUser.pGAdminUser.adminUserId
                .ne(userid) : null;
    }

    private BooleanExpression isRoleIdEq(Long userRoleId) {

        return (userRoleId != null) ? QPGUserRoles.pGUserRoles.roleId
                .eq(userRoleId) : null;
    }

    private BooleanExpression isLastNameEq(String lastName) {

        return (lastName != null && !"".equals(lastName)) ? QPGAdminUser.pGAdminUser.lastName
                .toUpperCase().like(
                        "%" + lastName.toUpperCase().replace("*", "") + "%")
                : null;
    }

    private BooleanExpression isFirstNameEq(String firstName) {

        return (firstName != null && !"".equals(firstName)) ? QPGAdminUser.pGAdminUser.firstName
                .toUpperCase().like(
                        "%" + firstName.toUpperCase().replace("*", "") + "%")
                : null;
    }

    private BooleanExpression isUserTypeEq(String userType) {

        return (userType != null && !"".equals(userType)) ? QPGAdminUser.pGAdminUser.userType
                .equalsIgnoreCase(userType) : null;
    }

    private BooleanExpression isUserNameEq(String userName) {

        return (userName != null && !"".equals(userName)) ? QPGAdminUser.pGAdminUser.userName
                .toUpperCase().like(
                        "%" + userName.toUpperCase().replace("*", "") + "%")
                : null;
    }

    private BooleanExpression isEmailIdEq(String emailId) {

        return (emailId != null && !"".equals(emailId)) ? QPGAdminUser.pGAdminUser.email
                .equalsIgnoreCase(emailId) : null;
    }

    private BooleanExpression isUserStatusEq(Integer status) {

        return (status != null) ? QPGAdminUser.pGAdminUser.status.eq(status)
                : null;
    }
     private BooleanExpression isUserStatusNotEq(){
          return(QPGAdminUser.pGAdminUser.status.ne(Integer.parseInt("3")));
      }


    private BooleanExpression isPhone(String phone) {
        return (phone != null && !"".equals(phone)) ? QPGAdminUser.pGAdminUser.phone
                .toUpperCase().like(
                        "%" + phone.toUpperCase().replace("*", "") + "%")
                : null;
    }

    private OrderSpecifier<Long> orderByUserIdAsc() {
        return QPGAdminUser.pGAdminUser.adminUserId.asc();
    }
    
    
    private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
        return QPGAdminUser.pGAdminUser.createdDate.desc();
      }

    private int getTotalNumberOfAdminRecords(AdminUserDTO userTO) {
        JPAQuery query = new JPAQuery(entityManager);
        List<PGAdminUser> adminuserList = query
                .from(QPGAdminUser.pGAdminUser, QPGUserRoles.pGUserRoles)
                .where(isAdminUserIdEq(userTO.getAdminUserId()),
                        isRoleIdEq(userTO.getUserRoleId()),
                        isLastNameEq(userTO.getLastName()),
                        isFirstNameEq(userTO.getFirstName()),
                        isUserNameEq(userTO.getUserName()),
                        isUserStatusEq(userTO.getStatus()),
                        isPhone(userTO.getPhone()),
                        isEmailIdEq(userTO.getEmail()),
                        QPGAdminUser.pGAdminUser.userRoleId
                                .eq(QPGUserRoles.pGUserRoles.roleId))
                .orderBy(orderByUserIdAsc()).list(QPGAdminUser.pGAdminUser);

        return (adminuserList != null && !adminuserList.isEmpty() ? adminuserList
                .size() : 0);
    }

    /**
     * DAO method to authenticate PG service Acquirer admin users
     * 
     * @param email
     * @param pgPass
     * @return
     * @throws DataAccessException
     */
    @Override
    public boolean authenticateAcquirerAdmin(String email, String pgPass)
            throws DataAccessException {
        boolean isAuthenticated = false;
        try {
            List<PGAdminUser> adminUsers = adminUserDaoRepository
                    .findByEmailAndPassword(email, pgPass);
            if (null != adminUsers && !adminUsers.isEmpty()) {
                isAuthenticated = true;
            }
        } catch (Exception e) {
          logger.error("ERROR:: AdminUserDaoImpl:: authenticateAcquirerAdmin method", e);
        }
        return isAuthenticated;
    }

    /**
     * DAO method to authenticate PG service Acquirer admin users
     * 
     * @param email
     * @param pgPass
     * @return
     * @throws DataAccessException
     */
    @Override
    public PGAdminUser authenticateAcquirerAdminUser(String email, String pgPass)
            throws DataAccessException {
        try {
            List<PGAdminUser> adminUsers = adminUserDaoRepository
                    .findByEmailAndPassword(email, pgPass);
            if (null != adminUsers && !adminUsers.isEmpty()
                && adminUsers.get(0).getStatus() == PGConstants.STATUS_SUCCESS) {
                return adminUsers.get(0);
            }
        } catch (Exception e) {
          logger.error("ERROR:: AdminUserDaoImpl:: authenticateAcquirerAdminUser method", e);
        }
        return null;
    }

    @Override
    public PGAdminUser findByEmail(String email) {
        return adminUserDaoRepository.findByEmailIdAndStatusNotLike(email, PGConstants.STATUS_DELETED);
    }

    @Override
    public PGAdminUser findByAdminUserIdAndEmailToken(Long userId, String token) {
        // TODO Auto-generated method stub
        return adminUserDaoRepository.findByAdminUserIdAndEmailToken(userId, token);
    }
    @Override
    public List<GenericUserDTO> searchGenericUser(GenericUserDTO userTo) {
        Integer pageIndex = userTo.getPageIndex();
        Integer pageSize = userTo.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords;
        List<GenericUserDTO> userRespList = new ArrayList<GenericUserDTO>();

        if (pageIndex == null || pageIndex == 1) {
            totalRecords = getTotalNumberOfUserRecords(userTo);
            userTo.setNoOfRecords(totalRecords);
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
                .from(QPGAdminUser.pGAdminUser, QPGUserRoles.pGUserRoles)
                .where(isAdminUserIdNotEq(userTo.getAdminUserId()),
                        isLastNameEq(userTo.getLastName()),
                        isFirstNameEq(userTo.getFirstName()),
                        isUserTypeEq(userTo.getUserType()),
                        isRoleIdEq(userTo.getUserRoleId()),
                        isUserNameEq(userTo.getUserName()),
                        isPhone(userTo.getPhone()),
                        isUserStatusEq(userTo.getStatus()),
                        isUserStatusNotEq(),
                        isEmailIdEq(userTo.getEmail()),
                        QPGAdminUser.pGAdminUser.userRoleId
                                .eq(QPGUserRoles.pGUserRoles.roleId),
                                isEntityIdEq(userTo.getEntityId()))
                .offset(offset)
                .limit(limit)
                .orderBy(orderByCreatedDateDesc())
                .list(QPGAdminUser.pGAdminUser.adminUserId,
                        QPGAdminUser.pGAdminUser.firstName,
                        QPGAdminUser.pGAdminUser.lastName,
                        QPGAdminUser.pGAdminUser.status,
                        QPGAdminUser.pGAdminUser.phone,
                        QPGAdminUser.pGAdminUser.userName,
                        QPGAdminUser.pGAdminUser.email,
                        QPGUserRoles.pGUserRoles.roleName,
                        QPGAdminUser.pGAdminUser.userType,
                        QPGAdminUser.pGAdminUser.createdDate,
                        QPGAdminUser.pGAdminUser.updatedDate);
        GenericUserDTO adminUserDto = null;
        for (Tuple data : dataList) {
            adminUserDto = new GenericUserDTO();
            adminUserDto.setAdminUserId(data
                    .get(QPGAdminUser.pGAdminUser.adminUserId));
            adminUserDto.setPhone(data.get(QPGAdminUser.pGAdminUser.phone));
            adminUserDto.setUserName(data.get(QPGAdminUser.pGAdminUser.userName));
            adminUserDto.setEmail(data.get(QPGAdminUser.pGAdminUser.email));
            adminUserDto.setUserRoleName(data.get(QPGUserRoles.pGUserRoles.roleName));
            adminUserDto.setFirstName(data.get(QPGAdminUser.pGAdminUser.firstName));
            adminUserDto.setLastName(data.get(QPGAdminUser.pGAdminUser.lastName));
            adminUserDto.setStatus(data.get(QPGAdminUser.pGAdminUser.status));
            adminUserDto.setUserType(data.get(QPGAdminUser.pGAdminUser.userType));
            adminUserDto.setCreatedDate(data.get(QPGAdminUser.pGAdminUser.createdDate));
            adminUserDto.setUsersGroup(Constants.USERS_GROUP_ADMIN);
            adminUserDto.setUpdatedDate(data.get(QPGAdminUser.pGAdminUser.updatedDate));
            
            userRespList.add(adminUserDto);
        }
        return userRespList;
    }
    
    private int getTotalNumberOfUserRecords(GenericUserDTO userTO) {
        JPAQuery query = new JPAQuery(entityManager);
        List<PGAdminUser> adminuserList = query
                .from(QPGAdminUser.pGAdminUser, QPGUserRoles.pGUserRoles)
                .where(isAdminUserIdNotEq(userTO.getAdminUserId()),
                        isRoleIdEq(userTO.getUserRoleId()),
                        isLastNameEq(userTO.getLastName()),
                        isFirstNameEq(userTO.getFirstName()),
                        isUserNameEq(userTO.getUserName()),
                        isUserStatusEq(userTO.getStatus()),
                        isUserStatusNotEq(),
                        isPhone(userTO.getPhone()),
                        isEmailIdEq(userTO.getEmail()),
                        isUserTypeEq(userTO.getUserType()),
                        QPGAdminUser.pGAdminUser.userRoleId
                                .eq(QPGUserRoles.pGUserRoles.roleId),
                                isEntityIdEq(userTO.getEntityId()))
                .orderBy(orderByCreatedDateDesc()).list(QPGAdminUser.pGAdminUser);

        return (adminuserList != null && !adminuserList.isEmpty() ? adminuserList
                .size() : 0);
    }
    
    @Override
    public List<Long> getRoleListAdmin() {
        return adminUserDaoRepository.getRoleList();
    }
    
    @Override
    public PGAdminUser findByUserNameAndStatus(String userName) {
        return adminUserDaoRepository.findByUserNameAndStatusNotLike(userName, PGConstants.STATUS_DELETED);
    }

    /**
     * @return
     */
    @Override
    public List<AdminUserDTO> searchAdminUserList() {
        List<AdminUserDTO> userAdminListData = new ArrayList<AdminUserDTO>();
        List<PGAdminUser> userAdminList = adminUserDaoRepository.findByPassRetryCount(Integer.parseInt("3"));
        AdminUserDTO userData = null;
        if ( null != userAdminList) {
            for (PGAdminUser pgadminuser : userAdminList) {
                userData = new AdminUserDTO();

                userData.setUserName(pgadminuser.getUserName());
                userData.setFirstName(pgadminuser.getFirstName());
                userData.setLastName(pgadminuser.getLastName());
                userData.setEmail(pgadminuser.getEmail());

                userAdminListData.add(userData);
            }
        }
        return userAdminListData;
        
    }
    
    private BooleanExpression isEntityIdEq(Long entityId) {

        return (entityId != null) ? QPGAdminUser.pGAdminUser.entityId
                .eq(entityId) : null;
    }

}
