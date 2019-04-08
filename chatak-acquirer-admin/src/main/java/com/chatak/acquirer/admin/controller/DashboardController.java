/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.controller.model.SettlementDataRequest;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.ProcessorConfig;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.IssSettlementDataDao;
import com.chatak.pg.acq.dao.PGParamsDao;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.acq.dao.model.PGParams;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 06-Jan-2015 8:48:01 PM
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class DashboardController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(MerchantController.class);

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private PGParamsDao paramsDao;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private BankService bankService;

  @Autowired
  private ResellerService resellerService;

  @Autowired
  private CurrencyConfigService currencyConfigService;

  @Autowired
  UserService userService;

  @Autowired
  MessageSource messageSource;
  @Autowired
  private MerchantValidateService merchantValidateService;
  
  @Autowired
  RoleController roleController;
  
  @Autowired
  private ProgramManagerService programManagerService;
  
  @Autowired
  private IsoService isoService;

  @Autowired
  private IssSettlementDataDao issSettlementDataDao;

  @PostConstruct
  private void loadConfiguration() {
    List<PGParams> pgParams = paramsDao.getAllPGParams();
    ProcessorConfig.setProcessorConfig(pgParams);
  }

  /**
   * Method to show login page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_HOME, method = RequestMethod.GET)
  public ModelAndView showLogin(HttpServletRequest request, Map model, HttpSession session) {
    logger.info("Entering:: DashboardController:: showLogin method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_HOME);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    LoginResponse loginResponse = (LoginResponse) session.getAttribute("loginResponse");
    if ("admin".equalsIgnoreCase(userType)) {
      modelAndView = showLoginCondition(session, modelAndView, existingFeature, userType);
    }
    List<Merchant> merchants = getMerchantsOnUserType(loginResponse);
    setMerchantSubList(modelAndView, merchants);
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionResponse transactionResponse = new TransactionResponse();
    List<AccountTransactionDTO> transactionList = new ArrayList<>();

    try {

      List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
      txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
      txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
      txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT);
      txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT);
      txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
      txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
      txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
      txnCodeList.add(AccountTransactionCode.FT_BANK);
      txnCodeList.add(AccountTransactionCode.FT_CHECK);
      transaction.setTransactionCodeList(txnCodeList);

      transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
      GetTransactionsListResponse processingTxnList =
          transactionService.searchAccountTransactions(transaction);

      transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      GetTransactionsListResponse executedTxnList = getExecutedTxnList(loginResponse, transaction);

      if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {

        int listSize = processingTxnList.getAccountTransactionList().size();
        transactionResponse
            .setAccountTxnList(listSize < Constants.TEN ? processingTxnList.getAccountTransactionList()
                : processingTxnList.getAccountTransactionList().subList(0, Constants.TEN));
        transactionResponse.setErrorCode(processingTxnList.getErrorCode());
        transactionResponse.setErrorMessage(processingTxnList.getErrorMessage());
        transactionResponse.setTotalNoOfRows(processingTxnList.getTotalResultCount());
        transactionList = transactionResponse.getAccountTxnList() != null
            ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
        modelAndView.addObject("processingListSize", listSize);
        session.setAttribute("processingListSize", listSize);
        modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
        session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
      }

      if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {

        int listSize = executedTxnList.getAccountTransactionList().size();
        transactionResponse
            .setAccountTxnList(listSize < Constants.TEN ? executedTxnList.getAccountTransactionList()
                : executedTxnList.getAccountTransactionList().subList(0, Constants.TEN));
        transactionList = transactionResponse.getAccountTxnList() != null
            ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
        modelAndView.addObject(PGConstants.EXECUTED_LIST_SIZE, listSize);
        session.setAttribute(PGConstants.EXECUTED_LIST_SIZE, listSize);
        modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
        session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
      }

    } catch (Exception e) {
      logger.error("ERORR:: DashboardController:: showLogin method",e);
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
    }
    model.put("transaction", new GetTransactionsListRequest());
    logger.info("Exiting:: DashboardController:: showLogin method");
    return modelAndView;
  }

  private GetTransactionsListResponse getExecutedTxnList(LoginResponse loginResponse,
      GetTransactionsListRequest transaction) {
    GetTransactionsListResponse executedTxnList = null;
    if (loginResponse.getUserType().equals(Constants.PM_USER_TYPE)
    	  || loginResponse.getUserType().equals(Constants.ISO_USER_TYPE)) {
      executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, loginResponse.getEntityId(), loginResponse.getUserType());
    } else {
      executedTxnList = transactionService.searchAccountTransactions(transaction);
    }
    return executedTxnList;
  }

  private List<Merchant> getMerchantsOnUserType(LoginResponse loginResponse) {
    List<Merchant> merchants = new ArrayList<>();
    if (loginResponse != null && loginResponse.getUserType().equals(PGConstants.ADMIN)) {
        merchants = merchantUpdateService.getMerchantByStatusPendingandDecline();
	} else if (loginResponse != null && loginResponse.getUserType().equals(PGConstants.PROGRAM_MANAGER_NAME)) {
		merchants = merchantUpdateService.getPmMerchantByEntityIdandEntityType(loginResponse.getEntityId(), loginResponse.getUserType());
	}
    return merchants;
  }

  private void setMerchantSubList(ModelAndView modelAndView, List<Merchant> merchants) {
    if (CommonUtil.isListNotNullAndEmpty(merchants) && merchants.size() > Constants.TEN) {
      modelAndView.addObject("merchantSubList", merchants.subList(0, Constants.TEN));
    } 
    else {
      modelAndView.addObject("merchantSubList", merchants);
    }
  }

  private ModelAndView showLoginCondition(HttpSession session, ModelAndView modelAndView,
      String existingFeature, String userType) {
    if ((!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DASHBOARD_FEATURE_ID)) || ("reseller".equalsIgnoreCase(userType)
            && !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_DASHBOARD_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    } 
    return modelAndView;
  }

  @RequestMapping(value = PENDING_MERCHANT_SHOW, method = RequestMethod.POST)
  public ModelAndView showViewSubMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("merchantViewId") final Long merchantViewId, HttpSession session, Map model) {
    logger.info("Entering :: MerchantController :: showViewSubMerchant method ");
    ModelAndView modelAndView = new ModelAndView(PENDING_MERCHANT_SHOW);
    Merchant merchant = new Merchant();
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    List<Option> processorNames = null;
    try {

      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject("countryList", countryList);
      merchant.setId(merchantViewId);
      merchant = merchantValidateService.getMerchant(merchant);
      if (null == merchant) {
        throw new ChatakAdminException();
      } else {
    	  validateMerchant(model, merchant);
        List<Option> options =
            merchantValidateService.getFeeProgramNamesForEdit(merchant.getFeeProgram());
        modelAndView.addObject("feeprogramnames", options);

        fetchState(session, modelAndView, merchant);

        String bankCurrencyCode = merchant.getBankCurrencyCode();
        Response currencyCodeAlpha = currencyConfigService.getcurrencyCodeAlpha(bankCurrencyCode);
        merchant.setCurrencyId(currencyCodeAlpha.getCurrencyCodeAlpha());

        Response agentnamesList = merchantUpdateService.getAgentNames(merchant.getLocalCurrency());
        if (agentnamesList != null) {
          modelAndView.addObject("agentnamesList", agentnamesList.getResponseList());
          session.setAttribute("agentnamesList", new ArrayList(agentnamesList.getResponseList()));
        }
        session.setAttribute("updateMerchantId", merchantViewId);
        merchant.setMerchantFlag(true);
        modelAndView.addObject("merchant", merchant);
        processorNames = merchantValidateService.getProcessorNames();
        List<Option> bankOptions = bankService.getBankData();
        modelAndView.addObject(PGConstants.BANKLIST, bankOptions);
        List<Option> resellerOptions = resellerService.getResellerData();
        modelAndView.addObject("resellerList", resellerOptions);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERORR:: DashboardController:: showViewSubMerchant method",e);
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
    }
    modelAndView.addObject("processorNames", processorNames);
    model.put("merchant", merchant);
    if(merchant != null) {
    	session.setAttribute("parentMerchantId", merchant.getParentMerchantId());
    }
    logger.info("EXITING :: MerchantController :: showViewSubMerchant");
    return modelAndView;
  }

private void validateMerchant(Map model, Merchant merchant) {
	try {
		MerchantResponse selectedCurrencyList = merchantUpdateService.findByMerchantId(merchant.getId());
		if (selectedCurrencyList != null) {
			merchant.setAssociatedTo(selectedCurrencyList.getMerchant().getAssociatedTo());
			if (merchant.getAssociatedTo() != null
					&& merchant.getAssociatedTo().equals(PGConstants.PROGRAM_MANAGER_NAME)) {
				 Response  programManagerResponse = programManagerService.findProgramManagerNameByCurrencyAndId(merchant.getId(),merchant.getLocalCurrency());
				model.put("selectedCardProgramList", selectedCurrencyList.getCardProgramRequests());
				model.put(PGConstants.SELECTED_ENTITY_LIST, selectedCurrencyList.getProgramManagerRequests());
				model.put(Constants.MERCHANT, selectedCurrencyList.getMerchant());
				model.put("EntityList", programManagerResponse.getResponseList());
			} else {
				Response  programManagerResponse = isoService.findIsoNameByCurrencyAndId(merchant.getId(), merchant.getLocalCurrency());
				model.put("selectedCardProgramList", selectedCurrencyList.getCardProgramRequests());
				model.put(PGConstants.SELECTED_ENTITY_LIST, selectedCurrencyList.getIsoRequests());
				model.put(Constants.MERCHANT, selectedCurrencyList.getMerchant());
				model.put("EntityList", programManagerResponse.getResponseList());
			}
		}
		} catch (InstantiationException e) {
			logger.error("ERORR:: DashboardController:: showViewSubMerchant method : InstantiationException",
					e);
		} catch (IllegalAccessException e) {
			logger.error("ERORR:: DashboardController:: showViewSubMerchant method :IllegalAccessException", e);
		}
}

  private void fetchState(HttpSession session, ModelAndView modelAndView, Merchant merchant) throws ChatakAdminException {
	Response stateList = merchantUpdateService.getStatesByCountry(merchant.getCountry());
	modelAndView.addObject("stateList", stateList.getResponseList());
	if(StringUtil.isListNotNullNEmpty(stateList.getResponseList())){
	session.setAttribute("stateList", new ArrayList(stateList.getResponseList()));
	}
	stateList = merchantUpdateService.getStatesByCountry(merchant.getBankCountry());
	modelAndView.addObject("bankStateList", stateList.getResponseList());
	if(StringUtil.isListNotNullNEmpty(stateList.getResponseList())){
	session.setAttribute("bankStateList", new ArrayList(stateList.getResponseList()));
	}
  }

  @RequestMapping(value = CHATAK_ADMIN_UNBLOCK_USERS, method = RequestMethod.GET)
  public ModelAndView showUnblockUsers(HttpServletRequest request, Map model, HttpSession session,
      GenericUserDTO userDataDto) {
	  logger.info("Entering :: DashboardController :: showUnblockUsers method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_UNBLOCK_USERS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_UNBLOCKUSERS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    roleController.getRoleListForRoles(session, model);
    modelAndView.addObject("flag", false);
    modelAndView.addObject(Constants.USERDATA_DTO, userDataDto);
    logger.info("Exiting :: DashboardController :: showUnblockUsers method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UNBLOCK_USERS_SEARCH, method = RequestMethod.POST)
  public ModelAndView searchAdminUser(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session, GenericUserDTO userDataDto, BindingResult bindingResult) {
	  logger.info("Entering :: DashboardController :: searchAdminUser method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_UNBLOCK_USERS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_UNBLOCKUSERS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
    	roleController.getRoleListForRoles(session, model);
      List<AdminUserDTO> adminUserList;
      if (userDataDto.getUserType().equals(PGConstants.ADMIN) 
    		  || userDataDto.getUserType().equals(Constants.PM_USER_TYPE)
    		  || userDataDto.getUserType().equals(Constants.ISO_USER_TYPE)) {
        adminUserList = userService.searchAdminUserList(userDataDto.getUserType());
      } else {
        adminUserList = userService.searchMerchantUserList();
      }
      if (null != adminUserList) {
        modelAndView.addObject("blockedUserList", adminUserList);
        modelAndView.addObject(PGConstants.TOTAL_RECORDS, adminUserList.size());
        session.setAttribute("blockedUserList", adminUserList);
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE,
            null, LocaleContextHolder.getLocale()));
      }

    } catch (Exception e) {
    	logger.error("Error :: DashboardController :: searchAdminUser method",e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE,
          null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject(Constants.USERDATA_DTO, userDataDto);
    model.put("userType", userDataDto.getUserType());
    logger.info("Exit :: DashboardController :: searchAdminUser method");
    return modelAndView;

  }
  
	@RequestMapping(value = CHATAK_ADMIN_UNBLOCK_USERS_SEARCH, method = RequestMethod.GET)
	public ModelAndView searchAdminUserGetMethod(HttpServletRequest request, HttpServletResponse response, Map model,
			HttpSession session, GenericUserDTO userDataDto, BindingResult bindingResult) {
		logger.info("Entering :: DashboardController :: searchAdminUser method");
		ModelAndView modelAndView = showUnblockUsers(request, model, session, userDataDto);
		logger.info("Exit :: DashboardController :: searchAdminUser method");
		return modelAndView;

	}

  @RequestMapping(value = CHATAK_ADMIN_DO_UNBLOCK_USERS, method = RequestMethod.POST)
  public ModelAndView unblockUser(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session, GenericUserDTO userDataDto) {
	  logger.info("Entering :: DashboardController :: unblockUser method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_UNBLOCK_USERS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_UNBLOCKUSERS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    String userName = request.getParameter("userName");
    String entityType = request.getParameter("entityType");
    Response responseval = new Response();
    try {
      if (userName != null && entityType.equals(PGConstants.ADMIN)
    		  || entityType.equals(Constants.PM_USER_TYPE)
    		  || entityType.equals(Constants.ISO_USER_TYPE)) {
      } else {
    	  responseval = userService.unblockAdminUser(userName);
        responseval = userService.unblockMerchantUser(userName);
      }
      if (responseval != null && responseval.getErrorCode().equals("00")) {
        modelAndView = showUnblockUsers(request, model, session, new GenericUserDTO());
        model.put(Constants.SUCESS, messageSource.getMessage("chatak.unblockuser.success.message",
            null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE,
            null, LocaleContextHolder.getLocale()));
      }

    } catch (ChatakAdminException e) {
    	logger.error("Error :: DashboardController :: unblockUser method",e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE,
          null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject(Constants.USERDATA_DTO, new GenericUserDTO());
    logger.info("Exit :: DashboardController :: unblockUser method");
    return modelAndView;
  }
  
	@PostMapping(value = FETCH_SETTLEMENT_DATA_BY_PMID)
	public ModelAndView showViewSettlementDetails(HttpServletRequest request, HttpServletResponse response,
			@FormParam("programViewId") final Long programViewId, @FormParam("batchDate") final Timestamp batchDate, HttpSession session, Map model) {
	    logger.info("Entering :: DashboardController :: showViewSettlementDetails :: Acquirer Programa manager id : " + programViewId);
		ModelAndView modelAndView = new ModelAndView(FETCH_SETTLEMENT_DATA_BY_PMID);
		modelAndView.addObject(Constants.ERROR, null);
		modelAndView.addObject(Constants.SUCESS, null);
		Long programId = (Long) session.getAttribute("programViewId");
		Timestamp date = (Timestamp) session.getAttribute("batchDate");
		try {
			
			if ((programId == null || programViewId != null) && (date == null || batchDate != null)) {
				programId = programViewId;
				date = batchDate;
			}
			
			List<PGIssSettlementData> list = issSettlementDataDao.findByAcqPmIdAndBatchDate(programId, date);
			SettlementDataRequest settlementDataRequest = new SettlementDataRequest();
			if (StringUtil.isListNotNullNEmpty(list)) {
				settlementDataRequest.setProgramManagerId(list.get(0).getAcqPmId());
				settlementDataRequest.setBatchDate(list.get(0).getBatchDate());
				settlementDataRequest.setTotalAmount(new BigDecimal(list.get(0).getTotalAmount()).divide(PGConstants.BIG_DECIMAL_HUNDRED));
				settlementDataRequest.setTotalTxnCount(list.get(0).getTotalTxnCount());
				settlementDataRequest.setProgramManagerName(list.get(0).getProgramManagerName());
                logger.info("selected Program manager details : " + settlementDataRequest.getProgramManagerId()
                    + settlementDataRequest.getProgramManagerName()
                    + settlementDataRequest.getTotalTxnCount() + settlementDataRequest.getTotalAmount());
			}
			model.put("settlementDataRequest", settlementDataRequest);
		} catch (Exception e) {
			logger.error("ERROR:: DashboardController:: showViewSettlementDetails method", e);
			model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
					LocaleContextHolder.getLocale()));
		}
		session.setAttribute("programViewId", programId);
		session.setAttribute("batchDate", date);
		logger.info("Exiting :: DashboardController :: showViewSettlementDetails");
		return modelAndView;
	}

	@RequestMapping(value = SHOW_ALL_PENDING_SETTLEMENT_DATA, method = RequestMethod.GET)
	public ModelAndView showAllPendingSettlementDetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords) {
		logger.info("Entering:: DashboardController:: showAllPendingSettlementDetails method");
		ModelAndView modelAndView = new ModelAndView(SHOW_ALL_PENDING_SETTLEMENT_DATA);
		List<PGIssSettlementData> list = null;
		 LoginResponse loginResponse = (LoginResponse) session.getAttribute("loginResponse");
		if(loginResponse != null && loginResponse.getUserType().equals(PGConstants.ADMIN)){
		     list = issSettlementDataDao.getAllPendingPM();
		} else if (loginResponse != null && loginResponse.getUserType().equals(PGConstants.PROGRAM_MANAGER_NAME)) {
			list = issSettlementDataDao.findByProgramManagerIdByStatus(loginResponse.getEntityId(), PGConstants.S_STATUS_PENDING);
		}
		processIssSettlementData(session, modelAndView, list);
		logger.info("EXITING :: DashboardController :: showAllPendingSettlementDetails");
		return modelAndView;
	}

	private void processIssSettlementData(HttpSession session, ModelAndView modelAndView,
			List<PGIssSettlementData> list) {
		List<SettlementDataRequest> settlementData = new ArrayList<>();
		if (StringUtil.isListNotNullNEmpty(list)) {
			for (PGIssSettlementData data : list) {
				SettlementDataRequest settlementDataRequest = new SettlementDataRequest();
				settlementDataRequest.setProgramManagerId(data.getAcqPmId());
				settlementDataRequest.setProgramManagerName(data.getProgramManagerName());
				settlementDataRequest.setBatchDate(data.getBatchDate());
				settlementDataRequest.setTotalAmount(new BigDecimal(data.getTotalAmount()).divide(PGConstants.BIG_DECIMAL_HUNDRED));
				settlementDataRequest.setTotalTxnCount(data.getTotalTxnCount());
				settlementData.add(settlementDataRequest);
			}
			modelAndView.addObject("settlementDataList", settlementData);
			session.setAttribute(PGConstants.TOTAL_RECORDS, settlementData.size());
		}
	}
}
