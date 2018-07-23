package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.service.PGMerchantService;
import com.chatak.pg.user.bean.AddMerchantBankRequest;
import com.chatak.pg.user.bean.AddMerchantBankResponse;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.DeleteMerchantRequest;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantBankDetailsRequest;
import com.chatak.pg.user.bean.GetMerchantBankDetailsResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

@RestController
@RequestMapping(value = "/merchantService", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class MerchantRestController {

  @Autowired
  private PGMerchantService merchantService;

  /**
   * @param request
   * @param response
   * @param session
   * @param addMerchantRequest
   * @return
   */
  @RequestMapping(value = "/addMerchant", method = RequestMethod.POST)
  public AddMerchantResponse addMerchant(HttpServletRequest request,
                                         HttpServletResponse response,
                                         HttpSession session,
                                         @RequestBody
                                         AddMerchantRequest addMerchantRequest) {
    return merchantService.addMerchant(addMerchantRequest);
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param updateMerchantRequest
   * @return
   */
  @RequestMapping(value = "/updateMerchant", method = RequestMethod.POST)
  public UpdateMerchantResponse updateMerchant(HttpServletRequest request,
                                               HttpServletResponse response,
                                               HttpSession session,
                                               @RequestBody
                                               UpdateMerchantRequest updateMerchantRequest) {
    return merchantService.updateMerchant(updateMerchantRequest);

  }

  /**
   * @param request
   * @param response
   * @param session
   * @param deleteMerchantRequest
   * @return
   */
  @RequestMapping(value = "/deleteMerchant", method = RequestMethod.POST)
  public DeleteMerchantResponse deleteMerchant(HttpServletRequest request,
                                               HttpServletResponse response,
                                               HttpSession session,
                                               @RequestBody
                                               DeleteMerchantRequest deleteMerchantRequest) {
    return merchantService.deleteMerchant(deleteMerchantRequest);

  }

  /**
   * @param request
   * @param response
   * @param session
   * @param getMerchantListRequest
   * @return
   */
  @RequestMapping(value = "/getMerchantList", method = RequestMethod.POST)
  public GetMerchantListResponse getMerchantList(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 HttpSession session,
                                                 @RequestBody
                                                 GetMerchantListRequest getMerchantListRequest) {
    return merchantService.getMerchantList(getMerchantListRequest);

  }

  /**
   * @param request
   * @param response
   * @param session
   * @param addMerchantBankRequest
   * @return
   */
  @RequestMapping(value = "/addMerchantBank", method = RequestMethod.POST)
  public AddMerchantBankResponse addMerchantBank(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 HttpSession session,
                                                 @RequestBody
                                                 AddMerchantBankRequest addMerchantBankRequest) {
    return merchantService.addMerchantBank(addMerchantBankRequest);
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param getMerchantBankDetailsRequest
   * @return
   */
  @RequestMapping(value = "/getMerchantBankDetails", method = RequestMethod.POST)
  public GetMerchantBankDetailsResponse getMerchantBankDetails(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               HttpSession session,
                                                               @RequestBody
                                                               GetMerchantBankDetailsRequest getMerchantBankDetailsRequest) {
    return merchantService.getMerchantBankDetails(getMerchantBankDetailsRequest);
  }

}
