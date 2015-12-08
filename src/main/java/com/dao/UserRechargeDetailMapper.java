package com.dao;

import com.dao.common.BaseMapper;
import com.dao.common.MyBatisMapper;
import com.dao.util.Searchable;
import com.model.UserRechargeDetail;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface UserRechargeDetailMapper extends BaseMapper<UserRechargeDetail, Long>{

    //充值列表
    List<Map<String,Object>> rechargeList(Searchable searchable);
}