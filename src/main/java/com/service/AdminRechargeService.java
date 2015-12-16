package com.service;

import com.dao.UserRechargeDetailMapper;
import com.dao.util.Searchable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.UserCardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //回填充值信息
    @Transactional
    public void reLoadCharge(long id,String fowNo,String time,long userId,Double amount,long user_id){
        //更新提现表
        String sql = "update user_recharge_detail set flowNo = ?,actTime = ?,status = 1,update_uid = ? where id = ?";
        jdbcTemplate.update(sql,new Object[]{fowNo,time,userId,id});
        //更新用户表
        String user = "update user_card_info set balance = balance+? where id = ?";
        jdbcTemplate.update(user,new Object[]{amount,user_id});
    }
    //拒绝上账
    public int refusedRecharge(long id,long userId,String remark){
        String sql = "update user_recharge_detail set status = 2,update_uid = ?,remark = ? where id = ?";
        return jdbcTemplate.update(sql,new Object[]{userId,remark,id});
    }

    //回填提现信息
    @Transactional
    public void reloadDraw(long id,int counts,Double money,String bankNo,long userId,long user_id){
        //更新提现信息
        String sql = "update user_withdraw_detail set counts = ?,amount = ?,flowNo = ?,status = 1,update_uid = ? where id = ?";
        jdbcTemplate.update(sql,new Object[]{counts,money,bankNo,userId,id});
        //提现清除余额
        String user = "update user_card_info set balance = 0 where id = ?";
        jdbcTemplate.update(user,new Object[]{user_id});
    }
    //拒绝提现
    public int refusedDraw(long id,long userId,String remark){
        String sql = "update user_withdraw_detail set status = 2,update_uid = ?,remark = ? where id = ?";
        return jdbcTemplate.update(sql,new Object[]{userId,remark,id});
    }

    //会员列表
    public PageInfo userList(Searchable searchable,int pageNumber,int pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        List<Map<String,Object>> list = userRechargeDetailMapper.userList(searchable);
        PageInfo page = new PageInfo(list);
        return page;
    }

    //锁定/解锁会员
    public int lock(long userId,int status){
        if(status == 1) {
            jdbcTemplate.update("DELETE FROM user_mobile_message WHERE mobile = (SELECT mobile FROM user_card_info WHERE id = ?)",new Object[]{userId});
        }
        String sql = "update user_card_info set status = ? where id = ?";
        return jdbcTemplate.update(sql,new Object[]{status,userId});
    }

    //会员信息
    public Map<String,Object> user(long userId){
        String sql = "select * from user_card_info where id = ?";
        Map<String,Object> user = jdbcTemplate.queryForMap(sql,new Object[]{userId});
        return user;
    }

    //银行列表
    public List<Map<String,Object>> bankList(){
        String sql = "select * from sys_code_table where available = 1";
        return jdbcTemplate.queryForList(sql);
    }

    //修改会员信息
    public int updateUser(UserCardInfo userCardInfo){
        String sql = "update user_card_info set account=?,customername=?,bank=?,cardnumber=?,mobile=?,status=? where id = ?";
        int i = jdbcTemplate.update(sql,new Object[]{
                userCardInfo.getAccount(),userCardInfo.getCustomername(),userCardInfo.getBank(),userCardInfo.getCardnumber(),
                userCardInfo.getMobile(),userCardInfo.getStatus(),userCardInfo.getId()
        });
        return i;
    }
}
