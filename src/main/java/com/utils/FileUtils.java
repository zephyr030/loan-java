package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 文件操作的工具类
 */
public class FileUtils {
	
	/**
	 * 读取一个指定文件内容
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath){
		if(filePath==null || filePath.trim().length()==0){
			return null;
		}
		
		StringBuilder b = new StringBuilder();
		try {
			FileInputStream in = new FileInputStream(filePath);
			byte buf[] = new byte[2048];
			int len = -1;
			while( (len = in.read(buf))!=-1 ){
				String s = new String(buf,0,len);
				b.append(s);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b.toString();
	}
	
	/**
	 * 把文件内容content写到path指定的目录中
	 * @param path
	 * @param content
	 */
	public static boolean writeFile(String path,String content){
		if(path==null || path.trim().equals("") || content==null){
			return false;
		}
		try {
			FileOutputStream out = new FileOutputStream(path);
			out.write(content.getBytes());
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	/**
	 * 读取classes目录的路径
	 * @return
	 */
	public static String getClassPath(){
		String path = null;
		path = FileUtils.class.getResource("/").getPath();
		return path;
	}
	
	/**
	 * 读取classes目录的路径
	 * @return
	 */
	public static String getClassPath(String file){
		try{
			String path = null;
			path = FileUtils.class.getResource("/"+file).getPath();
			return path;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 在给出的路径中找到文件名并返回。
	 */
	public static String getFileName(String path){
		if(path==null || path.trim().equals("")) {
			return path;
		}
		
		String name = null;
		int index = path.lastIndexOf("/");
		if(index==-1){
			return path;
		}else{
			name = path.substring(index+1,path.length());
		}		
		
		return name;
	}
	
	/**
	 * 从完整pckgClass中提取class名称。
	 * @param pckg
	 * @return
	 */
	public static String getClassName(String pckgClass){
		if(pckgClass==null || pckgClass.trim().equals("")){
			return pckgClass;
		}
		int index = pckgClass.lastIndexOf('.');
		return pckgClass.substring(index+1);
	}
	
	/**
	 * 读取指定文件名的后缀。
	 * @param name
	 * @return
	 */
	public static String getSuffix(String name){
		if(name==null || name.trim().equals("")) {
			return "";
		}
		
		name = getFileName(name);
		int index = name.lastIndexOf(".");
		if(index==-1){
			return "";
		}
		
		return name.substring(index+1,name.length());		
	}
	
	/**
	 * 大写首字符
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str){
		if(str==null || str.trim().equals("")){
			return str;
		}
		
		char[] cs = str.toCharArray();
		int c = cs[0];
		if(c>=97 && c<=122){
			cs[0] = (char)(c-32);
		}
		
		return new String(cs);		
	}
	
	/**
	 * 从reader中读取全部字符
	 * @param reader
	 * @return
	 */
	public static String getStringFromReader(Reader reader){
		if(reader==null){
			return "";
		}
		
		StringBuilder buf = new StringBuilder();
		int len = -1;
		char[] c = new char[1024];
		try{
			while((len=reader.read(c))!=-1){
				buf.append(new String(c,0,len));
			}
		}catch(IOException e){
			return "";
		}
		
		return buf.toString();
	}
	/**
	 * 在classpath目录写warning信息
	 * @param warninfo
	 */
	public static void writeWarning(String warninfo){
		String folder=FileUtils.getClassPath();
		Date date=new Date();
		String today=new SimpleDateFormat("yyyyMMdd").format(date.getTime());
		String filePath=folder+"business_warning_"+today+".txt";
		File file=new File(filePath);    
		if(!file.exists())    
		{    
		    try {    
		        file.createNewFile();    
		    } catch (IOException e) {    
		        e.printStackTrace();    
		    }    
		}
		
		String now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime());
		try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(file, true);
            writer.write(now+":  "+warninfo+"\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.applyPattern("0");
		
		long l = Long.valueOf(df.format(Float.parseFloat("0.00")));
		System.out.println(l);
	}
		
}
