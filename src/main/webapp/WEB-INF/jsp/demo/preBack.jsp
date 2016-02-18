<%@page import="java.util.TreeMap"%>
<%@page import="sun.reflect.generics.tree.Tree"%>
<%@page import="com.bebepay.component.util.PayUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
System.out.println("testBack start preBack.jsp");
String data = request.getParameter("data");
String encryptkey = request.getParameter("encryptkey");
System.out.println("data==="+data);
System.out.println("encryptkey==="+encryptkey);
PayUtil pay = new PayUtil();
//验签
TreeMap<String, String> backMap = pay.check(data, encryptkey);
backMap.remove("sign");
System.out.println("backMap==="+backMap);
System.out.println("testBack end preBack.jsp");
%>
<%=backMap%>
</body>
</html>