/**
 * 
 */
package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.controller.model.TerminalData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TerminalSearchResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.Terminal;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 05-Jan-2015 9:59:11 AM
 * @version 1.0
 */
public interface TerminalService {

  /**
   * Method to create Terminal
   * 
   * @param addTerminalRequest
   * @return
   * @throws ChatakAdminException
   */
  public AddTerminalResponse addTerminal(AddTerminalRequest addTerminalRequest) throws ChatakAdminException;
  
  /**
   * Method to update terminal
   * 
   * @param updateTerminalRequest
   * @return
   * @throws ChatakAdminException
   */
  public UpdateTerminalResponse updateTerminal(UpdateTerminalRequest updateTerminalRequest) throws ChatakAdminException;
  
  /**
   * Method to change the terminal status*
   * 
   * @param terminal
   * @param status
   * @return
   * @throws ChatakAdminException
   */
  public Response changeTerminalStatus(Terminal terminal, Integer status) throws ChatakAdminException;
  
  /**
   * Method to search terminals
   * 
   * @param getTerminalListRequest
   * @return
   * @throws ChatakAdminException
   */
  public TerminalSearchResponse searchTerminal(TerminalData terminal) throws ChatakAdminException;
  
  /**
   * Method to get terminal for edit
   * 
   * @param getTerminalListRequest
   * @return
   * @throws ChatakAdminException
   */
  public Terminal getTerminal(String terminalId) throws ChatakAdminException;
  
}
