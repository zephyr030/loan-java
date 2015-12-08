package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.service.AdminRechargeService;
import com.utils.Excel;
import com.utils.StringUtils;
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

    //上账查询列表
    @RequestMapping("/exportRechargeList")
    public void exportRechargeList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
                                   @RequestParam(required = false,defaultValue = "0") int type,
                                   @RequestParam(required = false,defaultValue = "") String flow,
                                   @RequestParam(required = false,defaultValue = "") String account,
                                   @RequestParam(required = false,defaultValue = "") String name,
                                   @RequestParam(required = false,defaultValue = "") String mobile,
                                   @RequestParam(required = false,defaultValue = "0") Double smoney,
                                   @RequestParam(required = false,defaultValue = "0") Double emoney,
                                   @RequestParam(required = false,defaultValue = "") String startTime,
                                   @RequestParam(required = false,defaultValue = "") String endTime,
                                   HttpServletRequest request, HttpServletResponse response)throws Exception{
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
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("编号", "编号");
        map.put("充值方式", "充值方式");
        map.put("第三方流水号", "第三方流水号");
        map.put("账号", "账号");
        map.put("姓名", "姓名");
        map.put("开户行", "开户行");
        map.put("银行账号", "银行账号");
        map.put("手机号", "手机号");
        map.put("充值金额", "充值金额");
        map.put("充值时间", "充值时间");

        Map<String,String> mapKey = new LinkedHashMap<String, String>();
        mapKey.put("id", "id");
        mapKey.put("recTypes", "recTypes");
        mapKey.put("flowNo", "flowNo");
        mapKey.put("account", "account");
        mapKey.put("customername", "customername");
        mapKey.put("bankname", "bankname");
        mapKey.put("cardnumber", "cardnumber");
        mapKey.put("mobile", "mobile");
        mapKey.put("amount", "amount");
        mapKey.put("recTime", "recTime");
        List<Map<String,Object>> list  = adminRechargeService.rechargeList(searchable);
        Excel.ExportExcel(request, response, map, mapKey,list);
    }

    //充值查询列表
    @RequestMapping("/rechargeOverList")
    public String rechargeOverList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
                               @RequestParam(required = false,defaultValue = "0") int type,
                               @RequestParam(required = false,defaultValue = "") String flow,
                               @RequestParam(required = false,defaultValue = "") String account,
                               @RequestParam(required = false,defaultValue = "") String name,
                               @RequestParam(required = false,defaultValue = "") String mobile,
                               @RequestParam(required = false,defaultValue = "0") Double smoney,
                               @RequestParam(required = false,defaultValue = "0") Double emoney,
                               @RequestParam(required = false,defaultValue = "") String startTime,
                               @RequestParam(required = false,defaultValue = "") String endTime,
                               @RequestParam(required = false,defaultValue = "0") int status,
                               HttpServletRequest request){
        Searchable searchable = new Searchable();
        if(type > 0){
            searchable.addCondition(new Condition("a.recType", SearchOperator.eq, type==1?"A01":"A02"));
        }
        if(!flow.equals("")){
            searchable.addCondition(new Condition("a.flowNo", SearchOperator.eq, flow));
        }
        if(!account.equals("")){
            searchable.addCondition(new Condition("c.cardnumber", SearchOperator.eq, account));
        }
        if(!name.equals("")){
            searchable.addCondition(new Condition("c.customername", SearchOperator.eq, name));
        }
        if(!mobile.equals("")){
            searchable.addCondition(new Condition("c.mobile", SearchOperator.eq, mobile));
        }
        if(smoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.gte, smoney));
        }
        if(emoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.lte, emoney));
        }
        if(!startTime.equals("")){
            searchable.addCondition(new Condition("a.recTime", SearchOperator.gte, startTime));
        }
        if(!endTime.equals("")){
            searchable.addCondition(new Condition("a.recTime", SearchOperator.lte, endTime));
        }
        if(status > 0){
            searchable.addCondition(new Condition("a.status", SearchOperator.eq, status));
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
        request.setAttribute("status",status);
        return "/admin/recharge/rechargeOverList";
    }

    //充值查询列表
    @RequestMapping("/exportRechargeOverList")
    public void exportRechargeOverList(@RequestParam(required = false,defaultValue = "1") int pageNumber,
                                   @RequestParam(required = false,defaultValue = "0") int type,
                                   @RequestParam(required = false,defaultValue = "") String flow,
                                   @RequestParam(required = false,defaultValue = "") String account,
                                   @RequestParam(required = false,defaultValue = "") String name,
                                   @RequestParam(required = false,defaultValue = "") String mobile,
                                   @RequestParam(required = false,defaultValue = "0") Double smoney,
                                   @RequestParam(required = false,defaultValue = "0") Double emoney,
                                   @RequestParam(required = false,defaultValue = "") String startTime,
                                   @RequestParam(required = false,defaultValue = "") String endTime,
                                   @RequestParam(required = false,defaultValue = "0") int status,
                                   HttpServletRequest request, HttpServletResponse response)throws Exception{
        Searchable searchable = new Searchable();
        if(type > 0){
            searchable.addCondition(new Condition("a.recType", SearchOperator.eq, type==1?"A01":"A02"));
        }
        if(!flow.equals("")){
            searchable.addCondition(new Condition("a.flowNo", SearchOperator.eq, flow));
        }
        if(!account.equals("")){
            searchable.addCondition(new Condition("c.cardnumber", SearchOperator.eq, account));
        }
        if(!name.equals("")){
            searchable.addCondition(new Condition("c.customername", SearchOperator.eq, name));
        }
        if(!mobile.equals("")){
            searchable.addCondition(new Condition("c.mobile", SearchOperator.eq, mobile));
        }
        if(smoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.gte, smoney));
        }
        if(emoney > 0){
            searchable.addCondition(new Condition("a.amount", SearchOperator.lte, emoney));
        }
        if(!startTime.equals("")){
            searchable.addCondition(new Condition("a.recTime", SearchOperator.gte, startTime));
        }
        if(!endTime.equals("")){
            searchable.addCondition(new Condition("a.recTime", SearchOperator.lte, endTime));
        }
        if(status > 0){
            searchable.addCondition(new Condition("a.status", SearchOperator.eq, status));
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("编号", "编号");
        map.put("充值方式", "充值方式");
        map.put("第三方流水号", "第三方流水号");
        map.put("账号", "账号");
        map.put("姓名", "姓名");
        map.put("开户行", "开户行");
        map.put("银行账号", "银行账号");
        map.put("手机号", "手机号");
        map.put("充值金额", "充值金额");
        map.put("充值时间", "充值时间");
        map.put("到账时间", "到账时间");
        map.put("状态", "状态");
        map.put("操作人", "操作人");

        Map<String,String> mapKey = new LinkedHashMap<String, String>();
        mapKey.put("id", "id");
        mapKey.put("recTypes", "recTypes");
        mapKey.put("flowNo", "flowNo");
        mapKey.put("account", "account");
        mapKey.put("customername", "customername");
        mapKey.put("bankname", "bankname");
        mapKey.put("cardnumber", "cardnumber");
        mapKey.put("mobile", "mobile");
        mapKey.put("amount", "amount");
        mapKey.put("recTime", "recTime");
        mapKey.put("actTime", "actTime");
        mapKey.put("statusName", "statusName");
        mapKey.put("exeUser", "exeUser");
        List<Map<String,Object>> list  = adminRechargeService.rechargeList(searchable);
        Excel.ExportExcel(request, response, map, mapKey,list);
    }
}
