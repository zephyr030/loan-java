<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
    <title>首页,充值提现系统</title>
    <meta content="" name="description">
    <link rel="stylesheet" type="text/css" href="${path}/static/css/index.css">
    <%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
    <script type="text/javascript">
        function gotoRecharge() {

            var check = $("input[type='checkbox']:checkbox:checked").length;
            if(check == 0) {
                alert("请勾选充值须知");
                return false;
            } else {
                return true;
            }
        }
    </script>
</head>

<body>
<div class="main">
    <h1>国际期货操盘—充值提现系统</h1>
    <div class="mainctn">
        <a href="${path}/user/recharge" class="main_btn1" onclick="return gotoRecharge();">我要充值</a>
        <a href="${path}/user/withdraw/index" class="main_btn2" onclick="return gotoRecharge();">我要提现</a>
    </div>
    <div class="main_agree"><input type="checkbox" checked="checked"><span>我已经阅读并完全同意</span><a href="#"  class="link">《充值须知》</a></div>
</div>
</body>
</html>