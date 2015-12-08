/**
 * 方法描述:登录
 *
 * author 小刘
 * version v1.0
 * date 2015/12/8
 */
$(function(){
    //验证用户
    $("#username").blur(function(event){
        var username = $("#username").val();
        if(username.length != 0){
            $.ajax({
                type:"post",
                url: "isUser",
                data: {username:username},
                dataType: "json",
                success: function(result){
                    if(parseInt(result.code) != 0){
                        var box = bootbox.alert("用户不存在", function() {});
                        //box.on('shown',function(){
                        //    $("#username").focus();
                        //});
                    }
                }
            });
        }
    });

    $("#username").keyup(function(event) {
        var charCode= (navigator.appName=="Netscape")?event.which:event.keyCode;
        if(charCode == 13){
            $("#password").focus();
        }
    });

    $("#password").keyup(function(event) {
        var charCode= (navigator.appName=="Netscape")?event.which:event.keyCode;
        if(charCode == 13){
           $("#login").click();
        }
    });

    //登录
    $("#login").click(function(event){
        var username = $("#username").val();
        var password = $("#password").val();
        if(username.length == 0){
            bootbox.alert("请填写用户名", function() {});
        }else if(password.length == 0){
            bootbox.alert("请输入密码", function() {$("#password").focus();});
            //bootbox.bind('shown',function(){
            //    $("#password").focus();
            //});
        }else{
            $.ajax({
                type:"post",
                url: "login",
                data: {username:username,password:password},
                dataType: "json",
                success: function(result){
                    if(parseInt(result.code) != 0){
                        var box = bootbox.alert("用户名或密码错误", function() {});
                        //box.on('shown',function(){
                        //    $("#username").focus();
                        //});
                    }else{
                        window.location.href = "main";
                    }
                }
            });
        }
    });
});
