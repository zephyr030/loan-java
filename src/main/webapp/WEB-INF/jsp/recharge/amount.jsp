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
  	<form action="${path}/user/recharge/confirm" id="amountForm" method="post">
		<div style="left: 200px;top: 200px;border: 1px">
			<input type="hidden" id="account" name="account" value="${account}"/><br/>
			请填写充值金额：<input type="text" id="amount" name="amount"/><br/>
			请选择充值通道： <input type="radio" name="recType" value="A01" />网银充值
			<input type="radio" name="recType" value="A02" checked="checked"/>银行转账<br/><br/>

			&nbsp;&nbsp;<input type="button" id="nextBn" value="下一步"/>&nbsp;
			<input type="button" id="backBn" value="上一步"/>&nbsp;&nbsp;&nbsp;
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