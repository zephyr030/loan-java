package com.annotation.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.annotation.model.UserRole;

public class UserAuthHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			UserRole userRole = ((HandlerMethod) handler).getMethodAnnotation(UserRole.class);
            
            //没有声明需要权限,或者声明不验证权限
            if(userRole == null || userRole.validate() == false) {
                return true;
            } else {
                Object obj = request.getSession().getAttribute("token");
                System.out.println("++++++++++++++++++++++++" + obj);
                if(null == obj) {
                    response.sendRedirect("/admin/login");
                    return false;
                } else {
                    return true;
                }
            }
        }
        else
            return true;
	}
}
