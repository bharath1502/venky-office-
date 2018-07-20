/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.RoleDao;
import com.chatak.pg.acq.dao.model.PGFeature;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.acq.dao.model.QPGFeature;
import com.chatak.pg.acq.dao.model.QPGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.QPGUserRoles;
import com.chatak.pg.acq.dao.repository.UsersRoleRepository;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Sep 21, 2016
 * @Time: 12:49:20 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

  @Autowired
  UsersRoleRepository usersRoleRepository;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * @param roleId
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGUserRoles findByUserRoleId(Long userRoleId) {
    return usersRoleRepository.findByRoleId(userRoleId);
  }

  /**
   * @param roleId
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGRolesFeatureMapping> findByRoleId(Long roleId) {
    JPAQuery query = new JPAQuery(entityManager);
    QPGRolesFeatureMapping qPGRoleFeatureMapping = QPGRolesFeatureMapping.pGRolesFeatureMapping;
    return query.from(qPGRoleFeatureMapping).where(isRoleId(roleId)).list(qPGRoleFeatureMapping);
  }


  private BooleanExpression isRoleId(Long roleId) {
    return (roleId != null && !"".equals(roleId.toString()))
        ? QPGRolesFeatureMapping.pGRolesFeatureMapping.roleId.eq(roleId) : null;
  }

  @Override
  public List<PGFeature> searchRole(String roleType) {

    JPAQuery query = new JPAQuery(entityManager);
    QPGFeature qPGFeatures = QPGFeature.pGFeature;
    return query.from(qPGFeatures).where(isRoleType(roleType)).list(qPGFeatures);
  }

  private BooleanExpression isRoleType(String roleType) {
    return (roleType != null && !"".equals(roleType))
        ? QPGFeature.pGFeature.roleType.equalsIgnoreCase(roleType) : null;
  }

  @Override
  public List<PGUserRoles> findByRoleName(String roleName) {
    JPAQuery query = new JPAQuery(entityManager);
    QPGUserRoles qHostUserRoles = QPGUserRoles.pGUserRoles;
    return query.from(qHostUserRoles).where(isRoleNameEq(roleName)).list(qHostUserRoles);
  }

  @Override
  public PGUserRoles createOrUpdatePGRole(PGUserRoles pgUserRoles) {
    return usersRoleRepository.save(pgUserRoles);
  }

  private BooleanExpression isRoleNameEq(String roleName) {
    return (roleName != null && !"".equals(roleName))
        ? QPGUserRoles.pGUserRoles.roleName.toUpperCase().eq(roleName.toUpperCase()) : null;
  }

   private BooleanExpression isRoleStatusEq(String activeStatus) {
    Integer status = Integer.valueOf(activeStatus);
    return (status != null && !"".equals(status.toString()))
        ? QPGUserRoles.pGUserRoles.status.eq(status) : null;
  }

  /**
   * @param roleId
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<Long> getFeatureDataOnRoleIdData(Long roleId) {
    JPAQuery query = new JPAQuery(entityManager);
    QPGFeature pgFeature = QPGFeature.pGFeature;
    QPGRolesFeatureMapping qPGRolesFeatureMapping = QPGRolesFeatureMapping.pGRolesFeatureMapping;
    return query.from(qPGRolesFeatureMapping, pgFeature)
        .where(isRoleId(roleId),
            QPGFeature.pGFeature.featureId
                .eq(QPGRolesFeatureMapping.pGRolesFeatureMapping.featureId))
        .list(qPGRolesFeatureMapping.featureId);
  }

  /**
   * @param rolesType
   * @return
   */
  @Override
  public List<PGUserRoles> findByRoleType(String rolesType) {
    JPAQuery query = new JPAQuery(entityManager);
    QPGUserRoles qPGUserRoles = QPGUserRoles.pGUserRoles;
    return query.from(qPGUserRoles)
        .where(qPGUserRoles.roleType.equalsIgnoreCase(rolesType), isRoleStatusEq("0"))
        .list(qPGUserRoles);
  }

	/**
	 * @param featureIds
	 * @return
	 */
	@Override
	public List<PGFeature> getFeatureDataByIds(List<Long> featureIds) {
		JPAQuery query = new JPAQuery(entityManager);
		QPGFeature qPGFeatures = QPGFeature.pGFeature;
		return query.from(qPGFeatures).where(qPGFeatures.featureId.in(featureIds)).list(qPGFeatures);
	}

}
