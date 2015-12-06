package com.annotation.resolver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.annotation.model.AuthUser;
import com.model.SysUser;
import com.service.UserServiceI;
import com.utils.CookiesUtils;

public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Autowired
	private UserServiceI userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if(parameter.hasParameterAnnotation(AuthUser.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		String token = (String)webRequest.getAttribute("token", RequestAttributes.SCOPE_SESSION);
		String id = token.split("\\|")[1];
		SysUser user = userService.getSysUser(id);
		return user;
	}

}
