package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.FeeCodeService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;
import com.chatak.pg.acq.dao.repository.AcquirerFeeCodeRepository;
import com.chatak.pg.model.AcquirerFeeCodeDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.PartnerFeeCodeDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class FeeCodeController implements URLMappingConstants {
  @Autowired
  FeeCodeService feeCodeService;

  @Autowired
  AcquirerFeeCodeRepository acquirerFeeCodeRepository;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private MessageSource messageSource;

  private static Logger logger = Logger.getLogger(FeeCodeController.class);

  @RequestMapping(value = CHATAK_ACQUIRER_FEE_SHOW, method = RequestMethod.GET)
  public ModelAndView showAcquirerFeePage(Map model, HttpSession session,
      @RequestParam("partnerId") String merchantCode) {
    logger.info("Entering:: FeeCodeController:: showCreateAcquirerPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ACQUIRER_FEE_SHOW);
    List<AcquirerFeeCodeDTO> pgAcquirerFeeCodeList = null;
    PGAcquirerFeeCode pgAcquirerFeeCode = new PGAcquirerFeeCode();
    try {
      pgAcquirerFeeCodeList =
          feeCodeService.findByPartnerIdAndMerchantCode((Constants.ONE).longValue(), merchantCode);
      modelAndView.addObject("pgAcquirerFeeCodeList", pgAcquirerFeeCodeList);
      session.setAttribute("partnerIdToAddAcquirerFee", merchantCode);
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: FeeCodeController:: showCreateAcquirerPage method", e);
    }
    modelAndView.addObject("pgAcquirerFeeCode", pgAcquirerFeeCode);
    logger.info("Exiting:: FeeCodeController:: showCreateAcquirerPage method");
    return modelAndView;
  }

  @RequestMapping(value = SHOW_CHATAK_PARTNER_FEE_EDIT, method = RequestMethod.GET)
  public ModelAndView showEditParnerFeePage(Map model, HttpSession session,
      HttpServletRequest request, HttpServletResponse response,
      @RequestParam("getMerchantId") String merchantId) {
    logger.info("Entering:: FeeCodeController:: showEditParnerFeePage method");
    PartnerFeeCodeDTO partnerFeeCodeDTO = null;
    ModelAndView modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_EDIT);
    try {
      partnerFeeCodeDTO = feeCodeService.getPartnerFeeCodeByEntityId(merchantId);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.info("ERROR:: FeeCodeController:: showEditParnerFeePage method",e);
    }
    modelAndView.addObject("partnerFeeCodeDTO", partnerFeeCodeDTO);
    logger.info("Exiting:: FeeCodeController:: showEditParnerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_CHATAK_PARTNER_FEE_EDIT, method = RequestMethod.POST)
  public ModelAndView processEditPartnerFeePage(Map model, HttpSession session,
      @ModelAttribute PartnerFeeCodeDTO partnerFeeCodeDTO, HttpServletRequest request,
      HttpServletResponse response) {
    ModelAndView modelAndView = null;
    logger.info("Entering:: FeeCodeController:: processEditPartnerFeePage method");
    try {
      feeCodeService.updatePartnerFeeCode(partnerFeeCodeDTO);
    } catch (ChatakAdminException e) {
      modelAndView = showPartnerFeePage(model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.info("Exiting:: FeeCodeController:: processEditPartnerFeePage method",e);
      return modelAndView;
    }
    modelAndView = showPartnerFeePage(model, session);
    modelAndView.addObject(Constants.SUCESS, "Updated successfully");
    logger.info("Exiting:: FeeCodeController:: processEditPartnerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = SHOW_CHATAK_AQUIRER_FEE_EDIT, method = RequestMethod.GET)
  public ModelAndView showEditAcquirerFeePage(Map model, HttpSession session,
      HttpServletRequest request, HttpServletResponse response,
      @RequestParam("getAquirerId") Long aquirerId) {
    logger.info("Entering:: FeeCodeController:: showEditAcquirerFeePage method");
    AcquirerFeeCodeDTO pgAcquirerFeeCode = null;
    ModelAndView modelAndView = new ModelAndView(CHATAK_ACQUIRER_FEE_EDIT);
    try {
      pgAcquirerFeeCode = feeCodeService.getAcquirerFeeCodeByID(aquirerId);
    } catch (Exception e) {
      logger.info("ERROR:: FeeCodeController:: showEditAcquirerFeePage method",e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("pgAcquirerFeeCode", pgAcquirerFeeCode);
    logger.info("Exiting:: FeeCodeController:: showEditAcquirerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PARTNER_FEE_SHOW, method = RequestMethod.GET)
  public ModelAndView showPartnerFeePage(Map model, HttpSession session) {
    logger.info("Entering:: FeeCodeController:: showPartnerFeePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_SHOW);
    PartnerFeeCodeDTO partnerFeeCodeDTO = new PartnerFeeCodeDTO();
    Merchant merchant = new Merchant();
    session.setAttribute(Constants.PARTNER_FEE_CODES_MODEL, merchant);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    modelAndView.addObject("partnerFeeCodeDTO", partnerFeeCodeDTO);
    modelAndView.addObject("flag", false);
    try {
      MerchantSearchResponse searchResponse = merchantUpdateService.searchAllMerchant(merchant);
      if (searchResponse.getMerchants() != null) {
        modelAndView.addObject("partnerFeeList", searchResponse.getMerchants());
        modelAndView.addObject("pageSize", merchant.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.info("ERROR:: FeeCodeController:: showPartnerFeePage method",e);
      modelAndView.addObject("partnerFeeList", new ArrayList<PartnerFeeCodeDTO>());
    }
    logger.info("Exiting:: FeeCodeController:: showPartnerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PARTNER_FEE_CREATE, method = RequestMethod.GET)
  public ModelAndView showCreatePartnerFeePage(Map model, HttpSession session,
      HttpServletRequest request, HttpServletResponse response,
      @RequestParam("merchantCode") String merchantCode) {
    logger.info("Entering:: FeeCodeController:: showCreatePartnerFeePage method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_CREATE);

    PartnerFeeCodeDTO partnerFeeCodeDTO = new PartnerFeeCodeDTO();
    Long userId = (Long) session.getAttribute("loginUserId");
    try {
      PGPartnerFeeCode pgPartnerFeeCode = feeCodeService.validatePartnerFeeCode(merchantCode);
      if (null != pgPartnerFeeCode) {
        partnerFeeCodeDTO.setAccountNumber(pgPartnerFeeCode.getAccountNumber());
        partnerFeeCodeDTO.setMerchantCode(pgPartnerFeeCode.getPartnerEntityId());
        partnerFeeCodeDTO.setPartnerName(pgPartnerFeeCode.getPartnerName());
        partnerFeeCodeDTO.setCreatedBy(userId.toString());
      } else {
        throw new ChatakAdminException();
      }
    } catch (ChatakAdminException e) {
      model.put(Constants.ERROR, "Merchant record not found or alrady have fee account");
      logger.info("ERROR:: FeeCodeController:: showPartnerFeePage method",e);
      modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_CREATE);
      modelAndView.addObject("flag", false);
      return modelAndView;
    }
    modelAndView.addObject("partnerFeeCodeDTO", partnerFeeCodeDTO);
    modelAndView.addObject("flag", true);
    logger.info("Exiting:: FeeCodeController:: showCreatePartnerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PARTNER_FEE_CREATE, method = RequestMethod.POST)
  public ModelAndView createPartnerFee(Map model, HttpSession session,
      @ModelAttribute PartnerFeeCodeDTO partnerFeeCodeDTO, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering:: FeeCodeController:: createPartnerFee method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_SHOW);
    try {
      feeCodeService.addPartnerFeeCode(partnerFeeCodeDTO);
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_SHOW);
      modelAndView.addObject("flag", false);
      logger.info("Exiting:: FeeCodeController:: createPartnerFee method",e);
      return modelAndView;
    }
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: FeeCodeController:: createPartnerFee method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PARTNER_FEE_PAGINATION_ACTION, method = RequestMethod.POST)
  public ModelAndView getFeeCodePagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: FeeCodeController:: getFeeCodePagination method");
    Merchant merchant = new Merchant();
    ModelAndView modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_SHOW);
    try {
      merchant.setPageIndex(pageNumber);
      merchant.setNoOfRecords(totalRecords);
      try {
        MerchantSearchResponse searchResponse = merchantUpdateService.searchAllMerchant(merchant);
        List<MerchantData> merchants = new ArrayList<>();
        if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchants())) {
          merchants = searchResponse.getMerchants();
          modelAndView.addObject("pageSize", merchant.getPageSize());
          modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
              searchResponse.getTotalNoOfRows());
        }
        modelAndView.addObject("partnerFeeList", merchants);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        logger.error("ERROR:: FeeCodeController:: getFeeCodePagination method", e);
      }
    } catch (Exception e) {
      logger.error("ERROR:: FeeCodeController:: getFeeCodePagination method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: FeeCodeController:: getFeeCodePagination method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_CHATAK_AQUIRER_FEE_EDIT, method = RequestMethod.POST)
  public ModelAndView processEditAcquirerFeePage(Map model, HttpSession session,
      @ModelAttribute AcquirerFeeCodeDTO pgAcquirerFeeCode, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering:: FeeCodeController:: processEditAcquirerFeePage method");
    ModelAndView modelAndView = null;
    if (null != pgAcquirerFeeCode) {
      try {
        pgAcquirerFeeCode.setPartnerId((Constants.ONE).longValue());
        Long userId = (Long) session.getAttribute("loginUserId");
        pgAcquirerFeeCode.setUpdatedBy(userId.toString());
        feeCodeService.updateAcquirerFeeCode(pgAcquirerFeeCode);
        modelAndView = showAcquirerFeePage(model, session, pgAcquirerFeeCode.getMerchantCode());
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.admin.acquirer.fee.code.update", null, LocaleContextHolder.getLocale()));
      } catch (ChatakAdminException e) {
        modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_SHOW);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        logger.info("Exiting:: FeeCodeController:: processEditAcquirerFeePage method",e);
        return modelAndView;
      }
    }
    logger.info("Exiting:: FeeCodeController:: processEditAcquirerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PARTNER_FEE_SEARCH, method = RequestMethod.GET)
  public ModelAndView searchPartnerFeePage(Map model, HttpSession session,
      HttpServletRequest request, HttpServletResponse response,
      @RequestParam("merchantCode") String merchantCode) {
    logger.info("Entering:: FeeCodeController:: searchPartnerFeePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_SHOW);
    PartnerFeeCodeDTO partnerFeeCodeDTO = new PartnerFeeCodeDTO();
    PGMerchant merchant = new PGMerchant();
    try {
      if (null != merchantCode && "" != merchantCode) {
        merchant = feeCodeService.getAcquirerFeeByMerchantCode(merchantCode);
        List<PGMerchant> feeAcquirerCodeSearch = new ArrayList<>();
        if (null != merchant) {
          feeAcquirerCodeSearch.add(merchant);
        }
        if (!feeAcquirerCodeSearch.isEmpty()) {
          modelAndView.addObject("partnerFeeList", feeAcquirerCodeSearch);
          modelAndView =
              PaginationUtil.getPagenationModel(modelAndView, feeAcquirerCodeSearch.size());
        }
      } else {
        modelAndView = showPartnerFeePage(model, session);
      }
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR, "Merchant record not found");
      logger.info("Exiting:: FeeCodeController:: searchPartnerFeePage method",e);
      modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_SHOW);
      modelAndView.addObject("flag", false);
      return modelAndView;
    }
    modelAndView.addObject("partnerFeeCodeDTO", partnerFeeCodeDTO);
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: FeeCodeController:: searchPartnerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_GET_PARTNER_FEE_CREATE_PAGE, method = RequestMethod.GET)
  public ModelAndView getPartnerFeePage(Map model, HttpSession session, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering:: FeeCodeController:: getPartnerFeePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PARTNER_FEE_CREATE);
    PartnerFeeCodeDTO partnerFeeCodeDTO = new PartnerFeeCodeDTO();
    modelAndView.addObject("partnerFeeCodeDTO", partnerFeeCodeDTO);
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: FeeCodeController:: getPartnerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ACQUIRER_FEE_CREATE_SHOW, method = RequestMethod.GET)
  public ModelAndView showCreateAcquirerFeePage(Map model, HttpSession session) {
    logger.info("Entering:: FeeCodeController:: showCreateAcquirerFeePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ACQUIRER_FEE_CREATE_SHOW);
    AcquirerFeeCodeDTO pgAcquirerFeeCode = new AcquirerFeeCodeDTO();
    modelAndView.addObject("pgAcquirerFeeCode", pgAcquirerFeeCode);
    logger.info("Exiting:: FeeCodeController:: showCreateAcquirerFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ACQUIRER_FEE_CREATE_PROCESS, method = RequestMethod.POST)
  public ModelAndView processCreateAcquirerFeePage(Map model, HttpSession session,
      @ModelAttribute AcquirerFeeCodeDTO pgAcquirerFeeCode) {
    logger.info("Entering:: FeeCodeController:: processCreateAcquirerFeePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ACQUIRER_FEE_CREATE_SHOW);
    try {
      pgAcquirerFeeCode
          .setMerchantCode(String.valueOf(session.getAttribute("partnerIdToAddAcquirerFee")));
      pgAcquirerFeeCode.setPartnerId((Constants.ONE).longValue());
      List<AcquirerFeeCodeDTO> list = feeCodeService.findByPartnerIdAndMerchantCode(
          pgAcquirerFeeCode.getPartnerId(), pgAcquirerFeeCode.getMerchantCode());
      if (CommonUtil.isListNotNullAndEmpty(list)) {
        setPgfee(pgAcquirerFeeCode, modelAndView, list);
      }
      feeCodeService.addAcquirerFeeCode(pgAcquirerFeeCode);
      modelAndView.addObject(Constants.SUCESS, messageSource
          .getMessage("chatak.admin.add.acquirer.fee.code", null, LocaleContextHolder.getLocale()));
    } catch (ChatakAdminException e) {
    	logger.error("ERROR:: FeeCodeController:: processCreateAcquirerFeePage method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("pgAcquirerFeeCode", pgAcquirerFeeCode);
    logger.info("Exiting:: FeeCodeController:: processCreateAcquirerFeePage method");
    return modelAndView;
  }

	private ModelAndView setPgfee(AcquirerFeeCodeDTO pgAcquirerFeeCode, ModelAndView modelAndView,
			List<AcquirerFeeCodeDTO> list) {
		for (AcquirerFeeCodeDTO pgFee : list) {
			if (pgFee.getAcquirerName().equals(pgAcquirerFeeCode.getAcquirerName())) {
				modelAndView.addObject("pgAcquirerFeeCode", pgAcquirerFeeCode);
				modelAndView.addObject(Constants.ERROR, messageSource
						.getMessage("chatak.admin.duplicate.acquirer.fee.code", null, LocaleContextHolder.getLocale()));
			}
		}
		return modelAndView;
	}
}
