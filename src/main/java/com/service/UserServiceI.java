package com.service;

import java.util.Map;

import com.model.SysUser;

public interface UserServiceI {
	
	public Map<String, Object> getUser(String id);
	
	public SysUser getSysUser(String id);

	public void getSys(String id);
}
