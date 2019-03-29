package com.chatak.acquirer.admin.controller;

import java.io.Serializable;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CurrenyValue;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.util.Constants;


@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
public class CurrencyController implements URLMappingConstants {

  @Autowired
  MessageSource messageSource;

  private static Logger logger = Logger.getLogger(CurrencyController.class);

  @Autowired
  CurrencyConfigService currencyConfigService;

  @RequestMapping(value = CHATAK_ADMIN_SHOW_CURRENCY_PAGE, method = RequestMethod.GET)
  public ModelAndView showCurrencySearchPage(HttpServletRequest request,
      HttpServletResponse response, CurrencyDTO currencyDTO, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: CurrencyController:: showCurrencySchemePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      currencyDTO.setPageIndex(Constants.ONE);
      currencyDTO.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);
    } catch (Exception exception) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: CurrencyController :: showCurrencySearchPage method", exception);
    }
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: CurrencyController:: showCurrencySearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CURRENCY_SEARCH_ACTION, method = RequestMethod.POST)
  public ModelAndView searchCurrencyAccount(HttpServletRequest request,
      HttpServletResponse response, CurrencyDTO currencyDTO, Map model, HttpSession session)
      throws ChatakAdminException {
    logger.info("Entering:: CurrencyController:: searchcurrencyAccount method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      Response currencyDTOlist = currencyConfigService.searchCurrencyConfig(currencyDTO);
      if (StringUtil.isListNotNullNEmpty(currencyDTOlist.getResponseList())) {
        session.setAttribute(Constants.CURRENCY_MODEL, currencyDTO);
        modelAndView.addObject("currencyDTOlist", currencyDTOlist.getResponseList());
      }
      modelAndView =
              PaginationUtil.getPagenationModel(modelAndView, currencyDTOlist.getTotalNoOfRows());
      model.put(Constants.CURRENCY_DTO, currencyDTO);
      modelAndView.addObject(Constants.CURRENCY_MODEL, currencyDTO);
    } catch (Exception exception) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: CurrencyController :: showCurrencySearchPage method", exception);
    }
    logger.info("Exiting:: CurrencyController:: searchcurrencyAccount method");
    modelAndView.addObject("flag", true);
    return modelAndView;
  }
  
	@RequestMapping(value = CHATAK_ADMIN_CURRENCY_SEARCH_ACTION, method = RequestMethod.GET)
	public ModelAndView searchCurrencyAccountGetMethod(HttpServletRequest request, HttpServletResponse response,
			CurrencyDTO currencyDTO, Map model, HttpSession session) throws ChatakAdminException {
		logger.info("Entering:: CurrencyController:: searchcurrencyAccount method");
		BindingResult bindingResult = null;
		ModelAndView modelAndView = showCurrencySearchPage(request, response, currencyDTO, bindingResult, model, session);
		logger.info("Exiting:: CurrencyController:: searchcurrencyAccount method");
		return modelAndView;
	}

  @RequestMapping(value = CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateCurrencyPage(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering:: CurrencyController:: showCreateCurrencyPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    List<Option> currencyList = currencyConfigService.getAllCurrencies();
    modelAndView.addObject("currencyNameList", currencyList);

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute("currencies", new ArrayList(currencyList));

    CurrencyDTO currencyDTO = new CurrencyDTO();
    model.put("currencyDTO", currencyDTO);
    logger.info("Exiting:: CurrencyController:: showCreateCurrencyPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CURRENCY_CREATE_PAGE, method = RequestMethod.POST)
  public ModelAndView createCurrencyPage(HttpServletRequest request, HttpServletResponse response,
      CurrencyDTO currencyDTO, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: CurrencyController:: createCurrencyPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      currencyDTO.setCreatedBy(userid.toString());
      CurrencyDTO dtoResponse = currencyConfigService.saveCurrencyConfig(currencyDTO);
      if (null != dtoResponse && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showCurrencySearchPage(request, response, new CurrencyDTO(), bindingResult,
            model, session);
        modelAndView.addObject(Constants.SUCESS, dtoResponse.getErrorMessage());
        model.put(Constants.CURRENCY_DTO, new CurrencyDTO());
      } else if (dtoResponse != null) {
        modelAndView.setViewName(CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE);
        modelAndView.addObject(Constants.ERROR, dtoResponse.getErrorMessage());
        model.put(Constants.CURRENCY_DTO, currencyDTO);
        List<Option> currencyList = (List<Option>) session.getAttribute("currencies");
        modelAndView.addObject("currencyNameList", currencyList);
      }
    } catch (Exception exception) {
      logger.error("ERROR:: CurrencyController:: createCurrencyPage method", exception);
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          "chatak.acquirer.createcurrency.error.message", null, LocaleContextHolder.getLocale()));
    }

    logger.info("Exiting:: CurrencyController:: createCurrencyPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_CURRENCY, method = RequestMethod.POST)
  public ModelAndView showEditCurrency(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getId") final Long getId, HttpSession session, Map model) {
    logger.info("Entering :: CurrencyController :: showEditCurrency method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_EDIT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      getCurrencyDetails(getId, modelAndView);
    } catch (Exception e) {
      logger.error("ERROR:: CurrencyController:: showEditCurrency method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: CurrencyController :: showEditCurrency");
    return modelAndView;
  }

  private ModelAndView getCurrencyDetails(final Long getId, ModelAndView modelAndView)
      throws ChatakAdminException {
    CurrencyDTO currencyDTO = currencyConfigService.getCurrencyConfigById(getId);
    if (null != currencyDTO) {
      modelAndView.addObject(Constants.CURRENCY_DTO, currencyDTO);
    }
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_VIEW_CURRENCY, method = RequestMethod.POST)
  public ModelAndView showViewCurrency(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getId") final Long getId, HttpSession session, Map model) {
    logger.info("Entering :: CurrencyController :: showEditCurrency method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_VIEW_CURRENCY);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      getCurrencyDetails(getId, modelAndView);
    } catch (Exception exception) {
      logger.error("ERROR:: CurrencyController:: showViewCurrency method", exception);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: CurrencyController :: showViewCurrency");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_CURRENCY, method = RequestMethod.POST)
  public ModelAndView updateCurrency(HttpServletRequest request, HttpServletResponse response,
      CurrencyDTO currencyDTO, BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: CurrencyController :: updateCurrency method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      currencyDTO.setCreatedBy(userid.toString());
      CurrencyDTO dtoResponse = currencyConfigService.updateCurrencyConfig(currencyDTO);
      if (null != dtoResponse && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showCurrencySearchPage(request, response, new CurrencyDTO(), bindingResult,
            model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.updatecurrency.update.message", null,
                LocaleContextHolder.getLocale()));
        model.put(Constants.CURRENCY_DTO, new CurrencyDTO());

      } else if (null != dtoResponse) {
        modelAndView.addObject(Constants.ERROR, dtoResponse.getErrorMessage());
      }
    } catch (Exception exception) {
      logger.info("Exiting:: CurrencyController:: updateCurrency method", exception);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting::  CurrencyController:: updateCurrency method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_ADMIN_DELETE_CURRENCY, method = RequestMethod.POST)
  public ModelAndView deleteCurrency(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getDeleteId") final Long getDeleteId, CurrencyDTO currencyDTO,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: CurrencyController :: deleteCurrency method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      CurrencyDTO currencyDelete = currencyConfigService.deleteCurrencyConfig(getDeleteId);
      if (null != currencyDelete
          && currencyDelete.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView.addObject(Constants.SUCESS, currencyDelete.getErrorMessage());
      } else if (currencyDelete != null) {
        modelAndView.addObject(Constants.ERROR, currencyDelete.getErrorMessage());
      }
      modelAndView.addObject("flag", false);

    } catch (Exception exception) {
      logger.info("CurrencyController:: deleteCurrency method", exception);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject(Constants.CURRENCY_DTO, new CurrencyDTO());
    logger.info("Exiting :: CurrencyController :: deleteCurrency method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_GET_CURRENCY_DATA, method = RequestMethod.GET)
  public @ResponseBody String getCurrencyBycurrencyName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: CurrencyController :: getCurrencyBycurrencyName method");
    String currencyName = request.getParameter("currencyName");

    try {
      Response currencyResponse = currencyConfigService.findByCurrencyName(currencyName);
      if (currencyResponse != null) {
        return JsonUtil.convertObjectToJSON(currencyResponse);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: CurrencyController:: getCurrencyBycurrencyName method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: CurrencyController:: getCurrencyBycurrencyName method");
    return null;
  }

  @RequestMapping(value = CHATAK_ADMIN_CURRENCY_ACTIVATION_SUSPENTION, method = RequestMethod.POST)
  public ModelAndView changeCurrencyStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long currencyId,
      @FormParam("suspendActiveStatus") final String currencyStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: CurrencyController:: changeCurrencyStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CURRENCY_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      CurrencyDTO currencyDTO = new CurrencyDTO();
      currencyDTO.setId(currencyId);
      currencyDTO.setReason(reason);
      Response currencyResponse =
          currencyConfigService.changeCurrencyStatus(currencyDTO, currencyStatus);
      modelAndView = searchCurrencyAccount(request, response, currencyDTO, model, session);
      if (ActionErrorCode.ERROR_CODE_00.equals(currencyResponse.getErrorCode())) {
        model.put(Constants.SUCESS, messageSource.getMessage(
            "chatak.acquirer.updatecurrency.status.sucess", null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: CurrencyController:: changeCurrencyStatus method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: CurrencyController:: changeCurrencyStatus method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_PAGINATION_CURRENCY, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering :: CurrencyController :: getPaginationList method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);
    try {
      CurrencyDTO currencyDTO = (CurrencyDTO) session.getAttribute(Constants.CURRENCY_MODEL);
      model.put(currencyDTO, currencyDTO);
      currencyDTO.setPageIndex(pageNumber);
      currencyDTO.setPageSize(totalRecords);
      Response currencyDTOlist = currencyConfigService.searchCurrencyConfig(currencyDTO);
      if (StringUtil.isListNotNullNEmpty(currencyDTOlist.getResponseList())) {
        session.setAttribute(Constants.CURRENCY_MODEL, currencyDTO);
        modelAndView.addObject(PGConstants.PAGE_SIZE, currencyDTO.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            currencyDTOlist.getTotalNoOfRows());
        session.setAttribute("pageNumber", pageNumber);
        session.setAttribute(PGConstants.TOTAL_RECORDS, totalRecords);
      }
      modelAndView.addObject(Constants.CURRENCY_MODEL, currencyDTO);
    } catch (Exception e) {
      logger.error("ERROR:: CurrencyController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: CurrencyController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_DOWNLOAD_CURRENCY, method = RequestMethod.POST)
  public ModelAndView downloadCurrencyReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam("downloadType") final String downloadType,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: CurrencyController :: downloadCurrencyReport method ");
    Integer totalRecords = Integer.valueOf(request.getParameter(PGConstants.TOTAL_RECORDS));
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CURRENCY_SEARCH_PAGE);
    try {
      List<CurrenyValue> list;
      CurrencyDTO currencyDTO = (CurrencyDTO) session.getAttribute(Constants.CURRENCY_MODEL);
      currencyDTO.setPageIndex(downLoadPageNumber);
      currencyDTO.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      if (downloadAllRecords) {
        currencyDTO.setPageIndex(Constants.ONE);
        currencyDTO.setPageSize(totalRecords);
      }
      Response currencyDTOlist = currencyConfigService.searchCurrencyConfig(currencyDTO);
      list = (List<CurrenyValue>) currencyDTOlist.getResponseList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(currencyDTOlist.getResponseList())) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadCurrencyReport(list, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);

      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SwitchController:: downloadSwitchReport method", e);
    }
    logger.info("Exiting:: SwitchController:: downloadSwitchReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadCurrencyReport(List<CurrenyValue> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Currency_");
    exportDetails.setHeaderMessageProperty("chatak.header.currency.messages");

    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(list));
  }

  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("currency-search-page.label.currencyname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencyCodeAlpha", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycodenumeric", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-create-page.label.currencyexponent", null,
                LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<CurrenyValue> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (CurrenyValue currencyDTO : list) {

      Object[] rowData = {currencyDTO.getCurrencyName(), currencyDTO.getCurrencyCodeAlpha(),
          Long.parseLong(currencyDTO.getCurrencyCodeNumeric()),currencyDTO.getCurrencyExponent(), currencyDTO.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
