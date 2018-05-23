package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.PartnerGroupPartnerMapRequest;
import com.chatak.pg.user.bean.ProgramManagerAccountRequest;
import com.chatak.pg.user.bean.ProgramManagerAccountResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;

public interface ProgramManagerService {

  public Response createProgramManager(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException;

  public ProgramManagerResponse searchProgramManagerDetails(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException;

  public ProgramManagerResponse searchProgramManagerAccountDetails(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException;

  public ProgramManagerResponse editProgramManager(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException;

  public ProgramManagerAccountResponse findProgramManagerAccountByAccountId(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException;

  public Response updateProgramManager(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException;

  public Response updateProgramManagerAccount(
      ProgramManagerAccountRequest programManagerAccountRequest) throws ChatakAdminException;

  public Response updateProgramManagerStatus(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException;

  public ProgramManagerResponse searchSystemProgramManager(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException;

  public ProgramManagerResponse getAllProgramManagers(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException;

  public ProgramManagerResponse getProgramManagersByBankId(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException;

  public BankResponse findBankByProgramManagerId(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException;

  public Response editProgramManagerAccount(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException;

  public List<Option> getActiveProgramManagers();
  
  /**
	 * @param programManagerRequest
	 * @return
	 * @throws PrepaidException
	 */
	public ProgramManagerResponse getAllIssuanceProgramManagers(ProgramManagerRequest programManagerRequest)throws ChatakAdminException;
	
	public ProgramManagerResponse getIssuanceProgramManagerById(ProgramManagerRequest programManagerRequest)throws ChatakAdminException;
	
	public CardProgramResponse searchCardProgramByProgramManager(PartnerGroupPartnerMapRequest partnerGroupPartnerMapRequest)throws ChatakAdminException;
}
