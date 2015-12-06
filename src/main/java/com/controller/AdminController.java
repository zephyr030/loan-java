package com.controller;

import com.model.UserCardInfo;
import com.service.UserCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 后台管理员Controller
 */
@Controller
public class AdminController extends BaseController {

	@Autowired
	private UserCardInfoService cardInfoService;
	/**
	 * 查询用户账户信息
	 * @return
     */
	@RequestMapping(value = "/admin/cardInfo/list")
	public String cardInfoList() {
		return "/admin/cardInfoList";
	}
}
