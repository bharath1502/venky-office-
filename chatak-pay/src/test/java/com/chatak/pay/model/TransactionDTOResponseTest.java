package com.chatak.pay.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDTOResponseTest {

	@InjectMocks
	TransactionDTOResponse transactionDTOResponse = new TransactionDTOResponse();

	@Before
	public void setUp() {
		List<TransactionDTO> transactionDTO = new ArrayList<>();
		transactionDTOResponse.setTransactionDTO(transactionDTO);

	}

	@Test
	public void testterminals() {
		List<TransactionDTO> transactionDTO = new ArrayList<>();
		Assert.assertEquals(transactionDTO, transactionDTOResponse.getTransactionDTO());
	}

}
