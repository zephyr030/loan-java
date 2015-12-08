package com.controller;

import com.annotation.model.AuthUser;
import com.annotation.model.UserRole;
import com.model.SysUser;
import com.service.UserService;
import com.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Diablo on 2015/12/6.
 */
@RequestMapping("/admin/*")
@Controller
public class LoginController extends BaseController  {

    @Autowired
    private UserService userService;

    //登录-首页
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/login/login";
    }

    //验证用户名是否存在
    @RequestMapping(value = "/isUser",method = RequestMethod.POST)
    @ResponseBody
    public String isUser(@RequestParam(value = "username") String username){
        Map<String,Object> map = ResultUtil.result();
        //检测用户是否存在
        SysUser user = userService.getSysUserByName(username);
        if(user != null){
            map.put("msg",user.getUsername());
        }else{
            map.put("code",1);
            map.put("msg","用户不存在");
        }
        return ResultUtil.toJSON(map);
    }

    //登录-Login
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          HttpServletRequest request) {
        Map<String,Object> map = ResultUtil.result();
        //检测用户是否存在
        SysUser user = userService.getSysUserByName(username);
        //验证几码
        if(null != user && user.getPassword().equals(password)) {
            String token = username + "|" + user.getId();
            request.getSession().setAttribute("token", token);
            request.getSession().setAttribute("sysUser",user);
            map.put("msg","登录成功");
        } else {
            map.put("code",1);
            map.put("msg","用户名或密码错误");
        }
        return ResultUtil.toJSON(map);
    }

    //主界面
    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String main(HttpServletRequest request){
        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        if(user == null){
            return "/login/login";
        }else{
            request.setAttribute("user",user);
            return "/admin/main";
        }
    }

    //退出登录
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("sysUser");
        request.getSession().removeAttribute("token");
        return "/login/login";
    }
}
