/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.model.Iso;
import com.chatak.pg.acq.dao.model.IsoAccount;
import com.chatak.pg.acq.dao.model.IsoPmMap;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.QCardProgram;
import com.chatak.pg.acq.dao.model.QIso;
import com.chatak.pg.acq.dao.model.QIsoCardProgramMap;
import com.chatak.pg.acq.dao.model.QIsoPmMap;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantEntityMap;
import com.chatak.pg.acq.dao.model.QPanRanges;
import com.chatak.pg.acq.dao.model.QProgramManager;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.IsoAccountRepository;
import com.chatak.pg.acq.dao.repository.IsoCardProgramMapRepository;
import com.chatak.pg.acq.dao.repository.IsoPmMapRepository;
import com.chatak.pg.acq.dao.repository.IsoRepository;
import com.chatak.pg.acq.dao.repository.ProgramManagerRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.IsoResponse;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.PanRangeRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
/**
 * @Author: Girmiti Software
 * @Date: May 7, 2018
 * @Time: 7:24:33 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("isoServiceDao")
public class IsoServiceDaoImpl implements IsoServiceDao {
	
	private static Logger logger = Logger.getLogger(IsoServiceDaoImpl.class);

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private IsoRepository isoRepository;
	
	@Autowired
	private IsoAccountRepository isoAccountRepository;
	
	@Autowired
	private IsoPmMapRepository isoPmMapRepository;
	
	@Autowired
	private IsoCardProgramMapRepository isoCardProgramMapRepository;

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	private ProgramManagerRepository programManagerRepository;
	/**
	 * @param id
	 * @return
	 */
	@Override
	public CardProgramResponse fetchCardProgramByPm(Long id) {
		CardProgramResponse response = new CardProgramResponse();
		Long isoId=null;
		
		StringBuilder query = new StringBuilder("SELECT cp.Id, cp.CURRENCY, pm.PAN_LOW, pm.PAN_HIGH,")
								.append(" pm.PROGRAM_MANAGER_NAME FROM PG_PM_CARD_PROGRAM_MAPPING  as pmcpmap ")
								.append(" left join PG_CARD_PROGRAM as cp on pmcpmap.CARD_PROGRAM_ID = cp.ID")
								.append(" left join PG_PROGRAM_MANAGER as pm on pm.ID = pmcpmap.PM_ID")
								.append(" where pmcpmap.PM_ID = :pmId");
		
		
		Query qry = entityManager.createNativeQuery(query.toString());
					qry.setParameter("pmId", id);
		List<Object> cardProgramResponse = qry.getResultList();
		Iterator<Object> itr = cardProgramResponse.iterator();
		List<CardProgramRequest> cardProgramList = new ArrayList<>();
		setCardPrograms(cardProgramList, itr, id,isoId);
		response.setCardProgramList(cardProgramList);
		return response;
	}

	private void setCardPrograms(List<CardProgramRequest> cardProgramList,Iterator<Object> itr,Long pmId,Long isoId) {
		CardProgramRequest cardProgramRequest;
		while(itr.hasNext()){
			Object[] objs = (Object[]) itr.next();
			cardProgramRequest= new CardProgramRequest();
			cardProgramRequest.setCardProgramId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
			cardProgramRequest.setCurrency(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
			cardProgramRequest.setPanLow(StringUtil.isNull(objs[2]) ? null : ((String) objs[2]));
			cardProgramRequest.setPanHigh(StringUtil.isNull(objs[3]) ? null : ((String) objs[3]));
			cardProgramRequest.setProgramManagerName(StringUtil.isNull(objs[4]) ? null : ((String) objs[4]));
			cardProgramRequest.setProgramManagerId(pmId!=null ? pmId : null);
			cardProgramRequest.setIsoId(isoId !=null ? isoId : null);
			cardProgramList.add(cardProgramRequest);
		}
	}

	/**
	 * @param objs
	 * @return
	 */
	private String requestIinExt(Object[] objs) {
		return StringUtil.isNull(objs[3]) ? null : ((String)objs[3]);
	}

	/**
	 * @param objs
	 * @return
	 */
	private String requestIin(Object[] objs) {
		return StringUtil.isNull(objs[2]) ? null : ((String)objs[2]);
	}

	/**
	 * @param objs
	 * @return
	 */
	private String requestCardProgramName(Object[] objs) {
		return StringUtil.isNull(objs[1]) ? null : (String)objs[1];
	}
	
	/**
	 * @param currencyId
	 * @return
	 */
	@Override
	public IsoResponse getISONameByAccountCurrency(String currencyId) {
		IsoResponse response = new IsoResponse();
		List<IsoRequest> list;
		StringBuilder query = new StringBuilder("SELECT distinct iso.Id,iso.ISO_NAME")
				.append(" FROM PG_ISO as iso")
				.append(" left join PG_PM_ISO_MAPPING as pm_iso_map on ISO_ID=iso.ID")
				.append(" left join PG_PROGRAM_MANAGER as pm on pm.ID=pm_iso_map.PM_ID")
				.append(" where pm.ACCOUNT_CURRENCY = :currencyId");

		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("currencyId", currencyId);
		List<Object> isoList = qry.getResultList();
		Iterator<Object> itr = isoList.iterator();
		list = new ArrayList<>();
		IsoRequest isoRequest;
		while (itr.hasNext()) {
			Object[] objs = (Object[]) itr.next();
			isoRequest = new IsoRequest();
			isoRequest.setId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
			isoRequest.setIsoName(requestCardProgramName(objs));
			list.add(isoRequest);
		}
		response.setIsoRequest(list);
		return response;
	}
	
	@Override
	public CardProgramResponse fetchCardProgramByIso(Long id, String currencyId) {
		CardProgramResponse response = new CardProgramResponse();

		StringBuilder query = new StringBuilder(
				"SELECT cp.Id as cardprogramId,cp.CARD_PROGRAM_NAME,cp.IIN,cp.IIN_EXT,cp.ISSUANCE_PARTNER_NAME,cp.CURRENCY,iso.ISO_NAME,cp.IIN_PARTNER_EXT, iso.ID as isoId")
						.append(" FROM PG_ISO_CARD_PROGRAM_MAPPING as pmcpmap ")
						.append(" left join PG_CARD_PROGRAM as cp on pmcpmap.CARD_PROGRAM_ID = cp.ID ")
						.append(" left join PG_ISO as iso on iso.ID=pmcpmap.ISO_ID ")
						.append(" where pmcpmap.ISO_ID = :pmId and cp.CURRENCY= :currencyId ");

		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("pmId", id);
		qry.setParameter("currencyId", currencyId);
		List<Object> cardProgramResponse = qry.getResultList();
		Iterator<Object> itr = cardProgramResponse.iterator();
		List<CardProgramRequest> cardProgramList = new ArrayList<>();
		CardProgramRequest cardProgramRequest;
		while (itr.hasNext()) {
			Object[] objs = (Object[]) itr.next();
			cardProgramRequest = new CardProgramRequest();
			cardProgramRequest.setCardProgramId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
			cardProgramRequest.setCardProgramName(requestCardProgramName(objs));
			cardProgramRequest.setIin(requestIin(objs));
			cardProgramRequest.setIinExt(StringUtil.isNull(objs[3]) ? null : (objs[3].toString()));
			cardProgramRequest.setPartnerName(StringUtil.isNull(objs[4]) ? null : ((String) objs[4]));
			cardProgramRequest.setCurrency(StringUtil.isNull(objs[5]) ? null : ((String) objs[5]));
			cardProgramRequest.setIsoName(StringUtil.isNull(objs[6]) ? null : ((String) objs[6]));
			cardProgramRequest.setPartnerCode(StringUtil.isNull(objs[7]) ? null : ((String)objs[7]));
			cardProgramRequest.setIsoId(StringUtil.isNull(objs[8]) ? null : ((BigInteger) objs[8]).longValue());
			cardProgramList.add(cardProgramRequest);
		}
		response.setCardProgramList(cardProgramList);
		return response;
	}

		/**
	 * @param iso
	 * @return
	 */
	@Override
	public Iso saveIso(Iso iso) {
		return isoRepository.save(iso);
	}

	/**
	 * @param isoRequest
	 * @return
	 */
	@Override
	public IsoResponse searchIso(IsoRequest isoRequest)
			 {
		List<IsoRequest> isoRequests = new ArrayList<>();
		IsoResponse isoResponse = new IsoResponse();
		int offset = 0;
		int limit = 0;
		Integer totalRecords = isoRequest.getNoOfRecords();

		if (isoRequest.getPageIndex() == null || isoRequest.getPageIndex() == 1) {
			totalRecords = getTotalNumberOfRecords(isoRequest);
			isoRequest.setNoOfRecords(totalRecords);
		}

		if (isoRequest.getPageIndex() == null
				&& isoRequest.getPageSize() == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (isoRequest.getPageIndex() - 1) * isoRequest.getPageSize();
			limit = isoRequest.getPageSize();
		}

		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> tupleList = query
				.from(QIso.iso)
				.where(isIsoNameLike(isoRequest.getIsoName().trim()),
						isBusinessEntityNameLike(isoRequest
								.getProgramManagerRequest().getBusinessName()),
						isStatus(isoRequest.getProgramManagerRequest()
								.getStatus()),
						isIsoId(isoRequest.getId()),
						isIsoIds(isoRequest.getIds()),
						isIsoEmailLike(isoRequest.getProgramManagerRequest().getContactEmail()))
				.offset(offset)
				.limit(limit)
				.orderBy(orderByIdDesc())
				.distinct()
				.list(QIso.iso.id, QIso.iso.isoName,
						QIso.iso.businessEntityName, QIso.iso.address,
						QIso.iso.city, QIso.iso.contactPerson,
						QIso.iso.country, QIso.iso.state,
						QIso.iso.createdDate, QIso.iso.phoneNumber,
						QIso.iso.status, QIso.iso.currency,QIso.iso.email);

		IsoRequest isoDTO = null;
		ProgramManagerRequest programManagerRequest = null;
		for (Tuple tuple : tupleList) {
			isoDTO = new IsoRequest();
			programManagerRequest = new ProgramManagerRequest();
			isoDTO.setId(tuple.get(QIso.iso.id));
			isoDTO.setIsoName(tuple.get(QIso.iso.isoName));
			programManagerRequest.setBusinessName(tuple
					.get(QIso.iso.businessEntityName));
			programManagerRequest.setContactName(tuple
					.get(QIso.iso.contactPerson));
			programManagerRequest.setCreatedDate(tuple
					.get(QIso.iso.createdDate));
			programManagerRequest.setContactPhone(tuple
					.get(QIso.iso.phoneNumber));
			programManagerRequest.setAccountCurrency(tuple.get(QIso.iso.currency));
			programManagerRequest.setStatus(tuple.get(QIso.iso.status));
			isoDTO.setAddress(tuple.get(QIso.iso.address));
			isoDTO.setCity(tuple.get(QIso.iso.city));
			isoDTO.setCountry(tuple.get(QIso.iso.country));
			isoDTO.setState(tuple.get(QIso.iso.state));
			programManagerRequest.setContactEmail(tuple.get(QIso.iso.email));
			isoDTO.setProgramManagerRequest(programManagerRequest);
			isoRequests.add(isoDTO);
		}
		isoResponse.setIsoRequest(isoRequests);
		isoResponse.setTotalNoOfRows(totalRecords);
		return isoResponse;
	}
	
	private BooleanExpression isBusinessEntityNameLike(String businessEntityName) {
	    return (StringUtil.isNullAndEmpty(businessEntityName) ? null
	        : QIso.iso.businessEntityName.toUpperCase()
	            .like("%" + businessEntityName.toUpperCase().replace("*", "") + "%"));
	  }

	  private BooleanExpression isIsoNameLike(String isoName) {
	    return (StringUtil.isNullAndEmpty(isoName) ? null
	        : QIso.iso.isoName.toUpperCase()
	            .like("%" + isoName.toUpperCase().replace("*", "") + "%"));
	  }

	  private BooleanExpression isStatus(String status) {
	    return (status != null && !"".equals(status))
	        ? QIso.iso.status.eq((status)) : null;
	  }
	  
	  private OrderSpecifier<Long> orderByIdDesc() {
		    return QIso.iso.id.desc();
		  }
	  
	  private BooleanExpression isIsoEmailLike(String contactEmail) {
        return (StringUtil.isNullAndEmpty(contactEmail) ? null
            : QIso.iso.email.toUpperCase()
                .like("%" + contactEmail.toUpperCase().replace("*", "") + "%"));
      }

	/**
	 * @param isoAccount
	 * @return
	 * @
	 */
	@Override
	public IsoAccount saveIsoAccount(IsoAccount isoAccount){
			return isoAccountRepository.save(isoAccount);	
	}
	
	@Override
	public Long getAccountNumberSeries(String accountNumber) {
		Long serialNumber = Long.valueOf(accountNumber);

		Query qry = entityManager
				.createNativeQuery("SELECT IFNULL( MAX( ACCOUNT_NUMBER ), :accountNumber ) + 1 FROM PG_ISO_ACCOUNT");
		qry.setParameter("accountNumber", accountNumber);
		List<Double> list = qry.getResultList();
		if (StringUtil.isListNotNullNEmpty(list)) {
			serialNumber = list.get(0).longValue();
		}
		return serialNumber;
	}
	
	@Override
	public List<Iso> findByIsoName(String isoName)  {
		return isoRepository.findByIsoName(isoName);
	}
	
	@Override
	public List<Iso> findByIsoId(Long isoId)  {
		return isoRepository.findById(isoId);
	}
	
	@Override
	public IsoResponse getIsoById(IsoRequest isoRequest)  {
		IsoResponse isoResponse = new IsoResponse();
		List<IsoRequest> isoRequests = new ArrayList<>();
		
		List<Iso>  isoList = findByIsoId(isoRequest.getId());
		IsoRequest isoDTO = null;
		ProgramManagerRequest programManagerRequest = null;
		if(isoList!=null){
			for(Iso iso: isoList){
				isoDTO = new IsoRequest();
				programManagerRequest = new ProgramManagerRequest();
				isoDTO.setId(iso.getId());
				isoDTO.setIsoName(iso.getIsoName());
				programManagerRequest.setBusinessName(iso.getBusinessEntityName());
				programManagerRequest.setContactName(iso.getContactPerson());
				programManagerRequest.setCreatedDate(iso.getCreatedDate());
				programManagerRequest.setContactPhone(iso.getPhoneNumber());
				programManagerRequest.setStatus(iso.getStatus());
				programManagerRequest.setAccountCurrency(iso.getCurrency());
				programManagerRequest.setContactEmail(iso.getEmail());
				programManagerRequest.setProgramManagerLogo(iso.getIsoLogo());
				isoDTO.setAddress(iso.getAddress());
				isoDTO.setCity(iso.getCity());
				isoDTO.setCountry(iso.getCountry());
				isoDTO.setState(iso.getState());
				isoDTO.setZipCode(iso.getZipCode());
				isoDTO.setProgramManagerRequest(programManagerRequest);
				isoDTO.setBankName(iso.getBankName());
				isoDTO.setBankAccNum(iso.getBankAccNum());
				isoDTO.setRoutingNumber(iso.getRoutingNumber());
				JPAQuery querys = new JPAQuery(entityManager);
			      List<Tuple> panRanges = querys.from(QPanRanges.panRanges)
			          .where(QPanRanges.panRanges.IsoId.eq(isoRequest.getId()))
			          .list(QPanRanges.panRanges.panLow,QPanRanges.panRanges.panHigh);
			      
			      List<PanRangeRequest> panRange = new ArrayList<>(0);
			      PanRangeRequest range;
			      for(Tuple ranges: panRanges){
			    	  range = new PanRangeRequest();
			    	  range.setPanLow(ranges.get(QPanRanges.panRanges.panLow));
			    	  range.setPanHigh(ranges.get(QPanRanges.panRanges.panHigh));
			    	  panRange.add(range);
			      }
			      isoDTO.setPanRangeList(panRange);
				isoRequests.add(isoDTO);
			}			
		}
		
		isoResponse.setIsoRequest(isoRequests);
		
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> tupleList = query
				.from(QProgramManager.programManager,QIsoPmMap.isoPmMap)
				.where(QIsoPmMap.isoPmMap.isoId.eq(isoRequest.getId()).and(QIsoPmMap.isoPmMap.pmId.eq(QProgramManager.programManager.id)))
				.distinct()
				.list(QProgramManager.programManager.id,QProgramManager.programManager.programManagerName);
		
		List<ProgramManagerRequest> programManagerRequests = new ArrayList<>();
		ProgramManagerRequest programManager = null;
		for (Tuple tuple : tupleList){
			programManager = new ProgramManagerRequest();
			programManager.setId(tuple.get(QProgramManager.programManager.id));
			programManager.setProgramManagerName(tuple.get(QProgramManager.programManager.programManagerName));
			programManagerRequests.add(programManager);
		}
		isoResponse.setProgramManagerRequestList(programManagerRequests);
		return isoResponse;
	}

	/**
	 * @param iso
	 * @return
	 * @
	 */
	@Override
	public Iso updateIso(Iso iso)  {
		return isoRepository.save(iso);
	}

	/**
	 * @param isoId
	 * @
	 */
	@Override
	public void deleteIsoPmMappingByIsoId(Long isoId)  {
		isoPmMapRepository.deleteByIsoId(isoId);		
	}

	/**
	 * @param isoId
	 * @
	 */
	@Override
	public void deleteIsoCardProgramMappingByIsoId(Long isoId) {
		isoCardProgramMapRepository.deleteByIsoId(isoId);
	}
	@Override
	public List<CardProgramRequest> fetchCardProgramByIso(Long isoId) {
		List<CardProgramRequest> allCardProgramList = new ArrayList<>(0);
		List<CardProgramRequest> selectedCardProgramList = new ArrayList<>(0);

		StringBuilder allCardProgramQuery = new StringBuilder("select subqry.ID,subqry.PAN_LOW,subqry.PAN_HIGH,")
        .append(" subqry.CURRENCY, subqry.PROGRAM_MANAGER_NAME,  subqry.PM_ID")
        .append(" from (select distinct cp.ID,pm.PAN_LOW,pm.PAN_HIGH,")
        .append(" cp.CURRENCY,pm.PROGRAM_MANAGER_NAME,pmcp.PM_ID")
        .append(" from PG_PM_ISO_MAPPING as pmiso")
        .append(" left join PG_PM_CARD_PROGRAM_MAPPING as pmcp on pmiso.PM_ID =pmcp.PM_ID")
        .append(" right join PG_PROGRAM_MANAGER as pm on pmiso.PM_ID = pm.ID")
        .append(" left join PG_CARD_PROGRAM as cp on pmcp.CARD_PROGRAM_ID = cp.ID")
        .append(" where pmiso.ISO_ID = :isoId )as subqry");
							  
		Query allCardProgramQry = entityManager.createNativeQuery(allCardProgramQuery.toString());
		allCardProgramQry.setParameter("isoId", isoId);
		List<Object> cardProgramResponse = allCardProgramQry.getResultList();
		if(StringUtil.isListNotNullNEmpty(cardProgramResponse)){
			Iterator<Object> itr = cardProgramResponse.iterator();
			setCardPrograms(allCardProgramList, itr,isoId);			
		}
		
		//selected cp's
		StringBuilder selectedCardProgramQuery = new StringBuilder("select subqry.ID,subqry.PAN_LOW,subqry.PAN_HIGH,")
        .append(" subqry.CURRENCY,subqry.PROGRAM_MANAGER_NAME,subqry.AMBIGUITY_PM_ID")
        .append(" from (select distinct cp.ID,cp.CURRENCY,pm.PAN_LOW,")
        .append(" pm.PAN_HIGH,pm.PROGRAM_MANAGER_NAME,pmiso.AMBIGUITY_PM_ID")
        .append(" from PG_ISO_CARD_PROGRAM_MAPPING as pmiso")
        .append(" right join PG_PROGRAM_MANAGER as pm on pmiso.AMBIGUITY_PM_ID = pm.ID")
        .append(" left join PG_CARD_PROGRAM as cp on pmiso.CARD_PROGRAM_ID = cp.ID")
        .append(" where pmiso.ISO_ID = :isoId )as subqry");
		
		Query selectedCardProgramQry = entityManager.createNativeQuery(selectedCardProgramQuery.toString());
		selectedCardProgramQry.setParameter("isoId", isoId);
        List<Object> cardProgramResponse1 = selectedCardProgramQry.getResultList();
        if(StringUtil.isListNotNullNEmpty(cardProgramResponse1)){
            Iterator<Object> itr = cardProgramResponse1.iterator();
            setCardPrograms(selectedCardProgramList, itr,isoId);            
        }
        
        Map<String, CardProgramRequest> masterCpMap = new HashMap<>();
        for(CardProgramRequest masterCp : allCardProgramList){
          masterCpMap.put(getKey(masterCp.getProgramManagerId(),masterCp), masterCp);
        }
        for(CardProgramRequest selectedCp : selectedCardProgramList){
          if(masterCpMap.containsKey(getKey(selectedCp.getProgramManagerId(),selectedCp))){
            CardProgramRequest cardProgram = masterCpMap.get(getKey(selectedCp.getProgramManagerId(),selectedCp));
            if(selectedCp.getCardProgramId().equals(cardProgram.getCardProgramId()) 
                // Compare the ambiguity ID
                && (selectedCp.getProgramManagerId().equals(cardProgram.getProgramManagerId()))) {
                // Set the card program as selected in the master card list
                cardProgram.setSelected(true);
            }
          }
        }
        allCardProgramList = new ArrayList<>(masterCpMap.values());
		return allCardProgramList;
	}
	
	private String getKey(Long entityId, CardProgramRequest cardProgramRequest){
	  return entityId+"_"+cardProgramRequest.getCardProgramId();
	}
	
	private void setCardPrograms(List<CardProgramRequest> cardProgramList,Iterator<Object> itr,Long isoId) {
      CardProgramRequest cardProgramRequest;
      while(itr.hasNext()){
          Object[] objs = (Object[]) itr.next();
          cardProgramRequest= new CardProgramRequest();
          cardProgramRequest.setCardProgramId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
          cardProgramRequest.setPanLow(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
          cardProgramRequest.setPanHigh(StringUtil.isNull(objs[2]) ? null : ((String) objs[2]));
          cardProgramRequest.setCurrency(StringUtil.isNull(objs[3]) ? null : ((String) objs[3]));
          cardProgramRequest.setProgramManagerName(StringUtil.isNull(objs[4]) ? null : ((String) objs[4]));
          cardProgramRequest.setProgramManagerId(StringUtil.isNull(objs[5]) ? null : ((BigInteger) objs[5]).longValue());
          cardProgramRequest.setIsoId(isoId !=null ? isoId : null);
          cardProgramList.add(cardProgramRequest);
      }
  }

	/**
	 * @param isoId
	 * @param currency
	 * @return
	 */
	@Override
	public IsoResponse fetchProgramManagerByIsoCurrency(Long isoId,
			String currency) {
		IsoResponse isoResponse = new IsoResponse();
		StringBuilder query = new StringBuilder("select subqry.ID, subqry.PROGRAM_MANAGER_NAME")
							.append(" from (select distinct pm.ID,pm.PROGRAM_MANAGER_NAME ")
							.append(" from PG_PROGRAM_MANAGER as pm  where pm.ACCOUNT_CURRENCY = :currency )as subqry")
							.append(" where subqry.ID not in (select pmiso.PM_ID from PG_PM_ISO_MAPPING as pmiso")
							.append(" where pmiso.PM_ID = subqry.ID and pmiso.ISO_ID= :isoId)");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("isoId", isoId);
		qry.setParameter("currency", currency);
		List<Object> programManagerResponse = qry.getResultList();	
		
		List<ProgramManagerRequest> programManagerRequests = new ArrayList<>(0);
		ProgramManagerRequest programManager = null;
		if(StringUtil.isListNotNullNEmpty(programManagerResponse)){
			Iterator<Object> itr = programManagerResponse.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				programManager = new ProgramManagerRequest();
				programManager.setId(((BigInteger)object[0]).longValue());
				programManager.setProgramManagerName(object[1].toString());
				programManagerRequests.add(programManager);
			}			
		}
		isoResponse.setProgramManagerRequestList(programManagerRequests);
		return isoResponse;
	}

	/**
	 * @param isoRequest
	 * @return
	 */
	@Override
	public List<IsoRequest> getAllIso(IsoRequest isoRequest) {
		logger.info("Entering :: IsoServiceDaoImpl :: getAllIso");
		List<IsoRequest> isoRequests = new ArrayList<>();
	    JPAQuery query = new JPAQuery(entityManager);
	    List<Iso> isos = query.from(QIso.iso)
	        .where(isStatus(isoRequest.getProgramManagerRequest().getStatus()), isIsoId(isoRequest.getId()))
	        .orderBy(QIso.iso.isoName.asc())
	        .list(QIso.iso);
	    if (StringUtil.isListNotNullNEmpty(isos)) {
	    	  try {
				isoRequests =
				    CommonUtil.copyListBeanProperty(isos, IsoRequest.class);
			} catch (InstantiationException e) {
				logger.error("Error :: IsoServiceDaoImpl :: getAllIso :: InstantiationException :: " + e.getMessage(), e);
			} catch (IllegalAccessException e) {
			    logger.error("Error :: IsoServiceDaoImpl :: getAllIso :: IllegalAccessException :: " + e.getMessage(), e);
			}
	    }
	    logger.info("Exiting :: IsoServiceDaoImpl :: getAllIso");
	    return isoRequests;
	  }
	
	private BooleanExpression isIsoId(Long isoId) {
	    return (StringUtil.isNull(isoId) ? null
	        : QIso.iso.id.eq(isoId));
	  }

	/**
	 * @param pmId
	 * @return
	 */
	@Override
	public List<IsoPmMap> findByPmId(Long pmId) {
		return isoPmMapRepository.findByPmId(pmId);
	}
	
	private BooleanExpression isIsoIds(List<Long> isoIds) {
	    return (StringUtil.isListNullNEmpty(isoIds) ? null
	        : QIso.iso.id.in(isoIds));
	  }
	
	/**
	 * @param id
	 * @param currencyId
	 * @return
	 */
	@Override
	public MerchantResponse getIsoNameByCurrencyAndId(Long id, String currencyId) {
		MerchantResponse response = new MerchantResponse();
		List<IsoRequest> list;
		StringBuilder query = new StringBuilder("select subqry.ID,subqry.ISO_NAME")
				.append(" from (select distinct iso.Id,iso.ISO_NAME ").append(" from PG_ISO as iso")
				.append(" where iso.CURRENCY = :currencyId )as subqry")
				.append(" where subqry.ID not in (select entity.ENTITY_ID from PG_MERCHANT_ENTITY_MAPPING as entity")
				.append(" where entity.ENTITY_ID = subqry.ID and entity.MERCHANT_ID = :merchantId)");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("merchantId", id);
		qry.setParameter("currencyId", currencyId);
		List<Object> isObjects = qry.getResultList();
		list = new ArrayList<>();
		IsoRequest requests;
		if (StringUtil.isListNotNullNEmpty(isObjects)) {
			Iterator<Object> itr = isObjects.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				requests = new IsoRequest();
				requests.setId(((BigInteger) object[0]).longValue());
				requests.setIsoName(object[1].toString());
				list.add(requests);
			}
			response.setIsoRequests(list);
		}
		return response;
	}
	
	@Override
	public IsoResponse getIsoNameByProgramManagerId(Long pmId) {
		IsoResponse isoResponse = new IsoResponse();
		List<IsoRequest> list;
		StringBuilder query = new StringBuilder(" SELECT  pi.ID, pi.ISO_NAME ").append(" FROM PG_ISO pi ")
				.append(" join PG_PM_ISO_MAPPING pm on pi.ID=pm.ISO_ID ").append(" where pm.PM_ID=:pmId ");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("pmId", pmId);
		List<Object> isObjects = qry.getResultList();
		list = new ArrayList<>();
		IsoRequest isoRequest;
		if (StringUtil.isListNotNullNEmpty(isObjects)) {
			Iterator<Object> itr = isObjects.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				isoRequest = new IsoRequest();
				isoRequest.setId(((BigInteger) object[0]).longValue());
				isoRequest.setIsoName(object[1].toString());
				list.add(isoRequest);
			}
			isoResponse.setIsoRequest(list);
		}
		return isoResponse;
	}
	
	private Integer getTotalNumberOfRecords(IsoRequest isoRequest) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> iso = query.from(QIso.iso)
				.where(isIsoNameLike(isoRequest.getIsoName().trim()),
						isBusinessEntityNameLike(isoRequest.getProgramManagerRequest().getBusinessName()),
						isStatus(isoRequest.getProgramManagerRequest().getStatus()), isIsoId(isoRequest.getId()),
						isIsoIds(isoRequest.getIds()), isIsoEmailLike(isoRequest.getProgramManagerRequest().getContactEmail()))
				.list(QIso.iso.id);
		return (StringUtil.isListNotNullNEmpty(iso) ? iso.size() : 0);
	}
	/**
	 * @param merchantId
	 * @return
	 */
	@Override
	public MerchantResponse findCardProgramByMerchantId(Long merchantId) {
		MerchantResponse response = new MerchantResponse();
		List<CardProgramRequest> allCardProgramList = new ArrayList<>(0);
        List<CardProgramRequest> selectedCardProgramList = new ArrayList<>(0);

        StringBuilder query = new StringBuilder("select subqry.ID,subqry.CARD_PROGRAM_NAME,subqry.IIN,")
        .append(" subqry.IIN_EXT,subqry.ISSUANCE_PARTNER_NAME, subqry.CURRENCY,")
        .append(" subqry.ISO_NAME,subqry.IIN_PARTNER_EXT, subqry.ISO_ID ")
        .append(" from (select distinct cp.ID,cp.CARD_PROGRAM_NAME,cp.IIN,")
        .append(" cp.IIN_EXT,cp.ISSUANCE_PARTNER_NAME,cp.CURRENCY,iso.ISO_NAME,cp.IIN_PARTNER_EXT,isocp.ISO_ID")
        .append(" from PG_MERCHANT_ENTITY_MAPPING as merchant_entity")
        .append(" left join PG_ISO_CARD_PROGRAM_MAPPING as isocp on merchant_entity.ENTITY_ID =isocp.ISO_ID")
        .append(" right join PG_ISO as iso on merchant_entity.ENTITY_ID = iso.ID")
        .append(" left join PG_CARD_PROGRAM as cp on isocp.CARD_PROGRAM_ID = cp.ID")
        .append(" where merchant_entity.MERCHANT_ID = :merchantId )as subqry");
                              
        Query allCardProgramQry = entityManager.createNativeQuery(query.toString());
        allCardProgramQry.setParameter("merchantId", merchantId);
        List<Object> cardProgramResponse = allCardProgramQry.getResultList();
        if(StringUtil.isListNotNullNEmpty(cardProgramResponse)){
            Iterator<Object> itr = cardProgramResponse.iterator();
            setMerchantCardPrograms(allCardProgramList, itr);         
        }
        
        //selected merchant-iso cp's
        StringBuilder query1 = new StringBuilder("select subqry.ID,subqry.CARD_PROGRAM_NAME,subqry.IIN,")
        .append(" subqry.IIN_EXT,subqry.ISSUANCE_PARTNER_NAME, subqry.CURRENCY,")
        .append(" subqry.ISO_NAME,subqry.IIN_PARTNER_EXT, subqry.ENTITY_ID ")
        .append(" from (select distinct cp.ID,cp.CARD_PROGRAM_NAME,cp.IIN,")
        .append(" cp.IIN_EXT,cp.ISSUANCE_PARTNER_NAME,cp.CURRENCY,iso.ISO_NAME,cp.IIN_PARTNER_EXT,merchantCpMap.ENTITY_ID")
        .append(" from PG_MERCHANT_CARD_PROGRAM_MAPPING as merchantCpMap")
        .append(" right join PG_ISO as iso on merchantCpMap.ENTITY_ID = iso.ID")
        .append(" left join PG_CARD_PROGRAM as cp on merchantCpMap.CARD_PROGRAM_ID = cp.ID")
        .append(" where merchantCpMap.MERCHANT_ID = :merchantId )as subqry");
        
        Query selectedCardProgramQry = entityManager.createNativeQuery(query1.toString());
        selectedCardProgramQry.setParameter("merchantId", merchantId);
        List<Object> selectedCardProgramResponse = selectedCardProgramQry.getResultList();
        if(StringUtil.isListNotNullNEmpty(selectedCardProgramResponse)){
            Iterator<Object> itr = selectedCardProgramResponse.iterator();
            setMerchantCardPrograms(selectedCardProgramList, itr);            
        }
        
        Map<String, CardProgramRequest> masterCpMap = new HashMap<>();
        for(CardProgramRequest masterCp : allCardProgramList){
          masterCpMap.put(getKey(masterCp.getIsoId(),masterCp), masterCp);
        }
        for(CardProgramRequest selectedCp : selectedCardProgramList){
          if(masterCpMap.containsKey(getKey(selectedCp.getIsoId(),selectedCp))){
            CardProgramRequest cardProgram = masterCpMap.get(getKey(selectedCp.getIsoId(),selectedCp));
            if(selectedCp.getCardProgramId().equals(cardProgram.getCardProgramId()) 
                // Compare the ambiguity ID
                && (selectedCp.getIsoId().equals(cardProgram.getIsoId()))) {
                // Set the card program as selected in the master card list
                cardProgram.setSelected(true);
            }
          }
        }
        allCardProgramList = new ArrayList<>(masterCpMap.values());
		response.setCardProgramRequests(allCardProgramList);
		return response;
	}
	
	private void setMerchantCardPrograms(List<CardProgramRequest> cardProgramList,Iterator<Object> itr) {
      CardProgramRequest cardProgramRequest;
      while(itr.hasNext()){
          Object[] objs = (Object[]) itr.next();
          cardProgramRequest= new CardProgramRequest();
          cardProgramRequest.setCardProgramId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
          cardProgramRequest.setCardProgramName(requestCardProgramName(objs));
          cardProgramRequest.setIin(requestIin(objs));
          cardProgramRequest.setIinExt(requestIinExt(objs));
          cardProgramRequest.setPartnerName(StringUtil.isNull(objs[4]) ? null : ((String) objs[4]));
          cardProgramRequest.setCurrency(StringUtil.isNull(objs[5]) ? null : ((String) objs[5]));
          cardProgramRequest.setEntityName(StringUtil.isNull(objs[6]) ? null : ((String) objs[6]));
          cardProgramRequest.setPartnerCode(StringUtil.isNull(objs[7]) ? null : ((String)objs[7]));
          cardProgramRequest.setIsoId(StringUtil.isNull(objs[8]) ? null : ((BigInteger) objs[8]).longValue());
          cardProgramList.add(cardProgramRequest);
      }
  }

	/**
	 * @param merchantId
	 * @return
	 */
	@Override
	public CardProgramResponse fetchIsoCardProgramByMerchantId(Long merchantId) {
		CardProgramResponse response = new CardProgramResponse();

		StringBuilder query = new StringBuilder(" SELECT pgiso.ID as isoId,pgiso.ISO_NAME,pgcp.ID as cardProgramId,pgcp.CARD_PROGRAM_NAME,pgcp.IIN,pgcp.IIN_EXT, pgcp.IIN_PARTNER_EXT,pgcp.ISSUANCE_PARTNER_NAME,pgcp.CURRENCY ")
				.append(" FROM PG_MERCHANT_ENTITY_MAPPING pgem")
				.append(" JOIN PG_ISO pgiso")
				.append(" ON pgiso.ID=pgem.ENTITY_ID")
				.append(" JOIN")
				 .append("  (SELECT ISO_ID,")
				  .append("   CARD_PROGRAM_ID")
				 .append("  FROM PG_ISO_CARD_PROGRAM_MAPPING")
				 .append("  GROUP BY ISO_ID,")
				  .append("   CARD_PROGRAM_ID")
				.append("   ) pgcpm")
				.append(" ON pgcpm.ISO_ID=pgiso.ID")
				.append(" JOIN PG_CARD_PROGRAM pgcp")
				.append(" ON pgcp.ID               =pgcpm.CARD_PROGRAM_ID")
				.append(" WHERE pgem.MERCHANT_ID   =:merchantId")
				.append(" AND CARD_PROGRAM_ID NOT IN")
				  .append(" (SELECT CARD_PROGRAM_ID FROM PG_MERCHANT_CARD_PROGRAM_MAPPING where MERCHANT_ID =:merchantId ")
				 .append("  )");

		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("merchantId", merchantId);
		List<Object> cardProgramResponse = qry.getResultList();
		Iterator<Object> itr = cardProgramResponse.iterator();
		List<CardProgramRequest> cardProgramList = new ArrayList<>();
		setCardPrograms(cardProgramList, itr);
		response.setCardProgramList(cardProgramList);
		return response;
	}
	
	private void setCardPrograms(List<CardProgramRequest> cardProgramList,Iterator<Object> itr) {
		CardProgramRequest cardProgramRequest;
		while(itr.hasNext()){
			Object[] objs = (Object[]) itr.next();
			cardProgramRequest= new CardProgramRequest();
			cardProgramRequest.setProgramManagerId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
			cardProgramRequest.setProgramManagerName(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
			cardProgramRequest.setCardProgramId(StringUtil.isNull(objs[2]) ? null : ((BigInteger) objs[2]).longValue());
			cardProgramRequest.setCardProgramName(StringUtil.isNull(objs[3]) ? null : ((String) objs[3]));
			cardProgramRequest.setIin(StringUtil.isNull(objs[4]) ? null : ((String) objs[4]));
			cardProgramRequest.setIinExt(StringUtil.isNull(objs[5]) ? null : ((String)objs[5]));
			cardProgramRequest.setPartnerCode(getCardProgramDetails(objs, Integer.parseInt("6")));
			cardProgramRequest.setPartnerName(getCardProgramDetails(objs, Integer.parseInt("7")));
			cardProgramRequest.setCurrency(getCardProgramDetails(objs, Integer.parseInt("8")));
			cardProgramList.add(cardProgramRequest);
		}
	}
	
	private String getCardProgramDetails(Object[] objs, int index) {
		return StringUtil.isNull(objs[index]) ? null : ((String) objs[index]);
	}

	public List<IsoAccount> findAccountByIsoId(Long isoId) {
		return isoAccountRepository.findByIsoIdAndAccountType(isoId, "SYSTEM_ACCOUNT");
	}

	/**
	 * @param entityId
	 * @return
	 */
	@Override
	public PGAccount findByEntityId(String entityId) {
		return accountRepository.findByEntityId(entityId);
	}

	/**
   * @param isoId
   * @param accountType
   * @return
   */
  @Override
  public List<IsoAccount> findByIsoIdAndAccountType(Long isoId, String accountType) {
    return isoAccountRepository.findByIsoIdAndAccountType(isoId, accountType);
  }
  
	@Override
	public List<IsoRequest> findIsoByProgramaManagerId(Long Pmid) {
		List<IsoRequest> isoRequests = new ArrayList<>();
		List<IsoPmMap> isoPmMapList = isoPmMapRepository.findByPmId(Pmid);
		for (IsoPmMap isoPmMap : isoPmMapList) {
			IsoRequest isoRequest = new IsoRequest();
			Iso iso = isoRepository.findById(isoPmMap.getIsoId()).get(0);
			isoRequest.setIsoName(iso.getIsoName());
			isoRequest.setId(iso.getId());
			isoRequests.add(isoRequest);
		}
		return isoRequests;
	}
	
	@Override
	public List<ProgramManagerRequest> findPmByIsoId(Long isoId) {
		List<IsoPmMap> isoPmMapList = isoPmMapRepository.findByIsoId(isoId);
		List<ProgramManagerRequest> programManagerRequestList = new ArrayList<>();
		for (IsoPmMap isoPmMap : isoPmMapList) {
			ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
			ProgramManager programamanger =  programManagerRepository.findById(isoPmMap.getPmId());
			programManagerRequest.setProgramManagerId(programamanger.getId());
			programManagerRequest.setProgramManagerName(programamanger.getProgramManagerName());
			programManagerRequestList.add(programManagerRequest);
		}
		return programManagerRequestList;
	}
	
	@Override
    public Long findByIsoIdAndCardProgramId(Long isoId,Long cardProgramId) {
        return isoCardProgramMapRepository.findByIsoIdAndCardProgramId(isoId,cardProgramId);
    }
	
	@Override
    public String findISOStatusById(Long isoId)  {
        return isoRepository.findISOStatusById(isoId);
    }
	
	@Override
    public int updateISOStatusById(Long isoId, String reason, String updatedBy, Timestamp updatedDate, String status)  {
        return isoRepository.updateISOStatusById(isoId, reason, updatedBy, updatedDate, status);
    }

	/**
	 * @param pmId
	 * @return
	 */
	@Override
	public List<Response> findIsoNameAndIdByEntityId(Long pmId) {
		List<Response> responses = new ArrayList<>();
		StringBuilder query = new StringBuilder(
				" select distinct pgiso.ID,pgiso.ISO_NAME from PG_PM_ISO_MAPPING pgisomp ")
				.append(" join PG_ISO pgiso on pgiso.ID=pgisomp.ISO_ID ")
				.append(" where pgisomp.ISO_ID not in (select id from PG_ISO pgis where pgis.ID in ")
				.append("(select ENTITY_ID from PG_MERCHANT_ENTITY_MAPPING )")
				.append("  ) and pgisomp.PM_ID=:entityId ");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("entityId", pmId);
		List<Object> list = qry.getResultList();
		if (StringUtil.isListNotNullNEmpty(list)) {
			Iterator<Object> itr = list.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				Response response = new Response();
				response.setIsoId(((BigInteger) object[0]).longValue());
				response.setIsoName(object[1].toString());
				responses.add(response);
			}
		}
		return responses;
	}
}
