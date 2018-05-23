package com.chatak.mailsender.model;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailSendRequestTest {
	
	private static final String ERROR_CODE = "errorCode";

	@InjectMocks
	MailSendRequest mailSendRequest = new MailSendRequest();
	
	@Mock
	Map<String, byte[]> attachmentMap;

	@Before
	public void setUp() {
		mailSendRequest.setText(ERROR_CODE);
		mailSendRequest.setToAddress("errorMessage");
		mailSendRequest.setSubject(ERROR_CODE);
		mailSendRequest.setAttachmentMap(attachmentMap);
	}

	@Test
	public void testMailSendRequest() {
		Assert.assertEquals(ERROR_CODE, mailSendRequest.getText());
		Assert.assertEquals("errorMessage", mailSendRequest.getToAddress());
		Assert.assertEquals(ERROR_CODE, mailSendRequest.getSubject());
		Assert.assertEquals(attachmentMap, mailSendRequest.getAttachmentMap());
	}

}
