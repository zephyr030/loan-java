package com.service;

import com.dao.SysUserMapper;
import com.dao.util.Searchable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 方法描述:TODO
 * <p/>
 * author 小刘
 * version v1.0
 * date 2015/12/17
 */
@Service
public class AdminSysUserService extends BaseService{
    @Autowired
    private SysUserMapper sysUserMapper;

    //系统用户列表
    public PageInfo userList(Searchable searchable, int pageNumber, int pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        List<Map<String,Object>> list = sysUserMapper.sysUserList(searchable);
        PageInfo page = new PageInfo(list);
        return page;
    }

    //添加用户
    public int addSysUser(String username,String password,int available){
        int status = 0;
        //是否添加过该用户
        String str = "select count(0) as getCount from sys_user where username = ?";
        int i = jdbcTemplate.queryForObject(str,new Object[]{username},Integer.class);
        if(i > 0){
            status = 1;
        }else{
            //保存用户
            String sql = "insert into sys_user(username,password,available) values (?,?,?)";
            jdbcTemplate.update(sql,new Object[]{username,password,available});
        }
        return status;
    }

    //启用/不启用/删除
    public void isOpen(long id,int status){
        String sql = "update sys_user set available = ? where id = ?";
        jdbcTemplate.update(sql,new Object[]{status,id});
    }
}
