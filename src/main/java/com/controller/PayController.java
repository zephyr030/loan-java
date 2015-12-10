package com.controller;

import com.alibaba.fastjson.JSON;
import com.bebepay.component.encrypt.AES;
import com.bebepay.component.encrypt.EncryUtil;
import com.bebepay.component.encrypt.RSA;
import com.bebepay.component.util.PayUtil;
import com.dao.SysTableCodeMapper;
import com.model.SysTableCode;
import com.model.UserCardInfo;
import com.service.BBPayApiService;
import com.service.UserCardInfoService;
import com.service.UserRechargeService;
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
    @Autowired
    private SysTableCodeMapper codeMapper;

    //获取配置文件payapi/properties
    private ResourceBundle resb = ResourceBundle.getBundle("bbpayapi");
    //币币支付请求地址
    private String payUrl = resb.getString("payUrl");
    //币币退款请求地址
    private String payOutUrl = resb.getString("orderBackUrl");
    //币币对账单下载请求地址
    private String fundsSettle = resb.getString("fundsSettleUrl");
    // 商户账户编号
    private String merchantAccount = resb.getString("merchantAccount");
    // 从配置文件读取币币的公钥
    private String bbPublicKey = resb.getString("bebepayPublicKey");
    // 从配置文件读取商户自己的私钥
    private String merchantPrivateKey = resb.getString("merchantPrivateKey");
    //md5key
    private String md5key = resb.getString("md5key");

    @RequestMapping(value = "pay/view", method = RequestMethod.GET)
    public String payView(@RequestParam(value = "account") String account,
                          @RequestParam(value = "amount") String amount,
                          @RequestParam(value = "detailId") String detailId,
                          HttpServletRequest request) throws Exception {
        UserCardInfo userCardInfo = userCardInfoService.getUserCardInfoByAccount(account, -1);
        SysTableCode sysTableCode = codeMapper.selectByPrimaryKey(userCardInfo.getBank());
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("service", payUrl);
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
        map.put("areturl", resb.getString("asynreturnURL"));         //商户后台回调地址
        map.put("sreturl", resb.getString("syncreturnURL"));         //商户前台回调地址
        map.put("pnc", sysTableCode.getStrval());             //支付节点编码
        map.put("userip", getIpAddr(request));           //用户IP
        map.put("userua", request.getHeader("user-agent"));           //终端UA
        map.put("merrmk", detailId);                //充值记录ID
        map.put("encry", "0");                      //加密方式字符 0 表示 RSA+AES,1 表示 MD5加密
        bbPayApiService.putOrderID(map, detailId);             // 订单号

        PayUtil bbUtil = new PayUtil();
        //调用币币提供的工具包里面的方法，进行组装，加签，加密操作。
        HashMap<String, String> map1 = bbUtil.assemble(map);
        String data = map1.get("data");
        String merchantaccount = map1.get("merchantaccount");
        String payUrl = map1.get("payUrl");
        String encryptkey = map1.get("encryptkey");

        TreeMap<String, String> params = new TreeMap<String, String>();

        params.put("merchantaccount",  merchantaccount);
        params.put("data", data);
        params.put("encryptkey", encryptkey);

        request.setAttribute("url",  payUrl);
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
        TreeMap<String, String> map = null;
        if("1".equals(encryptkey)) {
            map = EncryUtil.checkMD5Sign(data,md5key);
        } else {
            map = EncryUtil.checkDecryptAndSign(data, encryptkey, bbPublicKey, merchantPrivateKey);
        }
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
