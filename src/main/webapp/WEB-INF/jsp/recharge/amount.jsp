<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
	<title>提现,充值提现系统</title>
	<meta content="" name="description">
	<link rel="stylesheet" type="text/css" href="${path}/static/css/index.css">
	<%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
</head>

<body>
<div class="child">
	<div class="cd_title">
		<img src="${path}/static/images/logo.png">
		<img src="${path}/static/images/icon.png">
		<em>自助充值</em>
		<a href=""><< 返回首页</a>
	</div>
	<div class="cd_main">
		<ul class="cd_step cd_g_step">
			<li class="on"><a href="">1</a><i></i><p>填写账户信息</p></li>
			<li class="on"><a href="">2</a><i></i><p>填写充值金额</p></li>
			<li><a href="">3</a><i></i><p>确认充值信息</p></li>
			<li><a href="">4</a><i></i><p>前往充值</p></li>
			<li><a href="">5</a><i></i><p>充值成功</p></li>
		</ul>
		<form action="${path}/user/recharge/confirm" id="amountForm" method="post">
		<div class="cd_list cdg_m_list">
			<ul class="cd_i_list">
				<li>
					<label>填写充值金额：</label>
					<input type="hidden" id="account" name="account" value="${account}"/>
					<input type="text" id="amount" name="amount"/>
					<em>请输入100的整数倍</em>
				</li>
				<li>
					<label>选择充值通道：</label>
					<input type="radio" class="radio" name="recType" value="A01" /><i>网银充值</i>
					<input type="radio" class="radio" name="recType" value="A02" checked="checked"/><i>银行转账</i>
				</li>
			</ul>
			<div class="cd_m_btn cdg_m_btn">
				<a href="javascript:void(0)" class="cm_m_up" id="backBn">上一步</a>
				<a href="javascript:void(0)" class="cm_m_down" id="nextBn">下一步</a>
			</div>
		</div>
		</form>
	</div>
</div>
</body>

<script type="text/javascript">
	$(document).ready(function(){
		$("#nextBn").click(insertAmount);
		$("#backBn").click(goBack);
	});

	function insertAmount() {
		var account = $.trim($("#account").val());
		var amount = $.trim($("#amount").val());
		var recType = $("input[name=recType]").val();

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

<!--
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
-->