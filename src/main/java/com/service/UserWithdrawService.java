package com.service;

import com.dao.UserWithdrawDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
@Service
public class UserWithdrawService {

    @Autowired
    private UserWithdrawDetailMapper withDrawMapper;

}
