package com.service;

import com.dao.SysTableCodeMapper;
import com.dao.util.Searchable;
import com.model.SysTableCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
@Service
public class SysTableCodeService {

    @Autowired
    private SysTableCodeMapper codeMapper;

    /**
     * 查询银行信息（可以做缓存）
     * @param searchable
     * @return
     */
    public List<SysTableCode> selectBankList(Searchable searchable) {
        return  codeMapper.selectList(searchable);
    }
}
