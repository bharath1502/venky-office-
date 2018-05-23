package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.ShareModeEnum;
import com.chatak.pg.enums.TransactionType;

@RunWith(MockitoJUnitRunner.class)
public class RequestTest {

	@InjectMocks
	Request request = new Request();

	@Before
	public void setUp() {
		request.setCreatedBy("45");
		request.setOriginChannel("45");
		request.setMerchantId("45");
		request.setTerminalId("45");
		request.setTransactionType(TransactionType.AUTH);
		request.setEntryMode(EntryModeEnum.BARCODE);
		request.setShareMode(ShareModeEnum.PAY_SOMEONE);
		request.setPosEntryMode("45");
		request.setMode("45");
		request.setProcessorMid("45");

	}

	@Test
	public void testRequest() {
		Assert.assertEquals("45", request.getCreatedBy());
		Assert.assertEquals("45", request.getOriginChannel());
		Assert.assertEquals("45", request.getMerchantId());
		Assert.assertEquals("45", request.getTerminalId());
		Assert.assertEquals(TransactionType.AUTH, request.getTransactionType());
		Assert.assertEquals(EntryModeEnum.BARCODE, request.getEntryMode());
		Assert.assertEquals(ShareModeEnum.PAY_SOMEONE, request.getShareMode());
		Assert.assertEquals("45", request.getPosEntryMode());
		Assert.assertEquals("45", request.getMode());
		Assert.assertEquals("45", request.getProcessorMid());
	}
}
