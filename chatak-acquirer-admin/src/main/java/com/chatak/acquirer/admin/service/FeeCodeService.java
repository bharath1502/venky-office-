package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeeCodeResponseDetails;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;
import com.chatak.pg.model.AcquirerFeeCodeDTO;
import com.chatak.pg.model.PartnerFeeCodeDTO;
import com.chatak.pg.model.PartnerFeeCodeResponseDetails;

public interface FeeCodeService {

  public FeeCodeResponseDetails getAllFeeCodeList() throws ChatakAdminException;

  public PartnerFeeCodeResponseDetails getAllPartnerFeeCodeList() throws ChatakAdminException;

  public PartnerFeeCodeDTO getPartnerFeeCodeByEntityId(String partnerEntityId) throws ChatakAdminException;

  public PartnerFeeCodeDTO getPartnerFeeCodeByAccountNumber(Long accountNumber) throws ChatakAdminException;

  public void addPartnerFeeCode(PartnerFeeCodeDTO partnerFeeCodeDTO) throws ChatakAdminException;

  public PGPartnerFeeCode validatePartnerFeeCode(String merchantCode) throws ChatakAdminException;

  public List<PGAcquirerFeeCode> getAllAcquirerFeeCodes() throws ChatakAdminException;

  public void updatePartnerFeeCode(PartnerFeeCodeDTO partnerFeeCodeDTO) throws ChatakAdminException;

  public AcquirerFeeCodeDTO getAcquirerFeeCodeByID(Long acquirerFeeCodeId) throws ChatakAdminException;

  public void updateAcquirerFeeCode(AcquirerFeeCodeDTO pgAcquirerFeeCodeDTO) throws ChatakAdminException;
  
  public List<PGAcquirerFeeCode> getAllAcquirerFeeCodesByPartnerId(Long partnerId) throws ChatakAdminException;
  
  public List<AcquirerFeeCodeDTO> findByPartnerIdAndMerchantCode(Long partnerId, String merchantCode) throws ChatakAdminException;
  
  public List<PGAcquirerFeeCode> getAcquirerFeeCodeTemplatesByPartnerId(Long partnerId) throws ChatakAdminException;
  
  public void addAcquirerFeeCode(AcquirerFeeCodeDTO pgAcquirerFeeCode) throws ChatakAdminException;
  
  public PGMerchant getAcquirerFeeByMerchantCode(String merchantCode) throws ChatakAdminException;
}
