<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
	<title>提现,充值提现系统</title>
	<meta content="" name="description">
	<link rel="stylesheet" type="text/css" href="${path}/static/css/index.css">
	<%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
	<script type="text/javascript" src="${path}/static/script/withdraw/message.js" ></script>
</head>

<body>
<div class="child">
	<div class="cd_title">
		<img src="${path}/static/images/logo.png">
		<img src="${path}/static/images/icon.png">
		<em>自助提现</em>
		<a href="${path}/"><< 返回首页</a>
	</div>
	<div class="cd_main">
		<ul class="cd_step">
			<li class="on"><a>1</a><i></i><p>填写账户信息</p></li>
			<li class="on"><a>2</a><i></i><p>填写提现金额</p></li>
			<li class="on"><a>3</a><i></i><p>短信确认信息</p></li>
			<li><a>4</a><i></i><p>提现成功</p></li>
		</ul>
		<div class="cd_list">
			<ul class="cd_i_list cd_m_space">
				<li>
					<label>提现验证码：</label>
					<input type="text" class="cd_i_code" maxlength="6" id="v_code"/>
					<a href="javascript:void(0)" id="sendMessage">获取验证码</a>
				</li>
			</ul>
			<input type="hidden" value="${account}" id="account" />
			<div class="cd_btn"><a href="javascript:void(0)" id="validateBn">确认提现</a></div>
		</div>
	</div>
</div>
</body>

</html>
