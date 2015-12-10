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
        <img src="images/logo.png">
        <img src="images/icon.png">
        <em>自助提现</em>
        <a href=""><< 返回首页</a>
    </div>
    <div class="cd_main">
        <ul class="cd_step">
            <li class="on"><a href="">1</a><i></i><p>填写账户信息</p></li>
            <li class="on"><a href="">2</a><i></i><p>填写提现金额</p></li>
            <li class="on"><a href="">3</a><i></i><p>短信确认信息</p></li>
            <li class="on"><a href="">4</a><i></i><p>提现成功</p></li>
        </ul>
        <div class="cd_s_list">
            <h3>提现完成！</h3>
            <p>你的提现将于2小时内转入到你的银行卡中，请耐心等待！<br>若没有及时到账请联系你的居间商，或致电400-020-0158<br>你的提现流水号为：${flowNo}（建议你将本页面截图保留，直到银行卡金额到账）</p>
        </div>
    </div>
</div>
</body>
</html>
