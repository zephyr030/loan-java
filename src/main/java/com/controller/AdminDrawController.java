package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.service.AdminRechargeService;
import com.utils.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 方法描述:提现
 * <p/>
 * author 小刘
 * version v1.0
 * date 2015/12/9
 */
@RequestMapping("/admin/*")
@Controller
public class AdminDrawController {

    @Autowired
    private AdminRechargeService adminRechargeService;

    //提现查询列表
    @RequestMapping("/drawList")
    public String drawList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
                           @RequestParam(required = false,defaultValue = "0") int type,
                           @RequestParam(required = false,defaultValue = "") String account,
                           @RequestParam(required = false,defaultValue = "") String name,
                           @RequestParam(required = false,defaultValue = "") String mobile,
                           @RequestParam(required = false,defaultValue = "0") Double smoney,
                           @RequestParam(required = false,defaultValue = "0") Double emoney,
                           @RequestParam(required = false,defaultValue = "") String startTime,
                           @RequestParam(required = false,defaultValue = "") String endTime,
                           HttpServletRequest request){
        Searchable searchable = new Searchable();
        if(!account.equals("")){
            searchable.addCondition(new Condition("b.account", SearchOperator.eq, account));
        }
        if(!name.equals("")){
            searchable.addCondition(new Condition("b.customername", SearchOperator.eq, name));
        }
        if(!mobile.equals("")){
            searchable.addCondition(new Condition("b.mobile", SearchOperator.eq, mobile));
        }
        if(smoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.gte, smoney));
        }
        if(emoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.lte, emoney));
        }
        if(!startTime.equals("")){
            searchable.addCondition(new Condition("a.drawTime", SearchOperator.gte, startTime));
        }
        if(!endTime.equals("")){
            searchable.addCondition(new Condition("a.drawTime", SearchOperator.lte, endTime));
        }
        PageInfo pageInfo = adminRechargeService.drawList(searchable,pageNumber,20);
        request.setAttribute("page",pageInfo);
        request.setAttribute("type",type);
        request.setAttribute("account",account);
        request.setAttribute("name",name);
        request.setAttribute("mobile",mobile);
        request.setAttribute("smoney",smoney);
        request.setAttribute("emoney",emoney);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        return "/admin/draw/drawList";
    }

    //导出列表
    @RequestMapping("/exportDrawList")
    public void exportDrawList(@RequestParam(required = false,defaultValue = "") String account,
                               @RequestParam(required = false,defaultValue = "") String name,
                               @RequestParam(required = false,defaultValue = "") String mobile,
                               @RequestParam(required = false,defaultValue = "0") Double smoney,
                               @RequestParam(required = false,defaultValue = "0") Double emoney,
                               @RequestParam(required = false,defaultValue = "") String startTime,
                               @RequestParam(required = false,defaultValue = "") String endTime,
                               HttpServletRequest request, HttpServletResponse response)throws Exception{
        Searchable searchable = new Searchable();
        if(!account.equals("")){
            searchable.addCondition(new Condition("b.account", SearchOperator.eq, account));
        }
        if(!name.equals("")){
            searchable.addCondition(new Condition("b.customername", SearchOperator.eq, name));
        }
        if(!mobile.equals("")){
            searchable.addCondition(new Condition("b.mobile", SearchOperator.eq, mobile));
        }
        if(smoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.gte, smoney));
        }
        if(emoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.lte, emoney));
        }
        if(!startTime.equals("")){
            searchable.addCondition(new Condition("a.drawTime", SearchOperator.gte, startTime));
        }
        if(!endTime.equals("")){
            searchable.addCondition(new Condition("a.drawTime", SearchOperator.lte, endTime));
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("编号", "编号");
        map.put("账号", "账号");
        map.put("姓名", "姓名");
        map.put("开户行", "开户行");
        map.put("银行账号", "银行账号");
        map.put("手机号", "手机号");
        map.put("提现金额", "提现金额");
        map.put("提现时间", "提现时间");

        Map<String,String> mapKey = new LinkedHashMap<String, String>();
        mapKey.put("id", "id");
        mapKey.put("account","account");
        mapKey.put("customername", "customername");
        mapKey.put("bankname", "bankname");
        mapKey.put("cardnumber", "cardnumber");
        mapKey.put("mobile", "mobile");
        mapKey.put("amount", "amount");
        mapKey.put("drawTime", "drawTime");
        List<Map<String,Object>> list  = adminRechargeService.drawList(searchable);
        Excel.ExportExcel(request, response, map, mapKey,list);
    }


    //提现放款列表
    @RequestMapping("/drawOverList")
    public String drawOverList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
                           @RequestParam(required = false,defaultValue = "0") int type,
                           @RequestParam(required = false,defaultValue = "") String account,
                           @RequestParam(required = false,defaultValue = "") String name,
                           @RequestParam(required = false,defaultValue = "") String mobile,
                           @RequestParam(required = false,defaultValue = "0") Double smoney,
                           @RequestParam(required = false,defaultValue = "0") Double emoney,
                           @RequestParam(required = false,defaultValue = "") String startTime,
                           @RequestParam(required = false,defaultValue = "") String endTime,
                           HttpServletRequest request){
        Searchable searchable = new Searchable();
        if(!account.equals("")){
            searchable.addCondition(new Condition("b.account", SearchOperator.eq, account));
        }
        if(!name.equals("")){
            searchable.addCondition(new Condition("b.customername", SearchOperator.eq, name));
        }
        if(!mobile.equals("")){
            searchable.addCondition(new Condition("b.mobile", SearchOperator.eq, mobile));
        }
        if(smoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.gte, smoney));
        }
        if(emoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.lte, emoney));
        }
        if(!startTime.equals("")){
            searchable.addCondition(new Condition("a.drawTime", SearchOperator.gte, startTime));
        }
        if(!endTime.equals("")){
            searchable.addCondition(new Condition("a.drawTime", SearchOperator.lte, endTime));
        }
        PageInfo pageInfo = adminRechargeService.drawList(searchable,pageNumber,20);
        request.setAttribute("page",pageInfo);
        request.setAttribute("type",type);
        request.setAttribute("account",account);
        request.setAttribute("name",name);
        request.setAttribute("mobile",mobile);
        request.setAttribute("smoney",smoney);
        request.setAttribute("emoney",emoney);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        return "/admin/draw/drawOverList";
    }
}
