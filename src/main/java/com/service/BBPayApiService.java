package com.service;

import com.alibaba.fastjson.JSON;
import com.dao.SysPayLogMapper;
import com.model.SysPayLog;
import com.pay.EncryUtil;
import com.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Created by Administrator on 2015/12/7.
 */
@Service
public class BBPayApiService extends BaseService {
    //获取配置文件payapi/properties
    private ResourceBundle resb = ResourceBundle.getBundle("payapi");
    //币币支付请求地址
    private String payUrl = resb.getString("payapi.payUrl");
    //币币退款请求地址
    private String payOutUrl = resb.getString("payapi.payOutUrl");
    //币币对账单下载请求地址
    private String fundsSettle = resb.getString("payapi.fundsSettle");

    // 商户账户编号
    private String merchantaccount = resb.getString("payapi.merchantaccount");
    // 从配置文件读取币币的公钥
    private String bbPublicKey = resb.getString("payapi.bb_publickey");

    // 从配置文件读取商户自己的私钥
    private String merchantPrivateKey = resb.getString("payapi.merchant_privatekey");

    /* 生成订单orderId */
    protected static final String getOrderSql = "EXEC [Wit_BaseInfo].[dbo].[dc_getOrderID] ?,?,?";

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
        String sign = EncryUtil.handleRSA(new TreeMap<String, Object>(paramMap), merchantPrivateKey);
        paramMap.put("sign", sign);
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
        sysPayLog.setService(interfaceName);
        sysPayLog.setData(JSON.toJSONString(params));
        sysPayLogMapper.insert(sysPayLog);
        return orderNo;
    }

    /**
     * 修改订单返回状态
     * @param orderid 订单ID
     * @param returninfo 返回信息
     */
    private void updateOrderRetrunState(long orderid, String returninfo){
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
}
