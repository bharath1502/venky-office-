package com.chatak.pg.acq.dao.impl;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class BaseDao extends JdbcDaoSupport {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	void init(){
		setDataSource(dataSource);
	}

}
