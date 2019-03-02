package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.constants.URLMappingConstants;
import com.chatak.pay.service.VaultService;
import com.chatak.pg.bean.GetCardTokensRequest;
import com.chatak.pg.bean.GetCardTokensResponse;
import com.chatak.pg.bean.RegisterCardRequest;
import com.chatak.pg.bean.VaultResponse;
import com.chatak.pg.constants.VaultErrorCodes;

@RestController
@RequestMapping(value = "/vault", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class VaultController extends BaseController implements URLMappingConstants, Constant {

  /*@Autowired
  private VaultService vaultService;*/

  private static Logger logger = LogManager.getLogger(VaultController.class);

  /**
   * @param request
   * @param response
   * @param session
   * @param registerCardRequest
   * @return
   */
  @RequestMapping(value = "/registerCard", method = RequestMethod.POST)
  public VaultResponse registerCard(HttpServletRequest request,
                                    HttpServletResponse response,
                                    HttpSession session,
                                    @RequestBody RegisterCardRequest registerCardRequest) {
    VaultResponse registrationResponse = new VaultResponse();

    logger.debug("Entering:: VaultController:: registerCard method");

    try {
      vaultService.validateRegisterCardRequest(registerCardRequest);
      registrationResponse = vaultService.registerCardToken(registerCardRequest);

    } catch(Exception e) {
      logger.error("ERROR:: VaultController:: registerCard method", e);
      registrationResponse.setErrorCode(e.getMessage());
      registrationResponse.setErrorMessage(VaultErrorCodes.getInstance().getMessage(e.getMessage()));
      return registrationResponse;

    }
    logger.debug("Exiting :: VaultController:: registerCard method");
    return registrationResponse;
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param getCardTokensRequest
   * @return
   */
  @RequestMapping(value = "/getTokens", method = RequestMethod.POST)
  public GetCardTokensResponse getTokens(HttpServletRequest request,
                                         HttpServletResponse response,
                                         HttpSession session,
                                         @RequestBody GetCardTokensRequest getCardTokensRequest) {

    logger.debug("Entering:: VaultController:: getTokens method");
    GetCardTokensResponse getCardTokensResponse = new GetCardTokensResponse();
    try {
      vaultService.validateTokensRequest(getCardTokensRequest);
      getCardTokensResponse = vaultService.getCardTokens(getCardTokensRequest);
    } catch(Exception e) {
      logger.error("ERROR:: VaultController:: getTokens method", e);
        getCardTokensResponse.setErrorCode(e.getMessage());
        getCardTokensResponse.setErrorMessage(VaultErrorCodes.getInstance().getMessage(e.getMessage()));
    }
    logger.debug("Exiting :: VaultController:: getTokens method");

    return getCardTokensResponse;
  }

}
