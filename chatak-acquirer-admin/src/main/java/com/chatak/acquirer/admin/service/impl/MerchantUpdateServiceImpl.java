package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantCreateResponse;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.MerchantCardProgramMapDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantEntityMapDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.model.Iso;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;
import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.CurrencyRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AgentDTO;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.MerchantCurrencyMapping;
import com.chatak.pg.model.MerchantRequest;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 4:28:42 PM
 * @version 1.0
 */
/**
 * @author sharath
 */
@Service
public class MerchantUpdateServiceImpl implements MerchantUpdateService, PGConstants {

  private static Logger logger = Logger.getLogger(MerchantUpdateServiceImpl.class);

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  CurrencyDao currencyDao;

  @Autowired
  private CountryDao countryDao;

  @Autowired
  MailServiceManagement mailingServiceManagement;

  @Autowired
  IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  MerchantRepository merchantRepository;

  @Autowired
  CurrencyConfigRepository currencyConfigRepository;

  @Autowired
  CurrencyConfigDao currencyConfigDao;

  @Autowired
  SubMerchantDao subMerchantDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;
  
  @Autowired
  MerchantEntityMapDao merchantEntityMapDao;
  
  @Autowired
  ProgramManagerDao ProgramManagerDao;
  
  @Autowired
  MerchantCardProgramMapDao merchantCardProgramMapDao;
  
  @Autowired
  IsoServiceDao isoServiceDao;
  
  @Autowired
  CardProgramDao cardProgramDao;

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public MerchantSearchResponse searchMerchant(Merchant merchant, String userType, Long entityId) throws ChatakAdminException {
    logger.info("Entering:: MerchantServiceImpl:: searchMerchant method");
    GetMerchantListRequest searchMerchant = new GetMerchantListRequest();
    GetMerchantListResponse getMerchantListResponse = null;
    getSearchMerchantDetails(merchant, searchMerchant);
    searchMerchant.setPageSize(merchant.getPageSize());
    searchMerchant.setPageIndex(merchant.getPageIndex());
    searchMerchant.setNoOfRecords(merchant.getNoOfRecords());
    searchMerchant.setMerchantCode(merchant.getMerchantCode());
    searchMerchant.setSubMerchantCode(merchant.getSubMerchantCode());
    searchMerchant.setEntityIds(merchant.getEntitiesId());
    
    getMerchantListResponse = getMerchantResponse(searchMerchant, userType, entityId);
    
    List<MerchantRequest> pgMerchants = getMerchantListResponse.getMerchantRequests();
    List<PGMerchant> subMerchants = getMerchantListResponse.getSubMerchants();
    MerchantSearchResponse response = new MerchantSearchResponse();
    List<MerchantCreateResponse> list = new ArrayList<>(pgMerchants.size());
    MerchantCreateResponse merchantResponse = null;
    if (!CollectionUtils.isEmpty(pgMerchants)) {
    	  for (MerchantRequest pgMerchant : pgMerchants) {
    		  merchantResponse = new MerchantCreateResponse();
    		  setMerchantDetails(merchantResponse, pgMerchant);
    		  getMerchantCreateStatus(merchantResponse, pgMerchant);
    		  list.add(merchantResponse);
    	      }
    	  response.setMerchantCreateResponses(list);
    }
    if (!CollectionUtils.isEmpty(subMerchants)) {
      List<MerchantData> merchants = new ArrayList<>(subMerchants.size());
      MerchantData merchantRespObj = null;
      for (PGMerchant pgMerchant : subMerchants) {
        merchantRespObj = new MerchantData();
        
        setMerchantBasicDetails(merchantRespObj, pgMerchant);
        getMerchantStatus(merchantRespObj, pgMerchant);

        merchants.add(merchantRespObj);
      }
      response.setSubMerchants(merchants);
    }
    response.setTotalNoOfRows(getMerchantListResponse.getNoOfRecords());
    logger.info("Exiting:: MerchantServiceImpl:: searchMerchant method");
    return response;
  }

