package com.controller;

import com.alibaba.fastjson.JSON;
import com.dao.SysTableCodeMapper;
import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.model.SysTableCode;
import com.model.UserCardInfo;
import com.model.UserRechargeDetail;
import com.service.SysTableCodeService;
import com.service.UserCardInfoService;
import com.service.UserRechargeService;
import com.utils.AjaxResponse;

import com.utils.NumberUtils;
import com.utils.StringUtils;
import com.utils.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户充值Controller
 */
@Controller
public class UserRechargeController extends BaseController {

	@Autowired
	private UserCardInfoService cardInfoService;

	@Autowired
	private UserRechargeService rechargeService;

	@Autowired
	private SysTableCodeService codeService;

	@Autowired
	private SysTableCodeMapper codeMapper;

	/**
	 * 进入充值页面
	 * @return
	 */
	@RequestMapping(value = "/user/recharge", method = RequestMethod.GET)
	public String recharge(@RequestParam(value="account") String account,
						    Model model) {
	//	account = Security.decrypt(account);
		if(!StringUtils.isEmpty(account)) {
			UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, -1);
			model.addAttribute("cardInfo", cardInfo);
		}
		Searchable searchable = new Searchable();
		searchable.addCondition(new Condition("typeId", SearchOperator.eq, "BANK_ID"));
		searchable.addCondition(new Condition("available", SearchOperator.eq, 1));
		List<SysTableCode> bankList = codeService.selectBankList(searchable);
		model.addAttribute("bankList", bankList);
		return "/recharge/recharge";
	}

	/**
	 * 充值第一步：提交充值信息
	 * @param account
	 * @param customerName
	 * @param cardNumber
	 * @param bank
	 * @param mobile
     * @return
     */
	@RequestMapping(value = "/user/recharge/input", method = RequestMethod.GET)
	@ResponseBody
	public Object recharge(@RequestParam(value="account", required=true) String account,
						    @RequestParam(value="customerName", required=true) String customerName,
						    @RequestParam(value="cardNumber", required=true) String cardNumber,
						    @RequestParam(value="bank", required=true) int bank,
						    @RequestParam(value="mobile", required=true) String mobile) {
		AjaxResponse ajaxResponse = checkRechargeInfo(account,customerName,cardNumber,bank + "",mobile);
		if(ajaxResponse != null) {
			return ajaxResponse.toJsonString();
		}
		/*
			1.首先根据用户输入的超盘账号查询是否有用户
			2.如果有用户则验证其他信息是否一致
			3.如果没有则新增客户
		 */
		UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, 1);
		if(cardInfo != null) {
			ajaxResponse = cardInfoService.validateCardInfo(customerName,cardNumber,bank + "",mobile,cardInfo,0);
			if(ajaxResponse != null) {
				return ajaxResponse.toJsonString();
			}
		}else {
			/* 新增记录时，考虑到用户有第一次录入未激活的情况（未激活的原因通常为输入信息有误），
			 * 所以添加前先将未激活的记录删除掉，再重新添加
			 */
			cardInfo = new UserCardInfo();
			cardInfo.setAccount(account);
			cardInfo.setCustomername(customerName);
			cardInfo.setCardnumber(cardNumber);
			cardInfo.setBank(bank);
			cardInfo.setMobile(mobile);
			cardInfo.setBalance(BigDecimal.ZERO);
			cardInfo.setStatus(0);
			cardInfoService.save(cardInfo);
		}
		//添加成功后，将account值传回到页面，用于填写金额使用
		ajaxResponse = AjaxResponse.success();
		ajaxResponse.setData(account);
		return ajaxResponse.toJsonString();
	}

	/**
	 * 进入填写充值金额页面
	 * @param account
	 * @param model
     * @return
     */
	@RequestMapping(value = "/user/recharge/amount", method = RequestMethod.GET)
	public String insertAmount(@RequestParam(value="account", required=true) String account,
							    Model model) {
		model.addAttribute("account",account);
		return "/recharge/amount";
	}

	/**
	 * 充值第二步：填写充值金额
	 * @param account
	 * @param amount
	 * @param recType
     * @return
     */
	@RequestMapping(value = "/user/recharge/amount/input", method = RequestMethod.GET)
	@ResponseBody
	public Object insertAmount(@RequestParam(value="account", required=true) String account,
							    @RequestParam(value="amount", required=true) int amount,
							    @RequestParam(value="recType", required=true) String recType) {
		UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, -1);
		if(cardInfo == null) {
			return AjaxResponse.fail("你输入的账号信息有误").toJsonString();
		}

		if(!NumberUtils.isMultiple(amount, 100)) {
			return AjaxResponse.fail("你输入的金额不是100的整数倍").toJsonString();
		}
		if(!recType.equals("A01") && !recType.equals("A02")) {
			return AjaxResponse.fail("你选择的充值方式有误").toJsonString();
		}

