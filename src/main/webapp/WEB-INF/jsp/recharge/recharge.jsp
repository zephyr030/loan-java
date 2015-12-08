<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>咿呀网管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="keywords" content="咿呀网,后台管理系统,开源系统,后台源码下载">
	<meta name="description" content="咿呀网后台管理系统，是使用Java平台,采用主流SpringMVC+Mybatis技术，数据库使用免费MYSQL,前端使用Jquery和Easyui框架.系统完全开源，欢迎下载">
	<%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
  </head>
  
  <body>
		<div style="left: 200px;top: 200px;border: 1px">
			超盘账号<input type="text" id="account" value="${cardInfo.account}"/><br/>
			姓名<input type="text" id="customerName"  value="${cardInfo.customername}"/><br/>
			银行卡号<input type="text" id="cardNumber"  value="${cardInfo.cardnumber}"/><br/>
			开户行<input type="text" id="bankName"  value="${cardInfo.bankname}"/><br/>
			手机号<input type="text" id="mobile"  value="${cardInfo.mobile}"/><br/>
			<input type="button" id="rechargeBn" value="充值"/><br/>
		</div>
  </body>
  <script type="text/javascript">
	  $(document).ready(function(){
		 $("#rechargeBn").click(recharge);
	  });

	  function recharge() {
		  var account = $.trim($("#account").val());
		  var customerName = $.trim($("#customerName").val());
		  var cardNumber = $.trim($("#cardNumber").val());
		  var bankName = $.trim($("#bankName").val());
		  var mobile = $.trim($("#mobile").val());

		  $.ajax({
			  cache: false,
			  async: false,
			  type: "POST",
			  dataType: "json",
			  url:path + "/user/recharge",
			  data:{
				  account: account,
				  customerName: customerName,
				  cardNumber: cardNumber,
				  bankName: bankName,
				  mobile: mobile
			  },
			  error: function(request) {
				  alert("Connection error");
			  },
			  success: function(data) {
				  if(!data.success) {
					  alert(data.message);
				  }else {
					  document.location.href = path + "/user/recharge/amount?account=" + account;
				  }
			  }
		  });
	  }
  </script>
</html>