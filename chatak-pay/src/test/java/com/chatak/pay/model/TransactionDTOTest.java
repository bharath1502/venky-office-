package com.chatak.pay.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDTOTest {

	@InjectMocks
	TransactionDTO transactionDTO = new TransactionDTO();

	@Before
	public void setUp() {
		List<String> list = new ArrayList<>();
		transactionDTO.setId(Long.parseLong("123"));
		transactionDTO.setAccountId(Long.parseLong("123"));
		transactionDTO.setTransNumber("abcd");
		transactionDTO.setAgentAccountNumber("534");
		transactionDTO.setCustomerAccountNumber("abc");
		transactionDTO.setCardNumber("abcd");
		transactionDTO.setCardId(Long.parseLong("123"));
		transactionDTO.setAgentANI("534");
		transactionDTO.setTxnType("abc");
		transactionDTO.setTxnStatus("abcd");
		transactionDTO.setTxnAmount(Double.parseDouble("5434"));
		transactionDTO.setFeeAmount(Double.parseDouble("543"));
		transactionDTO.setTxnCode("abcd");
		transactionDTO.setTxnDescription("abcd");
		transactionDTO.setTxnRefNumber("534");
		transactionDTO.setRemarks("abc");
		transactionDTO.setFeeType("abcd");
		transactionDTO.setToDate("abcd");
		transactionDTO.setFromDate("534");
		transactionDTO.setAccountFlag("123");
		transactionDTO.setCreatedDate(new Timestamp(Long.parseLong("43")));
		transactionDTO.setUpdatedDate(new Timestamp(Long.parseLong("43")));
		transactionDTO.setTxnValueFrom(Double.parseDouble("5434"));
		transactionDTO.setTxnValueTo(Double.parseDouble("5434"));
		transactionDTO.setAvailableBalance(Double.parseDouble("5434"));
		transactionDTO.setSecurityKey("123");
		transactionDTO.setApprovalType("534");
		transactionDTO.setDescription1("abcd");
		transactionDTO.setDescription2("abcd");
		transactionDTO.setDescription3("abcd");
		transactionDTO.setDescription4("abcd");
		transactionDTO.setCurrentBalance(Double.parseDouble("5434"));
		transactionDTO.setApproverId("abcd");
		transactionDTO.setTxnDate("534");
		transactionDTO.setTxnCodes(list);
		transactionDTO.setUpdateViewDetailsFlag("abcd");
		transactionDTO.setCardChangeStatus("abcd");
		transactionDTO.setLoadCardHoursFlag("534");
		transactionDTO.setCardLoadApprovalType("123");
		transactionDTO.setCardLoadApprovalName("123");
		transactionDTO.setApproverSecurityKey("abcd");
		transactionDTO.setCardLoadApprovalFlag("534");
		transactionDTO.setRrn("abc");
		transactionDTO.setAdditionalData("abcd");
		transactionDTO.setAuthorizationIdentity("123");
		transactionDTO.setTxnDesc("534");
		transactionDTO.setSrcAccountType("abc");
		transactionDTO.setTgtAccountType("abcd");
		transactionDTO.setAgentUserLoginName("abc");
		transactionDTO.setCustomerPhoneNumber("abc");
	}

	@Test
	public void testTransactionDTO() {
		List<String> list = new ArrayList<>();
		Assert.assertEquals(Long.valueOf("123"), transactionDTO.getId());
		Assert.assertEquals(Long.valueOf("123"), transactionDTO.getAccountId());
		Assert.assertEquals("abcd", transactionDTO.getTransNumber());
		Assert.assertEquals("534", transactionDTO.getAgentAccountNumber());
		Assert.assertEquals("abc", transactionDTO.getCustomerAccountNumber());
		Assert.assertEquals("abcd", transactionDTO.getCardNumber());
		Assert.assertEquals(Long.valueOf("123"), transactionDTO.getCardId());
		Assert.assertEquals("534", transactionDTO.getAgentANI());
		Assert.assertEquals("abc", transactionDTO.getTxnType());
		Assert.assertEquals("abcd", transactionDTO.getTxnStatus());
		Assert.assertEquals(Double.valueOf("5434"), transactionDTO.getTxnAmount());
		Assert.assertEquals(Double.valueOf("543"), transactionDTO.getFeeAmount());
		Assert.assertEquals("abcd", transactionDTO.getTxnCode());
		Assert.assertEquals("abcd", transactionDTO.getTxnDescription());
		Assert.assertEquals("534", transactionDTO.getTxnRefNumber());
		Assert.assertEquals("abc", transactionDTO.getRemarks());
		Assert.assertEquals("abcd", transactionDTO.getFeeType());
		Assert.assertEquals("abcd", transactionDTO.getToDate());
		Assert.assertEquals("534", transactionDTO.getFromDate());
		Assert.assertEquals("123", transactionDTO.getAccountFlag());
		Assert.assertEquals(new Timestamp(Long.parseLong("43")), transactionDTO.getCreatedDate());
		Assert.assertEquals(new Timestamp(Long.parseLong("43")), transactionDTO.getUpdatedDate());
		Assert.assertEquals(Double.valueOf("5434"), transactionDTO.getTxnValueFrom());
		Assert.assertEquals(Double.valueOf("5434"), transactionDTO.getTxnValueTo());
		Assert.assertEquals(Double.valueOf("5434"), transactionDTO.getAvailableBalance());
		Assert.assertEquals("123", transactionDTO.getSecurityKey());
		Assert.assertEquals("534", transactionDTO.getApprovalType());
		Assert.assertEquals("abcd", transactionDTO.getDescription1());
		Assert.assertEquals("abcd", transactionDTO.getDescription2());
		Assert.assertEquals("abcd", transactionDTO.getDescription3());
		Assert.assertEquals("abcd", transactionDTO.getDescription4());
		Assert.assertEquals(Double.valueOf("5434"), transactionDTO.getCurrentBalance());
		Assert.assertEquals("abcd", transactionDTO.getApproverId());
		Assert.assertEquals("534", transactionDTO.getTxnDate());
		Assert.assertEquals(list, transactionDTO.getTxnCodes());
		Assert.assertEquals("abcd", transactionDTO.getUpdateViewDetailsFlag());
		Assert.assertEquals("abcd", transactionDTO.getCardChangeStatus());
		Assert.assertEquals("534", transactionDTO.getLoadCardHoursFlag());
		Assert.assertEquals("123", transactionDTO.getCardLoadApprovalType());
		Assert.assertEquals("123", transactionDTO.getCardLoadApprovalName());
		Assert.assertEquals("abcd", transactionDTO.getApproverSecurityKey());
		Assert.assertEquals("534", transactionDTO.getCardLoadApprovalFlag());
		Assert.assertEquals("abc", transactionDTO.getRrn());
		Assert.assertEquals("abcd", transactionDTO.getAdditionalData());
		Assert.assertEquals("123", transactionDTO.getAuthorizationIdentity());
		Assert.assertEquals("534", transactionDTO.getTxnDesc());
		Assert.assertEquals("abc", transactionDTO.getSrcAccountType());
		Assert.assertEquals("abcd", transactionDTO.getTgtAccountType());
		Assert.assertEquals("abc", transactionDTO.getAgentUserLoginName());
		Assert.assertEquals("abc", transactionDTO.getCustomerPhoneNumber());
	}

}
