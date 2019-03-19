package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FaqManagementDao;
import com.chatak.pg.acq.dao.model.Category;
import com.chatak.pg.acq.dao.model.CategoryModule;
import com.chatak.pg.acq.dao.model.CategoryModuleMapping;
import com.chatak.pg.acq.dao.model.FaqManagement;
import com.chatak.pg.acq.dao.repository.CategoryModuleMappingRepository;
import com.chatak.pg.acq.dao.repository.CategoryModuleRepository;
import com.chatak.pg.acq.dao.repository.CategoryRepository;
import com.chatak.pg.acq.dao.repository.FaqManagementRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.util.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Repository("faqManagementDao")
public class FaqManagementDaoImpl implements FaqManagementDao {

	private Logger logger = LogManager.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private FaqManagementRepository faqManagementRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryModuleRepository categoryModuleRepository;

	@Autowired
	private CategoryModuleMappingRepository categoryModuleMappingRepository;

	@Override
	public List<Category> getAllCategories() throws DataAccessException {
		return categoryRepository.findAll();
	}

	public List<CategoryModule> findByModuleName(Long categoryId){
		return categoryModuleRepository.findByCategoryId(categoryId);
	}

	@Override
	public CategoryModuleMapping findByCategoryMappingId(Long categoryId, Long moduleId)  {
		return categoryModuleMappingRepository.findByCategoryIdAndModuleId(categoryId, moduleId);
	}

	@Override
	public FaqManagement saveOrUpdateFaqManagement(FaqManagement faqManagementDto) {
		return faqManagementRepository.save(faqManagementDto);

	}

