package com.chatak.acquirer.admin.service;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.TransactionServiceImpl;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.TransactionReportDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.LitleEFTDTOsList;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(JsonUtil.class)
public class TransactionServiceTest {

  @InjectMocks
  private TransactionServiceImpl transactionServiceImpl = new TransactionServiceImpl();

  @Mock
  private TransactionDao transactionDao;

  @Mock
  private AccountDao accountDao;

  @Mock
  private MerchantDao merchantDao;

  @Mock
  private Transaction transactions;

  @Mock
  private GetTransactionsListResponse response;

  @Mock
  private GetTransactionsListRequest getTransactionsListRequest;

  @Mock
  private PGConstants pGConstants;

  @Mock
  private AccountTransactionsDao accountTransactionsDao;

  @Mock
  SettlementActionDTOList tempList;

  @Mock
  SettlemetActionDTO settlemetActionDTOList;

  @Mock
  VoidTransactionDao voidTransactionDao;

  @Mock
  RefundTransactionDao refundTransactionDao;

  @Mock
  MerchantUpdateDao merchantUpdateDao;

  @Mock
  ExecutedTransactionDao executedTransactionDao;

  @Mock
  TransactionReportDao transactionReportDao;

  @Test
  public void testSearchTransactions() throws ChatakAdminException {

    Mockito.when(transactionDao.getTransactions(getTransactionsListRequest, Constants.ONE_NINE_NINE_LONG))
        .thenReturn(Arrays.asList(transactions));
    response = transactionServiceImpl.searchTransactions(getTransactionsListRequest, Constants.ONE_NINE_NINE_LONG);
    Assert.assertNotNull(response);
  }

  @Test
  public void testSearchTransactionsBySettlementStatus() throws ChatakAdminException {

    Mockito.when(voidTransactionDao.getTransactionListToDashBoard(getTransactionsListRequest))
        .thenReturn(Arrays.asList(transactions));
    response =
        transactionServiceImpl.searchTransactionsBySettlementStatus(getTransactionsListRequest);
    Assert.assertNotNull(response);

  }


  @Test
  public void testGetAllTransactions() throws ChatakAdminException {

    PGTransaction pgTransactions = Mockito.mock(PGTransaction.class);
    PGAccount account = Mockito.mock(PGAccount.class);
    PGMerchant merchant = Mockito.mock(PGMerchant.class);
    Mockito.when(transactionDao.getTransactions(getTransactionsListRequest, Constants.ONE_NINE_NINE_LONG))
        .thenReturn(Arrays.asList(transactions));
    Mockito.when(transactionDao.getAllTransactions()).thenReturn(Arrays.asList(pgTransactions));
    Mockito.when(pgTransactions.getMerchantId()).thenReturn("111");
    Mockito.when(accountDao.getPgAccount(pgTransactions.getMerchantId())).thenReturn(account);
    Mockito.when(merchantUpdateDao.getMerchant(pgTransactions.getMerchantId()))
        .thenReturn(merchant);
    Mockito.when(pgTransactions.getTerminalId()).thenReturn("6");
    Mockito.when(account.getAccountNum()).thenReturn(Constants.ONE_NINE_NINE_LONG);
    Mockito.when(pgTransactions.getPosEntryMode()).thenReturn("00");
    response = transactionServiceImpl.getAllTransactions(getTransactionsListRequest,Constants.ONE_NINE_NINE_LONG );
    Assert.assertNotNull(response);

  }

  @Test
  public void testGetAllAccountsExecutedTransactions() {
    ReportsDTO list = Mockito.mock(ReportsDTO.class);
    Mockito.when(executedTransactionDao.getAllAccountsExecutedTransactionsReport())
        .thenReturn(Arrays.asList(list));
    List<ReportsDTO> list1 = transactionServiceImpl.getAllAccountsExecutedTransactions();
    Assert.assertNotNull(list1);
  }

