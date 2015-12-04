package com.common;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

public class JdbcLoanTemplate extends org.springframework.jdbc.core.JdbcTemplate{
	
	public JdbcLoanTemplate(DataSource dataSource) {
		setDataSource(dataSource);
		afterPropertiesSet();
	}
	
	@Override
	public Map<String, Object> queryForMap(String sql)
			throws DataAccessException {
		try {
			return super.queryForMap(sql);
		} catch(DataAccessException e) {
			return null;
		}
	}
}