	@Override
	public List<FaqManagementRequest> searchFaqManagement(FaqManagementRequest faqManagementRequest)
			throws DataAccessException {
		List<FaqManagementRequest> faqManagementData = new ArrayList<FaqManagementRequest>();

		Integer pageIndex = faqManagementRequest.getPageIndex();
		Integer pageSize = faqManagementRequest.getPageSize();
		;

		Integer offset = 0;
		Integer limit = 0;
		Integer totalRecords;

		if (pageIndex == null || pageIndex == 1) {
			totalRecords = getTotalNumberOfRecords(faqManagementRequest);
			faqManagementRequest.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}
		StringBuilder query = new StringBuilder(
				"select fm.QUESTION,fm.ANSWER,fm.STATUS,fm.FAQ_ID,fm.CATEGORY_MAPPING_ID,ct.CATEGORY_ID,cm.MODULE_ID,cm.MODULE_NAME,ct.CATEGORY_NAME ");
		query.append(" from CATEGORY ct inner join CATEGORY_MODULE cm on ct.CATEGORY_ID=cm.CATEGORY_ID ");
		query.append(" inner join CATEGORY_MODULE_MAPPING cmm on cm.MODULE_ID=cmm.MODULE_ID ");
		query.append(" inner join FAQ_MANAGEMENT fm on cmm.CATEGORY_MAPPING_ID=fm.CATEGORY_MAPPING_ID ");
		query.append(
				" where (:categoryId is null or ct.CATEGORY_ID=:categoryId) and (:moduleId is null or cm.MODULE_ID=:moduleId) and");
		query.append(" (:questionName is null or  fm.QUESTION like CONCAT('%', :questionName,'%'))");
		query.append(" LIMIT :offset, :limit");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("categoryId",
				(StringUtil.isNullAndEmpty(String.valueOf(faqManagementRequest.getCategoryId())) ? null
						: faqManagementRequest.getCategoryId()));
		qry.setParameter("moduleId",
				(StringUtil.isNullAndEmpty(String.valueOf(faqManagementRequest.getModuleId())) ? null
						: faqManagementRequest.getModuleId()));
		qry.setParameter("questionName",
				(faqManagementRequest.getQuestionName() != null && !(null == faqManagementRequest.getQuestionName())
						? faqManagementRequest.getQuestionName()
						: null));
		qry.setParameter("offset", offset);
		qry.setParameter("limit", limit);
		List<Object> list = qry.getResultList();
		if (StringUtil.isListNotNullNEmpty(list)) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] objs = (Object[]) it.next();
				setDataToFaqManagementSearchData(faqManagementData, objs);
			}
		}
		return faqManagementData;

	}

	private void setDataToFaqManagementSearchData(List<FaqManagementRequest> faqManagementData, Object[] objs) {
		FaqManagementRequest faqManagementRequest = new FaqManagementRequest();

		faqManagementRequest.setQuestionName(objs[0] != null ? (String) objs[0] : null);
		faqManagementRequest.setQuestionAnswer(objs[1] != null ? (String) objs[1] : null);
		faqManagementRequest.setStatus(objs[2] != null ? (String) objs[2] : null);

		faqManagementRequest.setFaqId(((BigInteger) objs[3]).longValue());
		faqManagementRequest.setCategoryMappingId(((BigInteger) objs[4]).longValue());
		faqManagementRequest.setCategoryId(((BigInteger) objs[5]).longValue());
		faqManagementRequest.setModuleId(((BigInteger) objs[6]).longValue());
		faqManagementRequest.setModuleName(objs[7] != null ? (String) objs[7] : null);
		faqManagementRequest.setCategoryName(objs[8] != null ? (String) objs[8] : null);
		faqManagementData.add(faqManagementRequest);
	}

	private int getTotalNumberOfRecords(FaqManagementRequest faqManagementRequest) {
		StringBuilder query = new StringBuilder(
				"select fm.QUESTION,fm.ANSWER,fm.STATUS,fm.FAQ_ID,fm.CATEGORY_MAPPING_ID,ct.CATEGORY_ID,cm.MODULE_ID,cm.MODULE_NAME,ct.CATEGORY_NAME ");
		query.append(" from CATEGORY ct inner join CATEGORY_MODULE cm on ct.CATEGORY_ID=cm.CATEGORY_ID ");
		query.append(" inner join CATEGORY_MODULE_MAPPING cmm on cm.MODULE_ID=cmm.MODULE_ID ");
		query.append("  inner join FAQ_MANAGEMENT fm on cmm.CATEGORY_MAPPING_ID=fm.CATEGORY_MAPPING_ID ");
		query.append(
				" where (:categoryId is null or ct.CATEGORY_ID=:categoryId) and (:moduleId is null or cm.MODULE_ID=:moduleId) and");
		query.append(" (:questionName is null or  fm.QUESTION like CONCAT('%', :questionName,'%'))");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("categoryId",
				(StringUtil.isNullAndEmpty(String.valueOf(faqManagementRequest.getCategoryId())) ? null
						: faqManagementRequest.getCategoryId()));
		qry.setParameter("moduleId",
				(StringUtil.isNullAndEmpty(String.valueOf(faqManagementRequest.getModuleId())) ? null
						: faqManagementRequest.getModuleId()));
		qry.setParameter("questionName",
				(faqManagementRequest.getQuestionName() != null && !(null == faqManagementRequest.getQuestionName())
						? faqManagementRequest.getQuestionName()
						: null));
		List<Object> list = qry.getResultList();

		return (StringUtil.isListNotNullNEmpty(list) ? list.size() : 0);
	}

	@Override
	public Category findByCategoryName(Long categoryId){
		return categoryRepository.findByCategoryId(categoryId);
	}

	@Override
	public CategoryModule findByModuleNameFaq(Long categoryId, Long moduleId) {
		return categoryModuleRepository.findByCategoryIdAndModuleId(categoryId, moduleId);
	}

	/**
	 * @param faqId
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public FaqManagementRequest findByFaqId(Long faqId) {
		FaqManagementRequest faqManagementData = new FaqManagementRequest();
		StringBuilder query = new StringBuilder(
				"select ct.CATEGORY_NAME,cm.MODULE_NAME,fm.FAQ_ID,fm.CATEGORY_MAPPING_ID,fm.QUESTION,fm.ANSWER,fm.STATUS,cmm.CATEGORY_ID,cmm.MODULE_ID");
		query.append(" from CATEGORY ct inner join CATEGORY_MODULE cm  on ct.CATEGORY_ID=cm.CATEGORY_ID");
		query.append("  inner join CATEGORY_MODULE_MAPPING cmm on cm.MODULE_ID=cmm.MODULE_ID");
		query.append("  inner join FAQ_MANAGEMENT fm on cmm.CATEGORY_MAPPING_ID=fm.CATEGORY_MAPPING_ID");
		query.append("  where fm.FAQ_ID=:faqId");

		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("faqId", StringUtil.isNull(faqId) ? null : faqId);
		List<Object> list = qry.getResultList();
		if (StringUtil.isListNotNullNEmpty(list)) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] objs = (Object[]) it.next();
				faqManagementData = setDataToFaqManagementRequest(faqManagementData, objs);
			}
		}

		return faqManagementData;
	}

	private FaqManagementRequest setDataToFaqManagementRequest(FaqManagementRequest faqManagementData, Object[] objs) {
		FaqManagementRequest faqManagementRequest = new FaqManagementRequest();
		faqManagementRequest.setCategoryName(objs[0] != null ? (String) objs[0] : null);
		faqManagementRequest.setModuleName(objs[1] != null ? (String) objs[1] : null);
		faqManagementRequest.setFaqId(((BigInteger) objs[2]).longValue());

		faqManagementRequest.setCategoryMappingId(((BigInteger) objs[3]).longValue());
		faqManagementRequest.setQuestionName(objs[4] != null ? (String) objs[4] : null);
		faqManagementRequest.setQuestionAnswer(objs[5] != null ? (String) objs[5] : null);
		faqManagementRequest.setStatus(objs[6] != null ? (String) objs[6] : null);
		faqManagementRequest.setCategoryId(((BigInteger) objs[7]).longValue());
		faqManagementRequest.setModuleId(((BigInteger) objs[8]).longValue());
		return faqManagementRequest;
	}

	/**
	 * @param faqManagementRequest
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<FaqManagementRequest> findByCategoryMappingId(FaqManagementRequest faqManagementRequest) {
		List<FaqManagementRequest> faqManagementRequestList = new ArrayList<FaqManagementRequest>();
		StringBuilder searchQuestionAnswer = new StringBuilder();
		searchQuestionAnswer.append("select * from FAQ_MANAGEMENT fm");
		searchQuestionAnswer.append(" where fm.CATEGORY_MAPPING_ID in (:categoryMappingId)");
		Query qry = entityManager.createNativeQuery(searchQuestionAnswer.toString());
		qry.setParameter("categoryMappingId", StringUtil.isNull(faqManagementRequest.getCategoryMappingId()) ? null
				: faqManagementRequest.getCategoryMappingId());
		@SuppressWarnings("unchecked")
		List<Object> list = qry.getResultList();

		if (StringUtil.isListNotNullNEmpty(list)) {
			@SuppressWarnings("rawtypes")
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				try {
					Object[] objs = (Object[]) itr.next();
					FaqManagementRequest faqRequestResult = new FaqManagementRequest();
					faqRequestResult.setQuestionName(StringUtil.isNull(objs[2]) ? null : (String) objs[2]);
					faqRequestResult.setQuestionAnswer(StringUtil.isNull(objs[3]) ? null : (String) objs[3]);
					faqManagementRequestList.add(faqRequestResult);

				} catch (Exception e) {
					logger.error("ERROR: FaqDaoImpl:: findByCategoryMappingId method", e);
				}
			}
		}
		return faqManagementRequestList;
	}
}
