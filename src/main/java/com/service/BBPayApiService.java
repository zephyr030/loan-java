package com.service;

import com.alibaba.fastjson.JSON;
import com.utils.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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



    /**
     * 给MAP中加订单ID
     * @param paramMap 需要加订单ID的MAP
     * @param remark 订单备注
     */
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void putOrderID(Map<String, String> paramMap, String remark) {
        String orderID = getOrderID(paramMap.get("service")==null?" ":paramMap.get("service"), JSON.toJSONString(paramMap), remark);
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
    public String getOrderID(String interfaceName,String params,String remark) {
        String orderNo = RandomUtil.getRandom(18);

        return orderNo;
    }

    /**
     * 修改订单返回状态
     * @param orderid 订单ID
     * @param returninfo 返回信息
     */
    private void updateOrderRetrunState(long orderid,String returninfo){
        StringBuffer sql = new StringBuffer();
        sql.append("update [Wit_BaseInfo].[dbo].[wit_yjf_detail] ");
        sql.append("set state = 1 , returninfo = ? ");
        sql.append("where orderID = ? ");
        jdbcTemplate.update(sql.toString(), returninfo , orderid);
    }

    /**
     * 获得请求易极付的基础参数
     * @param service 接口名称
     * @param orderNo 订单号
     */
    public static Map<String, String> getBaseParam(String service,String orderNo){
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