  @Test
  public void testGetAllTransactionsOnMerchantCode() {
    PowerMockito.mockStatic(JsonUtil.class);
    ReportsDTO reportsDTOList = Mockito.mock(ReportsDTO.class);
    Mockito.when(transactionReportDao.getAllTransactionsReport(getTransactionsListRequest))
        .thenReturn(Arrays.asList(reportsDTOList));
    response = transactionServiceImpl.getAllTransactionsOnMerchantCode(getTransactionsListRequest);
    Assert.assertNotNull(response);

  }

  @Test
  public void testSearchTransactionsReport() throws ChatakAdminException {
    PowerMockito.mockStatic(JsonUtil.class);
    ReportsDTO reportList = Mockito.mock(ReportsDTO.class);
    Mockito.when(transactionReportDao.getTransactionListReport(getTransactionsListRequest))
        .thenReturn(Arrays.asList(reportList));
    response = transactionServiceImpl.searchTransactionsReport(getTransactionsListRequest);
    Assert.assertNotNull(response);
  }

  @Test
  public void testGetAllManualTransactionListReport() {
    ReportsDTO reportList = Mockito.mock(ReportsDTO.class);
    Mockito.when(transactionReportDao.getTransactionListReport(getTransactionsListRequest))
        .thenReturn(Arrays.asList(reportList));
    List<ReportsDTO> ManualTransactionListReport =
        transactionServiceImpl.getAllManualTransactionListReport(getTransactionsListRequest);
    Assert.assertNotNull(ManualTransactionListReport);
  }

  @Test
  public void testGetAllExecutedAccTransFeeOnDate() {
    ReportsDTO reportList = Mockito.mock(ReportsDTO.class);
    Mockito.when(executedTransactionDao.getAllExecutedAccFeeOnDate(getTransactionsListRequest))
        .thenReturn(Arrays.asList(reportList));
    List<ReportsDTO> ExecutedAccTransFeeOnDateList =
        transactionServiceImpl.getAllExecutedAccTransFeeOnDate(getTransactionsListRequest);
    Assert.assertNotNull(ExecutedAccTransFeeOnDateList);
  }

  @Test
  public void testGetSystemOverViewData() {
    ReportsDTO reportsDTO = Mockito.mock(ReportsDTO.class);
    Mockito.when(accountDao.getOverViewData()).thenReturn(Arrays.asList(reportsDTO));
    List<ReportsDTO> reportsDTO1 = transactionServiceImpl.getSystemOverViewData();
    Assert.assertNotNull(reportsDTO1);
  }

  @Test
  public void testGetVirtualAccountFeeLogOnDate() {
    ReportsDTO virtualFeeList = Mockito.mock(ReportsDTO.class);
    Mockito.when(transactionReportDao.getVirtualAccountFeeLogOnDate(getTransactionsListRequest))
        .thenReturn(Arrays.asList(virtualFeeList));
    Mockito.when(virtualFeeList.getStatus()).thenReturn("0");
    List<ReportsDTO> virtualFeeLogList =
        transactionServiceImpl.getVirtualAccountFeeLogOnDate(getTransactionsListRequest);
    Assert.assertNotNull(virtualFeeLogList);
  }

  @Test
  public void testSearchAllTransactions() throws ChatakAdminException {
    Mockito.when(voidTransactionDao.getSearchTransactions(getTransactionsListRequest))
        .thenReturn(Arrays.asList(transactions));
    response = transactionServiceImpl.searchAllTransactions(getTransactionsListRequest);
    Assert.assertNotNull(response);
  }

