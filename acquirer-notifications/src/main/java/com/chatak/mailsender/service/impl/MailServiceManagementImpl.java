package com.chatak.mailsender.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.model.Response;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.prepaid.notification.constants.ErrorCode;

/**
 * Implementation Service class for send mail in plain-text or HTML formats
 * along with attachments.
 * 
 * @author Girmiti Software
 * @date 18-Nov-2014 2:40:39 PM
 * @version 1.0
 */
@Service("mailServiceManagement")
public class MailServiceManagementImpl implements MailServiceManagement {

	private final String CLASS_NAME = MailServiceManagementImpl.class
			.getSimpleName();

	private static Logger logger = Logger.getLogger(MailServiceManagementImpl.class);

	@Autowired
	JavaMailSender javaMailSender;

	/**
	 * Method to send mail in Plain-Text format to toAddress.
	 * 
	 * @param fromAddress
	 * @param text
	 * @param toAddress
	 * @param subject
	 * @return
	 * @throws MailGenericException
	 * @throws UnsupportedEncodingException
	 */
	public Response sendMailPlainText(String fromAddress, String text,
			String toAddress, String subject)
			throws PrepaidNotificationException {
		logger.info("Entering:: " + CLASS_NAME + " : " + "sendMailPlainText");

		Response response = new Response();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(new InternetAddress(fromAddress));
			helper.setFrom(fromAddress);
			helper.setTo(toAddress);
			helper.setText(text);
			helper.setSubject(subject);
		} catch (MessagingException e) {
			validateMessagingException(response, e);
		}

		try {
			validateJavaMailSender(response, message);
		} catch (MailException e) {
			validateMailException(response, e);
		}

		logger.info("Exiting:: " + CLASS_NAME + " : " + "sendMailPlainText");
		return response;
	}

	private void validateMailException(Response response, MailException e) {
		logger.error("ERROR:: " + CLASS_NAME + e.getMessage());
		response.setErrorCode(ErrorCode.MAIL_SEND_ERROR_CODE);
		response.setErrorMessage(ErrorCode.MAIL_SEND_ERROR_MESSAGE);
	}

	private void validateJavaMailSender(Response response, MimeMessage message) {
		javaMailSender.send(message);
		response.setErrorCode(ErrorCode.SUCCESS_CODE);
		response.setErrorMessage(ErrorCode.SUCCESS_MESSAGE);
	}

	private void validateMessagingException(Response response, MessagingException e) {
		logger.error("ERROR:: " + CLASS_NAME + e.getMessage());
		response.setErrorCode(ErrorCode.CONFIGURATION_ERROR_CODE);
		response.setErrorMessage(ErrorCode.CONFIGURATION_ERROR_MESSAGE);
	}

	/**
	 * Method to send mail in HTML format to toAddress.
	 * 
	 * @param fromAddress
	 * @param text
	 * @param toAddress
	 * @param subject
	 * @return
	 * @throws MailGenericException
	 */
	public Response sendMailHtml(String fromAddress, String text,
			String toAddress, String subject)
			throws PrepaidNotificationException {
		
		logger.info("Entering:: " + CLASS_NAME + " : " + "sendMailHtml");
		Response response = new Response();
		MimeMessage mimeMessage = null;
		MimeMessageHelper mimeMessageHelper;

		try {
			mimeMessage = javaMailSender.createMimeMessage();
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(new InternetAddress(fromAddress));
			mimeMessage.setContent(text, "text/html");
			mimeMessageHelper.setTo(toAddress);
			mimeMessageHelper.setSubject(subject);
		} catch (MessagingException e) {
			validateMessagingException(response, e);
		}

		try {
			validateJavaMailSender(response, mimeMessage);
		} catch (MailException e) {
			validateMailException(response, e);
		}

 		logger.info("Exiting:: " + CLASS_NAME + " : " + "sendMailHtml");
		return response;
	}

	/**
	 * Method to send mail with attachments to toAddress specified.
	 * 
	 * @param fromAddress
	 * @param text
	 * @param toAddress
	 * @param subject
	 * @param attachmentMap
	 * @return
	 * @throws MailGenericException
	 */
	public Response sendMailwithAttachment(String fromAddress, String text,
			String toAddress, String subject, Map<String, byte[]> attachmentMap)
			throws PrepaidNotificationException {
		logger.info("Entering:: " + CLASS_NAME + " : " + "sendMailwithAttachment");

		Response response = new Response();

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {

			helper = new MimeMessageHelper(message, true);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(text);
			helper.setFrom(new InternetAddress(fromAddress));

			for (Map.Entry<String, byte[]> entry : attachmentMap.entrySet()) {
				String fileName = entry.getKey();
				byte[] file = entry.getValue();

				helper.addAttachment(fileName, new ByteArrayResource(file));
			}
		} catch (MessagingException e) {
			validateMessagingException(response, e);
		}

		try {
			validateJavaMailSender(response, message);
		} catch (MailException e) {
			validateMailException(response, e);
		}

		logger.info("Exiting:: " + CLASS_NAME + " : " + "sendMailwithAttachment");
		return response;
	}
}
