package com.service;

import com.dao.UserMobileMessageMapper;
import com.dao.UserWithdrawDetailMapper;
import com.dao.util.Searchable;
import com.model.UserMobileMessage;
import com.model.UserWithdrawDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
@Service
public class UserWithdrawService {

    @Autowired
    private UserWithdrawDetailMapper withDrawMapper;


    public long save(UserWithdrawDetail detail) {
        withDrawMapper.insertSelective(detail);
        return detail.getId();
    }
}
