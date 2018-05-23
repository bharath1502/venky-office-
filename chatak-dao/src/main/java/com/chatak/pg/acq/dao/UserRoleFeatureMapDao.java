/**
 * 
 */
package com.chatak.pg.acq.dao;

import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;

/**
 * @Author: Girmiti Software
 * @Date: Sep 23, 2016
 * @Time: 2:55:31 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface UserRoleFeatureMapDao {

  public PGRolesFeatureMapping saveOrUpdateUserRoleFeatureMap(
      PGRolesFeatureMapping pgRoleFeatureMapping);

  /**
   * @param roleId
   */
  public void deleteRoleFeatureMapping(Long roleId);

}
