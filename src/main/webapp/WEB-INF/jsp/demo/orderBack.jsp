<%@page import="com.bebepay.component.util.OrderBackUtil"%>
<%@page import="com.bebepay.component.model.RespondJson"%>
<%@page import ="com.alibaba.fastjson.JSONObject" %>
<%@page import="com.bebepay.component.httpclient.HttpXmlClient"%>
<%@page import="com.bebepay.component.util.PayUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/***********订单退款 **********/
Map<String, Object> map = new HashMap<String, Object>();
//币币原订单号
map.put("orderid", "1273201540023296");
//商户原订单号
//map.put("merOrderid", "PAY201505050000000000001");
//商户出账单号
map.put("merOutOrderid", "1234567890123");
//退款金额
map.put("amount", 1);
//加密方式"0"表示RSA，"1"表示MD5
map.put("encry", "0");

OrderBackUtil util = new OrderBackUtil();
//订单退款
TreeMap<String, String> backMap = util.orderBack(map);
System.out.println("backMap==="+backMap);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
订单退款test
</body>
</html>