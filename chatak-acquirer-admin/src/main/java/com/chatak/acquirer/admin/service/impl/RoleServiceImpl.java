package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.controller.model.AddRoleResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeatureResponse;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.EntityFeatureDao;
import com.chatak.pg.acq.dao.RoleDao;
import com.chatak.pg.acq.dao.RolesFeatureMappingDao;
import com.chatak.pg.acq.dao.UserRoleFeatureMapDao;
import com.chatak.pg.acq.dao.UsersRoleDao;
import com.chatak.pg.acq.dao.model.PGFeature;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.RoleResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.MapUtil;
import com.chatak.pg.model.EditRoleResponse;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.RolesFeatureMappingDTO;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

  private static Logger logger = Logger.getLogger(FeeCodeServiceImpl.class);

  @Autowired
  UsersRoleDao usersRoleDao;

  @Autowired
  RolesFeatureMappingDao rolesFeatureMappingDao;

  @Autowired
  RoleDao roleDao;

  @Autowired
  UserRoleFeatureMapDao userRoleFeatureMapDao;
  
  @Autowired
  EntityFeatureDao entityFeatureDao;

  @Override
  public AddRoleResponse addRole(HttpServletRequest request) throws ChatakAdminException {
    AddRoleResponse addRoleResponse = new AddRoleResponse();
    try {
      PGUserRoles pgRoleData = usersRoleDao.getRoleOnRoleName(request.getParameter("roleName"));
      if (pgRoleData != null)
        throw new ChatakAdminException(Properties.getProperty("role.already.exist.message"));
      PGUserRoles pgUserRoles = new PGUserRoles();
      pgUserRoles.setRoleName(request.getParameter("roleName"));
      pgUserRoles.setStatus(Constants.ZERO);
      pgUserRoles = usersRoleDao.saveRole(pgUserRoles);
      String featureArray = request.getParameter("featureData");
      if (!StringUtils.isEmpty(featureArray)) {
        StringTokenizer featureData = new StringTokenizer(featureArray, ",");
        while (featureData.hasMoreElements()) {
          PGRolesFeatureMapping pgRolesFeatureMapping = new PGRolesFeatureMapping();
          pgRolesFeatureMapping.setRoleId(pgUserRoles.getRoleId());
          pgRolesFeatureMapping.setFeatureId(Long.parseLong(featureData.nextElement().toString()));
          rolesFeatureMappingDao.saveRoleFeatureMapping(pgRolesFeatureMapping);
        }
      }
      String subFeatureArray = request.getParameter("subFeatureData");
      StringTokenizer subFeatureData = new StringTokenizer(subFeatureArray, ",");
      while (subFeatureData.hasMoreElements()) {
        String subFeature = subFeatureData.nextElement().toString();
        String subArray[] = subFeature.split("sub");
        PGRolesFeatureMapping pgRolesFeatureMapping = new PGRolesFeatureMapping();
        pgRolesFeatureMapping.setRoleId(pgUserRoles.getRoleId());
        pgRolesFeatureMapping.setFeatureId(Long.parseLong(subArray[0]));
        rolesFeatureMappingDao.saveRoleFeatureMapping(pgRolesFeatureMapping);
      }
      addRoleResponse.setStatus(true);
      addRoleResponse.setMessage("success");
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: RoleServiceImpl:: addRole method1", e);
      addRoleResponse.setMessage(e.getMessage());
      addRoleResponse.setStatus(false);
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: addRole method2", e);
      addRoleResponse.setMessage(Properties.getProperty("role.create.error.message"));
      addRoleResponse.setStatus(false);
    }
    return addRoleResponse;
  }

  @Override
  public List<UserRolesDTO> roleList(UserRolesDTO userRolesDTO) throws ChatakAdminException {
    try {
      List<UserRolesDTO> roleList = usersRoleDao.searchRoles(userRolesDTO);
      return (StringUtils.isListNotNullNEmpty(roleList)) ? roleList : null;
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: roleList method2", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }

  }

  @Override
  public FeatureResponse getFeature(Long roleId) throws ChatakAdminException {
    return new FeatureResponse();
  }

  @Override
  public AddRoleResponse updateRole(HttpServletRequest request) throws ChatakAdminException {

    AddRoleResponse addRoleResponse = new AddRoleResponse();
    try {
      Long roleId = Long.parseLong(request.getParameter("roleId"));
      Integer status = Integer.parseInt(request.getParameter("status"));

      PGUserRoles userRole = usersRoleDao.findByRoleId(roleId);
      userRole.setStatus(status);
      usersRoleDao.saveRole(userRole);

      rolesFeatureMappingDao.deleteRolesFeatureMApping(roleId);
      String subFeatureArray = request.getParameter("subFeatureData");
      StringTokenizer subFeatureData = new StringTokenizer(subFeatureArray, ",");
      while (subFeatureData.hasMoreElements()) {
        String subFeature = subFeatureData.nextElement().toString();
        String subArray[] = subFeature.split("sub");
        PGRolesFeatureMapping pgRolesFeatureMapping = new PGRolesFeatureMapping();
        pgRolesFeatureMapping.setFeatureId(Long.parseLong(subArray[0]));
        rolesFeatureMappingDao.saveRoleFeatureMapping(pgRolesFeatureMapping);
      }
      addRoleResponse.setStatus(true);
      addRoleResponse.setMessage("success");
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: updateRole method2", e);
      addRoleResponse.setStatus(false);
    }
    return addRoleResponse;

  }

  @Override
  public List<UserRolesDTO> getRoleList() throws ChatakAdminException {
    try {
      return usersRoleDao.getRoles();
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: getRoleList", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));

    }
  }

  @Override
  public void deletRole(Long roleId) throws ChatakAdminException {

    try {
      PGUserRoles pgUserRoles = usersRoleDao.findByRoleId(roleId);
      pgUserRoles.setStatus(Constants.ONE);
      usersRoleDao.saveRole(pgUserRoles);
    } catch (Exception e) {
      logger.error("ERROR:: RoleServiceImpl:: deletRole method2", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }

  }

  @Override
  public Map<Long, List<String>> getFeatureList(List<UserRolesDTO> roleList) {
    return rolesFeatureMappingDao.getFeatureList(roleList);
  }

  @Override
  public List<PGRolesFeatureMapping> getAdminFeatureForEntityType(
      PGRolesFeatureMapping adminFeatureRequest) throws ChatakAdminException {
    return Collections.emptyList();
  }

  //Role permission creation
  @Override
  public FeatureResponse getAdminFeatureForEntityType(FeatureDTO featureDTO)
      throws ChatakAdminException {
    final String METHOD_NAME = "searchRole";
    logger.info("Entering:" + METHOD_NAME);
    FeatureResponse response = new FeatureResponse();
    List<FeatureDTO> featureDTOs = new ArrayList<FeatureDTO>();
    List<PGFeature> pgFeature = roleDao.searchRole(featureDTO.getRoleType());
    if (StringUtil.isListNotNullNEmpty(pgFeature)) {
      featureDTOs = CommonUtil.copyListBeanProperty(pgFeature, FeatureDTO.class);
    }
    response.setTotalNoOfRows(featureDTO.getNoOfRecords());
    response.setFeatureDTO(featureDTOs);
    return response;
  }

  @Override
  public Response createRole(UserRoleDTO userRoleDTO) throws ReflectiveOperationException {
    final String METHOD_NAME = "createRole";
    Response response = new Response();

    PGUserRoles pgUserRoles = null;
    try {

      List<PGUserRoles> roles = roleDao.findByRoleName(userRoleDTO.getRoleName());

      if (StringUtil.isListNotNullNEmpty(roles)) {
        if (roles.get(0).getStatus() != 1) {
          logger.error(METHOD_NAME + " Role Name already exist.");
          response.setErrorMessage(Properties
              .getProperty("Role Name already exist. Please try again with different Role Name."));
          return response;
        } else {
          pgUserRoles = CommonUtil.copyBeanProperties(userRoleDTO, PGUserRoles.class);
          pgUserRoles.setStatus(0);
          pgUserRoles = roleDao.createOrUpdatePGRole(pgUserRoles);

          updatePGRoleFeatureMapping(userRoleDTO, pgUserRoles);
          
          response.setErrorMessage(Constants.SUCESS);
          return response;
        }

      }
      pgUserRoles = CommonUtil.copyBeanProperties(userRoleDTO, PGUserRoles.class);
      pgUserRoles.setStatus(0);
      pgUserRoles = roleDao.createOrUpdatePGRole(pgUserRoles);

      updatePGRoleFeatureMapping(userRoleDTO, pgUserRoles);
      response.setErrorMessage(Constants.SUCESS);
    } catch (ReflectiveOperationException e) {
      logger.error(
          METHOD_NAME + "Exception occured while creating the Role: " + userRoleDTO.getRoleName());
      throw e;
    }
    return response;
  }

  private void updatePGRoleFeatureMapping(UserRoleDTO userRoleDTO, PGUserRoles pgUserRoles)
      throws ReflectiveOperationException {
    if (userRoleDTO.getFeature() != null) {
      for (String featureData : userRoleDTO.getFeature()) {
        PGRolesFeatureMapping pgRoleFeatureMapping = null;
        PGRolesFeatureMapping pgRoleFeatureMappingDTO = new PGRolesFeatureMapping();
        pgRoleFeatureMappingDTO.setRoleId(pgUserRoles.getRoleId());
        pgRoleFeatureMappingDTO.setFeatureId(Long.valueOf(featureData));
        pgRoleFeatureMappingDTO.setCreatedBy(userRoleDTO.getCreatedBy());
        pgRoleFeatureMappingDTO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        pgRoleFeatureMappingDTO.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        pgRoleFeatureMapping = CommonUtil.copyBeanProperties(pgRoleFeatureMappingDTO,
            PGRolesFeatureMapping.class);
        logger.info(
            " AdminRoleHandlerImpl:: Before saveOrUpdateUserRoleEntityMap:: addOrUpdateRole");
        userRoleFeatureMapDao.saveOrUpdateUserRoleFeatureMap(pgRoleFeatureMapping);
        logger.info(
            "Saving:: AdminRoleHandlerImpl:: FeaturesIn PortalRoleFeatureMap:: addOrUpdateRole method");
      }
    }
  }

  @Override
  public EditRoleResponse getRoleDetails(Long userRoleId) throws ReflectiveOperationException {
    final String METHOD_NAME = "searchUserRoles";
    logger.info("Entering:: " + METHOD_NAME);

    EditRoleResponse editRoleResponseData = new EditRoleResponse();
    try {
      PGUserRoles hostUserRoles = roleDao.findByUserRoleId(userRoleId);
      UserRoleDTO userRolesDTO = CommonUtil.copyBeanProperties(hostUserRoles, UserRoleDTO.class);

      List<Long> existingFeature = roleDao.getFeatureDataOnRoleIdData(userRolesDTO.getRoleId());

      List<FeatureDTO> featureDTOs = new ArrayList<FeatureDTO>();
      List<PGFeature> hostKTCFeature = roleDao.searchRole("ADMIN");
      if (StringUtil.isListNotNullNEmpty(hostKTCFeature)) {
        featureDTOs = CommonUtil.copyListBeanProperty(hostKTCFeature, FeatureDTO.class);
      }
      editRoleResponseData.setRoleRequest(userRolesDTO);
      editRoleResponseData.setFeatureMap(
          (StringUtil.isListNotNullNEmpty(featureDTOs)) ? getFeatureMap(featureDTOs) : null);
      editRoleResponseData.setExistingFeature(existingFeature);
      logger.info("Exiting:: AdminRoleHandlerImpl:: getRoleData method");
      return editRoleResponseData;
    } catch (ReflectiveOperationException e) {
      logger.error(METHOD_NAME + "Exception occured while get the Role: ");
      throw e;
    }
  }

  private Map<Long, List<FeatureDTO>> getFeatureMap(List<FeatureDTO> featureDTOs) {
    List<FeatureDTO> featureListData = null;
    Map<Long, List<FeatureDTO>> featureMap = new HashMap<Long, List<FeatureDTO>>();
    for (FeatureDTO feature : featureDTOs) {
      if (feature.getFeatureLevel().longValue() == 0) {
        featureListData = featureMap.get(feature.getFeatureId());
        if (featureListData == null)
          featureListData = new ArrayList<FeatureDTO>();
        featureListData.add(feature);
        featureMap.put(feature.getFeatureId(), featureListData);
      } else if (feature.getFeatureLevel().longValue() == 1) {
        featureListData = featureMap.get(feature.getRefFeatureId());
        if (featureListData == null)
          featureListData = new ArrayList<FeatureDTO>();
        featureListData.add(feature);
        featureMap.put(feature.getRefFeatureId(), featureListData);

      }

    }
    logger.info("Exiting:: AdminRoleHandlerImpl:: getFeatureList method");
    return MapUtil.mySortedMap(featureMap);
  }

  @Override
  public Response updateRoles(UserRoleDTO userRolesDTO) throws ChatakAdminException {
    final String METHOD_NAME = "updateRole";
    logger.info("Entering:: " + METHOD_NAME);
    Response response = new Response();
    try {
      PGUserRoles pgUserRoles = roleDao.findByUserRoleId(userRolesDTO.getUserRoleId());
      pgUserRoles.setRoleName(userRolesDTO.getRoleName());
      pgUserRoles.setDescription(userRolesDTO.getDescription());
      pgUserRoles.setRoleType(userRolesDTO.getRoleType());
      roleDao.createOrUpdatePGRole(pgUserRoles);
      userRoleFeatureMapDao.deleteRoleFeatureMapping(pgUserRoles.getRoleId());
      if (userRolesDTO.getFeature() != null) {
        for (String featureData : userRolesDTO.getFeature()) {
          PGRolesFeatureMapping pgRoleFeatureMapping = null;
          RolesFeatureMappingDTO pgRoleFeatureMappingDTO = new RolesFeatureMappingDTO();
          pgRoleFeatureMappingDTO.setRoleId(pgUserRoles.getRoleId());
          pgRoleFeatureMappingDTO.setFeatureId(Long.valueOf(featureData));
          pgRoleFeatureMappingDTO.setCreatedBy(userRolesDTO.getCreatedBy());
          pgRoleFeatureMappingDTO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
          pgRoleFeatureMappingDTO.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
          pgRoleFeatureMapping =
              CommonUtil.copyBeanProperties(pgRoleFeatureMappingDTO, PGRolesFeatureMapping.class);
          logger.info(
              " AdminRoleHandlerImpl:: Before saveOrUpdateUserRoleEntityMap:: addOrUpdateRole");
          userRoleFeatureMapDao.saveOrUpdateUserRoleFeatureMap(pgRoleFeatureMapping);
          logger.info(
              "Saving:: AdminRoleHandlerImpl:: FeaturesIn PortalRoleFeatureMap:: addOrUpdateRole method");
        }
      }
    } catch (NumberFormatException e) {
      logger.error(METHOD_NAME + "Exception occured while updating the Role: ", e);
    } catch (ReflectiveOperationException e) {
      logger.error("Error :: RoleServiceImpl :: updateRoles", e);
    }
    logger.info("Exiting ::" + METHOD_NAME);
    return response;
  }

  @Override
  public List<UserRoleDTO> getRoleListByType(String rolesType) {
    final String METHOD_NAME = "searchRole";
    logger.info("Entering:" + METHOD_NAME);
    List<UserRoleDTO> userDTOs = new ArrayList<UserRoleDTO>();
    List<PGUserRoles> pgUserRole = roleDao.findByRoleType(rolesType);
    if (StringUtil.isListNotNullNEmpty(pgUserRole)) {
        userDTOs = CommonUtil.copyListBeanProperty(pgUserRole, UserRoleDTO.class);
    }
    return userDTOs;
  }

  @Override
  public Response changeRoleStatus(UserRoleDTO userRoleDTO) throws ChatakAdminException {
    final String METHOD_NAME = "changeRoleStatus";
    logger.info("Entering:: " + METHOD_NAME);
    Response response = new Response();
    try {
      if (null != userRoleDTO) {
        PGUserRoles pgRoles = roleDao.findByUserRoleId(userRoleDTO.getRoleId());
        pgRoles.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        if (("Active").equalsIgnoreCase(userRoleDTO.getStatus())) {
          pgRoles.setStatus(PGConstants.STATUS_ACTIVE);
        } else {
          pgRoles.setStatus(PGConstants.STATUS_SUSPENDED);
        }
        pgRoles.setUpdatedBy(userRoleDTO.getUpdatedBy());
        pgRoles.setReason(userRoleDTO.getReason());
        roleDao.createOrUpdatePGRole(pgRoles);
        response.setErrorCode(Constants.SUCESS);
        response.setErrorMessage(Constants.SUCESS);
      }
    } catch (DataAccessException e) {
      response.setErrorCode(Constants.ERROR);
      logger.error("", e);
    }
    return response;
  }

  @Override
  public RoleResponse validateRolename(String roleName) throws ChatakAdminException {
    RoleResponse roleResponse = new RoleResponse();
    PGUserRoles pgRoleData = usersRoleDao.getRoleOnRoleName(roleName);
    if (pgRoleData != null) {
      if (pgRoleData.getStatus() == PGConstants.STATUS_INACTIVE
          || pgRoleData.getStatus() == PGConstants.STATUS_ACTIVE) {
        roleResponse.setRoleId(pgRoleData.getRoleId());
        roleResponse.setRoleName(pgRoleData.getRoleName());
        roleResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
        roleResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
        return roleResponse;

      } else {
        roleResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        roleResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        return roleResponse;
      }

    } else {
      roleResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      roleResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return roleResponse;
    }


  }

	@Override
	public List<Long> getFeaturesByEntity(String entity) throws ChatakAdminException {
		return entityFeatureDao.getFeaturesByEntity(entity);
	}

	@Override
	public FeatureResponse getFeatureDataByIds(List<Long> featureIds) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		FeatureResponse response = new FeatureResponse();
		List<FeatureDTO> featureDTOs = new ArrayList<>();
		List<PGFeature> pgFeature = roleDao.getFeatureDataByIds(featureIds);
		if (StringUtil.isListNotNullNEmpty(pgFeature)) {
			featureDTOs = CommonUtil.copyListBeanProperty(pgFeature, FeatureDTO.class);
		}
		response.setFeatureDTO(featureDTOs);
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return response;
	}
}
