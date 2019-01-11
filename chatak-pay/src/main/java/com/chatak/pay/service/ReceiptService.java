package com.chatak.pay.service;

import java.util.Map;

import com.chatak.pay.controller.model.Response;
import com.chatak.pg.acq.dao.model.PGTransaction;

public interface ReceiptService {

	public PGTransaction findTransactionDetails(String txnId);

	public Response sendEmail(Map<String, String> map, String vmFileName, String mailSubjectKey, String toEmailAddress);
}
