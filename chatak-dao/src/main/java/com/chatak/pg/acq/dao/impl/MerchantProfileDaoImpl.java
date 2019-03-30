/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGAccountHistory;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.AdminUserDaoRepository;
import com.chatak.pg.acq.dao.repository.FeeProgramRepository;
import com.chatak.pg.acq.dao.repository.MerchantBankRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.MerchantUserRepository;
import com.chatak.pg.acq.dao.repository.StateRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.AccountBalanceReportDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.MerchantRequest;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.AddMerchantBankRequest;
import com.chatak.pg.user.bean.AddMerchantBankResponse;
import com.chatak.pg.user.bean.DeleteMerchantRequest;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2017
 * @Time: 5:25:28 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("merchantProfileDao")
public class MerchantProfileDaoImpl extends MerchantDaoImpl implements MerchantProfileDao {

  private static Logger logger = Logger.getLogger(MerchantProfileDaoImpl.class);

  @Autowired
  private MerchantRepository merchantRepository;

  @Autowired
  private FeeProgramRepository feeProgramRepository;

  @Autowired
  private StateRepository stateRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private MerchantBankRepository bankRepository;

  @Autowired
  private MerchantUserRepository merchantUserRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private AdminUserDaoRepository adminUserDaoRepository;

  @Override
  public GetMerchantListResponse getMerchantlist(GetMerchantListRequest searchMerchant) {
	  logger.info("MerchantDaoImpl | getMerchantlist | Entering");
	    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
	    List<MerchantRequest> merchantList = new ArrayList<>();
	    try {
	    	 int startIndex = 0;
	         int endIndex = 0;
	         Integer totalRecords = searchMerchant.getNoOfRecords();

	         if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
	           totalRecords = getTotalNumberOfMerchantRecords(searchMerchant);
	           searchMerchant.setNoOfRecords(totalRecords);
	         }
	         getMerchantListResponse.setNoOfRecords(totalRecords);
	         if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
	        	 startIndex = 0;
	         } else {
	        	 startIndex = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
	        	 endIndex = searchMerchant.getPageSize() + startIndex;
	         }
	         	int resultIndex = endIndex - startIndex;
				StringBuilder query = new StringBuilder(" SELECT e.MerchantCode,")
						  .append(" e.MerchantName,")
						  .append("   e.EntityType,")
						  .append("   e.IsoName ,")
						  .append("   e.ProgramManager ,")
						  .append("   e.Email,")
						  .append("   e.Country,")
						  .append("   e.State,")
						  .append("   e.Status,")
						  .append("   e.CreatedDate,")
						  .append("   e.Id,")
						  .append("   e.Currency,e.PHONE")
						  .append(" FROM ")
						  .append("   (SELECT d.MerchantCode,")
						  .append("     d.MerchantName,")
						  .append("     d.EntityType,")
						  .append("     d.IsoName ,")
						  .append("     group_concat(d.ProgramManager) AS ProgramManager ,")
						  .append("     d.Email,")
						  .append("     d.Country,")
						  .append("     d.State,")
						  .append("     d.Status,")
						  .append("     d.CreatedDate,")
						  .append("     d.Id,")
						  .append("     d.Currency,d.PHONE")
						  .append("   FROM")
						  .append("     (SELECT c.MerchantCode,")
						  .append("       c.MerchantName,")
						  .append("       c.EntityType,")
						  .append("       group_concat( c.IsoName ) IsoName ,")
						  .append("       c.ProgramManager ,")
						  .append("       c.Email,")
						  .append("       c.Country,")
						  .append("       c.State,")
						  .append("       c.Status,")
						  .append("       c.CreatedDate,")
						  .append("       c.Id,")
						  .append("       c.Currency,c.PHONE")
						  .append("     FROM ")
						  .append("       ( SELECT DISTINCT b.MerchantCode,")
						  .append("         b.MerchantName,")
						  .append("         b.EntityType,")
						  .append("         b.IsoName ,")
						  .append("         b.ProgramManager ,")
						  .append("         b.Email,")
						  .append("         b.Country,")
						  .append("         b.State,")
						  .append("         b.Status,")
						  .append("         b.CreatedDate,")
						  .append("         b.Id,")
						  .append("         b.Currency,b.PHONE")
						  .append("       FROM")
						  .append("         (SELECT a.MerchantCode,")
						  .append("           a.MerchantName,")
						  .append("           a.EntityType,")
						  .append("           CASE")
						  .append("             WHEN a.EntityType='ISO'")
						  .append("             THEN a.IsoName")
						   .append("    END AS IsoName ,")
						  .append("           CASE")
						  .append("             WHEN a.EntityType='Program Manager'")
						  .append("             THEN a.IsoName")
						  .append("           END AS ProgramManager,")
						  .append("           a.Email,")
						  .append("           a.Country,")
						  .append("           a.State,")
						  .append("           a.Status,")
						  .append("           a.CreatedDate,")
						  .append("           a.Id,")
						   .append("          a.Currency,a.PHONE")
						  .append("         FROM")
						  .append("           (SELECT pgm.MERCHANT_CODE AS MerchantCode,")
						  .append("             pgm.BUSINESS_NAME       AS MerchantName,")
						  .append("             pgme.ENTITY_TYPE        AS EntityType,")
						  .append("             pgiso.ISO_NAME          AS IsoName,")
						  .append("             pgm.EMAIL               AS Email,")		 
						  .append("             pgm.COUNTRY Country,")
						  .append("             pgm.STATE State,")
						  .append("             pgm.STATUS         AS Status,")
						  .append("             pgm.CREATED_DATE   AS CreatedDate,")
						  .append("             pgm.ID             AS Id,")
						  .append("             pgm.LOCAL_CURRENCY AS Currency,pgm.PHONE as PHONE")
						  .append("           FROM PG_MERCHANT pgm")			  
						  .append("           JOIN PG_MERCHANT_ENTITY_MAPPING pgme")
						  .append("           ON pgme.MERCHANT_ID=pgm.id")
						  .append("           JOIN PG_ISO pgiso")
						  .append("           ON pgiso.id=pgme.ENTITY_ID")
						  .append("           UNION ALL")
						  .append("           SELECT pgm.MERCHANT_CODE AS MerchantCode,")
						  .append("             pgm.BUSINESS_NAME      AS MerchantName,")
						  .append("             pgme.ENTITY_TYPE       AS EntityType,")
						  .append("             pgiso.PROGRAM_MANAGER_NAME ProgramManager,")
						  .append("             pgm.EMAIL              AS Email,")				  
						  .append("             pgm.COUNTRY Country,")
						  .append("             pgm.STATE State,")
						  .append("             pgm.STATUS         AS Status,")
						  .append("             pgm.CREATED_DATE   AS CreatedDate,")
						  .append("             pgm.ID             AS Id,")
						  .append("             pgm.LOCAL_CURRENCY AS Currency,pgm.PHONE as PHONE")
						  .append("           FROM PG_MERCHANT pgm")	 
						  .append("           JOIN PG_MERCHANT_ENTITY_MAPPING pgme")
						  .append("           ON pgme.MERCHANT_ID=pgm.id")
						  .append("           JOIN PG_PROGRAM_MANAGER pgiso")
						  .append("           ON pgiso.id=pgme.ENTITY_ID")
						  .append("           )a")
						  .append("         )b")
						  .append("       )c")
						  .append("    GROUP BY c.MerchantCode,")
						  .append("      c.MerchantName,")
						  .append("      c.EntityType,")
						  .append("      c.ProgramManager ,")
						  .append("      c.Email,")
						  .append("      c.Country,")
						  .append("      c.State,")
						  .append("      c.Status,")
						  .append("      c.CreatedDate,")
						  .append("      c.Id,")
						  .append("      c.Currency,c.PHONE")
						  .append("    )d")
						    .append(" GROUP BY d.MerchantCode,")
						  .append("    d.MerchantName,")
						  .append("    d.EntityType,")
						  .append("    d.IsoName ,")
						  .append("    d.Email,")
						  .append("    d.Country,")
						  .append("    d.State,")
						  .append("    d.Status,")
						  .append("    d.CreatedDate,")
						  .append("    d.Id,")
						  .append("    d.Currency,d.PHONE")
						  .append("  )e")
								.append(" where  (:entityType is null or e.EntityType=:entityType) ")
								.append(" and (:programManagerName is null or e.ProgramManager=:programManagerName)")
								.append(" and (:isoName is null or e.IsoName=:isoName)")
								.append(" and (:merchantCode is null or e.MerchantCode=:merchantCode)")
								.append(" and (:businessName is null or e.MerchantName like CONCAT('%',:businessName,'%'))")
								.append(" and (:emailId is null or e.Email=:emailId)")
								.append(" and (:country is null or e.Country=:country)")
								.append(" and (:status is null or e.Status=:status)")
								 .append(" ORDER BY e.CreatedDate DESC")
								.append("  limit :startIndex,:resultSize");
				Query qry = entityManager.createNativeQuery(query.toString());
				qry.setParameter("startIndex", startIndex);
				qry.setParameter("resultSize", resultIndex);
				qry.setParameter("entityType", getMerchantlistEntityType(searchMerchant));
				qry.setParameter("programManagerName", getMerchantlistProgramManagerName(searchMerchant));
				qry.setParameter("isoName", getMerchantlistIsoName(searchMerchant));
				qry.setParameter("merchantCode", getMerchantlistMerchantCode(searchMerchant));
				qry.setParameter("businessName", getMerchantlistBusinessName(searchMerchant));
				qry.setParameter("emailId", getMerchantlistEmailId(searchMerchant));
				qry.setParameter("country", getMerchantlistCountry(searchMerchant));
				qry.setParameter("status", getMerchantlistStatus(searchMerchant));
				List<Object> cardProgramResponse = qry.getResultList();
				