  private MerchantData setMerchantBasicDetails(MerchantData merchantRespObj, PGMerchant pgMerchant) {
    merchantRespObj.setId(pgMerchant.getId());
    merchantRespObj.setBusinessName(pgMerchant.getBusinessName());
    merchantRespObj.setFirstName(pgMerchant.getFirstName());
    merchantRespObj.setLastName(pgMerchant.getLastName());
    merchantRespObj.setEmailId(pgMerchant.getEmailId());
    merchantRespObj.setPhone(pgMerchant.getPhone());
    merchantRespObj.setCity(pgMerchant.getCity());
    merchantRespObj.setCountry(pgMerchant.getCountry());
    merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
    merchantRespObj.setLocalCurrency(pgMerchant.getLocalCurrency());
    return merchantRespObj;
  }
  
  private MerchantCreateResponse setSubMerchantBasicDetails(MerchantCreateResponse merchantRespObj, MerchantRequest pgMerchant) {
	    merchantRespObj.setId(pgMerchant.getId());
	    merchantRespObj.setBusinessName(pgMerchant.getBusinessName());
	    merchantRespObj.setEntityType(pgMerchant.getEntityType());
	    merchantRespObj.setEmailId(pgMerchant.getEmailId());
	    merchantRespObj.setCardProgramName(pgMerchant.getCardProgramName());
	    merchantRespObj.setCity(pgMerchant.getCity());
	    merchantRespObj.setCountry(pgMerchant.getCountry());
	    merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
	    merchantRespObj.setLocalCurrency(pgMerchant.getCurrency());
	    merchantRespObj.setEntityName(pgMerchant.getEntityName());
	    merchantRespObj.setPhone(pgMerchant.getPhone());
	    return merchantRespObj;
	  }

  private MerchantData setMerchantRespObjData(MerchantData merchantRespObj, PGMerchant pgMerchant) {
    merchantRespObj.setId(pgMerchant.getId());
    merchantRespObj.setBusinessName(pgMerchant.getBusinessName());
    merchantRespObj.setEmailId(pgMerchant.getEmailId());
    merchantRespObj.setCountry(pgMerchant.getCountry());
    merchantRespObj.setState(pgMerchant.getState());
    merchantRespObj.setPhone(pgMerchant.getPhone());
    merchantRespObj.setCity(pgMerchant.getCity());
    merchantRespObj.setCreatedDate(pgMerchant.getCreatedDate());
    merchantRespObj.setUserName(pgMerchant.getUserName());
    return merchantRespObj;
  }

  private GetMerchantListRequest getSearchMerchantDetails(Merchant merchant, GetMerchantListRequest searchMerchant) {
    searchMerchant.setBusinessName(merchant.getBusinessName());
    searchMerchant.setCity(merchant.getCity());
    searchMerchant.setCountry(merchant.getCountry());
    searchMerchant.setEmailId(merchant.getEmailId());
    searchMerchant.setFirstName(merchant.getFirstName());
    searchMerchant.setStatus(merchant.getStatus());
    searchMerchant.setId(merchant.getId());
    searchMerchant.setLastName(merchant.getLastName());
    searchMerchant.setPhone(merchant.getPhone());
    searchMerchant.setEntityType(merchant.getEntityType());
    searchMerchant.setIsoName(merchant.getIsoName());
    searchMerchant.setCardProgramName(merchant.getCardProgramName());
    searchMerchant.setProgramManagerName(merchant.getProgramManagerName());
    return searchMerchant;
  }

