package com.utils;

/**
 * Created by Administrator on 2015/12/6 0006.
 */
public class NumberUtils extends org.springframework.util.NumberUtils{

    /**
     * 判断是否能被整除
     * @param value    被除数
     * @param divisor  除数
     * @return
     */
    public static boolean isMultiple(int value, int divisor) {
        //除数为0是不合法的
        if(divisor == 0 || value == 0) {
            return false;
        }
        if(value % divisor != 0) {
            return false;
        }
        return true;
    }
}
