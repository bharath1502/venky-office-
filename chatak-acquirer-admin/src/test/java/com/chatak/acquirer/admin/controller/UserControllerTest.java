/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserData;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 2, 2018 3:39:14 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest extends UserManagementControllerTest {

  private static Logger logger = Logger.getLogger(UserControllerTest.class);

  @Mock
  private Response response;

  @Test
  public void testDeleteUserNoExistingFeature() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_MERCHANT_USER)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"));
    } catch (Exception e) {
      logger.error("ERROR :: UserControllerTest :: testDeleteUserNoExistingFeature", e);

    }
  }

  @Test
  public void testDeleteUser() {
    UserData = new UserData();
    UserData.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(userService.deleteMerchantUser(Matchers.anyLong(), Matchers.anyString()))
          .thenReturn(UserData);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_MERCHANT_USER)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserControllerTest :: testDeleteUser", e);

    }
  }

  @Test
  public void testDeleteUserChatakAdminException() {
    try {
      Mockito.when(userService.deleteMerchantUser(Matchers.anyLong(), Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_MERCHANT_USER)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserControllerTest :: testDeleteUserChatakAdminException", e);

    }
  }

  @Test
  public void testDeleteUserException() {
    try {
      Mockito.when(userService.deleteMerchantUser(Matchers.anyLong(), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_MERCHANT_USER)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserControllerTest :: testDeleteUserException", e);

    }
  }

  @Test
  public void testShowUserTypeValue() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_TYPE_VALUE)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserControllerTest :: testShowUserTypeValue", e);

    }
  }

  @Test
  public void testShowUserTypeValueException() {
    try {
      Mockito.when(roleService.getRoleListByType(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_TYPE_VALUE)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_CREATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserControllerTest :: testShowUserTypeValueException", e);

    }
  }


  @Test
  public void testSearchUserElse() {
    genericUserDTO = new GenericUserDTO();
    genericUserDTO.setAddress1("address1");
    genericUserDTOs = new ArrayList<>();
    genericUserDTOs.add(genericUserDTO);
    try {
      Mockito.when(userService.searchMerchantUser(Matchers.any(GenericUserDTO.class)))
          .thenReturn(genericUserDTOs);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_USER_SEARCH).sessionAttr("adminId", 1l)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("pageSize", "1")
              .sessionAttr("sortedList", genericUserDTOs).param("noOfRecords", "1")
              .param("userType", "").param("noOfRecords", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testSearchUserElse", e);

    }
  }

  @Test
  public void testChangeUserStatusNoExistingFeatures() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.USER_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserManagementControllerTest :: testChangeUserStatusNoExistingFeatures", e);

    }
  }

  @Test
  public void testChangeUserStatusSuccess() {
    response = new Response();
    response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(userService.changeUserStatus(Matchers.any(UserData.class), Matchers.anyString()))
          .thenReturn(response);
      mockMvc
          .perform(post("/" + URLMappingConstants.USER_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").sessionAttr(Constants.PAGE_NUMBER, 1)
              .sessionAttr("userSearchRequest", genericUserDTO))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testChangeUserStatusSuccess", e);

    }
  }

  @Test
  public void testChangeUserStatusFail() {
    response = new Response();
    response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(userService.changeUserStatus(Matchers.any(UserData.class), Matchers.anyString()))
          .thenReturn(response);
      mockMvc
          .perform(post("/" + URLMappingConstants.USER_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").sessionAttr(Constants.PAGE_NUMBER, 1)
              .sessionAttr("userSearchRequest", genericUserDTO))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testChangeUserStatusFail", e);

    }
  }

  @Test
  public void testChangeUserStatusException() {
    try {
      Mockito.when(userService.changeUserStatus(Matchers.any(UserData.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.USER_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").sessionAttr(Constants.PAGE_NUMBER, 1)
              .sessionAttr("userSearchRequest", genericUserDTO))
          .andExpect(view().name(URLMappingConstants.CHATAK_USER_SEARCH));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testChangeUserStatusException", e);

    }
  }

  @Test
  public void testValidateUserName() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_USERNAME_VALIDATE));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testValidateUserName", e);

    }
  }

  @Test
  public void testValidateUserNameChatakAdminException() {
    try {
      Mockito.when(userService.validateUserName(Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_USERNAME_VALIDATE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserManagementControllerTest :: testValidateUserNameChatakAdminException", e);
    }
  }

  @Test
  public void testValidateMerchantIDByName() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANTCODE_BY_NAME)
          .param("merchantId", "merchantId"));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testValidateUserNameException", e);
    }
  }

  @Test
  public void testValidateMerchantIDByNameException() {
    try {
      Mockito.when(userService.merchantIdByMerchantName(Matchers.anyString(), Matchers.anyLong(), Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANTCODE_BY_NAME)
          .param("merchantId", "merchantId"));
    } catch (Exception e) {
      logger.error("ERROR :: UserManagementControllerTest :: testValidateMerchantIDByNameException",
          e);
    }
  }
}
