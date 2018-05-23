package com.chatak.merchant.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.merchant.model.MerchantData;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.itextpdf.text.DocumentException;


@RunWith(MockitoJUnitRunner.class)
public class UserReportsFileExportsUtilTest {

  @InjectMocks
  UserReportsFileExportsUtil userReportsFileExportsUtil;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpServletRequest request;

  @Mock
  List<MerchantData> list;

  @Mock
  List<Transaction> transactionList;

  @Mock
  Transaction transaction;

  @Mock
  MerchantData merchantData;

  @Mock
  MessageSource messageSource;

  @Mock
  ServletOutputStream os;

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificAllTranXl() {
    String headerMessage = "headerMessage";
    merchantData();
    userReportsFileExportsUtil.downloadSpecificAllTranXl(list, response, headerMessage,
        messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificAllTranXlOutputStream() throws IOException {
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificAllTranXl(list, response, headerMessage,
        messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificAllTransPdf() throws IOException {
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificAllTransPdf(list, response, headerMessage,
        messageSource);
  }

  private void merchantData() {
    list = new ArrayList<>();
    merchantData = new MerchantData();
    merchantData.setUserName("userName");
    list.add(merchantData);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificAllTransPdfDocumentException() throws IOException {
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenThrow(DocumentException.class);
    userReportsFileExportsUtil.downloadSpecificAllTransPdf(list, response, headerMessage,
        messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificAllTransPdfIoException() throws IOException {
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenThrow(IOException.class);
    userReportsFileExportsUtil.downloadSpecificAllTransPdf(list, response, headerMessage,
        messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserTransactionsXl() throws IOException {
    traansaction();
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificUserTransactionsXl(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserTransactionsException() throws IOException {
    traansaction();
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenThrow(NullPointerException.class);
    userReportsFileExportsUtil.downloadSpecificUserTransactionsXl(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserTransactionsPdf() throws IOException {
    transactionList = new ArrayList<>();
    transaction = new Transaction();
    transaction.setAcqChannel("acqChannel");
    transactionList.add(transaction);
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificUserTransactionsPdf(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserTransactionsDocumentException() throws IOException {
    traansaction();
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenThrow(DocumentException.class);
    userReportsFileExportsUtil.downloadSpecificUserTransactionsPdf(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserTransactionsIoException() throws IOException {
    traansaction();
    String headerMessage = "headerMessage";
    merchantData();
    Mockito.when(response.getOutputStream()).thenThrow(IOException.class);
    userReportsFileExportsUtil.downloadSpecificUserTransactionsPdf(transactionList, response,
        headerMessage, messageSource);
  }


  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserStatementXl() throws IOException {
    traansaction();
    String headerMessage = "headerMessage";
    transaction.setTransaction_type(Constants.DEBIT);
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificUserStatementXl(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserStatementElse() throws IOException {
    traansaction();
    String headerMessage = "headerMessage";
    transaction.setTransaction_type("credit");
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificUserStatementXl(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserStatementPdf() throws IOException {
    String headerMessage = "headerMessage";
    traansaction();
    transaction.setTransaction_type(Constants.DEBIT);
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificUserStatementPdf(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserStatementPdfElse() throws IOException {
    String headerMessage = "headerMessage";
    traansaction();
    merchantData();
    Mockito.when(response.getOutputStream()).thenReturn(os);
    userReportsFileExportsUtil.downloadSpecificUserStatementPdf(transactionList, response,
        headerMessage, messageSource);
  }

  private void traansaction() {
    transactionList = new ArrayList<>();
    transaction = new Transaction();
    transaction.setTransaction_type("credit");
    transaction.setAcqChannel("acqChannel");
    transactionList.add(transaction);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserStatementPdfDocumentException() throws IOException {
    String headerMessage = "headerMessage";
    traansaction();
    merchantData();
    Mockito.when(response.getOutputStream()).thenThrow(DocumentException.class);
    userReportsFileExportsUtil.downloadSpecificUserStatementPdf(transactionList, response,
        headerMessage, messageSource);
  }

  @SuppressWarnings({"static-access", "unchecked"})
  @Test
  public void testDownloadSpecificUserStatementPdfException() throws IOException {
    String headerMessage = "headerMessage";
    traansaction();
    merchantData();
    Mockito.when(response.getOutputStream()).thenThrow(NullPointerException.class);
    userReportsFileExportsUtil.downloadSpecificUserStatementPdf(transactionList, response,
        headerMessage, messageSource);
  }
}
