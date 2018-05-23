package com.chatak.acquirer.admin.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeatureResponse;
import com.chatak.acquirer.admin.service.FeatureService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.FeatureDataResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.enums.RoleLevel;
import com.chatak.pg.model.EditRoleResponse;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class RoleController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(RoleController.class);

  @Autowired
  private MessageSource messageSource;

  @Autowired
  FeatureService featureService;

  @Autowired
  RoleService roleService;

  @Autowired
  UserService userService;

  /**
   * showRoleSearch
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_ACCESS_ROLE_SEARCH, method = RequestMethod.GET)
  public ModelAndView showRoleSearch(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session) {
    logger.info("Entering:: RoleController:: showRoleCreate method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    
    UserRolesDTO userRoleDTO = new UserRolesDTO();
    model.put(Constants.USER_ROLE_DTO, userRoleDTO);
    try {
      session.setAttribute(Constants.SEARCH_ROLE_REQUEST, userRoleDTO);
      userRoleDTO.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
      userRoleDTO.setPageIndex(Constants.ONE);
      session.setAttribute(Constants.SEARCH_ROLE_REQUEST, userRoleDTO);
      roleService.roleList(userRoleDTO);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: RoleController:: showRoleSearch method", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: RoleController:: showRoleSearch method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
          LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", false);
    logger.info("Exit:: RoleController:: showRoleCreate method");
    return modelAndView;
  }

  /**
   * getRolePagination
   * 
   * @param session
   * @param pageNumber
   * @param totalRecords
   * @param model
   * @return
   */
  @RequestMapping(value = ROLE_PAGINATION_ACTION, method = RequestMethod.POST)
  public ModelAndView getRolePagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: RoleController:: getRolePagination method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_SEARCH);
    UserRolesDTO userRoleDTO = null;
    try {
      userRoleDTO = (UserRolesDTO) session.getAttribute(Constants.SEARCH_ROLE_REQUEST);
      model.put(Constants.USER_ROLE_DTO, userRoleDTO);
      userRoleDTO.setPageIndex(pageNumber);
      userRoleDTO.setNoOfRecords(totalRecords);
      List<UserRolesDTO> roleList = roleService.roleList(userRoleDTO);
      model.put(Constants.USER_ROLE_DTO, userRoleDTO);
      modelAndView.addObject("pageSize", userRoleDTO.getPageSize());
      modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
          userRoleDTO.getNoOfRecords().intValue());
      session.setAttribute("pageNumber", pageNumber);
      session.setAttribute("totalRecords", totalRecords);
      List responseList = new ArrayList();
      setAdminUserRoleList(modelAndView, roleList, responseList); 
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: RoleController:: getRolePagination method", e);
    }
    logger.info("Exit:: RoleController:: getRolePagination method");
    return modelAndView;
  }

  /**
   * searchRoleData
   * 
   * @param request
   * @param userRolesDTO
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_ACCESS_ROLE_SEARCH, method = RequestMethod.POST)
  public ModelAndView searchRoleData(HttpServletRequest request, UserRolesDTO userRolesDTO,
      HttpServletResponse response, Map model, HttpSession session) {
    logger.info("Entering:: RoleController:: searchRoleData method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_SEARCH);
    model.put(Constants.USER_ROLE_DTO, userRolesDTO);
    try {
      userRolesDTO.setPageIndex(Constants.ONE);
      session.setAttribute(Constants.SEARCH_ROLE_REQUEST, userRolesDTO);
      List<UserRolesDTO> roleList = roleService.roleList(userRolesDTO);
      modelAndView.addObject("pageSize", userRolesDTO.getPageSize());
      modelAndView =
          PaginationUtil.getPagenationModel(modelAndView, userRolesDTO.getNoOfRecords().intValue());
      session.setAttribute("pageNumber", Constants.ONE);
      session.setAttribute("totalRecords", userRolesDTO.getNoOfRecords());
      List responseList = new ArrayList();
      setAdminUserRoleList(modelAndView, roleList, responseList);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: RoleController:: searchRoleData method", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: RoleController:: searchRoleData method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
          LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", true);
    logger.info("Exit:: RoleController:: searchRoleData method");
    return modelAndView;
  }

  private void setAdminUserRoleList(ModelAndView modelAndView, List<UserRolesDTO> roleList,
      List responseList) throws ChatakAdminException {
    if (StringUtil.isListNotNullNEmpty(roleList)) {
      int listSize = roleList.size();
      List roleResponseList = roleList;
      List adminUserRoleList = userService.getAdminRoleList();
      List merchantUserRoleList = userService.getMerchantRoleList();
      if (StringUtil.isListNotNullNEmpty(adminUserRoleList)
          || StringUtil.isListNotNullNEmpty(merchantUserRoleList)) {
        UserRolesDTO tempRoleDTO;
        for (int i = 0; i < listSize; i++) {
          tempRoleDTO = (UserRolesDTO) roleResponseList.get(i);
          tempRoleDTO = isRoleCreatedUser(adminUserRoleList, merchantUserRoleList, tempRoleDTO);
          responseList.add(tempRoleDTO);
        }
        modelAndView.addObject(Constants.ROLE_LIST, responseList);
      } else {
        modelAndView.addObject(Constants.ROLE_LIST, roleResponseList);
      }
    }
  }

  private UserRolesDTO isRoleCreatedUser(List adminUserRoleList, List merchantUserRoleList,
      UserRolesDTO tempRoleDTO) {
    if (adminUserRoleList.contains(tempRoleDTO.getRoleId())
        || merchantUserRoleList.contains(tempRoleDTO.getRoleId())) {
      tempRoleDTO.setIsRoleCreatedUser("true");
    } else {
      tempRoleDTO.setIsRoleCreatedUser(Constants.FLAG_FALSE);
    }
    return tempRoleDTO;
  }

  /**
   * downloadRoleReport
   * 
   * @param session
   * @param model
   * @param request
   * @param downLoadPageNumber
   * @param downloadType
   * @param response
   * @return
   */
  @RequestMapping(value = DOWNLOAD_ROLE_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadRoleReport(HttpSession session, Map model, HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    String downloadType=request.getParameter("downloadType");
    logger.info("Entering:: RoleController:: downloadRoleReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_SEARCH);
    UserRolesDTO userRoles = null;
    try {
      List<UserRolesDTO> roleList = null;
      userRoles = (UserRolesDTO) session.getAttribute(Constants.SEARCH_ROLE_REQUEST);
      userRoles.setPageIndex(downLoadPageNumber);
      Integer pageSize = userRoles.getPageSize();
      if (downloadAllRecords) {
        userRoles.setPageIndex(Constants.ONE);
        userRoles.setPageSize(totalRecords);
      }
      roleList = roleService.roleList(userRoles);
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(roleList)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	 exportDetails.setExportType(ExportType.PDF);	
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadRoleReport(roleList, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
      userRoles.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: RoleController:: downloadRoleReport method", e);
    }
    logger.info("Exit:: RoleController:: downloadRoleReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<UserRolesDTO> roleList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Role_");
    exportDetails.setHeaderMessageProperty("chatak.header.role.messages");

    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(roleList));
  }

  @RequestMapping(value = DELETE_ROLE, method = RequestMethod.POST)
  public ModelAndView deletRole(HttpServletRequest request, HttpServletResponse response, Map model,
      HttpSession session, @FormParam("roleIdDeleteData") final Long roleIdDeleteData) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      roleService.deletRole(roleIdDeleteData);
      model.put(Constants.SUCESS, messageSource.getMessage("chatak.role.delete.message", null,
          LocaleContextHolder.getLocale()));
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: RoleController:: deletRole method", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: RoleController:: deletRole method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
          LocaleContextHolder.getLocale()));
    }
    return showRoleSearch(request, response, model, session);
  }

  @RequestMapping(value = VIEW_ROLE, method = RequestMethod.POST)
  public ModelAndView viewRole(HttpServletRequest request, HttpServletResponse response,
      @FormParam("roleIdViewData") final Long roleIdViewData, HttpSession session, Map model) {

    logger.info("Entering:: RoleController:: viewRole method");
    ModelAndView modelAndView = new ModelAndView(ACCESS_ROLE_VIEW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      model.put("roleIdData", roleIdViewData);
      Map<Long, List<FeatureDataResponse>> featureDataResponse = featureService.getFeature();
      model.put(Constants.FEATURE_DATA_RESPONSE, featureDataResponse);
      FeatureResponse featureResponse = roleService.getFeature(roleIdViewData);
      model.put(Constants.FEATURE_LIST, featureResponse.getFeature());
      model.put("subFeatureList", featureResponse.getSubFeature());
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: RoleController:: viewRole method", e);
    }
    logger.info("Exiting:: RoleController:: viewRole method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_ACCESS_ROLE_CREATE, method = RequestMethod.GET)
  public ModelAndView showCreateRole(HttpServletRequest request, HttpServletResponse response,
      Map<String, Object> model, HttpSession session) {

    logger.info("Entering:: RoleController:: showRole method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_CREATE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      UserRoleDTO userRoleDTO = new UserRoleDTO();
      model.put(Constants.USER_ROLE_DTO, userRoleDTO);
      
      List<RoleLevel> getAllTypeRoleLogin = new ArrayList<>();
      List<RoleLevel> rolesList = Arrays.asList(RoleLevel.values());
      getAllTypeRoleLogin = getRoleListForRoles(rolesList, session, getAllTypeRoleLogin);
      model.put("roleLevelList", getAllTypeRoleLogin);
      FeatureDTO featureDTO = new FeatureDTO();
      FeatureResponse roleFeatureResponse = null;
      if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.CP_SUPER_ADMIN.value())) {
        featureDTO.setRoleType("ADMIN");
        roleFeatureResponse = roleService.getAdminFeatureForEntityType(featureDTO);
      }
      List<FeatureDTO> featureList = null;
      if (roleFeatureResponse != null) {
        featureList = roleFeatureResponse.getFeatureDTO();
      }
      String adminRelatedFeatureIds = null;
      adminRelatedFeatureIds = messageSource.getMessage("chatak.admin.related.feature.id", null,
          LocaleContextHolder.getLocale());
      StringTokenizer st = new StringTokenizer(adminRelatedFeatureIds, ",");
      getFeatureIDsList(st);
      List<FeatureDTO> featureList2 = getAssignedFeatureList(featureList, session);
      modelAndView.addObject(Constants.FEATURE_LIST, featureList2);
    } catch (Exception e) {
      logger.info("ERROR:: UserRoleController:: showRole method", e);
    }
    logger.info("Exiting:: UserRoleController:: showRole method");
    return modelAndView;
  }

  //FeatureList
  public List<FeatureDTO> getAssignedFeatureList(List<FeatureDTO> prepaidFeatureRequestList,
      HttpSession session) {
    List<FeatureDTO> featureList2 = new ArrayList<>();
    if (StringUtil.isListNotNullNEmpty(prepaidFeatureRequestList)) {
      for (FeatureDTO featureIds : prepaidFeatureRequestList) {
        FeatureDTO prepaidFeatureRequest2 = new FeatureDTO();
        prepaidFeatureRequest2.setFeatureId(featureIds.getFeatureId());
        prepaidFeatureRequest2.setName(featureIds.getName());
        prepaidFeatureRequest2.setCreatedDate(featureIds.getCreatedDate());
        prepaidFeatureRequest2.setFeatureLevel(featureIds.getFeatureLevel());
        prepaidFeatureRequest2.setRefFeatureId(featureIds.getRefFeatureId());
        prepaidFeatureRequest2.setStatus(featureIds.getStatus());
        featureList2.add(prepaidFeatureRequest2);
      }
    }
    return featureList2;
  }

  @RequestMapping(value = PROCESS_CREATE_ROLE, method = RequestMethod.POST)
  public ModelAndView processCreateRole(HttpServletRequest request, Map<String, Object> model,
      HttpSession session, UserRoleDTO userRoleDTO, HttpServletResponse response) {
    logger.info("Entering:: RoleController:: createRole method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_ACCESS_ROLE_CREATE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      userRoleDTO.setCreatedBy(loginResponse.getUserId().toString());
      userRoleDTO.setCreatedDate(DateUtil.getCurrentTimestamp());
      model.put(Constants.USER_ROLE_DTO, userRoleDTO);
      String permissionData = request.getParameter("permission");
      logger.error("ERROR:: RoleController:: processCreateRole method"+"permissionData=" + permissionData);
      
      userRoleDTO.setFeature(StringUtil.converArray(permissionData));
      Response roleResponse = roleService.createRole(userRoleDTO);
      if (roleResponse != null) {
        if ("sucess".equalsIgnoreCase(roleResponse.getErrorMessage())) {
          modelAndView = showRoleSearch(request, response, model, session);
          modelAndView.addObject(Constants.SUCESS, messageSource.getMessage("role.create.message",
              null, LocaleContextHolder.getLocale()));
        } else {
          FeatureDTO featureDTO = new FeatureDTO();
          FeatureResponse roleFeatureResponse = null;
          featureDTO.setRoleType("ADMIN");
          loginResponse.getUserRoleId();
          roleFeatureResponse = roleService.getAdminFeatureForEntityType(featureDTO);
          List<FeatureDTO> featureList = roleFeatureResponse.getFeatureDTO();
          String adminRelatedFeatureIds = null;
          adminRelatedFeatureIds = messageSource.getMessage("chatak.admin.related.feature.id", null,
              LocaleContextHolder.getLocale());
          StringTokenizer st = new StringTokenizer(adminRelatedFeatureIds, ",");
          getFeatureIDsList(st);
          List<FeatureDTO> featureList2 = getAssignedFeatureList(featureList, session);
          model.put(Constants.FEATURE_LIST, featureList2);
          getFeatures(featureList2);
          String roleExistingFeatures = getRoleReatures(userRoleDTO);
          roleExistingFeatures += "|";
          session.setAttribute(Constants.ROLE_EXISTING_FEATURES, roleExistingFeatures);
          model.put(Constants.USER_ROLE_DTO, userRoleDTO);
          session.setAttribute(Constants.EXISTING_FEATURE_LIST, userRoleDTO.getFeature());
          modelAndView.addObject(Constants.ERROR, messageSource
              .getMessage("role.duplicate.name.error", null, LocaleContextHolder.getLocale()));
        }
      }
      logger.info("Exiting:: RoleController:: createRole method");
    } catch (Exception e) {
      logger.error("ERROR:: RoleController:: createCategory method", e);
      modelAndView.setViewName(CHATAK_ADMIN_ACCESS_ROLE_CREATE);
      modelAndView.addObject(Constants.ERROR,
          Properties.getProperty("ktc.tms.create.role.error.message"));
    }
    return modelAndView;
  }

  private void getFeatures(List<FeatureDTO> featureList2) {
    for (FeatureDTO feature : featureList2) {
      feature.setRoleFeatureId("|" + feature.getFeatureId().toString() + "|");
    }
  }

  private List<Long> getFeatureIDsList(StringTokenizer st) {
    List<Long> featureIdList = new ArrayList<>();
    while (st.hasMoreElements()) {
      featureIdList.add(Long.valueOf(st.nextElement().toString()));
    }
    return featureIdList;
  }

  private String getRoleReatures(UserRoleDTO userRoleDTO) {
    String roleExistingFeatures = "";
    for (String feature : userRoleDTO.getFeature()) {
      Long fet = Long.valueOf(feature);
      roleExistingFeatures += "|" + fet;
    }
    return roleExistingFeatures;
  }

  @RequestMapping(value = CHATAK_SHOW_ROLE_EDIT, method = RequestMethod.POST)
  public ModelAndView getRoleDetails(HttpServletRequest request,
      @FormParam("roleIdData") final Long roleIdData, HttpSession session,
      HttpServletResponse respone, Map model) {
    logger.info("Entering :: RoleController :: getRoleDetails method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_ROLE_EDIT_PAGE);
    UserRoleDTO userRolesDTO = new UserRoleDTO();
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      validateEditRoleResponse(roleIdData, session, model, userRolesDTO);
    } catch (Exception exp) {
      logger.error("ERROR :: RoleController :: getRoleDetails method",exp);
    }
    logger.info("Exiting :: RoleController :: getRoleDetails method");
    model.put(Constants.USER_ROLES_DTO, userRolesDTO);
    return modelAndView;
  }

  private void validateEditRoleResponse(final Long roleIdData, HttpSession session, Map model, UserRoleDTO userRolesDTO)
		throws ReflectiveOperationException, ChatakAdminException {
	  LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      EditRoleResponse editRoleResponseData = roleService.getRoleDetails(roleIdData);
      session.setAttribute("editRoleResponseData", editRoleResponseData);
      if (loginResponse.getUserRoleId() != null && editRoleResponseData.getRoleRequest() != null) {
        if (loginResponse.getUserRoleId()
            .compareTo(Long.valueOf(editRoleResponseData.getRoleRequest().getRoleId())) == 0) {
          model.put(Constants.SAME_ROLE_FLAG, "true");
        } else {
          model.put(Constants.SAME_ROLE_FLAG, Constants.FLAG_FALSE);
        }
      }
      model.put("roleId", editRoleResponseData.getRoleRequest().getRoleId().toString());
      userRolesDTO.setUserRoleId(editRoleResponseData.getRoleRequest().getRoleId());
      userRolesDTO.setRoleName(editRoleResponseData.getRoleRequest().getRoleName());
      userRolesDTO.setDescription(editRoleResponseData.getRoleRequest().getDescription());
      userRolesDTO.setRoleType(editRoleResponseData.getRoleRequest().getRoleType());
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setRoleType(editRoleResponseData.getRoleRequest().getRoleType());
        FeatureResponse featureResponse;
     
        featureResponse = roleService.getAdminFeatureForEntityType(featureDTO);
        List<FeatureDTO> featureList2 =
            getAssignedFeatureList(featureResponse.getFeatureDTO(), session);
        model.put(Constants.FEATURE_LIST, featureList2);
        getFeatures(featureList2);
        String roleExistingFeatures = "";
        for (Long feature : editRoleResponseData.getExistingFeature()) {
          roleExistingFeatures += "|" + feature;
        }
        roleExistingFeatures += "|";
        session.setAttribute(Constants.ROLE_EXISTING_FEATURES, roleExistingFeatures);
        model.put(Constants.USER_ROLES_DTO, userRolesDTO);
        session.setAttribute(Constants.EXISTING_FEATURE_LIST,
            editRoleResponseData.getExistingFeature());
  }

  @RequestMapping(value = CHATAK_SHOW_ROLE_VIEW, method = RequestMethod.POST)
  public ModelAndView getRoleView(HttpServletRequest request,
      @FormParam("roleIdViewData") final Long roleIdData, HttpSession session,
      HttpServletResponse respone, Map model) {
    logger.info("Entering :: RoleController :: getRoleDetails method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_ROLE_VIEW);
    UserRoleDTO userRolesDTO = new UserRoleDTO();
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      validateEditRoleResponse(roleIdData, session, model, userRolesDTO);
      
    } catch (Exception e) {
      logger.error("ERROR :: RoleController :: getRoleDetails method",e);
    }
    logger.info("Exiting :: RoleController :: getRoleDetails method");
    model.put(Constants.USER_ROLES_DTO, userRolesDTO);
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_CHATAK_ADMIN_ROLE_EDIT, method = RequestMethod.POST)
  public ModelAndView updateRole(HttpServletRequest request, UserRoleDTO userRolesDTO,
      HttpSession session, HttpServletResponse response, Map model) {
    logger.info("Entering :: RoleController :: updateRole method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_ROLE_EDIT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ROLES_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      userRolesDTO.setCreatedBy(loginResponse.getUserId().toString());
      userRolesDTO.setUpdatedBy(loginResponse.getUserId().toString());
      userRolesDTO.setUpdatedDate(DateUtil.getCurrentTimestamp());
      String permissionData = request.getParameter("permissions");
     
      logger.error("ERROR:: RoleController:: updateRole method"+"permissionData="+permissionData);
      
      userRolesDTO.setFeature(StringUtil.converArray(permissionData));
      roleService.updateRoles(userRolesDTO);
      modelAndView = showRoleSearch(request, response, model, session);
      modelAndView.addObject(Constants.SUCESS,
          messageSource.getMessage("role.update.message", null, LocaleContextHolder.getLocale()));
    } catch (Exception e) {
      logger.error("ERROR:: RoleController:: updateRole method", e);
      modelAndView.setViewName(CHATAK_SHOW_ROLE_EDIT_PAGE);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null, LocaleContextHolder.getLocale()));
      model.put(Constants.USER_ROLES_DTO, userRolesDTO);
    }
    logger.info("Exiting :: RoleController :: updateRole method");
    return modelAndView;
  }

  public List<RoleLevel> getRoleListForRoles(List<RoleLevel> roleLevelList, HttpSession session,
      List<RoleLevel> roleLevels) {
    LoginResponse loginResponse =
        (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.CP_SUPER_ADMIN.name())) {
      Arrays.asList(RoleLevel.CP_SUPER_ADMIN, RoleLevel.CP_MERCHANT,
          RoleLevel.CP_RESELLER, RoleLevel.CP_TMS);
    }
    if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.CP_MERCHANT.name())) {
      Arrays.asList(RoleLevel.CP_MERCHANT);
    }
    if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.CP_RESELLER.name())) {
      Arrays.asList(RoleLevel.CP_RESELLER);
    }
    if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.CP_TMS.name())) {
      Arrays.asList(RoleLevel.CP_TMS);
    }
    return roleLevels;
  }
  
  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("roleList-file-exportutil-roleType", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("roleList-file-exportutil-rolename", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("roleList-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<UserRolesDTO> userRoleList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (UserRolesDTO roleData : userRoleList) {

      Object[] rowData = {roleData.getRoleType(), roleData.getRoleName(),
          roleData.getStatus().intValue() == 0 ? "Active" : "InActive"

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