  @Test(expected = ChatakAdminException.class)
  public void testConfigureReqObj() throws ChatakAdminException {
    PowerMockito.mockStatic(JsonUtil.class);
    SettlementActionDTOList actionDTOList = Mockito.mock(SettlementActionDTOList.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    String requestObject = "abc";
    String[] removedTxns = {"abc", "def"};
    String jsonRequest = "{\"actionDTOs\":[" + requestObject + "]}";
    Mockito.when(session.getAttribute("selected_bulk_settlement_list_obj"))
        .thenReturn("selected_bulk_settlement_list_obj");
    Mockito.when(session.getAttribute("selected_bulk_settlement_list_obj"))
        .thenReturn(actionDTOList);
    Mockito.when(JsonUtil.convertJSONToObject(jsonRequest, SettlementActionDTOList.class))
        .thenReturn(actionDTOList);
    Mockito.when(tempList.getActionDTOs()).thenReturn(Arrays.asList(settlemetActionDTOList));
    transactionServiceImpl.configureReqObj(request, session, actionDTOList, requestObject,
        removedTxns);
  }

  @Test(expected = ChatakAdminException.class)
  public void testConfigureLitleReqObj() throws ChatakAdminException {
    PowerMockito.mockStatic(JsonUtil.class);
    PowerMockito.mockStatic(PGConstants.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    LitleEFTDTOsList litleEFTDTOsList = Mockito.mock(LitleEFTDTOsList.class);
    String requestObject = "abc";
    String[] removedTxns = {"abc", "def"};
    String jsonRequest = "{\"litleEFTDTOs\":[" + requestObject + "]}";
    Mockito.when(session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ))
        .thenReturn("selected_bulk_settlement_list_obj");
    Mockito.when(session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ))
        .thenReturn(litleEFTDTOsList);
    Mockito.when(JsonUtil.convertJSONToObject(jsonRequest, LitleEFTDTOsList.class))
        .thenReturn(litleEFTDTOsList);
    transactionServiceImpl.configureLitleReqObj(request, session, litleEFTDTOsList, requestObject,
        removedTxns);
  }

  @Test
  public void testPrepareAndBindTxnPopupData() throws ChatakAdminException {
    PowerMockito.mockStatic(JsonUtil.class);
    Transaction transactionList = Mockito.mock(Transaction.class);
    transactionServiceImpl.prepareAndBindTxnPopupData(Arrays.asList(transactionList));
  }

  @Test
  public void testSearchAccountTransactions() throws ChatakAdminException {
    Mockito.when(accountTransactionsDao.getAccountTransactions(getTransactionsListRequest))
        .thenReturn(response);
    response = transactionServiceImpl.searchAccountTransactions(getTransactionsListRequest);
    Assert.assertNotNull(response);
  }

  @Test
  public void testFetchTransactionDetails() throws ChatakAdminException {
    PGAccountTransactions accountTransactions = Mockito.mock(PGAccountTransactions.class);
    TransactionPopUpDataDto transactionPopUpDto = Mockito.mock(TransactionPopUpDataDto.class);
    String accountTransactionId = "1212";
    Mockito.when(accountTransactionsDao.getAccountTransactions(accountTransactionId))
        .thenReturn(Arrays.asList(accountTransactions));
    Mockito.when(accountTransactions.getPgTransactionId()).thenReturn("121");
    Mockito.when(refundTransactionDao.getTransactionPopUpDataDto(("121")))
        .thenReturn(transactionPopUpDto);
    transactionPopUpDto = transactionServiceImpl.fetchTransactionDetails(accountTransactionId);
    Assert.assertNotNull(transactionPopUpDto);
  }

  @Test
  public void testGetPartialRefundBalance() {
    PGTransaction pgTransaction = Mockito.mock(PGTransaction.class);
    PGMerchant pgMerchant = Mockito.mock(PGMerchant.class);
    Long refundedAmount = Constants.TWO_LONG;
    String accountTransactionId = "211";
    String merchantCode = "464355343928727";
    String pgTransactionId = "111";
    Mockito
        .when(
            accountTransactionsDao.getSaleAccountTransactionId(accountTransactionId, merchantCode))
        .thenReturn(pgTransactionId);
    Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(pgTransactionId))
        .thenReturn(refundedAmount);
    Mockito.when(transactionDao.getTransaction(merchantCode, "43928727", pgTransactionId))
        .thenReturn(pgTransaction);
    Mockito.when(merchantUpdateDao.getMerchantByCode(merchantCode)).thenReturn(pgMerchant);
    Mockito.when(pgTransaction.getTxnTotalAmount()).thenReturn(Constants.THREE_LONG);
    String partialRefundBalance =
        transactionServiceImpl.getPartialRefundBalance(accountTransactionId, merchantCode);
    Assert.assertNotNull(partialRefundBalance);
  }
}
