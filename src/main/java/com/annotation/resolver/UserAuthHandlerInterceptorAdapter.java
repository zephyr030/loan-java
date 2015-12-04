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
                //在这里实现自己的权限验证逻辑
                if(userRole.validate()) {//如果验证成功返回true（这里直接写false来模拟验证失败的处理）
                	
                    return true;
                } else {//如果验证失败
                	//返回到登录界面
                	return true;
                }       
            }
        }
        else
            return true;
	}
}
