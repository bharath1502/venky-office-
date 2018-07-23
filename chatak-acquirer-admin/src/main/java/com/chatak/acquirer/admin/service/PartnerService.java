package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.PartnerAccountRequest;
import com.chatak.pg.user.bean.PartnerAccountResponse;
import com.chatak.pg.user.bean.PartnerRequest;
import com.chatak.pg.user.bean.PartnerResponse;

public interface PartnerService {

  public Response updatePartner(PartnerRequest partnerRequest) throws ChatakAdminException;

  public Response updatePartnerAccount(PartnerAccountRequest partnerAccountRequest)
      throws ChatakAdminException;

  public Response createPartner(PartnerRequest partnerRequest) throws ChatakAdminException;

  public Response changePartnerStatus(PartnerRequest partnerRequest) throws ChatakAdminException;

  public PartnerResponse searchPartner(PartnerRequest partnerRequest) throws ChatakAdminException;

  public PartnerResponse searchPartnerAccount(PartnerRequest partnerRequest)
      throws ChatakAdminException;

  public PartnerResponse getAllPartners(PartnerRequest partnerRequest) throws ChatakAdminException;

  public PartnerResponse getAllPartnersForAdminUser(PartnerRequest partnerRequest)
      throws ChatakAdminException;

  public PartnerResponse findByPartnerId(PartnerRequest partnerRequest) throws ChatakAdminException;

  public PartnerAccountResponse findByPartnerAccountId(PartnerRequest partnerRequest)
      throws ChatakAdminException;

  public PartnerResponse editPartner(PartnerRequest partnerRequest) throws ChatakAdminException;

  public Response getPartnersByProgramManagerId(String programManagerId);
}