  private MerchantData getMerchantStatus(MerchantData merchantRespObj, PGMerchant pgMerchant) {
    if (pgMerchant.getStatus() == STATUS_SUCCESS) {
      merchantRespObj.setStatus(S_STATUS_ACTIVE);
    } else if (pgMerchant.getStatus() == STATUS_DELETED) {
        merchantRespObj.setStatus(S_STATUS_DELETED);
    } else if (pgMerchant.getStatus() == STATUS_PENDING) {
      merchantRespObj.setStatus(S_STATUS_PENDING);
    } else if (pgMerchant.getStatus() == STATUS_SELF_REGISTERATION_PENDING) {
        merchantRespObj.setStatus(S_STATUS_SELFREGISTERED);
    } else if (pgMerchant.getStatus() == STATUS_SUSPENDED) {
      merchantRespObj.setStatus(S_STATUS_SUSPENDED);
    }
    return merchantRespObj;
  }
  
  private MerchantCreateResponse getSubMerchantStatus(MerchantCreateResponse merchantRespObj, MerchantRequest pgMerchant) {
	    if (pgMerchant.getStatus() == STATUS_SUCCESS) {
	      merchantRespObj.setStatus(S_STATUS_ACTIVE);
	    } else if (pgMerchant.getStatus() == STATUS_DELETED) {
	        merchantRespObj.setStatus(S_STATUS_DELETED);
	    } else if (pgMerchant.getStatus() == STATUS_PENDING) {
	      merchantRespObj.setStatus(S_STATUS_PENDING);
	    } else if (pgMerchant.getStatus() == STATUS_SELF_REGISTERATION_PENDING) {
	        merchantRespObj.setStatus(S_STATUS_SELFREGISTERED);
	    } else if (pgMerchant.getStatus() == STATUS_SUSPENDED) {
	      merchantRespObj.setStatus(S_STATUS_SUSPENDED);
	    }
	    return merchantRespObj;
	  }

  private GetMerchantListResponse getMerchantResponse(GetMerchantListRequest searchMerchant, String userType, Long entityId) {
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    if (null != searchMerchant.getSubMerchantCode() && "" != searchMerchant.getSubMerchantCode()) {
      getMerchantListResponse = subMerchantDao.getMerchantlistOnSubMerchantCode(searchMerchant);
    } else if (userType.equalsIgnoreCase(Constants.PM_USER_TYPE)) {
    	getMerchantListResponse = merchantEntityMapDao.fetchMerchantsForPM(searchMerchant, entityId);
	} else if (userType.equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
		getMerchantListResponse = merchantEntityMapDao.fetchMerchantsForISO(searchMerchant,entityId);
	} else if (userType.equalsIgnoreCase(Constants.ADMIN_USER_TYPE)) {
		getMerchantListResponse = merchantProfileDao.getMerchantlist(searchMerchant);
	}
    return getMerchantListResponse;
  }

  @Override
  public Response changeMerchantStatus(Merchant merchant, Integer status)
      throws ChatakAdminException {
    return null;
  }

  @Override
  public List<Option> getActiveMerchants() throws ChatakAdminException {
    return Collections.emptyList();
  }

  @Override
  public DeleteMerchantResponse deleteMerchant(Long id) {
    return merchantProfileDao.deleteMerchantAndAssociatedAccounts(id);
  }

  /**
   * @param countryId
   * @return
   * @throws ChatakAdminException
   */
  @Override
  public Response getStatesByCountry(String countryId) throws ChatakAdminException {
    Response response = new Response();
    CountryRequest countryRequest = countryDao.getCountryByName(countryId);
    if (countryRequest.getName() != null) {
      List<PGState> pgStates = merchantProfileDao.getStateByCountryId(countryRequest.getId());
      List<Option> options = new ArrayList<>(pgStates.size());
      for (PGState state : pgStates) {
        Option option = new Option();
        option.setValue(state.getName());
        option.setLabel(state.getName());
        options.add(option);
      }
      Collections.sort(options, ALPHABETICAL_ORDER);
      response.setErrorCode("00");
      response.setResponseList(options);
      response.setErrorMessage("SUCCESS");
      response.setTotalNoOfRows(options.size());
    } else {
      response.setErrorMessage("failure");
      response.setErrorCode("99");
    }
    return response;
  }

