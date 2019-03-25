package com.chatak.acquirer.admin.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserData;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.enums.RoleLevel;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.model.MposFeatures;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.IsoResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;

@Controller
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserManagementController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(UserManagementController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  RoleService roleService;

  @Autowired
  UserService userService;

  @Autowired
  MerchantUpdateService merchantUpdateService;
  
  @Autowired
  RoleController roleController;
  
  @Autowired
  ProgramManagerService programManagerService;
  
  @Autowired
  IsoService isoService;

  /**
   * Method to create the user
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_USER_SEARCH, method = RequestMethod.GET)
  public ModelAndView showUser(HttpServletRequest request, HttpServletResponse response, Map model,
      HttpSession session) {
    logger.info("Entering:: UserManagementController:: showUser method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
      roleController.getRoleListForRoles(session, model);
      List<UserRolesDTO> userRoleList = roleService.getRoleList();
      session.setAttribute("userRoleListData", userRoleList);
      GenericUserDTO genericUserDTO = new GenericUserDTO();
      genericUserDTO.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
      genericUserDTO.setPageIndex(Constants.ONE);
      Long adminUserId = (Long) session.getAttribute("adminId");
      if (adminUserId != null) {
        genericUserDTO.setAdminUserId(adminUserId);
      }
      model.put("userDataDto", genericUserDTO);
      session.setAttribute(Constants.USER_SEARCH_REQUEST, genericUserDTO);
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: showUser method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", false);
    logger.info("Exit:: UserManagementController:: showUser method");
    return modelAndView;
  }

  private ModelAndView setInvalidRequestPage(HttpSession session, ModelAndView modelAndView) {
	session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
  }

  /**
   * Method for search user
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @param userDataDto
   * @return
   */
  @RequestMapping(value = CHATAK_USER_SEARCH, method = RequestMethod.POST)
  public ModelAndView searchUser(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session, GenericUserDTO userDataDto) {
    logger.info("Entering:: UserManagementController:: searchUser method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
    	roleController.getRoleListForRoles(session, model);
    	LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      List<UserRolesDTO> userRoleList = roleService.getRoleList();
      session.setAttribute("userRoleListData", userRoleList);
      userDataDto.setPageIndex(Constants.ONE);
      int pageSize = userDataDto.getPageSize();
      model.put("userDataDto", userDataDto);
      Long adminUserId = (Long) session.getAttribute("adminId");
      if (adminUserId != null) {
        userDataDto.setAdminUserId(adminUserId);
      }
      int nofAdminUsers = 0;
      int nofMerchantUsers = 0;
      List<GenericUserDTO> userList = new ArrayList<GenericUserDTO>();
      List<GenericUserDTO> userList1 = new ArrayList<GenericUserDTO>();
      String userType = userDataDto.getUserType();
      Long entityId = loginResponse.getEntityId();
      String loginuserType = loginResponse.getUserType();
      userDataDto.setEntityId(entityId);
      userDataDto.setLoginuserType(loginuserType);
      if (Constants.ADMIN_USER_TYPE.equalsIgnoreCase(userType)
    		  || Constants.PM_USER_TYPE.equalsIgnoreCase(userType)
    		  || Constants.ISO_USER_TYPE.equalsIgnoreCase(userType)) {
    	userDataDto.setEntityId(loginResponse.getEntityId());
        userList = userService.searchAdminUser(userDataDto);
        nofAdminUsers = userDataDto.getNoOfRecords();
      } else if (Constants.TYPE_MERCHANT.equalsIgnoreCase(userType)) {
        userDataDto.setAdminUserId(null);
        userList1 = userService.searchMerchantUser(userDataDto);
        nofMerchantUsers = userDataDto.getNoOfRecords();
      } else {
        userDataDto.setPageSize(Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE);
        userList = userService.searchAdminUser(userDataDto);
        nofAdminUsers = userDataDto.getNoOfRecords();
        userDataDto.setAdminUserId(null);
        userList1 = userService.searchMerchantUser(userDataDto);
        nofMerchantUsers = userDataDto.getNoOfRecords();
      }
      userDataDto.setNoOfRecords(nofAdminUsers + nofMerchantUsers);
      List<GenericUserDTO> userList3 = new ArrayList<GenericUserDTO>();
      userList3.addAll(userList);
      userList3.addAll(userList1);
      List<GenericUserDTO> sortedList = distinctList(userList3);
      userDataDto.setPageSize(pageSize);
      session.setAttribute(Constants.USER_SEARCH_REQUEST, userDataDto);
      if (StringUtils.isListNotNullNEmpty(sortedList)) {
        modelAndView.addObject("pageSize", pageSize);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            userDataDto.getNoOfRecords().intValue());
        session.setAttribute("pageNumber", Constants.ONE);
      }
      session.setAttribute(Constants.TOTAL_RECORDS, userDataDto.getNoOfRecords());
      modelAndView.addObject(Constants.USER_LIST, sortedList);
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: searchUser method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", true);
    logger.info("Exit:: UserManagementController:: searchUser method");
    return modelAndView;
  }

  /**
   * 
   * Method for create the user
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_USER_CREATE, method = RequestMethod.GET)
  public ModelAndView showCreateUser(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session) {
    logger.info("Entering:: UserManagementController:: showCreateUser method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_CREATE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_CREATE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
    	roleController.getRoleListForRoles(session, model);
      List<UserRolesDTO> userRoleList = roleService.getRoleList();
      session.setAttribute("userRoleListData", userRoleList);
      UserData userData = new UserData();
      final String requestType = request.getParameter("requestType");
      if (requestType != null && requestType.equalsIgnoreCase(Constants.USERS_GROUP_ADMIN)) {
        userData.setRequestType(Constants.USERS_GROUP_ADMIN);
      } else if (requestType != null
          && requestType.equalsIgnoreCase(Constants.USERS_GROUP_MERCHANT)) {
        userData.setRequestType(Constants.USERS_GROUP_MERCHANT);
      }
      /*To check is email uniqueness is allowed or not*/
      String isUserMailUnique = Properties.getProperty("prepaid.email.unique.enable");
      model.put("isUserMailUnique", isUserMailUnique);
      session.setAttribute("isUserMailUnique", isUserMailUnique);
      model.put("userData", userData);
      Map<String, String> merchantsMap = null;
      merchantsMap = merchantUpdateService
          .getMerchantNameAndMerchantCodeAsMapByMerchantType(Constants.TYPE_MERCHANT);
      session.setAttribute("merchantList", new HashMap(merchantsMap));
      modelAndView.addObject("merchantList", merchantsMap);
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: searchUser method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: UserManagementController:: showCreateUser method");
    return modelAndView;
  }

  /**
   * Method for create user
   * 
   * @param request
   * @param session
   * @param response
   * @param model
   * @param userData
   * @return
   */
  @RequestMapping(value = CHATAK_USER_CREATE, method = RequestMethod.POST)
  public ModelAndView createUser(HttpServletRequest request, HttpSession session,
      HttpServletResponse response, UserData userData, Map model) {
    logger.info("Entering:: UserManagementController:: CreateUser method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_CREATE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_CREATE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
    	roleController.getRoleListForRoles(session, model);
      model.put("userData", userData);
      String url = request.getRequestURL().toString();
      String uri = request.getRequestURI();
      if (Constants.TYPE_MERCHANT.equals(userData.getRoleType())) {
        String merchantLink = url.substring(0, url.length() - uri.length()) + "/"
            + Properties.getProperty("chatak.merchant.portal");
        userData.setMerchantLink(merchantLink);
      } else if (Constants.ADMIN_USER_TYPE.equals(userData.getRoleType()) 
    		  || RoleLevel.CP_PM.getValue().equalsIgnoreCase(userData.getRoleType())
    		  || RoleLevel.CP_ISO.getValue().equalsIgnoreCase(userData.getRoleType())) {
        String merchantLink = url.substring(0, url.length() - uri.length()) + "/"
            + Properties.getProperty("chatak.acquirer.admin");
        userData.setMerchantLink(merchantLink);
      }
      Long userId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      userData.setCreatedBy(userId.toString());
      userService.saveUser(userData);
      model.put(Constants.SUCESS, messageSource.getMessage("chatak.user.create.message", null,
          LocaleContextHolder.getLocale()));
      return showUser(request, response, model, session);
    } catch (ChatakAdminException e) {
      logger.error("Error :: UserManagementController:: CreateUser ChatakAdminException", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: CreateUser method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()) + " ," + e.getMessage());
    }
    logger.info("Exit:: UserManagementController:: CreateUser method");
    return modelAndView;
  }

  /**
   * Method for get the user pagination
   * 
   * @param session
   * @param pageNumber
   * @param totalRecords
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_USER_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getUserPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("requestType") final String requestType, Map model) {
    logger.info("Entering:: UserManagementController:: getUserPagination method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_SEARCH);
    GenericUserDTO userDataDto = null;
    List<GenericUserDTO> userList3 = null;
    try {
    	roleController.getRoleListForRoles(session, model);
      userDataDto = (GenericUserDTO) session.getAttribute(Constants.USER_SEARCH_REQUEST);
      model.put("userDataDto", userDataDto);
      userDataDto.setPageIndex(pageNumber);
      userList3 = new ArrayList<>();
      int pageSize = userDataDto.getPageSize();
      // Don't fetch the currently logged in user
      Long adminUserId = (Long) session.getAttribute("adminId");
      if (adminUserId != null) {
        userDataDto.setAdminUserId(adminUserId);
      }
      List<GenericUserDTO> userList = new ArrayList<>();
      List<GenericUserDTO> userList1 = new ArrayList<>();
      String userType = userDataDto.getUserType();
      if (userType != null && Constants.ADMIN_USER_TYPE.equalsIgnoreCase(userType)) {
        userList = userService.searchAdminUser(userDataDto);
      } else if (userType != null && Constants.TYPE_MERCHANT.equalsIgnoreCase(userType)) {
        userDataDto.setAdminUserId(null);
        userList1 = userService.searchMerchantUser(userDataDto);
      } else {
        userDataDto.setPageSize(Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE);
        userList = userService.searchAdminUser(userDataDto);
        userDataDto.setAdminUserId(null);
        userList1 = userService.searchMerchantUser(userDataDto);
      }
      userList3.addAll(userList);
      userList3.addAll(userList1);
      userDataDto.setPageSize(pageSize);
      userDataDto.setNoOfRecords(totalRecords);
      if (StringUtil.isListNotNullNEmpty(userList3)) {
        modelAndView.addObject("pageSize", pageSize);
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            userDataDto.getNoOfRecords().intValue());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
        modelAndView.addObject(Constants.USER_LIST, userList3);
      }
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: getUserPagination method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: UserManagementController:: getUserPagination method");
    return modelAndView;
  }

  /**
   * Method for download report
   * @param session
   * @param model
   * @param request
   * @param downLoadPageNumber
   * @param downloadType
   * @param response
   * @return
   */
  @RequestMapping(value = DOWNLOAD_USER_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadUserReport(HttpSession session, Map model, HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: UserManagementController:: downloadUserReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_SEARCH);
    GenericUserDTO userData = null;
    List<GenericUserDTO> userDataList = null;
    try {
      userData = (GenericUserDTO) session.getAttribute(Constants.USER_SEARCH_REQUEST);
      userData.setPageIndex(downLoadPageNumber);
      Integer pageSize = userData.getPageSize();
      if (downloadAllRecords) {
        userData.setPageIndex(Constants.ONE);
        userData.setPageSize(totalRecords);
      }
      userDataList = new ArrayList<GenericUserDTO>();
      List<GenericUserDTO> userList = userService.searchAdminUser(userData);
      List<GenericUserDTO> userListForMerchant = userService.searchMerchantUser(userData);
      userDataList.addAll(userList);
      userDataList.addAll(userListForMerchant);
      ExportDetails exportDetails = new ExportDetails();
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.XLS);
        exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
    }
    setExportDetailsDataForDownloadRoleReport(userDataList, exportDetails); 
    ExportUtil.exportData(exportDetails, response, messageSource);
      userData.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: UserManagementController:: downloadUserReport method", e);
    }
    logger.info("Exit:: UserManagementController:: downloadUserReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<GenericUserDTO> userList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("User_");
    exportDetails.setHeaderMessageProperty("chatak.header.user.messages");

    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(userList));
  }

  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("report-common-created-date", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("userList-file-exportutil-userType", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("userList-file-exportutil-roleName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.merchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.merchantname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.submerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("sub-merchant-account-search.label.sub-merchantname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-userName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("userList-file-exportutil-firstName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("userList-file-exportutil-lastName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("userList-file-exportutil-emailId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("userList-file-exportutil-status", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("report-common-suspended-date", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<GenericUserDTO> userList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (GenericUserDTO userData : userList) {
      String status = "";
      status = getStatus(userData);
          
      Object[] rowData = new Object[Integer.parseInt("13")];
      rowData[0] = getTimeString(userData.getCreatedDate());
      rowData[1] = userData.getUserType();
      rowData[Integer.parseInt("2")] = userData.getUserRoleName();
      if (userData.getUserType().equals("Merchant")) {
        rowData[Integer.parseInt("3")] =userData.getMerchantCode();
        rowData[Integer.parseInt("4")] =userData.getMerchantName();
      } else {
        rowData[Integer.parseInt("3")] = " ";
        rowData[Integer.parseInt("4")] = " ";
      }
      if (userData.getUserType().equals("SubMerchant")) {
        rowData[Integer.parseInt("5")] =userData.getMerchantCode();
        rowData[Integer.parseInt("6")] =userData.getMerchantName();
      } else {
        rowData[Integer.parseInt("5")] = " ";
        rowData[Integer.parseInt("6")] = " ";
      }
      rowData[Integer.parseInt("7")] = userData.getUserName();
      rowData[Integer.parseInt("8")] = userData.getFirstName();
      rowData[Integer.parseInt("9")] = userData.getLastName();
      rowData[Integer.parseInt("10")] = userData.getEmail();
      rowData[Integer.parseInt("11")] = status;
      if (userData.getStatus().equals(Constants.TWO)) {
        rowData[Integer.parseInt("12")] = userData.getUpdatedDate();
      } else {
        rowData[Integer.parseInt("12")] = " ";
      }
      fileData.add(rowData);
      }
    return fileData;
    }
  
  private static String getStatus(GenericUserDTO userData) {
    String status;
    if (userData.getStatus() == 0) {
      status = "Active";
    } else if (userData.getStatus().equals(Constants.ONE)) {
      status = "Pending";
    } else if (userData.getStatus().equals(Constants.TWO)) {
      status = "Suspended";
    } else if (userData.getStatus().equals(Constants.THREE)) {
      status = "Deleted";
    } else {
      status = "Declined";
    }
    return status;
  }
  
  private static String getTimeString(Timestamp time) {
    return (DateUtil.toDateStringFormat(time, DateUtil.VIEW_DATE_TIME_FORMAT));
  }
  
  /**
   * Method for edit user
   * @param model
   * @param request
   * @param session
   * @param userIdData
   * @return
   */
  @RequestMapping(value = CHATAK_USER_EDIT, method = RequestMethod.POST)
  public ModelAndView editUSer(Map model, HttpServletRequest request, HttpSession session,
      @FormParam("userIdData") final Long userIdData,
      @FormParam("usersGroupType") final String usersGroupType) {
    logger.info("Entering:: UserManagementController:: editUSer method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_EDIT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_EDIT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {

      UserData userData = validateUserRoleList(request, session, userIdData, usersGroupType, model);
      model.put(Constants.USER_EDIT_DATA, userData);
      validateSessionAttribute(session, modelAndView);
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: editUSer method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: UserManagementController:: editUSer method");
    return modelAndView;
  }

  private void validateSessionAttribute(HttpSession session, ModelAndView modelAndView) {
	Map<String, String> merchantsMap = null;
      if (null != session.getAttribute("merchantList")) {
        merchantsMap = (HashMap<String, String>) session.getAttribute("merchantList");
      } else {
        merchantsMap = merchantUpdateService
            .getMerchantNameAndMerchantCodeAsMapByMerchantType(Constants.TYPE_MERCHANT);
      }
      modelAndView.addObject("merchantList", merchantsMap);
  }

  private UserData validateUserRoleList(HttpServletRequest request, HttpSession session, final Long userIdData,
		final String usersGroupType,Map model) throws ChatakAdminException {
	LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
	List<UserRoleDTO> userRoleList = roleService.getRoleListByType(usersGroupType);
    session.setAttribute("userRoleListData", userRoleList);
    UserData userData = userService.getUserDataOnUsersGroupType(userIdData, usersGroupType);
    final String requestType = request.getParameter("requestType");
    if (requestType != null && requestType.equalsIgnoreCase(Constants.USERS_GROUP_ADMIN)) {
      userData.setRequestType(Constants.USERS_GROUP_ADMIN);
    } else if (requestType != null
        && requestType.equalsIgnoreCase(Constants.USERS_GROUP_MERCHANT)) {
      userData.setRequestType(Constants.USERS_GROUP_MERCHANT);
    }
    if(userData.getUserType().equals(Constants.MPOS_MERCHANT)){
        model.put("mposFeature", userData.getMpsoFeatures());
    }
    ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
    Map<Long, String> entityMap = new HashMap<>();
    if(userData.getUserType().equalsIgnoreCase(Constants.PM_USER_TYPE) && !StringUtil.isNull(userData.getEntityId())) {
    	programManagerRequest.setId(userData.getEntityId());
    	programManagerRequest.setLoginuserType(loginResponse.getUserType());
    	programManagerRequest.setEntityId(loginResponse.getEntityId());
        ProgramManagerResponse programManagerResponse = programManagerService
      		  .getAllProgramManagers(programManagerRequest);
    	entityMap = getEntityKeyValuePairs(programManagerResponse);
    }else if(userData.getUserType().equalsIgnoreCase(Constants.ISO_USER_TYPE) && !StringUtil.isNull(userData.getEntityId())) {
    	IsoRequest isoRequest = new IsoRequest();
        isoRequest.setProgramManagerRequest(programManagerRequest);
        isoRequest.setId(userData.getEntityId());
        IsoResponse isoResponse = isoService.getAllIso(isoRequest);
    	entityMap = getEntityKeyValuePairs(isoResponse);
    }
    model.put("entityList", entityMap);
	return userData;
  }

  /**
   * 
   * Method for update user
   * @param model
   * @param request
   * @param response
   * @param userData
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_USER_VIEW, method = RequestMethod.POST)
  public ModelAndView viewUSer(Map model, HttpServletRequest request, HttpSession session,
      @FormParam("userIdData") final Long userIdData,
      @FormParam("usersGroupType") final String usersGroupType) {
    logger.info("Entering:: UserManagementController:: editUSer method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_VIEW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_VIEW_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {

      UserData userData = validateUserRoleList(request, session, userIdData, usersGroupType, model);
      model.put(Constants.USER_VIEW_DATA, userData);
      validateSessionAttribute(session, modelAndView);
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: viewUSer method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: UserManagementController:: editUSer method");
    return modelAndView;
  }

  @RequestMapping(value = UPDATE_USER, method = RequestMethod.POST)
  public ModelAndView updateUser(Map model, HttpServletRequest request,
      HttpServletResponse response, UserData userData, HttpSession session) {
    logger.info("Entering:: UserManagementController:: updateUser method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_EDIT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_EDIT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
      Long createdBy = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      userData.setCreatedBy(String.valueOf(createdBy));
      model.put(Constants.USER_EDIT_DATA, userData);
      userService.updateUser(userData);
      model.put(Constants.SUCESS, messageSource.getMessage("chatak.user.request.update.message",
          null, LocaleContextHolder.getLocale()));
      return showUser(request, response, model, session);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: UserManagementController:: updateUser method", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: updateUser method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: UserManagementController:: updateUser method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_USER_EMAIL_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateUserEmailId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: UserManagementController :: validateUniqueUserName method");
    String emailId = request.getParameter("emailId");
    UserData userResponse = null;

    try {
      userResponse = userService.validateEmailId(emailId);
      return JsonUtil.convertObjectToJSON(userResponse);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: UserManagementController:: validateUniqueUserName method", e);
      setErrorCodeAndErrorMsg(modelAndView);
    }
    logger.info("Exiting:: UserManagementController:: validateUniqueUserName method");
    return null;
  }

  @RequestMapping(value = DELETE_MERCHANT_USER, method = RequestMethod.POST)
  public ModelAndView deleteUser(Map model, HttpServletRequest request, HttpSession session,
      @FormParam("getMerchantUserId") final Long getMerchantUserId,
      @FormParam("usersGroupTypes") final String usersGroupTypes, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_SEARCH);
    logger.info("Entering:: UserManagementController:: editUSer method");
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_DELETE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
      UserData userData = new UserData();
      model.put("userData", userData);
      userData = userService.deleteMerchantUser(getMerchantUserId, usersGroupTypes);
      if (null != userData && "00".equals(userData.getErrorCode())) {
        model.put(Constants.SUCESS, "User deleted successfully!");
        return showUser(request, response, model, session);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: UserManagementController:: editUSer method", e);
      model.put(Constants.ERROR, messageSource.getMessage("chatak.user.delete.message", null,
          LocaleContextHolder.getLocale()));
      return showUser(request, response, model, session);
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: editUSer method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: UserManagementController:: editUSer method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_USER_TYPE_VALUE)
  public ModelAndView showUserTypeValue(HttpServletRequest request, HttpServletResponse response,
      @FormParam("rolesType") final String rolesType, Map model, HttpSession session) {
    logger.info("Entering:: UserManagementController:: showCreateUser method");
      
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_CREATE);
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
    	roleController.getRoleListForRoles(session, model);
      List<UserRoleDTO> userRoleList = roleService.getRoleListByType(rolesType);
      session.setAttribute("userRoleListData", userRoleList);
      List<MposFeatures> mposFeature = null;
      if(rolesType.equals(Constants.MPOS_MERCHANT)){
    	  mposFeature = roleService.getMposeFeatures();
          model.put("mposFeature", mposFeature);
      }
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      IsoRequest isoRequest = new IsoRequest();
      CommonUtil.setEntityIdsFromUserType(programManagerRequest, isoRequest, session);
      programManagerRequest.setStatuses(Arrays.asList("Active"));
      programManagerRequest.setLoginuserType(loginResponse.getUserType());
      programManagerRequest.setEntityId(loginResponse.getEntityId());
      ProgramManagerResponse programManagerResponse = programManagerService
    		  .getAllProgramManagers(programManagerRequest);
      isoRequest.setProgramManagerRequest(programManagerRequest);
      IsoResponse isoResponse = isoService.getAllIso(isoRequest);
      Map<Long, String> entityMap = new HashMap<>();
      if (rolesType.equalsIgnoreCase(Constants.PM_USER_TYPE)) {
    	  entityMap = getEntityKeyValuePairs(programManagerResponse);
      } else if (rolesType.equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
    	  entityMap = getEntityKeyValuePairs(isoResponse);
      }
      model.put("entityList", entityMap);
      UserData userData = new UserData();
      userData.setRoleType(rolesType);
      model.put("userData", userData);
    } catch (Exception e) {
      logger.error("ERROR:: UserManagementController:: searchUser method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE , null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: UserManagementController:: showCreateUser method");
    return modelAndView;
  }

  public static List<GenericUserDTO> distinctList(List<GenericUserDTO> list) {
    if (list.isEmpty()) {
      return list;
    } else {
      Collections.sort(list, (GenericUserDTO o1, GenericUserDTO o2) -> {
        return o1.getCreatedDate().before(o2.getCreatedDate()) ? 1
            : validateCreatedDate(o1, o2);
      });
      return list;
    }
  }

  private static int validateCreatedDate(GenericUserDTO o1, GenericUserDTO o2) {
    return o1.getCreatedDate().after(o2.getCreatedDate()) ? -1 : 0;
  }

  @RequestMapping(value = USER_ACTIVATION_SUSPENTION, method = RequestMethod.POST)
  public ModelAndView changeUserStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long userId,
      @FormParam("suspendActiveStatus") final String userStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: UserRoleController:: changeUserStatus method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_USERS_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
      UserData userData = new UserData();
      userData.setUserId(userId);
      userData.setReason(reason);
      GenericUserDTO userDataDto =
          (GenericUserDTO) session.getAttribute(Constants.USER_SEARCH_REQUEST);
      userData.setRoleType(userDataDto.getUserType());
      Response userResponse = userService.changeUserStatus(userData, userStatus);
      modelAndView = getUserPagination(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS),
          (String) session.getAttribute("requestType"), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(userResponse.getErrorCode())) {
        model.put(Constants.SUCESS, userResponse.getErrorMessage());
      } else {
        model.put(Constants.ERROR, userResponse.getErrorMessage());
      }
    } catch (Exception e) {
      logger.error("ERROR:: UserRoleController:: changeUserStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: UserRoleController:: changeUserStatus method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_USERNAME_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateUserName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: UserManagementController :: validateUniqueUserName method");
    String userName = request.getParameter("userName");
    UserData userResponse = null;
    try {
      userResponse = userService.validateUserName(userName);
      return JsonUtil.convertObjectToJSON(userResponse);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: UserManagementController:: validateUniqueUserName method", e);
      setErrorCodeAndErrorMsg(modelAndView);
    }
    logger.info("Exiting:: UserManagementController:: validateUniqueUserName method");
    return null;
  }
  
  @RequestMapping(value = CHATAK_ADMIN_MERCHANTCODE_BY_NAME, method = RequestMethod.GET)
  public @ResponseBody String validateMerchantIDByName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_USER_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: UserManagementController :: validateMerchantIDByName method");
    String merchantId = request.getParameter("merchantId");
    UserData userResponse = null;
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      userResponse = userService.merchantIdByMerchantName(merchantId, loginResponse.getEntityId(), loginResponse.getUserType());
      return JsonUtil.convertObjectToJSON(userResponse);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: UserManagementController:: validateMerchantIDByName method", e);
      setErrorCodeAndErrorMsg(modelAndView);
    }
    logger.info("Exiting:: UserManagementController:: validateMerchantIDByName method");
    return null;
  }

  private void setErrorCodeAndErrorMsg(ModelAndView modelAndView) {
	UserData userResponse;
	userResponse = new UserData();
    userResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
    userResponse
        .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    modelAndView.addObject(Constants.ERROR,
        messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    userResponse.setRequestType(Constants.USERS_GROUP_MERCHANT);
  }
	
	private Map<Long,String> getEntityKeyValuePairs(Object entityObj) {
		Map<Long,String> entityMap = new HashMap<>();
		if(entityObj instanceof ProgramManagerResponse) {
			List<ProgramManagerRequest> programManagerList = ((ProgramManagerResponse) entityObj).getProgramManagersList();
			for(ProgramManagerRequest programManagerRequest : programManagerList) {
				entityMap.put(programManagerRequest.getId(), programManagerRequest.getProgramManagerName());
			}
		} else if(entityObj instanceof IsoResponse) {
			List<IsoRequest> isoList = ((IsoResponse) entityObj).getIsoRequest();
			for(IsoRequest isoRequest : isoList) {
				entityMap.put(isoRequest.getId(), isoRequest.getIsoName());
			}
		}
		return entityMap;
	}
	
}
