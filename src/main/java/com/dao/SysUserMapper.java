package com.dao;

import com.dao.common.BaseMapper;
import com.dao.common.MyBatisMapper;
import com.dao.util.Searchable;
import com.model.SysUser;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface SysUserMapper extends BaseMapper<SysUser,Integer> {

    SysUser getSysUserByName(String username);

    //系统用户列表
    List<Map<String,Object>> sysUserList(Searchable searchable);
}