$(document).ready(function(){
    $("#withdrawBn").click(withdraw);
});

function withdraw() {
    var account = $.trim($("#account").val());
    var customerName = $.trim($("#customerName").val());
    var cardNumber = $.trim($("#cardNumber").val());
    var bank = $.trim($("#bank").val());
    var mobile = $.trim($("#mobile").val());

    if(!validateInfo(account,customerName,cardNumber,mobile)) {
        return;
    }

    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        dataType: "json",
        url:path + "/user/withdraw/index",
        data:{
            account: account,
            customerName: customerName,
            cardNumber: cardNumber,
            bank: bank,
            mobile: mobile
        },
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
            if(!data.success) {
                alert(data.message);
            }else {
                $("#drawForm").submit();
            }
        }
    });
}

function validateInfo(account, customerName, cardNumber, mobile) {
    if(!account) {
        alert("请输入操盘账号信息");
        $("#account").focus();
        return false;
    }
    if(!checkAccount(account)) {
        alert("操盘账号格式有误");
        $("#account").focus();
        return false;
    }

    if(!customerName) {
        alert("请输入开户姓名");
        $("#customerName").focus();
        return false;
    }
    if(!cardNumber) {
        alert("请输入银行卡号");
        $("#cardNumber").focus();
        return false;
    }
    if(!mobile) {
        alert("请输入手机号");
        $("#mobile").focus();
        return false;
    }
    if(!checkMoblie(mobile)) {
        alert("手机号格式有误");
        $("#mobile").focus();
        return false;
    }
    return true;
}