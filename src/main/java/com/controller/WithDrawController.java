package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.model.SysTableCode;
import com.model.UserCardInfo;
import com.service.SysTableCodeService;
import com.service.UserCardInfoService;
import com.service.UserWithdrawService;
import com.utils.AjaxResponse;
import com.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户提现controller
 * Created by Administrator on 2015/12/8 0008.
 */

@Controller
@RequestMapping("/user/withdraw")
public class WithDrawController {

    @Autowired
    private UserWithdrawService withdrawService;

    @Autowired
    private SysTableCodeService codeService;

    @Autowired
    private UserCardInfoService cardInfoService;

    /**
     * 提现页面
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {

        Searchable searchable = new Searchable();
        searchable.addCondition(new Condition("typeId", SearchOperator.eq, "BANK_ID"));
        searchable.addCondition(new Condition("available", SearchOperator.eq, 1));
        List<SysTableCode> bankList = codeService.selectBankList(searchable);
        model.addAttribute("bankList", bankList);
        return "withdraw/withdraw";
    }

    /**
     * 提交提现信息
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    @ResponseBody
    public Object withdraw(@RequestParam(value="account", required=true) String account,
                            @RequestParam(value="customerName", required=true) String customerName,
                            @RequestParam(value="cardNumber", required=true) String cardNumber,
                            @RequestParam(value="bankName", required=true) String bankName,
                            @RequestParam(value="mobile", required=true) String mobile) {
        if(StringUtils.isEmpty(account) || account.length() > 30) {
            return AjaxResponse.fail("您输入的超盘账号格式有误").toJsonString();
        }

        UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, 1);
        if(cardInfo == null) {
            return AjaxResponse.fail("你输入的操盘账号信息有误").toJsonString();
        }

        AjaxResponse ajaxResponse = cardInfoService.validateCardInfo(customerName,cardNumber,bankName,mobile,cardInfo, 4);
        if(ajaxResponse != null) {
            return ajaxResponse.toJsonString();
        }
        return AjaxResponse.success().toJsonString();
    }

    /**
     * 提现第二步：确认账户信息
     * @param account
     * @return
     */
    @RequestMapping(value = "/confirm",method = RequestMethod.POST)
    public String confirmInfo(@RequestParam(value="account", required=true) String account,
                               Model model) {
        UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, 1);

        model.addAttribute("cardInfo", cardInfo);
        return "/withdraw/confirm";
    }



    /**
     * 检查提现信息有效性
     * @param account
     * @param customerName
     * @param cardNumber
     * @param bankName
     * @param mobile
     * @return
     */
    private AjaxResponse withdrawInfo(String account,
                                       String customerName,
                                       String cardNumber,
                                       String bankName,
                                       String mobile
    ) {
        AjaxResponse ajaxResponse = null;
        if(StringUtils.isEmpty(account) || account.length() > 30) {
            ajaxResponse = AjaxResponse.fail("您输入的超盘账号格式有误");
        }else if(StringUtils.isEmpty(customerName) || customerName.length() > 20) {
            ajaxResponse = AjaxResponse.fail("您输入的银行卡姓名格式有误");
        }else if(StringUtils.isEmpty(cardNumber) || cardNumber.length() > 30) {
            ajaxResponse = AjaxResponse.fail("您输入的银行卡号格式有误");
        }else if(StringUtils.isEmpty(bankName) || bankName.length() > 30) {
            ajaxResponse = AjaxResponse.fail("您输入的开户行信息格式有误");
        }else if(StringUtils.isEmpty(mobile) || !StringUtils.isMobile(mobile)) {
            ajaxResponse = AjaxResponse.fail("您输入的手机号格式有误");
        }
        return ajaxResponse;
    }
}
