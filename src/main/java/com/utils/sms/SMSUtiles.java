package com.utils.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SMSUtiles {
    //获取配置文件sms.properties
    private static ResourceBundle resb = ResourceBundle.getBundle("sms");
    //短信地址
    private static String Url = resb.getString("sms.url");
    //短信登陆名
    private static String account = resb.getString("sms.account");
    //短信密码
    private static String password = resb.getString("sms.password");

    public static void sendSMS(String phone, String content) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        //client.getParams().setContentCharset("GBK");
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", account),
                new NameValuePair("password", password), //密码可以使用明文密码或使用32位MD5加密
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };

        method.setRequestBody(data);


        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();


            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            if(code == "2"){
                System.out.println("短信提交成功");
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
