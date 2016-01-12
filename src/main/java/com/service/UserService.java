package com.service;

import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.SysUserMapper;
import com.model.SysUser;

@Service
public class UserService extends BaseService implements UserServiceI  {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private CacheManager cacheManager;


	public Map<String, Object> getUser(String id) {
		Map<String, Object> map = jdbcTemplate.queryForMap("select * from sys_user where id = ?", id);
		return map;
	}
	

	public SysUser getSysUser(String id) {
		System.out.println(id);
		SysUser user = sysUserMapper.selectByPrimaryKey(Integer.valueOf(id));
		System.out.println("userService no chache2!");
		return user;
	}
	

	public void getSys(String id) {
		
		Cache cache = cacheManager.getCache("ehcache_3600s");
		net.sf.ehcache.Element el = cache.get("user_" + id);
		if(null != el) {
			String v = (String)el.getObjectValue();
			System.out.println(v);
		}
	}


	public SysUser getSysUserByName(String username) {
		SysUser user = sysUserMapper.getSysUserByName(username);
		return user;
	}
	
}
