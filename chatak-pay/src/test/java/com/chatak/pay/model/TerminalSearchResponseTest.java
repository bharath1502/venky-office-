package com.chatak.pay.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TerminalSearchResponseTest {

	@InjectMocks
	TerminalSearchResponse terminalSearchResponse = new TerminalSearchResponse();

	@Before
	public void setUp() {
		List<Terminals> terminalLis = new ArrayList<>();
		terminalSearchResponse.setTerminalList(terminalLis);

	}

	@Test
	public void testterminals() {
		List<Terminals> terminalLis = new ArrayList<>();
		Assert.assertEquals(terminalLis, terminalSearchResponse.getTerminalList());
	}

}
