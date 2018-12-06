package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantCategoryCodeSearchResponse;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.MerchantCategoryCode;
import com.chatak.pg.user.bean.MerchantCategoryCodeResponse;

@RunWith(MockitoJUnitRunner.class)
public class MerchantCategoryCodeControllerTest {

  private static Logger logger = LoggerFactory.getLogger(MerchantCategoryCodeControllerTest.class);

  @InjectMocks
  MerchantCategoryCodeController merchantCategoryCodeController =
      new MerchantCategoryCodeController();

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
  private LoginDetails loginDetails;

  @Mock
  private MerchantCategoryCodeService mccService;

  @Mock
  private MerchantCategoryCodeResponse mccResponse;

  @Mock
  private MerchantCategoryCodeSearchResponse mccSearchResponse;

  @Mock
  private List<MerchantCategoryCode> mccList;

  @Mock
  private MerchantCategoryCode merchantCategoryCode;

  @Mock
  private List<String> selectedTCCMultiple;

  @Mock
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(merchantCategoryCodeController)
        .setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
    mccList = new ArrayList<>();
  }

  @Test
  public void testShowCreateMCCPage() {
    try {
      Mockito.when(mccService.getAllTCCs()).thenReturn(optionList);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testShowCreateMCCPage | Exception ", e);
    }
  }

  @Test
  public void testShowCreateMCCPageException() {
    try {
      Mockito.when(mccService.getAllTCCs()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE));
    } catch (Exception e) {
      logger.error(
          "MerchantCategoryCodeControllerTest | testShowCreateMCCPageException | Exception ", e);
    }
  }

  @Test
  public void testCreateMCC() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC0);
    try {
      Mockito.when(mccService.createMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testCreateMCC | Exception ", e);
    }
  }

  @Test
  public void testCreateMCCElse() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC1);
    try {
      Mockito.when(mccService.createMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testCreateMCCElse | Exception ", e);
    }
  }

  @Test
  public void testCreateMCCException() {
    try {
      Mockito.when(mccService.createMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testCreateMCCException | Exception ", e);
    }
  }

  @Test
  public void testSearchMCC() {
    mccSearchResponse = new MerchantCategoryCodeSearchResponse();
    mccList.add(merchantCategoryCode);
    mccSearchResponse.setMccs(mccList);
    mccSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param(TestConstants.PAGE_SIZE,
                  TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testSearchMCC | Exception ", e);
    }
  }

  @Test
  public void testSearchMCCException() {
    mccSearchResponse = new MerchantCategoryCodeSearchResponse();
    mccSearchResponse.setMccs(mccList);
    mccSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param(TestConstants.PAGE_SIZE,
                  TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testSearchMCCException | Exception ", e);
    }
  }

  @Test
  public void testShowEditMCC() {
    merchantCategoryCode = new MerchantCategoryCode();
    merchantCategoryCode.setSelectedTCCMultiple(selectedTCCMultiple);
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      Mockito.when(mccService.getMCCDetails(Matchers.anyLong())).thenReturn(merchantCategoryCode);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testShowEditMCC | Exception ", e);
    }
  }

  @Test
  public void testShowEditMCCElse() {
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testShowEditMCCElse | Exception ", e);
    }
  }

  @Test
  public void testShowEditMCCException() {
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      Mockito.when(mccService.getMCCDetails(Matchers.anyLong())).thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testShowEditMCCException | Exception ", e);
    }
  }

  @Test
  public void testShowEditMCCChatakException() {
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      Mockito.when(mccService.getMCCDetails(Matchers.anyLong())).thenThrow(chatakAdminException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error(
          "MerchantCategoryCodeControllerTest | testShowEditMCCChatakException | Exception ", e);
    }
  }

  @Test
  public void testShowViewMCC() {
    merchantCategoryCode = new MerchantCategoryCode();
    merchantCategoryCode.setSelectedTCCMultiple(selectedTCCMultiple);
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      Mockito.when(mccService.getMCCDetails(Matchers.anyLong())).thenReturn(merchantCategoryCode);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testShowViewMCC | Exception ", e);
    }
  }

  @Test
  public void testShowViewMCCElse() {
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testShowViewMCCElse | Exception ", e);
    }
  }

  @Test
  public void testShowViewMCCException() {
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      Mockito.when(mccService.getMCCDetails(Matchers.anyLong())).thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testShowViewMCCException | Exception ", e);
    }
  }

  @Test
  public void testShowViewMCCChatakException() {
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      Mockito.when(mccService.getMCCDetails(Matchers.anyLong())).thenThrow(chatakAdminException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error(
          "MerchantCategoryCodeControllerTest | testShowViewMCCChatakException | Exception ", e);
    }
  }

  @Test
  public void testUpdateMCC() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC2);
    try {
      Mockito.when(mccService.updateMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_UPDATE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testUpdateMCC | Exception ", e);
    }
  }

  @Test
  public void testUpdateMCCElse() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC0);
    try {
      Mockito.when(mccService.updateMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_UPDATE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testUpdateMCCElse | Exception ", e);
    }
  }

  @Test
  public void testUpdateMCCException() {
    try {
      Mockito.when(mccService.updateMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_UPDATE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()).param("editId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testUpdateMCCException | Exception ", e);
    }
  }

  @Test
  public void testDeleteMCC() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(mccService.deleteMcc(Matchers.anyLong())).thenReturn(mccResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_DELERE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString())
              .param("getDeleteMCCId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testDeleteMCC | Exception ", e);
    }
  }

  @Test
  public void testDeleteMCCElseIf() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(mccService.deleteMcc(Matchers.anyLong())).thenReturn(mccResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_DELERE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString())
              .param("getDeleteMCCId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testDeleteMCCElseIf | Exception ", e);
    }
  }

  @Test
  public void testDeleteMCCElse() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(mccService.deleteMcc(TestConstants.THREE_LONG)).thenReturn(mccResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_DELERE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString())
              .param("getDeleteMCCId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testDeleteMCCElse | Exception ", e);
    }
  }

  @Test
  public void testDeleteMCCException() {
    try {
      Mockito.when(mccService.deleteMcc(Matchers.anyLong())).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_DELERE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString())
              .param("getDeleteMCCId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testDeleteMCCException | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    mccSearchResponse = new MerchantCategoryCodeSearchResponse();
    mccList.add(merchantCategoryCode);
    mccSearchResponse.setMccs(mccList);
    mccSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_PAGINATION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
              .param(TestConstants.PAGE_NUMBER, TestConstants.TWO.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testGetPaginationList | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationListException() {
    try {
      Mockito.when(mccService.searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_PAGINATION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
              .param(TestConstants.PAGE_NUMBER, TestConstants.TWO.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error(
          "MerchantCategoryCodeControllerTest | testGetPaginationListException | Exception ", e);
    }
  }

  @Test
  public void testDownloadMerchantCategoryCodeList() {
    mccSearchResponse = new MerchantCategoryCodeSearchResponse();
    mccList.add(merchantCategoryCode);
    mccSearchResponse.setMccs(mccList);
    try {
      Mockito
          .when(merchantCategoryCodeService
              .searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.GET_MERCHANT_CATEGORY_CODE_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
          .param("downLoadPageNumber", TestConstants.TWO.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString())
          .param("downloadAllRecords", "true").param(TestConstants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error(
          "MerchantCategoryCodeControllerTest | testDownloadMerchantCategoryCodeList | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadMerchantCategoryCodeListPDF() {
    mccSearchResponse = new MerchantCategoryCodeSearchResponse();
    mccList.add(merchantCategoryCode);
    mccSearchResponse.setMccs(mccList);
    try {
      Mockito
          .when(merchantCategoryCodeService
              .searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.GET_MERCHANT_CATEGORY_CODE_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
          .param("downLoadPageNumber", TestConstants.TWO.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString())
          .param("downloadAllRecords", "true").param(TestConstants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error(
          "MerchantCategoryCodeControllerTest | testDownloadMerchantCategoryCodeListPDF | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadMerchantCategoryCodeListException() {
    try {
      Mockito
          .when(merchantCategoryCodeService
              .searchMerchantCategoryCode(Matchers.any(MerchantCategoryCode.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.GET_MERCHANT_CATEGORY_CODE_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
          .param("downLoadPageNumber", TestConstants.TWO.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString())
          .param("downloadAllRecords", "true").param(TestConstants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error(
          "MerchantCategoryCodeControllerTest | testDownloadMerchantCategoryCodeListException | Exception ",
          e);
    }
  }

  @Test
  public void testChangeMCCStatus() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito
          .when(
              mccService.changeMerchantCategoryCodeStatus(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccResponse);
      mockMvc.perform(
          post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_ACTIVATION_SUSPENTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
              .sessionAttr(TestConstants.PAGE_NUMBER, TestConstants.TWO)
              .param("suspendActiveId", TestConstants.TWO.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason")
              .param(TestConstants.DOWNLOAD_TYPE, "PDF"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testChangeMCCStatus | Exception ", e);
    }
  }

  @Test
  public void testChangeMCCStatusElse() {
    mccResponse = new MerchantCategoryCodeResponse();
    mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito
          .when(
              mccService.changeMerchantCategoryCodeStatus(Matchers.any(MerchantCategoryCode.class)))
          .thenReturn(mccResponse);
      mockMvc.perform(
          post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_ACTIVATION_SUSPENTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
              .sessionAttr(TestConstants.PAGE_NUMBER, TestConstants.TWO)
              .param("suspendActiveId", TestConstants.TWO.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason")
              .param(TestConstants.DOWNLOAD_TYPE, "PDF"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testChangeMCCStatusElse | Exception ", e);
    }
  }

  @Test
  public void testChangeMCCStatusException() {
    try {
      Mockito
          .when(
              mccService.changeMerchantCategoryCodeStatus(Matchers.any(MerchantCategoryCode.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(
          post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_ACTIVATION_SUSPENTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MCC_MODEL, merchantCategoryCode)
              .sessionAttr(TestConstants.PAGE_NUMBER, TestConstants.TWO)
              .param("suspendActiveId", TestConstants.TWO.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason")
              .param(TestConstants.DOWNLOAD_TYPE, "PDF"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH));
    } catch (Exception e) {
      logger.error("MerchantCategoryCodeControllerTest | testChangeMCCStatusException | Exception ",
          e);
    }
  }
}
