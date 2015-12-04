package com.service;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseService {
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	protected CacheManager cacheManager;
}
