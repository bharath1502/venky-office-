package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
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

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeeCodeResponseDetails;
import com.chatak.acquirer.admin.model.FeeProgramResponseDetails;
import com.chatak.acquirer.admin.service.FeeCodeService;
import com.chatak.acquirer.admin.service.FeeProgramService;
import com.chatak.pg.bean.FeeprogramNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.FeeCodeDTO;
import com.chatak.pg.model.FeeProgramDTO;

@RunWith(MockitoJUnitRunner.class)
public class FeeProgramControllerTest {

  private static Logger logger = Logger.getLogger(FeeProgramControllerTest.class);

  @InjectMocks
  FeeProgramController feeProgramController = new FeeProgramController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  BindingResult bindingResult;

  @Mock
  private Option option;

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
  private LoginDetails loginDetails;

  @Mock
  private LoginResponse loginResponse;

  @Mock
  private FeeCodeResponseDetails codeResponse;

  @Mock
  private FeeCodeService feeCodeService;

  @Mock
  private List<FeeCodeDTO> feeCodeList;

  @Mock
  private FeeProgramDTO feeProgramDTO;

  @Mock
  private List<FeeProgramDTO> feeProgramDTOList;

  @Mock
  private FeeProgramService feeProgramService;

  @Mock
  private FeeCodeDTO feeCodeDTO;

  @Mock
  private FeeProgramResponseDetails feeProgramResponseDetails;

