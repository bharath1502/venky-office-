package com.chatak.switches.prepaid;

import org.jpos.iso.ISOMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.switches.sb.exception.ChatakSwitchException;

@RunWith(MockitoJUnitRunner.class)
public class ChatakPrepaidSwitchTransactionTest {

	@InjectMocks
	ChatakPrepaidSwitchTransaction chatakPrepaidSwitchTransaction;

	@Test
	public void testInitConfig() {
		chatakPrepaidSwitchTransaction.initConfig("11", 1);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testAuth() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.auth(isoMsg);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testAuthAdviceRepeat() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.authAdviceRepeat(isoMsg);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testAuthAdvice() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.authAdvice(isoMsg);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testFinancial() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.financial(isoMsg);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testFinancialAdvice() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.financialAdvice(isoMsg);
	}

	@Test
	public void testFinancialAdviceRepeat() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.financialAdviceRepeat(isoMsg);
	}

	@Test
	public void testReversal() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.reversal(isoMsg);
	}

	@Test
	public void testReversalAdvice() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.reversalAdvice(isoMsg);
	}

	@Test
	public void testReversalAdviceRepeat() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.reversalAdviceRepeat(isoMsg);
	}

	@Test
	public void testSettlement() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.settlement(isoMsg);
	}

	@Test
	public void testNetwork() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.network(isoMsg);
	}

	@Test
	public void testNetworkAdvice() throws ChatakSwitchException {
		ISOMsg isoMsg = new ISOMsg();
		chatakPrepaidSwitchTransaction.networkAdvice(isoMsg);
	}
}
