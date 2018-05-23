package com.chatak.merchant.service;

import java.util.List;
import java.util.Map;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.EFTDetails;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransferListRequest;

public interface FundTransferService {

  public FundTransferDTO populateFundTransferDTO(String merchantCode, String fundTransferMode) throws ChatakMerchantException;

  public void processEFTFundsTransfer(FundTransferDTO fundTransferDTO) throws ChatakMerchantException;

  public void processCheckFundsTransfer(FundTransferDTO fundTransferDTO) throws ChatakMerchantException;

  public void logTransfers(FundTransferDTO fundTransferDTO) throws ChatakMerchantException;

  public List<ReportsDTO> getAllEftTransfersListOnMerchantCode(GetTransferListRequest request) throws ChatakMerchantException;

  public Map<String, Long> splitReportsAmount(List<ReportsDTO> eftTransferReportList);

  public List<EFTDetails> getReportsEFTAmount(Map<String, Long> splitList) throws ChatakMerchantException;

  public GetTransactionIdsListResponse getTransactionIdListOnTransferId(String transferId) throws ChatakMerchantException;

  public AccountBalanceDTO fetchAccountDetails(String accountNumber,String merchantCode) throws ChatakMerchantException;
  
  public String processAccountTransfer(AccountTransferRequest accountTransferRequest) throws ChatakMerchantException;
  
  public List<Option> getAccountList(String merchantCode) throws ChatakMerchantException;

}
