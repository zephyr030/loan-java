package com.service;

import com.alibaba.fastjson.JSON;
import com.dao.SysPayLogMapper;
import com.model.SysPayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/7.
 */
@Service
public class BBPayApiService extends BaseService {


    /* 生成订单orderId */
    //protected static final String getOrderSql = "EXEC [Wit_BaseInfo].[dbo].[dc_getOrderID] ?,?,?";

    @Autowired
    private SysPayLogMapper sysPayLogMapper;

    /**
     * 给MAP中加订单ID
     * @param paramMap 需要加订单ID的MAP
     * @param remark 订单备注
     */
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void putOrderID(Map<String, Object> paramMap, String remark) {
        String interfaceName = paramMap.get("service") == null ? " " : paramMap.get("service").toString();
        paramMap.remove("service");
        long orderID = getOrderID(interfaceName, paramMap, remark);
        paramMap.put("orderNo", String.valueOf(orderID));
    }

    /**
     * 获得订单ID 按照年月日和当天已下订单次数生成订单
     * @param interfaceName 调用的接口名
     * @param params		调用的所有参数 JSON格式
     * @param remark		备注 可空
     * @return 订单号
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public long getOrderID(String interfaceName, Map params,String remark) {
        long orderNo = System.currentTimeMillis();
        SysPayLog sysPayLog = new SysPayLog();
        sysPayLog.setOrderno(orderNo);
        sysPayLog.setType("AO");
        sysPayLog.setStatus("A0");
        sysPayLog.setService(interfaceName);
        sysPayLog.setData(JSON.toJSONString(params));
        sysPayLog.setRemark(remark);
        sysPayLogMapper.insert(sysPayLog);
        return orderNo;
    }

    /**
     * 修改订单返回状态
     * @param orderid 订单ID
     * @param returninfo 返回信息
     */
    public void updateOrderRetrunState(long orderid, String returninfo){
        StringBuffer sql = new StringBuffer();
        sql.append("update sys_pay_log ");
        sql.append("set status = 'A1' , returninfo = ? ");
        sql.append("where orderID = ? ");
        jdbcTemplate.update(sql.toString(), returninfo , orderid);
    }



    /**
     * 获得请求易极付的基础参数
     * @param service 接口名称
     * @param orderNo 订单号
     */
    public static Map<String, String> getBaseParam(String service, String orderNo){
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("service", service);
//        paramMap.put("partnerId", YjfConfig.partnerId);
//        paramMap.put("orderNo", orderNo);
//        paramMap.put("signType", YjfConfig.signType.getName());
//        paramMap.put("inputCharset", YjfConfig.inputCharset);
        return paramMap;
    }

    /**
     * 获得请求易极付的基础参数
     * @param service 接口名称
     */
    public static Map<String, String> getBaseParam(String service){
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("service", service);
//        paramMap.put("partnerId", YjfConfig.partnerId);
//        paramMap.put("signType", YjfConfig.signType.getName());
//        paramMap.put("inputCharset", YjfConfig.inputCharset);
        return paramMap;
    }

    public static void main(String args []) {
        String a = "CEsTqGVciFtwGQGPWHWJ+TxMagMrz+naA5zsP8iZBuXsFfF0RV8tz9IEq4CJzqdhifMLVYjZK7P1unKBI1xjckzX7uAFGoz9DXiE+ZKnJMycTNE4M0xm5286m4DDYQNe0sp7aryxJ70evAU5HVf4p5DcYYb8SNMyPbRZBO6ayyKilU9LOIYTqxVU62ezNKAj8td9LHIBKwnGEK90/ePyEfWkhCRFSXwgh5nZAGabXP1o94nJ5WhFYGvZn3I624IHTYDV3zRbAi48z8yUjirwdInKUn7kuzQBSbKy0vsk3LE5ysu3HLHl/96/hT6P022CbegNpwRKhQ3wyGzl5ClsOXEFzkh9j5oHFhs2I8frlvDv5JzCkSIUWrnHJC1jvzRJiV3S9/KsC2d3d+d78p+I1gRh4HKvSFpz65qipPgYKIVqxFeRh5iTdGPmAxC0akSoh5zN3obToO7Ku+FeTlcR8jSNfiz8bsfshTs8J7UgXmnzjptjuG7ZDtKM60ejj4PyHaMZp4FbRET+yLlGQVa9h29yNv/9aDRS17L6us20sTa77ndiEIlAgkWyp/hi31wUXIzNnV0FR96ZsUIN6exASVGRKGzNDFBaPdFVRBOfljUcMKQGgrA6PioY3G1hWoSJEqKD+NPf1b4juMd2ki4W6XF8eKhVCeuUwl+0HdM6C0ROmWdpTvIrxkRmO+klC/ZREI8tGZAwQYXWz6KZeCfD1VhO9lDaL5/GbRygGvlri96WL/NVBY25rYNu2WgoK0qvmr+ZpgL7IaaCXJHxcSEk8aobkrfEh8J32MDgPoiwj5nKBjg6gorflVuFzvyVSSPCf0+zI8yxOsFBnuJjiiiNdyEfBGZPdbhQUoN/e2F5KHDbroQNFbp8+GH2FvsNdoiOq2znd4LJEYPXdtQl5qPtAXbkjPlwZ6v+W7T84pXnWMTFJWFNHyoKNCOesu8/IRGJC1VpOl1IZOporQXmX40mwQ==";
        System.out.println(a.length());
    }
}
