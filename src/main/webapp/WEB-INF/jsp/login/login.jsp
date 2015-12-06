<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>登录</title>
    <%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
</head>
<body>
    <input type="text" name="username">
    <input type="password" name="password">
    <button>登录</button>
</body>
<script type="application/javascript">
    $(function(){
        $("button").click(function() {
            var username = $.trim($("input[name=username]").val());
            var password = $.trim($("input[name=password]").val());

            $.ajax({
                cache: true,
                type: "POST",
                dataType: "text",
                url:path + "/admin/login",
                data:{
                    username: username,
                    password: password
                },// 你的formid
                async: false,
                error: function(request) {
                    alert("Connection error");
                },
                success: function(data) {
                    if("true" == data) {
                        alert("登录成功");
                    } else {
                        alert("请检查用户名和密码是否正确");
                    }
                }
            });
        });
    });
</script>
</html>
