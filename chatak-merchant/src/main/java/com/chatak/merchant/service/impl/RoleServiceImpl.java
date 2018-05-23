package com.chatak.merchant.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.FeatureResponse;
import com.chatak.merchant.service.RoleService;
import com.chatak.pg.acq.dao.RolesFeatureMappingDao;
import com.chatak.pg.acq.dao.UsersRoleDao;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

  private static Logger logger = Logger.getLogger(RoleServiceImpl.class);

  @Autowired
  UsersRoleDao usersRoleDao;

  @Autowired
  RolesFeatureMappingDao rolesFeatureMappingDao;


  @Override
  public List<UserRolesDTO> roleList(UserRolesDTO userRolesDTO) throws ChatakMerchantException {
    try {
      List<UserRolesDTO> roleList = usersRoleDao.searchRoles(userRolesDTO);
      return (StringUtils.isListNotNullNEmpty(roleList)) ? roleList : null;
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: roleList method2", e);
      throw new ChatakMerchantException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }

  }

  @Override
  public FeatureResponse getFeature(Long roleId) throws ChatakMerchantException { 
    return new FeatureResponse();
  }


  @Override
  public List<UserRolesDTO> getRoleList() throws ChatakMerchantException {
    try {
      return usersRoleDao.getRoles();
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: getRoleList ", e);
      throw new ChatakMerchantException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));

    }
  }

  @Override
  public void deletRole(Long roleId) throws ChatakMerchantException {

    try {
      PGUserRoles pgUserRoles = usersRoleDao.findByRoleId(roleId);
      pgUserRoles.setStatus(Constants.ONE);
      usersRoleDao.saveRole(pgUserRoles);
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: deletRole method2", e);
      throw new ChatakMerchantException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }

  }

  @Override
  public Map<Long, List<String>> getFeatureList(List<UserRolesDTO> roleList) {
    return rolesFeatureMappingDao.getFeatureList(roleList);
  }

}
