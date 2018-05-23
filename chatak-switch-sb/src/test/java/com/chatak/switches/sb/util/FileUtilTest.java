package com.chatak.switches.sb.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class FileUtilTest {

	@InjectMocks
	FileUtil fileUtil;
	
	@Test
	public void testGetConfigFileInputStream() {
		fileUtil.getConfigFileInputStream();
	}
	
	@Test
	public void testGetJPOSPackagerFileInputStream() {
		fileUtil.getJPOSPackagerFileInputStream();
	}
	
	@Test
	public void testGetJPOSChatakPackagerFileInputStream() {
		fileUtil.getJPOSChatakPackagerFileInputStream();
	}
	
	@Test
	public void testGetLitleProperties() {
		fileUtil.getLitleProperties("xyz");
	}

}
