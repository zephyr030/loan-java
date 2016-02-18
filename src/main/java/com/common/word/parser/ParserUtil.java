package com.common.word.parser;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * 功能描述：文本解析类
 * @author zhao
 *
 */

public class ParserUtil {
	private static String charset = "GBK";
	private static Map<String,IParser> PARSER_MAP=new HashMap();
	private static String CONFIG= "parser.properties";
	static {
		   initParaserMap();   
	}
	public  static void initParaserMap() {
		   Properties props = new Properties();
		   InputStream in= null;
		   try{
	            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG);
	            props.load(in);}
	       catch(Exception e){
	    	    e.printStackTrace();
		   }
	       finally{
			   if(in!=null ){
				   try {in.close(); } 
				   catch (Exception e) {}
		        }
		   }
		   Map<String,IParser> paraserMap=new HashMap();
		   paraserMap.put(null, new IParser(){
						public String[] getFileTypes() {
							return null;
						}
						public String readText(File file, String charset) throws Exception {
							return "";
						}});
		   if(props.isEmpty()) return ;
		  
		   Iterator it=   props.keySet().iterator();
		   
		    while(it.hasNext()){
		    	 String key=(String) it.next();
		    	 String clazzStr=(String)props.get(key);
		    	 try {
					 Class clazzObj =Class.forName(clazzStr);
					 IParser parser= (IParser)clazzObj.newInstance();
					 paraserMap.put(key.toLowerCase(), parser);
				 } catch (Exception e) {
					  e.printStackTrace();
				 }
		    }   
		    ParserUtil.PARSER_MAP=paraserMap;
	}
   
    private static String parserText (File file ,String extendsName,String encoding) throws Exception{
    	 IParser parser =PARSER_MAP.get(extendsName);
    	  if(parser==null) return "";
    	  String text=parser.readText(file,encoding);
    		
    		 return text;
    }
    public static String readText(File file,String fileName,String charset)throws Exception{
		String extName = getFileType(fileName);
		return parserText (file ,extName,charset);
	}
	public static String readText(File file,String fileName)throws Exception {
		String extName = getFileType(fileName);
		return parserText (file ,extName,charset);
	}
	public static String readText(String filePath,String charset)throws Exception{
		String extName = getFileType(filePath);
		File file=new File(filePath);
		return parserText (file ,extName,charset);
	}
	public static String readText(String filePath) throws Exception {
		String extName = getFileType(filePath);
		File file=new File(filePath);
		return parserText (file ,extName,charset);
	}
    /**
     * 功能描述：获取文件类型
     * @param path
     * @return
     */
	private  static String getFileType(String path) {
		path = path.trim();
		int i = path.lastIndexOf(".");
		if (i != -1) {
			String type = path.substring(i + 1, path.length()).toLowerCase();
			return type;
		} else {
			return null;
		}
	}
	/**
	 * 判断文件是否可以接受
	 * @param file
	 * @return
	 */
	public  static boolean isAcceptability(File file)   {
		String type=getFileType(file.getName());
		type=type.toLowerCase();
		return PARSER_MAP.containsKey(type);
	}
	public  static boolean isAcceptability(String filePath)   {
		String type=getFileType(filePath);
		type=type.toLowerCase();
		return PARSER_MAP.containsKey(type);
	}
    /**
	private  String standardize(String str) {
		str = str.replaceAll("[\\r\\n]", "");
		str = str.replaceAll("维普资讯", "");
		str = str.replaceAll("http://www.cqvip.com", "");
		return str;
	}
     * @throws Exception */
	public static void main(String[] s) throws Exception{
		File file=new File("D:/桌面/惠今发标通知书0343.doc");
		System.out.println("内容："+ParserUtil.readText(file.getAbsolutePath()));
//		File[] files=file.listFiles();
//		File temp=null;
//		for(int i=0;i<files.length;i++){
//			 temp=files[i];
//			 if(temp.isDirectory()) continue;
//			 System.out.println("file："+temp.getAbsolutePath());
//			 System.out.println("内容："+ParserUtil.readText(temp.getAbsolutePath()));
//		}
	
	}
	
	
}
