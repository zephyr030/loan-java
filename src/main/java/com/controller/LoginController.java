package com.controller;

import com.annotation.model.UserRole;
import com.model.SysUser;
import com.service.UserService;
import com.utils.AjaxResponse;
import com.utils.CookiesUtils;
import com.utils.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Diablo on 2015/12/6.
 */
@Controller
public class LoginController extends BaseController  {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "admin/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String token = (String)request.getSession().getAttribute("token");
        System.out.println("token" + ":"  + token);
        request.getSession().setAttribute("token", null);
        return "/login/login";
    }

    @RequestMapping(value = "admin/login", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        System.out.println(username + ":"  + password);
        SysUser user = userService.getSysUserByName(username);
        if(null != user && user.getPassword().equals(password)) {
            String token = username + "|" + password;
            request.getSession().setAttribute("token", token);
            System.out.println("token" + ":"  + token);
            return "true";
        } else {
            return "false";
        }
    }

    @RequestMapping(value = "admin/select")
    @UserRole(validate = true)
    public String select() {
        return "/login/login";
    }

}
