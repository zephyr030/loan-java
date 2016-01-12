package com.controller;

import com.dao.util.Condition;
import com.dao.util.SearchOperator;
import com.dao.util.Searchable;
import com.github.pagehelper.PageInfo;
import com.model.SysUser;
import com.service.AdminSysUserService;
import com.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 方法描述:系统用户
 * <p/>
 * author 小刘
 * version v1.0
 * date 2015/12/17
 */
@RequestMapping("/admin/*")
@Controller
public class AdminSysUserController {
    @Autowired
    private AdminSysUserService adminSysUserService;

    //用户列表
    @RequestMapping("/sysUserList")
    public String sysUserList(HttpServletRequest request, @RequestParam(required = false,defaultValue = "1") int pageNumber){
        SysUser user = (SysUser) request.getSession().getAttribute("sysUser");
        if(user == null || !user.getUsername().equals("admin")){
            return "redirect:/admin/login";
        }
        Searchable searchable = new Searchable();
        searchable.addCondition(new Condition("username", SearchOperator.ne, "admin"));
        PageInfo pageInfo = adminSysUserService.userList(searchable,pageNumber,20);
        request.setAttribute("page",pageInfo);
        return "/admin/sysUser/userList";
    }

    //初始化添加会员
    @RequestMapping("/init")
    public String init(){
        return "/admin/sysUser/addUser";
    }

    //添加用户
    @RequestMapping("/addSysUser")
    @ResponseBody
    public String publishProject(@RequestParam(required = true, defaultValue = "") String username,
                                 @RequestParam(required = true, defaultValue = "") String password,
                                 @RequestParam(required = true, defaultValue = "0") int available){
        Map<String,Object> map = ResultUtil.result();
        try {
            int i = adminSysUserService.addSysUser(username,password,available);
            if(i > 0){
                map.put("code",1);
                map.put("msg","账号已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",-1);
            map.put("msg","接口请求异常");
        }
        return ResultUtil.toJSON(map);
    }

    //启用/关闭/删除
    @RequestMapping("/isUserOpen")
    public String isOpen(@RequestParam(required = true, defaultValue = "0") long userId,
                         @RequestParam(required = true, defaultValue = "0") int status){
        adminSysUserService.isOpen(userId,status);
        return "redirect:/admin/sysUserList";
    }
}
