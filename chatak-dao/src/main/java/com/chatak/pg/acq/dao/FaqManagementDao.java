package com.chatak.pg.acq.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.chatak.pg.acq.dao.model.Category;
import com.chatak.pg.acq.dao.model.CategoryModule;
import com.chatak.pg.acq.dao.model.CategoryModuleMapping;
import com.chatak.pg.acq.dao.model.FaqManagement;
import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.user.bean.FaqManagementResponse;


public interface FaqManagementDao {

	 public List<Category> getAllCategories()throws DataAccessException;

	 public List<CategoryModule> findByModuleName(Long categoryId)throws DataAccessException;

	 public CategoryModuleMapping findByCategoryMappingId(Long categoryId, Long moduleId) throws DataAccessException;

	 public FaqManagement saveOrUpdateFaqManagement(FaqManagement faqManagementDto) throws DataAccessException;

	 public List<FaqManagementRequest> searchFaqManagement(FaqManagementRequest faqManagementRequest)throws DataAccessException;

	 public Category findByCategoryName(Long categoryId) throws DataAccessException;

	 public CategoryModule findByModuleNameFaq(Long categoryId, Long moduleId) throws DataAccessException;

	 public FaqManagementRequest findByFaqId(Long faqId) throws DataAccessException;

	 public List<FaqManagementRequest> findByCategoryMappingId(FaqManagementRequest faqManagementRequest)throws DataAccessException;

}
