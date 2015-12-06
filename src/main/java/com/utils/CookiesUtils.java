package com.utils;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.security.Security;

public class CookiesUtils {
	/**
     * 根据cookie的名称获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null || name == null || name.length() == 0) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())){
                    //&& request.getServerName().equals(cookies[i].getDomain())) {
                return cookies[i];
            }
        }
        return null;
    }
 
    public static String getCookieValue(HttpServletRequest request, String name){
        Cookie ck = getCookie(request, name);
        if(ck!=null){
            return Security.decrypt(ck.getValue());
        }else{
            return null;
        }
    }
     
    /**
     * 删除cookie
     * @param request
     * @param response
     * @param cookie
     */
    public static void deleteCookie(HttpServletRequest request,
            HttpServletResponse response, Cookie cookie) {
        if (cookie != null) {
            cookie.setPath(getPath(request));
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
 
    /**
     * 设置cookie
     * @param request
     * @param response
     * @param name
     * @param value
     * 如果不设置时间，默认永久
     */
    public static void setCookie(HttpServletRequest request,
            HttpServletResponse response, String name, String value) {
        setCookie(request, response, name, value, false);
    }
    
    
    public static void setHttpCookie(HttpServletRequest request,
            HttpServletResponse response, String name, String value) {
    	setCookie(request, response, name, value, true);
    }
 
    /**
     * @param request
     * @param response
     * @param name
     * @param value
     * @param maxAge
     * 设置cookie，设定时间
     */
    public static void setCookie(HttpServletRequest request,
            HttpServletResponse response, String name, String value, boolean isHttpOnly, int maxAge) {
        String cookieValue = Security.encrypt(value);
        Cookie cookie = new Cookie(name, value == null ? "" : cookieValue.replaceAll("\r\n", ""));
        cookie.setHttpOnly(isHttpOnly);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getPath(request));
        response.addCookie(cookie);
    }

    /**
     * 设置cookie为session
     * @param request
     * @param response
     * @param name
     * @param value
     * @param isHttpOnly
     */
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value, boolean isHttpOnly) {
        String cookieValue = Security.encrypt(value);
        Cookie cookie = new Cookie(name, value == null ? "" : cookieValue.replaceAll("\r\n", ""));
        cookie.setHttpOnly(isHttpOnly);
        cookie.setPath(getPath(request));
        response.addCookie(cookie);
    }
 
    private static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return (path == null || path.length() == 0) ? "/" : path;
    }
    
    /**
     * 格式化header头里面的cookie字符串
     * @param cookiesString
     * @return
     */
    public static Map<String, String> parseCookiesString(String cookiesString) {
    	Map<String, String> map = new HashMap<String, String>();
    	String [] str = cookiesString.split(";");
    	for(String s : str) {
    		String[] tmp = s.split("=");
    		map.put(tmp[0].trim(), Security.decrypt(tmp[1]));
    	}
    	return map;
    }
}
