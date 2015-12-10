package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.service.AdminRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 方法描述:用户列表
 *
 * author 小刘
 * version v1.0
 * date 2015/12/10
 */
@RequestMapping("/admin/*")
@Controller
public class AdminUserController {

    @Autowired
    private AdminRechargeService adminRechargeService;

    //会员列表
    @RequestMapping("/userList")
    public String userList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
                           @RequestParam(required = false,defaultValue = "") String account,
                           HttpServletRequest request){

        Searchable searchable = new Searchable();
        if(account != null && !account.equals("")){
            Condition con1 = new Condition("account",SearchOperator.eq,account);
            Condition con2 = new Condition(Condition.OR, "customername",SearchOperator.eq,account);
            Condition con3 = new Condition(Condition.OR, "mobile",SearchOperator.eq,account);
            searchable.addCondition(con1);
            searchable.addCondition(con2);
            searchable.addCondition(con3);
        }
        PageInfo pageInfo = adminRechargeService.userList(searchable,pageNumber,20);
        request.setAttribute("page",pageInfo);
        request.setAttribute("account",account);
        return "/admin/user/userList";
    }

    //冻结/解冻
    @RequestMapping("/lock")
    public String lock(){

        return "";
    }
}
