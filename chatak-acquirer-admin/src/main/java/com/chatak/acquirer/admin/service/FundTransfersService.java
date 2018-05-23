package com.chatak.acquirer.admin.service;

import java.util.List;
import java.util.Map;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FundTransferActionListModel;
import com.chatak.acquirer.admin.model.FundTransferActionModel;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.BulkFundTransferResponse;
import com.chatak.pg.model.EFTDetails;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.TransferRequestsCount;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.user.bean.Response;

public interface FundTransfersService {
  public TransferRequestsCount getEFTTransferRequestsCount();

  public TransferRequestsCount getCheckTransferRequestsCount();

  public List<PGTransfers> getPGTransfersList(GetTransferListRequest transferListRequest);

  public Response processTransferAction(FundTransferActionModel fundTransferActionModel) throws ChatakAdminException;

  public BulkFundTransferResponse updateBulkFundTransferStatus(FundTransferActionListModel fundTransferActionDTOList,
                                                               String status,
                                                               String comments) throws ChatakAdminException;

  public List<ReportsDTO> getPGTransfersListOnTransferMode(GetTransferListRequest request);

  public LitleEFTRequest getLitleExecutedTransactions(LitleEFTRequest litleEFTRequest);

  public int getLitleExecutedTransactionsCount();

  public Response processLitleEFT(List<LitleEFTDTO> list) throws ChatakAdminException;

  public PGTransfers logTransfers(FundTransferDTO fundTransferDTO);

  public Response processCheckFundsTransfer(FundTransferDTO fundTransferDTO) throws ChatakAdminException;

  public Response processEFTFundsTransfer(FundTransferDTO fundTransferDTO) throws ChatakAdminException;

  public FundTransferDTO populateFundTransferDTO(String merchantCode, String fundTransferMode) throws ChatakAdminException;

  public Response processIndividualLitleEFT(LitleEFTDTO litleEFTDTO) throws ChatakAdminException;

  public List<ReportsDTO> getAllEftTransfersListOnMerchantCode(GetTransferListRequest request) throws ChatakAdminException;

  public Map<String, Long> splitReportsAmount(List<ReportsDTO> eftTransferReportList);

  public List<EFTDetails> getReportsEFTAmount(Map<String, Long> splitList) throws ChatakAdminException;

  public GetTransactionIdsListResponse getTransactionIdListOnTransferId(String transferId) throws ChatakAdminException;

  public LitleEFTRequest getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(String merchantCode,
                                                                                      Integer payoutFrequencyDays);

  public AccountBalanceDTO fetchAccountDetails(String accountNumber) throws ChatakAdminException;
  
  public String processAccountTransfer(AccountTransferRequest accountTransferRequest) throws ChatakAdminException;
}
