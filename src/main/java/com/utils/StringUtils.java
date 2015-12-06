package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/12/6 0006.
 */
public class StringUtils extends org.springframework.util.StringUtils{

    //验证是否为空
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    //验证是否是手机号码
    public static boolean isMobile(String mobile) {
        String regExp = "^[1][3-9]{1}[0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.find();//boolean
    }

    public static void main(String args[]) {
        String mobile = "12999999999";
        System.out.print(isMobile(mobile));
    }
}
