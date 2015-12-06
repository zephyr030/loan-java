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

		</div>
  		<table>
			<thead>
				<td width="70px">用户编号</td>
				<td width="200px">操盘账号</td>
				<td width="100px">姓名</td>
				<td width="200px">开户行</td>
				<td width="200px">银行卡号</td>
				<td width="120px">手机号</td>
				<td width="120px">账户余额</td>
				<td width="150px">创建时间</td>
			</thead>

			<tbody class="tableInfo">

			</tbody>

		</table>
  </body>
  <script type="text/javascript">
	  $(document).ready(function(){

	  });

	  function insertAmount() {
		  var account = $.trim($("#account").val());
		  var amount = $.trim($("#amount").val());
		  var recType = 'A02';

		  $.ajax({
			  cache: true,
			  type: "POST",
			  dataType: "json",
			  url:path + "/user/recharge/amount",
			  data:{
				  account: account,
				  amount: amount,
				  recType: recType
			  },
			  async: false,
			  error: function(request) {
				  alert("Connection error");
			  },
			  success: function(data) {
				  if(!data.success) {
					  alert(data.message);
				  }else {
					  alert(data.message);

				  }
			  }
		  });
	  }


	  function goBack() {
		  var account = $.trim($("#account").val());
		  var backURL = '${backURL}';
		  document.location.href = path + '/user/recharge?account=' + account;
	  }
  </script>
</html>