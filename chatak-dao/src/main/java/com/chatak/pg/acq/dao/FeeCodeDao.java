package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGFeeCode;
import com.chatak.pg.model.PartnerFeeCodeDTO;
import com.chatak.pg.model.PartnerFeeCodeResponseDetails;

public interface FeeCodeDao {
  /**
   * @return
   * @throws DataAccessException
   */
  public List<PGFeeCode> getAllFeeCodeList() throws DataAccessException;

  /**
   * @param feeCode
   * @return
   * @throws DataAccessException
   */
  public List<PGFeeCode> getFeeCodeDetails(Long feeCode) throws DataAccessException;

  /**
   * DAO method to fetch fee code for edit
   * 
   * @param feeProgramId
   * @return
   * @throws DataAccessException
   * @throws Exception
   */
  public List<PGFeeCode> getAllFeeCodeListForEdit(Long feeProgramId) throws IllegalAccessException;
  
  public PartnerFeeCodeResponseDetails getPartnetFeeList(PartnerFeeCodeDTO partnerFeeCodeDTO);
}
