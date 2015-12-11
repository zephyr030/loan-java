package com.controller;

import com.dao.SysTableCodeMapper;
import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.model.SysTableCode;
import com.model.UserCardInfo;
import com.model.UserMobileMessage;
import com.model.UserWithdrawDetail;
import com.service.SysTableCodeService;
import com.service.UserCardInfoService;
import com.service.UserMobileMessageService;
import com.service.UserWithdrawService;
import com.utils.AjaxResponse;
import com.utils.StringUtils;
import com.utils.security.Security;
import com.utils.sms.SMSUtiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private UserMobileMessageService messageService;

    @Autowired
    private SysTableCodeMapper codeMapper;

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
                            @RequestParam(value="bank", required=true) Integer bank,
                            @RequestParam(value="mobile", required=true) String mobile) {
        if(StringUtils.isEmpty(account) || account.length() > 30) {
            return AjaxResponse.fail("您输入的超盘账号格式有误").toJsonString();
        }

        Searchable searchable = new Searchable();
        searchable.addCondition(new Condition("account", SearchOperator.eq, account));
        searchable.addCondition(new Condition("status", SearchOperator.ne, 0));
        UserCardInfo cardInfo = cardInfoService.selectUserCardInfoBySearchable(searchable);
        if(cardInfo == null) {
            return AjaxResponse.fail("你输入的操盘账号信息有误").toJsonString();
        }

        AjaxResponse ajaxResponse = cardInfoService.validateCardInfo(customerName,cardNumber,bank,mobile,cardInfo, 4);

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
        SysTableCode sysTableCode = codeMapper.selectByPrimaryKey(cardInfo.getBank());
        cardInfo.setBanknameStr(sysTableCode.getText());
        model.addAttribute("cardInfo", cardInfo);
        return "/withdraw/confirm";
    }

    /**
     * 提现第三步：发送验证码
     * @param account
     * @param model
     * @return
     */
    @RequestMapping(value = "/message",method = RequestMethod.POST)
    public String message(@RequestParam(value="account", required=true) String account,
                           Model model) {
        model.addAttribute("account", account);
        return "/withdraw/message";
    }

    /**
     * 发送验证码
     * @param account
     * @return
     */
    @RequestMapping(value = "/message/send",method = RequestMethod.GET)
    @ResponseBody
    public Object sendMessage(@RequestParam(value="account", required=true) String account) {
        UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, 1);
        if(cardInfo == null) {
            return AjaxResponse.fail("你输入的操盘账号信息有误").toJsonString();
        }

        //查询当天验证码发送次数，如果超过5次不在发送
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Searchable searchable = new Searchable();
        searchable.addCondition(new Condition("mobile",SearchOperator.eq, cardInfo.getMobile()));
        searchable.addCondition(new Condition("date",SearchOperator.eq, simpleDateFormat.format(date)));
        searchable.addCondition(new Condition("sendType",SearchOperator.eq, 1));
        UserMobileMessage message = messageService.selectMessage(searchable);
        if(message != null && message.getTimes() >= 5) {
            return AjaxResponse.fail("当天发送次数超过5次，系统拒绝再次发送").toJsonString();
        }

        //发送6位数验证码
        String code = StringUtils.getSalt(6,2);
        if(message != null) {
            message.setCode(code);
            message.setTimes(message.getTimes() + 1);
            messageService.update(message);
        }else {
            message = new UserMobileMessage();
            message.setMobile(cardInfo.getMobile());
            message.setCode(code);
            message.setDate(date);
            messageService.save(message);
        }

        String template = SMSUtiles.getReplaceTemplate("sms.withdraw", "{code}", code);
        SMSUtiles.sendSMS(message.getMobile(), template);
        return AjaxResponse.success().toJsonString();
    }

    /**
     * 验证用户输入信息
     * @param account
     * @param code
     * @return
     */
    @RequestMapping(value = "/message/validate",method = RequestMethod.GET)
    @ResponseBody
    public Object validateMessage(@RequestParam(value="account", required=true) String account,
                                   @RequestParam(value="code", required=true) String code) {
        UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, 1);
        if(cardInfo == null) {
            return AjaxResponse.fail("你输入的操盘账号信息有误").toJsonString();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Searchable searchable = new Searchable();
        searchable.addCondition(new Condition("mobile",SearchOperator.eq, cardInfo.getMobile()));
        searchable.addCondition(new Condition("date",SearchOperator.eq, simpleDateFormat.format(date)));
        searchable.addCondition(new Condition("sendType",SearchOperator.eq, 1));
        UserMobileMessage message = messageService.selectMessage(searchable);
        if(!message.getCode().equals(code)) {
            if(message.getTimes() < 5) {
                return AjaxResponse.fail("你输入的验证码有误").toJsonString();
            }else {
                //冻结账号
                cardInfo.setStatus(2);
                cardInfoService.updateUserCardInfo(cardInfo);
                return AjaxResponse.fail("你连续5次验证码输入有误，账号将被冻结，请联系客服解禁！").toJsonString();
            }
        }

        UserWithdrawDetail detail = new UserWithdrawDetail();
        detail.setUserId(cardInfo.getId());
        detail.setAmount(cardInfo.getBalance());
        detail.setDrawtime(new Date());
        detail.setStatus(0);

        long flowNo = withdrawService.save(detail);
        AjaxResponse response =AjaxResponse.success();
        response.setData(flowNo);
        return response.toJsonString();
    }


    @RequestMapping("/success")
    public String success(@RequestParam(value="flowNo", required=true) String flowNo,
                           Model model) {
        model.addAttribute("flowNo", flowNo);
        return "/withdraw/success";
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
