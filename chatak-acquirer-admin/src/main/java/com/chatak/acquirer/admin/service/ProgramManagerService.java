package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
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
  
  public ProgramManagerResponse getAllIssuanceProgramManagers(ProgramManagerRequest programManagerRequest)
		throws ChatakAdminException;

  public ProgramManagerResponse getIssuanceProgramManagerById(ProgramManagerRequest programManagerRequest)
		throws ChatakAdminException;

  public CardProgramResponse searchCardProgramByProgramManager(
		PartnerGroupPartnerMapRequest partnerGroupPartnerMapRequest) throws ChatakAdminException;

  public CardProgramResponse getCardProgramsDetailsByIds(CardProgramRequest cardProgramRequest)
		throws ChatakAdminException;

  public List<CardProgramRequest> findCardProgramByPmId(Long programManagerId) throws ChatakAdminException;
  
  public Response findProgramManagerNameByAccountCurrency(String currencyId) throws ChatakAdminException;
  
  public Response findProgramManagerNameByCurrencyAndId(Long id,String currencyId);
  
  public Response findByProgramManagerIdAndAccountCurrency(Long pmId,String currencyId) throws ChatakAdminException;
  
  public CardProgramResponse findPMCardprogramByMerchantId(Long merchantId);
  
  public ProgramManagerAccount getPMAccountByPMIdAndAccountType(Long programManagerId, String accountType);
  
  public Response updateProgramManagerAccount(ProgramManagerAccount programManagerAccount);

  public ProgramManagerRequest findbyProgramManagerId(Long id);
  
  public CardProgramResponse searchIssuanceCardProgramByPM(
      PartnerGroupPartnerMapRequest partnerGroupPartnerMapRequest,String currency,String createdBy,String acquirerPmId)throws ChatakAdminException;
  
  public List<CardProgramRequest> getUnselectedCpByPm(Long programManagerId);
  
  public List<CardProgramRequest> getUnselectedCpForIndependentPm(Long programManagerId, String currency);
  
  public Response findProgramManagerNameAndIdByEntityId(Long entityId, Long merchantId);
  
}