				MerchantRequest request = null;
				if (StringUtil.isListNotNullNEmpty(cardProgramResponse)) {
					Iterator<Object> itr = cardProgramResponse.iterator();
					while (itr.hasNext()) {
						Object[] object = (Object[]) itr.next();
						request = new MerchantRequest();
						request.setMerchantCode(object[0].toString());
						request.setBusinessName(object[1].toString());
						request.setEntityType(object[2].toString());
						if(request.getEntityType().equals(Constants.ISO_USER_TYPE)){
							request.setEntityName(getEntityName(object,Integer.parseInt("3")));
						}else {
							request.setEntityName(getEntityName(object,Integer.parseInt("4"))); 
						}
						request.setEmailId(object[5].toString());
						request.setCountry(object[6].toString());
						request.setState(object[7].toString());
						request.setStatus(Integer.valueOf(object[8].toString()));
						request.setCreatedDate(object[9].toString());
						request.setId(((BigInteger)object[10]).longValue());
						request.setCurrency(object[11].toString());
						request.setPhone(((BigInteger)object[12]).longValue());
						merchantList.add(request);
					}
				}
				getMerchantListResponse.setNoOfRecords(searchMerchant.getNoOfRecords());    	
				getMerchantListResponse.setMerchantRequests(merchantList);
	    } catch (Exception e) {
	      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
	      getMerchantListResponse
	          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
	      logger.error("MerchantDaoImpl | getMerchantlist | Exception " + e);
	    }
	    logger.info("MerchantDaoImpl | getMerchantlist | Exiting");
	    return getMerchantListResponse;
  }

	/**
	 * @param object
	 * @return
	 */
	private String getEntityName(Object[] object,Integer index) {
		return object[index]!= null ? object[index].toString() : "";
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Integer getMerchantlistStatus(GetMerchantListRequest searchMerchant) {
		return searchMerchant.getStatus() != null ? searchMerchant.getStatus().intValue() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistCountry(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getCountry().equals("") ? searchMerchant.getCountry() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistEmailId(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getEmailId().equals("") ? searchMerchant.getEmailId() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistBusinessName(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getBusinessName().equals("") ? searchMerchant.getBusinessName() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistMerchantCode(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getMerchantCode().equals("") ? searchMerchant.getMerchantCode() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistCardProgramName(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getCardProgramName().equals("") ? searchMerchant.getCardProgramName() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistIsoName(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getIsoName().equals("") ? searchMerchant.getIsoName() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistProgramManagerName(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getProgramManagerName().equals("") ? searchMerchant.getProgramManagerName() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object getMerchantlistEntityType(GetMerchantListRequest searchMerchant) {
		return !searchMerchant.getEntityType().equals("") ? searchMerchant.getEntityType() : null;
	}

  private void setPGMerchantDetails(PGMerchant merchant, Tuple tuple) {
	merchant.setId(tuple.get(QPGMerchant.pGMerchant.id));
	merchant.setFirstName(tuple.get(QPGMerchant.pGMerchant.firstName));
	merchant.setBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
	merchant.setLastName(tuple.get(QPGMerchant.pGMerchant.lastName));
	merchant.setEmailId(tuple.get(QPGMerchant.pGMerchant.emailId));
	merchant.setPhone(tuple.get(QPGMerchant.pGMerchant.phone));
	merchant.setCity(tuple.get(QPGMerchant.pGMerchant.city));
	merchant.setCountry(tuple.get(QPGMerchant.pGMerchant.country));
	merchant.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));
	merchant.setStatus(tuple.get(QPGMerchant.pGMerchant.status));
	merchant.setAgentAccountNumber(tuple.get(QPGMerchant.pGMerchant.agentAccountNumber));
	merchant.setAgentClientId(tuple.get(QPGMerchant.pGMerchant.agentClientId));
	merchant.setAgentANI(tuple.get(QPGMerchant.pGMerchant.agentANI));
  }

  @Override
  public GetMerchantListResponse getAllMerchantlist(GetMerchantListRequest searchMerchant) {
    logger.info("MerchantDaoImpl | getAllMerchantlist | Entering");
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    List<PGMerchant> merchantList = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = searchMerchant.getNoOfRecords();

      if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
        totalRecords = getTotalNumberOfRecords(searchMerchant);
        searchMerchant.setNoOfRecords(totalRecords);
      }
      getMerchantListResponse.setNoOfRecords(totalRecords);
      if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
        limit = searchMerchant.getPageSize();
      }
      if (!"".equals(searchMerchant.getMerchantCode())
          && null != searchMerchant.getMerchantCode()) {
        PGMerchant pgMerchant =
            merchantRepository.findByMerchantCode(searchMerchant.getMerchantCode());
        if (null != pgMerchant && null != pgMerchant.getParentMerchantId()) {
          getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
          getMerchantListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
          return getMerchantListResponse;
        }
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant)
          .where(isBusinessNameLike(searchMerchant.getBusinessName()),
              isMerchantCodeEq(searchMerchant.getMerchantCode()),
              isCityLike(searchMerchant.getCity()), isCountryEq(searchMerchant.getCountry()),
              isEmailLike(searchMerchant.getEmailId()),
              isFirstNameLike(searchMerchant.getFirstName()),
              isLastNameLike(searchMerchant.getLastName()),
              isUserNameLike(searchMerchant.getUserName()),
              isCreatedDateLike(searchMerchant.getCreatedDate()),
              isPhoneEq(searchMerchant.getPhone()), isStatusEq(searchMerchant.getStatus()))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.firstName,
              QPGMerchant.pGMerchant.lastName, QPGMerchant.pGMerchant.emailId,
              QPGMerchant.pGMerchant.phone, QPGMerchant.pGMerchant.city,
              QPGMerchant.pGMerchant.country, QPGMerchant.pGMerchant.status,
              QPGMerchant.pGMerchant.userName, QPGMerchant.pGMerchant.createdDate,
              QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.parentMerchantId,
              QPGMerchant.pGMerchant.id, QPGMerchant.pGMerchant.agentAccountNumber,
              QPGMerchant.pGMerchant.agentANI, QPGMerchant.pGMerchant.agentClientId);
      if (!CollectionUtils.isEmpty(tupleList)) {
        merchantList = processTupleList(tupleList);
      }
      if (merchantList != null && !merchantList.isEmpty()) {
        getMerchantListResponse.setMerchants(merchantList);
        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        getMerchantListResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        getMerchantListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }

    } catch (Exception e) {
      logger.error("MerchantDaoImpl | getAllMerchantlist | Exception " + e);
      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getMerchantListResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("MerchantDaoImpl | getAllMerchantlist | Exiting");
    return getMerchantListResponse;
  }

  private List<PGMerchant> processTupleList(List<Tuple> tupleList) {
	List<PGMerchant> merchantList = new ArrayList<>();
	PGMerchant merchant = null;
	for (Tuple tuple : tupleList) {
	  merchant = new PGMerchant();
	  setPGMerchantDetails(merchant, tuple);
	  merchant.setCreatedDate(tuple.get(QPGMerchant.pGMerchant.createdDate));
	  merchant.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
	  merchantList.add(merchant);
	}
	return merchantList;
  }

  @Override
  public PGMerchant getMerchantById(Long id) {
    return merchantRepository.findById(id);
  }

  @Override
  public Response getUserByUsername(String userName) {
    PGMerchantUsers merchantUser = merchantUserRepository.findByUserNameAndStatusNotLike(userName, PGConstants.STATUS_DELETED);
    Response response = new Response();
    if (merchantUser != null) {
      return duplicateUserEntryResponse(response);
    }
    PGMerchant merchant = merchantRepository.findByUserName(userName);
    PGAdminUser adminUsers =
        adminUserDaoRepository.findByUserNameAndStatusNotLike(userName, PGConstants.STATUS_DELETED);
    if (merchant != null || adminUsers != null) {
      return duplicateUserEntryResponse(response);
    } else {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return response;
    }
  }

  private Response duplicateUserEntryResponse(Response response) {
    response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
    response.setErrorMessage(
        ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
    return response;
  }

  @Override
  public FeeProgramNameListDTO getActiveFeePrograms() {
    logger.info("MerchantDaoImpl | getActiveFeePrograms | Entering");
    FeeProgramNameListDTO feeProgramNameListDTO = new FeeProgramNameListDTO();
    List<PGFeeProgram> feeProgramsList = null;
    try {
      feeProgramsList = feeProgramRepository.findByStatus("Active");
      if (feeProgramsList != null && !feeProgramsList.isEmpty()) {
        feeProgramNameListDTO.setFeeProgramDTOs(feeProgramsList);
        feeProgramNameListDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        feeProgramNameListDTO.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        feeProgramNameListDTO.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        feeProgramNameListDTO.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }
    } catch (Exception e) {
      logger.error("MerchantDaoImpl | getActiveFeePrograms | Exception " + e);
      feeProgramNameListDTO.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      feeProgramNameListDTO
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("MerchantDaoImpl | getActiveFeePrograms | Exiting");
    return feeProgramNameListDTO;
  }

  @Override
  public DeleteMerchantResponse deleteMerchant(Long id) {
    DeleteMerchantResponse deleteMerchantResponse = new DeleteMerchantResponse();
    PGMerchant merchantDb = merchantRepository.findById(id);
    if (null != merchantDb) {

      merchantDb.setStatus(PGConstants.STATUS_DELETED);
      merchantDb.setUpdatedDate(DateUtil.getCurrentTimestamp());
      merchantDb.getPgMerchantUsers().get(0).setStatus(merchantDb.getStatus());
      merchantDb = merchantRepository.save(merchantDb);
      if (null == merchantDb.getParentMerchantId()) {
        List<PGMerchant> subMerchantLists =
            merchantRepository.findByParentMerchantId(merchantDb.getId());
        if (CommonUtil.isListNotNullAndEmpty(subMerchantLists)) {
          validateSubMerchantLists(merchantDb, subMerchantLists);
        }
      }
      deleteMerchantResponse.setIsdeleated(true);
      deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      deleteMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    } else {
      deleteMerchantResponse.setIsdeleated(false);
      deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      deleteMerchantResponse.setErrorMessage(PGConstants.MERCHANT_DETAIL_DELETE_ERROR);
    }
    return deleteMerchantResponse;
  }

  private void validateSubMerchantLists(PGMerchant merchantDb, List<PGMerchant> subMerchantLists) {
	for (PGMerchant subMerchant : subMerchantLists) {
	    PGAccount pgSubMerchantAccount = accountRepository.findByEntityIdAndCategory(
	        subMerchant.getMerchantCode(), PGConstants.PRIMARY_ACCOUNT);
	    if (null != pgSubMerchantAccount) {
	      if (!PGConstants.STATUS_DELETED.equals(subMerchant.getStatus())) {
	        subMerchant.setStatus(merchantDb.getStatus());
	        subMerchant.setUpdatedDate(DateUtil.getCurrentTimestamp());
	        subMerchant.getPgMerchantUsers().get(0).setStatus(merchantDb.getStatus());
	      }
	      merchantRepository.save(subMerchant);
	      accountRepository.save(pgSubMerchantAccount);
	    }
	  }
  }

  @Override
  public DeleteMerchantResponse deleteMerchantAndAssociatedAccounts(Long id) {
    DeleteMerchantResponse deleteMerchantResponse = new DeleteMerchantResponse();
    PGMerchant pgMerchant = merchantRepository.findById(id);
    if (pgMerchant != null) {
      List<PGTransaction> transactions =
          transactionRepository.getAllTransactionsOnMerchantCode(pgMerchant.getMerchantCode());
      if (StringUtil.isListNotNullNEmpty(transactions)) {

        deleteMerchantResponse.setIsdeleated(false);
        deleteMerchantResponse.setErrorMessage(
            messageSource.getMessage("chatak.acquirer.merchantbank.linked.transaction.error", null,
                LocaleContextHolder.getLocale()));
        return deleteMerchantResponse;
      } else {
        if (pgMerchant.getParentMerchantId() == null) {
          validateParentMerchantId(pgMerchant);
        } else {
          // This is a sub merchant
          // Delete associated bank accounts
          // Delete user accounts
          deleteMerchantUsersAndAccount(pgMerchant);
        }
      }
    } else {
      deleteMerchantResponse.setIsdeleated(false);
      deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      deleteMerchantResponse.setErrorMessage(PGConstants.MERCHANT_DETAIL_DELETE_ERROR);
      return deleteMerchantResponse;
    }
    // Everything went fine
    deleteMerchantResponse.setIsdeleated(true);
    deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    deleteMerchantResponse
        .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    return deleteMerchantResponse;
  }

  private void validateParentMerchantId(PGMerchant pgMerchant) {
	// Is a parent merchant
	  // Check if parent has sub merchants
	  List<PGMerchant> subMerchantLists = merchantRepository
	      .findByParentMerchantIdAndStatus(pgMerchant.getId(), PGConstants.STATUS_SUCCESS);
	  if (CommonUtil.isListNotNullAndEmpty(subMerchantLists)) {
	    validatePGMerchantList(subMerchantLists);
	  }
	  // Delete parent merchant
	  // Delete parent bank account
	  // Delete parent user account
	  deleteMerchantUsersAndAccount(pgMerchant);
  }

  private void validatePGMerchantList(List<PGMerchant> subMerchantLists) {
	// Delete all sub merchants and their respective accounts
	for (PGMerchant subMerchant : subMerchantLists) {
	  deleteMerchantUsersAndAccount(subMerchant);
	}
  }

  private int getTotalNumberOfMerchantRecords(GetMerchantListRequest searchMerchant) {
	  StringBuilder query = new StringBuilder(" SELECT e.MerchantCode,")
			  .append(" e.MerchantName,")
			  .append("   e.EntityType,")
			  .append("   e.IsoName ,")
			  .append("   e.ProgramManager ,")
			  .append("   e.Email,")
			  .append("   e.Country,")
			  .append("   e.State,")
			  .append("   e.Status,")
			  .append("   e.CreatedDate,")
			  .append("   e.Id,")
			  .append("   e.Currency,e.PHONE")
			  .append(" FROM ")
			  .append("   (SELECT d.MerchantCode,")
			  .append("     d.MerchantName,")
			  .append("     d.EntityType,")
			  .append("     d.IsoName ,")
			  .append("     group_concat(d.ProgramManager) AS ProgramManager ,")
			  .append("     d.Email,")
			  .append("     d.Country,")
			  .append("     d.State,")
			  .append("     d.Status,")
			  .append("     d.CreatedDate,")
			  .append("     d.Id,")
			  .append("     d.Currency,d.PHONE")
			  .append("   FROM")
			  .append("     (SELECT c.MerchantCode,")
			  .append("       c.MerchantName,")
			  .append("       c.EntityType,")
			  .append("       group_concat( c.IsoName ) IsoName ,")
			  .append("       c.ProgramManager ,")
			  .append("       c.Email,")
			  .append("       c.Country,")
			  .append("       c.State,")
			  .append("       c.Status,")
			  .append("       c.CreatedDate,")
			  .append("       c.Id,")
			  .append("       c.Currency,c.PHONE")
			  .append("     FROM ")
			  .append("       ( SELECT DISTINCT b.MerchantCode,")
			  .append("         b.MerchantName,")
			  .append("         b.EntityType,")
			  .append("         b.IsoName ,")
			  .append("         b.ProgramManager ,")
			  .append("         b.Email,")
			  .append("         b.Country,")
			  .append("         b.State,")
			  .append("         b.Status,")
			  .append("         b.CreatedDate,")
			  .append("         b.Id,")
			  .append("         b.Currency,b.PHONE")
			  .append("       FROM")
			  .append("         (SELECT a.MerchantCode,")
			  .append("           a.MerchantName,")
			  .append("           a.EntityType,")
			  .append("           CASE")
			  .append("             WHEN a.EntityType='ISO'")
			  .append("             THEN a.IsoName")
			   .append("    END AS IsoName ,")
			  .append("           CASE")
			  .append("             WHEN a.EntityType='Program Manager'")
			  .append("             THEN a.IsoName")
			  .append("           END AS ProgramManager,")
			  .append("           a.Email,")
			  .append("           a.Country,")
			  .append("           a.State,")
			  .append("           a.Status,")
			  .append("           a.CreatedDate,")
			  .append("           a.Id,")
			   .append("          a.Currency,a.PHONE")
			  .append("         FROM")
			  .append("           (SELECT pgm.MERCHANT_CODE AS MerchantCode,")
			  .append("             pgm.BUSINESS_NAME       AS MerchantName,")
			  .append("             pgme.ENTITY_TYPE        AS EntityType,")
			  .append("             pgiso.ISO_NAME          AS IsoName,")
			  .append("             pgm.EMAIL               AS Email,")
			  .append("             pgm.COUNTRY Country,")
			  .append("             pgm.STATE State,")
			  .append("             pgm.STATUS         AS Status,")
			  .append("             pgm.CREATED_DATE   AS CreatedDate,")
			  .append("             pgm.ID             AS Id,")
			  .append("             pgm.LOCAL_CURRENCY AS Currency,pgm.PHONE as PHONE")
			  .append("           FROM PG_MERCHANT pgm")
			  .append("           JOIN PG_MERCHANT_ENTITY_MAPPING pgme")
			  .append("           ON pgme.MERCHANT_ID=pgm.id")
			  .append("           JOIN PG_ISO pgiso")
			  .append("           ON pgiso.id=pgme.ENTITY_ID")
			  .append("           UNION ALL")
			  .append("           SELECT pgm.MERCHANT_CODE AS MerchantCode,")
			  .append("             pgm.BUSINESS_NAME      AS MerchantName,")
			  .append("             pgme.ENTITY_TYPE       AS EntityType,")
			  .append("             pgiso.PROGRAM_MANAGER_NAME ProgramManager,")
			  .append("             pgm.EMAIL              AS Email,")	  
			  .append("             pgm.COUNTRY Country,")
			  .append("             pgm.STATE State,")
			  .append("             pgm.STATUS         AS Status,")
			  .append("             pgm.CREATED_DATE   AS CreatedDate,")
			  .append("             pgm.ID             AS Id,")
			  .append("             pgm.LOCAL_CURRENCY AS Currency,pgm.PHONE as PHONE")
			  .append("           FROM PG_MERCHANT pgm")		  
			  .append("           JOIN PG_MERCHANT_ENTITY_MAPPING pgme")
			  .append("           ON pgme.MERCHANT_ID=pgm.id")
			  .append("           JOIN PG_PROGRAM_MANAGER pgiso")
			  .append("           ON pgiso.id=pgme.ENTITY_ID")
			  .append("           )a")
			  .append("         )b")
			  .append("       )c")
			  .append("    GROUP BY c.MerchantCode,")
			  .append("      c.MerchantName,")
			  .append("      c.EntityType,")
			  .append("      c.ProgramManager ,")
			  .append("      c.Email,")
			  .append("      c.Country,")
			  .append("      c.State,")
			  .append("      c.Status,")
			  .append("      c.CreatedDate,")
			  .append("      c.Id,")
			  .append("      c.Currency,c.PHONE")
			  .append("    )d")
			    .append(" GROUP BY d.MerchantCode,")
			  .append("    d.MerchantName,")
			  .append("    d.EntityType,")
			  .append("    d.IsoName ,")
			  .append("    d.Email,")
			  .append("    d.Country,")
			  .append("    d.State,")
			  .append("    d.Status,")
			  .append("    d.CreatedDate,")
			  .append("    d.Id,")
			  .append("    d.Currency,d.PHONE")
			  .append("  )e")
						.append(" where (:entityType is null or e.EntityType=:entityType)")
						.append(" and (:programManagerName is null or e.ProgramManager=:programManagerName)")
						.append(" and (:isoName is null or e.IsoName=:isoName)")
						.append(" and (:merchantCode is null or e.MerchantCode=:merchantCode)")
						.append(" and (:businessName is null or e.MerchantName like CONCAT('%',:businessName,'%'))")
						.append(" and (:emailId is null or e.Email=:emailId)")
						.append(" and (:country is null or e.Country=:country)")
						.append(" and (:status is null or e.Status=:status)")
						.append(" ORDER BY e.CreatedDate DESC");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("entityType", getMerchantlistEntityType(searchMerchant));
		qry.setParameter("programManagerName", getMerchantlistProgramManagerName(searchMerchant));
		qry.setParameter("isoName", getMerchantlistIsoName(searchMerchant));
		qry.setParameter("merchantCode", getMerchantlistMerchantCode(searchMerchant));
		qry.setParameter("businessName", getMerchantlistBusinessName(searchMerchant));
		qry.setParameter("emailId", getMerchantlistEmailId(searchMerchant));
		qry.setParameter("country", getMerchantlistCountry(searchMerchant));
		qry.setParameter("status", getMerchantlistStatus(searchMerchant));
		List<Object> cardProgramResponse = qry.getResultList();
    return (StringUtils.isListNotNullNEmpty(cardProgramResponse) ? cardProgramResponse.size() : 0);
  }

  @Override
  public int getTotalNumberOfRecords(GetMerchantListRequest searchMerchant) {
    JPAQuery query = new JPAQuery(entityManager);
    List<Long> list = query.from(QPGMerchant.pGMerchant)
        .where(isBusinessNameLike(searchMerchant.getBusinessName()),
            isCityLike(searchMerchant.getCity()), isCountryEq(searchMerchant.getCountry()),
            isEmailLike(searchMerchant.getEmailId()),
            isFirstNameLike(searchMerchant.getFirstName()),
            isLastNameLike(searchMerchant.getLastName()), isPhoneEq(searchMerchant.getPhone()),
            isMerchantCodeEq(searchMerchant.getMerchantCode()),
            isStatusEqSelfRegistered(searchMerchant.getStatus()))
        .list(QPGMerchant.pGMerchant.id);
    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private void deleteMerchantUsersAndAccount(PGMerchant merchant) {
    List<PGAccount> pgMerchantAccounts = accountRepository
        .findByEntityIdAndStatusNotLike(merchant.getMerchantCode(), PGConstants.S_STATUS_DELETED);
    for (PGAccount account : pgMerchantAccounts) {
      account.setStatus(PGConstants.S_STATUS_DELETED);
      account.setUpdatedDate(DateUtil.getCurrentTimestamp());
      accountRepository.save(account);
    }
    // Delete merchant users associated
    List<PGMerchantUsers> merchantUsers = merchant.getPgMerchantUsers();
    for (PGMerchantUsers user : merchantUsers) {
      user.setStatus(PGConstants.STATUS_DELETED);
      user.setUpdatedDate(DateUtil.getCurrentTimestamp());
      merchantUserRepository.save(user);
    }
    // Delete the merchant
    merchant.setUpdatedDate(DateUtil.getCurrentTimestamp());
    merchant.setStatus(PGConstants.STATUS_DELETED);
    merchantRepository.save(merchant);
  }

  @Override
  public DeleteMerchantResponse deleteMerchant(DeleteMerchantRequest deleteMerchantRequest) {
    DeleteMerchantResponse deleteMerchantResponse = new DeleteMerchantResponse();
    List<PGMerchant> merchantDb = merchantRepository.findByMerchantCodeAndStatus(
        deleteMerchantRequest.getMerchantCode(), PGConstants.STATUS_SUCCESS);
    if (StringUtil.isListNotNullNEmpty(merchantDb)) {
      merchantDb.get(0).setStatus(PGConstants.STATUS_DELETED);
      merchantDb.get(0).setUpdatedDate(DateUtil.getCurrentTimestamp());
      merchantRepository.save(merchantDb);
      deleteMerchantResponse.setIsdeleated(true);
      deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      deleteMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    } else {
      deleteMerchantResponse.setIsdeleated(false);
      deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      deleteMerchantResponse.setErrorMessage(PGConstants.MERCHANT_DETAIL_DELETE_ERROR);
    }
    return deleteMerchantResponse;
  }

  @Override
  public List<PGState> getStateById(String countryId) {
    return stateRepository.findById(Long.valueOf(countryId));
  }

  @Override
  public List<PGState> getStateByCountryId(Long countryId) {
    return stateRepository.findByCountryId(countryId);
  }

  @Override
  public PGMerchant getMerchantByEmailId(String email) {
    return merchantRepository.findByEmailId(email);
  }

  @Override
  public Response getUserByEmailId(String email) {
    String emailId = email.toLowerCase();
    PGMerchant merchant =
        merchantRepository.findByEmailIdAndStatusNotLike(emailId, PGConstants.STATUS_DELETED);
    Response response = new Response();
    if (merchant != null) {
      return duplicateUserEntryResponse(response);
    }
    PGMerchantUsers merchantUsers =
        merchantUserRepository.findByEmailIdAndStatusNotLike(emailId, PGConstants.STATUS_DELETED);
    if (merchantUsers != null) {
      return duplicateUserEntryResponse(response);
    } else {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return response;
    }
  }

  @Override
  public PGAccount getPgAccount(String merchantCode) {
    return accountRepository.findByEntityIdAndCategory(merchantCode, PGConstants.PRIMARY_ACCOUNT);
  }

  public AddMerchantBankResponse addMerchantBank(AddMerchantBankRequest addMerchantBankRequest) {
    logger.info("MerchantDaoImpl | addmerchantBank | Entering");
    AddMerchantBankResponse addMerchantBankResponse = new AddMerchantBankResponse();
    try { // check for valid merchant Response
      PGMerchant pgMerchant = getMerchant(addMerchantBankRequest.getMerchant_code());
      if (pgMerchant == null) {
        addMerchantBankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        addMerchantBankResponse.setErrorMessage(PGConstants.INVALID_MERCHANT_ID);
      } else {
        PGMerchantBank merchantBank = new PGMerchantBank();
        merchantBank.setAccountType(addMerchantBankRequest.getAccount_type());
        merchantBank.setBankAccNum(addMerchantBankRequest.getBank_acc_number());
        merchantBank.setBankCode(addMerchantBankRequest.getBank_code());
        merchantBank.setBankName(addMerchantBankRequest.getBank_name());
        merchantBank.setCreatedDate(DateUtil.getCurrentTimestamp());
        merchantBank.setCurrencyCode(addMerchantBankRequest.getCurrency_code());
        merchantBank.setMerchantId(pgMerchant.getId().toString());
        merchantBank.setRoutingNumber(addMerchantBankRequest.getRouting_num());
        merchantBank.setStatus(PGConstants.STATUS_SUCCESS);
        merchantBank = bankRepository.save(merchantBank);
        if (merchantBank != null) {
          logger.info("MerchantDaoImpl | addmerchantBank | Merchant details inserted successfully");
          addMerchantBankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
          addMerchantBankResponse.setErrorMessage(
              ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        } else {
          addMerchantBankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
          addMerchantBankResponse.setErrorMessage(PGConstants.MERCHANT_BANK_CREATION_ERROR);
        }
      }
    } catch (Exception e) {
      logger.error("MerchantDaoImpl | addmerchantBank | Exception" + e);
      addMerchantBankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      addMerchantBankResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("MerchantDaoImpl | addmerchantBank | Exiting");
    return addMerchantBankResponse;
  }

  @Override
  public List<PGMerchant> getAllMerchants() {
    return merchantRepository.findAll();
  }

  @Override
  public List<AccountBalanceReportDTO> getAllAccountsBalanceReport(Merchant merchant) {
    Integer pageIndex = merchant.getPageIndex();
    Integer pageSize = merchant.getPageSize();
    Integer totalRecords;
    int offset = 0;
    int limit = 0;
    if (pageIndex == null || pageIndex == 1) {
      totalRecords = getTotalNumberOfBalanceRecords();
      merchant.setNoOfRecords(totalRecords);
    }
    if (pageIndex == null && pageSize == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (pageIndex - 1) * pageSize;
      limit = pageSize;
    }
    List<AccountBalanceReportDTO> reportList = null;
    AccountBalanceReportDTO accountBalanceReport = null;
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> infoList = query.from(QPGMerchant.pGMerchant, QPGAccount.pGAccount)
        .where(QPGMerchant.pGMerchant.merchantCode.eq(QPGAccount.pGAccount.entityId)).offset(offset)
        .limit(limit).orderBy(orderByCreatedDateDesc()).list(QPGMerchant.pGMerchant.userName,
            QPGMerchant.pGMerchant.businessName, QPGAccount.pGAccount.createdDate,
            QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
            QPGMerchant.pGMerchant.localCurrency, QPGAccount.pGAccount.availableBalance,
            QPGAccount.pGAccount.currentBalance, QPGAccount.pGAccount.status);
    if (StringUtil.isListNotNullNEmpty(infoList)) {
      reportList = new ArrayList<>();
      for (Tuple tuple : infoList) {
        accountBalanceReport = new AccountBalanceReportDTO();
        accountBalanceReport.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
        accountBalanceReport.setBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
        accountBalanceReport.setAccCreationDate(DateUtil.toDateStringFormat(
            tuple.get(QPGAccount.pGAccount.createdDate), PGConstants.DATE_FORMAT));
        accountBalanceReport.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
        accountBalanceReport.setAccountType(tuple.get(QPGAccount.pGAccount.entityType));
        accountBalanceReport.setCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
        accountBalanceReport.setAvailableBalance(
            StringUtils.amountToString(tuple.get(QPGAccount.pGAccount.availableBalance)));
        accountBalanceReport.setCurrentBalance(
            StringUtils.amountToString(tuple.get(QPGAccount.pGAccount.currentBalance)));
        accountBalanceReport.setStatus(tuple.get(QPGAccount.pGAccount.status));
        reportList.add(accountBalanceReport);
      }
    }
    return reportList;
  }

  @Override
  public List<ReportsDTO> getAccountHistoryList() {
    List<ReportsDTO> reportList = null;
    ReportsDTO historyReport = null;
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> infoList = query
        .from(QPGAccountHistory.pGAccountHistory, QPGMerchant.pGMerchant,
            QPGTransaction.pGTransaction)
        .where(QPGMerchant.pGMerchant.merchantCode.eq(QPGAccountHistory.pGAccountHistory.entityId)
            .and(QPGAccountHistory.pGAccountHistory.transactionId
                .eq(QPGTransaction.pGTransaction.transactionId)))
        .orderBy(orderByAccountHistoryUpdatedDateDesc())
        .list(QPGAccountHistory.pGAccountHistory.accountNum,
            QPGAccountHistory.pGAccountHistory.currency,
            QPGAccountHistory.pGAccountHistory.transactionId,
            QPGAccountHistory.pGAccountHistory.entityId, QPGMerchant.pGMerchant.userName,
            QPGMerchant.pGMerchant.businessName, QPGAccountHistory.pGAccountHistory.feeBalance);
    if (StringUtil.isListNotNullNEmpty(infoList)) {
      reportList = new ArrayList<>();
      for (Tuple tuple : infoList) {
        historyReport = new ReportsDTO();
        historyReport.setAccountNumber(tuple.get(QPGAccountHistory.pGAccountHistory.accountNum));
        historyReport.setCurrency(tuple.get(QPGAccountHistory.pGAccountHistory.currency));
        historyReport.setCompanyName(tuple.get(QPGMerchant.pGMerchant.businessName));
        historyReport.setDateTime(
            DateUtil.toDateStringFormat(tuple.get(QPGAccountHistory.pGAccountHistory.updatedDate),
                DateUtil.VIEW_DATE_TIME_FORMAT));
        historyReport.setDescription(tuple.get(QPGAccountHistory.pGAccountHistory.accountDesc));
        historyReport.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
        historyReport.setTransactionId(tuple.get(QPGAccountHistory.pGAccountHistory.transactionId));
        historyReport.setAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)));
        historyReport.setPaymentMethod((tuple.get(QPGTransaction.pGTransaction.paymentMethod)));
        reportList.add(historyReport);
      }
    }
    return reportList;
  }
}
