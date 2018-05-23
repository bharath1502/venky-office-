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
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FundTransferActionListModel;
import com.chatak.acquirer.admin.model.FundTransferActionModel;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.FileExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.acq.dao.GetTransferListResponse;
import com.chatak.pg.acq.dao.PGTransfersDao;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.TransferRequestsCount;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.user.bean.Response;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;


@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
public class FundTransferController implements URLMappingConstants {
	
  private static final String TRANSFER_REQUESTS_LIST = "transferRequestsList";

  @Autowired
  MessageSource messageSource;

  private static Logger logger = Logger.getLogger(MerchantController.class);

  @Autowired
  PGTransfersDao pgTransfersDao;

  @Autowired
  FundTransfersService fundTransferService;

  @Autowired
  MerchantUpdateService merchantUpdateService;

  @RequestMapping(value = CHATAK_FUND_TRANSFER_ALL_REQUESTS_PAGE, method = RequestMethod.GET)
  public ModelAndView showFundTransferRequests(Map model, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering:: FundTransferController:: showFundTransferRequests method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_ALL_REQUESTS_PAGE);
    TransferRequestsCount transferRequestsCountEFT =
        fundTransferService.getEFTTransferRequestsCount();
    TransferRequestsCount transferRequestsCountCheck =
        fundTransferService.getCheckTransferRequestsCount();
    modelAndView.addObject("transferRequestsCountEFT", transferRequestsCountEFT);
    modelAndView.addObject("transferRequestsCountCheck", transferRequestsCountCheck);
    model.put("transferListRequest", new GetTransferListRequest());
    logger.info("Exiting:: FundTransferController:: showFundTransferRequests method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_FUND_TRANSFER_LIST_REQUESTS_PAGE, method = RequestMethod.POST)
  public ModelAndView getTransfersByStatus(Map model, HttpServletRequest request,
      HttpServletResponse response, GetTransferListRequest transferListRequest,
      HttpSession session) {
    logger.info("Entering:: FundTransferController:: getTransfersByStatus method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_LIST_REQUESTS_SHOW);
    GetTransferListResponse transferListResponse = new GetTransferListResponse();
    List<PGTransfers> transferRequestsList = null;
    transferListRequest.setPageIndex(Constants.ONE);
    transferListRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    try {
      transferRequestsList = fundTransferService.getPGTransfersList(transferListRequest);
      transferListResponse.setTotalResultCount(transferListRequest.getNoOfRecords());
      transferListResponse.setTransferRequestsList(transferRequestsList);
      modelAndView = PaginationUtil.getPagenationModel(modelAndView,
          transferListResponse.getTotalResultCount());
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: FundTransferController:: getTransfersByStatus method", e);
    }
    PGTransfers pgTransfers = new PGTransfers();
    FundTransferActionModel fundTransferActionModel = new FundTransferActionModel();
    modelAndView.addObject(TRANSFER_REQUESTS_LIST, transferRequestsList);
    modelAndView.addObject("transferListResponse", transferListResponse);
    modelAndView.addObject("fundTransferActionModel", fundTransferActionModel);
    modelAndView.addObject("transferMode", transferListRequest.getTransferMode());
    modelAndView.addObject("status", transferListRequest.getStatus());
    modelAndView.addObject("pgTransfers", pgTransfers);
    session.setAttribute(TRANSFER_REQUESTS_LIST, transferRequestsList);
    if ("EFT".equals(transferListRequest.getTransferMode())
        && "Pending".equals(transferListRequest.getStatus())) {
      model.put("isEFT_Pending", true);
    } else {
      model.put("isEFT_Pending", false);
    }
    logger.info("Exiting:: FundTransferController:: getTransfersByStatus method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PROCESS_FUND_TRANSFER_ACTION, method = RequestMethod.POST)
  public ModelAndView processTransfferAction(Map model, HttpServletRequest request,
      HttpServletResponse response, FundTransferActionModel fundTransferActionModel) {
    logger.info("Entering:: FundTransferController:: processTransfferAction method");
    ModelAndView modelAndView = showFundTransferRequests(model, request, response);
    try {
      Response respone = fundTransferService.processTransferAction(fundTransferActionModel);
      if (respone.getErrorCode().equals(ActionCode.ERROR_CODE_00)) {
        modelAndView.addObject(Constants.SUCESS,
            "Fund Transfer Is " + fundTransferActionModel.getAction());
        modelAndView = showFundTransferRequests(model, request, response);

      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR:: FundTransferController:: processTransfferAction method", e);
    }
    logger.info("Exiting:: FundTransferController:: processTransfferAction method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_BULK_FUND_TRANSFER_ACTION, method = RequestMethod.POST)
  public ModelAndView processBulkFundTransferAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam("requestObject") String requestObject, @FormParam("action") String action,
      Map model) throws ParseException {
    ModelAndView modelAndView = showFundTransferRequests(model, request, response);
    try {
      String jsonRequest = "{\"pgTransfersIds\":[" + requestObject + "]}";
      FundTransferActionListModel actionDTOList = (FundTransferActionListModel) JsonUtil
          .convertJSONToObject(jsonRequest, FundTransferActionListModel.class);
      for (FundTransferActionModel fundTransferActionModel : actionDTOList.getPgTransfersIds()) {
        fundTransferActionModel.setAction(action);
        Response respone = fundTransferService.processTransferAction(fundTransferActionModel);
        if (respone.getErrorCode().equals(ActionCode.ERROR_CODE_00)) {
          modelAndView.addObject(Constants.SUCESS,
              "Fund Transfer Is " + fundTransferActionModel.getAction());
          modelAndView = showFundTransferRequests(model, request, response);

        } else {
          modelAndView.addObject(Constants.ERROR, messageSource
              .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
        }
      }
        model.put(Constants.SUCESS, messageSource.getMessage(
            "chatak.admin.bulk.settlement.action.success", null, LocaleContextHolder.getLocale()));
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR:: MerchantController:: processBulkSettlementAction ChatakAdminException", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR:: MerchantController:: processBulkSettlementAction Exception", e);
    }
    logger.info("Exiting:: MerchantController:: processBulkSettlementAction method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_FUND_TRANSFER_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("transferMode") final String transferMode,
      @FormParam("status") final String status, Map model) {
    logger.info("Entering:: MerchantController:: getPaginationList method");
    GetTransferListRequest transferListRequest = new GetTransferListRequest();
    GetTransferListResponse transferListResponse = new GetTransferListResponse();
    ModelAndView modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_LIST_REQUESTS_SHOW);
    try {
      transferListRequest.setPageIndex(pageNumber);
      transferListRequest.setNoOfRecords(totalRecords);
      transferListRequest.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);
      transferListRequest.setStatus(status);
      transferListRequest.setTransferMode(transferMode);
      try {
        List<PGTransfers> transferRequestsList =
            fundTransferService.getPGTransfersList(transferListRequest);
        session.setAttribute(TRANSFER_REQUESTS_LIST, transferRequestsList);
        transferListResponse.setTotalResultCount(transferListRequest.getNoOfRecords());
        transferListResponse.setTransferRequestsList(transferRequestsList);
        if (transferRequestsList != null && !CollectionUtils.isEmpty(transferRequestsList)) {
          modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
              transferListResponse.getTotalResultCount());
          modelAndView.addObject("transferMode", transferListRequest.getTransferMode());
          modelAndView.addObject("status", transferListRequest.getStatus());
        }
        modelAndView.addObject(TRANSFER_REQUESTS_LIST, transferRequestsList);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        logger.error("ERROR:: MerchantController:: searchMerchant method", e);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: getPaginationList method", e);
    }
    FundTransferActionModel fundTransferActionModel = new FundTransferActionModel();
    modelAndView.addObject("fundTransferActionModel", fundTransferActionModel);
    logger.info("Exiting:: MerchantController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_REQUESTS_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadBatchReport(HttpServletRequest request, HttpServletResponse response,
      @FormParam("downloadType") final String downloadType,
      @FormParam("transferMode") final String transferMode,
      @FormParam("status") final String status, HttpSession session, Map model,
      @FormParam("requestObject") String requestObject) {
    logger.info("Entering :: TransactionController ;; downloadTransactionReport method ");

    ModelAndView modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_ALL_REQUESTS_PAGE);
    List<PGTransfers> transfersList =
        (List<PGTransfers>) session.getAttribute(TRANSFER_REQUESTS_LIST);
    try {
      ExportDetails exportDetails = new ExportDetails();

      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        if ("EFT".equalsIgnoreCase(transferMode)) {
          exportDetails.setExportType(ExportType.PDF);
        } else {
          exportDetails.setExportType(ExportType.PDF);
        }
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        if ("EFT".equalsIgnoreCase(transferMode)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        } else {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
      }
      if ("EFT".equalsIgnoreCase(transferMode)) {
        setExportDetailsDataForDownloadEFTBatchReport(transfersList, exportDetails); 
        ExportUtil.exportData(exportDetails, response, messageSource);
      }
      else {
        setExportDetailsDataForDownloadChequeBatchReport(transfersList, exportDetails); 
        ExportUtil.exportData(exportDetails, response, messageSource);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: TrasactionController:: downloadTransactionReport method", e);
    }
    logger.info("Exiting:: TransactionController:: downloadTransactionReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadEFTBatchReport(List<PGTransfers> transfersList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Transactions");
    exportDetails.setHeaderMessageProperty("fundtransfer-file-exportutiltransaction");

    exportDetails.setHeaderList(getEFTBatchHeaderList());
    exportDetails.setFileData(getEFTBatchFileData(transfersList));
  }

  private void setExportDetailsDataForDownloadChequeBatchReport(List<PGTransfers> transfersList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.reqest.messages");

    exportDetails.setHeaderList(getChequeBatchHeaderList());
    exportDetails.setFileData(getChequeBatchFileData(transfersList));
  }

  @RequestMapping(value = CHATAK_EFT_BATCH_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadTransactionReport(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model,
      @FormParam("transfersIds") Long[] transfersIds) {
    logger.info("Entering :: TransactionController ;; downloadTransactionReport method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_ALL_REQUESTS_PAGE);
    List<PGTransfers> transfersList =
        (List<PGTransfers>) session.getAttribute(TRANSFER_REQUESTS_LIST);
    try {
      Map<String, String> merchantNameMap =
          merchantUpdateService.getMerchantNameByMerchantCodeAsMap();
      FileExportUtil.downloadEFTRequestsXlBatch(transfersList, Arrays.asList(transfersIds),
          merchantNameMap, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: TrasactionController:: downloadTransactionReport method", e);
    }
    logger.info("Exiting:: TransactionController:: downloadTransactionReport method");
    return null;
  }

  @RequestMapping(value = CHATAK_FETCH_ACCOUNT_DETAILS, method = RequestMethod.POST)
  public @ResponseBody String fetchAccountDetails(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering:: TrasactionController:: fetchAccountDetails method");
    String accountNumber = request.getParameter("accountNumber");
    AccountBalanceDTO accountBalanceDTO = null;

    try {
      accountBalanceDTO = fundTransferService.fetchAccountDetails(accountNumber);
      return JsonUtil.convertObjectToJSON(accountBalanceDTO);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: TrasactionController:: fetchAccountDetails method",e);
    }
    return accountNumber;
  }

  @RequestMapping(name = CHATAK_PROCESS_ACCOUNT_TRANSFER, method = RequestMethod.GET)
  public ModelAndView showAccountTransfer(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering:: TrasactionController:: showAccountTransfer method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PROCESS_ACCOUNT_TRANSFER);
    session.invalidate();
    modelAndView.setViewName(INVALID_REQUEST_PAGE);
    AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
    model.put("accountTransferRequest", accountTransferRequest);
    logger.info("Exiting:: TrasactionController:: showAccountTransfer method");
    return modelAndView;
  }

  @RequestMapping(name = CHATAK_PROCESS_ACCOUNT_TRANSFER, method = RequestMethod.POST)
  public ModelAndView processAccountTransfer(Map model, HttpServletRequest request,
      HttpServletResponse response, AccountTransferRequest accountTransferRequest,
      HttpSession session) {
    logger.info("Entering:: TrasactionController:: processAccountTransfer method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PROCESS_ACCOUNT_TRANSFER);
    String balance = null;
    try {
      balance = fundTransferService.processAccountTransfer(accountTransferRequest);
      model.put(Constants.SUCESS,
          "Account Transfer is Completed Successfully ,Avaliable Balance is :$ " + balance);
      model.put(Constants.ERROR, null);
    } catch (ChatakAdminException e) {
      logger.info("Error:: TrasactionController:: processAccountTransfer method",e);
      model.put(Constants.SUCESS, null);
      model.put(Constants.ERROR, messageSource.getMessage("chatak.account.transfer.failure", null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: TrasactionController:: processAccountTransfer method");
    return modelAndView;
  }
  
  private List<String> getEFTBatchHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("dash-board-litle-eft-transactions-search.label.datetime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.merchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.amount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fund-transfer-list-show.label.fundtransfermode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fund-transfer-list-show.label.fromaccount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fund-transfer-list-show.label.toaccount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getEFTBatchFileData(List<PGTransfers> transfersList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (PGTransfers transfers : transfersList) {

      Object[] rowData = {
          transfers.getCreatedDate() != null
              ? DateUtil.toDateStringFormat(transfers.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT)
                  + ""
              : "",
          transfers.getMerchantId().toString() != null ? transfers.getMerchantId().toString() + ""
              : "",
          transfers.getAmount() != null
              ? PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(transfers.getAmount()) + ""
              : "",
          transfers.getTransferMode(), transfers.getFromAccount(), transfers.getToAccount(),
          transfers.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
  
  private List<String> getChequeBatchHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("dash-board-litle-eft-transactions-search.label.datetime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-account-transfer.label.merchantname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.address1", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.address2", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("virtual-terminal-void.label.city", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("virtual-terminal-sale.label.state", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("virtual-terminal-sale.label.zipcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.amount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fundtransfer-file-accountchatakaccountnumber", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getChequeBatchFileData(List<PGTransfers> transfersList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (PGTransfers transfers : transfersList) {

      Object[] rowData =
          {transfers.getCreatedDate() != null ? transfers.getCreatedDate() + "" : "",
              transfers.getNameOnAccount(), transfers.getCity(), transfers.getState(),
              transfers.getCity(), transfers.getState(), "",
              transfers.getAmount() != null ? PGConstants.DOLLAR_SYMBOL
                  + StringUtils.amountToString(transfers.getAmount()) + "" : "",
              transfers.getFromAccount() != null ? transfers.getFromAccount() + "" : ""

          };
      fileData.add(rowData);
    }

    return fileData;
  }
}
