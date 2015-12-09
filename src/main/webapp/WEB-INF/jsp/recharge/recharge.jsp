<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
	<title>充值,充值提现系统</title>
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
		<a href="${path}/"><< 返回首页</a>
	</div>
	<div class="cd_main">
		<ul class="cd_step cd_g_step">
			<li class="on"><a href="">1</a><i></i><p>填写账户信息</p></li>
			<li><a href="">2</a><i></i><p>填写充值金额</p></li>
			<li><a href="">3</a><i></i><p>确认充值信息</p></li>
			<li><a href="">4</a><i></i><p>前往充值</p></li>
			<li><a href="">5</a><i></i><p>充值成功</p></li>
		</ul>
		<div class="cd_list">
			<h2>请按要求填写以下信息：<i>（如果是第二次充值，请务必与首次资料一致）</i></h2>
			<ul class="cd_i_list">
				<li>
					<label>操盘账号：</label>
					<input type="text" id="account" value="${cardInfo.account}"/>
					<em>交易软件登录时所用的账号</em>
				</li>
				<li>
					<label>开户姓名：</label>
					<input type="text" id="customerName"  value="${cardInfo.customername}"/>
					<em>银行卡户主所留姓名</em>
				</li>
				<li>
					<label>开户银行：</label>
					<select id="bankName"  value="${cardInfo.bankname}">
						<option value="10020">中国建设银行</option>
						<option value="10018">中国工商银行</option>
						<option value="10022">中国农业银行</option>
						<option value="10009">中国银行</option>
					</select>
					<em>交易软件登录时所用的账号</em>
				</li>
				<li>
					<label>银行卡号：</label>
					<input type="text" id="cardNumber"  value="${cardInfo.cardnumber}"/>
				</li>
				<li>
					<label>手机号码：</label>
					<input type="text" id="mobile"  value="${cardInfo.mobile}"/>
					<em>提现时用于收取短信的手机号，请务必真实填写</em>
				</li>
			</ul>
			<div class="cd_btn"><a href="">开始充值</a></div>
		</div>
	</div>
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
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
-->