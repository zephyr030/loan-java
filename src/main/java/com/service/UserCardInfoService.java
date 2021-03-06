package com.service;

import com.dao.UserCardInfoMapper;
import com.dao.UserRechargeDetailMapper;
import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.UserCardInfo;
import com.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/12/5 0005.
 */
@Service
public class UserCardInfoService extends BaseService{

    @Autowired
    private UserCardInfoMapper cardInfoMapper;

    /**
     * 修改一条用户账号信息
     * @param userCardInfo
     * @return
     */
    public int updateUserCardInfo(UserCardInfo userCardInfo) {
        return cardInfoMapper.updateByPrimaryKeySelective(userCardInfo);
    }

    /**
     * 根据超盘账号查询用户账户信息
     * @param account
     * @return
     */
    public UserCardInfo getUserCardInfoByAccount(String account, int status) {
        Searchable searchable = new Searchable();
        searchable.addCondition(new Condition("account", SearchOperator.eq, account));
        if(status >= 0) {
            searchable.addCondition(new Condition("status", SearchOperator.eq, status));
        }
        return cardInfoMapper.selectBySearchable(searchable);
    }

    /**
     * 根据条件查询单个账户信息
     * @param searchable
     * @return
     */
    public UserCardInfo selectUserCardInfoBySearchable(Searchable searchable) {
        return cardInfoMapper.selectBySearchable(searchable);
    }

    /**
     * 新增一条用户账户信息
     * @param cardInfo
     * @return
     */
    public AjaxResponse save(UserCardInfo cardInfo) {
        //先删除未激活的记录
        Searchable searchable = new Searchable();
        searchable.addCondition(new Condition("account", SearchOperator.eq, cardInfo.getAccount()));
        searchable.addCondition(new Condition("status", SearchOperator.eq, 0));
        cardInfoMapper.deleteBySearchable(searchable);

        //再判断用户输入的手机号和银行卡号是否已被注册
//        searchable = new Searchable();
//        searchable.addCondition(new Condition("mobile", SearchOperator.eq, cardInfo.getMobile()));
//        UserCardInfo exsitInfo = cardInfoMapper.selectBySearchable(searchable);
//        if(exsitInfo != null) {
//            return AjaxResponse.fail("手机号已被其他账户注册使用");
//        }
//        searchable = new Searchable();
//        searchable.addCondition(new Condition("cardnumber", SearchOperator.eq, cardInfo.getCardnumber()));
//        exsitInfo = cardInfoMapper.selectBySearchable(searchable);
//        if(exsitInfo != null) {
//            return AjaxResponse.fail("银行卡号已被其他账户注册使用");
//        }
        //再添加新的记录
        cardInfoMapper.insertSelective(cardInfo);
        return null;
    }

    /**
     * 验证用户输入的账户信息是否一致, 当length=4的时候 表示只验证银行卡后4位
     * @return
     */
    public AjaxResponse validateCardInfo(String customerName,
                                          String cardNumber,
                                          Integer bank,
                                          String mobile,
                                          UserCardInfo cardInfo,
                                          int length) {
        AjaxResponse ajaxResponse = null;
        if(!cardInfo.getCustomername().equals(customerName)) {
            ajaxResponse = AjaxResponse.fail("你输入的账户姓名有误");
        }else if(length != 4 && !cardInfo.getCardnumber().equals(cardNumber)) {
            ajaxResponse = AjaxResponse.fail("你输入的银行卡号有误");
        }else if(cardInfo.getBank() != bank) {
            ajaxResponse = AjaxResponse.fail("你输入的开户行信息有误");
        }else if(!cardInfo.getMobile().equals(mobile)) {
            ajaxResponse = AjaxResponse.fail("你输入的手机号信息有误");
        }else if(cardInfo.getStatus() == 2) {
            ajaxResponse = AjaxResponse.fail("你的账号已被冻结，请联系管理员解禁");
        }else if(length == 4) {
            if(cardNumber.length() != 4) {
                ajaxResponse = AjaxResponse.fail("你输入的银行卡号有误");
            }else {
                String card = cardInfo.getCardnumber().substring(cardInfo.getCardnumber().length()-4, cardInfo.getCardnumber().length());
                if(!card.equals(cardNumber)) {
                    ajaxResponse = AjaxResponse.fail("你输入的银行卡号有误");
                }
            }
        }
        return ajaxResponse;
    }

    /**
     * 查询账户信息
     * @param searchable
     * @param pageNumber
     * @param pageSize
     */
    public PageInfo selectCardInfoList(Searchable searchable, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<UserCardInfo> list = cardInfoMapper.selectList(searchable);
        PageInfo page = new PageInfo(list);
        return page;
    }

}
