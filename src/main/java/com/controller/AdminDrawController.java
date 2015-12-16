package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.model.SysUser;
import com.service.AdminRechargeService;
import com.utils.Excel;
import com.utils.ResultUtil;
import com.xuan.utils.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        if(user == null){
            return "redirect:/admin/login";
        }
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
        searchable.addCondition(new Condition("a.status", SearchOperator.eq, 0));
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
        searchable.addCondition(new Condition("a.status", SearchOperator.eq, 0));
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("编号", "编号");
        map.put("账号", "账号");
        map.put("姓名", "姓名");
        map.put("开户行", "开户行");
        map.put("银行账号", "银行账号");
        map.put("手机号", "手机号");
        map.put("提现时间", "提现时间");

        Map<String,String> mapKey = new LinkedHashMap<String, String>();
        mapKey.put("id", "id");
        mapKey.put("account","account");
        mapKey.put("customername", "customername");
        mapKey.put("bankname", "bankname");
        mapKey.put("cardnumber", "cardnumber");
        mapKey.put("mobile", "mobile");
        mapKey.put("drawTime", "drawTime");
        List<Map<String,Object>> list  = adminRechargeService.drawList(searchable);
        Excel.ExportExcel(request, response, map, mapKey,list);
    }


    //提现放款列表
    @RequestMapping("/drawOverList")
    public String drawOverList(
                           @RequestParam(required = false,defaultValue = "1") int pageNumber,
                           @RequestParam(required = false,defaultValue = "") String account,
                           @RequestParam(required = false,defaultValue = "") String name,
                           @RequestParam(required = false,defaultValue = "") String mobile,
                           @RequestParam(required = false,defaultValue = "") String flowNo,
                           @RequestParam(required = false,defaultValue = "0") int counts,
                           @RequestParam(required = false,defaultValue = "0") Double smoney,
                           @RequestParam(required = false,defaultValue = "0") Double emoney,
                           @RequestParam(required = false,defaultValue = "") String startTime,
                           @RequestParam(required = false,defaultValue = "") String endTime,
                           @RequestParam(required = false,defaultValue = "") String stTime,
                           @RequestParam(required = false,defaultValue = "") String enTime,
                           HttpServletRequest request){
        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        if(user == null){
            return "redirect:/admin/login";
        }
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
        if(!flowNo.equals("")){
            searchable.addCondition(new Condition("a.flowNo", SearchOperator.eq, flowNo));
        }
        if(counts > 0){
            searchable.addCondition(new Condition("a.counts", SearchOperator.eq, counts));
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
        if(!stTime.equals("")){
            searchable.addCondition(new Condition("a.lastUpdateTime", SearchOperator.gte, stTime));
        }
        if(!enTime.equals("")){
            searchable.addCondition(new Condition("a.lastUpdateTime", SearchOperator.lte, enTime));
        }
        searchable.addCondition(new Condition("a.status", SearchOperator.ne, "0"));
        PageInfo pageInfo = adminRechargeService.drawList(searchable,pageNumber,20);
        request.setAttribute("page",pageInfo);
        request.setAttribute("account",account);
        request.setAttribute("name",name);
        request.setAttribute("flowNo",flowNo);
        request.setAttribute("counts",counts == 0?"":counts);
        request.setAttribute("mobile",mobile);
        request.setAttribute("smoney",smoney);
        request.setAttribute("emoney",emoney);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        request.setAttribute("stTime",stTime);
        request.setAttribute("enTime",enTime);
        return "/admin/draw/drawOverList";
    }

    //导出提现放款列表
    @RequestMapping("/ExcelDrawOverList")
    public void ExcelDrawOverList(
            @RequestParam(required = false,defaultValue = "1") int pageNumber,
            @RequestParam(required = false,defaultValue = "") String account,
            @RequestParam(required = false,defaultValue = "") String name,
            @RequestParam(required = false,defaultValue = "") String mobile,
            @RequestParam(required = false,defaultValue = "") String flowNo,
            @RequestParam(required = false,defaultValue = "0") int counts,
            @RequestParam(required = false,defaultValue = "0") Double smoney,
            @RequestParam(required = false,defaultValue = "0") Double emoney,
            @RequestParam(required = false,defaultValue = "") String startTime,
            @RequestParam(required = false,defaultValue = "") String endTime,
            @RequestParam(required = false,defaultValue = "") String stTime,
            @RequestParam(required = false,defaultValue = "") String enTime,
            HttpServletRequest request,HttpServletResponse response)throws Exception{
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
        if(!flowNo.equals("")){
            searchable.addCondition(new Condition("a.flowNo", SearchOperator.eq, flowNo));
        }
        if(counts > 0){
            searchable.addCondition(new Condition("a.counts", SearchOperator.eq, counts));
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
        if(!stTime.equals("")){
            searchable.addCondition(new Condition("a.lastUpdateTime", SearchOperator.gte, stTime));
        }
        if(!enTime.equals("")){
            searchable.addCondition(new Condition("a.lastUpdateTime", SearchOperator.lte, enTime));
        }
        searchable.addCondition(new Condition("a.status", SearchOperator.ne, "0"));
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("编号", "编号");
        map.put("账号", "账号");
        map.put("姓名", "姓名");
        map.put("开户行", "开户行");
        map.put("银行账号", "银行账号");
        map.put("手机号", "手机号");
        map.put("提现金额", "提现金额");
        map.put("提现时间", "提现时间");
        map.put("操作时间", "操作时间");
        map.put("交易手数", "交易手数");
        map.put("银行单号", "银行单号");
        map.put("操作人", "操作人");
        map.put("状态", "状态");
        map.put("备注", "备注");


        Map<String,String> mapKey = new LinkedHashMap<String, String>();
        mapKey.put("id", "id");
        mapKey.put("account","account");
        mapKey.put("customername", "customername");
        mapKey.put("bankname", "bankname");
        mapKey.put("cardnumber", "cardnumber");
        mapKey.put("mobile", "mobile");
        mapKey.put("amount", "amount");
        mapKey.put("drawTime", "drawTime");
        mapKey.put("lastUpdateTime", "lastUpdateTime");
        mapKey.put("counts", "counts");
        mapKey.put("flowNo", "flowNo");
        mapKey.put("exeUser", "exeUser");
        mapKey.put("description", "description");
        mapKey.put("remark", "remark");
        List<Map<String,Object>> list  = adminRechargeService.drawList(searchable);
        Excel.ExportExcel(request, response, map, mapKey,list);
    }


    //回填充值信息
    @RequestMapping("/reloadDraw")
    @ResponseBody
    public String reloadDraw(@RequestParam(required = false,defaultValue = "0") long id,
                             @RequestParam(required = false,defaultValue = "0") int counts,
                             @RequestParam(required = false,defaultValue = "0") Double money,
                             @RequestParam(required = false,defaultValue = "") String bankno,
                             @RequestParam(required = false,defaultValue = "0") long user_id,
                             HttpServletRequest request){

        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        Map<String,Object> map = ResultUtil.result();
        try{
            if(id <= 0){
                map.put("code",1);
                map.put("msg","缺少唯一主键");
            }else if(counts == 0){
                map.put("code",2);
                map.put("msg","请输入交易手数");
            }else if(money == 0){
                map.put("code",3);
                map.put("msg","请输入提现金额");
            }else if(Validators.isBlank(bankno)){
                map.put("code",4);
                map.put("msg","请输入银行单号");
            }else{
                adminRechargeService.reloadDraw(id,counts,money,bankno,user.getId(),user_id);
                map.put("code",0);
                map.put("msg","更新成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",-1);
            map.put("msg","更新信息出现异常");
        }
        return ResultUtil.toJSON(map);
    }

    //拒绝提现
    @RequestMapping("/refusedDraw")
    @ResponseBody
    public String refusedDraw(@RequestParam(required = false,defaultValue = "0") long id,
                              @RequestParam(required = false,defaultValue = "") String remark,
                              HttpServletRequest request){
        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        Map<String,Object> map = ResultUtil.result();
        try{
            if(id <= 0){
                map.put("code",1);
                map.put("msg","缺少唯一主键");
            }else{
                int i = adminRechargeService.refusedDraw(id,user.getId(),remark);
                if(i > 0){
                    map.put("code",0);
                    map.put("msg","更新成功");
                }else{
                    map.put("code",2);
                    map.put("msg","更新失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",-1);
            map.put("msg","更新信息出现异常");
        }
        return ResultUtil.toJSON(map);
    }
}
