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
	<script type="text/javascript">

		function gotoMessage() {
			$("#confirmForm").submit();
		}
	</script>
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
			<li><a>3</a><i></i><p>短信确认信息</p></li>
			<li><a>4</a><i></i><p>提现成功</p></li>
		</ul>
		<div class="cd_list">
			<ul class="cd_m_list">
				<li><label>操盘账户：</label><span>${cardInfo.account}</span></li>
				<li class="cd_c_space"><label>提现金额：</label><a>所有金额</a></li>
				<li><label>开户姓名：</label><span>${cardInfo.customername}</span></li>
				<li><label>开户银行：</label><span>${cardInfo.banknameStr}</span></li>
				<li><label>银行卡号：</label><span>${cardInfo.maskCardnumber}</span></li>
				<li><label>手机号码：</label><span>${cardInfo.mobile}</span></li>
			</ul>
			<form action="${path}/user/withdraw/message" id="confirmForm" method="post">
				<input type="hidden" value="${cardInfo.account}" name="account" />
			</form>
			<div class="cd_m_btn">
				<a href="javascript:history.go(-1)" class="cm_m_up">上一步</a>
				<a href="javascript:gotoMessage()" class="cm_m_down">下一步</a></div>
		</div>
	</div>
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
</body>

</html>
