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
<div class="cg_mask" style="display:none;"></div>
<div class="cg_layer" style="display:none;">
	<div class="cg_l_title"><p>充值提示</p><a href="javascript:void(0);" onclick="closwMask();"><img src="${path}/static/images/icon_03.png"></a></div>
	<div class="cg_l_ctn">
		<img src="${path}/static/images/icon_04.png">
		<p>请您在新打开的网上银行<br>页面完成付款。<br>充值遇到问题请咨询客服：<br>400-020-0158</p>
		<div class="cg_l_btn">
			<a href="${path}/user/recharge?account=${cardInfo.account}" class="cm_m_up">返回修改</a>
			<a href="${path}/user/recharge/success?account=${cardInfo.account}" class="cm_m_down">已完成支付</a>
		</div>
	</div>
</div>
<div class="child">
	<div class="cd_title">
		<img src="${path}/static/images/logo.png">
		<img src="${path}/static/images/icon.png">
		<em>自助充值</em>
		<a href="${path}"><< 返回首页</a>
	</div>
	<div class="cd_main">
		<ul class="cd_step cd_g_step">
			<li class="on"><a href="">1</a><i></i><p>填写账户信息</p></li>
			<li class="on"><a href="">2</a><i></i><p>填写充值金额</p></li>
			<li class="on"><a href="">3</a><i></i><p>确认充值信息</p></li>
			<li><a href="">4</a><i></i><p>前往充值</p></li>
			<li><a href="">5</a><i></i><p>充值成功</p></li>
		</ul>
		<div class="cd_list">
			<ul class="cd_m_list">
				<li><label>操盘账户：</label><span>${cardInfo.account}</span></li>
				<li class="cd_c_space"><label>充值金额：</label><a href="#">￥${amount}</a></li>
				<li><label>充值渠道：</label><span><c:if test="${'A01' eq recType}">网银充值</c:if><c:if test="${'A02' eq recType}">银行转账</c:if></span></li>
				<li><label>开户姓名：</label><span>${cardInfo.customername}</span></li>
				<li><label>开户银行：</label><span>${cardInfo.banknameStr}</span></li>
				<li><label>银行卡号：</label><span>${cardInfo.cardnumber}</span></li>
				<li class="cd_c_space"><label>手机号码：</label><span>${cardInfo.mobile}</span></li>
				<li><input type="checkbox" checked><span>我已经阅读并完全同意<a href="#" class="link">《充值须知》</a></span></li>
			</ul>
			<div class="cd_m_btn"><a href="javascript:history.go(-1);" class="cm_m_up" id="backBn">上一步</a><a href="${path}/user/recharge/confirm/info?account=${cardInfo.account}&recType=${recType}&amount=${amount}" class="cm_m_down" id="nextBn" <c:if test="${'A01' eq recType}">target="_blank"</c:if> onclick="return confirm();">下一步</a></div>
		</div>
	</div>
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#backBn").click(goBack);
	});

	function confirm() {
		var check = $("input[type='checkbox']:checkbox:checked").length;
		if(check == 0) {
			alert("请勾选充值须知");
			return false;
		}
		if('A01' == '${recType}') {
			$(".cg_mask").show();
			$(".cg_layer").show();
		}
		return true;
	}

	function closwMask() {
		$(".cg_mask").hide();
		$(".cg_layer").hide();
	}
</script>
</html>