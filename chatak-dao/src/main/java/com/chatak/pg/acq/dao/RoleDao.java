/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGFeature;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.PGUserRoles;

/**
 * @Author: Girmiti Software
 * @Date: Sep 21, 2016
 * @Time: 12:16:10 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface RoleDao {

  public PGUserRoles findByUserRoleId(Long roleId);

  public List<PGRolesFeatureMapping> findByRoleId(Long roleId);

  public PGUserRoles createOrUpdatePGRole(PGUserRoles hostUserRoles);

  /**
   * @param string
   * @return
   */
  public List<PGFeature> searchRole(String roleType);

  public List<PGUserRoles> findByRoleName(String roleName);

  public List<Long> getFeatureDataOnRoleIdData(Long roleId);

  /**
   * @param rolesType
   * @return
   */
  public List<PGUserRoles> findByRoleType(String rolesType);

}
