package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.IsoResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.Response;
import com.chatak.pg.util.Constants;

@Controller
@SuppressWarnings({"unchecked", "rawtypes"})
public class IsoController implements URLMappingConstants{

	  @Autowired
	  private MessageSource messageSource;

	  private static Logger logger = Logger.getLogger(IsoController.class);
	  
	  @Autowired
	  private IsoService isoService;
	  
	  @Autowired
	  private CurrencyConfigService currencyConfigService;
	  
	  @Autowired
	  private BankService bankService;
	  
	 
	  @RequestMapping(value = SHOW_ISO_CREATE, method = RequestMethod.GET)
	  public ModelAndView showIsoCreate(Map model, HttpSession session) {
		logger.info("Entering :: IsoController :: showIsoCreate");
	    ModelAndView modelAndView = new ModelAndView(ISO_CREATE_VIEW);
	    try {
	    	
	    	 List<Option> countryList = bankService.getCountries();
	         modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
	         session.setAttribute(Constants.COUNTRY_LIST, countryList);
	         
	    	List<Option> currencyList = currencyConfigService.getCurrencyConfigCode();
	        modelAndView.addObject("currencyList", currencyList);
	    } catch (Exception e) {
	      modelAndView.addObject(Constants.ERROR, messageSource
	          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
	      logger.error("Error :: IsoController :: showIsoCreate : " + e.getMessage(), e);
	    }
	    model.put("isoCreate", new IsoRequest());
	    logger.info("Exiting :: IsoController :: showIsoCreate");
	    return modelAndView;

	  }
	  
	@RequestMapping(value = PROCESS_ISO_CREATE, method = RequestMethod.POST)
	public ModelAndView processCreateIso(HttpServletRequest request , HttpServletResponse response, Map model, HttpSession session,
			IsoRequest isoRequest,@RequestParam("isoLogo") MultipartFile file) {
		logger.info("Entering :: IsoController :: processCreateIso");
		ModelAndView modelAndView = new ModelAndView(VIEW_ISO_SEARCH);
		String userType = (String) session
				.getAttribute(Constants.LOGIN_USER_TYPE);
		try {
			if (!file.isEmpty()) {
				isoRequest.getProgramManagerRequest().setProgramManagerLogo(file.getBytes());
			}
			List<Option> countryList = bankService.getCountries();
			modelAndView.addObject(Constants.COUNTRY_LIST, countryList);

			com.chatak.pg.bean.Response stateList = bankService.getStatesByCountry(isoRequest.getCountry());
			modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
		      		
			isoRequest.setCreatedBy(userType);
			Map<Long, Long> cardProgramAndEntityId = new HashMap<>();
		      String[] ids;
		      for(String id : isoRequest.getCardProgramIds()){
		        ids = id.split("@");
		        cardProgramAndEntityId.put(Long.valueOf(ids[0]), Long.valueOf(ids[1]));
		      }
		    isoRequest.setCardProgramAndEntityId(cardProgramAndEntityId);
			Response createISOResponse = isoService.createIso(isoRequest);
			if (createISOResponse.getErrorCode().equals(PGConstants.SUCCESS)) {
				modelAndView = showIsoSearch(request ,response, model, session);
				modelAndView.addObject(Constants.SUCESS, messageSource
						.getMessage("iso.create.success", null,
								LocaleContextHolder.getLocale()));
			}
		} catch (ChatakAdminException ex) {
		    logger.error("Error :: IsoController :: processCreateIso :: ChatakAdminException :: " + ex.getMessage(), ex);
			modelAndView = showIsoCreate(model, session);
			modelAndView.addObject(Constants.ERROR, ex.getErrorMessage());
			model.put("isoCreate", isoRequest);
		} catch (Exception e) {
		  logger.error("Error :: IsoController :: processCreateIso : " + e.getMessage(), e);
			modelAndView = showIsoCreate(model, session);
			modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
					Constants.CHATAK_GENERAL_ERROR, null,
					LocaleContextHolder.getLocale()));
			model.put("isoCreate", isoRequest);
		}
		logger.info("Exiting :: IsoController :: processCreateIso");
		return modelAndView;

	}
	  
	@RequestMapping(value = SHOW_ISO_SEARCH, method = RequestMethod.GET)
	public ModelAndView showIsoSearch(HttpServletRequest request, HttpServletResponse response,Map model, HttpSession session) {
		logger.info("Entering :: IsoController :: showIsoSearch");
		ModelAndView modelAndView = new ModelAndView(VIEW_ISO_SEARCH);

		try {
			setEnumValuesForSearchPage(model);
			model.put("searchList", "yes");
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
					Constants.CHATAK_GENERAL_ERROR, null,
					LocaleContextHolder.getLocale()));
			logger.error("Error :: IsoController :: showIsoSearch : " + e.getMessage(), e);
		}
		model.put("isoCreate", new IsoRequest());
		logger.info("Exiting :: IsoController :: showIsoSearch");
		return modelAndView;
	}

	@RequestMapping(value = GET_CARD_PROGRAM, method = RequestMethod.GET)
	public @ResponseBody String fetchCardProgramByPm(Map model, HttpSession session, @FormParam("pmId") Long pmId,
			@FormParam("entityType") String entityType, @FormParam("currencyId") String currencyId) {
		logger.info("Entering :: IsoController :: fetchCardProgramByPm");
		CardProgramResponse cardProgramResponse = null;
		try {
			if (null != entityType && entityType.equals(PGConstants.PROGRAM_MANAGER_NAME)) {
				cardProgramResponse = isoService.fetchCardProgramByPm(pmId);
			} else {
				cardProgramResponse = isoService.fetchCardProgramByIso(pmId, currencyId);
			}
			cardProgramResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			return JsonUtil.convertObjectToJSON(cardProgramResponse);
		} catch (Exception e) {
			logger.error("Error :: IsoController :: fetchCardProgramByPm : " + e.getMessage(), e);
		}
		logger.info("Exiting :: IsoController :: fetchCardProgramByPm");
		return null;
	}   	

	public void setEnumValuesForSearchPage(Map model) {
		model.put("statusList", Arrays.asList(Constants.ACTIVE, Constants.ACC_SUSPENDED));
	}
	
	@RequestMapping(value = PROCESS_ISO_SEARCH, method = RequestMethod.POST)
	public ModelAndView processIsoSearch(Map model, HttpSession session,
			IsoRequest isoRequest) {
 		logger.info("Entering :: IsoController :: processIsoSearch");
		ModelAndView modelAndView = new ModelAndView(VIEW_ISO_SEARCH);

		try {
			getIsoIdsMappedToEntity(isoRequest, session);
			setEnumValuesForSearchPage(model);
			isoRequest.setPageIndex(Constants.ONE);
			isoRequest.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
			if (isoRequest.getPageSize() != null) {
				isoRequest.setPageSize(isoRequest.getPageSize());
			} else {
				isoRequest
						.setPageSize(Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE);
			}
			session.setAttribute(Constants.ISO_REQUEST_EXPORT_DATA,
					isoRequest);
			IsoResponse isoResponse = isoService.searchIso(isoRequest);
			model.put("searchList", isoResponse.getIsoRequest());
			model.put(Constants.TOTAL_RECORDS, isoResponse.getTotalNoOfRows());
			 modelAndView = PaginationUtil.getPagenationModel(modelAndView,
					 isoResponse.getTotalNoOfRows(), isoRequest.getPageSize());
		      session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
					Constants.CHATAK_GENERAL_ERROR, null,
					LocaleContextHolder.getLocale()));
			logger.error("Error :: IsoController :: processIsoSearch : " + e.getMessage(), e);
		}
		model.put("isoCreate", isoRequest);
		logger.info("Exiting :: IsoController :: processIsoSearch");
		return modelAndView;

	}
	
	@RequestMapping(value = SHOW_ISO_EDIT, method = RequestMethod.POST)
	  public ModelAndView showIsoEdit(Map model, HttpSession session,@FormParam("isoId") Long isoId,HttpServletRequest request,HttpServletResponse response) {
		logger.info("Entering :: IsoController :: showIsoEdit");
	    ModelAndView modelAndView = new ModelAndView(VIEW_ISO_EDIT);
	    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
	    List<ProgramManagerRequest> programManagerList = new ArrayList<>();
	    try {
	    	IsoRequest isoRequest = new IsoRequest();
	    	isoRequest.setId(isoId);
	    	IsoResponse isoResponse = isoService.getIsoById(isoRequest);
	    	model.put("isoEdit", isoResponse);	    
	    	model.put("selectedPmList", isoResponse.getProgramManagerRequestList());
	    	model.put("cardProgramList", isoResponse.getCardProgramRequestList());
	    	 List<Option> countryList = bankService.getCountries();
			modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
			session.setAttribute(Constants.COUNTRY_LIST, countryList);
			com.chatak.pg.bean.Response stateList = bankService.getStatesByCountry(isoResponse.getIsoRequest().get(0).getCountry());
			modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());

			session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
	    	if(userType.equals(Constants.ADMIN_USER_TYPE)){
	    		programManagerList = fetchProgramManagerByCurrency(isoId,isoResponse.getIsoRequest().get(0).getProgramManagerRequest().getAccountCurrency());
	    	}
	    	model.put("programManagers", programManagerList);
	    	if(isoResponse.getIsoRequest().get(0).getProgramManagerRequest().getProgramManagerLogo()!=null){
	    		String logo = encodeToString(isoResponse.getIsoRequest().get(0).getProgramManagerRequest().getProgramManagerLogo(),"jpg");
		    	model.put("imageData", logo);	    		
	    	}
	    }catch(ChatakAdminException ex){
	    	modelAndView.addObject(Constants.ERROR, ex.getErrorMessage());
	    	logger.error("Error :: IsoController :: showIsoEdit :: ChatakAdminException :: " + ex.getMessage(), ex);
	    }catch (Exception e) {
	      modelAndView.addObject(Constants.ERROR, messageSource
	          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
	      logger.error("Error :: IsoController :: showIsoEdit : " + e.getMessage(), e);
	    }
	    logger.info("Exiting :: IsoController :: showIsoEdit");
	    return modelAndView;
	  }
	
	@RequestMapping(value = UPDATE_ISO, method = RequestMethod.POST)
	public ModelAndView updateIso(Map model, HttpSession session, IsoResponse isoRequest,@RequestParam("isoLogo") MultipartFile file) {
		logger.info("Entering :: IsoController :: updateIso");
		ModelAndView modelAndView = new ModelAndView(VIEW_ISO_SEARCH);

		try {
			IsoRequest isoReq;
			isoReq=isoRequest.getIsoRequest().get(0);
			if (!file.isEmpty()) {
				isoReq.getProgramManagerRequest().setProgramManagerLogo(file.getBytes());
			}
			List<Option> countryList = bankService.getCountries();
			modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
			session.setAttribute(Constants.COUNTRY_LIST, countryList);
			Map<Long, Long> cardProgramAndEntityId = new HashMap<>();
            String[] ids;
            for(String id : isoReq.getCardProgramIds()){
              ids = id.split("@");
              cardProgramAndEntityId.put(Long.valueOf(ids[0]), Long.valueOf(ids[1]));
            }
            isoReq.setCardProgramAndEntityId(cardProgramAndEntityId);
			isoService.updateIso(isoReq);
			modelAndView.addObject(Constants.SUCESS, messageSource
					.getMessage("iso.update.success", null,
							LocaleContextHolder.getLocale()));
			setEnumValuesForSearchPage(model);
		}catch(ChatakAdminException ex){
	    	modelAndView.addObject(Constants.ERROR, ex.getErrorMessage());
	    	logger.error("Error :: IsoController :: updateIso :: ChatakAdminException :: " + ex.getMessage(), ex);
	    }catch (Exception e) {
			modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
					Constants.CHATAK_GENERAL_ERROR, null,
					LocaleContextHolder.getLocale()));
			logger.error("Error :: IsoController :: updateIso : " + e.getMessage(), e);
		}
		model.put("isoCreate", new IsoRequest());
		model.put("searchList", "yes");
		logger.info("Exiting :: IsoController :: updateIso");
		return modelAndView;
	}
	
	@RequestMapping(value = FETCH_CARD_PROGRAM_BY_ISO, method = RequestMethod.GET)
	public @ResponseBody String fetchCardProgramByIso(Map model, HttpSession session, @FormParam("isoId") Long isoId) {
		logger.info("Entering :: IsoController :: fetchCardProgramByIso");
		try {
			IsoResponse isoResponse = isoService.fetchCardProgramByIso(isoId);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			return JsonUtil.convertObjectToJSON(isoResponse);
		} catch (Exception e) {
			logger.error("Error :: IsoController :: fetchCardProgramByIso : " + e.getMessage(), e);
		}
		logger.info("Exiting :: IsoController :: fetchCardProgramByIso");
		return null;
	}
	
	private List<ProgramManagerRequest> fetchProgramManagerByCurrency(Long isoId,String currency) {
		logger.info("Entering :: IsoController :: fetchProgramManagerByCurrency");
		IsoResponse isoResponse = new IsoResponse();
		try {
			isoResponse = isoService.fetchProgramManagerByIsoCurrency(isoId, currency);
		} catch (Exception e) {
			logger.error("Error :: IsoController :: fetchProgramManagerByCurrency : " + e.getMessage(), e);
		}
		logger.info("Exiting :: IsoController :: fetchProgramManagerByCurrency");
		return isoResponse.getProgramManagerRequestList();
	}
	
	private void getIsoIdsMappedToEntity(IsoRequest isoRequest, HttpSession session) throws ChatakAdminException{
		LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
		if(loginResponse.getUserType().equalsIgnoreCase(Constants.PM_USER_TYPE)) {
			ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
    		programManagerRequest.setProgramManagerId(loginResponse.getEntityId());
    		List<Long> isoIds = isoService.findByPmId(loginResponse.getEntityId());
    		isoRequest.setIds(isoIds);
		} else if(loginResponse.getUserType().equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
			isoRequest.setId(loginResponse.getEntityId());
		}
	}
	
	public static String encodeToString(byte[] image, String type) {
		String imageString = null;
		String encodedImage = null;

		try {
			encodedImage = org.apache.commons.codec.binary.Base64.encodeBase64String(image);
			imageString = "data:image/" + type + ";base64," + encodedImage;
		} catch (Exception e) {
			logger.error("Error :: IsoController :: encodeToString : " + e.getMessage(), e);
		}
		return imageString;
	}
	
	@RequestMapping(value = PREPAID_ADMIN_ISO_PAGINATION_ACTION, method = RequestMethod.POST)
	public ModelAndView isoPagination(HttpSession session, ModelMap model,
			@FormParam("programManagerPageData") final Integer programManagerPageData,
			@FormParam("totalRecords") final Integer totalRecords) {
		logger.info("Entering :: IsoController :: isoPagination");
		ModelAndView modelAndView = new ModelAndView(VIEW_ISO_SEARCH);
		try {
			setEnumValuesForSearchPage(model);
			IsoRequest isoRequest = new IsoRequest();
			model.put("isoCreate", isoRequest);
			isoRequest.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_TYPE).toString());
			isoRequest.setPageIndex(programManagerPageData);
			isoRequest.setNoOfRecords(totalRecords);
			IsoRequest sessionSearchList = (IsoRequest) session.getAttribute(Constants.ISO_REQUEST_EXPORT_DATA);
			isoRequest.setPageSize(sessionSearchList.getPageSize());
			sessionSearchList.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_TYPE).toString());
			sessionSearchList.setPageIndex(programManagerPageData);
			sessionSearchList.setNoOfRecords(totalRecords);
			getIsoPaginationList(session, modelAndView, programManagerPageData, sessionSearchList, model);
		} catch (ChatakAdminException e) {
		    logger.error("Error :: IsoController :: isoPagination :: ChatakAdminException :: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error :: IsoController :: isoPagination : " + e.getMessage(), e);
		}
		logger.info("Exiting :: IsoController :: isoPagination");
		return modelAndView;

	}

	private void getIsoPaginationList(HttpSession session, ModelAndView modelAndView, Integer programManagerPageData,
			IsoRequest sessionSearchList, Map model) throws ChatakAdminException {
		logger.info("Entering :: IsoController :: getIsoPaginationList");
		IsoResponse isoResponse = isoService.searchIso(sessionSearchList);
		if (isoResponse != null) {
			List<IsoRequest> isoResponseList = isoResponse.getIsoRequest();
			session.setAttribute(Constants.SEARCH_LIST, isoResponse);
			if (StringUtil.isListNotNullNEmpty(isoResponseList)) {
				modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, programManagerPageData,
						isoResponse.getTotalNoOfRows(), sessionSearchList.getPageSize());
				model.put("programManagerRequest", sessionSearchList);
				modelAndView.addObject("searchList", isoResponseList);
			}
		}
		logger.info("Exiting :: IsoController :: getIsoPaginationList");
	}

	@RequestMapping(value = GET_ISO_REPORT, method = RequestMethod.POST)
	public ModelAndView downloadIsoList(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
			@FormParam("downloadType") final String downloadType, @FormParam("totalRecords") final Integer totalRecords,
			@FormParam("downloadAllRecords") final boolean downloadAllRecords) {
		logger.info("Entering :: IsoController :: downloadIsoList");
		try {
			IsoRequest searchList = (IsoRequest) session.getAttribute(Constants.ISO_REQUEST_EXPORT_DATA);
			Integer pSize = searchList.getPageSize();
			if (downloadAllRecords) {
				searchList.setPageSize(totalRecords);
				searchList.setPageIndex(Constants.ONE);
			}
			IsoResponse isoResponse = isoService.searchIso(searchList);
			List<IsoRequest> isoRequests = isoResponse.getIsoRequest();

			ExportDetails exportDetails = new ExportDetails();
			if (StringUtil.isListNotNullNEmpty(isoRequests)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadIsoReport(isoRequests, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}

			searchList.setPageSize(pSize);
		} catch (ChatakAdminException e) {
		    logger.error("Error :: IsoController :: downloadIsoList :: ChatakAdminException :: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error :: IsoController :: downloadIsoList : " + e.getMessage(), e);
		}
		logger.info("Exiting :: IsoController :: downloadIsoList");
		return null;
	}

	private void setExportDetailsDataForDownloadIsoReport(List<IsoRequest> isoRequests, ExportDetails exportDetails) {
		exportDetails.setReportName("ISO_");
		exportDetails.setHeaderMessageProperty("admin.partner.message");

		exportDetails.setHeaderList(getIsoHeaderList());
		exportDetails.setFileData(getIsoFileData(isoRequests));
	}

	private List<String> getIsoHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("admin.iso.Name.message", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.BusinessEntityName.message", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("currency-search-page.label.currency", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("fee-program-search.label.status", null, LocaleContextHolder.getLocale()) };
		return new ArrayList<String>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getIsoFileData(List<IsoRequest> isoRequests) {
		List<Object[]> fileData = new ArrayList<Object[]>();

		for (IsoRequest isoRequest : isoRequests) {

			Object[] rowData = { isoRequest.getIsoName(), isoRequest.getProgramManagerRequest().getBusinessName(),
					isoRequest.getProgramManagerRequest().getAccountCurrency(),
					isoRequest.getProgramManagerRequest().getStatus() };
			fileData.add(rowData);
		}

		return fileData;
	}
	
	@RequestMapping(value = ADMIN_CHANGE_ISO_STATUS, method = RequestMethod.POST)
	public ModelAndView changeIsoStatus(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, final HttpSession session,
			@FormParam("manageProgramManagerId") final Long manageProgramManagerId,
			@FormParam("manageProgramManagerStatus") final String manageProgramManagerStatus,
			@FormParam("reason") String reason) {

		logger.info("Entering :: IsoController :: changeIsoStatus");
		ModelAndView modelAndView = new ModelAndView(VIEW_ISO_SEARCH);

		try {
			setEnumValuesForSearchPage(model);
			IsoRequest isoRequest = new IsoRequest();
			ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
			programManagerRequest.setStatus(manageProgramManagerStatus);
			model.put("isoCreate", isoRequest);
			isoRequest.setId(manageProgramManagerId);

			isoRequest.setProgramManagerRequest(programManagerRequest);
			isoRequest.setReason(reason);
			isoRequest.setUpdatedBy(session.getAttribute(Constants.LOGIN_USER_TYPE).toString());
			IsoResponse respon = isoService.changeStatus(isoRequest);
			if (respon != null && respon.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
				modelAndView.setViewName(VIEW_ISO_SEARCH);
				modelAndView = showIsoSearch(request, response, model, session);
				modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
						"prepaid.admin.changeIsostatus.success.message", null, LocaleContextHolder.getLocale()));
			} else {
				modelAndView.setViewName(VIEW_ISO_SEARCH);
				modelAndView = showIsoSearch(request, response, model, session);

				if (respon != null)
					modelAndView.addObject(Constants.ERROR, respon.getErrorMessage());
			}

		} catch (Exception e) {
			logger.error("Error :: IsoController :: changeIsoStatus : " + e.getMessage(), e);
			modelAndView = showIsoSearch(request, response, model, session);
			model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message", null,
					LocaleContextHolder.getLocale()));
		}
		logger.info("Exiting :: IsoController :: changeIsoStatus");
		return modelAndView;
	}

}
