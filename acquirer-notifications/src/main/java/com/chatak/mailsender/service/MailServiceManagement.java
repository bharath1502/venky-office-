package com.chatak.mailsender.service;

import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.model.Response;

public interface MailServiceManagement {

	/**
	 * SERVICE API to send mail in Plain Text format to the specified toAddress.
	 * 
	 * @param text
	 * @param toAddress
	 * @param subject
	 * @return
	 * @throws MailGenericException
	 */
	@Async
	public Response sendMailPlainText(String fromAddress, String text,
			String toAddress, String subject) throws PrepaidNotificationException;

	/**
	 * 
	 * SERVICE API to send mail in HTML format to the specified toAddress.
	 * 
	 * @param fromAddress
	 * @param text
	 * @param toAddress
	 * @param subject
	 * @return
	 * @throws MailGenericException
	 */
	@Async
	public Response sendMailHtml(String fromAddress, String text,
			String toAddress, String subject) throws PrepaidNotificationException;

	/**
	 * SERVICE API to send mail with attachments to the specified toAddress.
	 * 
	 * @param fromAddress
	 * @param text
	 * @param toAddress
	 * @param subject
	 * @param attachmentMap
	 * @return
	 * @throws MailGenericException
	 */
	@Async
	public Response sendMailwithAttachment(String fromAddress, String text,
			String toAddress, String subject, Map<String, byte[]> attachmentMap)
			throws PrepaidNotificationException;

}
