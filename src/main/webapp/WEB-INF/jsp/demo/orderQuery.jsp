<%@page import="com.bebepay.component.util.OrderQueryUtil"%>
<%@page import="com.bebepay.component.model.RespondJson"%>
<%@page import ="com.alibaba.fastjson.JSONObject" %>
<%@page import="com.bebepay.component.httpclient.HttpXmlClient"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/***********交易订单查询 **********/
Map<String, Object> map = new HashMap<String, Object>();
//商户订单号
//map.put("orderid", "12345677654321");
//币币订单号
map.put("bborderid", "1273178985415680");
//加密方式"0"表示RSA，"1"表示MD5
map.put("encry", "0");
OrderQueryUtil util = new OrderQueryUtil();
TreeMap<String, String> backMap = util.orderQuery(map);
System.out.println("backMaptest==="+backMap);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
test orderQuery
</body>
</html>