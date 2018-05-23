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
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CAPublicKeysResponse;
import com.chatak.acquirer.admin.model.Response;
import com.chatak.acquirer.admin.service.CAPublicKeysService;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.bean.PublickeyNameResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.CAPublicKeysDTO;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class CAPublicKeysControllerTest {

  private static Logger logger = Logger.getLogger(CAPublicKeysControllerTest.class);

  @InjectMocks
  CAPublicKeysController cAPublicKeysController = new CAPublicKeysController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  BindingResult bindingResult;

  @Mock
  private List<Option> optionList;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private ChatakAdminException chatakAdminException;

  @Mock
  private CAPublicKeysService caPublicKeysService;

  @Mock
  private CAPublicKeysResponse caPublicKeysResponse;

  @Mock
  private CAPublicKeysDTO caPublicKeysDTO;

  @Mock
  private List<CAPublicKeysDTO> searchList;

  @Mock
  private PGCaPublicKeys pgcapublickeys;

  @Mock
  private PublickeyNameResponse publickeyNameResponse;

  @Mock
  private LoginDetails loginDetails;

  @Mock
  private LoginResponse loginResponse;


  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(cAPublicKeysController).setViewResolvers(viewResolver)
        .build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testShowCAPublicKeysSearchPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_CA_PUBLIC_KEYS_SEARCH_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testShowCAPublicKeysSearchPage | Exception ", e);
    }
  }

  @Test
  public void testShowCAPublicKeysSearchPageException() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_CA_PUBLIC_KEYS_SEARCH_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, "sgf"))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "CAPublicKeysControllerTest | testShowCAPublicKeysSearchPageException | Exception ", e);
    }
  }

  @Test
  public void testCaPublicKeysSearch() {
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCaPublicKeysSearch | Exception ", e);
    }
  }

  @Test
  public void testCaPublicKeysSearchChatakAdminException() {
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc.perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_SEARCH)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
    } catch (Exception e) {
      logger.error(
          "CAPublicKeysControllerTest | testCaPublicKeysSearchChatakAdminException | Exception ",
          e);
    }
  }

  @Test
  public void testCaPublicKeysSearchException() {
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_SEARCH)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCaPublicKeysSearchException | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    caPublicKeysResponse = new CAPublicKeysResponse();
    caPublicKeysResponse.setCaPublicKeysList(searchList);
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_PAGINATION)
              .sessionAttr(Constants.PUBLIC_KEY_REQUEST, caPublicKeysDTO)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testGetPaginationList | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationListException() {
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_PAGINATION)
              .sessionAttr(Constants.PUBLIC_KEY_REQUEST, caPublicKeysDTO)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testGetPaginationListException | Exception ", e);
    }
  }

  @Test
  public void testDownloadCaPublicKeysReport() {
    caPublicKeysResponse = new CAPublicKeysResponse();
    caPublicKeysResponse.setCaPublicKeysList(searchList);
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_CA_PUBLIC_KEYS_REPORT)
          .sessionAttr(Constants.PUBLIC_KEY_REQUEST, caPublicKeysDTO)
          .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testDownloadCaPublicKeysReport | Exception ", e);
    }
  }

  @Test
  public void testDownloadCaPublicKeysReportPDF() {
    caPublicKeysResponse = new CAPublicKeysResponse();
    caPublicKeysResponse.setCaPublicKeysList(searchList);
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_CA_PUBLIC_KEYS_REPORT)
          .sessionAttr(Constants.PUBLIC_KEY_REQUEST, caPublicKeysDTO)
          .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testDownloadCaPublicKeysReportPDF | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadCaPublicKeysReportException() {
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_CA_PUBLIC_KEYS_REPORT)
          .sessionAttr(Constants.PUBLIC_KEY_REQUEST, caPublicKeysDTO)
          .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error(
          "CAPublicKeysControllerTest | testDownloadCaPublicKeysReportException | Exception ", e);
    }
  }

  @Test
  public void testShowCreateCApublicKeysPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testShowCreateCApublicKeysPage | Exception ", e);
    }
  }

  @Test
  public void testCreateCAPublicKeys() {
    caPublicKeysResponse = new CAPublicKeysResponse();
    caPublicKeysResponse.setTotalNoOfRows(Constants.TEN);
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCreateCAPublicKeys | Exception ", e);
    }
  }

  @Test
  public void testCreateCAPublicKeysNoOFRowsNull() {
    caPublicKeysResponse = new CAPublicKeysResponse();
    responseval = new Response();
    caPublicKeysResponse.setTotalNoOfRows(null);
    responseval.setErrorMessage(Constants.SUCESS);
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      Mockito.when(caPublicKeysService.createCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCreateCAPublicKeysNoOFRowsNull | Exception ",
          e);
    }
  }

  @Test
  public void testCreateCAPublicKeysElse() {
    caPublicKeysResponse = new CAPublicKeysResponse();
    responseval = new Response();
    caPublicKeysResponse.setTotalNoOfRows(null);
    responseval.setErrorMessage(Constants.SUCCESS);
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      Mockito.when(caPublicKeysService.createCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCreateCAPublicKeysElse | Exception ", e);
    }
  }

  @Test
  public void testCreateCAPublicKeysElseException() {
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCreateCAPublicKeysElseException | Exception ",
          e);
    }
  }

  @Test
  public void testCreateCAPublicKeysElseChatakAdminException() {
    try {
      Mockito.when(caPublicKeysService.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE));
    } catch (Exception e) {
      logger.error(
          "CAPublicKeysControllerTest | testCreateCAPublicKeysElseChatakAdminException | Exception ",
          e);
    }
  }

  @Test
  public void testShowCAPublicKeysEdit() {
    try {
      Mockito.when(caPublicKeysService.caPublicKeysById(Matchers.anyLong()))
          .thenReturn(pgcapublickeys);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_CA_PUBLIC_KEYS_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testShowCAPublicKeysEdit | Exception ", e);
    }
  }

  @Test
  public void testShowCAPublicKeysEditGetCaPublicKeyDataException() {
    try {
      Mockito.when(caPublicKeysService.caPublicKeysById(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_CA_PUBLIC_KEYS_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_EDIT_PAGE));
    } catch (Exception e) {
      logger.error(
          "CAPublicKeysControllerTest | testShowCAPublicKeysEditGetCaPublicKeyDataException | Exception ",
          e);
    }
  }

  @Test
  public void testShowCAPublicKeysView() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_CA_PUBLIC_KEYS_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.SHOW_CA_PUBLIC_KEYS_VIEW));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testShowCAPublicKeysView | Exception ", e);
    }
  }

  @Test
  public void testUpdateCAPublicKeys() {
    responseval = new Response();
    responseval.setErrorMessage(Constants.SUCESS);
    try {
      Mockito.when(caPublicKeysService.updateCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testUpdateCAPublicKeys | Exception ", e);
    }
  }

  @Test
  public void testUpdateCAPublicKeysElse() {
    responseval = new Response();
    responseval.setErrorMessage(Constants.SUCCESS);
    try {
      Mockito.when(caPublicKeysService.updateCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testUpdateCAPublicKeysElse | Exception ", e);
    }
  }

  @Test
  public void testUpdateCAPublicKeysException() {
    try {
      Mockito.when(caPublicKeysService.updateCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testUpdateCAPublicKeysException | Exception ", e);
    }
  }

  @Test
  public void testUpdateCAPublicKeysChatakAdminException() {
    try {
      Mockito.when(caPublicKeysService.updateCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_CA_PUBLIC_KEYS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testUpdateCAPublicKeysException | Exception ", e);
    }
  }

  @Test
  public void testChangeCAPublicKeysStatus() {
    caPublicKeysResponse = new CAPublicKeysResponse();
    caPublicKeysResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito
          .when(caPublicKeysService.changeCAPublicKeysStatus(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse)
              .sessionAttr(Constants.PAGE_NUMBER, Constants.ONE)
              .param("suspendActiveId", Constants.ONE.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testChangeCAPublicKeysStatus | Exception ", e);
    }
  }


  @Test
  public void testChangeCAPublicKeysStatusElse() {
    try {
      Mockito
          .when(caPublicKeysService.changeCAPublicKeysStatus(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse)
              .sessionAttr(Constants.PAGE_NUMBER, Constants.ONE)
              .param("suspendActiveId", Constants.ONE.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testChangeCAPublicKeysStatusElse | Exception ", e);
    }
  }

  @Test
  public void testChangeCAPublicKeysStatusException() {
    try {
      Mockito
          .when(caPublicKeysService.changeCAPublicKeysStatus(Matchers.any(CAPublicKeysDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CA_PUBLIC_KEYS_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse)
              .sessionAttr(Constants.PAGE_NUMBER, Constants.ONE)
              .param("suspendActiveId", Constants.ONE.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "CAPublicKeysControllerTest | testChangeCAPublicKeysStatusException | Exception ", e);
    }
  }

  @Test
  public void testValidateuniquePublickeyName() {
    try {
      Mockito.when(caPublicKeysService.validatePublickeyName(Matchers.anyString()))
          .thenReturn(publickeyNameResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_PUBLICKEYNAME_VALIDATE)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testValidateuniquePublickeyName | Exception ", e);
    }
  }

  @Test
  public void testCaPublicKeyDelete() {
    caPublicKeysDTO = new CAPublicKeysDTO();
    caPublicKeysDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(caPublicKeysService.findCAPublicKeyById(Matchers.anyLong()))
          .thenReturn(caPublicKeysDTO);
      Mockito.when(caPublicKeysService.saveOrUpdateCAPublicKey(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_CA_PUBLIC_KEY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("getCAPublicKeyID",
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCaPublicKeyDelete | Exception ", e);
    }
  }

  @Test
  public void testCaPublicKeyDeleteElse() {
    caPublicKeysDTO = new CAPublicKeysDTO();
    caPublicKeysDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(caPublicKeysService.findCAPublicKeyById(Matchers.anyLong()))
          .thenReturn(caPublicKeysDTO);
      Mockito.when(caPublicKeysService.saveOrUpdateCAPublicKey(Matchers.any(CAPublicKeysDTO.class)))
          .thenReturn(caPublicKeysDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_CA_PUBLIC_KEY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("getCAPublicKeyID",
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCaPublicKeyDeleteElse | Exception ", e);
    }
  }

  @Test
  public void testCaPublicKeyDeleteException() {
    try {
      Mockito.when(caPublicKeysService.findCAPublicKeyById(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.DELETE_CA_PUBLIC_KEY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("getCAPublicKeyID",
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CA_PUBLIC_KEYS_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CAPublicKeysControllerTest | testCaPublicKeyDeleteException | Exception ", e);
    }
  }

}
