<%@page import="com.bebepay.component.util.FundsSettleUtil"%>
<%@page import="com.bebepay.component.util.PayUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	/***********资金结算  **********/
Map<String, Object> map = new HashMap<String, Object>();
//结算类型 1201：自助结算，1302：委托结算(可以不传金额)
map.put("settle_type", "1201");
//结算金额
map.put("amount", 5);
//委托结算
//map.put("bank_config", "105653013127~|~6212263100019044747~|~周不以林~|~5~|~1~|~1281305441996801");
//自助结算
map.put("bank_config", "1281305441996801");
//加密方式"0"表示RSA，"1"表示MD5方式
map.put("encry", "0");

FundsSettleUtil util = new FundsSettleUtil();
TreeMap<String, String> backMap = util.fundsSettle(map);
System.out.println("backMaptest==="+backMap);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
资金结算test
</body>
</html>