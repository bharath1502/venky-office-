package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.BankSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.MDRService;
import com.chatak.acquirer.admin.service.PaymentSchemeService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.Bank;
import com.chatak.pg.model.DynamicMDRDTO;
import com.chatak.pg.user.bean.PaymentSchemeRequest;
import com.chatak.pg.user.bean.PaymentSchemeResponse;
import com.chatak.pg.util.Constants;

@Controller
@SuppressWarnings({"rawtypes", "unchecked"})
public class MDRController implements URLMappingConstants {
	
  private static final String DYNAMIC_MDR_DTO_LIST = "dynamicMDRDTOlist";
  
  private static final String DYNAMIC_MDR_DTO = "dynamicMDRDTO";
  
  @Autowired
  MessageSource messageSource;

  private static Logger logger = Logger.getLogger(MDRController.class);

  @Autowired
  private PaymentSchemeService paymentSchemeService;

  @Autowired
  private BankService bankService;

  @Autowired
  private MDRService mdrService;

  @RequestMapping(value = DYNAMIC_MDR_SHOW_SEARCH, method = RequestMethod.GET)
  public ModelAndView showDynamicMDRSearch(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session) {
    logger.info("Entering:: MDRController:: showDynamicMDRSearch method");

    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DYNAMIC_MDR_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
    Bank bankRequest = new Bank();
    try {
      DynamicMDRDTO dynamicMDRDTO = new DynamicMDRDTO();
      dynamicMDRDTO.setPageIndex(Constants.ONE);
      dynamicMDRDTO.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);
      validateBankSearchAndPaymentSchemeResponse(modelAndView, paymentSchemeRequest, bankRequest);
      Response dynamicMDRDTOlist = mdrService.searchDynamicMDR(dynamicMDRDTO);
      if (StringUtil.isListNotNullNEmpty(dynamicMDRDTOlist.getResponseList())) {
        session.setAttribute(Constants.DYNAMIC_MDR_MODEL, dynamicMDRDTO);
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, dynamicMDRDTOlist.getTotalNoOfRows());
        modelAndView.addObject(DYNAMIC_MDR_DTO_LIST, dynamicMDRDTOlist.getResponseList());
      }
      model.put(DYNAMIC_MDR_DTO, dynamicMDRDTO);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MDRController:: showDynamicMDRSearch method", e);
    }
    logger.error("Exiting:: MDRController:: showDynamicMDRSearch method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_DYNAMIC_MDR_SEARCH, method = RequestMethod.POST)
  public ModelAndView processDynamicMDRSearch(HttpServletRequest request,
      HttpServletResponse response, Map model, DynamicMDRDTO dynamicMDRDTO,
      BindingResult bindingResult, HttpSession session) {
    logger.info("Entering:: MDRController:: processDynamicMDRSearch method");

    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DYNAMIC_MDR_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    session.setAttribute(Constants.DYNAMIC_MDR_MODEL, dynamicMDRDTO);
    dynamicMDRDTO.setPageIndex(Constants.ONE);
    PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
    Bank bankRequest = new Bank();
    try {
      validateBankSearchAndPaymentSchemeResponse(modelAndView, paymentSchemeRequest, bankRequest);
      modelAndView.addObject("pageSize", dynamicMDRDTO.getPageSize());
      Response dynamicMDRDTOlist = mdrService.searchDynamicMDR(dynamicMDRDTO);
      if (StringUtil.isListNotNullNEmpty(dynamicMDRDTOlist.getResponseList())) {
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, dynamicMDRDTOlist.getTotalNoOfRows());
        modelAndView.addObject(DYNAMIC_MDR_DTO_LIST, dynamicMDRDTOlist.getResponseList());
      }
      model.put(DYNAMIC_MDR_DTO, dynamicMDRDTO);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MDRController:: processDynamicMDRSearch method", e);
    }
    logger.error("Exiting:: MDRController:: processDynamicMDRSearch method");
    return modelAndView;
  }

  @RequestMapping(value = DYNAMIC_MDR_SHOW_CREATE, method = RequestMethod.GET)
  public ModelAndView showDynamicMDRCreate(HttpServletRequest request, HttpServletResponse response,
      DynamicMDRDTO dynamicMDRDTO, BindingResult bindingResult, HttpSession session) {
    logger.info("Entering:: MDRController:: showDynamicMDRCreate method");
    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_CREATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DYNAMIC_MDR_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
    paymentSchemeRequest.setPageIndex(Constants.ONE);
    paymentSchemeRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

    Bank bankRequest = new Bank();
    bankRequest.setPageIndex(Constants.ONE);
    bankRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    try {
      validateBankSearchAndPaymentSchemeResponse(modelAndView, paymentSchemeRequest, bankRequest);

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MDRController:: showDynamicMDRCreate method", e);
    }
    return modelAndView;
  }

  private void validateBankSearchAndPaymentSchemeResponse(ModelAndView modelAndView,
		PaymentSchemeRequest paymentSchemeRequest, Bank bankRequest) throws ChatakAdminException {
	PaymentSchemeResponse paymentSchemeResponse =
          paymentSchemeService.searchPaymentScheme(paymentSchemeRequest);
      if (paymentSchemeResponse != null) {
        List<PaymentSchemeRequest> searchPaymentRequestList =
            paymentSchemeResponse.getPaymentSchemesRequest();
        modelAndView.addObject("searchPaymentRequestList", searchPaymentRequestList);
      }

      BankSearchResponse bankSearchResponse = bankService.searchBank(bankRequest);
      if (bankSearchResponse != null) {
        List<Bank> searchBankRequestList = bankSearchResponse.getBanks();
        modelAndView.addObject("bankResponse", searchBankRequestList);
      }
  }

  @RequestMapping(value = PROCESS_DYNAMIC_MDR_CREATE, method = RequestMethod.POST)
  public ModelAndView saveDynamicMDRCreate(HttpServletRequest request, HttpServletResponse response,
      DynamicMDRDTO dynamicMDRDTO, Map model, HttpSession session) {
    logger.info("Entering:: MDRController:: saveDynamicMDRCreate method");
    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DYNAMIC_MDR_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
      Bank bankRequest = new Bank();

      validateBankSearchAndPaymentSchemeResponse(modelAndView, paymentSchemeRequest, bankRequest);

      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      dynamicMDRDTO.setCreatedBy(userid.toString());
      DynamicMDRDTO dtoResponse = mdrService.saveOrUpdateDynamicMDR(dynamicMDRDTO);
      if (null != dtoResponse && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showDynamicMDRSearch(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.mdr.create.success", null, LocaleContextHolder.getLocale()));
        model.put(DYNAMIC_MDR_DTO, new DynamicMDRDTO());
      } else {
        modelAndView.setViewName(DYNAMIC_MDR_SHOW_CREATE_PAGE);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage("chatak.mdr.create.failure", null, LocaleContextHolder.getLocale()));
        model.put(DYNAMIC_MDR_DTO, dynamicMDRDTO);
      }
    } catch (Exception e) {
      logger.info("Exiting:: MDRController:: saveDynamicMDRCreate method",e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    return modelAndView;

  }

  @RequestMapping(value = DYNAMIC_MDR_SHOW_EDIT, method = RequestMethod.POST)
  public ModelAndView showMDRDynamicEdit(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getMDRId") final Long getMDRId, DynamicMDRDTO dynamicMDRDTO,
      HttpSession session) {
    logger.info("Entering:: MDRController:: showMDRDynamicEdit method");
    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_EDIT);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DYNAMIC_MDR_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
    paymentSchemeRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    paymentSchemeRequest.setPageIndex(Constants.ONE);

    Bank bankRequest = new Bank();
    bankRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    bankRequest.setPageIndex(Constants.ONE);

    try {
      validateBankSearchAndPaymentSchemeResponse(modelAndView, paymentSchemeRequest, bankRequest);

      DynamicMDRDTO dynamicMDR = mdrService.searchMDRById(getMDRId);
      if (null != dynamicMDR) {
        modelAndView.addObject(DYNAMIC_MDR_DTO, dynamicMDR);
      }

    } catch (Exception e) {
      logger.info("exiting:: MDRController:: showMDRDynamicEdit method",e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }

    return modelAndView;
  }

  @RequestMapping(value = PROCESS_DYNAMIC_MDR_UPDATE, method = RequestMethod.POST)
  public ModelAndView updateDynamicMDR(HttpServletRequest request, HttpServletResponse response,
      @FormParam("id") final Long id, DynamicMDRDTO dynamicMDRDTO, Map model, HttpSession session) {
    logger.info("Entering:: MDRController:: updateDynamicMDR method");
    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_SEARCH);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DYNAMIC_MDR_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      dynamicMDRDTO.setCreatedBy(userid.toString());
      dynamicMDRDTO.setId(id);
      DynamicMDRDTO dtoResponse = mdrService.saveOrUpdateDynamicMDR(dynamicMDRDTO);
      if (null != dtoResponse && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showDynamicMDRSearch(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.mdr.update.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_MDR1, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.info("Exiting:: MDRController:: updateDynamicMDR method",e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }

  @RequestMapping(value = DYNAMIC_MDR_PAGINATION, method = RequestMethod.POST)
  public ModelAndView dynamicMDRPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: MDRController:: dynamicMDRPagination method");
    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_SEARCH);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      Response response = null;
      PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
      Bank bankRequest = new Bank();
      validateBankSearchAndPaymentSchemeResponse(modelAndView, paymentSchemeRequest, bankRequest);
      DynamicMDRDTO dynamicMDRDTO =
          (DynamicMDRDTO) session.getAttribute(Constants.DYNAMIC_MDR_MODEL);
      model.put(DYNAMIC_MDR_DTO, dynamicMDRDTO);
      dynamicMDRDTO.setCreatedBy(userid.toString());
      dynamicMDRDTO.setPageIndex(pageNumber);
      dynamicMDRDTO.setNoOfRecords(totalRecords);
      response = mdrService.searchDynamicMDR(dynamicMDRDTO);
      List list = response.getResponseList();
      if (StringUtil.isListNotNullNEmpty(list)) {
        modelAndView.addObject("pageSize", dynamicMDRDTO.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber, totalRecords);
        modelAndView.addObject(DYNAMIC_MDR_DTO_LIST, list);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MDRController:: dynamicMDRPagination method", e);
    }
    logger.info("Exiting:: MDRController:: dynamicMDRPagination method");
    return modelAndView;
  }

  @RequestMapping(value = DOWNLOAD_DYNAMIC_MDR_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadDynamicMDRReport(HttpSession session, Map model,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering:: MDRController:: downloadDynamicMDRReport method");
    ModelAndView modelAndView = new ModelAndView(DYNAMIC_MDR_SHOW_SEARCH);
    DynamicMDRDTO dynamicMDRDTO = null;
    Response dynamicMDRResponse = null;
    try {
      dynamicMDRDTO = (DynamicMDRDTO) session.getAttribute(Constants.DYNAMIC_MDR_MODEL);
      dynamicMDRDTO.setPageIndex(downLoadPageNumber);
      Integer pageSize = dynamicMDRDTO.getPageSize();
      if (downloadAllRecords) {
        dynamicMDRDTO.setPageIndex(Constants.ONE);
        dynamicMDRDTO.setPageSize(totalRecords);
      }
      dynamicMDRResponse = mdrService.searchDynamicMDR(dynamicMDRDTO);
      List<DynamicMDRDTO> dynamicMDRResponseList =
          (List<DynamicMDRDTO>) dynamicMDRResponse.getResponseList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(dynamicMDRResponseList)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadDynamicMDRReport(dynamicMDRResponseList, exportDetails); 
      ExportUtil.exportData(exportDetails, response, messageSource);
      }
      dynamicMDRDTO.setPageSize(pageSize);
    } catch (Exception e) {
      logger.error("ERROR:: MDRController:: downloadDynamicMDRReport method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MDRController:: downloadDynamicMDRReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadDynamicMDRReport(List<DynamicMDRDTO> dynamicMDRDTO,
      ExportDetails exportDetails) {
    exportDetails.setReportName("MDRDynamicBin");
    exportDetails.setHeaderMessageProperty("chatak.header.mdr.messages");

    exportDetails.setHeaderList(getDynamicMDRHeaderList());
    exportDetails.setFileData(getDynamicMDRFileData(dynamicMDRDTO));
  }

  private List<String> getDynamicMDRHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("bin-file-exportutil-binnumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-dynamic-MDR-create-page.label.paymentscheme", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-dynamic-MDR-create-page.label.bankname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-dynamic-MDR-create-page.label.accounttype", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-dynamic-MDR-create-page.label.producttype", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-dynamic-MDR-create-page.label.transactiontype", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("commission-program-create.label.slab", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getDynamicMDRFileData(List<DynamicMDRDTO> dynamicMDRDTO) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (DynamicMDRDTO mdrDynamicBinData : dynamicMDRDTO) {

      Object[] rowData =
          {mdrDynamicBinData.getBinNumber().toString(), mdrDynamicBinData.getPaymentSchemeName(),
              mdrDynamicBinData.getBankName(), mdrDynamicBinData.getAccountType(),
              mdrDynamicBinData.getProductType(), mdrDynamicBinData.getTransactionType(),
              mdrDynamicBinData.getSlab() != null ? mdrDynamicBinData.getSlab() : " "

          };
      fileData.add(rowData);
    }

    return fileData;
  }

}
