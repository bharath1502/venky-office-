package com.chatak.acquirer.admin.controller;

import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantAccountSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.user.bean.MerchantDetailsForAccountResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
@Controller
public class MerchantAccountController implements URLMappingConstants {
  private static Logger logger = Logger.getLogger(MerchantAccountController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantAccountService merchantAccountService;

  @Autowired
  private BankService bankService;

  @Autowired
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Autowired
  private CurrencyConfigService currencyConfigService;
  
  @Autowired
  private MerchantUpdateService merchantUpdateService;
  
  @Autowired
  private MerchantValidateService merchantValidateService;

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE, method = RequestMethod.GET)
  public ModelAndView showMerchantAccountInitialCreatePage(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE);

    String entityType = request.getParameter("entityType");
    if (!StringUtil.isNullAndEmpty(entityType) && entityType.equals(PGConstants.MERCHANT)) {
      merchant.setMerchantType(PGConstants.MERCHANT);
    } else if (!StringUtil.isNullAndEmpty(entityType)) {
      merchant.setMerchantType(PGConstants.SUB_MERCHANT);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      validateMerchantDetailsForAccountResponse(merchant, modelAndView);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    modelAndView.addObject(Constants.MERCHANT, merchant);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_CREATE_PAGE, method = RequestMethod.POST)
  public ModelAndView showMerchantAccountCreatePage(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_CREATE);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    merchant.setMerchantCode(merchant.getMerchantCode());
    merchant.setCategory(PGConstants.SECONDARY_ACCOUNT);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);

      merchantAccountService.getMerchantConfigDetailsForAccountCreate(merchant);

      setListOfOption(session, modelAndView);

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    modelAndView.addObject(Constants.MERCHANT, merchant);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_CREATE, method = RequestMethod.POST)
  public ModelAndView createMerchantAccount(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_ACCOUNT_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);

    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);

      setListOfOption(session, modelAndView);

      Long loggedInUserId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      merchant.setCategory(PGConstants.SECONDARY_ACCOUNT);
      Response accountCreateResponse =
          merchantAccountService.createMerchantAccount(merchant, loggedInUserId);
      if (null != accountCreateResponse) {

        if (merchant.getMerchantType().equals(PGConstants.MERCHANT)) {
          modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
              "chatak.merchant.account.create.success", null, LocaleContextHolder.getLocale()));
        } else {
          modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
              "chatak.merchant.account.create.success", null, LocaleContextHolder.getLocale()));
        }

        modelAndView.addObject(Constants.ERROR, null);
        session.setAttribute(Constants.ERROR, null);
        merchant.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
        merchant.setPageIndex(Constants.ONE);

        merchant.setMerchantCode(null);
        validateMerchantDetailsForAccountResponse(merchant, modelAndView);
      }
    } catch (Exception e) {

      modelAndView.setViewName(CHATAK_MERCHANT_ACCOUNT_CREATE);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.MERCHANT, merchant);
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE, method = RequestMethod.GET)
  public ModelAndView showMerchantAccountSearchPage(HttpServletRequest request,
      HttpServletResponse response, MerchantAccountSearchDto merchantAccountSearchDto,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_ACCOUNT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto);
    merchantAccountSearchDto.setMerchantType(PGConstants.MERCHANT);
    merchantAccountSearchDto.setPageSize(Constants.INITIAL_PAGE_SIZE_FOR_MERCHANT_ACCOUNT_SEARCH);
    merchantAccountSearchDto.setPageIndex(Constants.ONE);
    try {
      MerchantAccountSearchResponse searchResponse =
          merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, null);
      modelAndView.addObject("searchResponse", searchResponse);
      validateMerchantAccountSearchResponse(merchantAccountSearchDto, modelAndView, searchResponse);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    modelAndView.addObject("merchantAccountSearchDto", merchantAccountSearchDto);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE, method = RequestMethod.POST)
  public ModelAndView merchantAccountSearch(HttpServletRequest request,
      HttpServletResponse response, MerchantAccountSearchDto merchantAccountSearchDto,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_ACCOUNT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto);
    merchantAccountSearchDto.setPageIndex(Constants.ONE);
    try {
      MerchantAccountSearchResponse searchResponse =
          merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, null);
      modelAndView.addObject("searchResponse", searchResponse);
      validateMerchantAccountSearchResponse(merchantAccountSearchDto, modelAndView, searchResponse);
    } catch (Exception exp) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", exp);
    }
    modelAndView.addObject("merchantAccountSearchDto", merchantAccountSearchDto);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGINATION, method = RequestMethod.POST)
  public ModelAndView merchantAccountSearchPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("sortProperty") final String sortProperty, Map model) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE);
    MerchantAccountSearchDto merchantAccountSearchDto =
        (MerchantAccountSearchDto) session.getAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL);
    merchantAccountSearchDto.setPageIndex(pageNumber);
    merchantAccountSearchDto.setNoOfRecords(totalRecords);
    try {
      validateMerchantAccountSearchResponse(pageNumber, sortProperty, modelAndView,
			merchantAccountSearchDto);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  private void validateMerchantAccountSearchResponse(final Integer pageNumber, final String sortProperty,
		ModelAndView modelAndView, MerchantAccountSearchDto merchantAccountSearchDto) {
	MerchantAccountSearchResponse searchResponse =
          merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, null);
      modelAndView.addObject("searchResponse", searchResponse);
      modelAndView.addObject("sortProperty", (null != sortProperty) ? sortProperty : "");
      if (searchResponse != null
          && !CollectionUtils.isEmpty(searchResponse.getMerchantAccountSearchDtoList())) {
        modelAndView.addObject(Constants.PAGE_SIZE, merchantAccountSearchDto.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject("merchantAccountSearchDto", merchantAccountSearchDto);
  }


  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE, method = RequestMethod.POST)
  public ModelAndView merchantDetailsSearchForAccountCreation(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    merchant.setPageIndex(Constants.ONE);
    try {
      validateMerchantDetailsForAccountResponse(merchant, modelAndView);
    } catch (Exception exp) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", exp);
    }
    modelAndView.addObject(Constants.MERCHANT, merchant);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  private void validateMerchantDetailsForAccountResponse(Merchant merchant, ModelAndView modelAndView) {
	MerchantDetailsForAccountResponse merchantDetailsResponse =
          merchantAccountService.getMerchantDetailsForAccountCreation(merchant);
      modelAndView.addObject("merchantDetails", merchantDetailsResponse);
      if (merchantDetailsResponse != null
          && !CollectionUtils.isEmpty(merchantDetailsResponse.getMerchantDetailsList())) {
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        PaginationUtil.getPagenationModel(modelAndView,
            merchantDetailsResponse.getTotalRecords());
      }
  }

  @RequestMapping(value = CHATAK_MERCHANTDETAILS_ACCOUNT_CREATE_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView getMerchantDetailsPaginationListForMerchantAccountCreate(
      final HttpSession session, @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: MerchantController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE);
    try {
      Merchant merchant = (Merchant) session.getAttribute(Constants.MERCHANTS_MODEL);
      model.put(Constants.MERCHANT, merchant);
      merchant.setPageIndex(pageNumber);
      merchant.setNoOfRecords(totalRecords);
      merchant.setPageSize(merchant.getPageSize());

      MerchantDetailsForAccountResponse merchantDetailsResponse =
          merchantAccountService.getMerchantDetailsForAccountCreation(merchant);
      modelAndView.addObject("merchantDetails", merchantDetailsResponse);
      if (merchantDetailsResponse != null
          && !CollectionUtils.isEmpty(merchantDetailsResponse.getMerchantDetailsList())) {
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            merchantDetailsResponse.getTotalRecords());
      }
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchant);
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_STATUS_CHANGE, method = RequestMethod.POST)
  public ModelAndView changeAccountStatus(HttpServletRequest request, HttpServletResponse response,
      MerchantAccountSearchDto merchantAccountSearchDto, BindingResult bindingResult, Map model,
      HttpSession session) {
    ModelAndView modelAndView = new ModelAndView();

    try {
      merchantAccountService.changeAccountStatus(merchantAccountSearchDto);
      if (merchantAccountSearchDto.getMerchantType().equals(PGConstants.MERCHANT)) {
        modelAndView = showMerchantAccountSearchPage(request, response,
            new MerchantAccountSearchDto(), bindingResult, model, session);
        merchantAccountSearchDto.setMerchantType(PGConstants.MERCHANT);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.merchant.account.status.change.success", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView = showSubMerchantAccountSearchPage(request, response,
            new MerchantAccountSearchDto(), bindingResult, model, session);
        merchantAccountSearchDto.setMerchantType(PGConstants.SUB_MERCHANT);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.submerchant.account.status.change.success", null,
                LocaleContextHolder.getLocale()));
      }
      merchantAccountSearchDto.setAccountStatus(null);

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: downloadMerchantReport method", e);
    }
    logger.info("Exiting:: MerchantController:: downloadMerchantReport method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_EDIT, method = RequestMethod.POST)
  public ModelAndView updateMerchantAccount(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_EDIT);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      merchantAccountService.updateMerchantAccount(merchant);

      if (merchant.getMerchantType().equals(PGConstants.MERCHANT)) {
        modelAndView = showMerchantAccountSearchPage(request, response,
            new MerchantAccountSearchDto(), bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.merchant.account.update.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView = showSubMerchantAccountSearchPage(request, response,
            new MerchantAccountSearchDto(), bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.submerchant.account.update.success", null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    modelAndView.addObject(Constants.MERCHANT, merchant);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_EDIT_PAGE, method = RequestMethod.POST)
  public ModelAndView merchantAccountEditPage(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_EDIT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.SUCESS, null);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    try {
      merchant = merchantAccountService.getAccount(merchant);
      if (merchant != null) {
        merchantAccountService.getMerchantConfigDetailsForAccountCreate(merchant);
        List<Option> countryList = merchantUpdateService.getCountries();
        modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
        session.setAttribute(Constants.COUNTRY_LIST, countryList);

        Response stateList = merchantUpdateService.getStatesByCountry(merchant.getBankCountry());
        modelAndView.addObject("bankStateList", stateList.getResponseList());
        session.setAttribute("bankStateList", stateList.getResponseList());

        setListOfOption(session, modelAndView);
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    modelAndView.addObject(Constants.MERCHANT, merchant);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  private void setListOfOption(HttpSession session, ModelAndView modelAndView) throws ChatakAdminException {
	List<Option> options = merchantValidateService.getFeeProgramNames();
	modelAndView.addObject("feeprogramnames", options);
	session.setAttribute("feeprogramnames", options);

	List<Option> processorNames = merchantValidateService.getProcessorNames();
	modelAndView.addObject("processorNames", processorNames);
	session.setAttribute("processorNames", processorNames);
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_SEARCH_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadMerchantAccountReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: MerchantController:: downloadMerchantReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE);
    MerchantAccountSearchDto merchantAccountSearchDto = null;
    MerchantAccountSearchResponse searchResponse = null;
    try {
      merchantAccountSearchDto =
          (MerchantAccountSearchDto) session.getAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL);
      merchantAccountSearchDto.setPageIndex(downLoadPageNumber);
      Integer pageSize = merchantAccountSearchDto.getPageSize();
      if (downloadAllRecords) {
        merchantAccountSearchDto.setPageIndex(Constants.ONE);
        merchantAccountSearchDto.setPageSize(totalRecords);
      }
      searchResponse = merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, null);
      List<MerchantAccountSearchDto> list = searchResponse.getMerchantAccountSearchDtoList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadMerchantAccountReport(list, exportDetails); 
      ExportUtil.exportData(exportDetails, response, messageSource);
      }
      merchantAccountSearchDto.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: downloadMerchantReport method", e);
    }
    logger.info("Exiting:: MerchantController:: downloadMerchantReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadMerchantAccountReport(List<MerchantAccountSearchDto> merchantAccountData,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Merchant_Accounts_");
    exportDetails.setHeaderMessageProperty("chatak.header.merchant.account.messages");

    exportDetails.setHeaderList(getMerchantAccountHeaderList());
    exportDetails.setFileData(getMerchantAccountFileData(merchantAccountData));
  }

  @RequestMapping(value = DOWNLOAD_PENDING_NMAS, method = RequestMethod.POST)
  public void downloadPendingNmas(HttpSession session, HttpServletRequest request,
      @FormParam("merchantDownloadId") final Integer merchantDownloadId,
      HttpServletResponse response) {
    logger.info("Entering:: MerchantController:: downloadMerchantReport method");
    try {
      byte[] bFile = new byte[1];
      OutputStream out = response.getOutputStream();
      response.setContentType("APPLICATION/OCTET-STREAM");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + "DemoNmas.txt" + "\"");
      out.write(bFile);
      out.flush();
      out.close();
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: downloadMerchantReport method", e);
    }
    logger.info("Exiting:: MerchantController:: downloadMerchantReport method");
  }

  @RequestMapping(value = SHOW_ALL_PENDING_MERCHANTS, method = RequestMethod.GET)
  public ModelAndView showAllPendingMerchants(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam("totalRecords") final Integer totalRecords) {
    logger.info("Entering:: MerchantController:: showAllPendingMerchants method");
    ModelAndView modelAndView = new ModelAndView(SHOW_ALL_PENDING_MERCHANTS);
    List<Merchant> merchants = merchantUpdateService.getMerchantByStatusPendingandDecline();
    modelAndView.addObject("pendingMerchants", merchants);
    session.setAttribute("totalRecords", merchants.size());
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE, method = RequestMethod.GET)
  public ModelAndView showSubMerchantAccountSearchPage(HttpServletRequest request,
      HttpServletResponse response, MerchantAccountSearchDto merchantAccountSearchDto,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_ACCOUNT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto);
    merchantAccountSearchDto.setMerchantType(PGConstants.SUB_MERCHANT);
    merchantAccountSearchDto.setPageSize(Constants.INITIAL_PAGE_SIZE_FOR_MERCHANT_ACCOUNT_SEARCH);
    merchantAccountSearchDto.setPageIndex(Constants.ONE);
    try {
      Map<String, String> merchantDataMap =
          merchantAccountService.getMerchantMapByMerchantType(PGConstants.MERCHANT);
      modelAndView.addObject("merchantDataMap", merchantDataMap);
      session.setAttribute("merchantDataMap", merchantDataMap);

      MerchantAccountSearchResponse searchResponse =
          merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, merchantDataMap);
      modelAndView.addObject("searchResponse", searchResponse);
      validateMerchantAccountSearchResponse(merchantAccountSearchDto, modelAndView, searchResponse);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    modelAndView.addObject("merchantAccountSearchDto", merchantAccountSearchDto);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE, method = RequestMethod.POST)
  public ModelAndView searchSubMerchantAccount(HttpServletRequest request,
      HttpServletResponse response, MerchantAccountSearchDto merchantAccountSearchDto,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    ModelAndView modelAndView = new ModelAndView(CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_ACCOUNT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto);
    merchantAccountSearchDto.setPageIndex(Constants.ONE);
    try {

      Map<String, String> merchantDataMap =
          (Map<String, String>) session.getAttribute("merchantDataMap");

      if (null == merchantDataMap) {
        merchantDataMap = merchantAccountService.getMerchantMapByMerchantType(PGConstants.MERCHANT);
        session.setAttribute("merchantDataMap", merchantDataMap);
      }

      modelAndView.addObject("merchantDataMap", merchantDataMap);

      MerchantAccountSearchResponse searchResponse =
          merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, merchantDataMap);
      modelAndView.addObject("searchResponse", searchResponse);
      validateMerchantAccountSearchResponse(merchantAccountSearchDto, modelAndView, searchResponse);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    modelAndView.addObject("merchantAccountSearchDto", merchantAccountSearchDto);
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }

  private void validateMerchantAccountSearchResponse(MerchantAccountSearchDto merchantAccountSearchDto,
		ModelAndView modelAndView, MerchantAccountSearchResponse searchResponse) {
	if (searchResponse != null
          && !CollectionUtils.isEmpty(searchResponse.getMerchantAccountSearchDtoList())) {
        modelAndView.addObject(Constants.PAGE_SIZE, merchantAccountSearchDto.getPageSize());
        PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
  }
  
  private List<String> getMerchantAccountHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("merchant-file-exportutil-merchantName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-state", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-accountNumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-contactPerson", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-currentBalance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getMerchantAccountFileData(
      List<MerchantAccountSearchDto> merchantAccountData) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (MerchantAccountSearchDto merchantData : merchantAccountData) {

      Object[] rowData = {merchantData.getBusinessName(), merchantData.getBankState(),
          merchantData.getMerchantAccountNumber(), merchantData.getBankAccountName(),
          Double.parseDouble(merchantData.getCurrentBalance()), merchantData.getAccountStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
