package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TerminalDataTest {

	@InjectMocks
	TerminalData terminalData = new TerminalData();

	@Before
	public void setUp() {
		terminalData.setMerchantId(Long.parseLong("45"));
		terminalData.setTerminalCode(Long.parseLong("45"));
		terminalData.setValidFromDate("23");
		terminalData.setValidToDate("23");
		terminalData.setStatus("23");
		terminalData.setId(Long.parseLong("45"));
		terminalData.setPageIndex(Integer.parseInt("45"));
		terminalData.setPageSize(Integer.parseInt("45"));
		terminalData.setNoOfRecords(Integer.parseInt("45"));
	}

	@Test
	public void testTerminalData() {
		Assert.assertEquals(Long.valueOf("45"), terminalData.getMerchantId());
		Assert.assertEquals(Long.valueOf("45"), terminalData.getTerminalCode());
		Assert.assertEquals("23", terminalData.getValidFromDate());
		Assert.assertEquals("23", terminalData.getValidToDate());
		Assert.assertEquals("23", terminalData.getStatus());
		Assert.assertEquals(Long.valueOf("45"), terminalData.getId());
		Assert.assertEquals(Integer.valueOf("45"), terminalData.getPageIndex());
		Assert.assertEquals(Integer.valueOf("45"), terminalData.getPageSize());
		Assert.assertEquals(Integer.valueOf("45"), terminalData.getNoOfRecords());

	}
}
