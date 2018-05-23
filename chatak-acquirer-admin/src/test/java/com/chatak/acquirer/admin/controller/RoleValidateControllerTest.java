package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeatureResponse;
import com.chatak.acquirer.admin.service.FeatureService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.util.Constants;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class RoleValidateControllerTest {
  
  private static Logger logger = LoggerFactory.getLogger(RoleValidateControllerTest.class);
  
  @InjectMocks
  RoleValidateController roleValidateController;
  
  @Mock
  FeatureService featureService;
  
  @Mock
  RoleService roleService;
  
  @Mock
  UserService userService;
  
  @Mock
  RoleController roleController;
  
  @Mock
  MessageSource messageSource;
  
  private MockMvc mockMvc;
  
  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(roleValidateController)
        .setViewResolvers(viewResolver).build();
  }

  @Test
  public void testValidateuniqueRoleName() {
    try {
      mockMvc
      .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_ROLENAME_VALIDATE));
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testValidateuniqueRoleName method", e);
    }
  }
  
  @Test
  public void testValidateuniqueRoleNameException() {
    try {
      Mockito.when(roleService.validateRolename(Matchers.anyString())).thenThrow(new NullPointerException());
      mockMvc
      .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_ROLENAME_VALIDATE));
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testValidateuniqueRoleNameException method", e);
    }
  }
  
  @Test
  public void testChangeRoleStatusIf() {
    Response response = new Response();
    response.setErrorCode("sucess");
    try {
      Mockito.when(roleService.changeRoleStatus(Matchers.any(UserRoleDTO.class))).thenReturn(response);
      mockMvc
      .perform(post("/" + URLMappingConstants.ROLE_ACTIVATION)
          .sessionAttr("loginRoleId", "123456").sessionAttr(Constants.PAGE_NUMBER, Integer.parseInt("1")));
      
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testChangeRoleStatusIf method", e);
    }
  } 
  
  @Test
  public void testChangeRoleStatusElse() {
    Response response = new Response();
    response.setErrorCode("failure");
    try {
      Mockito.when(roleService.changeRoleStatus(Matchers.any(UserRoleDTO.class))).thenReturn(response);
      mockMvc
      .perform(post("/" + URLMappingConstants.ROLE_ACTIVATION)
          .sessionAttr("loginRoleId", "123456").sessionAttr(Constants.PAGE_NUMBER, Integer.parseInt("1")));
      
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testChangeRoleStatusElse method", e);
    }
  } 
  
  @Test
  public void testChangeRoleStatusException() {
    try {
      Mockito.when(roleService.changeRoleStatus(Matchers.any(UserRoleDTO.class))).thenThrow(ChatakAdminException.class);
      mockMvc
      .perform(post("/" + URLMappingConstants.ROLE_ACTIVATION)
          .sessionAttr("loginRoleId", "123456").sessionAttr(Constants.PAGE_NUMBER, Integer.parseInt("1")));
      
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testChangeRoleStatusException method", e);
    }
  } 
  
  @Test
  public void testRoleNameByTypeAdmin() {
    FeatureResponse featureResponse = new FeatureResponse();
    List<FeatureDTO> featureDTOList = new ArrayList<>();
    FeatureDTO featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(Long.parseLong("1234569870"));
    featureDTOList.add(featureDTO);
    featureResponse.setFeatureDTO(featureDTOList);
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class))).thenReturn(featureResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("123456987");
      mockMvc
      .perform(post("/" + URLMappingConstants.ADMIN_ROLE_NAME)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist")
          .param("rolesType", "Admin"))
      .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testRoleNameByType method", e);
    }
  }
  
  @Test
  public void testRoleNameByTypeMerchant() {
    FeatureResponse featureResponse = new FeatureResponse();
    List<FeatureDTO> featureDTOList = new ArrayList<>();
    FeatureDTO featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(Long.parseLong("1234569870"));
    featureDTOList.add(featureDTO);
    featureResponse.setFeatureDTO(featureDTOList);
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class))).thenReturn(featureResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("123456987");
      mockMvc
      .perform(post("/" + URLMappingConstants.ADMIN_ROLE_NAME)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .param("rolesType", "Merchant"))
      .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testRoleNameByType method", e);
    }
  }
  
  @Test
  public void testRoleNameByTypeReseller() {
    FeatureResponse featureResponse = new FeatureResponse();
    List<FeatureDTO> featureDTOList = new ArrayList<>();
    FeatureDTO featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(Long.parseLong("1234569870"));
    featureDTOList.add(featureDTO);
    featureResponse.setFeatureDTO(featureDTOList);
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class))).thenReturn(featureResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("123456987");
      mockMvc
      .perform(post("/" + URLMappingConstants.ADMIN_ROLE_NAME)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .param("rolesType", "Reseller"))
      .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testRoleNameByType method", e);
    }
  }
  
  @Test
  public void testRoleNameByTypeTms() {
    FeatureResponse featureResponse = new FeatureResponse();
    List<FeatureDTO> featureDTOList = new ArrayList<>();
    FeatureDTO featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(Long.parseLong("1234569870"));
    featureDTOList.add(featureDTO);
    featureResponse.setFeatureDTO(featureDTOList);
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class))).thenReturn(featureResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("123456987");
      mockMvc
      .perform(post("/" + URLMappingConstants.ADMIN_ROLE_NAME)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .param("rolesType", "Tms"))
      .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testRoleNameByType method", e);
    }
  }
  
  @Test
  public void testRoleNameByTypeException() {
    FeatureResponse featureResponse = new FeatureResponse();
    List<FeatureDTO> featureDTOList = new ArrayList<>();
    FeatureDTO featureDTO = new FeatureDTO();
    featureDTO.setAgentAccountNumber(Long.parseLong("1234569870"));
    featureDTOList.add(featureDTO);
    featureResponse.setFeatureDTO(featureDTOList);
    try {
      Mockito.when(roleService.getAdminFeatureForEntityType(Matchers.any(FeatureDTO.class))).thenReturn(featureResponse);
      mockMvc
      .perform(post("/" + URLMappingConstants.ADMIN_ROLE_NAME)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .param("rolesType", "Tms"))
      .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_ACCESS_ROLE_CREATE));
    } catch (Exception e) {
      logger.error("ERROR:: RoleValidateControllerTest:: testRoleNameByType method", e);
    }
  }
}
