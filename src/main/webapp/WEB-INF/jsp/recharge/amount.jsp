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
	<script type="text/javascript" src="${path}/static/script/recharge/amount.js" ></script>
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
			<li class="on"><a>1</a><i></i><p>填写账户信息</p></li>
			<li class="on"><a>2</a><i></i><p>填写充值金额</p></li>
			<li><a>3</a><i></i><p>确认充值信息</p></li>
			<li><a>4</a><i></i><p>前往充值</p></li>
			<li><a>5</a><i></i><p>充值成功</p></li>
		</ul>
		<form id="amountForm" action="${path}/user/recharge/confirm" method="POST">
		<div class="cd_list cdg_m_list">
			<input type="hidden" id="account" name="account" value="${account}">
			<ul class="cd_i_list">
				<li>
					<label>填写充值金额：</label>
					<input type="text" id="amount" name="amount" onkeydown="InputNum(event)"/>
					<em>请输入100的整数倍</em>
				</li>
				<li>
					<label>选择充值通道：</label>
					<input type="radio" class="radio" name="recType" value="A01" /><i>网银充值</i>
					<input type="radio" class="radio" name="recType" value="A02" checked="checked"/><i>银行转账</i>
				</li>
			</ul>
			<div class="cd_m_btn cdg_m_btn">
				<a href="javascript:history.go(-1);" class="cm_m_up" id="backBn">上一步</a>
				<a href="javascript:void(0)" class="cm_m_down" id="nextBn">下一步</a>
			</div>
		</div>
		</form>
	</div>
</div>
</body>

</html>
