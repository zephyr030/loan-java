package com.dao;

import com.dao.common.BaseMapper;
import com.dao.common.MyBatisMapper;
import com.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 方法描述:充值查询
 * <p/>
 * author 小刘
 * version v1.0
 * date 2015/12/8
 */
@MyBatisMapper
public interface AminRechargeMapper extends BaseMapper<SysUser,Long>{
    //充值列表
    List<Map<String,Object>> rechargeList();
}
