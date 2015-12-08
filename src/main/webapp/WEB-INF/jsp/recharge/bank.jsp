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
  	<form action="${path}/user/recharge/end" id="amountForm" method="post">
		<div style="left: 200px;top: 200px;border:solid;width: 400px">
			 充值银行：<select name="bankName">
				<option value="A001" selected="selected">中国建设银行</option>
				<option value="A002">中国工商银行</option>
				<option value="A003">中国农业银行</option>
				<option value="A004">中国银行</option>
				<option value="A005">中国民生银行</option>
			</select>
			<br/>
			银行流水号：<input type="text" id="flowNo" />

			<input type="hidden" name="account" value="${account}" />
			<input type="hidden" name="amount" value="${amount}" />
			<input type="hidden" name="recType" value="${recType}" />
			<br/>
		</div>

	</form>
  </body>
  <script type="text/javascript">
	  $(document).ready(function(){
		  $("#nextBn").click(insertAmount);
		  $("#backBn").click(goBack);
	  });

	  function insertAmount() {
		  var account = $.trim($("#account").val());
		  var amount = $.trim($("#amount").val());
		  var recType = 'A02';

		  $.ajax({
			  cache: false,
			  async: false,
			  type: "POST",
			  dataType: "json",
			  url:path + "/user/recharge/amount",
			  data:{
				  account: account,
				  amount: amount,
				  recType: recType
			  },
			  error: function(request) {
				  alert("Connection error");
			  },
			  success: function(data) {
				  if(!data.success) {
					  alert(data.message);
				  }else {
					  $("#amountForm").submit();
				  }
			  }
		  });
	  }

	  function goBack() {
		  var account = $.trim($("#account").val());
		  document.location.href = path + '/user/recharge?account=' + account;
	  }
  </script>
</html>