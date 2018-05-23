package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.service.PGMerchantService;
import com.chatak.pg.user.bean.AddMerchantBankRequest;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.DeleteMerchantRequest;
import com.chatak.pg.user.bean.GetMerchantBankDetailsRequest;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.UpdateMerchantRequest;

@RunWith(MockitoJUnitRunner.class)
public class MerchantRestControllerTest {

	@InjectMocks
	MerchantRestController merchantRestController = new MerchantRestController();

	@Mock
	private PGMerchantService merchantService;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	HttpSession session;

	@Test
	public void testAddMerchant() {
		AddMerchantRequest addMerchantRequest=new AddMerchantRequest();
		merchantRestController.addMerchant(request, response, session, addMerchantRequest);
	}
	
	@Test
	public void testUpdateMerchant() {
		UpdateMerchantRequest updateMerchantRequest=new UpdateMerchantRequest();
		merchantRestController.updateMerchant(request, response, session, updateMerchantRequest);
	}
	
	@Test
	public void testDeleteMerchant() {
		DeleteMerchantRequest deleteMerchantRequest=new DeleteMerchantRequest();
		merchantRestController.deleteMerchant(request, response, session, deleteMerchantRequest);
	}
	
	@Test
	public void testGetMerchantList() {
		GetMerchantListRequest getMerchantListRequest=new GetMerchantListRequest();
		merchantRestController.getMerchantList(request, response, session, getMerchantListRequest);
	}
	
	@Test
	public void testAddMerchantBank() {
		 AddMerchantBankRequest addMerchantBankRequest=new AddMerchantBankRequest();
		merchantRestController.addMerchantBank(request, response, session, addMerchantBankRequest);
	}
	
	@Test
	public void testGetMerchantBankDetails() {
		GetMerchantBankDetailsRequest getMerchantBankDetailsRequest=new GetMerchantBankDetailsRequest();
		merchantRestController.getMerchantBankDetails(request, response, session, getMerchantBankDetailsRequest);
	}


}
