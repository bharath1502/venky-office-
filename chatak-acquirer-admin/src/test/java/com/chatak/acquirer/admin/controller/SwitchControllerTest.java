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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.service.SwitchService;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.Switch;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 29, 2017 9:44:06 AM
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SwitchControllerTest {

  private static Logger logger = Logger.getLogger(SwitchControllerTest.class);

  @InjectMocks
  SwitchController controller = new SwitchController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  BindingResult bindingResult;

  private MockMvc mockMvc;

  private Locale locale;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private LoginDetails loginDetails;

  @Mock
  private List<Option> optionList;

  @Mock
  private SwitchService switchService;

  @Mock
  SwitchResponse switchResponse;

  @Mock
  private SwitchRequest switchRequest;

  @Mock
  private List<SwitchRequest> switchRequests;

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
  public void testShowCreateSwitchPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testShowCreateSwitchPage", e);
    }
  }

  @Test
  public void testCreateSwitch() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito
          .when(
              switchService.addSwitchInformation(Matchers.any(Switch.class), Matchers.anyString()))
          .thenReturn(switchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE)
              .sessionAttr("loginUserId", 1l).sessionAttr("loginUserType", "loginUserType")
              .header("user-agent", "user-agent")
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testCreateSwitch", e);
    }
  }

  @Test
  public void testCreateSwitchElseIf() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z11);
    try {
      Mockito
          .when(
              switchService.addSwitchInformation(Matchers.any(Switch.class), Matchers.anyString()))
          .thenReturn(switchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE)
              .sessionAttr("loginUserId", 1l).sessionAttr("loginUserType", "loginUserType")
              .header("user-agent", "user-agent")
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testCreateSwitchElseIf", e);
    }
  }

  @Test
  public void testCreateSwitchElse() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
    try {
      Mockito
          .when(
              switchService.addSwitchInformation(Matchers.any(Switch.class), Matchers.anyString()))
          .thenReturn(switchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE)
              .sessionAttr("loginUserId", 1l).sessionAttr("loginUserType", "loginUserType")
              .header("user-agent", "user-agent")
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testCreateSwitchElse", e);
    }
  }

  @Test
  public void testCreateSwitchException() {
    switchResponse = new SwitchResponse();
    LocaleContextHolder.setLocale(locale);
    Mockito.when(messageSource.getMessage(TestConstants.CHATAK_GENERAL_ERROR, null,
        LocaleContextHolder.getLocale())).thenReturn("abc");
    try {
      Mockito
          .when(
              switchService.addSwitchInformation(Matchers.any(Switch.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE)
              .sessionAttr("loginUserId", 1l).sessionAttr("loginUserType", "loginUserType")
              .header("user-agent", "user-agent")
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SWITCH_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testCreateSwitchException", e);
    }
  }

  @Test
  public void testShowSearchSwitchPage() {
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenReturn(switchResponse);
      Mockito.when(request.getHeader(Matchers.anyString())).thenReturn("referer");
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testShowSearchSwitchPage", e);
    }
  }

  @Test
  public void testShowSearchSwitchPageException() {
    switchResponse = new SwitchResponse();
    LocaleContextHolder.setLocale(locale);
    Mockito.when(messageSource.getMessage(TestConstants.CHATAK_GENERAL_ERROR, null,
        LocaleContextHolder.getLocale())).thenReturn("abc");
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testShowSearchSwitchPageException", e);
    }
  }

  @Test
  public void testSearchSwitchInfo() {
    switchResponse = new SwitchResponse();
    switchResponse.setSwitchRequest(switchRequests);
    switchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenReturn(switchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testSearchSwitchInfo", e);
    }
  }

  @Test
  public void testSearchSwitchInfoElse() {
    switchResponse = new SwitchResponse();
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testSearchSwitchInfoElse", e);
    }
  }

  @Test
  public void testSearchSwitchInfoException() {
    switchResponse = new SwitchResponse();
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testSearchSwitchInfoException", e);
    }
  }

  @Test
  public void testShowEditSwitch() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_SWITCH));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testShowEditSwitch", e);
    }
  }

  @Test
  public void testShowEditSwitchException() {
    Mockito.when(switchService.getSwtichInformationById(Matchers.anyLong()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_SWITCH));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testShowEditSwitchException", e);
    }
  }

  @Test
  public void testUpdateSwitchElse() {
    SwitchResponse updateSwitchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(switchService.updateSwitchInformation(switchRequest, "loginUserId"))
        .thenReturn(updateSwitchResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginUserId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testUpdateSwitchElse", e);
    }
  }

  @Test
  public void testUpdateSwitch() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(switchService.updateSwitchInformation(Matchers.any(SwitchRequest.class),
        Matchers.anyString())).thenReturn(switchResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginUserId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testUpdateSwitch", e);
    }
  }

  @Test
  public void testUpdateSwitchException() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(switchService.updateSwitchInformation(Matchers.any(SwitchRequest.class),
        Matchers.anyString())).thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_SWITCH)
              .sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginUserId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testUpdateSwitchException", e);
    }
  }

  @Test
  public void testShowViewSwitch() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIEW_SWITCH_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginUserId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIEW_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testShowViewSwitch", e);
    }
  }

  @Test
  public void testShowViewSwitchException() {
    Mockito.when(switchService.getSwtichInformationById(Matchers.anyLong()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIEW_SWITCH_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIEW_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testShowViewSwitchException", e);
    }
  }

  @Test
  public void testGetPaginationListElse() {
    switchRequest.setPageIndex(Integer.parseInt("1"));
    switchRequest.setNoOfRecords(Integer.parseInt("1"));
    switchResponse.setSwitchRequest(switchRequests);
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenReturn(switchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_PAGINATION)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("searchSwitchRequestList", switchRequest))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testGetPaginationListElse", e);
    }
  }

  @Test
  public void testGetPaginationListExeception() {
    switchRequest.setPageIndex(Integer.parseInt("1"));
    switchRequest.setNoOfRecords(Integer.parseInt("1"));
    switchResponse.setSwitchRequest(switchRequests);
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_PAGINATION)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("searchSwitchRequestList", switchRequest))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testGetPaginationListExeception", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    switchResponse = new SwitchResponse();
    switchRequest.setPageIndex(Integer.parseInt("1"));
    switchRequest.setNoOfRecords(Integer.parseInt("1"));
    switchResponse.setSwitchRequest(switchRequests);
    switchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenReturn(switchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_PAGINATION)
              .sessionAttr("existingFeatures", "existingFeatures")
              .param("pageNumber", TestConstants.TWO.toString())
              .sessionAttr("searchSwitchRequestList", switchRequest))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testGetPaginationList", e);
    }
  }

  @Test
  public void testDownloadSwitchReportPDF() {
    switchResponse = new SwitchResponse();
    switchResponse.setSwitchRequest(switchRequests);
    LocaleContextHolder.setLocale(locale);
    Mockito.when(messageSource.getMessage("chatak.header.switch.messages", null,
        LocaleContextHolder.getLocale())).thenReturn("test");
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenReturn(switchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_REPORT)
          .sessionAttr("existingFeatures", "existingFeatures")
          .param("pageNumber", TestConstants.TWO.toString()).param("downloadType", "PDF")
          .param("downloadAllRecords", "true")
          .sessionAttr("searchSwitchRequestList", switchRequest));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testDownloadSwitchReportPDF", e);
    }
  }

  @Test
  public void testDownloadSwitchReportExcel() {
    switchResponse = new SwitchResponse();
    switchResponse.setSwitchRequest(switchRequests);
    LocaleContextHolder.setLocale(locale);
    Mockito.when(messageSource.getMessage("chatak.header.switch.messages", null,
        LocaleContextHolder.getLocale())).thenReturn("test");
    try {
      Mockito.when(switchService.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
          .thenReturn(switchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SWITCH_REPORT)
          .sessionAttr("existingFeatures", "existingFeatures")
          .param("pageNumber", TestConstants.TWO.toString()).param("downloadType", "XLS")
          .param("downloadAllRecords", "true")
          .sessionAttr("searchSwitchRequestList", switchRequest));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testDownloadSwitchReportExcel", e);
    }
  }

  @Test
  public void testchangeSwitchStatus() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(
        switchService.changeSwitchStatus(Matchers.any(SwitchRequest.class), Matchers.anyString()))
        .thenReturn(switchResponse);

    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.SWITCH_ACTIVATION_SUSPENTION)
              .sessionAttr("existingFeatures", "existingFeatures").param("switchStatus", "Active"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testchangeSwitchStatus", e);
    }
  }

  @Test
  public void testchangeSwitchStatusElse() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_18);
    Mockito.when(
        switchService.changeSwitchStatus(Matchers.any(SwitchRequest.class), Matchers.anyString()))
        .thenReturn(switchResponse);

    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.SWITCH_ACTIVATION_SUSPENTION)
              .sessionAttr("existingFeatures", "existingFeatures").param("switchStatus", "Active"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testchangeSwitchStatusElse", e);
    }
  }

  @Test
  public void testchangeSwitchStatusException() {
    switchResponse = new SwitchResponse();
    switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_18);
    Mockito.when(
        switchService.changeSwitchStatus(Matchers.any(SwitchRequest.class), Matchers.anyString()))
        .thenThrow(nullPointerException);

    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.SWITCH_ACTIVATION_SUSPENTION)
              .sessionAttr("existingFeatures", "existingFeatures").param("switchStatus", "Active"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SWITCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SwitchControllerTest :: testchangeSwitchStatusException", e);
    }
  }
}
