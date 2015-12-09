$(document).ready(function(){
    $("#sendMessage").click(sendMessage);
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
                setTimeout(timeLeft, 1000);

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
        $("#sendMessage").html(wd.time + "后重发……");
        setTimeout(timeLeft, 1000);
    }else {
        wd.time = 60;
        wd.isLock = false;
        $("#sendMessage").html("发送验证码");
    }
}

