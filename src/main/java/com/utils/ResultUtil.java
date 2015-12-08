package com.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * APP参数返回
 * Created by lzh on 2015/7/10.
 */
public class ResultUtil {

    //实例化时间戳GSON
    public static Gson create() {
        GsonBuilder gb = new GsonBuilder();
        //gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        //gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
        Gson gson = gb.create();
        return gson;
    }

    //实例化GSON
    private static Gson gson = create();

    /**
     * 实例化接口数据
     * @return
     */
    public static Map<String,Object> result(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",0);
        map.put("data","");
        map.put("msg","数据请求成功");
        return map;
    }

    /**
     * 对象转JSON
     * @param object
     * @return
     */
    public static String toJSON(Object object){
        return gson.toJson(object);
    }

    /**
     * JSON转对象
     * @param json
     * @return
     */
    public static Map<String,String> fromJSON(String json){
        Map<String,String> map = parseData(json);
        return map;
    }

    /**
     * 字符串实例化Map对象
     * 函数描述: 将json字符串转换为map
     * @param data
     * @return
     */
    private static Map<String, String> parseData(String data){
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
        return map;
    }
}
