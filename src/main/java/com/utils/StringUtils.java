package com.utils;

import java.util.Random;
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

    /**
     * 设置掩码
     * @param mask     掩码字段
     * @param start    掩码起始位置
     * @param end      掩码结束位置
     * @return
     */
    public static String maskString(String value, int start, int end, String mask) {
        if(isEmpty(value)) {
            return "";
        }
        if(start < 0 ) {
            start = 0;
        }
        if(value.length() < start) {
            return "";
        }
        if(value.length() < end) {
            end = value.length();
        }
        if(end <= start) {
            return "";
        }
        if(isEmpty(mask)) {
            mask = "*";
        }

        StringBuffer before = new StringBuffer(value.substring(0,start));
        String last = value.substring(end,value.length());
        for(int i=start;i<end;i++) {
            before.append(mask);
        }
        return before.append(last).toString();
    }

    /**
     * 生成随机盐
     * @param length  长度
     * @param type    类型  1 纯数字 ， 2字母加数字组合
     * @return
     */
    public static String getSalt(int length ,int type) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        if(type == 1) {
            for(int i=0;i<length;i++) {
                int r = random.nextInt(10);
                sb.append(r);
            }
        }else {
            String base = "abcdefghijklmnopqrstuvwxyz0123456789";
            for(int i=0;i<length;i++) {
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }
        }

        return sb.toString();
    }

    public static void main(String args[]) {
        String mobile = "12999999999";
        //System.out.print(isMobile(mobile));
        String mask = maskString(mobile,-1,12,"*");
        System.out.println(mask);
    }
}
