package com.chatak.switches.sb.util;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.switches.prepaid.ChatakPrepaidSwitchTransaction;
import com.chatak.switches.sb.SwitchTransaction;

@RunWith(MockitoJUnitRunner.class)
public class TestISOTest {

	private static Logger logger = Logger.getLogger(TestISO.class);

	@InjectMocks
	TestISO testISO;

	SwitchTransaction switchTransaction;

	Runnable task;

	@Before
	public void setUp() {
		switchTransaction = new ChatakPrepaidSwitchTransaction();

	}

	@Test
	public void testMain() {
		String[] args = { "abc", "bcd", "cde" };
		try {
			testISO.main(args);
		} catch (Exception e) {
			logger.error("ERROR:: TestISOTest:: testMain method", e);

		}
	}

}
