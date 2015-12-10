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
	<script type="text/javascript" src="${path}/static/script/recharge/view_recharge.js" ></script>
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
			<li><a>2</a><i></i><p>填写充值金额</p></li>
			<li><a>3</a><i></i><p>确认充值信息</p></li>
			<li><a>4</a><i></i><p>前往充值</p></li>
			<li><a>5</a><i></i><p>充值成功</p></li>
		</ul>
		<div class="cd_list">
			<h2>请按要求填写以下信息：<i>（如果是第二次充值，请务必与首次资料一致）</i></h2>
			<ul class="cd_i_list">
				<li>
					<label>操盘账号：</label>
					<input type="text" id="account" value="${cardInfo.account}" maxlength="30"/>
					<em>交易软件登录时所用的账号</em>
				</li>
				<li>
					<label>开户姓名：</label>
					<input type="text" id="customerName" value="${cardInfo.customername}" max="10"/>
					<em>银行卡户主所留姓名</em>
				</li>
				<li>
					<label>开户银行：</label>
					<select id="bank">
						<c:forEach var="obj" items="${bankList}" varStatus="this">
							<option value="${obj.id}" <c:if test="${obj.id == cardInfo.bank}">selected="selected"</c:if>>${obj.text}</option>
						</c:forEach>
					</select>
					<em>交易软件登录时所用的账号</em>
				</li>
				<li>
					<label>银行卡号：</label>
					<input type="text" id="cardNumber" value="${cardInfo.cardnumber}" maxlength="30"/>
				</li>
				<li>
					<label>手机号码：</label>
					<input type="text" id="mobile"  value="${cardInfo.mobile}" max="11"/>
					<em>提现时用于收取短信的手机号，请务必真实填写</em>
				</li>
			</ul>
			<div class="cd_btn"><a href="javascript:void(0)" id="rechargeBn">开始充值</a></div>
		</div>
	</div>
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
</body>

</html>
