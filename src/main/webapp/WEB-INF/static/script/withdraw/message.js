$(document).ready(function(){
    $("#sendMessage").click(sendMessage);
});

function sendMessage() {
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
                alert("ok");
            }
        }
    });
}

