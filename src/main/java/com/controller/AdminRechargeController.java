package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.service.AdminRechargeService;
import com.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 方法描述:充值查询列表
 *
 * author 小刘
 * version v1.0
 * date 2015/12/8
 */
@RequestMapping("/admin/*")
@Controller
public class AdminRechargeController {

    @Autowired
    private AdminRechargeService adminRechargeService;

    //充值查询列表
    @RequestMapping("/rechargeList")
    public String rechargeList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
                                @RequestParam(required = false,defaultValue = "0") int type,
                                @RequestParam(required = false,defaultValue = "") String flow,
                                @RequestParam(required = false,defaultValue = "") String account,
                                @RequestParam(required = false,defaultValue = "") String name,
                                @RequestParam(required = false,defaultValue = "") String mobile,
                                @RequestParam(required = false,defaultValue = "0") Double smoney,
                                @RequestParam(required = false,defaultValue = "0") Double emoney,
                                @RequestParam(required = false,defaultValue = "") String startTime,
                                @RequestParam(required = false,defaultValue = "") String endTime,
                               HttpServletRequest request){
        Searchable searchable = new Searchable();
        if(type > 0){
            searchable.addCondition(new Condition("recType", SearchOperator.eq, type==1?"A01":"A02"));
        }
        if(!flow.equals("")){
            searchable.addCondition(new Condition("flowNo", SearchOperator.eq, flow));
        }
        if(!account.equals("")){
            searchable.addCondition(new Condition("cardnumber", SearchOperator.eq, account));
        }
        if(!name.equals("")){
            searchable.addCondition(new Condition("customername", SearchOperator.eq, name));
        }
        if(!mobile.equals("")){
            searchable.addCondition(new Condition("mobile", SearchOperator.eq, mobile));
        }
        if(smoney > 0){
            searchable.addCondition(new Condition("amount", SearchOperator.gte, smoney));
        }
        if(emoney > 0){
            searchable.addCondition(new Condition("amount", SearchOperator.lte, emoney));
        }
        if(!startTime.equals("")){
            searchable.addCondition(new Condition("recTime", SearchOperator.gte, startTime));
        }
        if(!endTime.equals("")){
            searchable.addCondition(new Condition("recTime", SearchOperator.lte, endTime));
        }
        PageInfo pageInfo = adminRechargeService.rechargeList(searchable,pageNumber,20);
        request.setAttribute("page",pageInfo);
        request.setAttribute("type",type);
        request.setAttribute("flow",flow);
        request.setAttribute("account",account);
        request.setAttribute("name",name);
        request.setAttribute("mobile",mobile);
        request.setAttribute("smoney",smoney);
        request.setAttribute("emoney",emoney);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        return "/admin/recharge/rechargeList";
    }
}
