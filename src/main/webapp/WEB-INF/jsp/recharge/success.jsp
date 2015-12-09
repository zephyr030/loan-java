<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
    <title>提现,充值提现系统</title>
    <meta content="" name="description">
    <link rel="stylesheet" type="text/css" href="${path}/static/css/index.css">
</head>

<body>
<div class="child">
    <div class="cd_title">
        <img src="${path}/static/images/logo.png">
        <img src="${path}/static/images/icon.png">
        <em>充值成功</em>
        <a href="${path}/"><< 返回首页</a>
    </div>
    <div class="cd_main">
        <ul class="cd_step cd_g_step">
            <li class="on"><a href="">1</a><i></i><p>填写账户信息</p></li>
            <li class="on"><a href="">2</a><i></i><p>填写充值金额</p></li>
            <li class="on"><a href="">3</a><i></i><p>确认充值信息</p></li>
            <li class="on"><a href="">4</a><i></i><p>前往充值</p></li>
            <li class="on"><a href="">5</a><i></i><p>充值成功</p></li>
        </ul>
        <div class="cd_s_list">
            <h3>提现完成！</h3>
            <p>若正常到账，你的充值将于30分钟内入金到交易账号中，请耐心等待！<br>若没有及时到账请联系你的居间商，或致电400-020-0158<br>你的充值流水号为：${orderNo}（建议你将本页面截图保留，直到交易账号中金额到账）</p>
        </div>
    </div>
</div>
</body>
</html>
