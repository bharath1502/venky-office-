/**
 * 
 */
package com.chatak.acquirer.admin.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.Transaction;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:15:34 PM
 * @version 1.0
 */
public class TransactionResponseTest {

  private TransactionResponse response;

  @Mock
  private List<Transaction> transactionList;

  @Mock
  private List<AccountTransactionDTO> accountTxnList;

  @Before
  public void init() {
    response = new TransactionResponse();
    response.setTransactionList(transactionList);
    response.setAccountTxnList(accountTxnList);
  }

  @Test
  public void testTransactionResponse() {
    Assert.assertEquals(transactionList, response.getTransactionList());
    Assert.assertEquals(accountTxnList, response.getAccountTxnList());
  }

}
