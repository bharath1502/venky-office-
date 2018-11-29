/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeatureResponse;
import com.chatak.acquirer.admin.service.FeatureService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.EditRoleResponse;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 4, 2018 5:33:52 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class RoleControllerTest {

  private static Logger logger = Logger.getLogger(RoleControllerTest.class);

  @InjectMocks
  RoleController controller = new RoleController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  private MockMvc mockMvc;

  @Mock
  BindingResult bindingResult;

  @Mock
  private List<Option> optionList;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  FeatureService featureService;

  @Mock
  RoleService roleService;

  @Mock
  UserService userService;

  @Mock
  UserRolesDTO userRolesDTO;

  @Mock
  private List<UserRolesDTO> roleList;

  @Mock
  private UserRolesDTO role;

  @Mock
  private List<Long> longListValues;

  @Mock
  private FeatureResponse featureResponse;

  @Mock
  private List<String> featuresList;

  @Mock
  private LoginResponse loginResponse;

  @Mock
  private List<FeatureDTO> featureDTOs;

  @Mock
  private FeatureDTO featureDTO;

  @Mock
  private EditRoleResponse editRoleResponse;

  @Mock
  private UserRoleDTO userRoleDTO;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testShowRoleSearch() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowRoleSearch", e);
    }
  }

  @Test
  public void testShowRoleSearchChatakAdminException() {
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowRoleSearchChatakAdminException", e);
    }
  }

  @Test
  public void testShowRoleSearchException() {
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowRoleSearchException", e);
    }
  }

  @Test
  public void testGetRolePagination() {
    role = new UserRolesDTO();
    role.setAgentAccountNumber(1l);
    role.setAgentAni("agentAni");
    roleList = new ArrayList<>();
    roleList.add(0, role);
    roleList.add(role);
    longListValues = new ArrayList<>();
    longListValues.add(0, 0l);
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class))).thenReturn(roleList);
      Mockito.when(userService.getAdminRoleList()).thenReturn(longListValues);
      Mockito.when(userService.getMerchantRoleList()).thenReturn(longListValues);
      mockMvc
          .perform(post("/" + URLMappingConstants.ROLE_PAGINATION_ACTION)
              .sessionAttr(Constants.SEARCH_ROLE_REQUEST, userRolesDTO).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testGetRolePagination", e);
    }
  }

  @Test
  public void testGetRolePaginationElseRoleNotExist() {
    role = new UserRolesDTO();
    role.setAgentAccountNumber(1l);
    role.setAgentAni("agentAni");
    role.setRoleId(0l);
    roleList = new ArrayList<>();
    roleList.add(0, role);
    roleList.add(role);
    longListValues = new ArrayList<>();
    longListValues.add(0, 0l);
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class))).thenReturn(roleList);
      Mockito.when(userService.getAdminRoleList()).thenReturn(longListValues);
      Mockito.when(userService.getMerchantRoleList()).thenReturn(longListValues);
      mockMvc
          .perform(post("/" + URLMappingConstants.ROLE_PAGINATION_ACTION)
              .sessionAttr(Constants.SEARCH_ROLE_REQUEST, userRolesDTO).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testGetRolePaginationElseRoleNotExist", e);
    }
  }

  @Test
  public void testGetRolePaginationElse() {
    role = new UserRolesDTO();
    role.setAgentAccountNumber(1l);
    role.setAgentAni("agentAni");
    roleList = new ArrayList<>();
    roleList.add(0, role);
    roleList.add(role);
    longListValues = new ArrayList<>();
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class))).thenReturn(roleList);
      Mockito.when(userService.getAdminRoleList()).thenReturn(null);
      Mockito.when(userService.getMerchantRoleList()).thenReturn(longListValues);
      mockMvc
          .perform(post("/" + URLMappingConstants.ROLE_PAGINATION_ACTION)
              .sessionAttr(Constants.SEARCH_ROLE_REQUEST, userRolesDTO).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testGetRolePagination", e);
    }
  }

  @Test
  public void testGetRolePaginationException() {
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.ROLE_PAGINATION_ACTION)
              .sessionAttr(Constants.SEARCH_ROLE_REQUEST, userRolesDTO).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testGetRolePaginationException", e);
    }
  }

  @Test
  public void testSearchRoleData() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH)
              .param("noOfRecords", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testSearchRoleData", e);
    }
  }

  @Test
  public void testSearchRoleDataChatakAdminException() {
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testSearchRoleDataChatakAdminException", e);
    }
  }

  @Test
  public void testSearchRoleDataException() {
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testSearchRoleDataException", e);
    }
  }

  @Test
  public void testDownloadRoleReportPDF() {
    userRolesDTO = new UserRolesDTO();
    roleList = new ArrayList<>();
    role.setAgentAccountNumber(1l);
    role.setAgentAni("agentAni");
    roleList = new ArrayList<>();
    roleList.add(0, role);
    roleList.add(role);
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class))).thenReturn(roleList);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_ROLE_REPORT)
          .sessionAttr(Constants.SEARCH_ROLE_REQUEST, userRolesDTO).param("downloadAllRecords", "true")
          .param("downloadType", "PDF"));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testDownloadRoleReportPDF", e);
    }
  }

  @Test
  public void testDownloadRoleReportXLS() {
    userRolesDTO = new UserRolesDTO();
    roleList = new ArrayList<>();
    role.setAgentAccountNumber(1l);
    role.setAgentAni("agentAni");
    roleList = new ArrayList<>();
    roleList.add(0, role);
    roleList.add(role);
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class))).thenReturn(roleList);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_ROLE_REPORT)
          .sessionAttr(Constants.SEARCH_ROLE_REQUEST, userRolesDTO).param("downloadAllRecords", "true")
          .param("downloadType", "XLS"));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testDownloadRoleReportXLS", e);
    }
  }

  @Test
  public void testDownloadRoleReportException() {
    try {
      Mockito.when(roleService.roleList(Matchers.any(UserRolesDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_ROLE_REPORT)
          .sessionAttr(Constants.SEARCH_ROLE_REQUEST, userRolesDTO).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testDownloadRoleReportException", e);
    }
  }

  @Test
  public void testDeleteRole() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_ROLE).sessionAttr(Constants.EXISTING_FEATURES,
              Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testDeleteRole", e);
    }
  }

  @Test
  public void testDeleteRoleChatakAdminException() {
    try {
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_ROLE).sessionAttr(Constants.EXISTING_FEATURES,
              Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testDeleteRoleChatakAdminException", e);
    }
  }

  @Test
  public void testDeleteRoleException() {
    try {
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_ROLE).sessionAttr(Constants.EXISTING_FEATURES,
              Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testDeleteRoleException", e);
    }
  }

  @Test
  public void testViewRole() {
    featureResponse = new FeatureResponse();
    featuresList.add(0, "0");
    featuresList.add(1, "1");
    featureResponse.setFeature(featuresList);
    featureResponse.setSubFeature(featuresList);
    try {
      Mockito.when(roleService.getFeature(Matchers.anyLong())).thenReturn(featureResponse);
      mockMvc.perform(post("/" + URLMappingConstants.VIEW_ROLE).sessionAttr(Constants.EXISTING_FEATURES,
          Constants.EXISTING_FEATURES)).andExpect(view().name(URLMappingConstants.ACCESS_ROLE_VIEW));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testViewRole", e);
    }
  }

  @Test
  public void testViewRoleException() {
    try {
      Mockito.when(roleService.getFeature(Matchers.anyLong())).thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.VIEW_ROLE).sessionAttr(Constants.EXISTING_FEATURES,
          Constants.EXISTING_FEATURES)).andExpect(view().name(URLMappingConstants.ACCESS_ROLE_VIEW));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testViewRoleException", e);
    }
  }

  @Test
  public void testShowCreateRole() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(Constants.ADMIN_USER_TYPE);
    featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(1l);
    featureDTOs = new ArrayList<>();
    featureDTOs.add(featureDTO);
    featureResponse = new FeatureResponse();
    featureResponse.setFeatureDTO(featureDTOs);
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenReturn(featureResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("1,2,3,4,5");
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowCreateRole", e);
    }
  }

  @Test
  public void testShowCreateRoleException() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType("CP_MERCHANT");
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowCreateRoleException", e);
    }
  }

  @Test
  public void testShowCreateRoleExceptionAdmin() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType("CP_SUPER_ADMIN");
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowCreateRoleExceptionAdmin", e);
    }
  }

  @Test
  public void testShowCreateRoleExceptionReseller() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType("CP_RESELLER");
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowCreateRoleExceptionReseller", e);
    }
  }

  @Test
  public void testShowCreateRoleExceptionTms() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType("CP_TMS");
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testShowCreateRoleExceptionTms", e);
    }
  }

  @Test
  public void testProcessCreateRoleSuccess() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(Constants.ADMIN_USER_TYPE);
    loginResponse.setUserId(1l);
    responseval = new Response();
    responseval.setErrorMessage("sucess");
    try {
      Mockito.when(roleService.createRole(Matchers.any(UserRoleDTO.class))).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CREATE_ROLE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testProcessCreateRoleSuccess", e);
    }
  }

  @Test
  public void testProcessCreateRoleFail() {
    String[] strArray = {"1"};
    loginResponse = new LoginResponse();
    loginResponse.setUserType(Constants.ADMIN_USER_TYPE);
    loginResponse.setUserId(1l);
    responseval = new Response();
    responseval.setErrorMessage("failed");
    featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(1l);
    featureDTO.setFeatureId(1l);
    featureDTOs = new ArrayList<>();
    featureDTOs.add(featureDTO);
    featureResponse = new FeatureResponse();
    featureResponse.setFeatureDTO(featureDTOs);
    try {
      Mockito.when(roleService.createRole(Matchers.any(UserRoleDTO.class))).thenReturn(responseval);
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenReturn(featureResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("1,2,3,4,5");
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CREATE_ROLE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse)
              .param("adminRelatedFeatureIds", "1,2,3,4,5").param("permissionData", "1")
              .param("feature", strArray))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testProcessCreateRoleFail", e);
    }
  }

  @Test
  public void testGetRoleDetails() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(Constants.ADMIN_USER_TYPE);
    loginResponse.setUserRoleId(1l);
    loginResponse.setUserId(1l);
    editRoleResponse = new EditRoleResponse();
    userRoleDTO = new UserRoleDTO();
    userRoleDTO.setAgentAccountNumber(1l);
    userRoleDTO.setRoleId(1l);
    editRoleResponse.setRoleRequest(userRoleDTO);
    longListValues = new ArrayList<>();
    editRoleResponse.setExistingFeature(longListValues);
    featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(1l);
    featureDTO.setFeatureId(1l);
    featureDTOs = new ArrayList<>();
    featureDTOs.add(featureDTO);
    featureResponse = new FeatureResponse();
    featureResponse.setFeatureDTO(featureDTOs);
    try {
      Mockito.when(roleService.getRoleDetails(Matchers.anyLong())).thenReturn(editRoleResponse);
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenReturn(featureResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_ROLE_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("existingFeature", "1")
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse).param("featureId", "1l"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_ROLE_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testProcessCreateRoleFail", e);
    }
  }

  @Test
  public void testGetRoleDetailsException() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(Constants.ADMIN_USER_TYPE);
    loginResponse.setUserRoleId(Long.parseLong("2"));
    loginResponse.setUserId(1l);
    editRoleResponse = new EditRoleResponse();
    userRoleDTO = new UserRoleDTO();
    userRoleDTO.setAgentAccountNumber(1l);
    userRoleDTO.setRoleId(1l);
    editRoleResponse.setRoleRequest(userRoleDTO);
    longListValues = new ArrayList<>();
    editRoleResponse.setExistingFeature(longListValues);
    try {
      Mockito.when(roleService.getRoleDetails(Matchers.anyLong())).thenReturn(editRoleResponse);
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_ROLE_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse).param("featureId", "1l"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_ROLE_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testGetRoleDetailsException", e);
    }
  }

  @Test
  public void testGetRoleView() {
    editRoleResponse = new EditRoleResponse();
    userRoleDTO = new UserRoleDTO();
    userRoleDTO.setAgentAccountNumber(1l);
    userRoleDTO.setRoleId(1l);
    longListValues = new ArrayList<>();
    editRoleResponse.setExistingFeature(longListValues);
    editRoleResponse.setRoleRequest(userRoleDTO);
    loginResponse = new LoginResponse();
    loginResponse.setUserRoleId(Long.parseLong("1"));
    featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(1l);
    featureDTO.setFeatureId(1l);
    featureDTOs = new ArrayList<>();
    featureDTOs.add(featureDTO);
    featureResponse = new FeatureResponse();
    featureResponse.setFeatureDTO(featureDTOs);
    try {
      Mockito.when(roleService.getRoleDetails(Matchers.anyLong())).thenReturn(editRoleResponse);
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenReturn(featureResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_ROLE_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_ROLE_VIEW));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testGetRoleView", e);
    }
  }

  @Test
  public void testGetRoleViewException() {
    editRoleResponse = new EditRoleResponse();
    userRoleDTO = new UserRoleDTO();
    userRoleDTO.setAgentAccountNumber(1l);
    userRoleDTO.setRoleId(1l);
    longListValues = new ArrayList<>();
    editRoleResponse.setExistingFeature(longListValues);
    editRoleResponse.setRoleRequest(userRoleDTO);
    loginResponse = new LoginResponse();
    loginResponse.setUserRoleId(Long.parseLong("2"));
    featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(1l);
    featureDTO.setFeatureId(1l);
    featureDTOs = new ArrayList<>();
    featureDTOs.add(featureDTO);
    featureResponse = new FeatureResponse();
    featureResponse.setFeatureDTO(featureDTOs);
    try {
      Mockito.when(roleService.getRoleDetails(Matchers.anyLong())).thenReturn(editRoleResponse);
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_ROLE_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_ROLE_VIEW));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testGetRoleViewException", e);
    }
  }

  @Test
  public void testUpdateRole() {
    loginResponse = new LoginResponse();
    loginResponse.setUserId(1l);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_ROLE_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_SEARCH));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testUpdateRole", e);
    }
  }

  @Test
  public void testUpdateRoleException() {
    loginResponse = new LoginResponse();
    loginResponse.setUserId(1l);
    try {
      Mockito.when(roleService.updateRoles(Matchers.any(UserRoleDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_ROLE_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_ROLE_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: RoleControllerTest :: testUpdateRoleException", e);
    }
  }
}
