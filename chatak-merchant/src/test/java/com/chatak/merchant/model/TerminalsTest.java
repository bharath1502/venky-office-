package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class TerminalsTest {

	@InjectMocks
	Terminals terminals = new Terminals();

	@Before
	public void setUp() {
		terminals.setMerchantId(Long.parseLong("123"));
		terminals.setTerminalCode(Long.parseLong("1213"));
		terminals.setValidFromDate("1215");
		terminals.setValidToDate("1216");
		terminals.setStatus("status");
		terminals.setId(Long.parseLong("687"));
		terminals.setMerchantCode(Long.parseLong("9080"));
	}

	@Test
	public void testTerminals() {

		Assert.assertEquals(Long.valueOf("123"), terminals.getMerchantId());
		Assert.assertEquals(Long.valueOf("1213"), terminals.getTerminalCode());
		Assert.assertEquals("1215", terminals.getValidFromDate());
		Assert.assertEquals("1216", terminals.getValidToDate());
		Assert.assertEquals("status", terminals.getStatus());
		Assert.assertEquals(Long.valueOf("687"), terminals.getId());
		Assert.assertEquals(Long.valueOf("9080"), terminals.getMerchantCode());

	}
}
