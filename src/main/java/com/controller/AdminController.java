package com.controller;

import com.alibaba.fastjson.JSON;
import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.model.UserCardInfo;
import com.service.UserCardInfoService;
import com.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	/**
	 * 分页查询账户信息
	 * @param pageNumber     当前页
	 * @param pageSize       每页显示条数
	 * @param account        操盘账号
     * @return
     */
	@RequestMapping(value = "/admin/cardInfo/ajax/list")
	@ResponseBody
	public Object cardInfoList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
							    @RequestParam(required = false,defaultValue = "20") int pageSize,
							    @RequestParam(required = false) String account) {

		Searchable searchable = new Searchable();
		if(!StringUtils.isEmpty(account)) {
			searchable.addCondition(new Condition("account", SearchOperator.eq, account));
		}
		PageInfo pageInfo = cardInfoService.selectCardInfoList(searchable,pageNumber,pageSize);

		return JSON.toJSONStringWithDateFormat(pageInfo, "yyyy-MM-dd HH:mm:ss");
	}
}