  @Mock
  private FeeprogramNameResponse feeprogramNameResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(feeProgramController).setViewResolvers(viewResolver)
        .build();
    optionList = new ArrayList<>();
    option = new Option();
    feeCodeList = new ArrayList<>();
    feeProgramDTO = new FeeProgramDTO();
    feeProgramDTOList = new ArrayList<>();
  }

  @Test
  public void testShowFeeProgramSearch() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_FEE_PROGRAM_SEARCH)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramSearch | Exception ", e);
    }
  }

  @Test
  public void testShowFeeProgramCreate() {
    codeResponse = new FeeCodeResponseDetails();
    feeCodeList.add(feeCodeDTO);
    codeResponse.setFeeCodeList(feeCodeList);
    try {
      Mockito.when(feeCodeService.getAllFeeCodeList()).thenReturn(codeResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_FEE_PROGRAM_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramCreate | Exception ", e);
    }
  }

  @Test
  public void testShowFeeProgramCreateException() {
    try {
      Mockito.when(feeCodeService.getAllFeeCodeList()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_FEE_PROGRAM_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramCreateException | Exception ", e);
    }
  }

  @Test
  public void testCreateFeeProgram() {
    responseval = new Response();
    responseval.setErrorMessage(TestConstants.SUCESS);
    try {
      Mockito.when(feeProgramService.createFeeProgram(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testCreateFeeProgram | Exception ", e);
    }
  }

  @Test
  public void testCreateFeeProgramFailed() {
    responseval = new Response();
    responseval.setErrorMessage(TestConstants.FAILED);
    try {
      Mockito.when(feeProgramService.createFeeProgram(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testCreateFeeProgramFailed | Exception ", e);
    }
  }

  @Test
  public void testCreateFeeProgramException() {
    try {
      Mockito.when(feeProgramService.createFeeProgram(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testCreateFeeProgramException | Exception ", e);
    }
  }

  @Test
  public void testCreateFeeProgramChatakException() {
    try {
      Mockito.when(feeProgramService.createFeeProgram(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testCreateFeeProgramChatakException | Exception ",
          e);
    }
  }

  @Test
  public void testUpdateFeeProgram() {
    responseval = new Response();
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTO.setCreatedBy(TestConstants.ADMIN_VALUE);
    feeProgramDTO.setCreatedDate(new Timestamp(TestConstants.ZERO));
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    responseval.setErrorMessage(TestConstants.SUCESS);
    try {
      Mockito.when(feeProgramService.getByFeeProgramId(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      Mockito.when(feeProgramService.updateFeeProgram(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_FEE_PROGRAM)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testUpdateFeeProgram | Exception ", e);
    }
  }

  @Test
  public void testUpdateFeeProgramElse() {
    responseval = new Response();
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTO.setCreatedBy(TestConstants.ADMIN_VALUE);
    feeProgramDTO.setCreatedDate(new Timestamp(TestConstants.ZERO));
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    responseval.setErrorMessage(TestConstants.FAILED);
    try {
      Mockito.when(feeProgramService.getByFeeProgramId(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      Mockito.when(feeProgramService.updateFeeProgram(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_FEE_PROGRAM)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testUpdateFeeProgramElse | Exception ", e);
    }
  }

  @Test
  public void testUpdateFeeProgramException() {
    try {
      Mockito.when(feeProgramService.getByFeeProgramId(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_FEE_PROGRAM)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testUpdateFeeProgramException | Exception ", e);
    }
  }

  @Test
  public void testUpdateFeeProgramChatakException() {
    try {
      Mockito.when(feeProgramService.getByFeeProgramId(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_FEE_PROGRAM)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testUpdateFeeProgramChatakException | Exception ",
          e);
    }
  }

  @Test
  public void testSearchFeeProgram() {
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    feeProgramResponseDetails.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_SEARCH)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse)
              .param("pageSize", TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testSearchFeeProgram | Exception ", e);
    }
  }

  @Test
  public void testSearchFeeProgramElse() {
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_SEARCH)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testSearchFeeProgramElse | Exception ", e);
    }
  }

  @Test
  public void testSearchFeeProgramChatakException() {
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_SEARCH)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testSearchFeeProgramChatakException | Exception ",
          e);
    }
  }

  @Test
  public void testSearchFeeProgramException() {
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_SEARCH)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testSearchFeeProgramException | Exception ", e);
    }
  }

  @Test
  public void testShowFeeProgramEdit() {
    responseval = new Response();
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTO.setCreatedBy(TestConstants.ADMIN_VALUE);
    feeProgramDTO.setCreatedDate(new Timestamp(TestConstants.ZERO));
    feeProgramDTO.setFeeProgramName("feeProgramName");
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    responseval.setErrorMessage(TestConstants.FAILED);
    try {
      Mockito.when(feeProgramService.getFeeProgramDetails(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_FEE_PROGRAM_EDIT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramEdit | Exception ", e);
    }
  }

  @Test
  public void testShowFeeProgramEditException() {
    try {
      Mockito.when(feeProgramService.getFeeProgramDetails(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_FEE_PROGRAM_EDIT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramEditException | Exception ", e);
    }
  }

  @Test
  public void testShowFeeProgramEditChatakException() {
    try {
      Mockito.when(feeProgramService.getFeeProgramDetails(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_FEE_PROGRAM_EDIT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramEditChatakException | Exception ",
          e);
    }
  }

  @Test
  public void testShowFeeProgramView() {
    responseval = new Response();
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTO.setCreatedBy(TestConstants.ADMIN_VALUE);
    feeProgramDTO.setCreatedDate(new Timestamp(TestConstants.ZERO));
    feeProgramDTO.setFeeProgramName("feeProgramName");
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    responseval.setErrorMessage(TestConstants.FAILED);
    try {
      Mockito.when(feeProgramService.getFeeProgramDetails(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_FEE_PROGRAM_VIEW)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.SHOW_FEE_PROGRAM_VIEW));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramView | Exception ", e);
    }
  }

  @Test
  public void testShowFeeProgramViewException() {
    try {
      Mockito.when(feeProgramService.getFeeProgramDetails(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_FEE_PROGRAM_VIEW)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.SHOW_FEE_PROGRAM_VIEW));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramViewException | Exception ", e);
    }
  }

  @Test
  public void testShowFeeProgramViewChatakException() {
    try {
      Mockito.when(feeProgramService.getFeeProgramDetails(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_FEE_PROGRAM_VIEW)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.SHOW_FEE_PROGRAM_VIEW));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testShowFeeProgramViewChatakException | Exception ",
          e);
    }
  }

  @Test
  public void testGetfeeProgramPagination() {
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    feeProgramResponseDetails.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_PAGINATION_ACTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse)
              .sessionAttr(TestConstants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA, feeProgramDTO)
              .param("pageNumber", TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testGetfeeProgramPagination | Exception ", e);
    }
  }

  @Test
  public void testGetfeeProgramPaginationException() {
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.FEE_PROGRAM_PAGINATION_ACTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse)
              .sessionAttr(TestConstants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA, feeProgramDTO)
              .param("pageNumber", TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.FEE_PROGRAM_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testGetfeeProgramPaginationException | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadFeeProgramReport() {
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_FEE_PROGRAM_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse)
          .sessionAttr(TestConstants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA, feeProgramDTO)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadType", "XLS")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testDownloadFeeProgramReport | Exception ", e);
    }
  }

  @Test
  public void testDownloadFeeProgramReportPDF() {
    feeProgramResponseDetails = new FeeProgramResponseDetails();
    feeProgramDTOList.add(feeProgramDTO);
    feeProgramResponseDetails.setFeeCodeList(feeProgramDTOList);
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(feeProgramResponseDetails);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_FEE_PROGRAM_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse)
          .sessionAttr(TestConstants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA, feeProgramDTO)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadType", "PDF")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testDownloadFeeProgramReportPDF | Exception ", e);
    }
  }

  @Test
  public void testDownloadFeeProgramReportException() {
    try {
      Mockito.when(feeProgramService.searchFeeProgramForJpa(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_FEE_PROGRAM_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_RESPONSE_DATA, loginResponse)
          .sessionAttr(TestConstants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA, feeProgramDTO)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadType", "PDF")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testDownloadFeeProgramReportException | Exception ",
          e);
    }
  }

  @Test
  public void testValidateAccountNumFromCI() {
    try {
      Mockito.when(feeProgramService.validateFeePgmAccNo(Matchers.anyString())).thenReturn(false);
      mockMvc.perform(post("/" + URLMappingConstants.VALIDATE_FEEPGM_ACCOUNTNUM)
          .param("specificAccountNumber", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testValidateAccountNumFromCI | Exception ", e);
    }
  }

  @Test
  public void testValidateAccountNumFromCIException() {
    try {
      Mockito.when(feeProgramService.validateFeePgmAccNo(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.VALIDATE_FEEPGM_ACCOUNTNUM)
          .param("specificAccountNumber", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testValidateAccountNumFromCIException | Exception ",
          e);
    }
  }

  @Test
  public void testDeleteFeeProgram() {
    try {
      Mockito.when(feeProgramService.deleteFeeProgram(Long.valueOf(Matchers.anyLong())))
          .thenReturn(responseval);
      mockMvc.perform(get("/" + URLMappingConstants.SHOW_FEE_PROGRAM_DELETE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("feeProgramIds", "2"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testDeleteFeeProgram | Exception ", e);
    }
  }

  @Test
  public void testDeleteFeeProgramException() {
    try {
      Mockito.when(feeProgramService.deleteFeeProgram(Long.valueOf(Matchers.anyLong())))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.SHOW_FEE_PROGRAM_DELETE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("feeProgramIds", "2"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testDeleteFeeProgramException | Exception ", e);
    }
  }

  @Test
  public void testValidateFeeprogramName() {
    try {
      Mockito.when(feeProgramService.validateFeeprogramName(Matchers.anyString()))
          .thenReturn(feeprogramNameResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_FEEPROGRAM_VALIDATE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("feeProgramId", "2"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testValidateFeeprogramName | Exception ", e);
    }
  }

  @Test
  public void testChangeFeeProgramStatus() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(feeProgramService.changeFeeProgramStatus(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(responseval);
      mockMvc.perform(post("/" + URLMappingConstants.FEE_PROGRAM_STATUS_CHANGE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("suspendActiveId", "2")
          .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testChangeFeeProgramStatus | Exception ", e);
    }
  }

  @Test
  public void testChangeFeeProgramStatusElse() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(feeProgramService.changeFeeProgramStatus(Matchers.any(FeeProgramDTO.class)))
          .thenReturn(responseval);
      mockMvc.perform(post("/" + URLMappingConstants.FEE_PROGRAM_STATUS_CHANGE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("suspendActiveId", "2")
          .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testChangeFeeProgramStatusElse | Exception ", e);
    }
  }

  @Test
  public void testChangeFeeProgramStatusException() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(feeProgramService.changeFeeProgramStatus(Matchers.any(FeeProgramDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.FEE_PROGRAM_STATUS_CHANGE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("suspendActiveId", "2")
          .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"));
    } catch (Exception e) {
      logger.error("FeeProgramControllerTest | testChangeFeeProgramStatusException | Exception ",
          e);
    }
  }
}
