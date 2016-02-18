package com.controller;

import com.alibaba.fastjson.JSON;
import com.bebepay.component.encrypt.AES;
import com.bebepay.component.encrypt.EncryUtil;
import com.bebepay.component.encrypt.RSA;
import com.bebepay.component.util.PayUtil;
import com.dao.SysTableCodeMapper;
import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.model.SysTableCode;
import com.model.UserCardInfo;
import com.model.UserRechargeDetail;
import com.service.BBPayApiService;
import com.service.UserCardInfoService;
import com.service.UserRechargeService;
import com.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
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
//        map.put("pt", "02");  //02是网上银行
        map.put("transtime", System.currentTimeMillis() + ""); //交易时间
//        map.put("currency", "1");  //交易币种
        map.put("amount", Long.valueOf(amount) * 100);    //交易金额
        map.put("productcategory", "1");  //商品种类
        map.put("productname", "居间币充值");      //商品名称
        map.put("productdesc", "居间币充值");      //商品描述
        map.put("productprice", amount);     //商品单价
        map.put("productcount", "1");     //商品数量
//        map.put("identityid", account);       //用户标示
//        map.put("identitytype", "2");     //用户标示类型
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
        Logger.info("后台返回back|" + encryptkey + "|" + data);
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
            order = order.replace("BB", "");
            String merrmk = (String)backMap.get("merrmk");
            bbPayApiService.updateOrderRetrunState(Long.valueOf(order), payresult_view);

            //更新用户充值记录中的流水号
            UserRechargeDetail userRechargeDetail = new UserRechargeDetail();
            userRechargeDetail.setId(Long.valueOf(merrmk));
            userRechargeDetail.setFlowno((String)backMap.get("bborderid"));
            rechargeService.save(userRechargeDetail);
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
        Logger.info("前台返回view|" + encryptkey + "|" + data);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        TreeMap<String, String> map = null;
        if("1".equals(encryptkey)) {
            map = EncryUtil.checkMD5Sign(data,md5key);
        } else {
            map = EncryUtil.checkDecryptAndSign(data, encryptkey, bbPublicKey, merchantPrivateKey);
        }
        // 验签通过
        if(map != null) {
            String yb_aeskey = RSA.decrypt(encryptkey, merchantPrivateKey);
            String payresult_view = AES.decryptFromBase64(data, yb_aeskey);
            Map backMap = JSON.parseObject(payresult_view, Map.class);
            String orderNo = (String) backMap.get("merrmk");
            orderNo = orderNo.replace("BB", "");
            request.setAttribute("orderNo", orderNo);

            UserRechargeDetail userRechargeDetail = rechargeService.getDetailById(Long.valueOf(orderNo));
            String user_id = userRechargeDetail.getUserId().toString();
            Searchable searchable = new Searchable();
            searchable.addCondition(new Condition("id", SearchOperator.eq, user_id));
            UserCardInfo userCardInfo = userCardInfoService.selectUserCardInfoBySearchable(searchable);
            return "redirect:/user/recharge/success?account=" + userCardInfo.getAccount();
        }
        return "redirect:/user/recharge/success";
    }

    public static void main(String [] args) {
        String bbPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCg7Wwh0LaFMABgf44w0hqciJPw8HnnEbFDJmkeBtRjQHI0bkKh2bBe42qbhFLQZMpfc8CiZnxtUEc4T0J1n6Fd9WTgbF8boKF0dfMaCyr8zbgCVElN3CHIAVYOVHFT/3zHxlwYsrHo3VjWoU8Q5+qlrZ6UsX/EXXRcrZ3eKRCilQIDAQAB";
        String data = "6sOjMilRy9gITcIcAjgJDROhR1iVfktM2QBDck9UulKrFyPvwik9AlQU8PJLJLa/Sr4Ivb4B+hHTe5B8yd3il0bRD02StpDTeJLV3PtU9MhnSLXbUg0fRw5xz4pvQe8/xRurzm/8IKaQI2Hw+LUL/0hscqweC74LVdm4X04SuSEdd61lC2VpGl6heGbPQQ7DB6/9EXAVVbtNaiVef0i/iDJpQ0N8XH2pwy+/fC0645J35BGunMftzOlkSYgqbCXEowV3K+yJzYI5h8cJdydPAoegqRt561xrUXjPOpCCxptVmPst+z5glS+M23IW2gQg/e8KOhFPhai2tQBBv1BSYqD9sAsTlah1wxZPxl5I3oYUMMLMORotY3qgCYxa3iFT8al3TZHnpbJMOFn2fAquO92+leBvDk7712PhbqvHuBagNOQv5s2ZmrNy7NKdXjxOE+lK+u6FjILN5+GkN144Ew==";
        String encryptkey = "CI8zT99dfBBks7rSC1wH+2wXChYH9XAaAs8HEbJthIgsKLEBIdvadMhz8XR2HP7EqruCyFFE4YOEdUvFVGwJoQvSCKSy/90W+g1wEu+MiMPYw1Xp+9Uk7wZfhRHQX0p/jncMRmVb1fHCuJ85PMU8RbCiYYXhOqRimwqIJH4lfB8=";
        String merchantPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAI7HBIvDBDYI60bpZ7wVVIF7L4Jwz2oEXEbkNu7XzhCovL829yoKUPBTC46mIM51Tvwbgbn3gKABVI8C/D29Tfee42m4Qnb+/dmZx34AZQUBiDmHkv71Beu3vVow/EZVcjsGssrhcM3jDUuYbGeuIWCwviqI1XD2MeLXA/M5R94LAgMBAAECgYEAindMW1bIELdZpa3aord0+xXCn5hULxcHfuD4vW2hNNrQIglmbukOUePYA4Htswxli24Jw5basVJY9VEJ275pi1RNptbqP3FK2MfC9/DCKzPzw+rBlNqBlZprrlyKd00iEZ5+icS2Tkz7nRca9/loRFgTocgtmRbjW6KgHPkEUdECQQDY5/EjCm54xPTrD/UGAKxVlG5Gno26J3eog+UTry/aoLcbeFIhxFjjOyHXiHAPu0eNFz16zQcUFCIZsFQVixSvAkEAqILG47FG9yMLzmaX38J145SkLoqsY+LwnZxURU9kcZ0FknBGMdBK3wbc3huo2WfGV5IaTdgTH0PF+90RiHnbZQJAFExYV5QhVHHyDZFXt7EWb4fNbhRmZPZOj1mQdXAehVIm8I4o+Xn7a4BcIWRmQEKhZoW3PiezsuBTdJ34sG9shwJBAKeT1mKc66vd6GHMWQCnDEHUkinOsn1rNEopKwz6VTM/Kklk6gmj0LFWy9L9wr7hmrd/jjUXynxvE7bTNBp6xJkCQQCnk6rwttHHR54reYO3ohxDB2ndbHUms4zbph0WddKgpo9bM+o/I65Ty7pB+O/QRTP5W8Pq+skgk2f/Z7N7dPr9";
        try {
            TreeMap<String, String> map = EncryUtil.checkDecryptAndSign(data, encryptkey, bbPublicKey, merchantPrivateKey);
            System.out.println(map.toString());
            String yb_aeskey = RSA.decrypt(encryptkey, merchantPrivateKey);
            String payresult_view = AES.decryptFromBase64(data, yb_aeskey);
            System.out.println(payresult_view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
