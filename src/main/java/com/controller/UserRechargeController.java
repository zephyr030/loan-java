package com.controller;

import com.alibaba.fastjson.JSON;
import com.model.UserCardInfo;
import com.service.UserCardInfoService;
import com.utils.AjaxResponse;

import com.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户充值Controller
 */
@Controller
public class UserRechargeController extends BaseController {

	@Autowired
	private UserCardInfoService cardInfoService;

	/**
	 * 提交充值信息
	 * @param account
	 * @param customerName
	 * @param cardNumber
	 * @param bankName
	 * @param mobile
     * @param model
     * @return
     */
	@RequestMapping(value = "/user/recharge", method = RequestMethod.POST)
	@ResponseBody
	public Object recharge(@RequestParam(value="account", required=true) String account,
						    @RequestParam(value="customerName", required=true) String customerName,
						    @RequestParam(value="cardNumber", required=true) String cardNumber,
						    @RequestParam(value="bankName", required=true) String bankName,
						    @RequestParam(value="mobile", required=true) String mobile,
						    Model model) {
		AjaxResponse ajaxResponse = checkRechargeInfo(account,customerName,cardNumber,bankName,mobile);
		if(ajaxResponse != null) {
			return ajaxResponse.toJsonString();
		}
		/*
			1.首先根据用户输入的超盘账号查询是否有用户
			2.如果有用户则验证其他信息是否一致
			3.如果没有则新增客户
		 */

		UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account);
		if(cardInfo != null) {
			ajaxResponse = cardInfoService.validateCardInfo(customerName,cardNumber,bankName,mobile,cardInfo);
			if(ajaxResponse != null) {
				return ajaxResponse.toJsonString();
			}
		}else {
			//新增记录
			cardInfo = new UserCardInfo();
			cardInfo.setAccount(account);
			cardInfo.setCustomername(customerName);
			cardInfo.setCardnumber(cardNumber);
			cardInfo.setBankname(bankName);
			cardInfo.setMobile(mobile);
			cardInfoService.save(cardInfo);
		}

		return AjaxResponse.success();
	}

	/**
	 * 检查充值信息有效性
	 * @param account
	 * @param customerName
	 * @param cardNumber
	 * @param bankName
	 * @param mobile
     * @return
     */
	private AjaxResponse checkRechargeInfo(String account,
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
