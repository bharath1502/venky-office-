package com.chatak.switches.prepaid;

import org.jpos.iso.ISOMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.switches.sb.exception.ChatakSwitchException;

@RunWith(MockitoJUnitRunner.class)
public class PulseSwitchTransactionTest {

	@InjectMocks
	PulseSwitchTransaction pulseSwitchTransaction;

	@Mock
	ISOMsg isoMsg;

	@Test(expected = ChatakSwitchException.class)
	public void testAuth() throws ChatakSwitchException {
	    isoMsg = new ISOMsg();
		pulseSwitchTransaction.auth(isoMsg);
	}

	@Test
	public void testInitConfig() throws ChatakSwitchException {
		pulseSwitchTransaction.initConfig("abc", 1);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testAuthAdviceRepeat() throws ChatakSwitchException {
	    isoMsg = new ISOMsg();
		pulseSwitchTransaction.authAdviceRepeat(isoMsg);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testAuthAdvice() throws ChatakSwitchException {
	    isoMsg = new ISOMsg();
		pulseSwitchTransaction.authAdvice(isoMsg);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testFinancialAdvice() throws ChatakSwitchException {
	    isoMsg = new ISOMsg();
		pulseSwitchTransaction.financialAdvice(isoMsg);
	}

	@Test(expected = ChatakSwitchException.class)
	public void testFinancial() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.financial(isoMsg);
	}

	@Test
	public void testFinancialAdviceRepeat() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.financialAdviceRepeat(isoMsg);
	}

	@Test
	public void testReversal() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.reversal(isoMsg);
	}

	@Test
	public void testReversalAdvice() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.reversalAdvice(isoMsg);
	}

	@Test
	public void testReversalAdviceRepeat() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.reversalAdviceRepeat(isoMsg);
	}

	@Test
	public void testSettlement() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.settlement(isoMsg);
	}

	@Test
	public void testNetwork() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.network(isoMsg);
	}

	@Test
	public void testNetworkAdvice() throws ChatakSwitchException {
        isoMsg = new ISOMsg();
		pulseSwitchTransaction.networkAdvice(isoMsg);
	}

	@Test
	public void testInitConfigP() throws ChatakSwitchException {
		pulseSwitchTransaction.initConfig();
	}

}
