package com.chatak.acquirer.admin.controller;

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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.service.ExchangeRateService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.bean.ExchangeRate;
import com.chatak.pg.bean.ExchangeRateResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.util.Constants;


@SuppressWarnings({"rawtypes", "unchecked", "unused"})
@Controller
public class ExchangeRateController implements URLMappingConstants {

  @Autowired
  private ExchangeRateService exchangeRateService;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private MessageSource messageSource;

  private static Logger logger = Logger.getLogger(ExchangeRateController.class);

  @RequestMapping(value = CHATAK_ADMIN_EXCHANGE_RATE_CREATE_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateExchangeRatePage(HttpServletRequest request, Map model,
      HttpSession session) {
    logger.info("Entering:: ExchangeRateController:: showCreateExchangeRatePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EXCHANGE_RATE_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    ExchangeRate exchangeInfo = new ExchangeRate();
    session.setAttribute(Constants.EXCHANGE_RATE_INFO, exchangeInfo);
    exchangeInfo.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    exchangeInfo.setPageIndex(Constants.ONE);
    String succMsg = request.getParameter("succMsg");
    try {
      List<Option> currencyList = merchantUpdateService.getCurrencies();
      modelAndView.addObject("currencyList", currencyList);
      ExchangeRateResponse searchExchangeResponse =
          exchangeRateService.searchExchangeRateInfo(exchangeInfo);
      if (searchExchangeResponse != null) {
        List<ExchangeRate> searchExchangeList = searchExchangeResponse.getExchangeRateInfo();
        modelAndView.addObject("pageSize", exchangeInfo.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            searchExchangeResponse.getTotalNoOfRows());
        modelAndView.addObject(Constants.EXCHANGE_RATE_INFO, searchExchangeList);
      }
      modelAndView.addObject(Constants.SUCESS, succMsg);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ExchangeRateController:: showCreateExchangeRatePage method", e);
    }
    model.put(Constants.EXCHANGE_RATE, exchangeInfo);
    logger.info("Exiting:: ExchangeRateController:: showCreateExchangeRatePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_EXCHANGE_RATE_CREATE, method = RequestMethod.POST)
  public ModelAndView createExchangeRate(HttpServletRequest request, HttpServletResponse response,
      ExchangeRate exchangeRate, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: ExchangeRateController:: createExchangeRate method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EXCHANGE_RATE_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Response exchangeResponse = exchangeRateService.addExchangeRate(exchangeRate);
      if (null != exchangeResponse
          && exchangeResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showCreateExchangeRatePage(request, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.exchange.rate.create.success", null, LocaleContextHolder.getLocale()));
      } else if (null != exchangeResponse
          && exchangeResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_Z5)) {
        modelAndView = showCreateExchangeRatePage(request, model, session);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: ExchangeRateController:: createExchangeRate method", e);
    }
    model.put(Constants.EXCHANGE_RATE, new ExchangeRate());
    logger.info("Exiting:: ExchangeRateController:: createExchangeRate method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_EXCHANGE, method = RequestMethod.POST)
  public ModelAndView showEditExchange(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getExchangeId") final Long getExchangeId, HttpSession session, Map model) {
    logger.info("Entering :: ExchangeRateController :: showEditExchange method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_EXCHANGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      List<Option> currencyList = merchantUpdateService.getCurrencies();
      modelAndView.addObject(Constants.CURRENCY_LIST, currencyList);
      ExchangeRate exchangeInfo = exchangeRateService.getExchangeInfoById(getExchangeId);
      model.put(Constants.EXCHANGE_RATE, exchangeInfo);
    } catch (Exception e) {
      logger.error("ERROR:: ExchangeRateController:: showEditExchange method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: ExchangeRateController :: showEditExchange");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_EXCHANGE, method = RequestMethod.POST)
  public ModelAndView updateExchangeRate(HttpServletRequest request, HttpServletResponse response,
      ExchangeRate updateExchangeRate, BindingResult bindingResult, HttpSession session,
      Map model) {
    logger.info("Entering :: ExchangeRateController :: updateExchangeRate method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EXCHANGE_RATE_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    updateExchangeRate.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    updateExchangeRate.setPageIndex(Constants.ONE);
    try {
      ExchangeRateResponse updateExchangeResponse =
          exchangeRateService.updateExchangeRateInfo(updateExchangeRate);
      if (updateExchangeResponse != null
          && updateExchangeResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showCreateExchangeRatePage(request, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.exchange.rate.update.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: ExchangeRateController:: updateExchangeRate method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: ExchangeRateController:: updateExchangeRate method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_EXCHANGE_RATE_DELETE, method = RequestMethod.POST)
  public ModelAndView deleteExchangeRate(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getExchangeRateId") final Long getExchangeRateId, ExchangeRate exchangeInfo,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: ExchangeRateController :: deleteExchangeRate method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EXCHANGE_RATE_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      Response exchangeResponse = exchangeRateService.deleteExchangeRate(getExchangeRateId);
      if (exchangeResponse != null
          && exchangeResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        modelAndView = showCreateExchangeRatePage(request, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.exchange.rate.delete.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView = showCreateExchangeRatePage(request, model, session);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.exchange.rate.delete.failure", null, LocaleContextHolder.getLocale()));
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ExchangeRateController :: deleteExchangeRate method", e);
    }
    logger.info("EXITING :: ExchangeRateController :: deleteExchangeRate");
    return modelAndView;
  }

}
