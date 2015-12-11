package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.model.SysUser;
import com.model.UserCardInfo;
import com.service.AdminRechargeService;
import com.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        if(user == null){
            return "redirect:/admin/login";
        }
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
    @ResponseBody
    public String lock(@RequestParam(required = false,defaultValue = "0") long userId,
                       @RequestParam(required = false,defaultValue = "0") int status){
        Map<String,Object> map = ResultUtil.result();
        try{
            int i = adminRechargeService.lock(userId,status);
            if(i == 0){
                map.put("code",1);
                map.put("msg","操作失败");
            }else{
                map.put("msg","操作成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",-1);
            map.put("msg","处理异常");
        }
        return ResultUtil.toJSON(map);
    }

    //会员信息
    @RequestMapping("/user")
    public String user(@RequestParam(required = false,defaultValue = "0") long userId,
                       HttpServletRequest request){
        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        if(user == null){
            return "redirect:/admin/login";
        }
        //会员信息
        Map<String,Object> userObj = adminRechargeService.user(userId);
        request.setAttribute("user",userObj);
        //银行列表
        List<Map<String,Object>> bankList = adminRechargeService.bankList();
        request.setAttribute("bankList",bankList);
        return "/admin/user/user";
    }

    //修改会员信息
    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(UserCardInfo userCardInfo){
        Map<String,Object> map = ResultUtil.result();
        try{
            int i = adminRechargeService.updateUser(userCardInfo);
            if(i > 0){
                map.put("msg","更新成功");
            }else{
                map.put("code",1);
                map.put("msg","更新失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",-1);
            map.put("msg","操作异常");
        }
        return ResultUtil.toJSON(map);
    }

}
