package com.chatak.switches.jpos.util;

import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JPOSUtilTest {

	@InjectMocks
	JPOSUtil jPOSUtil;

	@Mock
	ISOMsg isoMsg;

	@Mock
	Logger logger;

	@Test
	public void testGetISOValidator() throws ISOException {
		jPOSUtil.getISOValidator();
	}

	@Test
	public void testGetChatakISOValidator() throws ISOException {
		jPOSUtil.getChatakISOValidator();
	}

	@Test
	public void testLogISODataISOMsg() throws ISOException {
		jPOSUtil.logISOData(isoMsg, logger);
	}

	@Test
	public void testLogISOData() {
		jPOSUtil.logISOData(isoMsg);
	}

	@Test
	public void testPrintData() {
		byte[] data = { 1, 1, 1, 1 };
		jPOSUtil.printData("a", data, 1, logger);
	}

}
