package com.chatak.pg.acq.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.UserRoleDao;
import com.chatak.pg.acq.dao.model.QUserRole;
import com.chatak.pg.acq.dao.model.UserRole;
import com.chatak.pg.acq.dao.repository.UserRoleDaoRepository;
import com.chatak.pg.constants.Status;
import com.chatak.pg.model.RoleDTO;
import com.chatak.pg.util.Constants;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends JdbcDaoSupport implements UserRoleDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  UserRoleDaoRepository userRoleDaoRepository;

  @Autowired
  private DataSource dataSource;


  @PostConstruct
  void init() {
    setDataSource(dataSource);
  }

  public UserRole findByUserRoleId(Long userRoleId) {
    return userRoleDaoRepository.findByUserRoleId(userRoleId);
  }


  public List<UserRole> findByRoleName(String roleName) throws DataAccessException {
    return userRoleDaoRepository.findByRoleNameIgnoreCase(roleName);
  }

  public UserRole createRole(final UserRole role, Long[] featureMapList)
      throws DataAccessException {
    Long roleId = null;
    final String sql =
        "insert into user_role (role_name, description, role_type, maker_checker_req, status, created_date,created_by)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();
    getJdbcTemplate().update(new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(sql, new String[] {"user_role_id"});) {
          pst.setString(1, role.getRoleName());
          pst.setString(Integer.parseInt("2"), role.getDescription());
          pst.setString(Integer.parseInt("3"), role.getRoleType());
          pst.setString(Integer.parseInt("4"), role.getMakerCheckerReq());
          pst.setString(Integer.parseInt("5"), role.getStatus());
          pst.setTimestamp(Integer.parseInt("6"), role.getCreatedDate());
          pst.setString(Integer.parseInt("7"), role.getCreatedBy());
          return pst;
        } catch (Exception e) {
          logger.error("Error :: UserRoleDaoImpl :: createRole", e);
        }
        return null;
      }
    }, keyHolder);

    roleId = (Long) keyHolder.getKey();

    if (featureMapList != null) {
      insertfeatureMapList(featureMapList, roleId, role.getCreatedDate(), role.getCreatedBy());
    }
    role.setUserRoleId(roleId);
    return role;
  }


  public UserRole updateRole(UserRole role, Long[] featureMapList) throws DataAccessException {
    String sql =
        "update user_role set role_name=?, description=?, role_type=?, maker_checker_req=?, status=?, updated_date=?,updated_by=?"
            + " where user_role_id=?";
    int count = getJdbcTemplate().update(sql,
        new Object[] {role.getRoleName(), role.getDescription(), role.getRoleType(),
            role.getMakerCheckerReq(), Constants.ACTIVE, role.getUpdatedDate(),
            role.getUpdatedBy(), role.getUserRoleId()});

    if (count > 0) {
      logger.info("Role updated successfully for role Id: " + role.getUserRoleId());
    }

    if (featureMapList != null) {
      insertfeatureMapList(featureMapList, role.getUserRoleId(), role.getUpdatedDate(),
          role.getUpdatedBy());
      logger.info("Rolefeature updated successfully for role Id: " + role.getUserRoleId());
    }
    return role;
  }

  public List<UserRole> searchRole(RoleDTO userRole) {

    Integer pageIndex = userRole.getPageIndex();
    Integer pageSize = userRole.getPageSize();
    Integer totalRecords;

    int offset = 0;
    int limit = 0;

    if (pageIndex == null || pageIndex == 1) {
      totalRecords = getTotalNumberOfRecords(userRole);
      userRole.setNoOfRecords(totalRecords);
    }

    if (pageIndex == null && pageSize == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (pageIndex - 1) * pageSize;
      limit = pageSize;
    }

    JPAQuery query = new JPAQuery(entityManager);
    List<UserRole> roleList = query.from(QUserRole.userRole)
        .where(isUserRoleIdEq(userRole.getUserRoleId()), isUserRoleNameEq(userRole.getRoleName()),
            isUserRoleStatusEq(userRole.getStatus()), isUserRoleTypeEq(userRole.getRoleType()))
        .offset(offset).limit(limit).orderBy(orderByRoleIdAsc()).list(QUserRole.userRole);
    return roleList;

  }

  private BooleanExpression isUserRoleIdEq(Long roleId) {

    return (roleId != null && !"".equals(roleId.toString())) ? QUserRole.userRole.userRoleId.eq(roleId) : null;
  }

  private BooleanExpression isUserRoleNameEq(String roleName) {

    return (roleName != null && !"".equals(roleName)) ? QUserRole.userRole.roleName.toUpperCase()
        .like("%" + roleName.toUpperCase().replace("*", "") + "%") : null;
  }

  private BooleanExpression isUserRoleStatusEq(String roleStatus) {

    return (roleStatus != null && !"".equals(roleStatus)) ? QUserRole.userRole.status.eq(roleStatus)
        : null;
  }

  private BooleanExpression isUserRoleTypeEq(String roleType) {

    return (roleType != null && !"".equals(roleType)) ? QUserRole.userRole.roleType.eq(roleType)
        : null;
  }

  private OrderSpecifier<Long> orderByRoleIdAsc() {
    return QUserRole.userRole.userRoleId.asc();
  }

  public Integer getTotalNumberOfRecords(RoleDTO userRole) throws DataAccessException {

    JPAQuery query = new JPAQuery(entityManager);
    List<UserRole> roleList = query.from(QUserRole.userRole)
        .where(isUserRoleIdEq(userRole.getUserRoleId()), isUserRoleNameEq(userRole.getRoleName()),
            isUserRoleStatusEq(userRole.getStatus()), isUserRoleTypeEq(userRole.getRoleType()))
        .list(QUserRole.userRole);
    return (roleList != null && !roleList.isEmpty()) ? roleList.size() : 0;
  }

  @Override
  public List<UserRole> findByStatus(String status) throws DataAccessException {
    return userRoleDaoRepository.findByStatusIgnoreCase(status);
  }


  private void insertfeatureMapList(Long[] featureMapList, Long roleId, Timestamp date,
      String createdBy) {

    for (Long featureId : featureMapList) {
      String sqlQuery =
          "insert into portal_role_feature_map(user_role_id, feature_id, status, created_date, created_by,updated_date,updated_by)"
              + " VALUES (?, ?, ?, ?, ?, ?, ?)";
      getJdbcTemplate().update(sqlQuery, new Object[] {roleId, featureId, Status.ACTIVE.name(),
          date, createdBy, date, createdBy,});
    }
  }

  public UserRole updateStatus(UserRole role) throws DataAccessException {
    return userRoleDaoRepository.save(role);
  }

  @Override
  public List<UserRole> findByRoleType(String roleType, String status) throws DataAccessException {
    JPAQuery query = new JPAQuery(entityManager);
    List<UserRole> roleList = new ArrayList<>();
    try {
      roleList = query.from(QUserRole.userRole)
          .where(isUserRoleStatusEq(status), isUserRoleTypeEq(roleType)).list(QUserRole.userRole);
    } catch (Exception e) {


      logger.error("ERROR:: UserRoleDaoImpl::findByRoleType ", e);
    }
    return roleList;
  }
}
