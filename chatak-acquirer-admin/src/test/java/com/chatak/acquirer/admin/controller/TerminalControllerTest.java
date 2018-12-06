/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.controller.model.TerminalData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TerminalSearchResponse;
import com.chatak.acquirer.admin.model.Terminals;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.TerminalService;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.Terminal;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 29, 2017 4:43:37 PM
 * @version 1.0
 */
public class TerminalControllerTest {

  private static Logger logger = Logger.getLogger(TerminalControllerTest.class);

  @InjectMocks
  private TerminalController controller = new TerminalController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  BindingResult bindingResult;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  ChatakAdminException chatakAdminException;

  @Mock
  private List<Option> optionList;

  @Mock
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private TerminalService terminalService;

  @Mock
  private AddTerminalResponse addTerminalResponse;

  @Mock
  private TerminalSearchResponse terminalSearchResponse;

  @Mock
  private List<Terminals> terminalList;

  @Mock
  private TerminalData terminalData;

  @Mock
  private Terminal terminal;

  @Mock
  private UpdateTerminalResponse updateTerminalResponse;

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
  public void testShowCreateTerminalPage() {
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testShowCreateTerminalPage", e);
    }
  }

  @Test
  public void testShowCreateTerminalPageException() {
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenThrow(chatakAdminException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testShowCreateTerminalPageException", e);
    }
  }

  @Test
  public void testcreateTerminal() {
    addTerminalResponse = new AddTerminalResponse();
    addTerminalResponse.setErrorCode(Constants.STATUS_CODE_SUCCESS);
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.addTerminal(Matchers.any(AddTerminalRequest.class)))
          .thenReturn(addTerminalResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testcreateTerminal", e);
    }
  }

  @Test
  public void testcreateTerminalElse() {
    addTerminalResponse = new AddTerminalResponse();
    addTerminalResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.addTerminal(Matchers.any(AddTerminalRequest.class)))
          .thenReturn(addTerminalResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testcreateTerminalElse", e);
    }
  }

  @Test
  public void testcreateTerminalNullResponse() {
    addTerminalResponse = new AddTerminalResponse();
    addTerminalResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.addTerminal(Matchers.any(AddTerminalRequest.class)))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testcreateTerminalNullResponse", e);
    }
  }

  @Test
  public void testcreateTerminalException() {
    addTerminalResponse = new AddTerminalResponse();
    addTerminalResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.addTerminal(Matchers.any(AddTerminalRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testcreateTerminalException", e);
    }
  }

  @Test
  public void testShowSearchTerminalPage() {
    terminalSearchResponse = new TerminalSearchResponse();
    terminalSearchResponse.setTerminalList(terminalList);
    terminalSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.searchTerminal(Matchers.any(TerminalData.class)))
          .thenReturn(terminalSearchResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testShowSearchTerminalPage", e);
    }
  }

  @Test
  public void testShowSearchTerminalPageException() {
    terminalSearchResponse = new TerminalSearchResponse();
    terminalSearchResponse.setTerminalList(terminalList);
    terminalSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.searchTerminal(Matchers.any(TerminalData.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testShowSearchTerminalPageException", e);
    }
  }

  @Test
  public void testSearchTerminal() {
    terminalSearchResponse = new TerminalSearchResponse();
    terminalSearchResponse.setTerminalList(terminalList);
    terminalSearchResponse.setTotalNoOfRows(Integer.parseInt("5"));
    try {
      Mockito.when(terminalService.searchTerminal(Matchers.any(TerminalData.class)))
          .thenReturn(terminalSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("merchantOptions", "merchantOptions"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testSearchTerminal", e);
    }
  }

  @Test
  public void testSearchTerminalException() {
    try {
      Mockito.when(terminalService.searchTerminal(Matchers.any(TerminalData.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("merchantOptions", "merchantOptions"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testSearchTerminalException", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    terminalSearchResponse = new TerminalSearchResponse();
    terminalSearchResponse.setTerminalList(terminalList);
    terminalSearchResponse.setTotalNoOfRows(Integer.parseInt("5"));
    try {
      Mockito.when(terminalService.searchTerminal(Matchers.any(TerminalData.class)))
          .thenReturn(terminalSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TERMINALS_PAGINATION)
              .sessionAttr("terminals", terminalData).param("pageNumber", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testSearchTerminalException", e);
    }
  }

  @Test
  public void testGetPaginationListNullResponse() {
    terminalSearchResponse = new TerminalSearchResponse();
    terminalSearchResponse.setTerminalList(terminalList);
    terminalSearchResponse.setTotalNoOfRows(Integer.parseInt("5"));
    try {
      Mockito.when(terminalService.searchTerminal(Matchers.any(TerminalData.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TERMINALS_PAGINATION)
              .sessionAttr("terminals", terminalData).param("pageNumber", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testGetPaginationListNullResponse", e);
    }
  }

  @Test
  public void testGetPaginationListException() {
    terminalSearchResponse = new TerminalSearchResponse();
    terminalSearchResponse.setTerminalList(terminalList);
    terminalSearchResponse.setTotalNoOfRows(Integer.parseInt("5"));
    try {
      Mockito.when(terminalService.searchTerminal(Matchers.any(TerminalData.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TERMINALS_PAGINATION)
              .sessionAttr("terminals", "terminalData"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testGetPaginationListNullResponse", e);
    }
  }

  @Test
  public void testShowEditTerminalPage() {
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.getTerminal(Matchers.anyString())).thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_EDIT_TERMINAL)
              .sessionAttr("terminals", "terminalData"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testShowEditTerminalPage", e);
    }
  }

  @Test
  public void testShowEditTerminalPageTerminalNotNull() {
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.getTerminal(Matchers.anyString())).thenReturn(terminal);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_EDIT_TERMINAL)
              .sessionAttr("terminals", "terminalData"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testShowEditTerminalPageTerminalNotNull", e);
    }
  }

  @Test
  public void testShowEditTerminalPageException() {
    try {
      Mockito.when(merchantUpdateService.getActiveMerchants()).thenReturn(optionList);
      Mockito.when(terminalService.getTerminal(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_EDIT_TERMINAL)
              .sessionAttr("terminals", "terminalData"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testShowEditTerminalPageException", e);
    }
  }

  @Test
  public void testUpdateTerminal() {
    updateTerminalResponse = new UpdateTerminalResponse();
    updateTerminalResponse.setUpdated(true);
    updateTerminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(terminalService.updateTerminal(Matchers.any(UpdateTerminalRequest.class)))
          .thenReturn(updateTerminalResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_TERMINAL)
              .sessionAttr("terminals", "terminalData"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testUpdateTerminal", e);
    }
  }

  @Test
  public void testUpdateTerminalElse() {
    updateTerminalResponse = new UpdateTerminalResponse();
    updateTerminalResponse.setUpdated(false);
    try {
      Mockito.when(terminalService.updateTerminal(Matchers.any(UpdateTerminalRequest.class)))
          .thenReturn(updateTerminalResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_TERMINAL)
              .sessionAttr("terminals", "terminalData"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testUpdateTerminalElse", e);
    }
  }

  @Test
  public void testUpdateTerminalException() {
    updateTerminalResponse = new UpdateTerminalResponse();
    updateTerminalResponse.setUpdated(false);
    try {
      Mockito.when(terminalService.updateTerminal(Matchers.any(UpdateTerminalRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_TERMINAL)
              .sessionAttr("terminals", "terminalData"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TERMINAL_PAGE));
    } catch (Exception e) {
      logger.error("Error :: TerminalControllerTest :: testUpdateTerminalException", e);
    }
  }

}
