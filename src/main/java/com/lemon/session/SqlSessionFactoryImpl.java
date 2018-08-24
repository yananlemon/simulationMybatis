package com.lemon.session;

import com.lemon.config.Configuration;

public class SqlSessionFactoryImpl implements ISqlSessionFactory {

	private Configuration config;
	
	public SqlSessionFactoryImpl(Configuration config) {
		this.config = config;
	}

	@Override
	public ISqlSession openSession() {
		SqlSessionImpl sqlSession=new SqlSessionImpl(config);
		return sqlSession;
	}

}
