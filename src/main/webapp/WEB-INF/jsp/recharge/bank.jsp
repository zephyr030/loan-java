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
			<li class="on"><a>1</a><i></i><p>填写账户信息</p></li>
			<li class="on"><a>2</a><i></i><p>填写充值金额</p></li>
			<li class="on"><a>3</a><i></i><p>确认充值信息</p></li>
			<li class="on"><a>4</a><i></i><p>前往充值</p></li>
			<li><a>5</a><i></i><p>充值成功</p></li>
		</ul>
		<ul class="cd_bank">
			<li>
				<img src="${path}/static/images/bank_01.png">
				<p>账号：3100 1518 0000 5003 1146<br>户名：上海信闳投资管理有限公司<br>开户行：中国建设银行上海黄浦支行</p>
			</li>
			<li>
				<img src="${path}/static/images/bank_02.png">
				<p>账号：1001 2689 1901 6802 706<br>户名：上海信闳投资管理有限公司<br>开户行：中国工商银行上海市金陵东路支行</p>
			</li>
			<li>
				<img src="${path}/static/images/bank_03.png">
				<p>账号：0335 7100 0400 11335<br>户名：上海信闳投资管理有限公司<br>开户行：中国农业银行上海西藏南路支行</p>
			</li>
			<li>
				<img src="${path}/static/images/bank_04.png">
				<p>账号：4572 6756 9167<br>户名：上海信闳投资管理有限公司<br>开户行：中国银行上海市大世界支行</p>
			</li>
		</ul>
		<form action="${path}/user/recharge/end" id="amountForm" method="post">
		<div class="cd_list cdg_m_list  cdg_i_list">
			<ul class="cd_i_list">
				<li>
					<label>充值银行：</label>
					<select id="bankId" name="bankId">
						<c:forEach items="${bankList}" var="obj" varStatus="this">
							<option value="${obj.typecode}" <c:if test="${obj.typecode == cardInfo.bankname}">selected="selected"</c:if>>${obj.text}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label>银行流水号：</label>
					<input type="text" id="flowNo" name="flowNo"/>
					<em>若无法查询，可留空白，但会增加到账时间</em>
					<input type="hidden" id="account" name="account" value="${account}" />
					<input type="hidden" id="amount" name="amount" value="${amount}" />
					<input type="hidden" id="recType" name="recType" value="${recType}" />
				</li>
			</ul>
			<div class="cd_m_btn"><a href="javascript:history.go(-1);" class="cm_m_up">上一步</a><a href="javascript:void(0)" class="cm_m_down" id="sureBn">下一步</a></div>
		</div>
		</form>
	</div>
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#sureBn").click(recharge);

	});

	function recharge() {
		$("#amountForm").submit();
	}

</script>
</html>