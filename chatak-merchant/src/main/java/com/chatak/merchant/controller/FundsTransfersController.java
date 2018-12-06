package com.chatak.merchant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.service.FundTransferService;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class FundsTransfersController implements URLMappingConstants {
  @Autowired
  FundTransferService fundTransferService;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  private static Logger logger = Logger.getLogger(FundsTransfersController.class);

  @RequestMapping(value = CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE, method = RequestMethod.GET)
  public ModelAndView showEFTTransferPage(Model model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: FundsTransfersController :: showEFTTransferPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchant pgMerchant = merchantProfileDao.getMerchantById(merchantId);
    FundTransferDTO fundTransferDTO;
    try {
      fundTransferDTO = fundTransferService.populateFundTransferDTO(pgMerchant.getMerchantCode(),
          PGConstants.FUND_TRANSFER_EFT);
      validateFundTransferDTO(model, fundTransferDTO);

    } catch (ChatakMerchantException exp) {
      logger.error("Error:: FundsTransfersController:: showEFTTransfl101erPage method", exp);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      model.addAttribute(Constants.FUND_TRANSFER_DTO, new FundTransferDTO());
    }
    logger.info("Exiting:: FundsTransfersController:: showEFTTransferPage method");
    return modelAndView;
  }

  private void validateFundTransferDTO(Model model, FundTransferDTO fundTransferDTO) {
	if (null == fundTransferDTO.getDebitAccount().getAccountNumber()) {
        new ModelAndView(CHATAK_FUND_TRANSFER_RESPONSE);
        model.addAttribute(Constants.ERROR, "No bank account found ");
        model.addAttribute(Constants.FUND_TRANSFER_DTO, fundTransferDTO);

      } else {
        model.addAttribute(Constants.FUND_TRANSFER_DTO, fundTransferDTO);
      }
  }

  @RequestMapping(value = CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE, method = RequestMethod.GET)
  public ModelAndView showCheckTransferPage(Model model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: FundsTransfersController :: showCheckTransferPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchant pgMerchant = merchantProfileDao.getMerchantById(merchantId);
    FundTransferDTO fundTransferDTO;
    try {
      fundTransferDTO = fundTransferService.populateFundTransferDTO(pgMerchant.getMerchantCode(),
          PGConstants.FUND_TRANSFER_CHECK);
      validateFundTransferDTO(model, fundTransferDTO);
    } catch (ChatakMerchantException e) {
      logger.error("Error:: FundsTransfersController:: showCheckTransferPage method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      model.addAttribute(Constants.FUND_TRANSFER_DTO, new FundTransferDTO());
    }
    logger.info("Exiting :: FundsTransfersController :: showCheckTransferPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PROCESS_EFT_FUND_TRANSFER, method = RequestMethod.POST)
  public ModelAndView processEFTTransfer(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session, FundTransferDTO fundTransferDTO) {
    logger.info("Entering :: FundsTransfersController :: processEFTTransfer method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_RESPONSE);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      fundTransferService.processEFTFundsTransfer(fundTransferDTO);
      model.put(Constants.SUCESS, "Send Funds To Account Request Is Initiated.");
    } catch (ChatakMerchantException e) {
      logger.error("Error:: FundsTransfersController:: showCheckTransferPage method", e);
      modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_RESPONSE);
      model.put(Constants.FUND_TRANSFER_DTO, fundTransferDTO);
      model.put(Constants.ERROR, e.getMessage());
    }
    logger.info("Exiting :: FundsTransfersController :: processEFTTransfer method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PROCESS_CHECK_FUND_TRANSFER, method = RequestMethod.POST)
  public ModelAndView processCheckTransfer(Model model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session, FundTransferDTO fundTransferDTO) {
    logger.info("Entering :: FundsTransfersController :: processCheckTransfer method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_RESPONSE);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      fundTransferService.processCheckFundsTransfer(fundTransferDTO);
      model.addAttribute(Constants.SUCESS, "Send Funds To Account Request Is Initiated.");
    } catch (ChatakMerchantException e) {
      logger.error("Error:: FundsTransfersController:: showCheckTransferPage method", e);
      modelAndView = new ModelAndView(CHATAK_FUND_TRANSFER_RESPONSE);
      model.addAttribute(Constants.FUND_TRANSFER_DTO, fundTransferDTO);
      model.addAttribute(Constants.ERROR, e.getMessage());

    }
    logger.info("Exiting :: FundsTransfersController :: processCheckTransfer method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_FETCH_ACCOUNT_DETAILS, method = RequestMethod.POST)
  public @ResponseBody String fetchAccountDetails(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering:: TrasactionController:: fetchAccountDetails method");
    String accountNumber = request.getParameter("accountNumber");
    AccountBalanceDTO accountBalanceDTO = null;
    Long merchantId = null;

    try {
      merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      accountBalanceDTO = fundTransferService.fetchAccountDetails(accountNumber,
          merchantProfileDao.getMerchantById(merchantId).getMerchantCode());
      return JsonUtil.convertObjectToJSON(accountBalanceDTO);
    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: TrasactionController:: fetchAccountDetails method",e);
    }
    return accountNumber;
  }

  @RequestMapping(value = CHATAK_PROCESS_ACCOUNT_TRANSFER, method = RequestMethod.GET)

  public ModelAndView showAccountTransfer(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering:: TrasactionController:: showAccountTransfer method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PROCESS_ACCOUNT_TRANSFER);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    List<Option> accounts = null;

    try {
      accounts = fundTransferService
          .getAccountList(merchantProfileDao.getMerchantById(merchantId).getMerchantCode());
    } catch (ChatakMerchantException e) {
      accounts = new ArrayList<>(1);
      logger.error("Error:: TrasactionController:: showAccountTransfer method", e);
    }
    model.put(Constants.ACCOUNTS, accounts);
    session.setAttribute(Constants.ACCOUNTS, new ArrayList(accounts));
    AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
    model.put("accountTransferRequest", accountTransferRequest);
    logger.info("Exiting:: TrasactionController:: showAccountTransfer method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_PROCESS_ACCOUNT_TRANSFER, method = RequestMethod.POST)
  public ModelAndView processAccountTransfer(Map model, HttpServletRequest request,
      HttpServletResponse response, AccountTransferRequest accountTransferRequest,
      HttpSession session) {
    logger.info("Entering:: TrasactionController:: processAccountTransfer method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_PROCESS_ACCOUNT_TRANSFER);
    String balance = null;
    List<Option> accounts = null;
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      balance = fundTransferService.processAccountTransfer(accountTransferRequest);
      model.put(Constants.SUCESS,
          "Account Transfer is Completed Successfully ,Avaliable Balance is :$ " + balance);
      model.put(Constants.ERROR, null);

      accounts = fundTransferService
          .getAccountList(merchantProfileDao.getMerchantById(merchantId).getMerchantCode());
      session.setAttribute(Constants.ACCOUNTS, new ArrayList(accounts));
    } catch (ChatakMerchantException e) {
      logger.info("Error:: TrasactionController:: processAccountTransfer method",e);
      model.put(Constants.SUCESS, null);
      model.put(Constants.ERROR, e.getMessage());

      accounts = (List<Option>) session.getAttribute(Constants.ACCOUNTS);
    }
    model.put(Constants.ACCOUNTS, accounts);
    logger.info("Exiting:: TrasactionController:: processAccountTransfer method");
    return modelAndView;
  }

}
