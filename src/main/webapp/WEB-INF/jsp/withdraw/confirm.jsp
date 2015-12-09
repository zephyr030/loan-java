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
			超盘账号<input type="text" value="${cardInfo.account}" disabled="disabled"/><br/>
			姓名<input type="text" value="${cardInfo.customername}" disabled="disabled"/><br/>
			银行卡号<input type="text" value="${cardInfo.cardnumber}" disabled="disabled"/><br/>
			开户行<input type="text" value="${cardInfo.bankname}" disabled="disabled"/><br/>
			手机号<input type="text" value="${cardInfo.mobile}" disabled="disabled"/><br/>
			充值金额<input type="text" value="${amount}" disabled="disabled"/><br/>
			充值方式<input type="text" name="recType" value="${recType}" disabled="disabled"/><br/>
			<input type="button" id="nextBn" value="下一步"/> &nbsp;&nbsp;
			<input type="button" id="backBn" value="重填"/><br/>
		</div>

		<form action="${path}/user/recharge/bank" id="confirmForm" method="post">
			<input type="hidden" id="account" name="account" value="${cardInfo.account}"/>
			<input type="hidden" id="amount" name="amount" value="${amount}"/>
			<input type="hidden" id="recType" name="recType" value="${recType}"/>
		</form>
  </body>
  <script type="text/javascript">
	$(document).ready(function(){
		$("#nextBn").click(confirmInfo);
		$("#backBn").click(goBack);
	});

	function confirmInfo() {
		var account = $("#account").val();

		$.ajax({
			cache: false,
			async: false,
			type: "GET",
			dataType: "json",
			url:path + "/user/recharge/confirm/info",
			data:{
				account: account
			},
			error: function(request) {
				alert("Connection error");
			},
			success: function(data) {
				if(!data.success) {
					alert(data.message);
				}else {
					$("#confirmForm").submit();
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