package com.controller;

import com.alibaba.fastjson.JSON;
import com.model.UserCardInfo;
import com.model.UserRechargeDetail;
import com.pay.AES;
import com.pay.EncryUtil;
import com.pay.RSA;
import com.service.BBPayApiService;
import com.service.UserCardInfoService;
import com.service.UserRechargeService;
import com.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Created by Administrator on 2015/12/8.
 */
@Controller
public class PayController extends BaseController {
    @Autowired
    private UserCardInfoService userCardInfoService;
    @Autowired
    private BBPayApiService bbPayApiService;
    @Autowired
    private UserRechargeService rechargeService;

    private ResourceBundle resb = ResourceBundle.getBundle("payapi");
    //获取公钥和私钥
    String merchantPrivateKey = resb.getString("payapi.merchant_privatekey");
    String bbPublicKey = resb.getString("payapi.bb_publickey");

    @RequestMapping(value = "pay/view", method = RequestMethod.GET)
    public String payView(@RequestParam(value = "account") String account,
                          @RequestParam(value = "amount") String amount,
                          @RequestParam(value = "detailId") String detailId,
                          HttpServletRequest request) throws Exception {
        UserCardInfo userCardInfo = userCardInfoService.getUserCardInfoByAccount(account, -1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("service", resb.getString("payapi.payUrl"));
        map.put("pt", "02");  //02是网上银行
        map.put("transtime", System.currentTimeMillis() + ""); //交易时间
        map.put("currency", "1");  //交易币种
        map.put("amount", amount);    //交易金额
        map.put("productcategory", "1");  //商品种类
        map.put("productname", "居间币充值");      //商品名称
        map.put("productdesc", "居间币充值");      //商品描述
        map.put("productprice", amount);     //商品单价
        map.put("productcount", "1");     //商品数量
        map.put("merrmk", "");           //商户备注信息
        map.put("identityid", account);       //用户标示
        map.put("identitytype", "2");     //用户标示类型
        map.put("areturl", resb.getString("payapi.asynreturnURL"));         //商户后台回调地址
        map.put("sreturl", resb.getString("payapi.syncreturnURL"));         //商户前台回调地址
        map.put("pnc", userCardInfo.getBankid());             //支付节点编码
        map.put("userip", getIpAddr(request));           //用户IP
        map.put("userua", request.getHeader("user-agent"));           //终端UA
        map.put("merrmk", detailId);                //充值记录ID
        bbPayApiService.putOrderID(map, detailId);             // 订单号

        //生成AES密匙
        String merchantAesKey = RandomUtil.getRandom(16);
        // 生成data
        String info = JSON.toJSONString(map);
        String data = AES.encryptToBase64(info, merchantAesKey);
        // 使用RSA算法将商户自己随机生成的AESkey加密
        String encryptkey = RSA.encrypt(merchantAesKey, bbPublicKey);

        TreeMap<String, String> params = new TreeMap<String, String>();

        params.put("merchantaccount",  resb.getString("payapi.merchantaccount"));
        params.put("data", data);
        params.put("encryptkey", encryptkey);

        request.setAttribute("url",  resb.getString("payapi.payUrl"));
        request.setAttribute("method", "POST");
        request.setAttribute("params", params);
        return "/recharge/loading";
    }

    /**
     * 订单返回后台调用接口
     * @param data
     * @param encryptkey
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pay/callback", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String payCallBack(String data, String encryptkey, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        TreeMap<String, String> map = EncryUtil.checkDecryptAndSign(data, encryptkey, bbPublicKey, merchantPrivateKey);
        // 验签通过
        if(map != null){
            String yb_aeskey = RSA.decrypt(encryptkey,merchantPrivateKey);
            String payresult_view = AES.decryptFromBase64(data,yb_aeskey);
            Map backMap = JSON.parseObject(payresult_view, Map.class);
            String order = (String)backMap.get("order");
            String merrmk = (String)backMap.get("order");
            bbPayApiService.updateOrderRetrunState(Long.valueOf(order), payresult_view);

//            UserRechargeDetail userRechargeDetail = new UserRechargeDetail();
//            userRechargeDetail.setId(Long.valueOf(merrmk));
//            userRechargeDetail.setStatus(Integer.valueOf((String)backMap.get("status")));
//            rechargeService.save(userRechargeDetail);
            return "YES";
        }else{
            return "NO";
        }
    }

    /**
     * 订单返回前台调用接口
     * @param data
     * @param encryptkey
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pay/backView", method = {RequestMethod.GET, RequestMethod.POST})
    public String backView(String data,
                           String encryptkey,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        TreeMap<String, String> map = EncryUtil.checkDecryptAndSign(data, encryptkey, bbPublicKey, merchantPrivateKey);
        // 验签通过
        if(map != null) {
            String yb_aeskey = RSA.decrypt(encryptkey, merchantPrivateKey);
            String payresult_view = AES.decryptFromBase64(data, yb_aeskey);
            Map backMap = JSON.parseObject(payresult_view, Map.class);
            String orderNo = (String) backMap.get("merrmk");
            request.setAttribute("orderNo", orderNo);
        }
        return "redirect:/user/recharge/success";
    }
}
