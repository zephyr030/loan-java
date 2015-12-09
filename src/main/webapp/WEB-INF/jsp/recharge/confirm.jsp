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
	<div class="cg_l_title"><p>充值提示</p><a href=""><img src="${path}/static/images/icon_03.png"></a></div>
	<div class="cg_l_ctn">
		<img src="${path}/static/images/icon_04.png">
		<p>请您在新打开的网上银行<br>页面完成付款。<br>充值遇到问题请咨询客服：<br>400-020-0158</p>
		<div class="cg_l_btn">
			<a href="" class="cm_m_up">返回修改</a>
			<a href="" class="cm_m_down">已完成支付</a>
		</div>
	</div>

</div>
<div class="child">
	<div class="cd_title">
		<img src="${path}/static/images/logo.png">
		<img src="${path}/static/images/icon.png">
		<em>自助充值</em>
		<a href="${path}/static/"><< 返回首页</a>
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
				<li><label>充值渠道：</label><span>${recType}</span></li>
				<li><label>开户姓名：</label><span>${cardInfo.customername}</span></li>
				<li><label>开户银行：</label><span>${cardInfo.bankname}</span></li>
				<li><label>银行卡号：</label><span>${cardInfo.cardnumber}</span></li>
				<li class="cd_c_space"><label>手机号码：</label><span>${cardInfo.mobile}</span></li>
				<li><input type="checkbox"><span>我已经阅读并完全同意<a href="#"  class="link">《充值须知》</a></span></li>
			</ul>
			<div class="cd_m_btn"><a href="javascript:void(0);" class="cm_m_up" id="backBn">上一步</a><a href="${path}/user/recharge/confirm/info?account=${cardInfo.account}&recType=${recType}" class="cm_m_down" id="nextBn" target="_blank">下一步</a></div>
		</div>
	</div>
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#nextBn").click(function() {
			$("#cg_mask").show();
			$("#cg_layer").show();
		});
		$("#backBn").click(goBack);
	});

//	function confirmInfo() {
//		var account = $("#account").val();
//
//		$.ajax({
//			cache: false,
//			async: false,
//			type: "GET",
//			dataType: "json",
//			url:path + "/user/recharge/confirm/info",
//			data:{
//				account: account
//			},
//			error: function(request) {
//				alert("Connection error");
//			},
//			success: function(data) {
//				if(!data.success) {
//					alert(data.message);
//				}else {
//					$("#confirmForm").submit();
//				}
//			}
//		});
//	}

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
-->