package com.service;

import com.dao.UserMobileMessageMapper;
import com.dao.util.Searchable;
import com.model.UserMobileMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/12/9 0009.
 */
@Service
public class UserMobileMessageService {

    @Autowired
    private UserMobileMessageMapper messageMapper;


    public UserMobileMessage selectMessage(Searchable searchable) {
        return messageMapper.selectBySearchable(searchable);
    }


    public void save(UserMobileMessage message) {
        messageMapper.insertSelective(message);
    }

    public void update(UserMobileMessage message) {
        messageMapper.updateByPrimaryKeySelective(message);
    }
}
