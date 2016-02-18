<%@page import="com.bebepay.component.httpclient.HttpXmlClient"%>
<%@page import="com.bebepay.component.util.PayUtil"%>
<%@page import="java.net.InetAddress"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	/**************************************支付 ******************************/
	Map<String, Object> map = new HashMap<String, Object>();
	int x=(int)(Math.random()*10000);
	//商户订单号
	map.put("order", ("zxtasdrefasdassdsdjasdklwedaadasdsddd3ayt1qwe"+x));
	//交易时间
	map.put("transtime", new Date().getTime() );
	//交易金额
	map.put("amount", 100);
	//商品种类
	map.put("productcategory", "1");
	//商品名称
	map.put("productname", "Fifa15");
	//商品描述
	map.put("productdesc", "Fifa15coins");
	//商品单价
	map.put("productprice", 1);
	//商品数量
	map.put("productcount", 1);
	//商户备注信息
	map.put("merrmk", "test");
	//终端UA
	map.put("userua", "test");
	//用户IP(本机)
	map.put("userip", "220.168.111.111");
	//商户后台回调地址
	map.put("areturl","http://momo.iask.in:11022/bbpay-java-demo/behBack.jsp");
	//商户前台回调地址
	map.put("sreturl","http://momo.iask.in:11022/bbpay-java-demo/preBack.jsp");
	//传入00001会到银行选择页面,50009表示中国银行,11001(手机)需要联系市场部开通
	//map.put("pnc", "30018");
	//(手机)
	map.put("pnc", "00001");
	//加密方式"0"表示RSA，"1"表示MD5
	map.put("encry", "0");


	PayUtil bbUtil = new PayUtil();
	//调用币币提供的工具包里面的方法，进行组装，加签，加密操作。
	HashMap<String, String> map1 = bbUtil.assemble(map);
	System.out.println(map1);
	String data = map1.get("data");
	String merchantaccount = map1.get("merchantaccount");
	String payUrl = map1.get("payUrl");
	String encryptkey = map1.get("encryptkey");

%>
<html>
<head>
	<title>pay demo</title>
</head>
<body>
<form action="<%=payUrl %>" method="post">
	data：<input type="text" name="data" value="<%=data %>" />
	encryptkey:<input type="text" name="encryptkey" value="<%=encryptkey %>" />
	merchantaccount：<input type="text" name="merchantaccount" value="<%=merchantaccount%>"/>
	<input type="submit" value="submit" />
</form>

</body>
</html>
