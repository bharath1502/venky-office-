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

import com.chatak.pg.acq.dao.UserRoleFeatureMapDao;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.repository.RolesFeatureMappingRepository;

/**
 * @Author: Girmiti Software
 * @Date: Sep 23, 2016
 * @Time: 2:56:40 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("userRoleFeatureMapDao")
public class UserRoleFeatureMapDaoImpl implements UserRoleFeatureMapDao {


  @Autowired
  private RolesFeatureMappingRepository rolesFeatureMappingRepository;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * @param hostKTCRoleFeatureMapping
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGRolesFeatureMapping saveOrUpdateUserRoleFeatureMap(
      PGRolesFeatureMapping pgRolesFeatureMapping) {

    return rolesFeatureMappingRepository.save(pgRolesFeatureMapping);
  }

  /**
   * @param roleId
   */
  @Override
  public void deleteRoleFeatureMapping(Long userRoleId) {
    List<PGRolesFeatureMapping> pgRoleFeatureMapping =
        rolesFeatureMappingRepository.findByRoleId(userRoleId);
    rolesFeatureMappingRepository.delete(pgRoleFeatureMapping);
  }

}
