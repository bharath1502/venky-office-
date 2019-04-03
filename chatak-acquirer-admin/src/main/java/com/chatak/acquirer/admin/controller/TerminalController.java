/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.controller.model.TerminalData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TerminalSearchResponse;
import com.chatak.acquirer.admin.model.Terminals;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.TerminalService;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.Terminal;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;
import com.chatak.pg.util.Constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 07-Jan-2015 4:14:49 PM
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
public class TerminalController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(TerminalController.class);
  
  private static final String MERCHANT_OPTIONS = "merchantOptions";
  
  private static final String TERMINAL = "terminal";

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private TerminalService terminalService;

  @Autowired
  private MessageSource messageSource;

  /**
   * Method to show create terminal page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_CREATE_TERMINAL_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateTerminalPage(Map model, HttpSession session) {
    logger.info("Entering:: TerminalController:: showCreateTerminalPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_TERMINAL_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      List<Option> options = merchantUpdateService.getActiveMerchants();
      session.setAttribute(MERCHANT_OPTIONS, new ArrayList(options));
      modelAndView.addObject(MERCHANT_OPTIONS, options);
    } catch (ChatakAdminException e) {
      logger.error("Error :: TerminalController:: showCreateTerminalPage method", e);
    }
    logger.info("Exiting:: TerminalController:: showCreateTerminalPage method");
    model.put(TERMINAL, new AddTerminalRequest());
    return modelAndView;
  }

  /**
   * Method to show create terminal page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_CREATE_TERMINAL, method = RequestMethod.POST)
  public ModelAndView createTerminal(HttpServletRequest request, HttpServletResponse response,
      AddTerminalRequest terminal, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: TerminalController:: createTerminal method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_TERMINAL_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      List<Option> options = merchantUpdateService.getActiveMerchants();
      session.setAttribute(MERCHANT_OPTIONS, new ArrayList(options));
      modelAndView.addObject(MERCHANT_OPTIONS, options);
      AddTerminalResponse addTerminalResponse = terminalService.addTerminal(terminal);
      if (null != addTerminalResponse) {
        if (addTerminalResponse.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
          modelAndView.addObject(Constants.SUCESS, messageSource
              .getMessage("chatak.terminal.create.success", null, LocaleContextHolder.getLocale()));
          terminal = new AddTerminalRequest();
        } else {
          modelAndView.addObject(Constants.ERROR, addTerminalResponse.getErrorMessage());
        }
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: TerminalController:: createTerminal method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put(TERMINAL, terminal);
    logger.info("Exiting:: TerminalController:: createTerminal method");
    return modelAndView;
  }

  /**
   * Method to show search terminal page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_SEARCH_TERMINAL_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchTerminalPage(HttpServletRequest request,
      HttpServletResponse response, TerminalData terminal, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: TerminalController:: showSearchTerminalPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TERMINAL_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    terminal.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    terminal.setPageIndex(Constants.ONE);
    session.setAttribute(Constants.TERMINALS_MODEL, terminal);
    try {
      List<Option> options = merchantUpdateService.getActiveMerchants();
      session.setAttribute(MERCHANT_OPTIONS, new ArrayList(options));
      modelAndView.addObject(MERCHANT_OPTIONS, options);
      TerminalSearchResponse searchResponse = terminalService.searchTerminal(terminal);
      List<Terminals> terminalList = new ArrayList<Terminals>();
      if (null != searchResponse && !CollectionUtils.isEmpty(searchResponse.getTerminalList())) {
        terminalList = searchResponse.getTerminalList();
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.TERMINALS_MODEL, terminalList);
    } catch (Exception e) {
      logger.error("ERROR:: TerminalController:: showSearchTerminalPage method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put(TERMINAL, terminal);
    logger.info("Exiting:: TerminalController:: showSearchTerminalPage method");
    return modelAndView;
  }

  /**
   * Method to show search terminal
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_SEARCH_TERMINAL, method = RequestMethod.POST)
  public ModelAndView searchTerminal(HttpServletRequest request, HttpServletResponse response,
      TerminalData terminal, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: TerminalController:: searchTerminal method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TERMINAL_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    model.put("search", "show");
    try {
      modelAndView.addObject(MERCHANT_OPTIONS, session.getAttribute(MERCHANT_OPTIONS));
      TerminalSearchResponse searchResponse = terminalService.searchTerminal(terminal);
      List<Terminals> terminalList = new ArrayList<Terminals>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getTerminalList())) {
        terminalList = searchResponse.getTerminalList();
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.TERMINALS_MODEL, terminalList);
    } catch (Exception e) {
      logger.error("ERROR:: TerminalController:: searchTerminal method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put(TERMINAL, terminal);
    logger.info("Exiting:: TerminalController:: searchTerminal method");
    return modelAndView;
  }

  /**
   * Method used for Pagination Util
   * 
   * @param session
   * @param pageNumber
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_TERMINALS_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering:: TerminalController:: getPaginationList method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TERMINAL_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      TerminalData terminal = (TerminalData) session.getAttribute(Constants.TERMINALS_MODEL);
      model.put(TERMINAL, terminal);
      terminal.setPageIndex(pageNumber);
      terminal.setNoOfRecords(totalRecords);
      terminal.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      modelAndView = validateTerminalSearchResponse(pageNumber, modelAndView, terminal);
    } catch (Exception e) {
      logger.error("ERROR:: TerminalController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: TerminalController:: getPaginationList method");
    return modelAndView;
  }

  private ModelAndView validateTerminalSearchResponse(final Integer pageNumber,
      ModelAndView modelAndView, TerminalData terminal) {
    try {
      TerminalSearchResponse searchResponse = terminalService.searchTerminal(terminal);
      List<Terminals> terminals = new ArrayList<Terminals>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getTerminalList())) {
        terminals = searchResponse.getTerminalList();
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.TERMINALS_MODEL, terminals);
    } catch (Exception e) {
      logger.error("ERROR:: TerminalController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }

  /**
   * Method to show edit terminal page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_EDIT_TERMINAL, method = RequestMethod.POST)
  public ModelAndView showEditTerminalPage(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getTerminalId") final String getTerminalId, Terminal terminal, 
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: TerminalController:: showEditTerminal method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_TERMINAL_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      List<Option> options = merchantUpdateService.getActiveMerchants();
      session.setAttribute(MERCHANT_OPTIONS, new ArrayList(options));
      modelAndView.addObject(MERCHANT_OPTIONS, options);
      Terminal terminalrequest = terminalService.getTerminal(getTerminalId);
      if (null == terminalrequest)
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
      else
        modelAndView.addObject(TERMINAL, terminalrequest);
    } catch (Exception e) {
      logger.error("ERROR:: TerminalController:: showEditTerminal method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: TerminalController:: showEditTerminal method");
    return modelAndView;
  }

  /**
   * Method to update terminal
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_UPDATE_TERMINAL, method = RequestMethod.POST)
  public ModelAndView updateTerminal(HttpServletRequest request, HttpServletResponse response,
      UpdateTerminalRequest updateTerminalRequest, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: TerminalController:: updateTerminal method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TERMINAL_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      UpdateTerminalResponse updateTerminalResponse =
          terminalService.updateTerminal(updateTerminalRequest);
      if (updateTerminalResponse.isUpdated()
          && updateTerminalResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        TerminalData terminalData = new TerminalData();
        modelAndView = showSearchTerminalPage(request, response, new TerminalData(), bindingResult,
            model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.terminal.update.success", null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(TERMINAL, terminalData);

      } else {
        modelAndView.setViewName(CHATAK_ADMIN_EDIT_TERMINAL_PAGE);
        modelAndView.addObject(TERMINAL, updateTerminalRequest);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: TerminalController:: updateTerminal method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: TerminalController:: updateTerminal method");
    return modelAndView;
  }
}
