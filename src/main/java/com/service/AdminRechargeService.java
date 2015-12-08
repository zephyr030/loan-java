package com.service;

import com.dao.UserRechargeDetailMapper;
import com.dao.util.Searchable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 方法描述:TODO
 * <p/>
 * author 小刘
 * version v1.0
 * date 2015/12/8
 */
@Service
public class AdminRechargeService extends BaseService{
    @Autowired
    private UserRechargeDetailMapper userRechargeDetailMapper;

    //充值列表
    public PageInfo rechargeList(Searchable searchable, int pageNumber, int pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        List<Map<String,Object>> list = userRechargeDetailMapper.rechargeList(searchable);
        PageInfo page = new PageInfo(list);
        return page;
    }

    //导出充值列表Excel
    public List<Map<String,Object>> rechargeList(Searchable searchable){
        List<Map<String,Object>> list = userRechargeDetailMapper.rechargeList(searchable);
        return list;
    }

    //提现列表
    public PageInfo drawList(Searchable searchable, int pageNumber, int pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        List<Map<String,Object>> list = userRechargeDetailMapper.drawList(searchable);
        PageInfo page = new PageInfo(list);
        return page;
    }

    //导出提现列表Excel
    public List<Map<String,Object>> drawList(Searchable searchable){
        List<Map<String,Object>> list = userRechargeDetailMapper.drawList(searchable);
        return list;
    }

}