//		//创建新的充值申请记录
//		UserRechargeDetail rechargeDetail = new UserRechargeDetail();
//		rechargeDetail.setUserId(cardInfo.getId());
//		rechargeDetail.setAmount(new BigDecimal(amount));
//		rechargeDetail.setRectype(recType);

//		rechargeService.save(rechargeDetail);
		return AjaxResponse.success("操作成功").toJsonString();
	}


	/**
	 * 第三步：确认充值信息
	 * @param account
	 * @param amount
	 * @param recType
     * @return
     */
	@RequestMapping(value = "/user/recharge/confirm", method = RequestMethod.POST)
	public String confirmRechargeInfo(@RequestParam(value="account", required=true) String account,
									   @RequestParam(value="amount", required=true) int amount,
									   @RequestParam(value="recType", required=true) String recType,
									   Model model) {
		UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, -1);
		SysTableCode sysTableCode = codeMapper.selectByPrimaryKey(cardInfo.getBank());
		cardInfo.setBanknameStr(sysTableCode.getText());
		model.addAttribute("cardInfo", cardInfo);
		model.addAttribute("amount", amount);
		model.addAttribute("recType", recType);
		return "/recharge/confirm";
	}

	/**
	 * 用户确认充值信息
	 * @param account
	 * @return
     */
	@RequestMapping(value = "/user/recharge/confirm/info", method = RequestMethod.GET)
	public String confirmInfo(@RequestParam(value="account", required=true) String account,
							  @RequestParam(value="recType", required=true) String recType,
							  @RequestParam(value="amount", required=true) Long amount,
							  HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, -1);
		if(cardInfo == null) {
			return AjaxResponse.fail("你输入的账号信息有误").toJsonString();
		}
		//激活账户信息，激活后，用户不可以再修改
		if(cardInfo.getStatus() == 0) {
			cardInfo.setStatus(1);
			cardInfoService.updateUserCardInfo(cardInfo);
		}

		if("A01".equals(recType)) {
			//记录本次充值信息
			UserRechargeDetail detail = new UserRechargeDetail();
			detail.setUserId(cardInfo.getId());
			detail.setAmount(new BigDecimal(amount));
			detail.setRectype(recType);
			rechargeService.save(detail);
			request.getSession().setAttribute("detail_id", detail.getId());
			return "redirect:/pay/view?account=" + account + "&amount=" + amount + "&detailId=" + detail.getId();
		} else {
			return "redirect:/user/recharge/bank?account=" + account + "&amount=" + amount + "&recType=" + recType;
		}
	}

	/**
	 * 选择充值入账银行
	 * @param account
	 * @param amount
	 * @param recType
	 * @param model
     * @return
     */
	@RequestMapping(value = "/user/recharge/bank", method = RequestMethod.GET)
	public String selectBank(@RequestParam(value="account", required=true) String account,
							  @RequestParam(value="amount", required=true) int amount,
							  @RequestParam(value="recType", required=true) String recType,
							  Model model){
		model.addAttribute("account",account);
		model.addAttribute("amount",amount);
		model.addAttribute("recType",recType);

		Searchable searchable = new Searchable();
		searchable.addCondition(new Condition("typeId", SearchOperator.eq, "SYS_BANK"));
		searchable.addCondition(new Condition("available", SearchOperator.eq, 1));
		List<SysTableCode> bankList = codeService.selectBankList(searchable);
		model.addAttribute("bankList", bankList);
		return "/recharge/bank";
	}

	/**
	 * 充值最后一步：选择充值银行，填写流水号, 确认充值
	 * @return
     */
	@RequestMapping(value = "/user/recharge/end", method = RequestMethod.POST)
	public String rechargeSure(@RequestParam(value="account", required=true) String account,
							   @RequestParam(value="amount", required=true) int amount,
							   @RequestParam(value="recType", required=true) String recType,
							   @RequestParam(value="bankId", required=true) String bankId,
							   @RequestParam(value="flowNo", required=false) String flowNo,
							   HttpServletRequest request,
							   HttpServletResponse response) {

		UserCardInfo cardInfo = cardInfoService.getUserCardInfoByAccount(account, 1);
		if(cardInfo == null) {
			return AjaxResponse.fail("你输入的账号信息有误").toJsonString();
		}
		//记录本次充值信息
		UserRechargeDetail detail = new UserRechargeDetail();
		detail.setUserId(cardInfo.getId());
		detail.setAmount(new BigDecimal(amount));
		detail.setRectype(recType);
		detail.setFlowno(flowNo);
		detail.setBankId(bankId);
		rechargeService.save(detail);
		request.setAttribute("orderNo", detail.getId());
		return "/recharge/success";
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

	/**
	 * 返回成功过页面
	 * @return
     */
	@RequestMapping(value = "/user/recharge/success")
	public String success(@RequestParam(value="account", required=true) String account,
						  HttpServletRequest request) {
		String orderNo = request.getSession().getAttribute("detail_id") + "";
		request.setAttribute("orderNo", orderNo);
		return "/recharge/success";
	}

}
