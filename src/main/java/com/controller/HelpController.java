package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/12/15.
 */
@Controller
public class HelpController extends BaseController  {

    @RequestMapping(value = "help/agree")
    public String agree() {
        return "help/agree";
    }
}
