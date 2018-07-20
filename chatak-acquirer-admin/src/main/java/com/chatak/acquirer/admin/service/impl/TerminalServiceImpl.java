/**
 * 
 */
package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.acquirer.admin.controller.model.TerminalData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TerminalSearchResponse;
import com.chatak.acquirer.admin.model.Terminals;
import com.chatak.acquirer.admin.service.TerminalService;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.constants.Status;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.GetTerminalListRequest;
import com.chatak.pg.user.bean.GetTerminalListResponse;
import com.chatak.pg.user.bean.Terminal;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 05-Jan-2015 9:59:37 AM
 * @version 1.0
 */
@Service
public class TerminalServiceImpl implements TerminalService {

  @Autowired
  private TerminalDao terminalDao;

  @Override
  public AddTerminalResponse addTerminal(AddTerminalRequest addTerminalRequest)
      throws ChatakAdminException {
    AddTerminalResponse addTerminalResponse = new AddTerminalResponse();
    addTerminalResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
    addTerminalResponse.setErrorMessage(PGConstants.DUPLICATE_MESSAGE_TERMINAL);
    return addTerminalResponse;
  }

  @Override
  public UpdateTerminalResponse updateTerminal(UpdateTerminalRequest updateTerminalRequest)
      throws ChatakAdminException {
    return new UpdateTerminalResponse();
  }

  @Override
  public Response changeTerminalStatus(Terminal terminal, Integer status)
      throws ChatakAdminException {
    Response response = new Response();
    if (status.equals(PGConstants.STATUS_DELETED)) {
      PGTerminal pgTerminal = new PGTerminal();
      boolean isDeleted = terminalDao.changeTerminalStatus(pgTerminal, status);
      if (isDeleted) {
        response.setErrorCode(PGConstants.STATUS_SUCCESS.toString());
        response.setErrorMessage("Success");
      } else {
        response.setErrorCode(PGConstants.STATUS_DECLINED.toString());
        response.setErrorMessage("Unable to change the status.");
      }
    } else {
      //Need to Implemented based on requirement
    }
    return response;
  }

  @Override
  public TerminalSearchResponse searchTerminal(TerminalData terminal) throws ChatakAdminException {
    GetTerminalListRequest getTerminalListRequest = new GetTerminalListRequest();
    getTerminalListRequest.setMerchant_id(terminal.getMerchantId());
    getTerminalListRequest.setPageIndex(terminal.getPageIndex());
    getTerminalListRequest.setPageSize(terminal.getPageSize());
    getTerminalListRequest.setNoOfRecords(terminal.getNoOfRecords());

    GetTerminalListResponse response = terminalDao.getTerminals(getTerminalListRequest);
    TerminalSearchResponse terminalSearchResponse = new TerminalSearchResponse();
    List<PGTerminal> terminalList = response.getTerminalList();
    if (!CollectionUtils.isEmpty(terminalList)) {
      List<Terminals> terminals = new ArrayList<>(terminalList.size());
      Terminals terminalData = null;
      for (PGTerminal pgTerminal : terminalList) {
        terminalData = new Terminals();
        terminalData.setId(pgTerminal.getId());
        terminalData.setMerchantCode(pgTerminal.getMerchantId());
        terminalData.setTerminalCode(Long.valueOf(pgTerminal.getTerminalId()));
        terminalData.setStatus(Status.valueOf(pgTerminal.getStatus()));
        terminals.add(terminalData);
      }
      terminalSearchResponse.setTerminalList(terminals);
      terminalSearchResponse.setTotalNoOfRows(response.getNoOfRecords());
    }
    return terminalSearchResponse;
  }

  @Override
  public Terminal getTerminal(String terminalId) throws ChatakAdminException {
    return new Terminal();
  }

}
