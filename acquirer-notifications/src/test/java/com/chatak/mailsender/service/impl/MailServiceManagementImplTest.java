package com.chatak.mailsender.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import com.chatak.mailsender.exception.PrepaidNotificationException;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceManagementImplTest {

	@InjectMocks
	MailServiceManagementImpl mailServiceManagementImpl = new MailServiceManagementImpl();

	@Mock
	JavaMailSender javaMailSender;

	@Mock
	MimeMessage message;

	@Test
	public void testSendMailPlainText() throws PrepaidNotificationException {
		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(message);
		mailServiceManagementImpl.sendMailPlainText("fromAddress", "text", "toAddress", "subject");
	}

	@Test
	public void testSendMailHtml() throws PrepaidNotificationException {
		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(message);
		mailServiceManagementImpl.sendMailHtml("fromAddress", "text", "toAddress", "subject");
	}

	@Test
	public void testSendMailwithAttachment() throws PrepaidNotificationException {
		Map<String, byte[]> attachmentMap = new HashMap<>();
		byte[] b = { 1, 1, 1 };
		attachmentMap.put("abc", b);
		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(message);
		mailServiceManagementImpl.sendMailwithAttachment("fromAddress", "text", "toAddress", "subject", attachmentMap);
	}

}
