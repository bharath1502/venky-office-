package com.chatak.switches.sb.exception;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.chatak.pg.util.Constants;

public class ChatakSwitchExceptionTest {

	private static Logger logger = Logger.getLogger(ChatakSwitchException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakSwitchException chatakSwitchException = new ChatakSwitchException();

	ChatakSwitchException chatakSwitchException1 = new ChatakSwitchException(Constants.EXCEPTION);

	ChatakSwitchException chatakSwitchException2 = new ChatakSwitchException(throwable);

	ChatakSwitchException chatakSwitchException3 = new ChatakSwitchException(Constants.EXCEPTION, throwable);

	ChatakSwitchException chatakSwitchException4 = new ChatakSwitchException(Constants.EXCEPTION, throwable, true, true);

	@Test
	public void testChatakSwitchException() {
		logger.info("ERROR :: ChatakSwitchExceptionTest :: testChatakSwitchException:");

	}

}
