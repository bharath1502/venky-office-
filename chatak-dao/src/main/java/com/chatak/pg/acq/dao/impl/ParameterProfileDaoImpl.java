package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ParameterProfileDao;
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGIsoCountryCode;
import com.chatak.pg.acq.dao.model.PGLocalCurrencyCode;
import com.chatak.pg.acq.dao.model.PGParameterProfile;
import com.chatak.pg.acq.dao.model.PGTerminalCapabilities;
import com.chatak.pg.acq.dao.model.QPGParameterProfile;
import com.chatak.pg.acq.dao.model.QPGTerminalCapabilities;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.IsoCountryCodeRepository;
import com.chatak.pg.acq.dao.repository.LocalCurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.ParameterProfileRepository;
import com.chatak.pg.acq.dao.repository.TerminalCapabilitiesRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.ParameterProfileDTO;
import com.chatak.pg.model.TerminalCapabilitiesDTO;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("parameterProfileDao")
public class ParameterProfileDaoImpl implements ParameterProfileDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ParameterProfileRepository parameterProfileRepository;
	
	@Autowired
	private TerminalCapabilitiesRepository terminalCapabilitiesRepository;
	

	@Autowired
	private IsoCountryCodeRepository isoCountryCodeRepository;
	
	@Autowired
	private LocalCurrencyCodeRepository localCurrencyCodeRepository;
	
	@Autowired
	private CurrencyCodeRepository currencyCodeRepository;

	@SuppressWarnings("unchecked")
	@Override
	public Long findByProfileName(String profileName) {
		Query query = entityManager .createQuery("Select p.profileId FROM PGParameterProfile p WHERE p.profileName =:profileName");
		query.setParameter("profileName",profileName);
		List<Long> list = query.getResultList();
		return (StringUtil.isListNotNullNEmpty(list)) ? list.get(0) : null;
	}

	@Override
	public PGParameterProfile createOrUpdateParameterProfile(PGParameterProfile parameterProfile) {
		return parameterProfileRepository.save(parameterProfile);
	}

	
	@Override
	public PGParameterProfile findByParameterProfileId(Long profileId) {
		PGParameterProfile parameterProfile = null;
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> parameterProfileList= query.from(QPGParameterProfile.pGParameterProfile,QPGTerminalCapabilities.pGTerminalCapabilities)
			                            .where(isProfileIdEq(profileId),	
			    		                 QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId.eq(QPGTerminalCapabilities.pGTerminalCapabilities.terminalCapabilitiesId))
			    		               .list(QPGParameterProfile.pGParameterProfile.profileId,QPGParameterProfile.pGParameterProfile.profileName,QPGParameterProfile.pGParameterProfile.terminalType,
			    		            		 QPGParameterProfile.pGParameterProfile.isoCountryCode,QPGParameterProfile.pGParameterProfile.localCurrencyCode,QPGParameterProfile.pGParameterProfile.currencyCode,
			    		            		 QPGParameterProfile.pGParameterProfile.targetPercentage,QPGParameterProfile.pGParameterProfile.terminalFloorLimit,QPGParameterProfile.pGParameterProfile.maxTargetPercentage,
			    		            		 QPGParameterProfile.pGParameterProfile.defaultCdol,QPGParameterProfile.pGParameterProfile.defaultDdol,QPGParameterProfile.pGParameterProfile.defaultTdol,
                                     		 QPGParameterProfile.pGParameterProfile.unAttendMerchant,QPGParameterProfile.pGParameterProfile.unAttendfinancialInstitute,
			    		            		 QPGParameterProfile.pGParameterProfile.attendMerchant,QPGParameterProfile.pGParameterProfile.attendFinancialInstitute,
			    		            		 QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId,
			    		            		 QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId,QPGParameterProfile.pGParameterProfile.attended,QPGParameterProfile.pGParameterProfile.unAttended,
			    		            		 QPGParameterProfile.pGParameterProfile.attendFinancialInstitute, QPGParameterProfile.pGParameterProfile.attendMerchant,QPGParameterProfile.pGParameterProfile.unAttendMerchant,
			    		            		 QPGParameterProfile.pGParameterProfile.unAttendfinancialInstitute,QPGParameterProfile.pGParameterProfile.createdBy,QPGParameterProfile.pGParameterProfile.createdDate,
			    		            		 QPGParameterProfile.pGParameterProfile.status,QPGParameterProfile.pGParameterProfile.unAttendCardHolder);

		for (Tuple tuple:parameterProfileList) {
			parameterProfile = new PGParameterProfile();
			PGTerminalCapabilities terminalCapabilities = new PGTerminalCapabilities();
			parameterProfile.setTerminalCapabilities(terminalCapabilities);
			parameterProfile.setProfileId(tuple.get(QPGParameterProfile.pGParameterProfile.profileId));
			parameterProfile.setProfileName(tuple.get(QPGParameterProfile.pGParameterProfile.profileName));
			parameterProfile.setCurrencyCode(tuple.get(QPGParameterProfile.pGParameterProfile.currencyCode));
			parameterProfile.setTerminalType(tuple.get(QPGParameterProfile.pGParameterProfile.terminalType));
			parameterProfile.setIsoCountryCode(tuple.get(QPGParameterProfile.pGParameterProfile.isoCountryCode));
			parameterProfile.setLocalCurrencyCode(tuple.get(QPGParameterProfile.pGParameterProfile.localCurrencyCode));
			parameterProfile.setCurrencyCode(tuple.get(QPGParameterProfile.pGParameterProfile.currencyCode));
			parameterProfile.setTargetPercentage(tuple.get(QPGParameterProfile.pGParameterProfile.targetPercentage));
			parameterProfile.setTerminalFloorLimit(tuple.get(QPGParameterProfile.pGParameterProfile.terminalFloorLimit));
			parameterProfile.setDefaultCdol(tuple.get(QPGParameterProfile.pGParameterProfile.defaultCdol));
			parameterProfile.setDefaultDdol(tuple.get(QPGParameterProfile.pGParameterProfile.defaultDdol));
			parameterProfile.setDefaultTdol(tuple.get(QPGParameterProfile.pGParameterProfile.defaultTdol));
			parameterProfile.setMaxTargetPercentage(tuple.get(QPGParameterProfile.pGParameterProfile.maxTargetPercentage));
			parameterProfile.setAttendMerchant(tuple.get(QPGParameterProfile.pGParameterProfile.attendMerchant));
			parameterProfile.setAttendFinancialInstitute(tuple.get(QPGParameterProfile.pGParameterProfile.attendFinancialInstitute));
			parameterProfile.setUnAttendMerchant(tuple.get(QPGParameterProfile.pGParameterProfile.unAttendMerchant));
			parameterProfile.setUnAttendfinancialInstitute(tuple.get(QPGParameterProfile.pGParameterProfile.unAttendfinancialInstitute));
			parameterProfile.setAttended(tuple.get(QPGParameterProfile.pGParameterProfile.attended));
			parameterProfile.setUnAttended(tuple.get(QPGParameterProfile.pGParameterProfile.unAttended));
			parameterProfile.setAttendFinancialInstitute(tuple.get(QPGParameterProfile.pGParameterProfile.attendFinancialInstitute));
			parameterProfile.setAttendMerchant(tuple.get(QPGParameterProfile.pGParameterProfile.attendMerchant));
			parameterProfile.setUnAttendfinancialInstitute(tuple.get(QPGParameterProfile.pGParameterProfile.unAttendfinancialInstitute));
            parameterProfile.setUnAttendMerchant(tuple.get(QPGParameterProfile.pGParameterProfile.unAttendMerchant));
            parameterProfile.setStatus(tuple.get(QPGParameterProfile.pGParameterProfile.status));
            parameterProfile.setCreatedBy(tuple.get(QPGParameterProfile.pGParameterProfile.createdBy));
            parameterProfile.setCreatedDate(tuple.get(QPGParameterProfile.pGParameterProfile.createdDate));
            parameterProfile.setUnAttendCardHolder(tuple.get(QPGParameterProfile.pGParameterProfile.unAttendCardHolder));
            parameterProfile.getTerminalCapabilities().setTerminalCapabilitiesId(tuple.get(QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId));
		}
		return parameterProfile;
	}
	
	@Override
	public List<ParameterProfileDTO> searchParameterProfile(ParameterProfileDTO parameterProfileTO) {
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = parameterProfileTO.getPageIndex();
		Integer pageSize = parameterProfileTO.getPageSize();
		Integer totalRecords;
		
		 if(parameterProfileTO.getPageIndex() == null || parameterProfileTO.getPageIndex() == 1){
	            totalRecords = getTotalNumberOfRecords(parameterProfileTO);
	            parameterProfileTO.setNoOfRecords(totalRecords);
	     }

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}
		
		List<ParameterProfileDTO> parameterProfileList = new ArrayList<ParameterProfileDTO>();
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> profileList = query.from(QPGParameterProfile.pGParameterProfile,QPGTerminalCapabilities.pGTerminalCapabilities)
				                       .where(isProfileIdEq(parameterProfileTO.getProfileId()),
				                    		   isProfileNameLike(parameterProfileTO.getProfileName()),
				                    		   isTerminalTypeLike(parameterProfileTO.getTerminalType()),
								               isStatusEq(parameterProfileTO.getStatus()),
								               isParameterType(parameterProfileTO.getParameterType()),
								               QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId.eq(QPGTerminalCapabilities.pGTerminalCapabilities.terminalCapabilitiesId))
						                      .offset(offset)
						               .limit(limit).orderBy(orderByProfileIdDesc())
				                       .list(QPGParameterProfile.pGParameterProfile.profileId,QPGParameterProfile.pGParameterProfile.profileName,QPGParameterProfile.pGParameterProfile.terminalType,
			    		            		 QPGParameterProfile.pGParameterProfile.isoCountryCode,QPGParameterProfile.pGParameterProfile.localCurrencyCode,QPGParameterProfile.pGParameterProfile.currencyCode,
			    		            		 QPGParameterProfile.pGParameterProfile.targetPercentage,QPGParameterProfile.pGParameterProfile.terminalFloorLimit,QPGParameterProfile.pGParameterProfile.status,
			    		            		 QPGParameterProfile.pGParameterProfile.defaultCdol,QPGParameterProfile.pGParameterProfile.defaultDdol,QPGParameterProfile.pGParameterProfile.defaultTdol,
			    		            		 QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId);
		for (Tuple tuple : profileList) {
			ParameterProfileDTO parameterProfile = new ParameterProfileDTO();
			TerminalCapabilitiesDTO terminalCapabilities = new TerminalCapabilitiesDTO();
			parameterProfile.setTerminalCapabilities(terminalCapabilities);
			parameterProfile.setProfileId(tuple.get(QPGParameterProfile.pGParameterProfile.profileId));
			parameterProfile.setProfileName(tuple.get(QPGParameterProfile.pGParameterProfile.profileName));
			parameterProfile.setTerminalType(tuple.get(QPGParameterProfile.pGParameterProfile.terminalType));
			parameterProfile.setTargetPercentage(tuple.get(QPGParameterProfile.pGParameterProfile.targetPercentage));
			parameterProfile.setTerminalFloorLimit(tuple.get(QPGParameterProfile.pGParameterProfile.terminalFloorLimit));
			parameterProfile.setDefaultCdol(tuple.get(QPGParameterProfile.pGParameterProfile.defaultCdol));
			parameterProfile.setDefaultDdol(tuple.get(QPGParameterProfile.pGParameterProfile.defaultDdol));
			parameterProfile.setDefaultTdol(tuple.get(QPGParameterProfile.pGParameterProfile.defaultTdol));
			parameterProfile.setStatus(tuple.get(QPGParameterProfile.pGParameterProfile.status));
			parameterProfile.getTerminalCapabilities().setTerminalCapabilitiesId(tuple.get(QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId));
			parameterProfileList.add(parameterProfile);
		}
		return parameterProfileList;
	}
	

	private int getTotalNumberOfRecords(ParameterProfileDTO parameterProfileTO) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> list = query.from(QPGParameterProfile.pGParameterProfile,QPGTerminalCapabilities.pGTerminalCapabilities)
				 .where(isProfileIdEq(parameterProfileTO.getProfileId()),
						 isProfileNameLike(parameterProfileTO.getProfileName()),
			               isTerminalTypeLike(parameterProfileTO.getTerminalType()),
			               isStatusEq(parameterProfileTO.getStatus()),
			               isParameterType(parameterProfileTO.getParameterType()),
			                QPGParameterProfile.pGParameterProfile.terminalCapabilities.terminalCapabilitiesId.eq(QPGTerminalCapabilities.pGTerminalCapabilities.terminalCapabilitiesId))
						 .list(QPGParameterProfile.pGParameterProfile.profileId);
		return (StringUtil.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	public PGParameterProfile updateStatus(PGParameterProfile parameterProfile)throws DataAccessException{
		return parameterProfileRepository.save(parameterProfile);
	}

	@Override
	public PGTerminalCapabilities findByTerminalCapabilitiesId(Long terminalCapablitiesId) {
		return terminalCapabilitiesRepository.findByTerminalCapabilitiesId(terminalCapablitiesId);
	}
	
	@Override
	public List<PGIsoCountryCode> getIsoCountryCodeList() {
		return isoCountryCodeRepository.findAll();
	}

	@Override
	public List<PGLocalCurrencyCode> getLocalCurrencyCodeList() {
		return localCurrencyCodeRepository.findAll();
	}

	@Override
	public List<PGCurrencyCode> getCurrencyCodeList() {
		return currencyCodeRepository.findAll();
	}
	
	private BooleanExpression isProfileIdEq(Long profileId) {
		return profileId != null ? QPGParameterProfile.pGParameterProfile.profileId.eq(profileId): null;
	}
	
	private OrderSpecifier<Long> orderByProfileIdDesc() {
        return QPGParameterProfile.pGParameterProfile.profileId.desc();
    }

	private BooleanExpression isProfileNameLike(String profileName) {
		return (profileName != null && !"".equals(profileName))? QPGParameterProfile.pGParameterProfile.profileName.toUpperCase().like("%" + profileName.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isTerminalTypeLike(String terminalType) {
		return (terminalType != null && !"".equals(terminalType))? QPGParameterProfile.pGParameterProfile.terminalType.eq(terminalType): null;
	}
    
	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status))? QPGParameterProfile.pGParameterProfile.status.eq(status): null;
	}

	private BooleanExpression isParameterType(String parameterType) {

		return (parameterType != null && !"".equals(parameterType)) ? QPGParameterProfile.pGParameterProfile.parameterType.equalsIgnoreCase(parameterType): null;
	}
}