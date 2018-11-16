/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.EntityFeatureDao;
import com.chatak.pg.acq.dao.model.QPGEntity;
import com.chatak.pg.acq.dao.model.QPGEntityFeature;
import com.chatak.pg.acq.dao.model.QPGFeature;
import com.chatak.pg.acq.dao.model.QPGRolesFeatureMapping;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: May 11, 2018
 * @Time: 1:31:57 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("entityFeatureDao")
public class EntityFeatureDaoImpl implements EntityFeatureDao{
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @param entity
	 * @return
	 */
	@Override
	public List<Long> getFeaturesByEntity(String entity) {
		JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
		return query.from(QPGEntityFeature.pGEntityFeature,QPGEntity.pGEntity)
		.select(QPGEntityFeature.pGEntityFeature.featureId)
		.where(QPGEntity.pGEntity.name.equalsIgnoreCase(entity),
				QPGEntityFeature.pGEntityFeature.entityId.eq(QPGEntity.pGEntity.id))
		.orderBy(QPGEntityFeature.pGEntityFeature.featureId.asc())
		.fetch();
	}

}
