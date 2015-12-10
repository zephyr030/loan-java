$(document).ready(function(){
    $("#sendMessage").click(sendMessage);
    $("#validateBn").click(validateCode);
});

function sendMessage() {
    if(wd.isLock) {
        return;
    }

    var account = $("#account").val();
    $.ajax({
        cache: false,
        async: false,
        type: "GET",
        dataType: "json",
        url:path + "/user/withdraw/message/send",
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
                alert("验证码已发送，请注意查收");
                wd.isLock = true;
                $("#sendMessage").addClass("not");
                setTimeout(timeLeft, 1);

            }
        }
    });
}

var wd = {
    time: 60,
    isLock: false
}

function timeLeft() {
    wd.time = wd.time - 1;

    if(wd.time >= 0) {
        $("#sendMessage").html(wd.time + "秒后重发……");
        setTimeout(timeLeft, 1000);
    }else {
        wd.time = 60;
        wd.isLock = false;
        $("#sendMessage").removeClass("not");
        $("#sendMessage").html("发送验证码");
    }
}



function validateCode() {
    var account = $("#account").val();
    var code = $.trim($("#v_code").val());

    if(code == "" || code.length != 6) {
        alert("请输入6位数验证码");
        return;
    }

    $.ajax({
        cache: false,
        async: false,
        type: "GET",
        dataType: "json",
        url:path + "/user/withdraw/message/validate",
        data:{
            account: account,
            code: code
        },
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
            if(!data.success) {
                alert(data.message);
            }else {
                document.location.href = path + "/user/withdraw/success?flowNo=" + data.data;
            }
        }
    });
}

