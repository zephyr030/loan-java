package com.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.utils.AjaxResponse;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSON;
import com.annotation.model.AuthUser;
import com.annotation.model.UserRole;
import com.model.SysUser;
import com.service.UserServiceI;
import com.utils.CookiesUtils;
import com.utils.PropUtils;

@Controller
public class UserController extends BaseController {
	@Autowired
	protected CacheManager cacheManager;
	
	@Autowired
	private UserServiceI userService;
	
	@RequestMapping(value = "user/{id}")
	@ResponseBody
	public String getUser(@AuthUser SysUser auth,
						  @PathVariable String id,
						  @RequestParam(value="type", required=false, defaultValue="20") String type,
						  HttpServletRequest request, 
						  HttpServletResponse response) {
//		response.setContentType("text/html;charset=UTF-8");
		System.out.println(JSON.toJSONString(auth));
		
//		Cache cache  = cacheManager.getCache("ehcache_3600s");
//		System.out.println(JSON.toJSONString(cache.get("user1_" + id).getObjectValue()));
		
		System.out.println(getIpAddr(request));
		
		System.out.println(auth.getUsername());
		com.utils.Logger.info("info go to---------------------->" + type);
		com.utils.Logger.debug("debug go to---------------------->" + type);
		System.out.println(PropUtils.getProperty("loan.warring"));
		
		return auth.getUsername();
	}
	
	@RequestMapping(value = "user2/{id}")
	public String getUser2(@AuthUser SysUser auth,
			  @PathVariable String id,
			  @RequestParam(value="type", required=false, defaultValue="20") String type,
			  Model model,
			  HttpServletRequest request, 
			  HttpServletResponse response){
		String c = CookiesUtils.getCookieValue(request, "user");
		CookiesUtils.setHttpCookie(request, response, "user", c + 1);
		model.addAttribute("user", id);
		//ModelAndView view = new ModelAndView("index", "user", id);
		return "index";
	}

	/**
	 * 进入充值页面
	 * @return
     */
	@RequestMapping(value = "/user/recharge", method = RequestMethod.GET)
	public String recharge() {

		return "/recharge/recharge";
	}

}
