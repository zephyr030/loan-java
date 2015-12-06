package com.utils.security;

import java.io.UnsupportedEncodingException;

public class Security {
	
	private static char[] password = null;
	
	static{
		//key需要写一个
		String key = "tdpay";
		key = MD5.md5(key);
		password = key.toCharArray();
	}
	
//	public static void init(String key){
//		key = MD5.md5(key);
//		password = key.toCharArray();
//	}
	
    public static String encrypt(String str) {
    	if(str==null || str.equals("")){
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
        }
        return null;
    }
    
    public static String encode(String str){
    	if(str==null || str.equals("")){
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
//    	String s = "{\"ConnectionLimit\":100,\"ClientName\":\"张飞测试机\",\"CurrentBandwidth\":0,\"ClientSpType\":\"0\",\"ClientIp\":\"192.168.1.86\",\"RejectUserIds\":\"10002278，10002256\",\"CurrentConnection\":0,\"City\":\"beijing\",\"BandwidthLimit\":5000,\"VipUserIds\":\"10002279,10002275\",\"MsgType\":\"init\"}";
//		System.out.println(s);
//		s = encrypt(s);
//		System.out.println(s);
//		s = decrypt(s);
//		System.out.println(s);
        System.out.println(decrypt("U1YLVlQD"));


		String a = "敌法";
		System.out.println(a.length());
	}

}
