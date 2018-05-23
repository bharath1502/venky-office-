package com.chatak.merchant.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class TerminalSearchResponseTest {

	@InjectMocks
	TerminalSearchResponse terminalSearchResponse = new TerminalSearchResponse();

	private List<Terminals> terminalList;

	@Before
	public void setUp() {
		terminalSearchResponse.setTerminalList(terminalList);
	}

	@Test
	public void testTerminalSearchResponse() {

		Assert.assertEquals(terminalList, terminalSearchResponse.getTerminalList());

	}

}
