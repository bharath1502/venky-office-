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
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BinService;
import com.chatak.acquirer.admin.service.SwitchService;
import com.chatak.pg.bean.BinDuplicateResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.BinDTO;
import com.chatak.pg.model.BinResponse;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class BinControllerTest {

  private static Logger logger = Logger.getLogger(BinControllerTest.class);

  private static final String GET_BIN_ID = "getBinId";
  
  @InjectMocks
  BinController binController = new BinController();

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
  private SwitchService switchService;

  @Mock
  private SwitchResponse searchSwitchResponse;

  @Mock
  private ChatakAdminException chatakAdminException;

  @Mock
  private LoginDetails loginDetails;

  @Mock
  private BinService binService;

  @Mock
  private BinDTO binDTO;

  @Mock
  private BinResponse binResponseList;

  @Mock
  private SwitchRequest switchRequest;

  @Mock
  private BinDuplicateResponse reBinResponse;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private List<BinDTO> bins;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(binController).setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testShowBinSearch() {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenReturn(searchSwitchResponse);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testShowBinSearch | Exception ", e);
    }
  }

  @Test
  public void testShowBinSearchException() {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BinControllerTest | testShowBinSearchException | Exception ", e);
    }
  }

  @Test
  public void testProcessBinSearch() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenReturn(searchSwitchResponse);
    Mockito.when(binService.searchBins(Matchers.any(BinDTO.class))).thenReturn(binResponseList);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_SEARCH).sessionAttr(Constants.EXISTING_FEATURES,
              Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testProcessBinSearch | Exception ", e);
    }
  }

  @Test
  public void testProcessBinSearchException() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_SEARCH).sessionAttr(Constants.EXISTING_FEATURES,
              Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testProcessBinSearchException | Exception ", e);
    }
  }

  @Test
  public void testSaveBin() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenReturn(searchSwitchResponse);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.ONUS_BIN_CREATE_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CREATE));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSaveBin | Exception ", e);
    }
  }

  @Test
  public void testSaveBinException() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.ONUS_BIN_CREATE_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CREATE));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSaveBinException | Exception ", e);
    }
  }

  @Test
  public void testSaveBinProcess() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class))).thenReturn(binDTO);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_SAVE_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSaveBinProcess | Exception ", e);
    }
  }

  @Test
  public void testSaveBinProcessElse() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class))).thenReturn(binDTO);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_SAVE_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSaveBinProcessElse | Exception ", e);
    }
  }

  @Test
  public void testSaveBinProcessException() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_SAVE_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSaveBinProcessException | Exception ", e);
    }
  }

  @Test
  public void testEditBin() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenReturn(searchSwitchResponse);
    Mockito.when(binService.searchBinById(Matchers.anyLong())).thenReturn(binDTO);
    Mockito.when(switchService.getSwtichInformationById(Matchers.anyLong()))
        .thenReturn(switchRequest);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_EDIT));
    } catch (Exception e) {
      logger.error("BankControllerTest | testEditBin | Exception ", e);
    }
  }

  @Test
  public void testEditBinException() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_EDIT));
    } catch (Exception e) {
      logger.error("BankControllerTest | testEditBinException | Exception ", e);
    }
  }

  @Test
  public void testEditView() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenReturn(searchSwitchResponse);
    Mockito.when(binService.searchBinById(Matchers.anyLong())).thenReturn(binDTO);
    Mockito.when(switchService.getSwtichInformationById(Matchers.anyLong()))
        .thenReturn(switchRequest);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_VIEW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testEditView | Exception ", e);
    }
  }

  @Test
  public void testEditViewException() throws ChatakAdminException {
    Mockito.when(switchService.getSwitchByStatus(Matchers.anyInt()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_VIEW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testEditViewException | Exception ", e);
    }
  }

  @Test
  public void testUpdateBin() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class))).thenReturn(binDTO);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_UPDATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testUpdateBin | Exception ", e);
    }
  }

  @Test
  public void testUpdateBinElse() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class))).thenReturn(binDTO);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_UPDATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testUpdateBinElse | Exception ", e);
    }
  }

  @Test
  public void testUpdateBinException() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_UPDATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testUpdateBinException | Exception ", e);
    }
  }

  @Test
  public void testBinDelete() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class))).thenReturn(binDTO);
    Mockito.when(binService.searchBinById(Matchers.anyLong())).thenReturn(binDTO);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_DELETE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testBinDelete | Exception ", e);
    }
  }

  @Test
  public void testBinDeleteElse() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class))).thenReturn(binDTO);
    Mockito.when(binService.searchBinById(Matchers.anyLong())).thenReturn(binDTO);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_DELETE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testBinDeleteElse | Exception ", e);
    }
  }

  @Test
  public void testBinDeleteException() throws ChatakAdminException {
    binDTO = new BinDTO();
    binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(binService.saveOrUpdateBin(Matchers.any(BinDTO.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_DELETE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param(GET_BIN_ID,
                  Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testBinDeleteException | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationList() throws ChatakAdminException {
    binResponseList = new BinResponse();
    binResponseList.setBins(bins);
    binDTO.setPageSize(Constants.TEN);
    binResponseList.setNoOfRecords(Constants.TEN);
    Mockito.when(binService.searchBins(Matchers.any(BinDTO.class))).thenReturn(binResponseList);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.GET_BINS_FOR_PAGINATIONS)
              .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.TOTAL_RECORDS, Constants.TEN.toString())
              .param(GET_BIN_ID, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString())
              .param("pageNumber", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testGetPaginationList | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationListException() throws ChatakAdminException {
    Mockito.when(binService.searchBins(Matchers.any(BinDTO.class))).thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.GET_BINS_FOR_PAGINATIONS)
              .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.TOTAL_RECORDS, Constants.TEN.toString())
              .param(GET_BIN_ID, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString())
              .param("pageNumber", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testGetPaginationListException | Exception ", e);
    }
  }

  @Test
  public void testDownloadBinReport() throws ChatakAdminException {
    binResponseList = new BinResponse();
    binResponseList.setBins(bins);
    binDTO.setPageSize(Constants.TEN);
    binResponseList.setNoOfRecords(Constants.TEN);
    Mockito.when(binService.searchBins(Matchers.any(BinDTO.class))).thenReturn(binResponseList);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.GET_BIN_REPORT)
          .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true")
          .param("downLoadPageNumber", "1").param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDownloadBinReport | Exception ", e);
    }
  }

  @Test
  public void testDownloadBinReportPDF() throws ChatakAdminException {
    binResponseList = new BinResponse();
    binResponseList.setBins(bins);
    binDTO.setPageSize(Constants.TEN);
    binResponseList.setNoOfRecords(Constants.TEN);
    Mockito.when(binService.searchBins(Matchers.any(BinDTO.class))).thenReturn(binResponseList);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.GET_BIN_REPORT)
          .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true")
          .param("downLoadPageNumber", "1").param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDownloadBinReportPDF | Exception ", e);
    }
  }

  @Test
  public void testDownloadBinReportException() throws ChatakAdminException {
    Mockito.when(binService.searchBins(Matchers.any(BinDTO.class))).thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.GET_BIN_REPORT)
          .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true")
          .param("downLoadPageNumber", "1").param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDownloadBinReportException | Exception ", e);
    }
  }

  @Test
  public void testValidateUniqueBinId() throws ChatakAdminException {
    Mockito.when(binService.validateBin(Matchers.anyLong())).thenReturn(reBinResponse);
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_BIN_VALIDATE)
          .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .param("binId", Constants.ONE.toString()));
    } catch (Exception e) {
      logger.error("BankControllerTest | testValidateUniqueBinId | Exception ", e);
    }
  }

  @Test
  public void testValidateUniqueBinIdException() throws ChatakAdminException {
    Mockito.when(binService.validateBin(Matchers.anyLong())).thenThrow(nullPointerException);
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_BIN_VALIDATE)
          .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .param("binId", Constants.ONE.toString()));
    } catch (Exception e) {
      logger.error("BankControllerTest | testValidateUniqueBinIdException | Exception ", e);
    }
  }

  @Test
  public void testChangeBinStatus() throws ChatakAdminException {
    binResponseList = new BinResponse();
    binResponseList.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(binService.changeBinStatus(Matchers.any(BinDTO.class)))
        .thenReturn(binResponseList);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.PAGE_NUMBER, Constants.ONE).param("suspendActiveId", "1")
              .param("suspendActiveStatus", "Active").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testChangeBinStatus | Exception ", e);
    }
  }

  @Test
  public void testChangeBinStatusElse() throws ChatakAdminException {
    binResponseList = new BinResponse();
    binResponseList.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(binService.changeBinStatus(Matchers.any(BinDTO.class)))
        .thenReturn(binResponseList);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ONUS_BIN_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.PAGE_NUMBER, Constants.ONE).param("suspendActiveId", "1")
              .param("suspendActiveStatus", "Active").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.ONUS_BIN_CONFIGURATION_SHOW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testChangeBinStatusElse | Exception ", e);
    }
  }

  @Test
  public void testChangeBinStatusException() throws ChatakAdminException {
    Mockito.when(binService.searchBins(Matchers.any(BinDTO.class))).thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.ONUS_BIN_ACTIVATION_SUSPENTION)
          .sessionAttr(Constants.SEARCH_BIN_INFO, binDTO)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true")
          .param("downLoadPageNumber", "1").param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("BankControllerTest | testChangeBinStatusException | Exception ", e);
    }
  }

}
