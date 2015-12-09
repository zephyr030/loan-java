package com.utils.security;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Security {
	
	private static char[] password = null;
	
	static{
		//key需要写一个
		String key = "tbpay";
		key = MD5.md5(key);
		password = key.toCharArray();
	}
	
//	public static void init(String key){
//		key = MD5.md5(key);
//		password = key.toCharArray();
//	}
	
    public static String encrypt(String str) {
    	if(str==null || str.trim().equals("")){
    		return "";
    	}
    	str = encode(str);
    	str = Base64.encode(str.getBytes());
    	str = str.replace("+", "(");
        str = str.replace("/", ")");
        str = str.replace("=", "@");
        str = str.replace("\n", "");
        return str;
    }

    public static String decrypt(String str)
    {
    	if(str==null || str.trim().equals("")){
    		return "";
    	}
    	str = str.replace("(", "+");
        str = str.replace(")", "/");
        str = str.replace("@", "=");
        try {
            byte re[] = Base64.decode(str);
            str = new String(re);
            str = encode(str);
            return str;
        }
        catch(UnsupportedEncodingException e)  {

        }catch (ArrayIndexOutOfBoundsException e) {

		}
        return null;
    }
    
    public static String encode(String str){
    	if(str==null || str.trim().equals("")){
    		return "";
    	}
    	int len = str.length();
    	StringBuilder re = new StringBuilder();
    	char[] cs = str.toCharArray();
    	for(int i=0,j=0;j<len;i++,j++){
    		char c = (char)(cs[j]^password[i]);
    		re.append(c);
    		if(i==31){
    			i=0;
    		}
    	}
    	
    	return re.toString();
    }


    
    public static void main(String[] args) {

	}

}
