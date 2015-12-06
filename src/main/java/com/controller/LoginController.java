package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Diablo on 2015/12/6.
 */
@Controller
public class LoginController extends BaseController  {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin() {
        return null;
    }
}
