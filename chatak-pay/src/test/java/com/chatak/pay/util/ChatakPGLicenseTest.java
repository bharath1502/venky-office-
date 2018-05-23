package com.chatak.pay.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChatakPGLicenseTest {

	private Logger logger = Logger.getLogger(ChatakPGLicense.class);

	@InjectMocks
	ChatakPGLicense chatakPGLicense;

	@Test
	public void testChatakPGLicense() {
		logger.info("Info:: ChatakPGLicenseTest:: testChatakPGLicense method");
	}

}
