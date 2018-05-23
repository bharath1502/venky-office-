package com.chatak.switches.jpos;

import org.jpos.iso.ISOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SwitchISOPackagerTest {

	@InjectMocks
	SwitchISOPackager switchISOPackager;

	@Test
	public void testGetGenericPackager() throws ISOException {
		switchISOPackager.getGenericPackager();
	}

	@Test
	public void testGetChatakGenericPackager() throws ISOException {
		switchISOPackager.getChatakGenericPackager();
	}

}
