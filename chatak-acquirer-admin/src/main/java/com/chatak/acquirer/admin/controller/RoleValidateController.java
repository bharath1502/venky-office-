package com.chatak.acquirer.admin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeatureResponse;
import com.chatak.acquirer.admin.service.FeatureService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.RoleResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.RoleLevel;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class RoleValidateController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(RoleValidateController.class);

  @Autowired
  private MessageSource messageSource;

  @Autowired
  FeatureService featureService;

  @Autowired
  RoleService roleService;

  @Autowired
  UserService userService;

  @Autowired
  RoleController roleController;

  @RequestMapping(value = CHATAK_ADMIN_UNIQUE_ROLENAME_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateuniqueRoleName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: BinController :: validateuniqueRoleName method");
    String roleName = request.getParameter("roleName");
    RoleResponse roleResponse = null;
    try {

      roleResponse = roleService.validateRolename(roleName);
      return JsonUtil.convertObjectToJSON(roleResponse);
    } catch (Exception e) {
      roleResponse = new RoleResponse();
      roleResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      roleResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: RoleController:: validateuniqueRoleName method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(roleResponse);
      } catch (ChatakAdminException e1) {
    	  logger.error("ERROR :: RoleController :: validateuniqueRoleName :: convertObjectToJSON", e1);
      }
    }
    logger.info("Exiting:: RoleController:: validateuniqueRoleName method");
    return null;
  }

  @RequestMapping(value = ROLE_ACTIVATION, method = RequestMethod.POST)
  public ModelAndView changeRoleStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long roleActivateId,
      @FormParam("suspendActiveStatus") final String roleStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: RoleController:: changeRoleStatus method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_SEARCH);
    try {
      roleController.getRoleListForRoles(session, model);
      UserRoleDTO userRoleDTO = new UserRoleDTO();
      model.put("userRoleDTO", userRoleDTO);
      userRoleDTO.setRoleId(roleActivateId);
      userRoleDTO.setStatus(roleStatus);
      userRoleDTO.setReason(reason);
      userRoleDTO.setUpdatedBy(session.getAttribute(Constants.LOGIN_ROLE_ID).toString());
      Response saveResponse = roleService.changeRoleStatus(userRoleDTO);
      if (saveResponse != null && Constants.SUCESS.equals(saveResponse.getErrorCode())) {
        modelAndView = roleController.getRolePagination(session,
            StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
                : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
            (Integer) session.getAttribute("totalRecords"), model);
        model.put(Constants.SUCESS, messageSource.getMessage("prepaid.role.sucess.activate.message",
            null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView = roleController.getRolePagination(session,
            StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
                : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
            (Integer) session.getAttribute("totalRecords"), model);
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: RoleController:: changeRoleStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: RoleController:: changeRoleStatus method");
    return modelAndView;
  }

  @RequestMapping(value = ADMIN_ROLE_NAME, method = RequestMethod.POST)
  public ModelAndView roleNameByType(HttpServletRequest request, HttpServletResponse response,
      UserRoleDTO userRoleRequest, @FormParam("rolesType") final String rolesType, Map model,
      HttpSession session) {
    logger.info("Entering:: RoleController:: roleNameByType method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_CREATE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
    	roleController.getRoleListForRoles(session, model);
      userRoleRequest.setRoleType(rolesType);
      model.put("userRoleDTO", userRoleRequest);
      FeatureDTO featureDTO = new FeatureDTO();
      FeatureResponse roleFeatureResponse = null;
      if (rolesType.equalsIgnoreCase(RoleLevel.CP_SUPER_ADMIN.getValue())) {
        featureDTO.setRoleType("ADMIN");
        roleFeatureResponse = roleService.getAdminFeatureForEntityType(featureDTO);
      } else if (rolesType.equalsIgnoreCase(RoleLevel.CP_MERCHANT.getValue())) {
        featureDTO.setRoleType("MERCHANT");
        roleFeatureResponse = roleService.getAdminFeatureForEntityType(featureDTO);
      } else if (rolesType.equalsIgnoreCase(RoleLevel.CP_RESELLER.getValue())) {
        featureDTO.setRoleType("RESELLER");
        roleFeatureResponse = roleService.getAdminFeatureForEntityType(featureDTO);
      } else if (rolesType.equalsIgnoreCase(RoleLevel.CP_TMS.getValue())) {
        featureDTO.setRoleType("TMS");
        roleFeatureResponse = roleService.getAdminFeatureForEntityType(featureDTO);
      } else if (rolesType.equalsIgnoreCase(RoleLevel.CP_PM.getValue())) {
    	  featureDTO.setRoleType(RoleLevel.CP_PM.getValue());
          List<Long> featureIds = roleService.getFeaturesByEntity(RoleLevel.CP_PM.getValue());
          roleFeatureResponse = roleService.getFeatureDataByIds(featureIds);
      } else if (rolesType.equalsIgnoreCase(RoleLevel.CP_ISO.getValue())) {
    	  featureDTO.setRoleType(RoleLevel.CP_ISO.getValue());
            List<Long> featureIds = roleService.getFeaturesByEntity(RoleLevel.CP_ISO.getValue());
            roleFeatureResponse = roleService.getFeatureDataByIds(featureIds);
      }
      List<FeatureDTO> featureList=null; 
      if(roleFeatureResponse!=null){
        featureList = roleFeatureResponse.getFeatureDTO();
      }
      
      List<Long> featureIdList = new ArrayList<>();
      String adminRelatedFeatureIds = null;
      adminRelatedFeatureIds = messageSource.getMessage("chatak.admin.related.feature.id", null,
          LocaleContextHolder.getLocale());
      StringTokenizer st = new StringTokenizer(adminRelatedFeatureIds, ",");
      while (st.hasMoreElements()) {
        featureIdList.add(Long.valueOf(st.nextElement().toString()));
      }
      List<FeatureDTO> featureList2 = roleController.getAssignedFeatureList(featureList, session, featureDTO.getRoleType());
      modelAndView.addObject("featureList", featureList2);
    } catch (Exception e) {
      logger.info("ERROR:: RoleController:: roleNameByType method", e);
    }
    logger.info("Exiting:: RoleController:: roleNameByType method");
    return modelAndView;
  }
}
