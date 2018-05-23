package com.chatak.merchant.controller.model;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class RequestTest {

	@InjectMocks
	Request request = new Request();

	private Timestamp updatedDate;

	private Timestamp createdDate;

	@Before
	public void setUp() {
		request.setPageIndex(Integer.parseInt("43"));
		request.setCreatedBy("createdBy");
		request.setPageSize(Integer.parseInt("78"));
		request.setNoOfRecords(Integer.parseInt("89"));
		request.setUpdatedBy("updatedBy");
		request.setUpdatedDate(updatedDate);
		request.setCreatedDate(createdDate);
	}

	@Test
	public void testRequest() {
		Assert.assertEquals(Integer.valueOf("43"), request.getPageIndex());
		Assert.assertEquals("createdBy", request.getCreatedBy());
		Assert.assertEquals(Integer.valueOf("78"), request.getPageSize());
		Assert.assertEquals(Integer.valueOf("89"), request.getNoOfRecords());
		Assert.assertEquals("updatedBy", request.getUpdatedBy());
		Assert.assertEquals(updatedDate, request.getUpdatedDate());
		Assert.assertEquals(createdDate, request.getCreatedDate());

	}

}
