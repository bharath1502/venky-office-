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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.BankSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.Bank;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class BankController implements URLMappingConstants {

  @Autowired
  private MessageSource messageSource;

  private static Logger logger = Logger.getLogger(UserManagementController.class);

  @Autowired
  private BankService bankService;

  @Autowired
  private CurrencyConfigService currencyConfigService;

  @RequestMapping(value = BANK_CREATE, method = RequestMethod.GET)
  public ModelAndView showCreateBankPage(Map model, HttpSession session) {
    logger.info("Entering:: BankController:: showCreateBankPage method");
    ModelAndView modelAndView = new ModelAndView(BANK_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    Bank bank = new Bank();
    try {

      List<Option> countryList = bankService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);

      List<Option> currencyList = currencyConfigService.getCurrencyConfigCode();
      modelAndView.addObject("currencyList", currencyList);
      session.setAttribute("currencyList", currencyList);

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BankController:: showCreateBankPage method", e);
    }

    model.put("bank", bank);
    logger.info("Exiting:: BankController:: showCreateBankPage method");
    return modelAndView;

  }

  @RequestMapping(value = BANK_CREATE, method = RequestMethod.POST)
  public ModelAndView createBank(HttpServletRequest request, HttpServletResponse response,
      Bank bank, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BankController:: createBank method");
    ModelAndView modelAndView = new ModelAndView(BANK_CREATE);

    DataBinder dataBinder = new DataBinder(new Bank());
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BANK_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long createdBy = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      bank.setCreatedBy(String.valueOf(createdBy));


      String currencyCodeAlpha = bank.getCurrencyCodeAlpha();
      Response responseval = currencyConfigService.getCurrencyCodeNumeric(currencyCodeAlpha);
      bank.setCurrencyId(responseval.getCurrencyId());

      List<Option> countryList = bankService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);

      Response stateList = bankService.getStatesByCountry(bank.getCountry());
      modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
      session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
      modelAndView.addObject("bank", bank);

      BankResponse bankResponse = bankService.createBank(bank);
      if (null != bankResponse
          && bankResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_B0)) {
        modelAndView = searchBank(request, response, new Bank(), dataBinder.getBindingResult(),
            model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_B0, null, LocaleContextHolder.getLocale()));
        model.put("bank", new Bank());
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_B1, null, LocaleContextHolder.getLocale()));
        model.put("bank", bank);
      }
    } catch (Exception e) {
      logger.error("ERROR:: BankController:: createBank method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BankController:: createBank method");
    return modelAndView;
  }

  @RequestMapping(value = BANK_SEARCH, method = RequestMethod.GET)
  public ModelAndView searchBank(HttpServletRequest request, HttpServletResponse response,
      Bank bank, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BankController:: searchBank method");
    ModelAndView modelAndView = new ModelAndView(BANK_SEARCH);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.BANK_MODEL, bank);
    bank.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    bank.setPageIndex(Constants.ONE);
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: BankController:: searchBank method");
    return modelAndView;
  }

  @RequestMapping(value = BANK_SEARCH, method = RequestMethod.POST)
  public ModelAndView searchBankList(HttpServletRequest request, HttpServletResponse response,
      Bank bank, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BankController:: searchBank method");
    ModelAndView modelAndView = new ModelAndView(BANK_SEARCH);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.BANK_MODEL, bank);
    bank.setPageIndex(Constants.ONE);
    try {
      BankSearchResponse searchResponse = bankService.searchBank(bank);
      List<Bank> banks = new ArrayList<>();
      if (searchResponse != null) {
        banks = searchResponse.getBanks();
        modelAndView.addObject("pageSize", bank.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
      }
      modelAndView.addObject("banks", banks);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BankController:: searchBank method", e);
    }
    modelAndView.addObject("flag", true);
    logger.info("Exiting:: BankController:: searchBank method");
    return modelAndView;
  }

  @RequestMapping(value = BANK_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: BankController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(BANK_SEARCH);
    try {
      Bank bank = (Bank) session.getAttribute(Constants.BANK_MODEL);
      model.put(Constants.BANK_MODEL, bank);
      bank.setPageIndex(pageNumber);
      bank.setNoOfRecords(totalRecords);

      BankSearchResponse searchResponse = bankService.searchBank(bank);
      List<Bank> banks = new ArrayList<>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getBanks())) {
        banks = searchResponse.getBanks();
        modelAndView.addObject("pageSize", bank.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
        session.setAttribute("pageNumber", pageNumber);
        session.setAttribute("totalRecords", totalRecords);
      }
      modelAndView.addObject("banks", banks);

    } catch (Exception e) {
      logger.error("ERROR:: BankController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BankController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = BANK_EDIT, method = RequestMethod.POST)
  public ModelAndView showEditBank(HttpServletRequest request, HttpServletResponse response,
      @FormParam("editBankName") final String editBankName, HttpSession session, Map model) {
    logger.info("Entering:: BankController:: showEditBank method");
    ModelAndView modelAndView = new ModelAndView(BANK_EDIT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BANK_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Bank bank = new Bank();
    DataBinder dataBinder = new DataBinder(new Bank());

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.BANK_MODEL, bank);
    try {
      List<Option> countryList = bankService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      bank.setBankName(editBankName);
      bank = bankService.findByBankName(bank);

      if (null != bank) {

        Response stateList = bankService.getStatesByCountry(bank.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());

        session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
        modelAndView.addObject("bank", bank);

      } else {
        throw new ChatakAdminException();
      }

    } catch (ChatakAdminException e) {
      modelAndView =
          searchBank(request, response, new Bank(), dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BankController:: showEditBank method", e);
    }

    logger.info("Exiting:: BankController:: showEditBank method");
    return modelAndView;

  }

  @RequestMapping(value = BANK_VIEW, method = RequestMethod.POST)
  public ModelAndView showViewBank(HttpServletRequest request, HttpServletResponse response,
      @FormParam("bankViewName") final String bankViewName, HttpSession session, Map model) {
    logger.info("Entering:: BankController:: showViewBank method");
    ModelAndView modelAndView = new ModelAndView(BANK_VIEW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BANK_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Bank bank = new Bank();
    DataBinder dataBinder = new DataBinder(new Bank());
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.BANK_MODEL, bank);
    try {
      bank.setBankName(bankViewName);
      bank = bankService.findByBankName(bank);

      if (null != bank) {
        modelAndView.addObject(Constants.BANK_MODEL, bank);
      } else {
        throw new ChatakAdminException();
      }

    } catch (ChatakAdminException e) {
      modelAndView =
          searchBank(request, response, new Bank(), dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BankController:: showViewBank method", e);
    } catch (Exception e) {
      modelAndView =
          searchBank(request, response, new Bank(), dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BankController:: showViewBank method", e);
    }

    logger.info("Exiting:: BankController:: showViewBank method");
    return modelAndView;

  }

  @RequestMapping(value = BANK_DELETE, method = RequestMethod.POST)
  public ModelAndView deleteBank(HttpServletRequest request, HttpServletResponse response,
      @FormParam("deleteBankName") final String deleteBankName, Bank bankData,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: BankController :: deleteBank method ");
    ModelAndView modelAndView = new ModelAndView(BANK_SEARCH);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BANK_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      BankResponse bankResponse = bankService.deleteBankByName(deleteBankName);
      if (null != bankResponse
          && bankResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_B2)) {
        modelAndView.addObject(Constants.SUCESS, bankResponse.getErrorMessage());
      } else if (bankResponse != null) {
        modelAndView.addObject(Constants.ERROR, bankResponse.getErrorMessage());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BankController:: deleteBank method", e);
    }
    modelAndView.addObject("flag", false);
    logger.info("EXITING :: BankController :: deleteBank");
    return modelAndView;
  }

  @RequestMapping(value = BANK_UPDATE, method = RequestMethod.POST)
  public ModelAndView updateBank(HttpServletRequest request, HttpServletResponse response,
      Bank bank, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BankController:: updateBank method");
    ModelAndView modelAndView = new ModelAndView(BANK_EDIT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BANK_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    DataBinder dataBinder = new DataBinder(new Bank());
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long updatedBy = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      bank.setUpdatedBy(String.valueOf(updatedBy));
      List<Option> countryList = bankService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);


      BankResponse bankResponse = bankService.updateBank(bank);
      if (null != bankResponse
          && bankResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_B4)) {
        modelAndView = searchBank(request, response, new Bank(), dataBinder.getBindingResult(),
            model, session);

        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_B4, null, LocaleContextHolder.getLocale()));
        model.put(Constants.BANK_MODEL, new Bank());

      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_B5, null, LocaleContextHolder.getLocale()));
        model.put(Constants.BANK_MODEL, bank);
      }
    } catch (Exception e) {
      logger.error("ERROR:: BankController:: updateBank method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BankController:: updateBank method");
    return modelAndView;
  }

  @RequestMapping(value = GET_STATES_BY_COUNTRY_ID, method = RequestMethod.GET)
  public @ResponseBody String getStatesById(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(BANK_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: BankController :: getStatesById method");
    String country = request.getParameter("countryid");
    try {
      Response response2 = bankService.getStatesByCountry(country);
      if (response2 != null) {
        return JsonUtil.convertObjectToJSON(response2);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: BankController:: getStatesById method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BankController:: getStatesById method");
    return null;
  }

  @RequestMapping(value = GET_BANK_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadBankList(HttpSession session, HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: BankController:: downloadBankList method");
    BankSearchResponse bankSearchResponse = null;
    Bank bank = null;
    List<Bank> list = null;
    try {
      bank = (Bank) session.getAttribute(Constants.BANK_MODEL);
      bank.setPageIndex(downLoadPageNumber);
      Integer pageSize = bank.getPageSize();
      if (downloadAllRecords) {
        bank.setPageIndex(Constants.ONE);

        bank.setPageSize(totalRecords);
      }
      bankSearchResponse = bankService.searchBank(bank);
      list = bankSearchResponse.getBanks();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadRoleReport(list, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
      bank.setPageSize(pageSize);
    } catch (Exception e) {
      logger.error("ERROR:: BankController:: downloadBankList method", e);
    }
    logger.info("Exiting:: BankController:: downloadBankList method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<Bank> bankList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("BankList");
    exportDetails.setHeaderMessageProperty("chatak.header.bank.messages");

    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(bankList));
  }

  @RequestMapping(value = BANK_STATUS_CHANGE, method = RequestMethod.POST)
  public ModelAndView changeBankStatus(final HttpSession session,
      @FormParam("suspendActiveId") final String bankName,
      @FormParam("suspendActiveStatus") final String bankStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: BankController:: changeBankStatus method");

    ModelAndView modelAndView = new ModelAndView(BANK_SEARCH);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      Bank bank = new Bank();
      bank.setBankName(bankName);
      bank.setStatus(bankStatus);
      bank.setReason(reason);
      BankResponse bankResponse = bankService.changeBankStatus(bank);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute("pageNumber"),
          (Integer) session.getAttribute("totalRecords"), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(bankResponse.getErrorCode())) {
        model.put(Constants.SUCESS, bankResponse.getErrorMessage());
      } else {
        model.put(Constants.ERROR, bankResponse.getErrorMessage());
      }
    } catch (Exception e) {
      logger.error("ERROR:: BankController:: changeBankStatus method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BankController:: changeBankStatus method");
    return modelAndView;
  }
  
  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("bank-file-exportutil-bankname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("bank-file-exportutil-bankshortname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("bank-file-exportutil-acquirerid", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<Bank> bankList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (Bank bankData : bankList) {

      Object[] rowData = {bankData.getBankName(), bankData.getBankShortName(),
          bankData.getCurrencyCodeAlpha(), bankData.getAcquirerId(), bankData.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
