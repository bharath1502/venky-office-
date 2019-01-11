package com.chatak.pay.service.impl;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.service.ReceiptService;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@Service
public class ReceiptServiceImpl implements ReceiptService {

	public static final String EMAIL_TEMPLATE_FILE_PATH = "prepaid.email.template.file.path";

	public static final String SOURCE_EMAIL_ID = "prepaid.from.email.id";

	private static Logger logger = LogManager.getLogger(ReceiptServiceImpl.class.getName());

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IVelocityTemplateCreator iVelocityTemplateCreator;

	@Autowired
	TransactionDao transactionDao;

	@Autowired
	MailServiceManagement mailingServiceManagement;

	@Override
	public PGTransaction findTransactionDetails(String txnId) {
		return transactionDao.getTransactionDetails(txnId);
	}

	@Override
	public Response sendEmail(Map<String, String> map, String vmFileName, String mailSubjectKey,
			String toEmailAddress) {
		Response response = new Response();
		try {
			String body = iVelocityTemplateCreator.createEmailTemplate(map,
					Properties.getProperty(EMAIL_TEMPLATE_FILE_PATH) + vmFileName);

			mailingServiceManagement.sendMailHtml(Properties.getProperty(SOURCE_EMAIL_ID), body, toEmailAddress,
					messageSource.getMessage(mailSubjectKey, null, LocaleContextHolder.getLocale()));
			response.setErrorCode(ChatakPayErrorCode.GEN_001.name());
		} catch (Exception e) {
			logger.info("Exiting:: ReceiptServiceImpl:: sendMail method");
			response.setErrorCode(ChatakPayErrorCode.GEN_002.name());
		}
		return response;
	}

}
