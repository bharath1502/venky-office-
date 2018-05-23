package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TerminalsTest {
	
	@InjectMocks
	Terminals terminals = new Terminals();

	@Before
	public void setUp() {
		terminals.setMerchantId(Long.parseLong("43"));
		terminals.setValidFromDate("abc");
		terminals.setTerminalCode(Long.parseLong("43"));
		terminals.setValidToDate("456");
		terminals.setId(Long.parseLong("543"));
		terminals.setStatus("534");
		terminals.setMerchantCode(Long.parseLong("543"));

	}

	@Test
	public void testterminals() {
		Assert.assertEquals(Long.valueOf("43"), terminals.getMerchantId());
		Assert.assertEquals("abc", terminals.getValidFromDate());
		Assert.assertEquals(Long.valueOf("43"), terminals.getTerminalCode());
		Assert.assertEquals("456", terminals.getValidToDate());
		Assert.assertEquals(Long.valueOf("543"), terminals.getId());
		Assert.assertEquals("534", terminals.getStatus());
		Assert.assertEquals(Long.valueOf("543"), terminals.getMerchantCode());

	}
}
