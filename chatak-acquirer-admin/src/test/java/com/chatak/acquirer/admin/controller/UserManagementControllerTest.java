/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserData;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 1, 2018 12:45:08 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class UserManagementControllerTest {

  private static Logger logger = Logger.getLogger(UserManagementControllerTest.class);

  @InjectMocks
  UserManagementController controller = new UserManagementController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  BindingResult bindingResult;

  MockMvc mockMvc;

  @Mock
  MessageSource messageSource;

  @Mock
  NullPointerException nullPointerException;

  @Mock
  List<Option> optionList;

  @Mock
  Timestamp date;

  @Mock
  RoleService roleService;

  @Mock
  UserService userService;

  @Mock
  MerchantUpdateService merchantUpdateService;

  @Mock
  List<UserRolesDTO> userRolesDTOs;

  @Mock
  List<GenericUserDTO> genericUserDTOs;

  @Mock
  GenericUserDTO genericUserDTO;

  @Mock
  UserData UserData;

  @Mock
  Map<String, String> merchantsMap;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
  }

  @Before
  public void pro() {
    java.util.Properties properties = new java.util.Properties();
    properties.setProperty("admin.services.users.feature.id", "notExist");
    properties.setProperty("admin.services.users.feature.id", "exist");
    properties.setProperty("admin.services.users.create.feature.id", "notExist");
    properties.setProperty("admin.services.users.create.feature.id", "exist");
    properties.setProperty("admin.services.users.edit.feature.id", "notExist");
    properties.setProperty("admin.services.users.edit.feature.id", "exist");

    properties.setProperty("admin.services.users.view.feature.id", "notExist");
    properties.setProperty("admin.services.users.view.feature.id", "exist");
    properties.setProperty("admin.services.users.delete.feature.id", "notExist");
    properties.setProperty("admin.services.users.delete.feature.id", "exist");
    
    Properties.mergeProperties(properties);
  }

  @Test
  public void testShowUserExistingFeature() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_USER_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist").sessionAttr("adminId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testShowUserExistingFeature", e);

    }
  }

  @Test
  public void testShowUser() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_USER_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).sessionAttr("adminId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testShowUser", e);

    }
  }

  @Test
  public void testShowUserException() {
    try {
      Mockito.when(roleService.getRoleList()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_USER_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).sessionAttr("adminId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testShowUserException", e);

    }
  }

  @Test
  public void testSearchUserExistingFeatures() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_SEARCH).sessionAttr("adminId", 1l)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist").param(Constants.PAGE_SIZE, "1")
              .param("noOfRecords", "1").param("userType", "Admin").param("noOfRecords", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testSearchUserExistingFeatures", e);

    }
  }

  @Test
  public void testSearchUserAdmin() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_SEARCH).sessionAttr("adminId", 1l)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(Constants.PAGE_SIZE, "1")
              .param("noOfRecords", "1").param("userType", "Admin").param("noOfRecords", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testSearchUserAdmin", e);

    }
  }

  @Test
  public void testSearchUserMerchant() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_SEARCH).sessionAttr("adminId", 1l)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(Constants.PAGE_SIZE, "1")
              .param("noOfRecords", "1").param("userType", "Merchant").param("noOfRecords", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testSearchUserMerchant", e);

    }
  }

  @Test
  public void testSearchUser() {
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTOs = new ArrayList<>();
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchAdminUser(genericUserDTO)).thenReturn(genericUserDTOs);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_SEARCH).sessionAttr("adminId", 1l)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(Constants.PAGE_SIZE, "1")
              .sessionAttr("sortedList", genericUserDTOs).param("noOfRecords", "1")
              .param("userType", "").param("noOfRecords", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testSearchUser", e);

    }
  }

  @Test
  public void testSearchUserException() {
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTOs = new ArrayList<>();
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchMerchantUser(genericUserDTO)).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_SEARCH).sessionAttr("adminId", 1l)
              .param(Constants.PAGE_SIZE, "1").sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("userType", "Admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testSearchUserException", e);

    }
  }

  @Test
  public void testShowCreateUserRequestTypeAdmin() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_USER_CREATE).sessionAttr("adminId", 1l)
              .param(Constants.PAGE_SIZE, "1").sessionAttr("existingFeature", "existingFeature")
              .param("requestType", "ADMIN").sessionAttr(Constants.EXISTING_FEATURES, "existingFeature"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testShowCreateUserRequestTypeAdmin",
          e);

    }
  }

  @Test
  public void testShowCreateUserNoFeature() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_USER_CREATE).sessionAttr("adminId", 1l)
              .param(Constants.PAGE_SIZE, "1").sessionAttr("existingFeature", "notExist")
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testShowCreateUserNoFeature", e);

    }
  }

  @Test
  public void testShowCreateUserRequestTypeMerchant() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_USER_CREATE).sessionAttr("adminId", 1l)
              .param(Constants.PAGE_SIZE, "1").sessionAttr("existingFeature", "existingFeature")
              .param("requestType", "MERCHANT").sessionAttr(Constants.EXISTING_FEATURES, "existingFeature"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testShowCreateUserRequestTypeMerchant",
          e);

    }
  }

  @Test
  public void testShowCreateUserException() {
    try {
      Mockito.when(roleService.getRoleList()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_USER_CREATE).sessionAttr("adminId", 1l)
              .param(Constants.PAGE_SIZE, "1").sessionAttr("existingFeature", "existingFeature")
              .param("requestType", "MERCHANT").sessionAttr(Constants.EXISTING_FEATURES, "existingFeature"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testShowCreateUserException", e);

    }
  }

  @Test
  public void testCreateUserNotExist() {
    try {
      Mockito.when(roleService.getRoleList()).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_CREATE).sessionAttr("adminId", 1l)
              .param(Constants.PAGE_SIZE, "1").sessionAttr("existingFeature", "notExist")
              .param("requestType", "MERCHANT").sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testCreateUserNotExist", e);

    }
  }

  @Test
  public void testCreateUserExistRoleTypeAdmin() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_CREATE).sessionAttr("loginUserId", 1l)
              .sessionAttr("existingFeature", "exist").param("roleType", "Admin")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testCreateUserExistRoleTypeAdmin", e);

    }
  }

  @Test
  public void testCreateUserExistRoleTypeMerchant() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_CREATE).sessionAttr("loginUserId", 1l)
              .sessionAttr("existingFeature", "exist").param("roleType", "Merchant")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testCreateUserExistRoleTypeMerchant",
          e);

    }
  }

  @Test
  public void testGetUserPagination() {
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTO.setAddress2("address2");
    genericUserDTO.setPageSize(Integer.parseInt("1"));
    genericUserDTOs = new ArrayList<>();
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchAdminUser(genericUserDTO)).thenReturn(genericUserDTOs);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_PAGINATION)
              .sessionAttr("userSearchRequest", genericUserDTO).param("pageNumber", "1")
              .param("totalRecords", "1").sessionAttr("adminId", 1l).sessionAttr(Constants.PAGE_SIZE, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testGetUserPagination", e);

    }
  }

  @Test
  public void testGetUserPaginationException() {
    try {
      Mockito.when(userService.searchAdminUser(genericUserDTO)).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_PAGINATION)
              .sessionAttr("userSearchRequest", genericUserDTO).param("pageNumber", "1")
              .param("totalRecords", "1").sessionAttr("adminId", 1l).sessionAttr(Constants.PAGE_SIZE, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testGetUserPaginationException", e);

    }
  }

  @Test
  public void testGetUserPaginationAdmin() {
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTO.setAddress2("address2");
    genericUserDTO.setPageSize(Integer.parseInt("1"));
    genericUserDTO.setUserType("Admin");
    genericUserDTOs = new ArrayList<>();
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchAdminUser(genericUserDTO)).thenReturn(genericUserDTOs);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_PAGINATION)
              .sessionAttr("userSearchRequest", genericUserDTO).param("pageNumber", "1")
              .param("totalRecords", "1").sessionAttr("adminId", 1l).sessionAttr(Constants.PAGE_SIZE, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testGetUserPaginationAdmin", e);

    }
  }

  @Test
  public void testGetUserPaginationMerchant() {
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTO.setAddress2("address2");
    genericUserDTO.setPageSize(Integer.parseInt("1"));
    genericUserDTO.setUserType("Merchant");
    genericUserDTOs = new ArrayList<>();
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchAdminUser(genericUserDTO)).thenReturn(genericUserDTOs);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_PAGINATION)
              .sessionAttr("userSearchRequest", genericUserDTO).param("pageNumber", "1")
              .param("totalRecords", "1").sessionAttr("adminId", 1l).sessionAttr(Constants.PAGE_SIZE, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testGetUserPaginationMerchant", e);

    }
  }

  @Test
  public void testDownloadUserReportPDF() {
    genericUserDTOs = new ArrayList<>();
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTO.setAddress2("address2");
    genericUserDTO.setStatus(Integer.parseInt("1"));
    genericUserDTO.setUserType("Merchant");
    genericUserDTO.setUpdatedDate(date);
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchAdminUser(Matchers.any(GenericUserDTO.class)))
          .thenReturn(genericUserDTOs);
      Mockito.when(userService.searchMerchantUser(Matchers.any(GenericUserDTO.class)))
          .thenReturn(genericUserDTOs);
      Mockito.when(userService.searchAdminUser(genericUserDTO)).thenReturn(genericUserDTOs);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_USER_REPORT)
          .sessionAttr("userSearchRequest", genericUserDTO).param("downLoadPageNumber", "1")
          .param("downloadAllRecords", "true").param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testDownloadUserReportPDF", e);

    }
  }

  @Test
  public void testDownloadUserReportXLS() {
    genericUserDTOs = new ArrayList<>();
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTO.setAddress2("address2");
    genericUserDTO.setStatus(Integer.parseInt("1"));
    genericUserDTO.setUserType("Merchant");
    genericUserDTO.setUpdatedDate(date);
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchAdminUser(Matchers.any(GenericUserDTO.class)))
          .thenReturn(genericUserDTOs);
      Mockito.when(userService.searchMerchantUser(Matchers.any(GenericUserDTO.class)))
          .thenReturn(genericUserDTOs);
      Mockito.when(userService.searchAdminUser(genericUserDTO)).thenReturn(genericUserDTOs);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_USER_REPORT)
          .sessionAttr("userSearchRequest", genericUserDTO).param("downLoadPageNumber", "1")
          .param("downloadAllRecords", "true").param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testDownloadUserReportXLS", e);

    }
  }

  @Test
  public void testDownloadUserReportException() {
    try {
      Mockito.when(userService.searchAdminUser(Matchers.any(GenericUserDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_USER_REPORT)
          .sessionAttr("userSearchRequest", genericUserDTO).param("downLoadPageNumber", "1")
          .param("downloadAllRecords", "true").param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testDownloadUserReportException", e);

    }
  }

  @Test
  public void testEditUserInvalidFeature() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_USER_EDIT)
          .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_EDIT));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testEditUserInvalidFeature", e);

    }
  }

  @Test
  public void testEditUserAdmin() {
    UserData = new UserData();
    UserData.setRequestType(Constants.USERS_GROUP_ADMIN);
    merchantsMap = new HashMap<>();
    merchantsMap.put("address1", "address1");
    try {
      Mockito
          .when(userService.getUserDataOnUsersGroupType(Matchers.anyLong(), Matchers.anyString()))
          .thenReturn(UserData);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("requestType", "ADMIN")
              .sessionAttr("merchantList", merchantsMap))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_EDIT));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testEditUserAdmin", e);

    }
  }

  @Test
  public void testEditUserMerchant() {
    UserData = new UserData();
    UserData.setRequestType(Constants.USERS_GROUP_ADMIN);
    try {
      Mockito
          .when(userService.getUserDataOnUsersGroupType(Matchers.anyLong(), Matchers.anyString()))
          .thenReturn(UserData);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("requestType", "MERCHANT"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_EDIT));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testEditUserMerchant", e);

    }
  }

  @Test
  public void testEditUserException() {
    UserData = new UserData();
    UserData.setRequestType(Constants.USERS_GROUP_ADMIN);
    try {
      Mockito
          .when(userService.getUserDataOnUsersGroupType(Matchers.anyLong(), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("requestType", "MERCHANT"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_EDIT));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testEditUserException", e);

    }
  }

  @Test
  public void testViewUserInvalidFeature() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_USER_VIEW)
          .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_VIEW));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testViewUserInvalidFeature", e);

    }
  }

  @Test
  public void testViewUserAdmin() {
    UserData = new UserData();
    UserData.setRequestType(Constants.USERS_GROUP_ADMIN);
    merchantsMap = new HashMap<>();
    merchantsMap.put("address1", "address1");
    try {
      Mockito
          .when(userService.getUserDataOnUsersGroupType(Matchers.anyLong(), Matchers.anyString()))
          .thenReturn(UserData);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("requestType", "ADMIN")
              .sessionAttr("merchantList", merchantsMap))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_VIEW));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testViewUserAdmin", e);

    }
  }

  @Test
  public void testViewUserMerchant() {
    UserData = new UserData();
    UserData.setRequestType(Constants.USERS_GROUP_ADMIN);
    try {
      Mockito
          .when(userService.getUserDataOnUsersGroupType(Matchers.anyLong(), Matchers.anyString()))
          .thenReturn(UserData);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("requestType", "MERCHANT"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_VIEW));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testViewUserMerchant", e);

    }
  }

  @Test
  public void testViewUserException() {
    UserData = new UserData();
    UserData.setRequestType(Constants.USERS_GROUP_ADMIN);
    try {
      Mockito
          .when(userService.getUserDataOnUsersGroupType(Matchers.anyLong(), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("requestType", "MERCHANT"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_VIEW));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testViewUserException", e);

    }
  }

  @Test
  public void testUpdateUserInvalidFeature() {
    try {
      mockMvc.perform(
          post("/" + URLMappingConstants.UPDATE_USER).sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testUpdateUserInvalidFeature", e);

    }
  }

  @Test
  public void testUpdateUser() {
    try {
      mockMvc
          .perform(
              post("/" + URLMappingConstants.UPDATE_USER).sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testUpdateUser", e);

    }
  }

  @Test
  public void testUpdateUserException() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_USER)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").sessionAttr("loginUserId", "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_EDIT));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testUpdateUserException", e);

    }
  }

  @Test
  public void testValidateUserEmailId() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_USER_EMAIL_VALIDATE)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist").sessionAttr("loginUserId", "exist"));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testValidateUserEmailId", e);

    }
  }

  @Test
  public void testValidateUserEmailIdChatakAdminException() {
    try {
      Mockito.when(userService.validateEmailId(Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_USER_EMAIL_VALIDATE)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist").sessionAttr("loginUserId", "exist"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserManagementControllerTest :: testValidateUserEmailIdChatakAdminException",
          e);

    }
  }
}
