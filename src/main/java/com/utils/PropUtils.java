package com.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 这个类加载了classes目下witdai.properties
 */
public class PropUtils {

	public static final Properties properties = new Properties();
	
	static{
		init();
	}
	
	public static void init(){
		try{
			String path = FileUtils.getClassPath("loan.properties");
			InputStream in = new FileInputStream(path);
			properties.load(in);
			in.close();
		}catch(Throwable e){
		}
	}
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
	
	public static int getIntProperty(String key){
		return Integer.parseInt(getProperty(key));
	}
	
}
