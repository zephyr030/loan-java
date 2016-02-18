<%@page import="com.bebepay.component.util.ReturnOrderQueryUtil"%>
<%@page import="com.bebepay.component.model.RespondJson"%>
<%@page import ="com.alibaba.fastjson.JSONObject" %>
<%@page import="com.bebepay.component.httpclient.HttpXmlClient"%>
<%@page import="com.bebepay.component.util.PayUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/***********退款订单查询  **********/
Map<String, Object> map = new HashMap<String, Object>();
//商户订单号
//map.put("orderid", "1273201540023296");
map.put("orderid", "1273227314906112");
//币币订单号
map.put("bborderid", "1273227314906112");
//加密方式"0"表示RSA，"1"表示MD5
map.put("encry", "0");

ReturnOrderQueryUtil util = new ReturnOrderQueryUtil();
TreeMap<String, String> backMap = util.returnOrderQuery(map);
System.out.println("backMap==="+backMap);


%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
test
</body>
</html>