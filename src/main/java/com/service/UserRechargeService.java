package com.service;

import com.dao.UserCardInfoMapper;
import com.dao.UserRechargeDetailMapper;
import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.model.UserCardInfo;
import com.model.UserRechargeDetail;
import com.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/12/5 0005.
 */
@Service
public class UserRechargeService extends BaseService{

    @Autowired
    private UserRechargeDetailMapper rechargeMapper;

    /**
     * 新增充值记录
     * @param rechargeDetail
     * @return
     */
    public int save(UserRechargeDetail rechargeDetail) {
        return rechargeMapper.insertSelective(rechargeDetail);
    }
}
