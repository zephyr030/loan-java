package com.dao;

import com.dao.common.BaseMapper;
import com.dao.common.MyBatisMapper;
import com.model.SysUser;

@MyBatisMapper
public interface SysUserMapper extends BaseMapper<SysUser,Integer> {

    SysUser getSysUserByName(String username);
}