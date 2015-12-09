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
		<a href="${path}/"><< 返回首页</a>
	</div>
	<div class="cd_main">
		<ul class="cd_step cd_g_step">
			<li class="on"><a href="">1</a><i></i><p>填写账户信息</p></li>
			<li class="on"><a href="">2</a><i></i><p>填写充值金额</p></li>
			<li class="on"><a href="">3</a><i></i><p>确认充值信息</p></li>
			<li class="on"><a href="">4</a><i></i><p>前往充值</p></li>
			<li><a href="">5</a><i></i><p>充值成功</p></li>
		</ul>
		<ul class="cd_bank">
			<li>
				<img src="images/bank_01.png">
				<p>账号：3100 1518 0000 5003 1146<br>户名：上海信闳投资管理有限公司<br>开户行：中国建设银行上海黄浦支行</p>
			</li>
			<li>
				<img src="images/bank_02.png">
				<p>账号：1001 2689 1901 6802 706<br>户名：上海信闳投资管理有限公司<br>开户行：中国工商银行上海市金陵东路支行</p>
			</li>
			<li>
				<img src="images/bank_03.png">
				<p>账号：0335 7100 0400 11335<br>户名：上海信闳投资管理有限公司<br>开户行：中国农业银行上海西藏南路支行</p>
			</li>
			<li>
				<img src="images/bank_04.png">
				<p>账号：4572 6756 9167<br>户名：上海信闳投资管理有限公司<br>开户行：中国银行上海市大世界支行</p>
			</li>
		</ul>
		<div class="cd_list cdg_m_list  cdg_i_list">
			<ul class="cd_i_list">
				<li>
					<label>充值银行：</label>
					<select>
						<option></option>
					</select>
				</li>
				<li>
					<label>银行流水号：</label>
					<input type="text" id="flowNo" />
					<em>若无法查询，可留空白，但会增加到账时间</em>
				</li>
			</ul>
			<div class="cd_m_btn"><a href="javascript:void(0)" class="cm_m_up">上一步</a><a href="javascript:void(0)" class="cm_m_down" id="sureBn">下一步</a></div>
		</div>
	</div>
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#sureBn").click(recharge);

	});

	function recharge() {
		var account = $.trim($("#account").val());
		var amount = $.trim($("#amount").val());
		var recType = $.trim($("#recType").val());
		var flowNo = $.trim($("#flowNo").val());
		var bankId = $.trim($("#bankId").val());

		$.ajax({
			cache: false,
			async: false,
			type: "POST",
			dataType: "json",
			url:path + "/user/recharge/end",
			data:{
				account: account,
				amount: amount,
				recType: recType,
				bankId: bankId,
				flowNo: flowNo
			},
			error: function(request) {
				alert("Connection error");
			},
			success: function(data) {
				if(!data.success) {
					alert(data.message);
				}else {
					alert("ok");
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
  	<form action="${path}/user/recharge/end" id="amountForm" method="post">
		<div style="left: 200px;top: 200px;border:solid;width: 400px">
			 充值银行：<select id="bankId">
				<option value="A001" selected="selected">中国建设银行</option>
				<option value="A002">中国工商银行</option>
				<option value="A003">中国农业银行</option>
				<option value="A004">中国银行</option>
				<option value="A005">中国民生银行</option>
			</select>
			<br/>
			银行流水号：<input type="text" id="flowNo" />

			<input type="hidden" id="account" value="${account}" />
			<input type="hidden" id="amount" value="${amount}" />
			<input type="hidden" id="recType" value="${recType}" />
			<br/>

			<input type="button" value="确认" id="sureBn"/>
		</div>

	</form>
  </body>
  <script type="text/javascript">
	  $(document).ready(function(){
		  $("#sureBn").click(recharge);

	  });

	  function recharge() {
		  var account = $.trim($("#account").val());
		  var amount = $.trim($("#amount").val());
		  var recType = $.trim($("#recType").val());
		  var flowNo = $.trim($("#flowNo").val());
		  var bankId = $.trim($("#bankId").val());

		  $.ajax({
			  cache: false,
			  async: false,
			  type: "POST",
			  dataType: "json",
			  url:path + "/user/recharge/end",
			  data:{
				  account: account,
				  amount: amount,
				  recType: recType,
				  bankId: bankId,
				  flowNo: flowNo
			  },
			  error: function(request) {
				  alert("Connection error");
			  },
			  success: function(data) {
				  if(!data.success) {
					  alert(data.message);
				  }else {
					 alert("ok");
				  }
			  }
		  });
	  }

  </script>
</html>
-->