  @Override
  public List<Option> getCountries() {
    List<CountryRequest> countryRequests = countryDao.findAllCountries();
    List<Option> options = new ArrayList<>();
    if (null != countryRequests) {
      for (CountryRequest countryRequest : countryRequests) {
        Option option = new Option();
        option.setLabel(countryRequest.getName());
        option.setValue(countryRequest.getName());
        options.add(option);
      }
    }
    Collections.sort(options, ALPHABETICAL_ORDER);
    return options;
  }

  /**
   * Comparator method for option class
   */
  private static Comparator<Option> ALPHABETICAL_ORDER = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (0 == res) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });

  /**
   * @param emailId
   * @return
   * @throws ChatakAdminException
   */


  @Override
  public MerchantSearchResponse searchSubMerchantList(Merchant merchant)
      throws ChatakAdminException {
    GetMerchantListRequest searchMerchant = new GetMerchantListRequest();
    GetMerchantListResponse getMerchantListResponse = null;
    searchMerchant.setId(merchant.getParentMerchantId());
    searchMerchant.setPageIndex(merchant.getPageIndex());
    searchMerchant.setPageSize(merchant.getPageSize());
    searchMerchant.setNoOfRecords(merchant.getNoOfRecords());
    getMerchantListResponse = subMerchantDao.getSubMerchantListOnMerchantId(searchMerchant);
    List<PGMerchant> pgMerchants = getMerchantListResponse.getSubMerchants();
    MerchantSearchResponse response = new MerchantSearchResponse();
    if (!CollectionUtils.isEmpty(pgMerchants)) {
      List<MerchantData> merchants = new ArrayList<>(pgMerchants.size());
      MerchantData merchantRespObj = null;
      for (PGMerchant pgMerchant : pgMerchants) {
        merchantRespObj = new MerchantData();
        merchantRespObj.setBusinessName(pgMerchant.getBusinessName());
        merchantRespObj.setId(pgMerchant.getId());
        merchantRespObj.setFirstName(pgMerchant.getFirstName());
        merchantRespObj.setLastName(pgMerchant.getLastName());
        merchantRespObj.setPhone(pgMerchant.getPhone());
        merchantRespObj.setCity(pgMerchant.getCity());
        merchantRespObj.setEmailId(pgMerchant.getEmailId());
        merchantRespObj.setCountry(pgMerchant.getCountry());

        getMerchantStatus(merchantRespObj, pgMerchant);

        merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
        merchants.add(merchantRespObj);
      }
      response.setTotalNoOfRows(getMerchantListResponse.getNoOfRecords());
      response.setMerchants(merchants);
    }
    return response;
  }

  public MerchantSearchResponse searchAllMerchant(Merchant merchant) throws ChatakAdminException {
    logger.info("Entering:: MerchantServiceImpl:: searchMerchant method");
    GetMerchantListRequest searchMerchant = new GetMerchantListRequest();
    GetMerchantListResponse getMerchantListResponse = null;
    getSearchMerchantDetails(merchant, searchMerchant);
    searchMerchant.setPageSize(merchant.getPageSize());
    searchMerchant.setPageIndex(merchant.getPageIndex());
    searchMerchant.setNoOfRecords(merchant.getNoOfRecords());
    searchMerchant.setMerchantCode(merchant.getMerchantCode());
    searchMerchant.setSubMerchantCode(merchant.getSubMerchantCode());
    getMerchantListResponse = merchantProfileDao.getAllMerchantlist(searchMerchant);

    List<PGMerchant> pgMerchants = getMerchantListResponse.getMerchants();
    MerchantSearchResponse response = new MerchantSearchResponse();
    if (!CollectionUtils.isEmpty(pgMerchants)) {
      List<MerchantData> merchants = new ArrayList<>(pgMerchants.size());
      MerchantData merchantRespObj = null;
      for (PGMerchant pgMerchant : pgMerchants) {
        merchantRespObj = new MerchantData();
        setMerchantRespObjData(merchantRespObj, pgMerchant);

        getMerchantStatus(merchantRespObj, pgMerchant);

        merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
        merchants.add(merchantRespObj);
      }
      response.setTotalNoOfRows(getMerchantListResponse.getNoOfRecords());
      response.setMerchants(merchants);
    }
    logger.info("Exiting:: MerchantServiceImpl:: searchMerchant method");
    return response;
  }

  public int getTotalNumberOfRecords(GetMerchantListRequest searchMerchant) {
    return merchantProfileDao.getTotalNumberOfRecords(searchMerchant);
  }

  public Map<String, String> getMerchantNameByMerchantCodeAsMap() {
    List<Map<String, String>> merchantList = merchantDao.getMerchantNamesAndMerchantCode();
    Map<String, String> merchantMap = new HashMap<>();
    if (StringUtil.isListNotNullNEmpty(merchantList)) {
      for (Map<String, String> map : merchantList) {
        merchantMap.put(map.get("0"), map.get("1"));
      }
    }
    return merchantMap;
  }

  @Override
  public void updateSubMerchantsPartnerId(String merchantCode) {
    Long merchantId = merchantDao.getMerchantIdOnMerchantCode(merchantCode);
    List<PGMerchant> subMerchantsLsit = merchantDao.findById(merchantId);
    subMerchantDao.updateSubMerchantsPartnerAndAgentId(subMerchantsLsit);
  }

  @Override
  public Map<String, String> getMerchantNameAndMerchantCodeAsMapByMerchantType(
      String merchantType) {
    List<Map<String, String>> merchantList =
        merchantDao.getMerchantNameAndMerchantCodeByMerchantType(merchantType);
    Map<String, String> merchantMap = new HashMap<>();
    if (StringUtil.isListNotNullNEmpty(merchantList)) {
      for (Map<String, String> map : merchantList) {
        merchantMap.put(map.get("0"), map.get("1"));
      }
    }
    return merchantMap;
  }

  @Override
  public Long getMerchantIdOnMerchantCode(Long parentMerchantId) {
    return merchantDao.getMerchantIdOnMerchantCode(parentMerchantId.toString());
  }

  @Override
  public MerchantSearchResponse searchSubMerchants(Merchant merchant, String userType, Long entityId) throws ChatakAdminException {
    GetMerchantListRequest searchMerchant = new GetMerchantListRequest();
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    getSearchMerchantDetails(merchant, searchMerchant);
    searchMerchant.setMerchantCode(merchant.getMerchantCode());
    searchMerchant.setSubMerchantCode(merchant.getSubMerchantCode());
    searchMerchant.setProgramManagerName(merchant.getProgramManagerName());
    searchMerchant.setCardProgramName(merchant.getCardProgramName());
    searchMerchant.setId(merchant.getParentMerchantId());
    searchMerchant.setPageIndex(merchant.getPageIndex());
    searchMerchant.setPageSize(merchant.getPageSize());
    searchMerchant.setNoOfRecords(merchant.getNoOfRecords());
    searchMerchant.setIsoName(merchant.getIsoName());
   
    if (userType.equalsIgnoreCase(Constants.ADMIN_USER_TYPE)) {
    	getMerchantListResponse = subMerchantDao.getSubMerchantList(searchMerchant);
    } else if (userType.equalsIgnoreCase(Constants.PM_USER_TYPE)) {
    	getMerchantListResponse = merchantEntityMapDao.fetchSubMerchantsForPM(searchMerchant, entityId);
    } else if (userType.equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
    	getMerchantListResponse = merchantEntityMapDao.fetchSubMerchantsForISO(searchMerchant, entityId);
    } 
    
    List<MerchantRequest> pgMerchants = getMerchantListResponse.getMerchantRequestList();
    MerchantSearchResponse response = new MerchantSearchResponse();
    if (!CollectionUtils.isEmpty(pgMerchants)) {
      List<MerchantCreateResponse> merchants = new ArrayList<>(pgMerchants.size());
      MerchantCreateResponse merchantRespObj = null;
      for (MerchantRequest pgMerchant : pgMerchants) {
        merchantRespObj = new MerchantCreateResponse();
        setSubMerchantListDetails(merchantRespObj, pgMerchant);
        
        getSubMerchantStatus(merchantRespObj, pgMerchant);
        merchants.add(merchantRespObj);
      }
      response.setMerchantCreateResponseList(merchants);
    }
    response.setTotalNoOfRows(getMerchantListResponse.getNoOfRecords());
    return response;
  }

  private MerchantCreateResponse setSubMerchantListDetails(MerchantCreateResponse merchantRespObj, MerchantRequest pgMerchant) {
    setSubMerchantBasicDetails(merchantRespObj, pgMerchant);
    return merchantRespObj;
  }

  @Override
  public Map<String, String> getMerchantCodeAndCompanyName(String merchantType) {
    List<Map<String, String>> dataList = merchantDao.getMerchantCodeAndCompanyName(merchantType);
    Map<String, String> merchantDataMap = new HashMap<>();
    if (StringUtil.isListNotNullNEmpty(dataList)) {
      for (Map<String, String> map : dataList) {
        merchantDataMap.put(map.get("0"), map.get("1"));
      }
    }
    return merchantDataMap;
  }

  @Override
  public List<Merchant> getMerchantByStatus(Integer status) {
    List<Merchant> merchants = new ArrayList<>();
    List<PGMerchant> merchant = merchantUpdateDao.getMerchantsByStatus(status);
    if (StringUtil.isListNotNullNEmpty(merchant)) {
      for (PGMerchant merch : merchant) {
        addMerchantsList(merchants, merch);
      }
    }
    return distinctList(merchants);
  }

  private List<Merchant> addMerchantsList(List<Merchant> merchants, PGMerchant merch) {
    Merchant localMerchant = new Merchant();
    localMerchant.setBusinessName(merch.getBusinessName());
    localMerchant.setAddress1(merch.getAddress1());
    localMerchant.setAddress2(merch.getAddress2());
    localMerchant.setStatus(merch.getStatus());
    localMerchant.setCreatedBy(merch.getCreatedBy());
    localMerchant.setId(merch.getId());
    merchants.add(localMerchant);
    return merchants;
  }

  @Override
  public List<Option> getCurrencies() {
    List<CurrencyRequest> currencyRequests = currencyDao.findAllCurrencies();
    List<Option> options = new ArrayList<>();
    if (currencyRequests != null) {
      for (CurrencyRequest currencyRequest : currencyRequests) {
        Option option = new Option();
        option.setLabel(currencyRequest.getCode());
        option.setValue(currencyRequest.getCode());
        options.add(option);
      }
    }
    Collections.sort(options, ALPHABETICAL_ORDER);
    return options;
  }

  @Override
  public MerchantResponse findByMerchantId(Long getMerchantId)
      throws InstantiationException, IllegalAccessException {
	  MerchantResponse merchantResponse = new MerchantResponse();
	  List<ProgramManagerRequest> programManagerRequests = new ArrayList<>(); 
	  ProgramManagerRequest programManagerRequest=null;
	  CardProgramRequest cardProgramRequest = null;
	  List<IsoRequest> isoRequests = new ArrayList<>();
	  IsoRequest isoRequest = null;
    Merchant merchant = new Merchant();
    PGMerchant pGMerchant = merchantProfileDao.getMerchantById(getMerchantId);
    List<MerchantCurrencyMapping> merchantCurrencyMapping = new ArrayList<>();
    List<PGMerchantCurrencyMapping> pgMerchantCurrencyMapping =
        currencyDao.findByMerchantCode(pGMerchant.getMerchantCode());
    if (StringUtil.isListNotNullNEmpty(pgMerchantCurrencyMapping)) {
      merchantCurrencyMapping =
          CommonUtil.copyListBeanProperty(pgMerchantCurrencyMapping, MerchantCurrencyMapping.class);
    }
    merchant.setSelectedCurrencyMapping(merchantCurrencyMapping);
    List<PGMerchantEntityMap> merchantEntityMaps = merchantEntityMapDao.findByMerchantId(getMerchantId);
    MerchantResponse response;    
		if (StringUtil.isListNotNullNEmpty(merchantEntityMaps)
				&& merchantEntityMaps.get(0).getEntitytype().equals(PGConstants.PROGRAM_MANAGER_NAME)){
    		for(PGMerchantEntityMap entityMap : merchantEntityMaps){
    			setFindByMerchantId(programManagerRequests, merchant, entityMap);
        	}
    		 response = merchantCardProgramMapDao.findCardProgramByMerchantId(getMerchantId);
    		if(null != response){
    			merchantResponse.setCardProgramRequests(response.getCardProgramRequests());
    		}
      } else {
    	for(PGMerchantEntityMap entityMap : merchantEntityMaps){
			merchant.setEntityId(entityMap.getEntityId());
			isoRequest = new IsoRequest();
			merchant.setAssociatedTo(entityMap.getEntitytype());
			List<Iso> list = isoServiceDao.findByIsoId(merchant.getEntityId());
				if (StringUtil.isListNotNullNEmpty(list)) {
					isoRequest.setIsoName(list.get(0).getIsoName());
					isoRequest.setId(list.get(0).getId());
					isoRequests.add(isoRequest);
				}
			}
    	 response = isoServiceDao.findCardProgramByMerchantId(getMerchantId);
    	 if(null != response){
 			merchantResponse.setCardProgramRequests(response.getCardProgramRequests());
 		}
	}
    merchantResponse.setProgramManagerRequests(programManagerRequests);
    merchantResponse.setIsoRequests(isoRequests);
    merchantResponse.setMerchant(merchant);
    return merchantResponse;
  }

	/**
	 * @param programManagerRequests
	 * @param merchant
	 * @param entityMap
	 */
	private void setFindByMerchantId(List<ProgramManagerRequest> programManagerRequests, Merchant merchant,
			PGMerchantEntityMap entityMap) {
		ProgramManagerRequest programManagerRequest;
		merchant.setEntityId(entityMap.getEntityId());
		merchant.setAssociatedTo(entityMap.getEntitytype());
		programManagerRequest = ProgramManagerDao.findProgramManagerById(merchant.getEntityId());
		if (programManagerRequest != null) {
			programManagerRequest.setProgramManagerName(programManagerRequest.getProgramManagerName());
			programManagerRequest.setId(programManagerRequest.getId());
			programManagerRequests.add(programManagerRequest);
		}
	}

  @Override
  public MerchantSearchResponse getMerchantCode(String merchantCode) {
    MerchantSearchResponse merchantSearchResponse = new MerchantSearchResponse();
    PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(merchantCode);
    merchantSearchResponse.setMerchantData(pgMerchant);
    return merchantSearchResponse;
  }

  @Override
  public List<Merchant> getMerchantByStatusPendingandDecline() {
    List<Merchant> merchants = new ArrayList<>();
    List<PGMerchant> merchant = merchantUpdateDao.getMerchantByStatusPendingandDecline();
    if (StringUtil.isListNotNullNEmpty(merchant)) {
      for (PGMerchant merch : merchant) {
        addMerchantsList(merchants, merch);
      }
    }
    return merchants;
  }

  public static List<Merchant> distinctList(List<Merchant> merchants) {
    if (merchants.isEmpty()) {
      return merchants;
    } else {
      Collections.sort(merchants, (Merchant o1, Merchant o2) -> {
        return o1.getId() < o2.getId() ? 1 : validateId(o1, o2);
      });
      return merchants;
    }
  }

  private static int validateId(Merchant o1, Merchant o2) {
    return (o1.getId() > o2.getId()) ? -1 : 0;
  }

  public Response getAgentNames(String currencyAlpha) {
    Response response = new Response();
    CurrencyDTO currency = new CurrencyDTO();
    currency.setCurrencyCodeAlpha(currencyAlpha);
    try {
      List<Option> options = new ArrayList<>();
      String output = JsonUtil.postIssuanceRequest(currency,
              "/agentManagementService/agentService/searchAllAgent",String.class);
      AgentDTOResponse agentDTOlist = mapper.readValue(output, AgentDTOResponse.class);
      if (agentDTOlist != null && agentDTOlist.getAgentDTOlist() != null) {
        for (AgentDTO agentRequest : agentDTOlist.getAgentDTOlist()) {
          Option option = new Option();
          option.setLabel(String.valueOf(agentRequest.getAgentId()));
          option.setValue(agentRequest.getAgentName());
          options.add(option);
        }
      }
      Collections.sort(options, ALPHABETICAL_ORDER);
      response.setResponseList(options);
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
      response.setTotalNoOfRows(options.size());
      return response;
    } catch (Exception e) {
      logger.error("Error :: MerchantUpdateServiceImpl :: getAgentNames", e);
    }
    return null;
  }

	@Override
	public List<Long> findByEntityIdAndEntitytype(List<Long> entityIds, String entityType) {
		List<Long> merchantIds = new ArrayList<>();
		List<PGMerchantEntityMap> merchantEntityMaps;
		for (Long entityId : entityIds) {
			merchantEntityMaps = merchantUpdateDao.findByEntityIdAndEntitytype(entityId, entityType);
			for (PGMerchantEntityMap merchantEntityMap : merchantEntityMaps) {
				merchantIds.add(merchantEntityMap.getMerchantId());
			}
		}
		return merchantIds;
	}

	private MerchantCreateResponse setMerchantDetails(MerchantCreateResponse merchantRespObj,
			MerchantRequest pgMerchant) {
		merchantRespObj.setId(pgMerchant.getId());
		merchantRespObj.setBusinessName(pgMerchant.getBusinessName());
		merchantRespObj.setEntityName(pgMerchant.getEntityName());
		merchantRespObj.setEmailId(pgMerchant.getEmailId());
		merchantRespObj.setCardProgramName(pgMerchant.getCardProgramName());
		merchantRespObj.setCity(pgMerchant.getCity());
		merchantRespObj.setCountry(pgMerchant.getCountry());
		merchantRespObj.setEntityType(pgMerchant.getEntityType());
		merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
		merchantRespObj.setId(pgMerchant.getId());
		merchantRespObj.setLocalCurrency(pgMerchant.getCurrency());
		merchantRespObj.setPhone(pgMerchant.getPhone());
		return merchantRespObj;
	}

	private MerchantCreateResponse getMerchantCreateStatus(MerchantCreateResponse merchantRespObj,
			MerchantRequest pgMerchant) {
		if (pgMerchant.getStatus() == STATUS_SUCCESS) {
			merchantRespObj.setStatus(S_STATUS_ACTIVE);
		} else if (pgMerchant.getStatus() == STATUS_DELETED) {
			merchantRespObj.setStatus(S_STATUS_DELETED);
		} else if (pgMerchant.getStatus() == STATUS_PENDING) {
			merchantRespObj.setStatus(S_STATUS_PENDING);
		} else if (pgMerchant.getStatus() == STATUS_HOLD) {
			merchantRespObj.setStatus(PG_TXN_DECLILNED);
		} else if (pgMerchant.getStatus() == STATUS_SUSPENDED) {
			merchantRespObj.setStatus(S_STATUS_SUSPENDED);
		}
		return merchantRespObj;
	}

	@Override
	public List<Merchant> getPmMerchantByEntityIdandEntityType(Long entityId, String entityType) {
		List<Merchant> merchants = new ArrayList<>();
		List<PGMerchant> merchant = merchantUpdateDao.getPmMerchantByEntityIdandEntityType(entityId, entityType);
		if (StringUtil.isListNotNullNEmpty(merchant)) {
			for (PGMerchant merch : merchant) {
				addMerchantsList(merchants, merch);
			}
		}
		return merchants;
	}
}
