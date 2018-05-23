package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ProfileDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.repository.ProfileRepository;

@Repository("profileDao")
public class ProfileDaoImpl implements ProfileDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public PGAdminUser saveOrUpdate(PGAdminUser adminUser) throws DataAccessException {
		return profileRepository.save(adminUser);
	}

	@Override
	public List<PGAdminUser> findByUserName(String userName) throws DataAccessException {
		return profileRepository.findByUserName(userName);
	}

	@Override
	public List<PGAdminUser> findByadminUserId(Long adminUserId) throws DataAccessException {
		return profileRepository.findByadminUserId(adminUserId);
	}
	
}